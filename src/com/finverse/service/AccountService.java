package com.finverse.service;

import com.finverse.dao.AccountDAO;
import com.finverse.dao.AccountDAOImpl;
import com.finverse.model.Account;
import com.finverse.model.AccountStatus;
import com.finverse.model.AccountType;
import com.finverse.model.User;

public class AccountService {
    private static AccountService instance;
    private AccountDAO accountDAO = new AccountDAOImpl();
    private AccountService() {
    }

    public static AccountService getInstance() {
        if (instance == null) {
            instance = new AccountService();
        }
        return instance;
    }

    public Account createAccount(User user) {
        Account account = new Account();
        account.setUserId(user.getUserId());
        account.setAccountType(AccountType.SAVINGS);
        account.setAccountStatus(AccountStatus.ACTIVE);
        accountDAO.saveAccount(account);
        return account;
    }

    public Account getAccount(int userId) {
        return accountDAO.getAccountByUserId(userId);
    }
}