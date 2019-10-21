package indhu.com.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class BlogLike {
	@Id
	@GeneratedValue
	private int blogLikeId;
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

	public int getBlogLikeId() {
		return blogLikeId;
	}

	public void setBlogLikeId(int blogLikeId) {
		this.blogLikeId = blogLikeId;
	}
	
	
}
