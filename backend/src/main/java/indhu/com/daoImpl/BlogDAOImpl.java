package indhu.com.daoImpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import indhu.com.dao.BlogDAO;
import indhu.com.model.Blog;
import indhu.com.model.Friend;

@Repository("BlogDAO")
@Transactional
public class BlogDAOImpl implements BlogDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public boolean addBlog(Blog blog) {
		try {
			sessionFactory.getCurrentSession().save(blog);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean deleteBlog(Blog blog) {
		try {
			sessionFactory.getCurrentSession().remove(blog);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateBlog(Blog blog) {
		try {
			sessionFactory.getCurrentSession().update(blog);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean approveBlog(Blog blog) {
		blog.setStatus("A");
		try {
			sessionFactory.getCurrentSession().update(blog);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean rejectBlog(Blog blog) {
		blog.setStatus("R");
		try {
			sessionFactory.getCurrentSession().update(blog);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<Blog> getBlogList() {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Blog");
		List<Blog> blogList = query.list();
		session.close();
		return blogList;
	}

	@Override
	public List<Blog> getUserBlogList(String username) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Blog WHERE username = '" + username + "'");
		List<Blog> blogList = query.list();
		session.close();
		return blogList;
	}

	@Override
	public List<Blog> getLimitedBlogList(String username, int startRowNum, int endRowNum) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Friend where (username = '" + username + "' or friendUsername='"
				+ username + "') and status = 'A'");
		List<Friend> friendList = query.list();
		String usernameList = "''";
		if (friendList.size() > 0) {
			for (Friend friend : friendList) {
				usernameList += "'" + (friend.getFriendUsername().equals(username) ? friend.getUsername()
						: friend.getFriendUsername()) + "',";
			}
			usernameList = usernameList.substring(2, usernameList.length() - 1);
		}
		query = session
				.createSQLQuery("select * from ( select a.*, ROWNUM rnum from ( select * from blog where (username IN ("
						+ usernameList + ") AND status = 'A') order by createddate DESC) a where ROWNUM <= " + endRowNum
						+ ") where rnum >= " + startRowNum)
				.addEntity(Blog.class);
		List<Blog> blogList = query.list();
		session.close();
		return blogList;
	}

	@Override
	public Blog getBlog(int blogId) {
		Session session = sessionFactory.openSession();
		Blog blog = session.get(Blog.class, blogId);
		session.close();
		return blog;
	}

	@Override
	public boolean incrementLike(Blog blog) {
		try {
			blog.setLikes(blog.getLikes() + 1);
			sessionFactory.getCurrentSession().update(blog);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean incrementDislike(Blog blog) {
		try {
			blog.setDislikes(blog.getDislikes() + 1);
			sessionFactory.getCurrentSession().update(blog);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean decrementLike(Blog blog) {
		try {
			blog.setLikes(blog.getLikes() - 1);
			sessionFactory.getCurrentSession().update(blog);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean decrementDislike(Blog blog) {
		try {
			blog.setDislikes(blog.getDislikes() - 1);
			sessionFactory.getCurrentSession().update(blog);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<Blog> blogSearch(String queryText) {
		String queryTextLower = queryText.toLowerCase();
		String queryTextUpper = Character.toUpperCase(queryTextLower.charAt(0)) + queryTextLower.substring(1);
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Blog where blogTitle LIKE '%" + queryTextLower
				+ "%' or blogTitle LIKE '%" + queryTextUpper + "%' or blogContent LIKE '%" + queryTextLower
				+ "%' or blogContent LIKE '%" + queryTextUpper + "%'");
		List<Blog> blogList = query.list();
		session.close();
		return blogList;
	}
}
