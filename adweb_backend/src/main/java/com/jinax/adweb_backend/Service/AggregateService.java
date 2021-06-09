package com.jinax.adweb_backend.Service;

import com.jinax.adweb_backend.Component.Tower.Hanoi;
import com.jinax.adweb_backend.Entity.HanoiHistory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jinaxCai
 */
@Service
public class AggregateService {
    private  final HanoiHistoryService hanoiHistoryService;
    private final GameHistoryService gameHistoryService;

    public AggregateService(HanoiHistoryService hanoiHistoryService, GameHistoryService gameHistoryService) {
        this.hanoiHistoryService = hanoiHistoryService;
        this.gameHistoryService = gameHistoryService;
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
}
