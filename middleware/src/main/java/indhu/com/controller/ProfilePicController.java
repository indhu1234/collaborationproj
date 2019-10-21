package indhu.com.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.google.gson.Gson;
import indhu.com.dao.ProfilePicDAO;
import indhu.com.model.ProfilePic;
import indhu.com.model.UserDetail;

@RestController
public class ProfilePicController {
	@Autowired
	ProfilePicDAO profilePicDAO;

	@PostMapping(value = "/addProfilePic")
	public void addProfilePic(@RequestParam(value = "profilePic") CommonsMultipartFile file, HttpSession session) {
		UserDetail user = (UserDetail) session.getAttribute("userDetail");
		if (user == null)
			System.out.println("user is null");
		if (file == null) {
			System.out.println("null image");
		} else {
			if (file.getSize() > 100000) {
				System.out.println("size exceeded");
			} else if (file.getSize() == 0) {
				System.out.println("size = 0");
			} else {
				ProfilePic profilePic = new ProfilePic();
				profilePic.setUsername(user.getUsername());
				profilePic.setImage(file.getBytes());
				profilePicDAO.addProfilePic(profilePic);
				System.out.println("Addeded profilePic");
			}
		}
	}

	@PostMapping(value = "/updateProfilePic")
	public void updateProfilePic(@RequestParam(value = "updateProfilePic") CommonsMultipartFile file, HttpSession session) {
		UserDetail userDetail = (UserDetail) session.getAttribute("userDetail");
		if (file == null) {
			System.out.println("Please select an image.");
		} else {
			if (file.getSize() > 100000)
				System.out.println("Image size must not exceed 1Mb.");
			else if (file.getSize() == 0)
				System.out.println("Please select a valid image.");
			else {
				ProfilePic profilePic = profilePicDAO.getProfilePic(userDetail.getUsername());
				profilePic.setImage(file.getBytes());
				profilePicDAO.updateProfilePic(profilePic);
			}
		}
	}

	@GetMapping(value = "/deleteProfilePic/{username}")
	public String deleteProfilePic(@PathVariable("username") String username) {
		if (profilePicDAO.deleteProfilePic(username)) {
			Gson gson = new Gson();
			return gson.toJson("Profile picture deleted");
		} else
			return "Error deleting Profile picture";
	}

	@RequestMapping(value = "/showProfilePic/{username}", method = RequestMethod.GET)
	public @ResponseBody byte[] showProfilePic(@PathVariable("username") String username) {
		ProfilePic profilePic = profilePicDAO.getProfilePic(username);
		if (profilePic != null)
			return profilePic.getImage();
		else
			return null;
	}

	@GetMapping(value = "/getProfilePic/{username}")
	public String getProfilePic(@PathVariable("username") String username) {
		ProfilePic profilePic = profilePicDAO.getProfilePic(username);

		if (profilePic != null) {
			Gson gson = new Gson();
			return gson.toJson(profilePic);
		} else
			return null;
	}
}