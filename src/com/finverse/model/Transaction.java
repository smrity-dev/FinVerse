package com.finverse.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {

    // Declare private variables
    private int transactionId;
    private TransactionType transactionType;
    private BigDecimal amount;
    private BigDecimal balanceAfterTransaction;
    private String remarks;
    private LocalDateTime transactionTime;
    private int accountId;

    //Default Constructor
    public Transaction() {
    }

    // Generate  Getter-Setter method
    public int getTransactionId() {
        return transactionId;
    }
    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }
    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getBalanceAfterTransaction() {
        return balanceAfterTransaction;
    }
    public void setBalanceAfterTransaction(BigDecimal balanceAfterTransaction) {
        this.balanceAfterTransaction = balanceAfterTransaction;
    }

    public String getRemarks() {
        return remarks;
    }
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }
    public void setTransactionTime(LocalDateTime transactionTime) {
        this.transactionTime = transactionTime;
    }

    public int getAccountId() {
        return accountId;
    }
    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }


    // Generate Constructor
    public Transaction(int transactionId, TransactionType transactionType, BigDecimal amount, BigDecimal balanceAfterTransaction, String remarks, LocalDateTime transactionTime, int accountId) {
        this.transactionId = transactionId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.balanceAfterTransaction = balanceAfterTransaction;
        this.remarks = remarks;
        this.transactionTime = transactionTime;
        this.accountId = accountId;
    }

    // Generate toString()
    @Override
    public String toString() {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy | hh:mm:ss a");

        return "Transaction{" +
                "transactionId=" + transactionId +
                ", transactionType='" + transactionType + '\'' +
                ", amount=" + amount +
                ", balanceAfterTransaction=" + balanceAfterTransaction +
                ", remarks='" + remarks + '\'' +
                ", transactionTime=" + transactionTime.format(formatter) +
                ", accountId=" + accountId +
                '}';
    }
}