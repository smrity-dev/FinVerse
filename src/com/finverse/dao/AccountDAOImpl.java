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
}