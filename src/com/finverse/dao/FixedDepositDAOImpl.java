package com.finverse.dao;

import com.finverse.model.FixedDeposit;

import java.util.ArrayList;
import java.util.List;

public class FixedDepositDAOImpl implements FixedDepositDAO {

    private static final List<FixedDeposit> fdList =
            new ArrayList<>();

    @Override
    public void saveFD(FixedDeposit fd) {
        fdList.add(fd);
    }

    @Override
    public List<FixedDeposit> getFDs(int userId) {

        List<FixedDeposit> list =
                new ArrayList<>();

        for (FixedDeposit fd : fdList) {

            if (fd.getUserId() == userId) {
                list.add(fd);
            }
        }

        return list;
    }

    @Override
    public List<FixedDeposit> getAllFDs() {
        return fdList;
    }

    @Override
    public FixedDeposit getFD(int fdId) {

        for (FixedDeposit fd : fdList) {

            if (fd.getFdId() == fdId) {
                return fd;
            }
        }

        return null;
    }

    @Override
    public void closeFD(FixedDeposit fd) {
        fd.setClosed(true);
    }
}