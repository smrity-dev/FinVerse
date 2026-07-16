package com.finverse.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ScheduledTransfer {

    private int scheduleId;
    private int userId;
    private String receiverAccount;
    private BigDecimal amount;
    private LocalDate transferDate;
    private boolean completed;

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getReceiverAccount() {
        return receiverAccount;
    }

    public void setReceiverAccount(String receiverAccount) {
        this.receiverAccount = receiverAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(LocalDate transferDate) {
        this.transferDate = transferDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {

        return "Schedule ID : " + scheduleId +
                "\nReceiver : " + receiverAccount +
                "\nAmount : ₹" + amount +
                "\nDate : " + transferDate +
                "\nCompleted : " + completed;
    }
}