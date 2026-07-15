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
import java.util.List;

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
        if (account.getAccountStatus() != AccountStatus.ACTIVE) {
            System.out.println("Account is not Active.");
            return;
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            return;
        }
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
        if (account.getAccountStatus() != AccountStatus.ACTIVE) {
            return false;
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
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
        if (sender.getAccountStatus() != AccountStatus.ACTIVE) {
            return false;
        }
        Account receiver = accountDAO.getAccountByAccountNumber(receiverAccountNumber);
        if (receiver == null) {
            return false;
        }
        if (receiver.getAccountStatus() != AccountStatus.ACTIVE) {
            return false;
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        if (sender.getBalance().compareTo(amount) < 0) {
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
        TransactionService.getInstance().saveTransaction(
                sender.getAccountId(),
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

    public BigDecimal checkBalance(int userId) {
        Account account = accountDAO.getAccountByUserId(userId);
        if (account == null) {
            return BigDecimal.ZERO;
        }
        return account.getBalance();
    }

    public void printAccountSummary(Account account) {
        System.out.println("\n========== ACCOUNT SUMMARY ==========");
        System.out.println("Account Number : " + account.getAccountNumber());
        System.out.println("Account Type   : " + account.getAccountType());
        System.out.println("Status         : " + account.getAccountStatus());
        System.out.println("Balance        : ₹" + account.getBalance());
        System.out.println("Created At     : " + account.getCreatedAt());
        System.out.println("Updated At     : " + account.getUpdatedAt());
    }

    public Account searchAccount(String accountNumber) {
        return accountDAO.getAccountByAccountNumber(accountNumber);
    }

    public boolean changeAccountType(Account account, AccountType accountType) {
        if (account.getAccountStatus() != AccountStatus.ACTIVE) {
            return false;
        }
        account.setAccountType(accountType);
        account.setUpdatedAt(LocalDateTime.now());
        accountDAO.changeAccountType(account, accountType);
        return true;
    }

    public void blockAccount(Account account) {
        account.setAccountStatus(AccountStatus.BLOCKED);
        account.setUpdatedAt(LocalDateTime.now());
        accountDAO.blockAccount(account);
    }

    public void activateAccount(Account account) {
        account.setAccountStatus(AccountStatus.ACTIVE);
        account.setUpdatedAt(LocalDateTime.now());
        accountDAO.activateAccount(account);
    }

    public boolean closeAccount(Account account) {
        if (account.getBalance().compareTo(BigDecimal.ZERO) != 0) {
            return false;
        }
        account.setAccountStatus(AccountStatus.CLOSED);
        account.setUpdatedAt(LocalDateTime.now());
        accountDAO.closeAccount(account);
        return true;
    }

    public void deleteAccount(Account account) {
        accountDAO.deleteAccount(account);
    }

    public BigDecimal getTotalBankBalance() {
        return accountDAO.getTotalBalance();
    }

    public int getTotalAccounts() {
        return accountDAO.getTotalAccounts();
    }

    public int getActiveAccounts() {
        return accountDAO.getActiveAccounts();
    }

    public int getClosedAccounts() {
        return accountDAO.getClosedAccounts();
    }

    public List<Account> getAllAccounts(){
        return accountDAO.getAllAccounts();
    }

    public boolean blockAccount(int userId) {
        Account account = accountDAO.getAccountByUserId(userId);
        if (account == null) {
            return false;
        }
        account.setAccountStatus(AccountStatus.BLOCKED);
        account.setUpdatedAt(LocalDateTime.now());
        accountDAO.blockAccount(account);
        return true;
    }

    public boolean unblockAccount(int userId) {
        Account account = accountDAO.getAccountByUserId(userId);
        if (account == null) {
            return false;
        }
        account.setAccountStatus(AccountStatus.ACTIVE);
        account.setUpdatedAt(LocalDateTime.now());
        accountDAO.activateAccount(account);
        return true;
    }

}