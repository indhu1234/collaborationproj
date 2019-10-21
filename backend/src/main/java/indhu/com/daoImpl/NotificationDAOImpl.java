package indhu.com.daoImpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import indhu.com.dao.NotificationDAO;
import indhu.com.model.Notification;

@Repository("notificationDAO")
@Transactional
public class NotificationDAOImpl implements NotificationDAO {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public boolean addNotification(Notification notification) {
		try {
			sessionFactory.getCurrentSession().save(notification);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean deleteNotification(Notification notification) {
		try {
			sessionFactory.getCurrentSession().delete(notification);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean markNotificationAsRead(int notificationId) {
		try {
			Notification notification = getNotification(notificationId);
			notification.setStatus("R");
			sessionFactory.getCurrentSession().update(notification);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean markNotificationAsUnread(int notificationId) {
		try {
			Notification notification = getNotification(notificationId);
			notification.setStatus("UR");
			sessionFactory.getCurrentSession().update(notification);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Notification getNotification(int notificationId) {
		try {
			Session session = sessionFactory.openSession();
			Notification notification = session.get(Notification.class, notificationId);
			session.close();
			return notification;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<Notification> getNotificationList(String username) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Notification WHERE username = '" + username + "'");
		List<Notification> notificationList = query.list();
		session.close();
		return notificationList;
	}

}
