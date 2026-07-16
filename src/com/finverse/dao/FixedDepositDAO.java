package com.finverse.dao;

import com.finverse.model.FixedDeposit;

import java.util.List;

public interface FixedDepositDAO {

    void saveFD(FixedDeposit fd);

    List<FixedDeposit> getFDs(int userId);

    List<FixedDeposit> getAllFDs();

    FixedDeposit getFD(int fdId);

    void closeFD(FixedDeposit fd);

}