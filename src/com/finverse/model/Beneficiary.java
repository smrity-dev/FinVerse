package com.finverse.model;

import java.time.LocalDateTime;

public class Beneficiary {

    private int beneficiaryId;
    private int userId;
    private String beneficiaryName;
    private String accountNumber;
    private LocalDateTime addedAt;
    private boolean favourite;

    public Beneficiary() {
    }

    public Beneficiary(int beneficiaryId,
                       int userId,
                       String beneficiaryName,
                       String accountNumber,
                       LocalDateTime addedAt) {
        this.beneficiaryId = beneficiaryId;
        this.userId = userId;
        this.beneficiaryName = beneficiaryName;
        this.accountNumber = accountNumber;
        this.addedAt = addedAt;
    }

    public int getBeneficiaryId() {
        return beneficiaryId;
    }
    public void setBeneficiaryId(int beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }
    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }
    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }
    
    public boolean isFavourite() {
        return favourite;
    }
    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    @Override
    public String toString() {
        return "Beneficiary ID : " + beneficiaryId +
                "\nName : " + beneficiaryName +
                "\nAccount Number : " + accountNumber +
                "\nAdded At : " + addedAt;
    }
}