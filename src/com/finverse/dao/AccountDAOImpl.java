package com.finverse.dao;

import com.finverse.model.Account;
import java.util.ArrayList;
import java.util.List;


public class AccountDAOImpl implements AccountDAO {
    private static final List<Account> accounts = new ArrayList<>();

    @Override
    public void saveAccount(Account account) {
        accounts.add(account);
    }

    @Override
    public Account getAccountByUserId(int userId) {
        for (Account account : accounts) {
            if (account.getUserId() == userId) {
                return account;
            }
        }
        return null;
    }

    @Override
    public void updateAccount(Account account) {
        // ArrayList me object reference update ho jata hai.
        // JDBC version me yahi SQL UPDATE banega.
    }

    @Override
    public Account getAccountByAccountNumber(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }
    @Override
    public List<Account> getAllAccounts() {
        return accounts;
    }
}