package play.yabe.models;

import play.modules.ebean.BaseModel;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class User extends BaseModel {

	@NotNull
	private String email;

	@NotNull
	private String password;

	private String fullname;

	private boolean isAdmin;

	public User(String email, String password, String fullname) {
		this.email = email;
		this.password = password;
		this.fullname = fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean admin) {
		isAdmin = admin;
	}

	public static User connect(String email, String password) {
		return db().createQuery(User.class)
			.where()
			.raw("email=? and password=?", email, password)
			.findUnique()
		;
	}

	public String toString() {
		return email;
	}
}
