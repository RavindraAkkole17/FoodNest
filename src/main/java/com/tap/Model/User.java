package com.tap.Model;

import java.sql.Timestamp;

public class User {

	private int userId;
	private String username;
	private String Password;
	private String email;
	private String address;
	private String role;
	Timestamp createdDate;
	Timestamp lastLoginDate;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return username;
	}
	public void setUserName(String userName) {
		this.username = userName;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public Timestamp getLastLoginDate() {
		return lastLoginDate;
	}
	public void setLastLoginDate(Timestamp lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	public User() {
		// TODO Auto-generated constructor stub
	}
	public User(String username, String password, String email, String address, String role) {
		super();
		this.username = username;
		Password = password;
		this.email = email;
		this.address = address;
		this.role = role;
	}
	
	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + username + ", Password=" + Password + ", email=" + email
				+ ", address=" + address + ", role=" + role + ", createdDate=" + createdDate + ", lastLoginDate="
				+ lastLoginDate + "]";
	}
	public User(int userId, String username, String password, String email, String address, String role,
			Timestamp createdDate, Timestamp lastLoginDate) {
		super();
		this.userId = userId;
		this.username = username;
		Password = password;
		this.email = email;
		this.address = address;
		this.role = role;
		this.createdDate = createdDate;
		this.lastLoginDate = lastLoginDate;
	}

}
