package com.jinax.adweb_backend.Service;

import com.jinax.adweb_backend.Entity.HanoiHistory;
import com.jinax.adweb_backend.Entity.OuEntity.HanoiHistoryWithUsername;
import com.jinax.adweb_backend.Repository.HanoiHistoryRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

/**
 * @author jinaxCai
 */
@Service
public class HanoiHistoryService {
    private final HanoiHistoryRepository hanoiHistoryRepository;
    private final EntityManager entityManager;
    public HanoiHistoryService(HanoiHistoryRepository hanoiHistoryRepository, EntityManager entityManager) {
        this.hanoiHistoryRepository = hanoiHistoryRepository;
        this.entityManager = entityManager;
    }

    public Integer insertHanoiHistory(HanoiHistory hanoiHistory){
        hanoiHistory.setId(null);
        HanoiHistory save = hanoiHistoryRepository.save(hanoiHistory);
        return save.getId();
    }

    public List<HanoiHistoryWithUsername> getAllByGameId(int gameId){
        Query q = entityManager.createNativeQuery("select hanoi_history.id,first_tower,second_tower,third_tower,operation_id,game_id,u.username from hanoi_history left join operation o on hanoi_history.operation_id = o.id left join user u on o.user_id = u.id where game_id = :gameId");
        q.setParameter("gameId",gameId);
        List<Object[]> list = q.getResultList();
        List<HanoiHistoryWithUsername> resultList = new ArrayList<>();
        for (Object[] result : list) {
            HanoiHistoryWithUsername h = new HanoiHistoryWithUsername();
            h.setId(((Number)result[0]).intValue());
            h.setFirstTower(result[1].toString());
            h.setSecondTower(result[2].toString());
            h.setThirdTower(result[3].toString());
            h.setGameId(gameId);
            h.setOperationId(((Number)result[4]).intValue());
            h.setUsername(result[6].toString());
            resultList.add(h);
        }
        return resultList;
    }

    public Optional<HanoiHistory> getById(int historyId){
        return hanoiHistoryRepository.getByIdEquals(historyId);
    }
}
