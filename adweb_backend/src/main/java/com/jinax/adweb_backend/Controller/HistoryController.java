package com.jinax.adweb_backend.Controller;

import com.jinax.adweb_backend.Component.exception.HistoryNotExistException;
import com.jinax.adweb_backend.Entity.GameHistory;
import com.jinax.adweb_backend.Entity.HanoiHistory;
import com.jinax.adweb_backend.Service.GameHistoryService;
import com.jinax.adweb_backend.Service.HanoiHistoryService;
import com.jinax.adweb_backend.Service.OperationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author : chara
 */
@RestController
@Api("历史记录相关操作")
@RequestMapping("/history")
public class HistoryController {

    private final GameHistoryService gameHistoryService;
    private final HanoiHistoryService hanoiHistoryService;
    private final OperationService operationService;

    public HistoryController(GameHistoryService gameHistoryService, HanoiHistoryService hanoiHistoryService, OperationService operationService) {
        this.gameHistoryService = gameHistoryService;
        this.hanoiHistoryService = hanoiHistoryService;
        this.operationService = operationService;
    }

    @GetMapping("/{username}")
    @ApiOperation("获取用户参与过的游戏记录")
    @ResponseBody
    public List<GameHistory> getGamesByUsername(@PathVariable("username") String username){
        return gameHistoryService.getGamesByUsername(username);
    }

    @GetMapping("/hanoi/all/{gameId}")
    @ApiOperation("获取一盘游戏中的全部棋盘历史状态记录")
    @ResponseBody
    public List<HanoiHistory> getHanoiHistoryByGameId(@PathVariable("gameId") Integer gameId){
        return hanoiHistoryService.getAllByGameId(gameId);
    }

    @GetMapping("/hanoi/{historyId}")
    @ApiOperation("获取特定一步的棋盘历史状态")
    @ResponseBody
    public HanoiHistory getHanoiHistoryByHistoryId(@PathVariable("historyId") Integer historyId){
        return hanoiHistoryService.getById(historyId).orElseThrow(()-> new HistoryNotExistException("历史记录不存在"));
    }

    @GetMapping("/hanoi/operation/{username}/{gameId}")
    @ApiOperation("获取用户在某局游戏中的操作记录")
    @ResponseBody
    public List<Map<String,Integer>> getHanoiHistoryByHistoryId(@PathVariable("username") String username, @PathVariable("gameId") Integer gameId){
        return operationService.getOperationsByUsernameAndGameId(username,gameId);
    }
}
