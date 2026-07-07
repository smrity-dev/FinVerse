package com.finverse.service;

import com.finverse.dao.AccountDAO;
import com.finverse.dao.AccountDAOImpl;
import com.finverse.model.Account;
import com.finverse.model.AccountStatus;
import com.finverse.model.AccountType;
import com.finverse.model.User;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AccountService {

    private static AccountService instance;
    private AccountDAO accountDAO = new AccountDAOImpl();
    private static int nextAccountId = 1001;
    private static int nextAccountNumber = 100001;

    private AccountService() {
    }

    public static AccountService getInstance() {
        if (instance == null) {
            instance = new AccountService();
        }
        return instance;
    }

    private String generateAccountNumber() {
        return "FV" + nextAccountNumber++;
    }

    public Account createAccount(User user) {
        Account account = new Account();
        account.setAccountId(nextAccountId++);
        account.setAccountNumber(generateAccountNumber());
        account.setUserId(user.getUserId());
        account.setAccountType(AccountType.SAVINGS);
        account.setAccountStatus(AccountStatus.ACTIVE);
        account.setBalance(BigDecimal.ZERO);
        account.setCreatedAt(LocalDateTime.now());
        account.setUpdatedAt(LocalDateTime.now());
        accountDAO.saveAccount(account);
        return account;
    }

    public Account getAccount(int userId) {
        return accountDAO.getAccountByUserId(userId);
    }

}