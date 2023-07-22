package com.example.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Modifying
    @Query("DELETE FROM Transaction t WHERE t.userId = :userId AND t.status = 'failure'")
    void deleteFailedTransactions();

    List<Transaction> findByUserIdAndStatus(Long userId, String success);
}
