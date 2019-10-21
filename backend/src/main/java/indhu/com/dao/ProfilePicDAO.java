package indhu.com.dao;

import indhu.com.model.ProfilePic;

public interface ProfilePicDAO {
	public boolean addProfilePic(ProfilePic profilePic);

	public boolean deleteProfilePic(String username);
	
	public boolean updateProfilePic(ProfilePic profilepic);

	public ProfilePic getProfilePic(String username);
}
