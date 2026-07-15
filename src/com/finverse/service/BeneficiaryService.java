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

    private BeneficiaryDAO beneficiaryDAO =
            new BeneficiaryDAOImpl();

    private static int nextBeneficiaryId = 1;

    private BeneficiaryService() {

    }

    public static BeneficiaryService getInstance() {

        if(instance==null){
            instance=new BeneficiaryService();
        }

        return instance;
    }

    public boolean addBeneficiary(User user,
                                  String beneficiaryName,
                                  String accountNumber){

        Account account =
                AccountService.getInstance()
                        .getAccount(user.getUserId());

        // Cannot add own account
        if(account.getAccountNumber().equals(accountNumber)){
            return false;
        }

        // Account must exist
        if(!AccountService.getInstance().accountExists(accountNumber)){
            return false;
        }

        // Duplicate check
        if(beneficiaryDAO.getBeneficiary(
                user.getUserId(),
                accountNumber)!=null){

            return false;
        }

        // Maximum 10 beneficiaries
        if(beneficiaryDAO
                .getBeneficiaries(user.getUserId())
                .size()>=10){

            return false;
        }

        Beneficiary beneficiary = new Beneficiary();

        beneficiary.setBeneficiaryId(nextBeneficiaryId++);
        beneficiary.setUserId(user.getUserId());
        beneficiary.setBeneficiaryName(beneficiaryName);
        beneficiary.setAccountNumber(accountNumber);
        beneficiary.setAddedAt(LocalDateTime.now());

        beneficiaryDAO.saveBeneficiary(beneficiary);

        return true;
    }

    public List<Beneficiary> getBeneficiaries(int userId){

        return beneficiaryDAO.getBeneficiaries(userId);

    }

    public boolean removeBeneficiary(User user,
                                     String accountNumber){

        Beneficiary beneficiary =
                beneficiaryDAO.getBeneficiary(
                        user.getUserId(),
                        accountNumber);

        if(beneficiary==null){
            return false;
        }

        beneficiaryDAO.deleteBeneficiary(beneficiary);

        return true;

    }

    public boolean isBeneficiary(int userId,
                                 String accountNumber){

        return beneficiaryDAO.getBeneficiary(
                userId,
                accountNumber)!=null;

    }

}