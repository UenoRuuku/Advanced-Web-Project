package com.jinax.adweb_backend.Service;

import com.jinax.adweb_backend.Entity.HanoiHistory;
import com.jinax.adweb_backend.Repository.HanoiHistoryRepository;
import org.springframework.stereotype.Service;

/**
 * @author jinaxCai
 */
@Service
public class HanoiHistoryService {
    private final HanoiHistoryRepository hanoiHistoryRepository;

    public HanoiHistoryService(HanoiHistoryRepository hanoiHistoryRepository) {
        this.hanoiHistoryRepository = hanoiHistoryRepository;
    }

    public Integer insertHanoiHistory(HanoiHistory hanoiHistory){
        hanoiHistory.setId(null);
        HanoiHistory save = hanoiHistoryRepository.save(hanoiHistory);
        return save.getId();
    }
}
