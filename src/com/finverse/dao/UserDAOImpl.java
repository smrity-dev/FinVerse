package com.finverse.dao;

import com.finverse.model.User;
import java.util.ArrayList;
import java.util.List;

// UserDAO interface me methods likha hai usi ko define kara hai idher
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
                    //equalsIgnoreCase capital small letters ko ignore kar ke compare kare ga ex:-  Smrity and smrity dono same rhega
                    return true;
                }
            }
            return false;
        }


    @Override
    public boolean phoneExists(String phoneNumber) {
        for (User user : users) {
            if (user.getPhoneNumber().equals(phoneNumber)) {
                //equals case sensitivite hota hai exactly same words hone chaiye tabhi true milega ex:- Smrity ka Smrity hi not smrity
                return true;
            }
        }
        return false;
    }

    // Password change,Forget ke liye 2 methods
    @Override
    public void updateUser(User user) {
        // ArrayList me object automatically update ho jata hai.
        // JDBC version me SQL UPDATE chalega.
    }
    @Override
    public User getUserByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }
        return null;
    }
}