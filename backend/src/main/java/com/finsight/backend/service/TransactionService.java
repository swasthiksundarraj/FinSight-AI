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

    public Transaction createTransaction(TransactionRequest transactionRequest, User user) {
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setType(transactionRequest.getType());
        transaction.setCategory(transactionRequest.getCategory());
        transaction.setDescription(transactionRequest.getDescription());
        transaction.setTransactionDate(transactionRequest.getTransactionDate());
        transaction.setUser(user);

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactionsByUser(User user) {
        return transactionRepository.findByUserId(user.getId());
    }

    public Optional<Transaction> getTransactionByIdAndUser(Long id, User user) {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        if (transaction.isPresent() && transaction.get().getUser().getId().equals(user.getId())) {
            return transaction;
        }
        return Optional.empty();
    }

    public void deleteTransaction(Long id, User user) {
        Optional<Transaction> transaction = getTransactionByIdAndUser(id, user);
        if (transaction.isEmpty()) {
            throw new IllegalArgumentException("Transaction not found or unauthorized.");
        }
        transactionRepository.deleteById(id);
    }
}