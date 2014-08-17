package net.fmaylinch.suggest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mongodb.morphia.annotations.Entity;

import java.util.Comparator;

@Entity(value = "users", noClassnameStored = true)
public class User extends Model {

	private String name;
	private String email;
	private String password;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}

	public static final Comparator<? super User> NAME_COMPARATOR = new Comparator<User>() {
		@Override
		public int compare(User u1, User u2) {
			return u1.getName().compareTo(u2.getName());
		}
	};
}
