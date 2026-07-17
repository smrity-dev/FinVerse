package com.finverse.dao;

import com.finverse.model.ScheduledTransfer;
import java.util.ArrayList;
import java.util.List;

// Database Connection Imports

import com.finverse.database.DBConnection;
import java.sql.*;

public class ScheduledTransferDAOImpl implements ScheduledTransferDAO {

    @Override
    public void save(ScheduledTransfer transfer) {
        String sql = """
            
                INSERT INTO scheduled_transfers
            (
                user_id,
                sender_account,
                receiver_account,
                amount,
                transfer_date,
                completed
            )
            VALUES(?,?,?,?,?,?)
            """ ;
        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps =
                    con.prepareStatement(sql,
                            Statement.RETURN_GENERATED_KEYS)){
            ps.setInt(1, transfer.getUserId());
            ps.setString(2, transfer.getSenderAccount());
            ps.setString(3, transfer.getReceiverAccount());
            ps.setBigDecimal(4, transfer.getAmount());
            ps.setDate(5,
                    Date.valueOf(
                            transfer.getTransferDate()));
            ps.setBoolean(6, transfer.isCompleted());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys(); if(rs.next()){
                transfer.setScheduleId(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ScheduledTransfer> getAll() {

        List<ScheduledTransfer> list = new ArrayList<>();

        String sql =
                "SELECT * FROM scheduled_transfers ORDER BY transfer_date";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                ScheduledTransfer t = new ScheduledTransfer();

                t.setScheduleId(rs.getInt("schedule_id"));
                t.setUserId(rs.getInt("user_id"));
                t.setSenderAccount(rs.getString("sender_account"));
                t.setReceiverAccount(rs.getString("receiver_account"));
                t.setAmount(rs.getBigDecimal("amount"));
                t.setTransferDate(rs.getDate("transfer_date").toLocalDate());
                t.setCompleted(rs.getBoolean("completed"));

                list.add(t);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<ScheduledTransfer> getByUser(int userId) {

        List<ScheduledTransfer> list = new ArrayList<>();

        String sql =
                "SELECT * FROM scheduled_transfers WHERE user_id=? ORDER BY transfer_date";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                ScheduledTransfer t = new ScheduledTransfer();

                t.setScheduleId(rs.getInt("schedule_id"));
                t.setUserId(rs.getInt("user_id"));
                t.setSenderAccount(rs.getString("sender_account"));
                t.setReceiverAccount(rs.getString("receiver_account"));
                t.setAmount(rs.getBigDecimal("amount"));
                t.setTransferDate(rs.getDate("transfer_date").toLocalDate());
                t.setCompleted(rs.getBoolean("completed"));

                list.add(t);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public void deleteSchedule(ScheduledTransfer transfer) {

        String sql =
                "DELETE FROM scheduled_transfers WHERE schedule_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, transfer.getScheduleId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<ScheduledTransfer> getAllSchedules() {

        List<ScheduledTransfer> list = new ArrayList<>();
        String sql =
                "SELECT * FROM scheduled_transfers ORDER BY transfer_date";
        try(Connection con=DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery()){
            while(rs.next()){
                ScheduledTransfer s=
                        new ScheduledTransfer();
                s.setScheduleId(rs.getInt("schedule_id"));
                s.setUserId(rs.getInt("user_id"));
                s.setSenderAccount(rs.getString("sender_account"));
                s.setReceiverAccount(rs.getString("receiver_account"));
                s.setAmount(rs.getBigDecimal("amount"));
                s.setTransferDate(rs.getDate("transfer_date").toLocalDate());
                s.setCompleted(rs.getBoolean("completed"));
                list.add(s);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public ScheduledTransfer getSchedule(int scheduleId) {

        String sql = "SELECT * FROM scheduled_transfers WHERE schedule_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, scheduleId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ScheduledTransfer s =
                        new ScheduledTransfer();
                s.setScheduleId(rs.getInt("schedule_id"));
                s.setUserId(rs.getInt("user_id"));
                s.setSenderAccount(rs.getString("sender_account"));
                s.setReceiverAccount(rs.getString("receiver_account"));
                s.setAmount(rs.getBigDecimal("amount"));
                s.setTransferDate(rs.getDate("transfer_date").toLocalDate());
                s.setCompleted(rs.getBoolean("completed"));
                return s;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ScheduledTransfer> getSchedules(int userId) {

        List<ScheduledTransfer> list =
                new ArrayList<>();
        String sql = "SELECT * FROM scheduled_transfers WHERE user_id=? ORDER BY transfer_date";
        try(Connection con=DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)){
            ps.setInt(1,userId);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                ScheduledTransfer s =
                        new ScheduledTransfer();
                s.setScheduleId(rs.getInt("schedule_id"));
                s.setUserId(rs.getInt("user_id"));
                s.setSenderAccount(rs.getString("sender_account"));
                s.setReceiverAccount(rs.getString("receiver_account"));
                s.setAmount(rs.getBigDecimal("amount"));
                s.setTransferDate(rs.getDate("transfer_date").toLocalDate());
                s.setCompleted(rs.getBoolean("completed"));
                list.add(s);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return list;
    }
}