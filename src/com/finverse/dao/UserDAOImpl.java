package com.finverse.dao;

import com.finverse.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Database connection imports

import com.finverse.database.DBConnection;

// UserDAO interface me methods likha hai usi ko define kara hai idher
public class UserDAOImpl implements UserDAO {

    @Override
    public boolean saveUser(User user) {

        String sql = """
        INSERT INTO users
        (
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
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     sql,
                     Statement.RETURN_GENERATED_KEYS
             )) {
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPhoneNumber());
            ps.setString(5, user.getPassword());
            ps.setString(6, user.getAtmPin());
            ps.setBoolean(7, user.isPinGenerated());
            ps.setBoolean(8, user.isAccountLocked());
            ps.setInt(9, user.getFailedLoginAttempts());
            if (user.getLastLogin() != null) {
                ps.setTimestamp(10,
                        Timestamp.valueOf(user.getLastLogin()));
            } else {
                ps.setTimestamp(10, null);
            }
            ps.setBigDecimal(11,
                    user.getDailyTransferAmount());
            if (user.getLastTransferDate() != null) {
                ps.setDate(12,
                        java.sql.Date.valueOf(
                                user.getLastTransferDate()));
            } else {
                ps.setDate(12, null);
            }
            ps.setTimestamp(13,
                    Timestamp.valueOf(user.getCreatedAt()));
            ps.setTimestamp(14,
                    Timestamp.valueOf(user.getUpdatedAt()));
            int rows = ps.executeUpdate();
            if (rows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    user.setUserId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User login(String email, String password) {

        String sql = """
            SELECT *
            FROM users
            WHERE email = ? AND password = ?
            """;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setPassword(rs.getString("password"));
                user.setAtmPin(rs.getString("atm_pin"));
                user.setPinGenerated(rs.getBoolean("pin_generated"));
                user.setAccountLocked(rs.getBoolean("account_locked"));
                user.setFailedLoginAttempts(rs.getInt("failed_login_attempts"));
                Timestamp lastLogin = rs.getTimestamp("last_login");
                if (lastLogin != null) {
                    user.setLastLogin(lastLogin.toLocalDateTime());
                }
                user.setDailyTransferAmount(
                        rs.getBigDecimal("daily_transfer_amount")
                );
                java.sql.Date lastTransferDate =
                        rs.getDate("last_transfer_date");
                if (lastTransferDate != null) {
                    user.setLastTransferDate(
                            lastTransferDate.toLocalDate()
                    );
                }
                user.setCreatedAt(
                        rs.getTimestamp("created_at").toLocalDateTime()
                );
                user.setUpdatedAt(
                        rs.getTimestamp("updated_at").toLocalDateTime()
                );
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean emailExists(String email) {

        String sql = """
            SELECT COUNT(*)
            FROM users
            WHERE email = ?
            """;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean phoneExists(String phoneNumber) {

        String sql = """
            SELECT COUNT(*)
            FROM users
            WHERE phone_number = ?
            """;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, phoneNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Password change,Forget ke lie 2 methods

    @Override
    public void updateUser(User user) {

        String sql = """
            UPDATE users
            SET
                first_name = ?,
                last_name = ?,
                email = ?,
                phone_number = ?,
                password = ?,
                atm_pin = ?,
                pin_generated = ?,
                account_locked = ?,
                failed_login_attempts = ?,
                last_login = ?,
                daily_transfer_amount = ?,
                last_transfer_date = ?,
                updated_at = ?
            WHERE user_id = ?
            """;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPhoneNumber());
            ps.setString(5, user.getPassword());
            ps.setString(6, user.getAtmPin());
            ps.setBoolean(7, user.isPinGenerated());
            ps.setBoolean(8, user.isAccountLocked());
            ps.setInt(9, user.getFailedLoginAttempts());
            if (user.getLastLogin() != null) {
                ps.setTimestamp(10, Timestamp.valueOf(user.getLastLogin()));
            } else {
                ps.setTimestamp(10, null);
            }
            ps.setBigDecimal(11, user.getDailyTransferAmount());
            if (user.getLastTransferDate() != null) {
                ps.setDate(12, java.sql.Date.valueOf(user.getLastTransferDate()));
            } else {
                ps.setDate(12, null);
            }
            ps.setTimestamp(13, Timestamp.valueOf(user.getUpdatedAt()));
            ps.setInt(14, user.getUserId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getUserByEmail(String email) {

        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setPassword(rs.getString("password"));
                user.setAtmPin(rs.getString("atm_pin"));
                user.setPinGenerated(rs.getBoolean("pin_generated"));
                user.setAccountLocked(rs.getBoolean("account_locked"));
                user.setFailedLoginAttempts(rs.getInt("failed_login_attempts"));
                Timestamp lastLogin = rs.getTimestamp("last_login");
                if (lastLogin != null) {
                    user.setLastLogin(lastLogin.toLocalDateTime());
                }
                user.setDailyTransferAmount(
                        rs.getBigDecimal("daily_transfer_amount")
                );
                java.sql.Date lastTransfer =
                        rs.getDate("last_transfer_date");
                if (lastTransfer != null) {
                    user.setLastTransferDate(
                            lastTransfer.toLocalDate()
                    );
                }
                user.setCreatedAt(
                        rs.getTimestamp("created_at").toLocalDateTime()
                );
                user.setUpdatedAt(
                        rs.getTimestamp("updated_at").toLocalDateTime()
                );
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setPassword(rs.getString("password"));
                user.setAtmPin(rs.getString("atm_pin"));
                user.setPinGenerated(rs.getBoolean("pin_generated"));
                user.setAccountLocked(rs.getBoolean("account_locked"));
                user.setFailedLoginAttempts(rs.getInt("failed_login_attempts"));
                Timestamp lastLogin = rs.getTimestamp("last_login");
                if (lastLogin != null) {
                    user.setLastLogin(lastLogin.toLocalDateTime());
                }
                user.setDailyTransferAmount(
                        rs.getBigDecimal("daily_transfer_amount")
                );
                java.sql.Date lastTransfer =
                        rs.getDate("last_transfer_date");
                if (lastTransfer != null) {
                    user.setLastTransferDate(
                            lastTransfer.toLocalDate()
                    );
                }
                user.setCreatedAt(
                        rs.getTimestamp("created_at").toLocalDateTime()
                );
                user.setUpdatedAt(
                        rs.getTimestamp("updated_at").toLocalDateTime()
                );
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User getUserById(int userId) {

        String sql = "SELECT * FROM users WHERE user_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setPassword(rs.getString("password"));
                user.setAtmPin(rs.getString("atm_pin"));
                user.setPinGenerated(rs.getBoolean("pin_generated"));
                user.setAccountLocked(rs.getBoolean("account_locked"));
                user.setFailedLoginAttempts(rs.getInt("failed_login_attempts"));
                Timestamp lastLogin = rs.getTimestamp("last_login");
                if (lastLogin != null) {
                    user.setLastLogin(lastLogin.toLocalDateTime());
                }
                user.setDailyTransferAmount(
                        rs.getBigDecimal("daily_transfer_amount")
                );
                java.sql.Date lastTransfer =
                        rs.getDate("last_transfer_date");
                if (lastTransfer != null) {
                    user.setLastTransferDate(
                            lastTransfer.toLocalDate()
                    );
                }
                user.setCreatedAt(
                        rs.getTimestamp("created_at").toLocalDateTime()
                );
                user.setUpdatedAt(
                        rs.getTimestamp("updated_at").toLocalDateTime()
                );
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteUser(User user) {

        String sql = "DELETE FROM users WHERE user_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, user.getUserId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}