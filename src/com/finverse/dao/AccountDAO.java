package com.finverse.dao;

import com.finverse.model.Account;

import java.util.List;

public interface AccountDAO {

    void saveAccount(Account account);
    Account getAccountByUserId(int userId);

    void updateAccount(Account account);
    Account getAccountByAccountNumber(String accountNumber);
    List<Account> getAllAccounts();

}