package com.jinax.adweb_backend.Service;

import com.jinax.adweb_backend.Component.Tower.Hanoi;
import com.jinax.adweb_backend.Entity.HanoiHistory;
import com.jinax.adweb_backend.Entity.Operation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jinaxCai
 */
@Service
public class AggregateService {
    private  final HanoiHistoryService hanoiHistoryService;
    private final GameHistoryService gameHistoryService;
    private final OperationService operationService;
    public AggregateService(HanoiHistoryService hanoiHistoryService, GameHistoryService gameHistoryService, OperationService operationService) {
        this.hanoiHistoryService = hanoiHistoryService;
        this.gameHistoryService = gameHistoryService;
        this.operationService = operationService;
    }

    @Transactional
    public Integer startNewGame(Hanoi hanoi){
        Integer gameId = gameHistoryService.createNewGame();
        HanoiHistory history = new HanoiHistory();
        history.setOperationId(-1);
        history.setGameId(gameId);
        history.setFirstTower(hanoi.getFirstString());
        history.setSecondTower(hanoi.getSecondString());
        history.setThirdTower(hanoi.getThirdString());
        hanoiHistoryService.insertHanoiHistory(history);
        return gameId;
    }

    @Transactional
    public boolean endGame(Operation operation,Hanoi hanoi,int gameId){
        saveOperationTogetherWithHanoiHistory(operation, hanoi, gameId);
        return endGame(operation,hanoi,gameId);
    }

    @Transactional
    public Integer saveOperationTogetherWithHanoiHistory(Operation operation,Hanoi hanoi,int gameId){
        int operationId = operationService.insertOperation(operation);
        HanoiHistory history = new HanoiHistory(
                hanoi.getFirstString(),hanoi.getSecondString(),hanoi.getThirdString(),operationId,gameId, operation);
        gameHistoryService.updateStepNum(gameId);
        return hanoiHistoryService.insertHanoiHistory(history);
    }
}
