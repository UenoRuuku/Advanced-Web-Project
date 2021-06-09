package com.jinax.adweb_backend.Repository;

import com.jinax.adweb_backend.Entity.HanoiHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author jinaxCai
 */

@Repository
public interface HanoiHistoryRepository extends JpaRepository<HanoiHistory,Integer> {
}
