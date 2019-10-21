package indhu.com.dao;

import java.util.List;

import indhu.com.model.Friend;
import indhu.com.model.UserDetail;

public interface FriendDAO {
	// public List<Friend> getFriendList(String username);
	public List<UserDetail> getFriendList(String username);

	public List<Friend> getPendingFriends(String username);

	public List<UserDetail> getSuggestedFriends(String username);

	public boolean sendFriendReq(Friend friend);

	public boolean acceptFriendReq(int friendId);

	public boolean deleteFriendReq(int friendId);

	public boolean unfriend(String username1, String username2);

	public Friend getFriendDetail(int friendId);

	public Friend checkIfFriends(String username1, String username2, boolean ignoreStatus);
}
