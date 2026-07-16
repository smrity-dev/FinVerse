package com.finverse.dao;

import com.finverse.model.Notification;
import java.util.ArrayList;
import java.util.List;

// Database connection Imports

import com.finverse.database.DBConnection;
import java.sql.*;

public class NotificationDAOImpl implements NotificationDAO {

    private static final List<Notification> notifications = new ArrayList<>();

    @Override
    public void saveNotification(Notification notification) {

        String sql = """
            INSERT INTO notifications
            (
                user_id,
                title,
                message,
                is_read,
                created_at
            )
            VALUES(?,?,?,?,?)
            """;
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, notification.getUserId());
            ps.setString(2, notification.getTitle());
            ps.setString(3, notification.getMessage());
            ps.setBoolean(4, notification.isRead());
            ps.setTimestamp(
                    5,
                    Timestamp.valueOf(notification.getCreatedAt())
            );
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                notification.setNotificationId(rs.getInt(1));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Notification> getNotifications(int userId) {

        List<Notification> list = new ArrayList<>();
        String sql = "SELECT * FROM notifications WHERE user_id=? ORDER BY created_at DESC";
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1,userId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Notification notification =
                        new Notification();
                notification.setNotificationId(
                        rs.getInt("notification_id"));
                notification.setUserId(
                        rs.getInt("user_id"));
                notification.setTitle(
                        rs.getString("title"));
                notification.setMessage(
                        rs.getString("message"));
                notification.setRead(
                        rs.getBoolean("is_read"));
                notification.setCreatedAt(
                        rs.getTimestamp("created_at")
                                .toLocalDateTime());
                list.add(notification);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void markAllAsRead(int userId) {

        String sql = "UPDATE notifications SET is_read=TRUE WHERE user_id=?";
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1,userId);
            ps.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public int getUnreadCount(int userId) {

        String sql = "SELECT COUNT(*) FROM notifications WHERE user_id=? AND is_read=FALSE";
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1,userId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void deleteAll(int userId) {

        String sql = "DELETE FROM notifications WHERE user_id=?";
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1,userId);
            ps.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}