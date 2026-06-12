package com.finsight.backend.repository;

import com.finsight.backend.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT COALESCE(SUM(t.amount), 0.0) FROM Transaction t WHERE UPPER(t.type) = UPPER(:type)")
    Double sumAmountByType(@Param("type") String type);
}
