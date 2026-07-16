package com.finverse.dao;

import com.finverse.model.Beneficiary;
import java.util.ArrayList;
import java.util.List;

public class BeneficiaryDAOImpl implements BeneficiaryDAO{

    private static final List<Beneficiary> beneficiaries=new ArrayList<>();

    @Override
    public void saveBeneficiary(Beneficiary beneficiary) {
        beneficiaries.add(beneficiary);
    }

    @Override
    public List<Beneficiary> getBeneficiaries(int userId) {
        List<Beneficiary> list=new ArrayList<>();
        for(Beneficiary b:beneficiaries){
            if(b.getUserId()==userId){
                list.add(b);
            }
        }
        return list;
    }

    @Override
    public Beneficiary getBeneficiary(int userId,String accountNumber) {
        for(Beneficiary b:beneficiaries){
            if(b.getUserId()==userId &&
                    b.getAccountNumber().equals(accountNumber)){
                return b;
            }
        }
        return null;
    }

    @Override
    public void deleteBeneficiary(Beneficiary beneficiary) {
        beneficiaries.remove(beneficiary);
    }

    @Override
    public void markFavourite(Beneficiary beneficiary) {
        beneficiary.setFavourite(true);
    }

    @Override
    public Beneficiary searchBeneficiary(int userId,String accountNumber){
        for(Beneficiary beneficiary:beneficiaries){
            if(beneficiary.getUserId()==userId
                    && beneficiary.getAccountNumber().equals(accountNumber))
                return beneficiary;
        }
        return null;
    }
}