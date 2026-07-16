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
    private String accountNumber;
    private String referenceNumber;
    private TransactionStatus transactionStatus;

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

    public String getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    // Generate Constructor
    public Transaction(int transactionId, TransactionType transactionType, BigDecimal amount, BigDecimal balanceAfterTransaction, String remarks, LocalDateTime transactionTime, String accountNumber) {
        this.transactionId = transactionId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.balanceAfterTransaction = balanceAfterTransaction;
        this.remarks = remarks;
        this.transactionTime = transactionTime;
        this.accountNumber = accountNumber;
    }

    // Generate toString()
    @Override
    public String toString() {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy | hh:mm:ss a");

        return "Transaction{" +
                "\ntransactionId = " + transactionId +
                "\nReference No = " + referenceNumber +
                "\nStatus = " + transactionStatus +
                "\ntransactionType = '" + transactionType + '\'' +
                "\namount = " + amount +
                ", balanceAfterTransaction = " + balanceAfterTransaction +
                ", remarks = '" + remarks + '\'' +
                ", transactionTime = " + transactionTime.format(formatter) +
                ", accountNumber = " + accountNumber +
                '}';
    }
}