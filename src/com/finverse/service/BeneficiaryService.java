package com.finverse.service;

import com.finverse.dao.BeneficiaryDAO;
import com.finverse.dao.BeneficiaryDAOImpl;
import com.finverse.model.Account;
import com.finverse.model.Beneficiary;
import com.finverse.model.User;
import java.time.LocalDateTime;
import java.util.List;

public class BeneficiaryService {

    private static BeneficiaryService instance;
    private BeneficiaryDAO beneficiaryDAO = new BeneficiaryDAOImpl();

    private BeneficiaryService() {
    }

    public static BeneficiaryService getInstance() {
        if (instance == null) {
            instance = new BeneficiaryService();
        }
        return instance;
    }

    public boolean addBeneficiary(User user,
                                  String accountNumber,
                                  String beneficiaryName) {
        Account account =
                AccountService.getInstance()
                        .searchAccount(accountNumber);
        if(account == null){
            return false;
        }
        Account myAccount =
                AccountService.getInstance()
                        .getAccount(user.getUserId());
        if(myAccount.getAccountNumber().equals(accountNumber)){
            System.out.println("You cannot add your own account.");
            return false;
        }
        if(beneficiaryDAO.getBeneficiary(
                user.getUserId(),
                accountNumber)!=null){
            System.out.println("Beneficiary Already Exists.");
            return false;
        }
        Beneficiary beneficiary = new Beneficiary();
        beneficiary.setUserId(user.getUserId());
        beneficiary.setBeneficiaryName(beneficiaryName);
        beneficiary.setAccountNumber(accountNumber);
        beneficiary.setAddedAt(LocalDateTime.now());
        beneficiaryDAO.saveBeneficiary(beneficiary);
        NotificationService.getInstance().addNotification(
                user.getUserId(),
                "Beneficiary Added",
                beneficiaryName + " added successfully."
        );
        return true;
    }

    public List<Beneficiary> getBeneficiaries(int userId) {
        return beneficiaryDAO.getBeneficiaries(userId);
    }
    public boolean deleteBeneficiary(User user,
                                     String accountNumber) {
        Beneficiary beneficiary =
                beneficiaryDAO.getBeneficiary(
                        user.getUserId(),
                        accountNumber
                );
        if (beneficiary == null) {
            return false;
        }
        beneficiaryDAO.deleteBeneficiary(beneficiary);
        NotificationService.getInstance().addNotification(
                user.getUserId(),
                "Beneficiary Removed",
                beneficiary.getBeneficiaryName()
                        + " removed successfully."
        );
        return true;
    }

    public boolean isBeneficiary(User user, String accountNumber) {
        return beneficiaryDAO.getBeneficiary(
                user.getUserId(),
                accountNumber
        ) != null;
    }

    public boolean markFavourite(User user,String accountNumber){
        Beneficiary beneficiary =
                beneficiaryDAO.getBeneficiary(
                        user.getUserId(),
                        accountNumber);
        if(beneficiary==null){
            return false;
        }
        beneficiaryDAO.markFavourite(beneficiary);
        return true;
    }

    public Beneficiary searchBeneficiary(User user,String accountNumber){
        return beneficiaryDAO.searchBeneficiary(
                user.getUserId(),
                accountNumber);
    }

}