package com.finverse.dao;

import com.finverse.model.Account;
import com.finverse.model.AccountStatus;
import com.finverse.model.AccountType;

import java.math.BigDecimal;
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

    @Override
    public void deleteAccount(Account account) {
        accounts.remove(account);
    }

    @Override
    public void blockAccount(Account account) {
        account.setAccountStatus(AccountStatus.BLOCKED);
    }

    @Override
    public void activateAccount(Account account) {
        account.setAccountStatus(AccountStatus.ACTIVE);
    }

    @Override
    public void closeAccount(Account account) {
        account.setAccountStatus(AccountStatus.CLOSED);
    }

    @Override
    public void changeAccountType(Account account, AccountType accountType) {
        account.setAccountType(accountType);
    }

    @Override
    public BigDecimal getTotalBalance() {
        BigDecimal total = BigDecimal.ZERO;
        for(Account account : accounts){
            total = total.add(account.getBalance());
        }
        return total;
    }

    @Override
    public int getTotalAccounts() {
        return accounts.size();
    }

    @Override
    public int getActiveAccounts(){
        int count=0;
        for(Account account:accounts){
            if(account.getAccountStatus()==AccountStatus.ACTIVE){
                count++;
            }
        }
        return count;
    }

    @Override
    public int getClosedAccounts(){
        int count=0;
        for(Account account:accounts){
            if(account.getAccountStatus()==AccountStatus.CLOSED){
                count++;
            }
        }
        return count;
    }
}