package com.finverse.dao;

import com.finverse.model.Transaction;
import java.util.List;

public interface TransactionDAO {

    void saveTransaction(Transaction transaction);
    List<Transaction> getTransactions(String accountNumber);

    List<Transaction> getAllTransactions();
    List<Transaction> getTransactions();
}