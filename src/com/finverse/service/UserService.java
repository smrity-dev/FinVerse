package com.finverse.service;

import com.finverse.dao.UserDAO;
import com.finverse.dao.UserDAOImpl;
import com.finverse.model.Account;
import com.finverse.model.User;
import java.time.LocalDateTime;

public class UserService {

    private static UserService instance;
    private UserDAO userDAO = new UserDAOImpl();
    private static int nextUserId = 1;

    private UserService() {
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public void registerUser(User user) {

        if (userDAO.emailExists(user.getEmail())) {
            System.out.println("\nEmail already registered!");
            return;
        }
        if (userDAO.phoneExists(user.getPhoneNumber())) {
            System.out.println("Phone Number Already Registered!");
            return;
        }

        // Auto Generate User ID
        user.setUserId(nextUserId++);

        // Created & Updated Time
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        userDAO.saveUser(user);
        System.out.println("\nRegistration Successful!");
        System.out.println(user);

        AccountService accountService = AccountService.getInstance();
        Account account = accountService.createAccount(user);
        System.out.println("\nAccount Created Successfully!");
        System.out.println(account);
    }

    public User login(String email, String password) {
        return userDAO.login(email, password);
    }
    
}
