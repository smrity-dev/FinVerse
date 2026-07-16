package com.finverse.dao;

import com.finverse.model.Account;
import java.util.List;
import java.math.BigDecimal;
import com.finverse.model.AccountType;

public interface AccountDAO {

    boolean saveAccount(Account account);
    Account getAccountByUserId(int userId);

    void updateAccount(Account account);
    Account getAccountByAccountNumber(String accountNumber);
    List<Account> getAllAccounts();

    void deleteAccount(Account account);
    void blockAccount(Account account);
    void activateAccount(Account account);
    void closeAccount(Account account);
    void changeAccountType(Account account, AccountType accountType);
    BigDecimal getTotalBalance();
    int getTotalAccounts();
    int getActiveAccounts();
    int getClosedAccounts();

}