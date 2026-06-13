package com.finsight.backend.repository;

import com.finsight.backend.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT COALESCE(SUM(t.amount), 0.0) FROM Transaction t WHERE UPPER(t.type) = UPPER(:type)")
    Double sumAmountByType(@Param("type") String type);

    @Query("SELECT COALESCE(SUM(t.amount), 0.0) FROM Transaction t WHERE t.user.id = :userId AND UPPER(t.type) = UPPER(:type)")
    Double sumAmountByTypeAndUserId(@Param("type") String type, @Param("userId") Long userId);

    @Query("SELECT COALESCE(SUM(t.amount), 0.0) FROM Transaction t WHERE t.user.id = :userId AND UPPER(t.category) = UPPER(:category)")
    Double sumAmountByUserIdAndCategory(@Param("userId") Long userId, @Param("category") String category);

    @Query("SELECT t FROM Transaction t WHERE t.user.id = :userId")
    List<Transaction> findByUserId(@Param("userId") Long userId);
}