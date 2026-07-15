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
import com.finverse.model.AccountType;

public class BankingUI {

    Scanner scanner = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("------------------------------------------------------------");
            System.out.println("                      WELCOME TO FINVERSE ");
            System.out.println("------------------------------------------------------------");
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
            System.out.println("Invalid Email Format! Please use characters,numbers,@,.");
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
                System.out.println("This Email is not Registered! Use Registered Email");
                continue;
            }
            break;
        }

        int attempts = 0;
        while (attempts < 3) {
            System.out.print("Enter Password : ");
            String password = scanner.nextLine();
            User user = userService.login(email, password);
            // Ager UserDAOImpl ke andar user null nhi hua to DashBoard khulega otherwise attempts ++ honge
            if (user != null) {
                System.out.println("\nLogin Successful!");
                userDashboard(user);
                return ;
            }
            attempts++;
            if (attempts < 3) {
                System.out.println("Incorrect Password!");
                System.out.println("Attempts Left : " + (3 - attempts));
            }
            else {
                System.out.println("Too many failed attempts! Try Again...");
                System.out.println("Returning to Main Menu...");
            }
        }
    }

    private void userDashboard(User user) {

        while (true) {
            System.out.println("------------------------------------------------------------");
            System.out.println("                Welcome ! Dear " + user.getFirstName());
            System.out.println("------------------------------------------------------------");
            System.out.println("1. View Profile");
            System.out.println("2. View Account");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. Transfer");
            System.out.println("6. Transaction History");
            System.out.println("7. Mini Statement");
            System.out.println("8. Change Password");
            System.out.println("9. Forgot Password");
            System.out.println("10. Update Profile");
            System.out.println("11. Check Balance");
            System.out.println("12. Account Summary");
            System.out.println("13. Search Account");
            System.out.println("14. Change Account Type");
            System.out.println("15. Close Account");
            System.out.println("16. Generate Pin");
            System.out.println("17. Change Pin");
            System.out.println("18. Logout");


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
                    changePassword(user);
                    break;
                case 9:
                    forgotPassword();
                    break;
                case 10:
                    updateProfile(user);
                    break;
                case 11:
                    checkBalance(user);
                    break;
                case 12:
                    accountSummary(user);
                    break;
                case 13:
                    searchAccount();
                    break;
                case 14:
                    changeAccountType(user);
                    break;
                case 15:
                    closeAccount(user);
                    break;
                case 16:
                    generatePin(user);
                    break;
                case 17:
                    changePin(user);
                    break;
                case 18:
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
        System.out.println("Last Login : "+user.getLastLogin());
        System.out.println("ATM PIN Generated : "+user.isPinGenerated());
        System.out.println("Account Locked : "+user.isAccountLocked());
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

        AccountService accountService = AccountService.getInstance();
        Account account = accountService.getAccount(user.getUserId());
        BigDecimal amount;
        while (true) {
            System.out.print("Enter Amount : ₹");
            String input = scanner.nextLine();
            try {
                amount = new BigDecimal(input);
                if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                    System.out.println("Amount must be greater than 0!");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid Amount! Please enter a valid number.");
            }
        }
        System.out.println("\n========== CONFIRM DEPOSIT ==========");
        System.out.println("Account Number : " + account.getAccountNumber());
        System.out.println("Deposit Amount : ₹" + amount);
        System.out.print("\nConfirm Deposit? (Y/N) : ");
        String choice = scanner.nextLine();
        if (!choice.equalsIgnoreCase("Y")) {
            System.out.println("Deposit Cancelled!");
            return;
        }
        accountService.deposit(account, amount);
        System.out.println("\nDeposit Successful!");
        System.out.println("Updated Balance : ₹" + account.getBalance());
    }

    private void withdraw(User user) {

        AccountService accountService = AccountService.getInstance();
        Account account = accountService.getAccount(user.getUserId());
        BigDecimal amount;
        while (true) {
            System.out.print("Enter Amount : ₹");
            String input = scanner.nextLine();
            try {
                amount = new BigDecimal(input);
                if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                    System.out.println("Amount must be greater than 0!");
                    continue;
                }
                if (account.getBalance().compareTo(amount) < 0) {
                    System.out.println("Insufficient Balance!");
                    System.out.println("Available Balance : ₹" + account.getBalance());
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid Amount! Please enter a valid number.");
            }
        }
        while (true) {
            System.out.println("\n========== CONFIRM WITHDRAW ==========");
            System.out.println("Account Number : " + account.getAccountNumber());
            System.out.println("Withdraw Amount : ₹" + amount);
            System.out.print("\nConfirm Withdrawal? (Y/N) : ");
            String choice = scanner.nextLine();

            if (!user.isPinGenerated()) {
                System.out.println("\nGenerate ATM PIN First.");
                return;
            }
            System.out.print("Enter ATM PIN : ");
            String pin = scanner.nextLine();
            boolean verified = UserService.getInstance().verifyPin(user, pin);
            if (!verified) {
                System.out.println("Incorrect ATM PIN!");
                return;
            }

            if (choice.equalsIgnoreCase("Y")) {
                boolean success = accountService.withdraw(account, amount);
                if (success) {
                    System.out.println("\nWithdrawal Successful!");
                    System.out.println("Remaining Balance : ₹" + account.getBalance());
                } else {
                    System.out.println("\nWithdrawal Failed!");
                }
                return;
            } else if (choice.equalsIgnoreCase("N")) {
                System.out.println("Withdrawal Cancelled!");
                return;
            } else {
                System.out.println("Invalid Choice! Please enter Y or N.");
            }
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
            String input = scanner.nextLine();
            try {
                amount = new BigDecimal(input);
                if(sender.getBalance().compareTo(amount)<0){
                    System.out.println("Insufficient Balance!");
                    return;
                }
                if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                    System.out.println("Amount must be greater than zero!");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid amount!");
            }
        }

        System.out.println("\n========= CONFIRM TRANSFER =========");
        System.out.println("From Account : " + sender.getAccountNumber());
        System.out.println("To Account   : " + receiverAccount);
        System.out.println("Amount       : ₹" + amount);
        System.out.print("\nConfirm Transfer? (Y/N) : ");
        String choice = scanner.nextLine();
        if (!choice.equalsIgnoreCase("Y")) {
            System.out.println("Transfer Cancelled! Not enough amount to transfer");
            return;
        }

        if (!user.isPinGenerated()) {
            System.out.println("Generate ATM PIN First.");
            return;
        }
        System.out.print("Enter ATM PIN : ");
        String pin = scanner.nextLine();
        if (!UserService.getInstance().verifyPin(user, pin)) {
            System.out.println("Incorrect ATM PIN!");
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

    private void changePassword(User user) {

        UserService userService = UserService.getInstance();
        while (true) {
            System.out.print("Enter Current Password : ");
            String currentPassword = scanner.nextLine();
            String newPassword;
            while (true) {
                System.out.print("Enter New Password : ");
                newPassword = scanner.nextLine();
                if(currentPassword.equals(newPassword)){
                    System.out.println("New Password cannot be same as Current Password.");
                    continue;
                }
                if (UserValidation.isValidPassword(newPassword)) {
                    break;
                }
                System.out.println("Password must contain uppercase, lowercase, digit, special character and minimum 8 characters.");
            }
            System.out.print("Confirm New Password : ");
            String confirmPassword = scanner.nextLine();
            if (!newPassword.equals(confirmPassword)) {
                System.out.println("Passwords do not match!");
                continue;
            }
            boolean success = userService.changePassword(user, currentPassword, newPassword);
            if (success) {
                System.out.println("\nPassword Changed Successfully!");
                return;
            }
            System.out.println("Current Password is Incorrect!");
        }
    }

    private void forgotPassword() {

        UserService userService = UserService.getInstance();
        User user;
        while (true) {
            System.out.print("Enter Registered Email : ");
            String email = scanner.nextLine();
            if (!UserValidation.isValidEmail(email)) {
                System.out.println("Invalid Email Format!");
                continue;
            }
            user = userService.getUserByEmail(email);
            if (user == null) {
                System.out.println("Email Not Registered!");
                continue;
            }
            break;
        }
        while (true) {
            System.out.print("Enter Registered Phone Number : ");
            String phone = scanner.nextLine();
            if (!phone.equals(user.getPhoneNumber())) {
                System.out.println("Phone Number does not match!");
                continue;
            }
            break;
        }
        String newPassword;
        while (true) {
            System.out.print("Enter New Password : ");
            newPassword = scanner.nextLine();
            if (UserValidation.isValidPassword(newPassword)) {
                break;
            }
            System.out.println("Invalid Password Format!");
        }
        while (true) {
            System.out.print("Confirm New Password : ");
            String confirmPassword = scanner.nextLine();
            if (!newPassword.equals(confirmPassword)) {
                System.out.println("Passwords do not match!");
                continue;
            }
            break;
        }
        userService.resetPassword(user, newPassword);
        System.out.println("\nPassword Reset Successfully!");
    }

    private void updateProfile(User user) {
        UserService userService = UserService.getInstance();
        System.out.println("\n========== UPDATE PROFILE ==========");
        System.out.print("First Name : ");
        String firstName = scanner.nextLine();
        System.out.print("Last Name : ");
        String lastName = scanner.nextLine();
        String email;
        while (true) {
            System.out.print("Email : ");
            email = scanner.nextLine();
            if (!UserValidation.isValidEmail(email)) {
                System.out.println("Invalid Email!");
                continue;
            }
            break;
        }
        String phone;
        while (true) {
            System.out.print("Phone Number : ");
            phone = scanner.nextLine();
            if (!UserValidation.isValidPhone(phone)) {
                System.out.println("Invalid Phone Number!");
                continue;
            }
            break;
        }
        boolean success = userService.updateProfile(
                user,
                firstName,
                lastName,
                email,
                phone
        );
        if(success){
            System.out.println("\nProfile Updated Successfully!");
        }
        else{
            System.out.println("\nEmail or Phone Already Exists!");
        }
    }

    private void checkBalance(User user) {

        BigDecimal balance = AccountService
                .getInstance()
                .checkBalance(user.getUserId());

        System.out.println("\n========== BALANCE ==========");
        System.out.println("Available Balance : ₹" + balance);
    }

    private void accountSummary(User user) {
        Account account = AccountService
                .getInstance()
                .getAccount(user.getUserId());
        AccountService
                .getInstance()
                .printAccountSummary(account);
    }

    private void searchAccount() {
        System.out.print("\nEnter Account Number : ");
        String accountNumber = scanner.nextLine();
        Account account =
                AccountService.getInstance().searchAccount(accountNumber);
        if(account==null){
            System.out.println("Account Not Found!");
            return;
        }
        System.out.println("\n========== ACCOUNT ==========");
        System.out.println(account);
    }

    private void changeAccountType(User user){
        Account account= AccountService.getInstance().getAccount(user.getUserId());
        System.out.println();
        System.out.println("1. SAVINGS");
        System.out.println("2. CURRENT");
        System.out.println("3. SALARY");
        System.out.print("Choose : ");
        int choice=scanner.nextInt();
        scanner.nextLine();
        AccountType accountType;
        switch(choice){
            case 1:
                accountType=AccountType.SAVINGS;
                break;
            case 2:
                accountType=AccountType.CURRENT;
                break;
            case 3:
                accountType=AccountType.SALARY;
                break;
            default:
                System.out.println("Invalid Choice!");
                return;
        }
        boolean success= AccountService.getInstance().changeAccountType(account,accountType);
        if(success){
            System.out.println("Account Type Updated Successfully.");
        }
        else{
            System.out.println("Account is not Active.");
        }
    }

    private void closeAccount(User user){
        Account account= AccountService.getInstance().getAccount(user.getUserId());
        if(account.getBalance().compareTo(BigDecimal.ZERO)>0){
            System.out.println();
            System.out.println("Withdraw remaining balance before closing account.");
            return;
        }
        System.out.print("Are you sure? (Y/N) : ");
        String choice=scanner.nextLine();
        if(!choice.equalsIgnoreCase("Y")){
            System.out.println("Account Closing Cancelled.");
            return;
        }

        if (!user.isPinGenerated()) {
            System.out.println("Generate ATM PIN First.");
            return;
        }
        System.out.print("Enter ATM PIN : ");
        String pin = scanner.nextLine();
        if (!UserService.getInstance().verifyPin(user, pin)) {
            System.out.println("Incorrect ATM PIN!");
            return;
        }

        boolean success= AccountService.getInstance().closeAccount(account);
        if(success){
            System.out.println("Account Closed Successfully.");
        }
        else{
            System.out.println("Unable to Close Account.");
        }
    }

    private void generatePin(User user) {
        if (user.isPinGenerated()) {
            System.out.println("ATM PIN already generated.");
            return;
        }
        while (true) {
            System.out.print("Enter 4 Digit PIN : ");
            String pin = scanner.nextLine();
            if (!UserValidation.isValidPin(pin)) {
                System.out.println("Invalid PIN!");
                continue;
            }
            boolean success =
                    UserService.getInstance()
                            .generatePin(user, pin);
            if (success) {
                System.out.println("ATM PIN Generated Successfully.");
            }
            return;
        }
    }

    private void changePin(User user) {
        if (!user.isPinGenerated()) {
            System.out.println("Generate ATM PIN First.");
            return;
        }
        System.out.print("Old PIN : ");
        String oldPin = scanner.nextLine();
        System.out.print("New PIN : ");
        String newPin = scanner.nextLine();
        if(oldPin.equals(newPin)){
            System.out.println("New PIN cannot be same as Old PIN.");
            return;
        }
        if (!UserValidation.isValidPin(newPin)) {
            System.out.println("PIN must contain exactly 4 digits.");
            return;
        }
        boolean success = UserService.getInstance().changePin(user, oldPin, newPin);
        if (success) {
            System.out.println("PIN Changed Successfully.");
        } else {
            System.out.println("Old PIN Incorrect.");
        }
    }
}