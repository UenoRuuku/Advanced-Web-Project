package com.jinax.adweb_backend.Repository;

import com.jinax.adweb_backend.Entity.GameHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author jinaxCai
 */
@Repository
public interface GameHistoryRepository extends JpaRepository<GameHistory,Integer> {
    @Modifying
    @Query(value = "update game_history set step_num = step_num + 1 where id = ?1",nativeQuery = true)
    public void updateStepNum(int gameId);

    @Modifying
    @Query(value = "update game_history set end_time = ?2 where id = ?1",nativeQuery = true)
    public void endGame(int gameId, Timestamp time);

    @Query(value = "select game_history.id,start_time,end_time,step_num from game_history left join hanoi_history hh on game_history.id = hh.game_id left join operation o on hh.operation_id = o.id left join user u on o.user_id = u.id where u.username = ?1",nativeQuery = true)
    public List<GameHistory> getAllGamesInvolvingUser(String username);
}
