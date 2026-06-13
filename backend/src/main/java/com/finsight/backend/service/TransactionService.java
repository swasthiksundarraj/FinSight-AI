package com.finsight.backend.service;

import com.finsight.backend.dto.TransactionRequest;
import com.finsight.backend.entity.Transaction;
import com.finsight.backend.entity.User;
import com.finsight.backend.repository.TransactionRepository;
import com.finsight.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    public Transaction createTransaction(TransactionRequest transactionRequest) {
        Optional<User> user = userRepository.findById(transactionRequest.getUserId());

        if (user.isEmpty()) {
            throw new IllegalArgumentException("User not found with ID: " + transactionRequest.getUserId());
        }

        Transaction transaction = new Transaction();
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setType(transactionRequest.getType());
        transaction.setCategory(transactionRequest.getCategory());
        transaction.setDescription(transactionRequest.getDescription());
        transaction.setTransactionDate(transactionRequest.getTransactionDate());
        transaction.setUser(user.get());

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }

    public List<Transaction> getTransactionsByUserId(Long userId) {
        // Verify that the user exists
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }
        return transactionRepository.findByUserId(userId);
    }

    public void deleteTransaction(Long id) {
        if (!transactionRepository.existsById(id)) {
            throw new IllegalArgumentException("Transaction not found with ID: " + id);
        }
        transactionRepository.deleteById(id);
    }
}

