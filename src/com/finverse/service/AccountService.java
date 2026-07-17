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

    private String generateAccountNumber(int userId) {
        return "FV" + String.format("%07d", userId);
    }

    public Account createAccount(User user) {
        Account account = new Account();
        account.setAccountNumber(generateAccountNumber(user.getUserId()));
        account.setUserId(user.getUserId());
        account.setAccountType(AccountType.SAVINGS);
        account.setAccountStatus(AccountStatus.ACTIVE);
        account.setBalance(BigDecimal.ZERO);
        account.setCreatedAt(LocalDateTime.now());
        account.setUpdatedAt(LocalDateTime.now());
        boolean saved = accountDAO.saveAccount(account);
        if (!saved) {
            System.out.println("Account Creation Failed!");
            return null;
        }
        NotificationService.getInstance().addNotification(
                user.getUserId(),
                "Account Created",
                "Welcome to FinVerse. Your account number is "
                        + account.getAccountNumber()
        );
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
                account.getAccountNumber(),
                TransactionType.DEPOSIT,
                amount,
                account.getBalance(),
                "Cash Deposit"
        );
        NotificationService.getInstance().sendNotification(
                account.getUserId(),
                "₹" + amount + " deposited successfully. Current Balance : ₹"
                        + account.getBalance()
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
                account.getAccountNumber(),
                TransactionType.WITHDRAW,
                amount,
                account.getBalance(),
                "Cash Withdrawal"
        );
        NotificationService.getInstance().sendNotification(
                account.getUserId(),
                "₹" + amount + " withdrawn successfully. Current Balance : ₹"
                        + account.getBalance()
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
                sender.getAccountNumber(),
                TransactionType.TRANSFER,
                amount,
                sender.getBalance(),
                "Transfer to " + receiver.getAccountNumber()
        );
        NotificationService.getInstance().sendNotification(
                sender.getUserId(),
                "₹" + amount +
                        " transferred to " +
                        receiver.getAccountNumber()
        );
        TransactionService.getInstance().saveTransaction(
                receiver.getAccountNumber(),
                TransactionType.DEPOSIT,
                amount,
                receiver.getBalance(),
                "Received from " + sender.getAccountNumber()
        );
        NotificationService.getInstance().sendNotification(
                receiver.getUserId(),
                "₹" + amount +
                        " received from " +
                        sender.getAccountNumber()
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
        NotificationService.getInstance().addNotification(
                account.getUserId(),
                "Account Type Changed",
                "Your account type is now "
                        + account.getAccountType()
        );
        return true;
    }

    public void blockAccount(Account account) {
        account.setAccountStatus(AccountStatus.BLOCKED);
        account.setUpdatedAt(LocalDateTime.now());
        accountDAO.blockAccount(account);
        NotificationService.getInstance().addNotification(
                account.getUserId(),
                "Account Blocked",
                "Your account has been blocked by the bank."
        );
    }

    public void activateAccount(Account account) {
        account.setAccountStatus(AccountStatus.ACTIVE);
        account.setUpdatedAt(LocalDateTime.now());
        accountDAO.activateAccount(account);
        NotificationService.getInstance().addNotification(
                account.getUserId(),
                "Account Activated",
                "Your account has been activated."
        );
    }

    public boolean closeAccount(Account account) {
        if (account.getBalance().compareTo(BigDecimal.ZERO) != 0) {
            return false;
        }
        account.setAccountStatus(AccountStatus.CLOSED);
        account.setUpdatedAt(LocalDateTime.now());
        accountDAO.closeAccount(account);
        NotificationService.getInstance().addNotification(
                account.getUserId(),
                "Account Closed",
                "Your account has been closed successfully."
        );
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
        NotificationService.getInstance().addNotification(
                account.getUserId(),
                "Blocked Account",
                "Your account has been blocked."
        );
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
        NotificationService.getInstance().addNotification(
                account.getUserId(),
                "Account Unblocked",
                "Account Unblocked Successfully."
        );
        return true;
    }

    public void addInterest(Account account) {
        if(account.getAccountType()!=AccountType.SAVINGS){
            System.out.println("Interest available only for Savings Account.");
            return;
        }
        BigDecimal rate = new BigDecimal("0.04");
        BigDecimal interest =
                account.getBalance().multiply(rate);
        account.setBalance(
                account.getBalance().add(interest)
        );
        account.setInterestEarned(
                account.getInterestEarned().add(interest)
        );
        account.setUpdatedAt(LocalDateTime.now());
        accountDAO.updateAccount(account);
        TransactionService.getInstance().saveTransaction(
                account.getAccountNumber(),
                TransactionType.DEPOSIT,
                interest,
                account.getBalance(),
                "Annual Interest"
        );
        System.out.println("Interest Added : ₹"+interest);
    }

    public BigDecimal calculateInterest(Account account) {
        if (account.getAccountStatus() != AccountStatus.ACTIVE) {
            return BigDecimal.ZERO;
        }
        if (account.getAccountType() != AccountType.SAVINGS) {
            return BigDecimal.ZERO;
        }
        BigDecimal rate = new BigDecimal("3.5");
        BigDecimal interest =
                account.getBalance()
                        .multiply(rate)
                        .divide(new BigDecimal("100"));
        account.setBalance(account.getBalance().add(interest));
        accountDAO.updateAccount(account);
        TransactionService.getInstance().saveTransaction(
                account.getAccountNumber(),
                TransactionType.DEPOSIT,
                interest,
                account.getBalance(),
                "Savings Interest"
        );
        NotificationService.getInstance().sendNotification(
                account.getUserId(),
                "₹" + interest + " interest credited."
        );
        return interest;
    }
}