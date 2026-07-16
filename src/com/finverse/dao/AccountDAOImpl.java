package com.finverse.dao;

import com.finverse.model.Account;
import com.finverse.model.AccountStatus;
import com.finverse.model.AccountType;
import java.math.BigDecimal;

// Database Connection Imports

import com.finverse.database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AccountDAOImpl implements AccountDAO {

    @Override
    public boolean saveAccount(Account account) {

        String sql = """
        INSERT INTO accounts
        (
            account_number,
            user_id,
            account_type,
            account_status,
            balance,
            created_at,
            updated_at
        )
        VALUES (?,?,?,?,?,?,?)
        """;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, account.getAccountNumber());
            ps.setInt(2, account.getUserId());
            ps.setString(3, account.getAccountType().name());
            ps.setString(4, account.getAccountStatus().name());
            ps.setBigDecimal(5, account.getBalance());
            ps.setTimestamp(6, Timestamp.valueOf(account.getCreatedAt()));
            ps.setTimestamp(7, Timestamp.valueOf(account.getUpdatedAt()));
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Account getAccountByUserId(int userId) {

        String sql = "SELECT * FROM accounts WHERE user_id=?";
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1,userId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Account account = new Account();
                account.setAccountNumber(
                        rs.getString("account_number"));
                account.setUserId(
                        rs.getInt("user_id"));
                account.setAccountType(
                        AccountType.valueOf(
                                rs.getString("account_type")));
                account.setAccountStatus(
                        AccountStatus.valueOf(
                                rs.getString("account_status")));
                account.setBalance(
                        rs.getBigDecimal("balance"));
                account.setCreatedAt(
                        rs.getTimestamp("created_at")
                                .toLocalDateTime());
                account.setUpdatedAt(
                        rs.getTimestamp("updated_at")
                                .toLocalDateTime());
                return account;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateAccount(Account account) {

        String sql="""
            UPDATE accounts
            SET
                account_type=?,
                account_status=?,
                balance=?,
                updated_at=?
            WHERE account_number=?
            """;
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setString(1,
                    account.getAccountType().name());
            ps.setString(2,
                    account.getAccountStatus().name());
            ps.setBigDecimal(3,
                    account.getBalance());
            ps.setTimestamp(4,
                    Timestamp.valueOf(
                            account.getUpdatedAt()));
            ps.setString(5,
                    account.getAccountNumber());
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Account getAccountByAccountNumber(String accountNumber) {

        String sql = "SELECT * FROM accounts WHERE account_number=?";
        try(Connection connection=DBConnection.getConnection();
            PreparedStatement ps= connection.prepareStatement(sql))
        {
            ps.setString(1,accountNumber);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                Account account=new Account();
                account.setAccountNumber(
                        rs.getString("account_number"));
                account.setUserId(
                        rs.getInt("user_id"));
                account.setAccountType(
                        AccountType.valueOf(
                                rs.getString("account_type")));
                account.setAccountStatus(
                        AccountStatus.valueOf(
                                rs.getString("account_status")));
                account.setBalance(
                        rs.getBigDecimal("balance"));
                account.setCreatedAt(
                        rs.getTimestamp("created_at")
                                .toLocalDateTime());
                account.setUpdatedAt(
                        rs.getTimestamp("updated_at")
                                .toLocalDateTime());
                return account;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM accounts";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Account account = new Account();
                account.setAccountNumber(rs.getString("account_number"));
                account.setUserId(rs.getInt("user_id"));
                account.setAccountType(
                        AccountType.valueOf(rs.getString("account_type")));
                account.setAccountStatus(
                        AccountStatus.valueOf(rs.getString("account_status")));
                account.setBalance(rs.getBigDecimal("balance"));
                account.setCreatedAt(
                        rs.getTimestamp("created_at").toLocalDateTime());
                account.setUpdatedAt(
                        rs.getTimestamp("updated_at").toLocalDateTime());
                accounts.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public void deleteAccount(Account account) {

        String sql =
                "DELETE FROM accounts WHERE account_number=?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps =
                     connection.prepareStatement(sql)) {

            ps.setString(1, account.getAccountNumber());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void blockAccount(Account account) {

        account.setAccountStatus(AccountStatus.BLOCKED);

        updateAccount(account);
    }

    @Override
    public void activateAccount(Account account) {

        account.setAccountStatus(AccountStatus.ACTIVE);

        updateAccount(account);
    }

    @Override
    public void closeAccount(Account account) {

        account.setAccountStatus(AccountStatus.CLOSED);

        updateAccount(account);
    }

    @Override
    public void changeAccountType(Account account,
                                  AccountType accountType) {

        account.setAccountType(accountType);

        updateAccount(account);
    }

    @Override
    public BigDecimal getTotalBalance() {

        String sql =
                "SELECT SUM(balance) total FROM accounts";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps =
                     connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {

                BigDecimal total = rs.getBigDecimal("total");

                return total == null
                        ? BigDecimal.ZERO
                        : total;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return BigDecimal.ZERO;
    }

    @Override
    public int getTotalAccounts() {

        String sql =
                "SELECT COUNT(*) total FROM accounts";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps =
                     connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public int getActiveAccounts() {

        String sql =
                "SELECT COUNT(*) total FROM accounts WHERE account_status='ACTIVE'";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps =
                     connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public int getClosedAccounts() {

        String sql =
                "SELECT COUNT(*) total FROM accounts WHERE account_status='CLOSED'";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps =
                     connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
}