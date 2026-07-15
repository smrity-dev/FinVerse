package com.finverse.dao;

import com.finverse.model.User;
import java.util.List;

public interface UserDAO {

    void saveUser(User user);
    User login(String email, String password);

    boolean emailExists(String email);
    boolean phoneExists(String phoneNumber);

    // Password change,forget karne ke liye
    void updateUser(User user);
    User getUserByEmail(String email);

    List<User> getAllUsers();
    void deleteUser(User user);
}

// UserDAO Database store rakhta hai , ye ek interface class hai , inka andar ke methods ko UserDAOImpl implement karega alag file me