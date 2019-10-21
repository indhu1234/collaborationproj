package indhu.com.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class BlogDislike {
	@Id
	@GeneratedValue
	private int blogDislikeId;
	private String username;
	private int blogId;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getBlogId() {
		return blogId;
	}

	public void setBlogId(int blogId) {
		this.blogId = blogId;
	}

	public int getBlogDislikeId() {
		return blogDislikeId;
	}

	public void setBlogDislikeId(int blogDislikeId) {
		this.blogDislikeId = blogDislikeId;
	}

}
