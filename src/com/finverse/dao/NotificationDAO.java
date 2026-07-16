package com.finverse.dao;

import com.finverse.model.Notification;
import java.util.List;

public interface NotificationDAO {

    void saveNotification(Notification notification);
    List<Notification> getNotifications(int userId);
    void markAllAsRead(int userId);
    int getUnreadCount(int userId);
    void deleteAll(int userId);

}