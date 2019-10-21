package indhu.com.dao;

import java.util.List;

import javax.persistence.OrderBy;

import indhu.com.model.Forum;

public interface ForumDAO {

	public boolean addForum(Forum forum);

	public boolean deleteForum(Forum forum);

	public boolean updateForum(Forum forum);

	public boolean approveForum(Forum forum);

	public boolean rejectForum(Forum forum);

	public List<Forum> getForumList();

	public List<Forum> getUserForumList(String username);
	
	public List<Forum> getLimitedForumList(String username, int startRowNum, int endRowNum);

	public Forum getForum(int forumId);
	
	public List<Forum> forumSearch(String queryText);
}
