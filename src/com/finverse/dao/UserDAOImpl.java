package com.finverse.dao;

import com.finverse.model.User;
import java.util.ArrayList;
import java.util.List;

// Database connection imports

import com.finverse.database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

// UserDAO interface me methods likha hai usi ko define kara hai idher
public class UserDAOImpl implements UserDAO {
    private static final List<User> users = new ArrayList<>();

    @Override
    public void saveUser(User user) {
        String sql = """
        INSERT INTO users
        (
            user_id,
            first_name,
            last_name,
            email,
            phone_number,
            password,
            atm_pin,
            pin_generated,
            account_locked,
            failed_login_attempts,
            last_login,
            daily_transfer_amount,
            last_transfer_date,
            created_at,
            updated_at
        )
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, user.getUserId());
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getLastName());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPhoneNumber());
            ps.setString(6, user.getPassword());
            ps.setString(7, user.getAtmPin());
            ps.setBoolean(8, user.isPinGenerated());
            ps.setBoolean(9, user.isAccountLocked());
            ps.setInt(10, user.getFailedLoginAttempts());
            if (user.getLastLogin() != null) {
                ps.setTimestamp(11, Timestamp.valueOf(user.getLastLogin()));
            } else {
                ps.setTimestamp(11, null);
            }
            ps.setBigDecimal(12, user.getDailyTransferAmount());
            if (user.getLastTransferDate() != null) {
                ps.setDate(13, java.sql.Date.valueOf(user.getLastTransferDate()));
            } else {
                ps.setDate(13, null);
            }
            ps.setTimestamp(14, Timestamp.valueOf(user.getCreatedAt()));
            ps.setTimestamp(15, Timestamp.valueOf(user.getUpdatedAt()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    @Override
    public List<User> getAllUsers(){
        return users;
    }

    @Override
    public User getUserById(int userId) {
        for (User user : users) {
            if (user.getUserId() == userId) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void deleteUser(User user){
        users.remove(user);
    }
}