package com.finverse.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FixedDeposit {

    private int fdId;
    private int userId;
    private String accountNumber;
    private BigDecimal amount;
    private double interestRate;
    private int durationMonths;
    private LocalDate startDate;
    private LocalDate maturityDate;
    private BigDecimal maturityAmount;
    private String status;

    public int getFdId() {
        return fdId;
    }

    public void setFdId(int fdId) {
        this.fdId = fdId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public int getDurationMonths() {
        return durationMonths;
    }

    public void setDurationMonths(int durationMonths) {
        this.durationMonths = durationMonths;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(LocalDate maturityDate) {
        this.maturityDate = maturityDate;
    }

    public BigDecimal getMaturityAmount() {
        return maturityAmount;
    }

    public void setMaturityAmount(BigDecimal maturityAmount) {
        this.maturityAmount = maturityAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public String toString() {

        return "FD ID : " + fdId +
                "\nAccount : ₹" + accountNumber +
                "\nAmount : ₹" + amount +
                "\nInterest : " + interestRate + "%" +
                "\nDuration : " + durationMonths + " Months" +
                "\nStart Date : " + startDate +
                "\nMaturity Date : " + maturityDate +
                "\nMaturity Amount : ₹" + maturityAmount +
                "\nStatus : " + status;
    }
}