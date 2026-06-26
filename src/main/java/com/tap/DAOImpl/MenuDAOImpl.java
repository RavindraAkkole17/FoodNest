package com.tap.DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.tap.DAO.MenuDAO;
import com.tap.Model.Menu;
import com.tap.utility.DBConnection;

public class MenuDAOImpl implements MenuDAO {

    private static final String INSERT_QUERY =
            "INSERT INTO menu (restaurantId, itemName, description, price, isAvailable, category, createdAt, updatedAt, deletedAt, path) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?, ?)";

    private static final String SELECT_QUERY =
            "SELECT * FROM menu WHERE menuId = ?";

    private static final String SELECT_ALL_QUERY =
            "SELECT * FROM menu WHERE restaurantId = ?";

    private static final String UPDATE_QUERY =
            "UPDATE menu SET restaurantId=?, itemName=?, description=?, price=?, isAvailable=?, category=?, updatedAt=? WHERE menuId=?";

    private static final String DELETE_QUERY =
            "DELETE FROM menu WHERE menuId=?";

    @Override
    public void addMenu(Menu m) {
        Connection connection = DBConnection.getConnection();

        try {
            PreparedStatement stmt = connection.prepareStatement(INSERT_QUERY);

            stmt.setInt(1, m.getRestaurantID());
            stmt.setString(2, m.getItemName());
            stmt.setString(3, m.getDescription());
            stmt.setDouble(4, m.getPrice());
            stmt.setBoolean(5, m.isAvailable());
            stmt.setString(6, m.getCategory());
            stmt.setTimestamp(7, m.getCreatedAt());
            stmt.setTimestamp(8, m.getUpdatedAt());
            stmt.setTimestamp(9, m.getDeletedAt());
            stmt.setString(10, m.getPath());

            int i = stmt.executeUpdate();
            System.out.println(i);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateMenu(Menu m) {

        Connection connection = DBConnection.getConnection();

        try {
            PreparedStatement stmt = connection.prepareStatement(UPDATE_QUERY);

            stmt.setInt(1, m.getRestaurantID());
            stmt.setString(2, m.getItemName());
            stmt.setString(3, m.getDescription());
            stmt.setDouble(4, m.getPrice());
            stmt.setBoolean(5, m.isAvailable());
            stmt.setString(6, m.getCategory());
            stmt.setTimestamp(7, m.getUpdatedAt());
            stmt.setInt(8, m.getMenuID());

            int i = stmt.executeUpdate();
            System.out.println(i);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteMenu(int id) {

        Connection connection = DBConnection.getConnection();

        try {
            PreparedStatement stmt = connection.prepareStatement(DELETE_QUERY);

            stmt.setInt(1, id);

            int i = stmt.executeUpdate();
            System.out.println(i);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Menu getMenu(int id) {

        Connection connection = DBConnection.getConnection();
        Menu m = null;

        try {
            PreparedStatement stmt = connection.prepareStatement(SELECT_QUERY);

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                m = getMenuByResultSet(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return m;
    }

    @Override
    public List<Menu> getAllMenu(int id) {

        Connection connection = DBConnection.getConnection();
        List<Menu> list = new ArrayList<>();

        try {
            PreparedStatement stmt = connection.prepareStatement(SELECT_ALL_QUERY);

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Menu m = getMenuByResultSet(rs);
                list.add(m);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static Menu getMenuByResultSet(ResultSet rs) throws SQLException {

        int menuId = rs.getInt("menuId");
        int restaurantId = rs.getInt("restaurantId");
        String itemName = rs.getString("itemName");
        String description = rs.getString("description");
        double price = rs.getDouble("price");
        boolean isAvailable = rs.getBoolean("isAvailable");
        String category = rs.getString("category");
        Timestamp createdAt = rs.getTimestamp("createdAt");
        Timestamp updatedAt = rs.getTimestamp("updatedAt");
        Timestamp deletedAt = rs.getTimestamp("deletedAt");
        String path = rs.getString("path");

        return new Menu(
                menuId,
                restaurantId,
                itemName,
                description,
                price,
                isAvailable,
                category,
                createdAt,
                updatedAt,
                deletedAt,
                path
        );
    }
}