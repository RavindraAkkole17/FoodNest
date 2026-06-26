package com.tap.DAOImpl;

import com.tap.Model.User;
import com.tap.utility.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.tap.DAO.UserDAO;

public class UserDAOImpl implements UserDAO{

	private static final String INSERT_QUERY = "INSERT INTO user(username,password,email,address,role,lastLoginDate) VALUES (?,?,?,?,?,?)";
	private static final String SELECT_QUERY = "SELECT * FROM user WHERE userId = ?";
	private static final String SELECT_QUERY2 = "SELECT * FROM user";
	private static final String UPDATE_QUERY = "UPDATE user SET username = ?, password = ?, email = ?, address = ?, lastLoginDate = ? WHERE userId = ? ";
	private static final String DELETE_QUERY = "DELETE FROM user WHERE userId = ?";
	@Override
	public int addUser(User u) {
		Connection connection = DBConnection.getConnection();
		int i=0;
		try {
			PreparedStatement stmt = connection.prepareStatement(INSERT_QUERY);
			stmt.setString(1, u.getUserName());
			stmt.setString(2, u.getPassword());
			stmt.setString(3, u.getEmail());
			stmt.setString(4, u.getAddress());
			stmt.setString(5, u.getRole());
			stmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
			i = stmt.executeUpdate();
			System.out.println(i);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	@Override
	public void updateUser(User u) {
		// TODO Auto-generated method stub
		Connection connection = DBConnection.getConnection();
		try {
			PreparedStatement stmt = connection.prepareStatement(UPDATE_QUERY);
			stmt.setString(1, u.getUserName());
			stmt.setString(2, u.getPassword());
			stmt.setString(3, u.getEmail());
			stmt.setString(4, u.getAddress());
			stmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
			stmt.setInt(6, u.getUserId());
			int i = stmt.executeUpdate();
			System.out.println(i);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void deleteUser(int id) {
		// TODO Auto-generated method stub
		Connection connection = DBConnection.getConnection();
		try {
			PreparedStatement stmt = connection.prepareStatement(DELETE_QUERY);
			stmt.setInt(1, id);
			int i = stmt.executeUpdate();
			System.out.println(i);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public User getUser(int id) {
		// TODO Auto-generated method stub
		Connection connection = DBConnection.getConnection();
		User u = null;
		try {
			PreparedStatement stmt = connection.prepareStatement(SELECT_QUERY);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				u = getUserByResultSet(rs);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return u;
	}

	@Override
	public List<User> getAllUser() {
		// TODO Auto-generated method stub
		List<User> list = new ArrayList<User>();
		Connection connection = DBConnection.getConnection();
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(SELECT_QUERY2);
			while(rs.next()) {
				User u = getUserByResultSet(rs);
				list.add(u);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	public static User getUserByResultSet(ResultSet rs) throws SQLException{
		int userId  = rs.getInt("userId");
		String username = rs.getString("username");
		String password = rs.getString("password");
		String email = rs.getString("email");
		String address = rs.getString("address");
		String role = rs.getString("role");
		Timestamp createdDate = rs.getTimestamp("createdDate");
		Timestamp lastLoginDate = rs.getTimestamp("lastLoginDate");
		 User u = new User(userId, username, password, email, address, role, createdDate, lastLoginDate);
		 return u;
	}

}
