package indhu.com.daoImpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import indhu.com.dao.JobDAO;
import indhu.com.model.Blog;
import indhu.com.model.Friend;
import indhu.com.model.Job;

@Repository("jobDAO")
@Transactional
public class JobDAOImpl implements JobDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public boolean addJob(Job job) {
		try {
			sessionFactory.getCurrentSession().save(job);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteJob(Job job) {
		try {
			sessionFactory.getCurrentSession().delete(job);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean updateJobDetails(Job job) {
		try {
			sessionFactory.getCurrentSession().update(job);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean openJob(Job job) {
		try {
			job.setJobStatus("open");
			sessionFactory.getCurrentSession().update(job);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean closeJob(Job job) {
		try {
			job.setJobStatus("closed");
			sessionFactory.getCurrentSession().update(job);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Job getJob(int jobId) {
		Session session = sessionFactory.openSession();
		Job job = session.get(Job.class, jobId);
		session.close();
		return job;
	}

	@Override
	public List<Job> getJobList() {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Job");
		List<Job> jobList = query.list();
		session.close();
		return jobList;
	}
	
	@Override
	public List<Job> getUserJobList(String username) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Job WHERE username = '" + username + "'");
		List<Job> jobList = query.list();
		session.close();
		return jobList;
	}

	@Override
	public List<Job> getLimitedJobList(String username, int startRowNum, int endRowNum) {
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
				.createSQLQuery("select * from ( select a.*, ROWNUM rnum from ( select * from job where (username IN ("
						+ usernameList + ") AND jobStatus = 'open') order by postedDate DESC) a where ROWNUM <= "
						+ endRowNum + ") where rnum >= " + startRowNum)
				.addEntity(Job.class);
		List<Job> jobList = query.list();
		session.close();
		return jobList;
	}

	@Override
	public List<Job> jobSearch(String queryText) {
		String queryTextLower = queryText.toLowerCase();
		String queryTextUpper = Character.toUpperCase(queryTextLower.charAt(0)) + queryTextLower.substring(1);
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Job where jobDesignation LIKE '%" + queryTextLower
				+ "%' or jobDesignation LIKE '%" + queryTextUpper + "%' or jobDescription LIKE '%" + queryTextLower
				+ "%' or jobDescription LIKE '%" + queryTextUpper + "%' or jobProfile LIKE '%" + queryTextLower
				+ "%' or jobProfile LIKE '%" + queryTextUpper + "%'");
		List<Job> jobList = query.list();
		session.close();
		return jobList;
	}	
}
