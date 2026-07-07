package com.finverse.ui;

import java.util.Scanner;
import com.finverse.model.User;
import com.finverse.service.UserService;
import com.finverse.validation.UserValidation;

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
        User user = new User();
        System.out.print("First Name: ");
        user.setFirstName(scanner.nextLine());
        System.out.print("Last Name: ");
        user.setLastName(scanner.nextLine());

        String email;
        while (true) {
            System.out.print("Enter Email : ");
            email = scanner.nextLine();
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
            if (UserValidation.isValidPhone(phone)) {
                user.setPhoneNumber(phone);
                break;
            }
            System.out.println("Invalid Phone Number!");
        }
        
        String password;
        while (true) {
            System.out.print("Create Password : ");
            password = scanner.nextLine();
            if (UserValidation.isValidPassword(password)) {
                user.setPassword(password);
                break;
            }
            System.out.println("Password must contain at least 8 characters.");
        }

        UserService userService = UserService.getInstance();
        userService.registerUser(user);
    }

    public void loginUser() {
        UserService userService = UserService.getInstance();
        System.out.print("Email : ");
        String email = scanner.nextLine();
        System.out.print("Password : ");
        String password = scanner.nextLine();
        User user = userService.login(email, password);
        if (user != null) {
            System.out.println("\nWelcome " + user.getFirstName());
        } else {
            System.out.println("\nInvalid Email or Password");
        }

    }
}