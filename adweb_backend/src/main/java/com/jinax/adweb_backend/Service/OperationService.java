package com.jinax.adweb_backend.Service;

import com.jinax.adweb_backend.Entity.Operation;
import com.jinax.adweb_backend.Repository.OperationRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : chara
 */
@Service
public class OperationService {
    private final OperationRepository operationRepository;
    private final EntityManager entityManager;

    public OperationService(OperationRepository operationRepository, EntityManager entityManager) {
        this.operationRepository = operationRepository;
        this.entityManager = entityManager;
    }


    public int insertOperation(Operation operation){
        Operation save = operationRepository.save(operation);
        return save.getId();
    }

    public List<Map<String,Integer>> getOperationsByUsernameAndGameId(String username,int gameId){
        Query q = entityManager.createNativeQuery("select `from`,`to`,plate_size,hh.id history_id from operation left join hanoi_history hh on operation.id = hh.operation_id left join user u on operation.user_id = u.id where u.username = :username and game_id = :gameId");
        q.setParameter("username",username);
        q.setParameter("gameId",gameId);
        List<Object[]> list = q.getResultList();
        List<Map<String,Integer>> resultMap = new ArrayList<>();
        for (Object[] result : list) {
            HashMap<String, Integer> map = new HashMap<>();
            map.put("from",((Number)result[0]).intValue());
            map.put("to",((Number)result[1]).intValue());
            map.put("target_hanoi",((Number)result[2]).intValue());
            map.put("after_history_id",((Number)result[3]).intValue());
            resultMap.add(map);
        }
        return resultMap;
    }
}
