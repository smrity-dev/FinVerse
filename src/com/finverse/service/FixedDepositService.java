package com.finverse.service;

import com.finverse.dao.FixedDepositDAO;
import com.finverse.dao.FixedDepositDAOImpl;
import com.finverse.model.Account;
import com.finverse.model.FixedDeposit;
import com.finverse.model.User;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class FixedDepositService {

    private static FixedDepositService instance;

    private FixedDepositDAO fixedDepositDAO =
            new FixedDepositDAOImpl();

    private static int nextFDId = 1;

    private FixedDepositService() {
    }

    public static FixedDepositService getInstance() {

        if (instance == null) {
            instance = new FixedDepositService();
        }

        return instance;
    }

    public boolean createFD(User user,
                            BigDecimal amount,
                            int months) {
        Account account =
                AccountService.getInstance()
                        .getAccount(user.getUserId());
        if (account == null) {
            return false;
        }
        // Minimum FD
        if (amount.compareTo(new BigDecimal("5000")) < 0) {
            return false;
        }
        // Balance Check
        if (account.getBalance().compareTo(amount) < 0) {
            return false;
        }
        double interestRate =
                getInterestRate(months);

        BigDecimal maturityAmount = calculateMaturityAmount(
                        amount,
                        interestRate,
                        months
                );
        FixedDeposit fd = new FixedDeposit();
        fd.setFdId(nextFDId++);
        fd.setUserId(user.getUserId());
        fd.setAmount(amount);
        fd.setInterestRate(interestRate);
        fd.setDurationMonths(months);

        LocalDate today = LocalDate.now();

        fd.setStartDate(today);
        fd.setMaturityDate(today.plusMonths(months));
        fd.setMaturityAmount(maturityAmount);
        fd.setClosed(false);

        // Deduct money from account
        account.setBalance(
                account.getBalance().subtract(amount)
        );
        fixedDepositDAO.saveFD(fd);
        NotificationService.getInstance().sendNotification(
                user.getUserId(),
                "Fixed Deposit created successfully."
        );
        return true;
    }

    public List<FixedDeposit> getFDs(int userId) {
        return fixedDepositDAO.getFDs(userId);
    }

    public List<FixedDeposit> getAllFDs() {
        return fixedDepositDAO.getAllFDs();
    }

    public FixedDeposit getFD(int fdId) {
        return fixedDepositDAO.getFD(fdId);
    }

    private double getInterestRate(int months) {
        if (months == 12)
            return 6.5;
        if (months == 24)
            return 7.0;
        if (months == 36)
            return 7.5;
        return 6.0;
    }

    private BigDecimal calculateMaturityAmount(
            BigDecimal amount,
            double rate,
            int months) {
        BigDecimal interest =
                amount.multiply(
                                BigDecimal.valueOf(rate))
                        .multiply(
                                BigDecimal.valueOf(months))
                        .divide(
                                BigDecimal.valueOf(1200), 2,
                                RoundingMode.HALF_UP);
        return amount.add(interest);
    }


    public boolean closeFD(User user, int fdId) {

        FixedDeposit fd = fixedDepositDAO.getFD(fdId);
        if (fd == null) {
            return false;
        }
        if (fd.getUserId() != user.getUserId()) {
            return false;
        }
        if (fd.isClosed()) {
            return false;
        }
        Account account =
                AccountService.getInstance()
                        .getAccount(user.getUserId());
        account.setBalance(
                account.getBalance().add(fd.getMaturityAmount())
        );
        fd.setClosed(true);
        fixedDepositDAO.closeFD(fd);
        NotificationService.getInstance().addNotification(
                user.getUserId(),
                "Fixed Deposit Closed",
                "Your Fixed Deposit has been closed successfully."
        );
        return true;
    }

}