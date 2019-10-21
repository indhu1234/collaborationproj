package indhu.com.test;

import static org.junit.Assert.assertTrue;


import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


import indhu.com.dao.BlogDAO;
import indhu.com.dao.UserDAO;
import indhu.com.model.Blog;

import junit.framework.TestCase;

public class BlogDAOTestCase {

	static BlogDAO blogDAO;
	static UserDAO userDAO;

	@BeforeClass
	public static void executeFirst() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.scan("indhu.com");
		context.refresh();
		blogDAO = (BlogDAO) context.getBean("BlogDAO");
	}

	
	@Test
	public void addBlogTest() {
		Blog blog = new Blog();
		blog.setBlogTitle("Test");
		blog.setBlogContent("This is the sample of blog 3");
		blog.setCreatedDate(new Date());
		blog.setStatus("A");
		blog.setUsername("ccc");
		blog.setLikes(0);
		blog.setDislikes(0);
		assertTrue("Problem adding blog", blogDAO.addBlog(blog));
	}

	@Ignore
	@Test
	public void deleteBlogTest() {
		Blog blog = blogDAO.getBlog(3);
		assertTrue("Problem deleting blog", blogDAO.deleteBlog(blog));
	}

	@Ignore
	@Test
	public void updateBlogTest() {
		Blog blog = blogDAO.getBlog(41);
		blog.setCreatedDate(new java.util.Date());
		assertTrue("Problem updating blog", blogDAO.updateBlog(blog));
	}

	@Ignore
	@Test
	public void approveBlogTest() {
		Blog blog = blogDAO.getBlog(3);
		assertTrue("Problem approving blog", blogDAO.approveBlog(blog));
	}

	@Ignore
	@Test
	public void rejectBlogTest() {
		Blog blog = blogDAO.getBlog(3);
		assertTrue("Problem rejecting blog", blogDAO.rejectBlog(blog));
	}

	@Ignore
	@Test
	public void incrementLikesTest() {
		Blog blog = blogDAO.getBlog(23);
		assertTrue("Problem incrementing blog like", blogDAO.incrementLike(blog));
	}

	@Ignore
	@Test
	public void incrementDislikesTest() {
		Blog blog = blogDAO.getBlog(23);
		assertTrue("Problem incrementing blog like", blogDAO.incrementDislike(blog));
	}

	@Ignore
	@Test
	public void decrementLikesTest() {
		Blog blog = blogDAO.getBlog(23);
		assertTrue("Problem incrementing blog like", blogDAO.decrementLike(blog));
	}

	@Ignore
	@Test
	public void decrementDislikesTest() {
		Blog blog = blogDAO.getBlog(23);
		assertTrue("Problem incrementing blog like", blogDAO.decrementDislike(blog));
	}

	@Ignore
	@Test
	public void listBlogsTest() {
		List<Blog> blogList = blogDAO.getBlogList();
		for (Blog blog : blogList) {
			System.out.print(blog.getBlogId() + "\t");
			System.out.print(blog.getBlogTitle() + "\t");
			System.out.println(blog.getBlogContent() + "\n");
		}
	}
	
	@Ignore
	@Test
	public void getLimitedBlogListTest() {
		List<Blog> blogList = blogDAO.getLimitedBlogList("aaa",0, 2);
		for (Blog blog : blogList) {
			System.out.print(blog.getBlogTitle() + "\t");
			System.out.println(blog.getCreatedDate() + "\n");
		}
	}
	
	@Ignore
	@Test
	public void getBlogTest() {
		Blog blog=blogDAO.getBlog(501);
		System.out.println(blog.getBlogId());
		System.out.println(blog.getCreatedDate().toString());
		System.out.println(blog.getCreatedDate());
	}
}