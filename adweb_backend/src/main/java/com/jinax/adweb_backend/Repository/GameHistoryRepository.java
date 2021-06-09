package com.jinax.adweb_backend.Repository;

import com.jinax.adweb_backend.Entity.GameHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author jinaxCai
 */
@Repository
public interface GameHistoryRepository extends JpaRepository<GameHistory,Integer> {
    @Query(value = "update game_history set step_num = step_num + 1 where id = ?1",nativeQuery = true)
    public boolean updateStepNum(int gameId);
}
