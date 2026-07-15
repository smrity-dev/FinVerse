package com.finverse.dao;

import com.finverse.model.Beneficiary;
import java.util.List;

public interface BeneficiaryDAO {

    void saveBeneficiary(Beneficiary beneficiary);
    List<Beneficiary> getBeneficiaries(int userId);
    Beneficiary getBeneficiary(int userId,String accountNumber);
    void deleteBeneficiary(Beneficiary beneficiary);
    
}