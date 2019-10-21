package indhu.com.daoImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import indhu.com.dao.BlogLikeDislikeDAO;
import indhu.com.model.BlogDislike;
import indhu.com.model.BlogLike;

@Repository("blogLikeDislikeDAO")
@Transactional
public class BlogLikeDislikeDAOImpl implements BlogLikeDislikeDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public boolean addBlogLike(BlogLike like) {
		try {
			sessionFactory.getCurrentSession().save(like);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean removeBlogLike(BlogLike like) {
		try {
			sessionFactory.getCurrentSession().delete(like);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean addBlogDislike(BlogDislike dislike) {
		try {
			sessionFactory.getCurrentSession().save(dislike);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean removeBlogDislike(BlogDislike dislike) {
		try {
			sessionFactory.getCurrentSession().delete(dislike);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public BlogLike getBlogLikeById(int likeId) {
		Session session = sessionFactory.openSession();
		BlogLike like = session.get(BlogLike.class, likeId);
		session.close();
		return like;
	}

	@Override
	public BlogDislike getBlogDislikeById(int dislikeId) {
		Session session = sessionFactory.openSession();
		BlogDislike dislike = session.get(BlogDislike.class, dislikeId);
		session.close();
		return dislike;
	}

	@Override
	public BlogLike getBlogLikeByUser(String username, int blogId) {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(BlogLike.class);
		criteria.add(Restrictions.eq("username", username));
		criteria.add(Restrictions.eq("blogId", blogId));
		List list = criteria.list();
		session.close();
		if (list.size() != 0)
			return (BlogLike) list.get(0);
		else
			return null;
	}

	@Override
	public BlogDislike getBlogDislikeByUser(String username, int blogId) {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(BlogDislike.class);
		criteria.add(Restrictions.eq("username", username));
		criteria.add(Restrictions.eq("blogId", blogId));
		List list = criteria.list();
		session.close();
		if (list.size() != 0)
			return (BlogDislike) list.get(0);
		else
			return null;
	}

	@Override
	public List<BlogLike> getBlogLikesList(String username) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from LikeDetail WHERE username = '" + username + "'");
		List<BlogLike> likesList = query.list();
		session.close();
		return likesList;
	}

	@Override
	public List<BlogDislike> getBlogDislikesList(String username) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Dislike WHERE username = '" + username + "'");
		List<BlogDislike> dislikesList = query.list();
		session.close();
		return dislikesList;
	}
}
