package com.jinax.adweb_backend.Repository;

import com.jinax.adweb_backend.Entity.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author jinaxCai
 */
@Repository
public interface OperationRepository extends JpaRepository<Operation,Integer> {
}
