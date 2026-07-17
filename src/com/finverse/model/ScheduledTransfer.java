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
    private String senderAccount;

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

    public String getSenderAccount() { return senderAccount; }

    public void setSenderAccount(String senderAccount) { this.senderAccount = senderAccount; }

    @Override
    public String toString() {

        return "Schedule ID : " + scheduleId +
                "\nReceiver : " + receiverAccount +
                "\nSender : " + senderAccount +
                "\nAmount : ₹" + amount +
                "\nDate : " + transferDate +
                "\nCompleted : " + completed;
    }
}