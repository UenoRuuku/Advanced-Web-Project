package com.jinax.adweb_backend.Service;

import com.jinax.adweb_backend.Entity.GameHistory;
import com.jinax.adweb_backend.Repository.GameHistoryRepository;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author jinaxCai
 */
@Service
public class GameHistoryService {
    private final GameHistoryRepository gameHistoryRepository;

    public GameHistoryService(GameHistoryRepository gameHistoryRepository) {
        this.gameHistoryRepository = gameHistoryRepository;
    }

    /**
     *
     * @return gameId
     * @throws  RuntimeException if update db failed
     */
    public Integer createNewGame(){
        GameHistory save = gameHistoryRepository.save(new GameHistory());
        return save.getId();
    }

    public boolean endGame(int gameId){
        gameHistoryRepository.endGame(gameId, new Timestamp(System.currentTimeMillis()));
        return true;
    }

    public List<GameHistory> getGamesByUsername(String username){
        return gameHistoryRepository.getAllGamesInvolvingUser(username);
    }

    /**
     *
     * @return true on success
     * @throws  RuntimeException if update db failed
     */
    public boolean updateStepNum(int gameId){
        return gameHistoryRepository.updateStepNum(gameId);
    }
}
