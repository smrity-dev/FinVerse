package com.finverse.service;

import com.finverse.dao.NotificationDAO;
import com.finverse.dao.NotificationDAOImpl;
import com.finverse.model.Notification;
import java.time.LocalDateTime;
import java.util.List;

public class NotificationService {

    private static NotificationService instance;
    private NotificationDAO notificationDAO = new NotificationDAOImpl();

    private NotificationService() {
    }

    public static NotificationService getInstance() {
        if (instance == null) {
            instance = new NotificationService();
        }
        return instance;
    }

    public void addNotification(int userId,
                                String title,
                                String message) {
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setRead(false);
        notification.setCreatedAt(LocalDateTime.now());
        notificationDAO.saveNotification(notification);
    }

    public List<Notification> getNotifications(int userId) {
        return notificationDAO.getNotifications(userId);
    }

    public void markAllAsRead(int userId) {
        notificationDAO.markAllAsRead(userId);
    }

    public int getUnreadCount(int userId) {
        return notificationDAO.getUnreadCount(userId);
    }

    public void deleteAllNotifications(int userId) {
        notificationDAO.deleteAll(userId);
    }

    public void sendNotification(int userId, String message) {
        addNotification(
                userId,
                "Notification",
                message
        );
    }
}