package indhu.com.test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import indhu.com.dao.UserDAO;
import indhu.com.model.UserDetail;

public class UserDAOTestCase {
	static UserDAO userDAO;

	@BeforeClass
	public static void executeFirst() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.scan("indhu.com");
		context.refresh();
		userDAO = (UserDAO) context.getBean("userDAO");
	}

	//@Ignore
	@Test
	public void addUserTest() {
		UserDetail user = new UserDetail();
		user.setUsername("john");
		user.setPassword("john123");
		user.setFirstName("john");
		user.setLastName("cena");
		user.setEmail("johncena@wwe.com");
		user.setRole("student");
		user.setIsOnline("Off");
		user.setStatus("A");
		assertTrue("Problem adding user", userDAO.addUser(user));
	}

	@Ignore
	@Test
	public void deleteUserTest() {
		UserDetail user = userDAO.getUser("sss");
		assertTrue("Problem deleting user", userDAO.deleteUser(user));
	}

	@Ignore
	@Test
	public void updateUserTest() {
		UserDetail user = userDAO.getUser("admin");
		user.setRole("Admin");
		assertTrue("Problem updating user", userDAO.updateUser(user));
	}

	@Ignore
	@Test
	public void approveUserTest() {
		UserDetail user = userDAO.getUser("lokish");
		assertTrue("Problem approving user", userDAO.approveUser(user));
	}

	@Ignore
	@Test
	public void rejectUserTest() {
		UserDetail user = userDAO.getUser("lokish12");
		assertTrue("Problem rejecting user", userDAO.rejectUser(user));
	}

	@Ignore
	@Test
	public void getUserTest() {
		UserDetail user = userDAO.getUser("f4");
		assertTrue("Problem retrieving user", userDAO.rejectUser(user));
		System.out.println(user.getFirstName() + "," + user.getEmail());
	}

	@Ignore
	@Test
	public void getUserListTest() {
		List<UserDetail> userList = userDAO.getUserList();
		for (UserDetail user : userList) {
			System.out.println(user.getUsername());
		}
	}
}