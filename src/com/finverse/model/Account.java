package com.finverse.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Account {

    private int accountId;
    private String accountNumber;
    private AccountType accountType;
    private AccountStatus accountStatus;
    private BigDecimal balance;
    private int userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Default Constructor
    public Account() {
        this.balance = BigDecimal.ZERO;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getter/Setter Methods from right click after declaration
    public int getAccountId() {
        return accountId;
    }
    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public AccountType getAccountType() {
        return accountType;
    }
    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }
    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public BigDecimal getBalance() {
        return balance;
    }
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Account(int accountId, String accountNumber, AccountType accountType, AccountStatus accountStatus, BigDecimal balance, int userId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.accountStatus = accountStatus;
        this.balance = balance;
        this.userId = userId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {


        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy | hh:mm:ss a");

        return "Account{" + "accountId=" + accountId +
                ", accountNumber='" + accountNumber + '\'' +
                ", accountType='" + accountType + '\'' +
                ", accountStatus='" + accountStatus + '\'' +
                ", balance=" + balance +
                ", userId=" + userId +
                ", createdAt=" + createdAt.format(formatter) +
                ", updatedAt=" + updatedAt.format(formatter) +
                '}';
    }
}