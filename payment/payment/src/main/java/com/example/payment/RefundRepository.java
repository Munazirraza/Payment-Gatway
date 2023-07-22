package com.example.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefundRepository extends JpaRepository<Refund, Long> {
    @Query("SELECT r.userId, SUM(r.amount) FROM Refund r GROUP BY r.userId ORDER BY SUM(r.amount) DESC")
    List<Object[]> findUserIdWithMaxRefundAmount();
}




