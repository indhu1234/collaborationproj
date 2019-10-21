package indhu.com.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import indhu.com.dao.FriendDAO;
import indhu.com.dao.UserDAO;
import indhu.com.model.Friend;
import indhu.com.model.UserDetail;

@Repository("friendDAO")
@Transactional
public class FriendDAOImpl implements FriendDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public boolean sendFriendReq(Friend friend) {
		try {
			friend.setStatus("P");
			sessionFactory.getCurrentSession().save(friend);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean acceptFriendReq(int friendId) {
		try {
			Session session = sessionFactory.openSession();
			Friend friend = session.get(Friend.class, friendId);
			session.close();
			friend.setStatus("A");
			sessionFactory.getCurrentSession().update(friend);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean deleteFriendReq(int friendId) {
		try {
			sessionFactory.getCurrentSession().delete(getFriendDetail(friendId));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean unfriend(String username1, String username2) {
		try {
			Session session = sessionFactory.openSession();
			Query query = session.createQuery("from Friend where (username = '" + username1 + "' and friendUsername = '"
					+ username2 + "') or (friendUsername = '" + username1 + "' and username = '" + username2 + "')");
			Friend friend = (Friend) query.getSingleResult();
			session.close();
			sessionFactory.getCurrentSession().delete(friend);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	@Override
	public Friend getFriendDetail(int friendId) {
		try {
			Session session = sessionFactory.openSession();
			Friend friend = session.get(Friend.class, friendId);
			session.close();
			return friend;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<UserDetail> getFriendList(String username) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Friend where (username = '" + username + "' or friendUsername='"
				+ username + "') and status = 'A'");
		List<Friend> list = query.list();
		List<UserDetail> friendList = new ArrayList<UserDetail>();
		for (Friend friend : list) {

			if (friend.getUsername().equals(username)) {
				UserDetail user = session.get(UserDetail.class, friend.getFriendUsername());
				friendList.add(user);
			} else {
				UserDetail user = session.get(UserDetail.class, friend.getUsername());
				friendList.add(user);
			}
		}
		session.close();
		return friendList;
	}

	@Override
	public List<Friend> getPendingFriends(String username) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Friend where friendUsername = '" + username + "' and status = 'P'");
		List<Friend> friendList = query.list();
		session.close();
		return friendList;
	}

	@Override
	public List<UserDetail> getSuggestedFriends(String username) {
		List<UserDetail> suggestedFriendList = new ArrayList<UserDetail>();
		List<UserDetail> friendList = getFriendList(username);
		for (UserDetail user : friendList) {
			List<UserDetail> friendsFriendList = getFriendList(user.getUsername());
			for (UserDetail friendOfUser : friendsFriendList) {
				if (friendOfUser.getUsername().equals(username)) {
					continue;
				}
				Friend friend = checkIfFriends(username, friendOfUser.getUsername(), true);
				if (friend == null) {
					if (suggestedFriendList.isEmpty()) {

						suggestedFriendList.add(friendOfUser);
					} else {
						boolean userExists = false;
						for (UserDetail u : suggestedFriendList) {
							if (friendOfUser.getUsername().equals(u.getUsername())) {
								userExists = true;

								break;
							}
						}
						if (userExists == false) {

							suggestedFriendList.add(friendOfUser);
						}
					}
				}
			}
		}
		return suggestedFriendList;
	}

	@Override
	public Friend checkIfFriends(String username1, String username2, boolean ignoreStatus) {
		Friend friend;
		Session session = sessionFactory.openSession();
		Query query;
		if (ignoreStatus == true)
			query = session.createQuery("from Friend where ((username = '" + username1 + "' and friendUsername = '"
					+ username2 + "') or (friendUsername = '" + username1 + "' and username = '" + username2 + "'))");
		else
			query = session.createQuery("from Friend where ((username = '" + username1 + "' and friendUsername = '"
					+ username2 + "') or (friendUsername = '" + username1 + "' and username = '" + username2
					+ "')) and status = 'A'");
		try {
			friend = (Friend) query.getSingleResult();
			return friend;
		} catch (Exception e) {
			return null;
		}
	}
}