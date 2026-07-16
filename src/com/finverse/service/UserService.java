package com.finverse.service;

import com.finverse.dao.UserDAO;
import com.finverse.dao.UserDAOImpl;
import com.finverse.model.Account;
import com.finverse.model.User;
import java.time.LocalDateTime;
import java.util.List;

public class UserService {

    private static UserService instance;
    private UserDAO userDAO = new UserDAOImpl();

    private UserService() {
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public boolean emailExists(String email){
        return userDAO.emailExists(email);
    }

    public boolean phoneExists(String phone){
        return userDAO.phoneExists(phone);
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
        // Created Time
        user.setCreatedAt(LocalDateTime.now());
        // Updated Time
        user.setUpdatedAt(LocalDateTime.now());
        boolean saved = userDAO.saveUser(user);
        if (!saved) {
            System.out.println("\nRegistration Failed!");
            return;
        }
        System.out.println("\nRegistration Successful!");
        System.out.println(user);

        AccountService accountService = AccountService.getInstance();
        Account account = accountService.createAccount(user);
        if (account != null) {
            System.out.println("\nAccount Created Successfully!");
            System.out.println(account);
        } else {
            System.out.println("\nAccount Creation Failed!");
        }
    }

    public User login(String email, String password) {
        return userDAO.login(email, password);
    }

    public boolean changePassword(User user, String currentPassword, String newPassword) {
        if (!user.getPassword().equals(currentPassword)) {
            return false;
        }
        user.setPassword(newPassword);
        user.setUpdatedAt(LocalDateTime.now());
        userDAO.updateUser(user);
        NotificationService.getInstance().addNotification(
                user.getUserId(),
                "Password Changed",
                "Your account password has been changed successfully."
        );
        return true;
    }

    public User getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }

    public void resetPassword(User user, String newPassword) {
        user.setPassword(newPassword);
        user.setUpdatedAt(LocalDateTime.now());
        userDAO.updateUser(user);
        NotificationService.getInstance().addNotification(
                user.getUserId(),
                "Password Reset",
                "Your password has been reset successfully."
        );
    }

    // Admin ke lie
    public int getTotalUsers() {
        return getAllUsers().size();
    }

    // User ke lie
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public User getUserById(int userId){
        return userDAO.getUserById(userId);
    }

    public void deleteUser(User user) {
        userDAO.deleteUser(user);
    }

    public boolean updateProfile(User user,
                                 String firstName,
                                 String lastName,
                                 String email,
                                 String phone) {
        if (!user.getEmail().equalsIgnoreCase(email) && userDAO.emailExists(email)) {
            return false;
        }
        if (!user.getPhoneNumber().equals(phone) && userDAO.phoneExists(phone)) {
            return false;
        }
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPhoneNumber(phone);
        user.setUpdatedAt(LocalDateTime.now());
        userDAO.updateUser(user);
        NotificationService.getInstance().addNotification(
                user.getUserId(),
                "Profile Updated",
                "Your profile details have been updated successfully."
        );
        return true;
    }

    public boolean generatePin(User user, String pin) {
        if (user.isPinGenerated()) {
            return false;
        }
        user.setAtmPin(pin);
        user.setPinGenerated(true);
        user.setUpdatedAt(LocalDateTime.now());
        userDAO.updateUser(user);
        NotificationService.getInstance().addNotification(
                user.getUserId(),
                "ATM PIN Generated",
                "Your ATM PIN has been generated successfully."
        );
        return true;
    }

    public boolean changePin(User user, String oldPin, String newPin) {
        if (!user.getAtmPin().equals(oldPin)) {
            return false;
        }
        user.setAtmPin(newPin);
        user.setUpdatedAt(LocalDateTime.now());
        userDAO.updateUser(user);
        NotificationService.getInstance().addNotification(
                user.getUserId(),
                "ATM PIN Changed",
                "Your ATM PIN has been changed successfully."
        );
        return true;
    }

    public boolean verifyPin(User user,String pin) {
        return user.getAtmPin() != null &&
                user.getAtmPin().equals(pin);
    }

    public void lockAccount(User user) {
        user.setAccountLocked(true);
        user.setUpdatedAt(LocalDateTime.now());
        userDAO.updateUser(user);
    }
}
