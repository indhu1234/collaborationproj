package indhu.com.dao;

import java.util.List;

import indhu.com.model.UserDetail;

public interface UserDAO {
	public boolean addUser(UserDetail user);

	public boolean deleteUser(UserDetail user);

	public boolean updateUser(UserDetail user);

	public UserDetail getUser(String username);

	public boolean approveUser(UserDetail user);

	public boolean rejectUser(UserDetail user);

	public List<UserDetail> getUserList();

	public List<UserDetail> userSearch(String queryText);
}
