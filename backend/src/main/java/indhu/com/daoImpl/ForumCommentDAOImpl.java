package indhu.com.daoImpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import indhu.com.dao.ForumCommentDAO;
import indhu.com.model.ForumComment;

@Repository("forumCommentDAO")
@Transactional
public class ForumCommentDAOImpl implements ForumCommentDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public boolean addForumComment(ForumComment forumComment) {
		try {
			sessionFactory.getCurrentSession().save(forumComment);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean editForumComment(ForumComment forumComment) {
		try {
			sessionFactory.getCurrentSession().update(forumComment);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean deleteForumComment(ForumComment forumComment) {
		try {
			sessionFactory.getCurrentSession().delete(forumComment);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<ForumComment> getForumCommentList(int forumId) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from ForumComment WHERE forumId = '" + forumId + "'");
		List<ForumComment> forumCommentList = query.list();
		session.close();
		return forumCommentList;
	}

	@Override
	public ForumComment getForumComment(int commentId) {
		Session session = sessionFactory.openSession();
		ForumComment forumComment = session.get(ForumComment.class, commentId);
		session.close();
		return forumComment;
	}

}
