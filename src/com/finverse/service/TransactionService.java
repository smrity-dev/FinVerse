package com.finverse.service;

import com.finverse.dao.TransactionDAO;
import com.finverse.dao.TransactionDAOImpl;
import com.finverse.model.Transaction;
import com.finverse.model.TransactionStatus;
import com.finverse.model.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class TransactionService {
    private static TransactionService instance;
    private TransactionDAO transactionDAO = new TransactionDAOImpl();
    private TransactionService() {
    }

    public static TransactionService getInstance() {
        if (instance == null) {
            instance = new TransactionService();
        }
        return instance;
    }

    public void saveTransaction(String accountNumber,
                                TransactionType type,
                                BigDecimal amount,
                                BigDecimal balance,
                                String remarks) {

        Transaction transaction = new Transaction();

        transaction.setReferenceNumber(generateReferenceNumber());
        transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        transaction.setAccountNumber(accountNumber);
        transaction.setTransactionType(type);
        transaction.setAmount(amount);
        transaction.setBalanceAfterTransaction(balance);
        transaction.setRemarks(remarks);
        transaction.setTransactionTime(LocalDateTime.now());
        boolean saved = transactionDAO.saveTransaction(transaction);
        if(!saved){
            System.out.println("Transaction Failed!");
            return;
        }

    }
    public List<Transaction> getTransactions(String accountNumber) {
        return transactionDAO.getTransactions(accountNumber);
    }

    public List<Transaction> getMiniStatement(String accountNumber) {
        List<Transaction> transactions =
                transactionDAO.getTransactions(accountNumber);
        int size = transactions.size();
        if (size <= 5) {
            return transactions;
        }
        return transactions.subList(size - 5, size);
    }

    public int getTotalTransactions() {
        return transactionDAO.getTransactions().size();
    }

    private String generateReferenceNumber(){
        return "TXN" + System.currentTimeMillis();
    }
}