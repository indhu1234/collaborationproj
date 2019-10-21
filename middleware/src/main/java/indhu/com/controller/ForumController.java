package indhu.com.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import indhu.com.dao.ForumDAO;
import indhu.com.dao.FriendDAO;
import indhu.com.model.Forum;
import indhu.com.model.UserDetail;

@RestController
public class ForumController {
	@Autowired
	ForumDAO forumDAO;
	@Autowired
	FriendDAO friendDAO;

	@PostMapping(value = "/addForum")
	public String addForum(@Valid @RequestBody Forum forum) {
		forum.setCreatedDate(new java.util.Date());

		if (forumDAO.addForum(forum)) {
			List<UserDetail> friendList = friendDAO.getFriendList(forum.getUsername());
			for (UserDetail user : friendList) {
			}
			Gson gson = new Gson();
			return gson.toJson(forum);
		} else {
			return null;
		}
	}

	@GetMapping(value = "/deleteForum/{forumId}")
	public String deleteForum(@PathVariable("forumId") int forumId) {
		Forum forum = forumDAO.getForum(forumId);
		if (forumDAO.deleteForum(forum)) {
			{
				Gson gson = new Gson();
				return gson.toJson(forum);
			}
		} else {
			return null;
		}
	}

	@PostMapping(value = "/updateForum")
	public String updateForum(@Valid @RequestBody Forum forum) {
		forum.setCreatedDate(forumDAO.getForum(forum.getForumId()).getCreatedDate());
		if (forumDAO.updateForum(forum)) {
			Gson gson = new Gson();
			return gson.toJson(forum);
		} else {
			return null;
		}
	}

	@GetMapping(value = "/approveForum/{forumId}")
	public String approveForum(@PathVariable("forumId") int forumId) {
		Forum forum = forumDAO.getForum(forumId);
		if (forumDAO.approveForum(forum)) {
			{
				Gson gson = new Gson();
				return gson.toJson(forum);
			}
		} else {
			return null;
		}
	}

	@GetMapping(value = "/rejectForum/{forumId}")
	public String rejectForum(@PathVariable("forumId") int forumId) {
		Forum forum = forumDAO.getForum(forumId);
		if (forumDAO.rejectForum(forum)) {
			{
				Gson gson = new Gson();
				return gson.toJson(forum);
			}
		} else {
			return null;
		}
	}

	@GetMapping(value = "/getForumList")
	public String getForumList() {
		List<Forum> forumList = forumDAO.getForumList();
		if (forumList != null) {
			Gson gson = new Gson();
			return gson.toJson(forumList);
		} else {
			return null;
		}
	}

	@GetMapping(value = "/forumSearch/{queryText}")
	public String forumSearch(@PathVariable("queryText") String queryText) {
		List<Forum> forumList = forumDAO.forumSearch(queryText);
		if (forumList != null) {
			Gson gson = new Gson();
			return gson.toJson(forumList);
		} else {
			return null;
		}
	}

	@GetMapping(value = "/getUserForumList/{username}")
	public String getUserForumList(@PathVariable("username") String username) {
		List<Forum> forumList = forumDAO.getUserForumList(username);
		if (forumList != null) {
			Gson gson = new Gson();
			return gson.toJson(forumList);
		} else {
			return null;
		}
	}

	@GetMapping(value = "/getLimitedForumList/{username}/{startRowNum}/{endRowNum}")
	public String getLimitedForumList(@PathVariable("username") String username,
			@PathVariable("startRowNum") int startRowNum, @PathVariable("endRowNum") int endRowNum) {
		List<Forum> forumList = forumDAO.getLimitedForumList(username, startRowNum, endRowNum);
		if (forumList != null) {
			Gson gson = new Gson();
			return gson.toJson(forumList);
		} else {
			return null;
		}
	}

	@GetMapping(value = "/getForum/{forumId}")
	public String getForum(@PathVariable("forumId") int forumId) {
		Forum forum = forumDAO.getForum(forumId);
		if (forum != null) {
			Gson gson = new Gson();
			return gson.toJson(forum);
		} else
			return null;
	}

}
