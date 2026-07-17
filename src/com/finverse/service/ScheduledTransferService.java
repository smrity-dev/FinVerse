package com.finverse.service;

import com.finverse.dao.ScheduledTransferDAO;
import com.finverse.dao.ScheduledTransferDAOImpl;
import com.finverse.model.Account;
import com.finverse.model.ScheduledTransfer;
import com.finverse.model.User;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ScheduledTransferService {

    private static ScheduledTransferService instance;
    private ScheduledTransferDAO scheduledTransferDAO = new ScheduledTransferDAOImpl();

    private ScheduledTransferService() {
    }

    public static ScheduledTransferService getInstance() {
        if (instance == null) {
            instance = new ScheduledTransferService();
        }
        return instance;
    }

    public boolean scheduleTransfer(User user,
                                    String receiverAccount,
                                    BigDecimal amount,
                                    LocalDate transferDate) {
        Account sender = AccountService.getInstance().getAccount(user.getUserId());
        if (sender == null) {
            return false;
        }
        if (sender.getAccountNumber().equals(receiverAccount)) {
            return false;
        }
        if (!AccountService.getInstance()
                .accountExists(receiverAccount)) {
            return false;
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        if (transferDate.isBefore(LocalDate.now())) {
            return false;
        }

        ScheduledTransfer transfer = new ScheduledTransfer();
        transfer.setUserId(user.getUserId());
        transfer.setReceiverAccount(receiverAccount);
        transfer.setSenderAccount(sender.getAccountNumber());
        transfer.setAmount(amount);
        transfer.setTransferDate(transferDate);
        transfer.setCompleted(false);
        scheduledTransferDAO.save(transfer);
        NotificationService.getInstance().addNotification(
                user.getUserId(),
                "Scheduled Transfer Created",
                "₹" + amount +
                        " transfer scheduled for " +
                        transferDate + "."
        );
        return true;
    }

    public List<ScheduledTransfer> getSchedules(int userId) {
        return scheduledTransferDAO.getSchedules(userId);
    }
    public List<ScheduledTransfer> getAllSchedules() {
        return scheduledTransferDAO.getAllSchedules();
    }

    public void executeScheduledTransfers() {

        List<ScheduledTransfer> schedules =
                scheduledTransferDAO.getAllSchedules();
        LocalDate today = LocalDate.now();
        for (ScheduledTransfer schedule : schedules) {
            if (schedule.isCompleted()) {
                continue;
            }
            if (!schedule.getTransferDate().equals(today)) {
                continue;
            }
            Account sender =
                    AccountService.getInstance()
                            .getAccount(schedule.getUserId());
            Account receiver =
                    AccountService.getInstance()
                            .searchAccount(schedule.getReceiverAccount());
            if (sender == null || receiver == null) {
                continue;
            }
            boolean success =
                    AccountService.getInstance().transfer(
                            sender,
                            receiver.getAccountNumber(),
                            schedule.getAmount()
                    );
            if (success) {
                schedule.setCompleted(true);
                System.out.println();
                System.out.println("Scheduled Transfer Executed");
                System.out.println("Amount : ₹" + schedule.getAmount());
                System.out.println("To : " + receiver.getAccountNumber());
                NotificationService.getInstance().addNotification(
                        schedule.getUserId(),
                        "Scheduled Transfer Executed",
                        "₹" + schedule.getAmount() +
                                " transferred to " +
                                receiver.getAccountNumber()
                );
            }
        }
    }

    public boolean cancelSchedule(User user, int scheduleId) {
        ScheduledTransfer transfer = scheduledTransferDAO.getSchedule(scheduleId);
        if (transfer == null) {
            return false;
        }
        if (transfer.getUserId() != user.getUserId()) {
            return false;
        }
        if (transfer.isCompleted()) {
            return false;
        }
        scheduledTransferDAO.deleteSchedule(transfer);
        NotificationService.getInstance().addNotification(
                user.getUserId(),
                "Scheduled Transfer Cancelled",
                "Your scheduled transfer has been cancelled."
        );
        return true;
    }
}