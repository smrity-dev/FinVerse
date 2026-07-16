package com.finverse.dao;

import com.finverse.model.Notification;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAOImpl implements NotificationDAO {

    private static final List<Notification> notifications = new ArrayList<>();

    @Override
    public void saveNotification(Notification notification) {
        notifications.add(notification);
    }

    @Override
    public List<Notification> getNotifications(int userId) {
        List<Notification> list = new ArrayList<>();
        for (Notification notification : notifications) {
            if (notification.getUserId() == userId) {
                list.add(notification);
            }
        }
        return list;
    }

    @Override
    public void markAllAsRead(int userId) {
        for (Notification notification : notifications) {
            if (notification.getUserId() == userId) {
                notification.setRead(true);
            }
        }
    }

    @Override
    public int getUnreadCount(int userId) {
        int count = 0;
        for (Notification notification : notifications) {
            if (notification.getUserId() == userId && !notification.isRead()) {
                count++;
            }
        }
        return count;
    }

    @Override
    public void deleteAll(int userId) {
        notifications.removeIf(notification ->
                notification.getUserId() == userId);
    }
}