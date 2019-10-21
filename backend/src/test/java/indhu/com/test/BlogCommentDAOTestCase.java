package indhu.com.test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import indhu.com.dao.BlogCommentDAO;
import indhu.com.model.BlogComment;

public class BlogCommentDAOTestCase {
	static BlogCommentDAO blogCommentDAO;

	@BeforeClass
	public static void executeFirst() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.scan("indhu.com");
		context.refresh();
		blogCommentDAO = (BlogCommentDAO) context.getBean("blogCommentDAO");
	}
	
	
	@Test
	public void addBlogCommentTest() {
		BlogComment blogComment = new BlogComment();
		blogComment.setBlogId(2);
		blogComment.setCommentDate(new java.util.Date());
		blogComment.setCommentMessage("My second comment");
		blogComment.setUsername("aaa");
		assertTrue("Problem adding blog comment", blogCommentDAO.addBlogComment(blogComment));
	}
	
/*	@Ignore
	@Test
	public void deleteBlogCommentTest() {
		BlogComment blogComment = blogCommentDAO.getBlogComment(2);
		assertTrue("Problem deleting blog comment", blogCommentDAO.deleteBlogComment(blogComment));
	}
	
	@Ignore
	@Test
	public void editBlogCommentTest() {
		BlogComment blogComment = blogCommentDAO.getBlogComment(80);
		blogComment.setCommentMessage("Edited comment");
		assertTrue("Problem editing blog comment", blogCommentDAO.editBlogComment(blogComment));
	} */
	
	
	@Test
	public void listBlogCommentsTest() {
		List<BlogComment> blogCommentList = blogCommentDAO.getBlogCommentList(95);
		for(BlogComment bc : blogCommentList)
		{
			System.out.print("\n"+bc.getCommentId()+"\t");
			System.out.println(bc.getCommentMessage());
		}
	}
}