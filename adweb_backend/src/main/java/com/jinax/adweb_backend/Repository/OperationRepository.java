package com.jinax.adweb_backend.Repository;

import com.jinax.adweb_backend.Entity.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author jinaxCai
 */
@Repository
public interface OperationRepository extends JpaRepository<Operation,Integer> {
//    @Query(value = "select 'from',`to`,plate_size,hh.id history_id from operation left join hanoi_history hh on operation.id = hh.operation_id left join user u on operation.user_id = u.id where u.username = ?1 and game_id = ?2",nativeQuery = true)
//    public List<Map<String,Integer>> getAllOperationsByUserIdAndGameId(String username, Integer gameId);
    @Modifying(flushAutomatically = true)
    @Query(value = "insert into operation (user_id, `from`, `to`, plate_size) VALUES (:#{#o.userId},:#{#o.from},:#{#o.to},:#{#o.plateSize})",nativeQuery = true)
    public void insertOperation(Operation o);
}
