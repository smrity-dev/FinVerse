package com.finverse.dao;

import com.finverse.model.Transaction;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAOImpl implements TransactionDAO {

    private static final List<Transaction> transactions = new ArrayList<>();

    @Override
    public void saveTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    @Override
    public List<Transaction> getTransactions(int accountId) {
        List<Transaction> list = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getAccountId() == accountId) {
                list.add(transaction);
            }
        }
        return list;
    }

    // User
    @Override
    public List<Transaction> getAllTransactions(){
        return transactions;
    }

    // Admin
    @Override
    public List<Transaction> getTransactions() {
        return transactions;
    }
}
