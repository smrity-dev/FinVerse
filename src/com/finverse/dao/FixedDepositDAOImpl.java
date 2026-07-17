package com.finverse.dao;

import com.finverse.database.DBConnection;
import com.finverse.model.FixedDeposit;

// DataBase connection Import
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FixedDepositDAOImpl implements FixedDepositDAO {

    @Override
    public void saveFD(FixedDeposit fd) {

        String sql="""
        INSERT INTO fixed_deposits
        (
            user_id,
            account_number,
            amount,
            interest_rate,
            duration_months,
            maturity_amount,
            start_date,
            maturity_date,
            status
        )
        VALUES(?,?,?,?,?,?,?,?,?)
        """;
        try(Connection con= DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS))
        {
            ps.setInt(1,fd.getUserId());
            ps.setString(2,fd.getAccountNumber());
            ps.setBigDecimal(3,fd.getAmount());
            ps.setDouble(4,fd.getInterestRate());
            ps.setInt(5,fd.getDurationMonths());
            ps.setBigDecimal(6,fd.getMaturityAmount());
            ps.setDate(7,Date.valueOf(fd.getStartDate()));
            ps.setDate(8,Date.valueOf(fd.getMaturityDate()));
            ps.setString(9,fd.getStatus());
            ps.executeUpdate();
            ResultSet rs=ps.getGeneratedKeys();
            if(rs.next()){
                fd.setFdId(rs.getInt(1));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<FixedDeposit> getFDs(int userId) {

        List<FixedDeposit> list=new ArrayList<>();
        String sql="SELECT * FROM fixed_deposits WHERE user_id=?";
        try(Connection con=DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql))
        {
            ps.setInt(1,userId);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                FixedDeposit fd=new FixedDeposit();
                fd.setFdId(rs.getInt("fd_id"));
                fd.setUserId(rs.getInt("user_id"));
                fd.setAccountNumber(rs.getString("account_number"));
                fd.setAmount(rs.getBigDecimal("amount"));
                fd.setInterestRate(rs.getDouble("interest_rate"));
                fd.setDurationMonths(rs.getInt("duration_months"));
                fd.setMaturityAmount(rs.getBigDecimal("maturity_amount"));
                fd.setStartDate(rs.getDate("start_date").toLocalDate());
                fd.setMaturityDate(rs.getDate("maturity_date").toLocalDate());
                fd.setStatus(rs.getString("status"));
                list.add(fd);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<FixedDeposit> getAllFDs() {

        List<FixedDeposit> list=new ArrayList<>();
        String sql="SELECT * FROM fixed_deposits";
        try(Connection con=DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery())
        {
            while(rs.next()){
                FixedDeposit fd=new FixedDeposit();
                fd.setFdId(rs.getInt("fd_id"));
                fd.setUserId(rs.getInt("user_id"));
                fd.setAccountNumber(rs.getString("account_number"));
                fd.setAmount(rs.getBigDecimal("amount"));
                fd.setInterestRate(rs.getDouble("interest_rate"));
                fd.setDurationMonths(rs.getInt("duration_months"));
                fd.setMaturityAmount(rs.getBigDecimal("maturity_amount"));
                fd.setStartDate(rs.getDate("start_date").toLocalDate());
                fd.setMaturityDate(rs.getDate("maturity_date").toLocalDate());
                fd.setStatus(rs.getString("status"));
                list.add(fd);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public FixedDeposit getFD(int fdId) {

        String sql="SELECT * FROM fixed_deposits WHERE fd_id=?";
        try(Connection con=DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql))
        {
            ps.setInt(1,fdId);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                FixedDeposit fd=new FixedDeposit();
                fd.setFdId(rs.getInt("fd_id"));
                fd.setUserId(rs.getInt("user_id"));
                fd.setAccountNumber(rs.getString("account_number"));
                fd.setAmount(rs.getBigDecimal("amount"));
                fd.setInterestRate(rs.getDouble("interest_rate"));
                fd.setDurationMonths(rs.getInt("duration_months"));
                fd.setMaturityAmount(rs.getBigDecimal("maturity_amount"));
                fd.setStartDate(rs.getDate("start_date").toLocalDate());
                fd.setMaturityDate(rs.getDate("maturity_date").toLocalDate());
                fd.setStatus(rs.getString("status"));
                return fd;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void closeFD(FixedDeposit fd) {

        String sql="""
        UPDATE fixed_deposits
        SET status='CLOSED'
        WHERE fd_id=?
        """;
        try(Connection con=DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql))
        {
            ps.setInt(1,fd.getFdId());
            ps.executeUpdate();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}