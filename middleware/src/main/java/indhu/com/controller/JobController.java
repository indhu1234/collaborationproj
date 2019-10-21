package indhu.com.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import indhu.com.dao.JobDAO;
import indhu.com.dao.FriendDAO;
import indhu.com.model.Job;
import indhu.com.model.UserDetail;

@RestController
public class JobController {
	@Autowired
	JobDAO jobDAO;

	@Autowired
	FriendDAO friendDAO;

	@PostMapping(value = "/addJob")
	public String addJob(@Valid @RequestBody Job job) {
		job.setPostedDate(new java.util.Date());

		if (jobDAO.addJob(job)) {
			List<UserDetail> friendList = friendDAO.getFriendList(job.getUsername());
			Gson gson = new Gson();
			return gson.toJson(job);
		} else {
			return null;
		}
	}

	@GetMapping(value = "/deleteJob/{jobId}")
	public String deleteJob(@PathVariable("jobId") int jobId) {
		Job job = jobDAO.getJob(jobId);
		if (jobDAO.deleteJob(job)) {
			{
				Gson gson = new Gson();
				return gson.toJson(job);
			}
		} else {
			return null;
		}
	}

	@PostMapping(value = "/updateJob")
	public String updateJob(@Valid @RequestBody Job job) {
		job.setPostedDate(jobDAO.getJob(job.getJobId()).getPostedDate());
		if (jobDAO.updateJobDetails(job)) {
			Gson gson = new Gson();
			return gson.toJson(job);
		} else {
			return null;
		}
	}

	@GetMapping(value = "/openJob/{jobId}")
	public String openJob(@PathVariable("jobId") int jobId) {
		Job job = jobDAO.getJob(jobId);
		if (jobDAO.openJob(job)) {
			{
				Gson gson = new Gson();
				return gson.toJson(job);
			}
		} else {
			return null;
		}
	}

	@GetMapping(value = "/closeJob/{jobId}")
	public String closeJob(@PathVariable("jobId") int jobId) {
		Job job = jobDAO.getJob(jobId);
		if (jobDAO.closeJob(job)) {
			{
				Gson gson = new Gson();
				return gson.toJson(job);
			}
		} else {
			return null;
		}
	}

	@GetMapping(value = "/getJobList")
	public String getJobList() {
		List<Job> jobList = jobDAO.getJobList();
		if (jobList != null) {
			Gson gson = new Gson();
			return gson.toJson(jobList);
		} else {
			return null;
		}
	}

	@GetMapping(value = "/jobSearch/{queryText}")
	public String jobSearch(@PathVariable("queryText") String queryText) {
		List<Job> jobList = jobDAO.jobSearch(queryText);
		if (jobList != null) {
			Gson gson = new Gson();
			return gson.toJson(jobList);
		} else {
			return null;
		}
	}

	@GetMapping(value = "/getUserJobList/{username}")
	public String getUserJobList(@PathVariable("username") String username) {
		List<Job> jobList = jobDAO.getUserJobList(username);
		if (jobList != null) {
			Gson gson = new Gson();
			return gson.toJson(jobList);
		} else {
			return null;
		}
	}

	@GetMapping(value = "/getLimitedJobList/{username}/{startRowNum}/{endRowNum}")
	public String getLimitedJobList(@PathVariable("username") String username,
			@PathVariable("startRowNum") int startRowNum, @PathVariable("endRowNum") int endRowNum) {
		List<Job> jobList = jobDAO.getLimitedJobList(username, startRowNum, endRowNum);
		if (jobList != null) {
			Gson gson = new Gson();
			return gson.toJson(jobList);
		} else {
			return null;
		}
	}

	@GetMapping(value = "/getJob/{jobId}")
	public String getJob(@PathVariable("jobId") int jobId) {
		Job job = jobDAO.getJob(jobId);
		if (job != null) {
			Gson gson = new Gson();
			return gson.toJson(job);
		} else
			return null;
	}
}
