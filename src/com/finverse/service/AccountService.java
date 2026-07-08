package com.finverse.service;

import com.finverse.dao.AccountDAO;
import com.finverse.dao.AccountDAOImpl;
import com.finverse.model.Account;
import com.finverse.model.AccountStatus;
import com.finverse.model.AccountType;
import com.finverse.model.User;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.finverse.model.TransactionType;

public class AccountService {

    private static AccountService instance;
    private AccountDAO accountDAO = new AccountDAOImpl();
    private static int nextAccountId = 1;
    private static int nextAccountNumber = 1000000;

    private AccountService() {
    }

    public static AccountService getInstance() {
        if (instance == null) {
            instance = new AccountService();
        }
        return instance;
    }

    private String generateAccountNumber() {
        return "FV" + nextAccountNumber++;
    }

    public Account createAccount(User user) {
        Account account = new Account();
        account.setAccountId(nextAccountId++);
        account.setAccountNumber(generateAccountNumber());
        account.setUserId(user.getUserId());
        account.setAccountType(AccountType.SAVINGS);
        account.setAccountStatus(AccountStatus.ACTIVE);
        account.setBalance(BigDecimal.ZERO);
        account.setCreatedAt(LocalDateTime.now());
        account.setUpdatedAt(LocalDateTime.now());
        accountDAO.saveAccount(account);
        return account;
    }

    public Account getAccount(int userId) {
        return accountDAO.getAccountByUserId(userId);
    }

    public void deposit(Account account, BigDecimal amount) {
        account.setBalance(account.getBalance().add(amount));
        account.setUpdatedAt(LocalDateTime.now());
        accountDAO.updateAccount(account);
        TransactionService.getInstance().saveTransaction(
                account.getAccountId(),
                TransactionType.DEPOSIT,
                amount,
                account.getBalance(),
                "Cash Deposit"
        );
    }

    public boolean withdraw(Account account, BigDecimal amount) {
        if (account.getBalance().compareTo(amount) < 0) {
            return false;
        }
        account.setBalance(account.getBalance().subtract(amount));
        account.setUpdatedAt(LocalDateTime.now());
        accountDAO.updateAccount(account);
        TransactionService.getInstance().saveTransaction(
                account.getAccountId(),
                TransactionType.WITHDRAW,
                amount,
                account.getBalance(),
                "Cash Withdrawal"
        );
        return true;
    }

    public boolean transfer(Account sender, String receiverAccountNumber, BigDecimal amount) {
        Account receiver = accountDAO.getAccountByAccountNumber(receiverAccountNumber);
        if (receiver == null) {
            return false;
        }
        if (sender.getBalance().compareTo(amount) < 0) {
            return false;
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        // Debit Sender
        sender.setBalance(sender.getBalance().subtract(amount));
        sender.setUpdatedAt(LocalDateTime.now());
        // Credit Receiver
        receiver.setBalance(receiver.getBalance().add(amount));
        receiver.setUpdatedAt(LocalDateTime.now());
        accountDAO.updateAccount(sender);
        accountDAO.updateAccount(receiver);
        TransactionService.getInstance().saveTransaction(sender.getAccountId(),
                TransactionType.TRANSFER,
                amount,
                sender.getBalance(),
                "Transfer to " + receiver.getAccountNumber()
        );
        TransactionService.getInstance().saveTransaction(
                receiver.getAccountId(),
                TransactionType.DEPOSIT,
                amount,
                receiver.getBalance(),
                "Received from " + sender.getAccountNumber()
        );
        return true;
    }

    public boolean accountExists(String accountNumber) {
        return accountDAO.getAccountByAccountNumber(accountNumber) != null;
    }
}