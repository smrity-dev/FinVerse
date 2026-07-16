package com.finverse.dao;

import com.finverse.model.Beneficiary;
import java.util.ArrayList;
import java.util.List;

// Database connection Imports

import com.finverse.database.DBConnection;
import java.sql.*;

public class BeneficiaryDAOImpl implements BeneficiaryDAO{

    private static final List<Beneficiary> beneficiaries=new ArrayList<>();

    @Override
    public void saveBeneficiary(Beneficiary beneficiary) {

        String sql = """
            INSERT INTO beneficiaries
            (
                user_id,
                beneficiary_name,
                account_number,
                added_at
            )
            VALUES (?,?,?,?)
            """;
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, beneficiary.getUserId());
            ps.setString(2, beneficiary.getBeneficiaryName());
            ps.setString(3, beneficiary.getAccountNumber());
            ps.setTimestamp(4,
                    Timestamp.valueOf(beneficiary.getAddedAt()));
            ps.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Beneficiary> getBeneficiaries(int userId) {

        List<Beneficiary> list = new ArrayList<>();
        String sql = "SELECT * FROM beneficiaries WHERE user_id=?";
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Beneficiary beneficiary = new Beneficiary();
                beneficiary.setBeneficiaryId(
                        rs.getInt("beneficiary_id"));
                beneficiary.setUserId(
                        rs.getInt("user_id"));
                beneficiary.setBeneficiaryName(
                        rs.getString("beneficiary_name"));
                beneficiary.setAccountNumber(
                        rs.getString("account_number"));
                beneficiary.setAddedAt(
                        rs.getTimestamp("added_at")
                                .toLocalDateTime());
                list.add(beneficiary);
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Beneficiary getBeneficiary(int userId, String accountNumber) {

        String sql = """
            SELECT *
            FROM beneficiaries
            WHERE user_id=?
            AND account_number=?
            """;
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, userId);
            ps.setString(2, accountNumber);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                Beneficiary beneficiary = new Beneficiary();
                beneficiary.setBeneficiaryId(
                        rs.getInt("beneficiary_id"));
                beneficiary.setUserId(
                        rs.getInt("user_id"));
                beneficiary.setBeneficiaryName(
                        rs.getString("beneficiary_name"));
                beneficiary.setAccountNumber(
                        rs.getString("account_number"));
                beneficiary.setAddedAt(
                        rs.getTimestamp("added_at")
                                .toLocalDateTime());
                return beneficiary;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteBeneficiary(Beneficiary beneficiary) {

        String sql = "DELETE FROM beneficiaries WHERE beneficiary_id=?";
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, beneficiary.getBeneficiaryId());
            ps.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void markFavourite(Beneficiary beneficiary) {
        beneficiary.setFavourite(true);
    }

    @Override
    public Beneficiary searchBeneficiary(int userId, String accountNumber) {
        return getBeneficiary(userId, accountNumber);
    }
}