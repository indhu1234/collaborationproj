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
import indhu.com.dao.BlogCommentDAO;
import indhu.com.dao.BlogDAO;
import indhu.com.model.BlogComment;

@RestController
public class BlogCommentController {

	@Autowired
	BlogCommentDAO blogCommentDAO;
	@Autowired
	BlogDAO blogDAO;

	@PostMapping(value = "/addBlogComment")
	public String addBlogComment(@RequestBody BlogComment blogComment) {
		// the following line is just for testing. delete it after completing frontend
		blogComment.setCommentDate(new java.util.Date());
		if (blogCommentDAO.addBlogComment(blogComment)) {
			Gson gson = new Gson();
			return gson.toJson(blogComment);
		} else {
			return null;
		}
	}

	@GetMapping(value = "/deleteBlogComment/{blogCommentId}")
	public String deleteBlogComment(@PathVariable("blogCommentId") int blogCommentId) {
		BlogComment blogComment = blogCommentDAO.getBlogComment(blogCommentId);
		if (blogCommentDAO.deleteBlogComment(blogComment)) {
			Gson gson = new Gson();
			return gson.toJson(blogComment);
		} else {
			return null;
		}
	}

	@PostMapping(value = "/editBlogComment")
	public String editBlogComment(@RequestBody BlogComment blogComment) {
		blogComment.setCommentDate(blogCommentDAO.getBlogComment(blogComment.getCommentId()).getCommentDate());
		if (blogCommentDAO.editBlogComment(blogComment)) {
			{
				Gson gson = new Gson();
				return gson.toJson(blogComment);
			}
		} else {
			return null;
		}
	}

	@GetMapping(value = "/getBlogComment/{blogCommentId}")
	public String getBlogComment(@PathVariable("blogCommentId") int blogCommentId) {
		BlogComment blogComment = blogCommentDAO.getBlogComment(blogCommentId);
		if (blogComment != null) {
			Gson gson = new Gson();
			return gson.toJson(blogComment);
		} else
			return null;
	}

	@GetMapping(value = "/getBlogCommentList/{blogId}")
	public String getBlogCommentList(@PathVariable("blogId") int blogId) {
		List<BlogComment> blogCommentList = blogCommentDAO.getBlogCommentList(blogId);
		if (blogCommentList != null) {
			{
				Gson gson = new Gson();
				return gson.toJson(blogCommentList);
			}
		} else {
			return null;
		}
	}
}
