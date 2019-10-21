package indhu.com.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table
public class UserDetail {
	@Id
	@NotBlank(message = "Must not be blank.")
	@Size(min = 5, max = 10, message = "Must contain 5 to 10 characters.")
	private String username;
	@NotBlank(message = "Must not be blank.")
	@Size(min = 8, max = 12, message = "Must contain 8 to 12 characters.")
	private String password;
	@Transient
	@Size(min = 8, max = 12, message = "Must contain 8 to 12 characters.")
	@NotBlank(message = "Must not be blank.")	
	private String confirmPassword;
	@NotBlank(message = "Must not be blank.")
	@Size(min = 1, max = 10, message = "Must contain 1 to 10 characters.")
	private String firstName;
	@NotBlank(message = "Must not be blank.")
	@Size(min = 1, max = 10, message = "Must contain 1 to 10 characters.")
	private String lastName;
	@NotBlank(message = "Must not be blank.")
	@Email(message = "Must be a well-formed email address.")
	private String email;
	private String role;
	private String status;
	private String isOnline;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(String isOnline) {
		this.isOnline = isOnline;
	}

}
