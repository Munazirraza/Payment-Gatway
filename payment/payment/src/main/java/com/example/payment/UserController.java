package com.example.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private RefundRepository refundRepository;

    // POST API - Add a User
    @PostMapping
    public User addUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    // POST API - Add a Transaction for a given amount
    @PostMapping("/{userId}/transactions")
    public Transaction addTransaction(@PathVariable Long userId, @RequestBody Transaction transaction) {
        transaction.setUserId(userId);
        return transactionRepository.save(transaction);
    }

    // GET API - Find the total amount of successful transactions for a user
    @GetMapping("/{userId}/total-successful-amount")
    public Double getTotalSuccessfulAmount(@PathVariable Long userId) {
        List<Transaction> successfulTransactions = transactionRepository.findByUserIdAndStatus(userId, "success");
        return successfulTransactions.stream().mapToDouble(Transaction::getAmount).sum();
    }

    // DELETE API - Delete all transactions that are failed
    @DeleteMapping("/failed-transactions")
    public ResponseEntity<String> deleteFailedTransactions() {
        try {
            transactionRepository.deleteFailedTransactions();
            return ResponseEntity.ok("All failed transactions have been deleted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete failed transactions.");
        }
    }


    // GET API - Return UserId with maximum refund amount
    @GetMapping("/user-with-max-refund")
    public Long getUserIdWithMaxRefundAmount() {
        List<Object[]> results = refundRepository.findUserIdWithMaxRefundAmount();
        if (results.isEmpty()) {
            return null;
        }
        return ((Number) results.get(0)[0]).longValue();
    }
}

