package indhu.com.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table
public class Job {
	@Id
	@GeneratedValue
	private int jobId;
	@NotBlank(message = "Must not be blank.")
	@Size(min = 10, max = 50, message = "Must contain 10 to 50 characters.")
	private String jobProfile;
	@NotBlank(message = "Must not be blank.")
	@Size(min = 10, max = 1000, message = "Must contain 10 to 1000 characters.")
	private String jobDescription;
	@NotBlank(message = "Must not be blank.")
	@Size(min = 1, max = 50, message = "Must contain 1 to 50 characters.")
	private String jobDesignation;
	@NotBlank(message = "Must not be blank.")
	@Size(min = 10, max = 100, message = "Must contain 10 to 100 characters.")
	private String qualificationRequired;
	private String jobStatus;
	private String username;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss a")
	private Date postedDate;

	public String getJobDesignation() {
		return jobDesignation;
	}

	public void setJobDesignation(String jobDesignation) {
		this.jobDesignation = jobDesignation;
	}

	public int getJobId() {
		return jobId;
	}

	public void setJobId(int jobId) {
		this.jobId = jobId;
	}

	public String getJobProfile() {
		return jobProfile;
	}

	public void setJobProfile(String jobProfile) {
		this.jobProfile = jobProfile;
	}

	public String getJobDescription() {
		return jobDescription;
	}

	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}

	public String getQualificationRequired() {
		return qualificationRequired;
	}

	public void setQualificationRequired(String qualificationRequired) {
		this.qualificationRequired = qualificationRequired;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	public Date getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
