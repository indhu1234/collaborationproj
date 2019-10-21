package indhu.com.test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import indhu.com.dao.ForumDAO;
import indhu.com.model.Forum;

public class ForumDAOTestCase {

	static ForumDAO forumDAO;

	@BeforeClass
	public static void executeFirst() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.scan("indhu.com");
		context.refresh();
		forumDAO = (ForumDAO) context.getBean("forumDAO");
	}

	@Ignore
	@Test
	public void addForumTest() {
		for (int i = 0; i < 50; i++) {
			Forum forum = new Forum();
			forum.setCreatedDate(new java.util.Date());
			forum.setForumTitle("Forum title " + (i + 1));
			forum.setForumContent("Forum content " + (i + 1));
			forum.setStatus("A");
			forum.setUsername("john");
			assertTrue("Problem adding forum", forumDAO.addForum(forum));
		}
	}

	@Ignore
	@Test
	public void deleteForumTest() {
		Forum forum = forumDAO.getForum(7);
		assertTrue("Problem deleting forum", forumDAO.deleteForum(forum));
	}

	@Ignore
	@Test
	public void updateForumTest() {
		Forum forum = forumDAO.getForum(8);
		forum.setForumContent("This is the changed forum content");
		assertTrue("Problem updating forum", forumDAO.updateForum(forum));
	}

	@Ignore
	@Test
	public void approveForumTest() {
		Forum forum = forumDAO.getForum(3);
		assertTrue("Problem approving forum", forumDAO.approveForum(forum));
	}

	@Ignore
	@Test
	public void rejectForumTest() {
		Forum forum = forumDAO.getForum(3);
		assertTrue("Problem rejecting forum", forumDAO.rejectForum(forum));
	}

	@Ignore
	@Test
	public void listForumsTest() {
		List<Forum> forumList = forumDAO.getForumList();
		for (Forum forum : forumList) {
			System.out.print("\n" + forum.getForumId() + "\t");
			System.out.print(forum.getForumTitle() + "\t");
			System.out.println(forum.getStatus());
		}
	}

	@Ignore
	@Test
	public void getForumListTest() {
		List<Forum> forumList = forumDAO.getForumList();
		for (Forum forum : forumList) {
			System.out.print(forum.getForumId() + "\t");
			System.out.print(forum.getForumTitle() + "\t");
			System.out.println(forum.getForumContent() + "\n");
		}
	}

	@Ignore
	@Test
	public void getUserForumListTest() {
		List<Forum> forumList = forumDAO.getUserForumList("michaelj");
		for (Forum forum : forumList) {
			System.out.print(forum.getForumId() + "\t");
			System.out.print(forum.getForumTitle() + "\t");
			System.out.println(forum.getForumContent() + "\n");
		}
	}

	@Ignore
	@Test
	public void getLimitedForumListTest() {
		List<Forum> forumList = forumDAO.getLimitedForumList("michaelj", 0, 2);
		for (Forum forum : forumList) {
			System.out.print(forum.getForumTitle() + "\t");
			System.out.println(forum.getCreatedDate() + "\n");
		}
	}

	@Ignore
	@Test
	public void getForumTest() {
		Forum forum = forumDAO.getForum(501);
		System.out.println(forum.getForumId());
		System.out.println(forum.getCreatedDate().toString());
		System.out.println(forum.getCreatedDate());
	}

	@Ignore
	@Test
	public void forumSearchTest() {
		List<Forum> forumList = forumDAO.forumSearch("aaa");
		for (Forum forum : forumList) {
			System.out.println(forum.getForumTitle() + "\t");
		}
	}
}
