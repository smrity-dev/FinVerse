package com.finverse.dao;

import com.finverse.model.User;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    private static final List<User> users = new ArrayList<>();

    @Override
    public void saveUser(User user) {
        users.add(user);
    }

    @Override
    public User login(String email, String password) {
        for (User user : users) {
            if (user.getEmail().equals(email)
                    && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

        @Override
        public boolean emailExists(String email) {
            for (User user : users) {
                if (user.getEmail().equalsIgnoreCase(email)) {
                    return true;
                }
            }
            return false;
        }

    @Override
    public boolean phoneExists(String phoneNumber) {
        for (User user : users) {
            if (user.getPhoneNumber().equals(phoneNumber)) {
                return true;
            }
        }
        return false;
    }
}