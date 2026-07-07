package com.finverse.dao;

import com.finverse.model.Account;

public interface AccountDAO {

    void saveAccount(Account account);
    Account getAccountByUserId(int userId);

}