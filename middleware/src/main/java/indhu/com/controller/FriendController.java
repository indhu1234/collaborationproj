package indhu.com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import indhu.com.dao.FriendDAO;
import indhu.com.model.Friend;
import indhu.com.model.UserDetail;

@RestController
public class FriendController {
	@Autowired
	FriendDAO friendDAO;

	@PostMapping(value = "/sendFriendReq")
	public String sendFriendReq(@RequestBody Friend friend) {
		if (friendDAO.sendFriendReq(friend)) {
			Gson gson = new Gson();
			return gson.toJson(friend);
		} else {
			return null;
		}
	}

	@GetMapping(value = "/acceptFriendReq/{friendReqId}")
	public String acceptFriendReq(@PathVariable("friendReqId") int friendId) {
		if (friendDAO.acceptFriendReq(friendId)) {
			Gson gson = new Gson();
			return gson.toJson(friendDAO.getFriendDetail(friendId));
		} else {
			return null;
		}
	}

	@GetMapping(value = "/rejectFriendReq/{friendReqId}")
	public String deleteFriendReq(@PathVariable("friendReqId") int friendId) {
		if (friendDAO.deleteFriendReq(friendId)) {
			Gson gson = new Gson();
			return gson.toJson(friendDAO.deleteFriendReq(friendId));
		} else {
			return null;
		}
	}

	@GetMapping(value = "/unfriend/{username1}/{username2}")
	public String unfriend(@PathVariable("username1") String username1, @PathVariable("username2") String username2) {
		if (friendDAO.unfriend(username1, username2)) {
			Gson gson = new Gson();
			return gson.toJson(true);
		} else {
			return null;
		}
	}

	@GetMapping(value = "/listFriends/{username}")
	public String getFriendList(@PathVariable("username") String username) {
		List<UserDetail> friendList = friendDAO.getFriendList(username);
		if (friendList != null) {
			Gson gson = new Gson();
			return gson.toJson(friendList);
		} else {
			return null;
		}
	}

	@GetMapping(value = "/listPendingFriends/{username}")
	public String getPendingFriendList(@PathVariable("username") String username) {
		List<Friend> pendingFriendList = friendDAO.getPendingFriends(username);
		if (pendingFriendList != null) {
			Gson gson = new Gson();
			return gson.toJson(pendingFriendList);
		} else {
			return null;
		}
	}

	@GetMapping(value = "/listSuggestedFriends/{username}")
	public String getSuggestedFriendList(@PathVariable("username") String username) {
		List<UserDetail> suggestedFriendList = friendDAO.getSuggestedFriends(username);
		if (suggestedFriendList != null) {
			Gson gson = new Gson();
			return gson.toJson(suggestedFriendList);
		} else
			return null;
	}

	@GetMapping(value = "/checkIfFriends/{username}/{friendUsername}")
	public String getSuggestedFriendList(@PathVariable("username") String username,
			@PathVariable("friendUsername") String friendUsername) {
		Friend friend = friendDAO.checkIfFriends(username, friendUsername, true);
		if (friend != null) {
			Gson gson = new Gson();
			return gson.toJson(friend);
		} else
			return null;
	}
}
