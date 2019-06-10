package models.records;

import java.util.Date;

public class UserBuilder {
	private User user;
	
	public User build() {
		return user;
	}
	
	public UserBuilder() {
		this.user = new User();
	}
	
	public UserBuilder setId(int id) {
		this.user.setId(id);
		return this;
	}
	
	public UserBuilder setName(String name) {
		this.user.setName(name);
		return this;
	}
	
	public UserBuilder setEmail(String email) {
		this.user.setEmail(email);
		return this;
	}
	
	public UserBuilder setPassword(String password) {
		this.user.setPassword(password);
		return this;
	}
	
	public UserBuilder setBirthDate(Date birthDate) {
		this.user.setBirthDate(birthDate);
		return this;
	}
	
	public UserBuilder setAddress(String address) {
		this.user.setAddress(address);
		return this;
	}
	
	public UserBuilder setPermission(byte permission) {
		this.user.setPermission(permission);
		return this;
	}	
	
}
