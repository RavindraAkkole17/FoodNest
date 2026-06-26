package com.tap.DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.tap.DAO.RestaurantDAO;
import com.tap.Model.Restaurant;
import com.tap.Model.User;
import com.tap.utility.DBConnection;

public class RestaurantDAOImpl implements RestaurantDAO {
	private static final String INSERT_QUERY = " INSERT INTO restaurant ( name, cuisineType,deliveryTime, address,adminUserId, rating, isActive) VALUES (?,?,?,?,?,?,?)";
	private static final String SELECT_QUERY = "SELECT * FROM restaurant WHERE restaurantId = ?";
	private static final String SELECT_QUERY2 = "SELECT * FROM restaurant";
	private static final String UPDATE_QUERY = "UPDATE restaurant SET name = ?, cuisineType = ?, deliveryTime = ?, address = ?, rating =?, isActive = ? WHERE restaurantId = ?";
	private static final String DELETE_QUERY = "DELETE FROM restaurant WHERE restaurantId = ?";
	
	@Override
	public void addRestaurant(Restaurant r) {
		// TODO Auto-generated method stub
		Connection connection = DBConnection.getConnection();
		try {
			PreparedStatement stmt = connection.prepareStatement(INSERT_QUERY);
			stmt.setString(1, r.getName());
			stmt.setString(2, r.getCuisineType());
			stmt.setInt(3, r.getDeliveryTime());
			stmt.setString(4, r.getAddress());
			stmt.setInt(5, r.getAdminUserId());
			stmt.setFloat(6, r.getRating());
			stmt.setInt(7, r.getIsActive());
			int i = stmt.executeUpdate();
			System.out.println(i);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void updateRestaurant(Restaurant r) {
		Connection connection = DBConnection.getConnection();
		try {
			PreparedStatement stmt = connection.prepareStatement(UPDATE_QUERY);
			stmt.setString(1, r.getName());
			stmt.setString(2, r.getCuisineType());
			stmt.setInt(3, r.getDeliveryTime());
			stmt.setString(4, r.getAddress());
			stmt.setFloat(5, r.getRating());
			stmt.setInt(6, r.getIsActive());
			stmt.setInt(7, r.getRestaurantId());
			int i = stmt.executeUpdate();
			System.out.println(i);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void deleteRestaurant(int id) {
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
	public Restaurant getRestaurant(int id) {
		// TODO Auto-generated method stub
		Connection connection = DBConnection.getConnection();
		Restaurant r = null;
		try {
			PreparedStatement stmt = connection.prepareStatement(SELECT_QUERY);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				 r = getRestaurantByResultSet(rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return r;
	}

	@Override
	public List<Restaurant> getAllRestaurant() {
		Connection connection = DBConnection.getConnection();
		List<Restaurant> list = new ArrayList<Restaurant>();
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(SELECT_QUERY2);
			while(rs.next()) {
				 Restaurant r = getRestaurantByResultSet(rs);
				 list.add(r);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public static Restaurant getRestaurantByResultSet(ResultSet rs) throws SQLException{
		int restaurantId  = rs.getInt("restaurantId");
		String name = rs.getString("name");
		String cuisineType = rs.getString("cuisineType");
		int deliveryTime = rs.getInt("deliveryTime");
		String address = rs.getString("address");
		int adminUserId = rs.getInt("adminUserId");
		float rating = rs.getFloat("rating");
		int isActive = rs.getInt("isActive");
		String path = rs.getString("path");
		Restaurant r = new Restaurant(restaurantId, name, cuisineType, deliveryTime, address, adminUserId, rating, isActive, path);
		 return r;
	}

}
