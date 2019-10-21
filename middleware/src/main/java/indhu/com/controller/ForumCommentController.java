package indhu.com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import indhu.com.dao.ForumCommentDAO;
import indhu.com.dao.ForumDAO;
import indhu.com.model.ForumComment;

@RestController
public class ForumCommentController {

	@Autowired
	ForumCommentDAO forumCommentDAO;
	@Autowired
	ForumDAO forumDAO;

	@PostMapping(value = "/addForumComment")
	public String addForumComment(@RequestBody ForumComment forumComment) {
		// the following line is just for testing. delete it after completing frontend
		forumComment.setCommentDate(new java.util.Date());
		if (forumCommentDAO.addForumComment(forumComment)) {
			Gson gson = new Gson();
			return gson.toJson(forumComment);
		} else {
			return null;
		}
	}

	@GetMapping(value = "/deleteForumComment/{forumCommentId}")
	public String deleteForumComment(@PathVariable("forumCommentId") int forumCommentId) {
		ForumComment forumComment = forumCommentDAO.getForumComment(forumCommentId);
		if (forumCommentDAO.deleteForumComment(forumComment)) {
			Gson gson = new Gson();
			return gson.toJson(forumComment);
		} else {
			return null;
		}
	}

	@PostMapping(value = "/editForumComment")
	public String editForumComment(@RequestBody ForumComment forumComment) {
		forumComment.setCommentDate(forumCommentDAO.getForumComment(forumComment.getCommentId()).getCommentDate());
		if (forumCommentDAO.editForumComment(forumComment)) {
			{
				Gson gson = new Gson();
				return gson.toJson(forumComment);
			}
		} else {
			return null;
		}
	}

	@GetMapping(value = "/getForumComment/{forumCommentId}")
	public String getForumComment(@PathVariable("forumCommentId") int forumCommentId) {
		ForumComment forumComment = forumCommentDAO.getForumComment(forumCommentId);
		if (forumComment != null) {
			Gson gson = new Gson();
			return gson.toJson(forumComment);
		} else {
			return null;
		}
	}

	@GetMapping(value = "/getForumCommentList/{forumId}")
	public String getForumCommentList(@PathVariable("forumId") int forumId) {
		List<ForumComment> forumCommentList = forumCommentDAO.getForumCommentList(forumId);
		if (forumCommentList != null) {
			{
				Gson gson = new Gson();
				return gson.toJson(forumCommentList);
			}
		} else {
			return null;
		}
	}
}
