package com.finverse.dao;

import com.finverse.model.Transaction;
import java.util.ArrayList;
import java.util.List;

// Database connection Imports

import com.finverse.database.DBConnection;
import com.finverse.model.TransactionStatus;
import com.finverse.model.TransactionType;
import java.sql.*;

public class TransactionDAOImpl implements TransactionDAO {

    @Override
    public boolean saveTransaction(Transaction transaction) {

        String sql = """
        INSERT INTO transactions
        (
            account_number,
            transaction_type,
            transaction_status,
            amount,
            balance_after_transaction,
            reference_number,
            description,
            created_at
        )
        VALUES(?,?,?,?,?,?,?,?)
        """;
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, transaction.getAccountNumber());
            ps.setString(2, transaction.getTransactionType().name());
            ps.setString(3, transaction.getTransactionStatus().name());
            ps.setBigDecimal(4, transaction.getAmount());
            ps.setBigDecimal(5, transaction.getBalanceAfterTransaction());
            ps.setString(6, transaction.getReferenceNumber());
            ps.setString(7, transaction.getRemarks());
            ps.setTimestamp(8,
                    Timestamp.valueOf(transaction.getTransactionTime()));
            int rows = ps.executeUpdate();
            if(rows > 0){
                ResultSet rs = ps.getGeneratedKeys();
                if(rs.next()){
                    transaction.setTransactionId(rs.getInt(1));
                }
                return true;
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Transaction> getTransactions(String accountNumber) {

        List<Transaction> list = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE account_number=? ORDER BY created_at DESC";
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setString(1,accountNumber);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Transaction transaction = new Transaction();
                transaction.setTransactionId(
                        rs.getInt("transaction_id"));
                transaction.setAccountNumber(
                        rs.getString("account_number"));

                transaction.setTransactionType(
                        TransactionType.valueOf(
                                rs.getString("transaction_type")));
                transaction.setTransactionStatus(
                        TransactionStatus.valueOf(
                                rs.getString("transaction_status")));
                transaction.setAmount(
                        rs.getBigDecimal("amount"));
                transaction.setBalanceAfterTransaction(
                        rs.getBigDecimal("balance_after_transaction"));
                transaction.setReferenceNumber(
                        rs.getString("reference_number"));
                transaction.setRemarks(
                        rs.getString("description"));
                transaction.setTransactionTime(
                        rs.getTimestamp("created_at")
                                .toLocalDateTime());
                list.add(transaction);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    // User
    @Override
    public List<Transaction> getAllTransactions() {
        List<Transaction> list = new ArrayList<>();
        String sql = "SELECT * FROM transactions ORDER BY created_at DESC";
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery())
        {
            while(rs.next()){
                Transaction transaction = new Transaction();
                transaction.setTransactionId(
                        rs.getInt("transaction_id"));
                transaction.setAccountNumber(
                        rs.getString("account_number"));
                transaction.setTransactionType(
                        TransactionType.valueOf(
                                rs.getString("transaction_type")));
                transaction.setTransactionStatus(
                        TransactionStatus.valueOf(
                                rs.getString("transaction_status")));
                transaction.setAmount(
                        rs.getBigDecimal("amount"));
                transaction.setBalanceAfterTransaction(
                        rs.getBigDecimal("balance_after_transaction"));
                transaction.setReferenceNumber(
                        rs.getString("reference_number"));
                transaction.setRemarks(
                        rs.getString("description"));
                transaction.setTransactionTime(
                        rs.getTimestamp("created_at")
                                .toLocalDateTime());
                list.add(transaction);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    // Admin
    @Override
    public List<Transaction> getTransactions() {
        return getAllTransactions();
    }
}
