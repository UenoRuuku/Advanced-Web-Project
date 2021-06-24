package com.jinax.adweb_backend.Repository;

import com.jinax.adweb_backend.Entity.HanoiHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author jinaxCai
 */

@Repository
public interface HanoiHistoryRepository extends JpaRepository<HanoiHistory,Integer> {
    public List<HanoiHistory> getAllByGameIdEquals(Integer gameId);
    @Query(value = "SELECT new HanoiHistory(h.id,h.firstTower,h.secondTower,h.thirdTower,h.operationId,h.gameId) FROM HanoiHistory h WHERE h.id = :hanoiId")
    public Optional<HanoiHistory> getByIdEquals(Integer hanoiId);
}
