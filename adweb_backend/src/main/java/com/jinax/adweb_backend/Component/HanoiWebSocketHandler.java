package com.jinax.adweb_backend.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jinax.adweb_backend.Component.Tower.Hanoi;
import com.jinax.adweb_backend.Entity.Operation;
import com.jinax.adweb_backend.Service.AggregateService;
import com.jinax.adweb_backend.Service.GameHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author : chara
 */
public class HanoiWebSocketHandler implements WebSocketHandler {
    private static final int ID_NOT_NEED = -1;
    private static final Map<String,WebSocketSession> users = new ConcurrentHashMap<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(HanoiWebSocketHandler.class);
    private final GameHistoryService gameHistoryService;
    private final AggregateService aggregateService;

    private final Hanoi hanoi;
    private WebSocketSession movingSession;
    private int from;
    private int id;
    private int gameId;
    private static final ExecutorService pool = Executors.newFixedThreadPool(5);

    public HanoiWebSocketHandler(GameHistoryService gameHistoryService,  AggregateService aggregateService, Hanoi hanoi) {
        this.gameHistoryService = gameHistoryService;
        this.hanoi = hanoi;
        this.aggregateService = aggregateService;
        this.gameId = aggregateService.startNewGame(hanoi);//TODO should handle exception
        movingSession = null;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        users.put((String) session.getAttributes().get("username"),session);
        LOGGER.info("ConnectionEstablished"+"=>当前在线用户的数量是:{}",users.size());
        FullTowerResponse fullTowerResponse = null;
        fullTowerResponse = new FullTowerResponse(hanoi.getNumPlates(),hanoi.getFirstArray(),hanoi.getSecondArray(),hanoi.getThirdArray(),id);
        ObjectMapper mapper = new ObjectMapper();
        String message = mapper.writeValueAsString(fullTowerResponse);
        pool.submit(new SendMessageTask(session,new TextMessage(message)));
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String username = (String) session.getAttributes().get("username");
        LOGGER.info("Data from {}"+"=>{}",username,message.getPayload());
        ObjectMapper mapper = new ObjectMapper();
        MovingRequest request = mapper.readValue((String) message.getPayload(), MovingRequest.class);
        switch (request.getType()){
            case MovingRequest.PLAYER_MOVING:{
                MovingResponse response = new MovingResponse(username,request.posX,request.posY,request.type,request.pillar,request.plane,ID_NOT_NEED);
                String returnMessage = mapper.writeValueAsString(response);
                sendMessageToUsers(returnMessage);
                break;
            }
            case MovingRequest.HANOI_START_MOVING:{
                if(movingSession != null){
                    return;
                }
                movingSession = session;
                from = request.pillar;
                MovingResponse response = new MovingResponse(username,request.posX,request.posY,request.type,request.pillar,request.plane,id++);
                String returnMessage = mapper.writeValueAsString(response);
                sendMessageToUsers(returnMessage);
                break;
            }
            case MovingRequest.HANOI_MOVING:{
                if(session != movingSession){
                    return;
                }
                MovingResponse response = new MovingResponse(username,request.posX,request.posY,request.type,request.pillar,request.plane,id++);
                String returnMessage = mapper.writeValueAsString(response);
                sendMessageToUsers(returnMessage);
                break;
            }
            case MovingRequest.HANOI_END_MOVING:{
                if(session != movingSession){
                    return;
                }
                int to = request.getPillar();
                boolean result = hanoi.update(from, to);//update hanoi
                //deal with db
                Operation operation = new Operation((Integer) session.getAttributes().get("id"),(short)from,(short)to,(short)hanoi.getTower(from).getLast().getSize());
                if(result){
                    //one game finished
                    aggregateService.saveOperationTogetherWithHanoiHistory(operation,hanoi,gameId);
                    hanoi.refresh();
                    this.gameId = aggregateService.startNewGame(hanoi);
                }else{
                    aggregateService.saveOperationTogetherWithHanoiHistory(operation,hanoi,gameId);
                }

                MovingResponse response = new MovingResponse(
                        username,request.posX,request.posY,request.type,request.pillar,request.plane,id++);
                String returnMessage = mapper.writeValueAsString(response);
                sendMessageToUsers(returnMessage);
                movingSession = null;
                break;
            }
            case MovingRequest.GET_TOWER:{
                FullTowerResponse fullTowerResponse = null;
                fullTowerResponse = new FullTowerResponse(hanoi.getNumPlates(),hanoi.getFirstArray(),hanoi.getSecondArray(),hanoi.getThirdArray(),id);
                String returnMessage = mapper.writeValueAsString(fullTowerResponse);
                pool.submit(new SendMessageTask(session,new TextMessage(returnMessage)));
            }
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if(session.isOpen()){
            session.close();
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        users.remove((String) session.getAttributes().get("username"));
        LOGGER.info("ConnectionClosed"+"=>当前在线用户的数量是:{}",users.size());
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    private void sendMessageToUsers(String message) throws JsonProcessingException {
        //这里可能因为并发问题导致访问到已经退出的 user，但是不关键
        for (WebSocketSession user : users.values()) {
            pool.submit(new SendMessageTask(user,new TextMessage(message)));// omit the future returned
        }
    }

    private static class MovingRequest{
        private static final int PLAYER_MOVING = 1;
        private static final int HANOI_START_MOVING = 2;
        private static final int HANOI_MOVING= 3;
        private static final int HANOI_END_MOVING = 4;
        private static final int GET_TOWER = 5;

        private float posX;
        private float posY;
        private int type;
        private int pillar;
        private int plane;

        public MovingRequest(float posX, float posY, int type, int pillar, int plane) {
            this.posX = posX;
            this.posY = posY;
            this.type = type;
            this.pillar = pillar;
            this.plane = plane;
        }

        public MovingRequest() {
        }

        public float getPosX() {
            return posX;
        }

        public void setPosX(float posX) {
            this.posX = posX;
        }

        public float getPosY() {
            return posY;
        }

        public void setPosY(float posY) {
            this.posY = posY;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getPillar() {
            return pillar;
        }

        public void setPillar(int pillar) {
            this.pillar = pillar;
        }

        public int getPlane() {
            return plane;
        }

        public void setPlane(int plane) {
            this.plane = plane;
        }
    }

    private static class MovingResponse{
        private String username;
        private float posX;
        private float posY;
        private int type;
        private int pillar;
        private int plane;
        private int id;

        public MovingResponse() {
        }

        public MovingResponse(String username, float posX, float posY, int type, int pillar, int plane, int id) {
            this.username = username;
            this.posX = posX;
            this.posY = posY;
            this.type = type;
            this.pillar = pillar;
            this.plane = plane;
            this.id = id;

        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public float getPosX() {
            return posX;
        }

        public void setPosX(float posX) {
            this.posX = posX;
        }

        public float getPosY() {
            return posY;
        }

        public void setPosY(float posY) {
            this.posY = posY;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getPillar() {
            return pillar;
        }

        public void setPillar(int pillar) {
            this.pillar = pillar;
        }

        public int getPlane() {
            return plane;
        }

        public void setPlane(int plane) {
            this.plane = plane;
        }
    }

    private static class FullTowerResponse {
        private int length;
        private int[] source;
        private int[] middle;
        private int[] target;
        private int id;

        public FullTowerResponse() {
        }

        public FullTowerResponse(int length) {
            this.length = length;
            this.source = new int[length];
            this.middle = new int[length];
            this.target = new int[length];
        }

        public FullTowerResponse(int length, int[] source, int[] middle, int[] target, int id) {
            this.length = length;
            this.source = source;
            this.middle = middle;
            this.target = target;
            this.id = id;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public int[] getSource() {
            return source;
        }

        public void setSource(int[] source) {
            this.source = source;
        }

        public int[] getMiddle() {
            return middle;
        }

        public void setMiddle(int[] middle) {
            this.middle = middle;
        }

        public int[] getTarget() {
            return target;
        }

        public void setTarget(int[] target) {
            this.target = target;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

}
