package com.finverse.service;

import com.finverse.dao.TransactionDAO;
import com.finverse.dao.TransactionDAOImpl;
import com.finverse.model.Transaction;
import com.finverse.model.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class TransactionService {
    private static TransactionService instance;
    private TransactionDAO transactionDAO = new TransactionDAOImpl();
    private static int nextTransactionId = 1;
    private TransactionService() {
    }

    public static TransactionService getInstance() {
        if (instance == null) {
            instance = new TransactionService();
        }
        return instance;
    }

    public void saveTransaction(int accountId,
                                TransactionType type,
                                BigDecimal amount,
                                BigDecimal balance,
                                String remarks) {

        Transaction transaction = new Transaction();

        transaction.setTransactionId(nextTransactionId++);
        transaction.setAccountId(accountId);
        transaction.setTransactionType(type);
        transaction.setAmount(amount);
        transaction.setBalanceAfterTransaction(balance);
        transaction.setRemarks(remarks);
        transaction.setTransactionTime(LocalDateTime.now());

        transactionDAO.saveTransaction(transaction);
    }
    public List<Transaction> getTransactions(int accountId) {
        return transactionDAO.getTransactions(accountId);
    }

    public List<Transaction> getMiniStatement(int accountId) {
        List<Transaction> transactions =
                transactionDAO.getTransactions(accountId);
        int size = transactions.size();
        if (size <= 5) {
            return transactions;
        }
        return transactions.subList(size - 5, size);
    }

    public int getTotalTransactions() {
        return transactionDAO.getTransactions().size();
    }
}