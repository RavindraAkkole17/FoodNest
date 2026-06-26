package com.tap.DAO;

import java.util.List;

import com.tap.Model.User;

public interface UserDAO {
	int addUser(User u);
	void updateUser(User u);
	void deleteUser(int id);
	User getUser(int id);
	List<User> getAllUser();
	
}
