package com.finverse.dao;

import com.finverse.model.User;

public interface UserDAO {

    void saveUser(User user);
    User login(String email, String password);
    
    boolean emailExists(String email);
    boolean phoneExists(String phoneNumber);
}