package models.records;

import java.util.Date;
import java.util.List;

public class User {
	private int id;
	private String name;
	private String email;
	private String password;
	private Date birthDate;
	private String address;
	private byte permission;	
	
	public User() {
		this.id = 0;
		this.name = "";
		this.email = "";
		this.password = "";
		this.birthDate = null;
		this.address = "";
		this.permission = 0;		
	}
	
	public User(int id, String name, String email, String password, Date birthDate, String address, byte permission) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.birthDate = birthDate;
		this.address = address;
		this.permission = permission;		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public byte getPermission() {
		return permission;
	}

	public void setPermission(byte permission) {
		this.permission = permission;
	}
	
}
