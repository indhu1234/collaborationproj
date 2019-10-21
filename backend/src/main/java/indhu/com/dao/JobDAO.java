package indhu.com.dao;

import java.util.List;

import indhu.com.model.Blog;
import indhu.com.model.Job;

public interface JobDAO {
	public boolean addJob(Job job);

	public boolean deleteJob(Job job);

	public boolean updateJobDetails(Job job);

	public boolean openJob(Job job);

	public boolean closeJob(Job job);

	public Job getJob(int jobId);

	public List<Job> getJobList();
	
	public List<Job> getUserJobList(String username);
	
	public List<Job> jobSearch(String queryText);

	public List<Job> getLimitedJobList(String username, int startRowNum, int endRowNum);
}