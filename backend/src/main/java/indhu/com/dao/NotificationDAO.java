package indhu.com.dao;

import java.util.List;

import indhu.com.model.Notification;

public interface NotificationDAO {
	public boolean addNotification(Notification notification);

	public boolean deleteNotification(Notification notification);

	public boolean markNotificationAsRead(int notificationId);

	public boolean markNotificationAsUnread(int notificationId);

	public Notification getNotification(int notificationId);

	public List<Notification> getNotificationList(String username);
}
