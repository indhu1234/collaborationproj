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
import indhu.com.dao.BlogDAO;
import indhu.com.dao.BlogLikeDislikeDAO;
import indhu.com.dao.FriendDAO;
import indhu.com.model.Blog;
import indhu.com.model.UserDetail;

@RestController
public class BlogController {
	@Autowired
	BlogDAO blogDAO;
	@Autowired
	BlogLikeDislikeDAO blogLikeDislikeDAO;
	@Autowired
	FriendDAO friendDAO;

	@PostMapping(value = "/addBlog")
	public String addBlog(@Valid @RequestBody Blog blog) {
		blog.setCreatedDate(new java.util.Date());

		if (blogDAO.addBlog(blog)) {
			Gson gson = new Gson();
			return gson.toJson(blog);
		} else {
			return null;
		}
	}

	@GetMapping(value = "/deleteBlog/{blogId}")
	public String deleteBlog(@PathVariable("blogId") int blogId) {
		Blog blog = blogDAO.getBlog(blogId);
		if (blogDAO.deleteBlog(blog)) {
			{
				Gson gson = new Gson();
				return gson.toJson(blog);
			}
		} else {
			return null;
		}
	}

	@PostMapping(value = "/updateBlog")
	public String updateBlog(@Valid @RequestBody Blog blog) {
		blog.setCreatedDate(blogDAO.getBlog(blog.getBlogId()).getCreatedDate());
		if (blogDAO.updateBlog(blog)) {
			Gson gson = new Gson();
			return gson.toJson(blog);
		} else {
			return null;
		}
	}

	@GetMapping(value = "/approveBlog/{blogId}")
	public String approveBlog(@PathVariable("blogId") int blogId) {
		Blog blog = blogDAO.getBlog(blogId);
		if (blogDAO.approveBlog(blog)) {
			{
				Gson gson = new Gson();
				return gson.toJson(blog);
			}
		} else {
			return null;
		}
	}

	@GetMapping(value = "/rejectBlog/{blogId}")
	public String rejectBlog(@PathVariable("blogId") int blogId) {
		Blog blog = blogDAO.getBlog(blogId);
		if (blogDAO.rejectBlog(blog)) {
			{
				Gson gson = new Gson();
				return gson.toJson(blog);
			}
		} else {
			return null;
		}
	}

	@GetMapping(value = "/getBlogList")
	public String getBlogList() {
		List<Blog> blogList = blogDAO.getBlogList();
		if (blogList != null) {
			Gson gson = new Gson();
			return gson.toJson(blogList);
		} else {
			return null;
		}
	}

	@GetMapping(value = "/blogSearch/{queryText}")
	public String blogSearch(@PathVariable("queryText") String queryText) {
		List<Blog> blogList = blogDAO.blogSearch(queryText);
		if (blogList != null) {
			Gson gson = new Gson();
			return gson.toJson(blogList);
		} else {
			return null;
		}
	}

	@GetMapping(value = "/getUserBlogList/{username}")
	public String getUserBlogList(@PathVariable("username") String username) {
		List<Blog> blogList = blogDAO.getUserBlogList(username);
		if (blogList != null) {
			Gson gson = new Gson();
			return gson.toJson(blogList);
		} else {
			return null;
		}
	}

	@GetMapping(value = "/getLimitedBlogList/{username}/{startRowNum}/{endRowNum}")
	public String getLimitedBlogList(@PathVariable("username") String username,
			@PathVariable("startRowNum") int startRowNum, @PathVariable("endRowNum") int endRowNum) {
		List<Blog> blogList = blogDAO.getLimitedBlogList(username, startRowNum, endRowNum);
		if (blogList != null) {
			Gson gson = new Gson();
			return gson.toJson(blogList);
		} else {
			return null;
		}
	}

	@GetMapping(value = "/getBlog/{blogId}")
	public String getBlog(@PathVariable("blogId") int blogId) {
		Blog blog = blogDAO.getBlog(blogId);
		if (blog != null) {
			Gson gson = new Gson();
			return gson.toJson(blog);
		} else
			return null;
	}

	@GetMapping(value = "/incrementLike/{blogId}")
	public String incrementLike(@PathVariable("blogId") int blogId) {
		Blog blog = blogDAO.getBlog(blogId);
		if (blogDAO.incrementLike(blog)) {
			Gson gson = new Gson();
			return gson.toJson(blog);
		} else {
			return null;
		}
	}

	@GetMapping(value = "/incrementDislike/{blogId}")
	public String incrementDislike(@PathVariable("blogId") int blogId) {
		Blog blog = blogDAO.getBlog(blogId);
		if (blogDAO.incrementDislike(blog)) {
			Gson gson = new Gson();
			return gson.toJson(blog);
		} else {
			return null;
		}
	}

	@GetMapping(value = "/decrementLike/{blogId}")
	public String decrementLike(@PathVariable("blogId") int blogId) {
		Blog blog = blogDAO.getBlog(blogId);
		if (blogDAO.decrementLike(blog)) {
			Gson gson = new Gson();
			return gson.toJson(blog);
		} else {
			return null;
		}
	}

	@GetMapping(value = "/decrementDislike/{blogId}")
	public String decrementDislike(@PathVariable("blogId") int blogId) {
		Blog blog = blogDAO.getBlog(blogId);
		if (blogDAO.decrementDislike(blog)) {
			Gson gson = new Gson();
			return gson.toJson(blog);
		} else {
			return null;
		}
	}

}
