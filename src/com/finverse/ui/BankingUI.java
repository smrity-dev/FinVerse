package com.finverse.ui;

import java.util.Scanner;
//User se inputs lega unki information ke lie
import com.finverse.dao.UserDAO;
import com.finverse.model.User;
//Email,phone,password verification ke lie
import com.finverse.validation.UserValidation;

import com.finverse.service.AccountService;
import com.finverse.service.UserService;
import com.finverse.model.Account;
import java.math.BigDecimal;
import java.util.List;
import com.finverse.model.Transaction;
import com.finverse.service.TransactionService;

public class BankingUI {

    Scanner scanner = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("==================================");
            System.out.println("      WELCOME TO FINVERSE ");
            System.out.println("==================================");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose Option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    loginUser();
                    break;
                case 3:
                    System.out.println("Thank you for using FinVerse.");
                    return;
                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }

    public void registerUser() {

        System.out.println("\n===== User Registration =====");
        // Object of User class to get the information of their
        User user = new User();
        System.out.print("First Name: ");
        user.setFirstName(scanner.nextLine());
        System.out.print("Last Name: ");
        user.setLastName(scanner.nextLine());

        UserService userService = UserService.getInstance();

        String email;
        while (true) {
            System.out.print("Enter Email : ");
            email = scanner.nextLine();

            if (userService.emailExists(email)) {
                System.out.println("Email already exists!");
                continue;
            }
            if (UserValidation.isValidEmail(email)) {
                user.setEmail(email);
                break;
            }
            System.out.println("Invalid Email Format!");
        }

        String phone;
        while (true) {
            System.out.print("Enter Phone Number : ");
            phone = scanner.nextLine();
            if (userService.phoneExists(phone)) {
                System.out.println("Phone already registered!");
                continue;
            }
            if (UserValidation.isValidPhone(phone)) {
                user.setPhoneNumber(phone);
                break;
            }
            System.out.println("Invalid Phone Number! Please Enter 10 digit's valid Phone number");
        }
        
        String password;
        while (true) {
            System.out.print("Create Password : ");
            password = scanner.nextLine();
            if (UserValidation.isValidPassword(password)) {
                user.setPassword(password);
                break;
            }
            System.out.println("Password must contain at least a uppercase , a lowercase, a digit , a special character and 8 characters or more ");
        }
        userService.registerUser(user);
    }

    public void loginUser() {

        UserService userService = UserService.getInstance();

        String email;

        while (true) {

            System.out.print("Enter Email : ");
            email = scanner.nextLine();

            if (!UserValidation.isValidEmail(email)) {
                System.out.println("Invalid Email Format!");
                continue;
            }

            if (!userService.emailExists(email)) {
                System.out.println("This Email is not Registered!");
                continue;
            }

            break;
        }

        int attempts = 0;
        while (attempts < 3) {
            System.out.print("Enter Password : ");
            String password = scanner.nextLine();
            User user = userService.login(email, password);
            if (user != null) {
                System.out.println("\nLogin Successful!");
                userDashboard(user);
                return;
            }
            attempts++;
            if (attempts < 3) {
                System.out.println("Incorrect Password!");
                System.out.println("Attempts Left : " + (3 - attempts));
            } else {
                System.out.println("Too many failed attempts!");
                System.out.println("Returning to Main Menu...");
            }
        }
    }

    private void userDashboard(User user) {

        while (true) {
            System.out.println("\n==============================");
            System.out.println(" Welcome " + user.getFirstName());
            System.out.println("==============================");
            System.out.println("1. View Profile");
            System.out.println("2. View Account");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. Transfer");
            System.out.println("6. Transaction History");
            System.out.println("7. Mini Statement");
            System.out.println("8. Logout");
            System.out.print("Choose Option : ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    viewProfile(user);
                    break;
                case 2:
                    viewAccount(user);
                    break;
                case 3:
                    deposit(user);
                    break;
                case 4:
                    withdraw(user);
                    break;
                case 5:
                    transfer(user);
                    break;
                case 6:
                    showTransactions(user);
                    break;
                case 7:
                    miniStatement(user);
                    break;
                case 8:
                    System.out.println("Logout Successful!");
                    return;
                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }

    private void viewProfile(User user) {
        System.out.println("\n========== USER PROFILE ==========");
        System.out.println("User ID       : " + user.getUserId());
        System.out.println("First Name    : " + user.getFirstName());
        System.out.println("Last Name     : " + user.getLastName());
        System.out.println("Email         : " + user.getEmail());
        System.out.println("Phone Number  : " + user.getPhoneNumber());
        System.out.println("Created At    : " + user.getCreatedAt());
    }

    private void viewAccount(User user) {
        Account account = AccountService.getInstance().getAccount(user.getUserId());
        System.out.println("\n========== ACCOUNT DETAILS ==========");
        System.out.println("Account ID      : " + account.getAccountId());
        System.out.println("Account Number  : " + account.getAccountNumber());
        System.out.println("Account Type    : " + account.getAccountType());
        System.out.println("Status          : " + account.getAccountStatus());
        System.out.println("Balance         : ₹" + account.getBalance());
        System.out.println("Created At      : " + account.getCreatedAt());
    }

    private void deposit(User user) {
        Account account = AccountService.getInstance().getAccount(user.getUserId());
        System.out.print("Enter Amount : ");
        BigDecimal amount = scanner.nextBigDecimal();
        scanner.nextLine();
        AccountService.getInstance().deposit(account, amount);
        System.out.println("\nDeposit Successful!");
        System.out.println("Updated Balance : ₹" + account.getBalance());
    }

    private void withdraw(User user) {
        Account account = AccountService.getInstance().getAccount(user.getUserId());
        System.out.print("Enter Amount : ");
        BigDecimal amount = scanner.nextBigDecimal();
        scanner.nextLine();
        boolean success = AccountService.getInstance().withdraw(account, amount);
        if (success) {
            System.out.println("\nWithdrawal Successful!");
            System.out.println("Remaining Balance : ₹" + account.getBalance());
        } else {
            System.out.println("\nInsufficient Balance!");
        }
    }

    private void transfer(User user) {
        AccountService accountService = AccountService.getInstance();
        Account sender = accountService.getAccount(user.getUserId());
        String receiverAccount;
        while (true) {
            System.out.print("Receiver Account Number : ");
            receiverAccount = scanner.nextLine().trim();
            if (receiverAccount.isEmpty()) {
                System.out.println("Account Number cannot be empty!");
                continue;
            }
            if (receiverAccount.equals(sender.getAccountNumber())) {
                System.out.println("You cannot transfer money to your own account!");
                continue;
            }
            if (!accountService.accountExists(receiverAccount)) {
                System.out.println("Account Number not found!");
                continue;
            }
            break;
        }
        BigDecimal amount;
        while (true) {
            System.out.print("Amount : ₹");
            amount = scanner.nextBigDecimal();
            scanner.nextLine();
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                System.out.println("Amount must be greater than zero!");
                continue;
            }
            if (sender.getBalance().compareTo(amount) < 0) {
                System.out.println("Insufficient Balance!");
                continue;
            }
            break;
        }
        System.out.println("\n========= CONFIRM TRANSFER =========");
        System.out.println("From Account : " + sender.getAccountNumber());
        System.out.println("To Account   : " + receiverAccount);
        System.out.println("Amount       : ₹" + amount);
        System.out.print("\nConfirm Transfer? (Y/N) : ");
        String choice = scanner.nextLine();
        if (!choice.equalsIgnoreCase("Y")) {
            System.out.println("Transfer Cancelled!");
            return;
        }
        boolean success = accountService.transfer(sender, receiverAccount, amount);
        if (success) {
            System.out.println("\nTransfer Successful!");
            System.out.println("Available Balance : ₹" + sender.getBalance());
        } else {
            System.out.println("\nTransfer Failed!");
        }
    }

    private void showTransactions(User user) {
        Account account = AccountService.getInstance().getAccount(user.getUserId());
        List<Transaction> transactions = TransactionService.getInstance()
                .getTransactions(account.getAccountId());
        System.out.println("\n========= TRANSACTION HISTORY =========");
        if (transactions.isEmpty()) {
            System.out.println("No Transactions Found.");
            return;
        }
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }

    private void miniStatement(User user) {
        Account account = AccountService.getInstance().getAccount(user.getUserId());
        List<Transaction> transactions = TransactionService.getInstance()
                        .getMiniStatement(account.getAccountId());
        System.out.println("\n========== MINI STATEMENT ==========");
        if (transactions.isEmpty()) {
            System.out.println("No Transactions Found.");
            return;
        }
        for (Transaction transaction : transactions) {
            System.out.println("--------------------------------");
            System.out.println("Time : " + transaction.getTransactionTime());
            System.out.println("Type : " + transaction.getTransactionType());
            System.out.println("Amount : ₹" + transaction.getAmount());
            System.out.println("Balance : ₹" + transaction.getBalanceAfterTransaction());
        }
    }
}