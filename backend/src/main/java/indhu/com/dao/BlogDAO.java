package indhu.com.dao;

import java.util.List;

import javax.persistence.OrderBy;

import indhu.com.model.Blog;

public interface BlogDAO {

	public boolean addBlog(Blog blog);

	public boolean deleteBlog(Blog blog);

	public boolean updateBlog(Blog blog);

	public boolean approveBlog(Blog blog);

	public boolean rejectBlog(Blog blog);

	public boolean incrementLike(Blog blog);

	public boolean incrementDislike(Blog blog);

	public boolean decrementLike(Blog blog);

	public boolean decrementDislike(Blog blog);

	public List<Blog> getBlogList();

	public List<Blog> getUserBlogList(String username);
	
	public List<Blog> getLimitedBlogList(String username, int startRowNum, int endRowNum);

	public Blog getBlog(int blogId);
	
	public List<Blog> blogSearch(String queryText);
}
