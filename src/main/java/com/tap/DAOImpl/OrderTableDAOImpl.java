package com.tap.DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.tap.DAO.OrderTableDAO;
import com.tap.Model.OrderTable;
import com.tap.utility.DBConnection;

public class OrderTableDAOImpl implements OrderTableDAO {

    private static final String INSERT_QUERY =
            "INSERT INTO ordertable (userId, restaurantId, orderDate, totalAmount, status, paymentMethod) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SELECT_QUERY =
            "SELECT * FROM ordertable WHERE orderId = ?";

    private static final String SELECT_ALL_QUERY =
            "SELECT * FROM ordertable";

    private static final String UPDATE_QUERY =
            "UPDATE ordertable SET userId=?, restaurantId=?, orderDate=?, totalAmount=?, status=?, paymentMethod=? WHERE orderId=?";

    private static final String DELETE_QUERY =
            "DELETE FROM ordertable WHERE orderId=?";

    @Override
    public int addOrderTable(OrderTable orderTable) {

        Connection connection = DBConnection.getConnection();
        int generatedId = -1;

        try {
            PreparedStatement stmt = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, orderTable.getUserId());
            stmt.setInt(2, orderTable.getRestaurantId());
            stmt.setTimestamp(3, orderTable.getOrderDate());
            stmt.setDouble(4, orderTable.getTotalAmount());
            stmt.setString(5, orderTable.getStatus());
            stmt.setString(6, orderTable.getPaymentMethod());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    generatedId = rs.getInt(1);
                }
            }
            System.out.println("Generated Order ID: " + generatedId);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedId;
    }

    @Override
    public void updateOrderTable(OrderTable orderTable) {

        Connection connection = DBConnection.getConnection();

        try {
            PreparedStatement stmt = connection.prepareStatement(UPDATE_QUERY);

            stmt.setInt(1, orderTable.getUserId());
            stmt.setInt(2, orderTable.getRestaurantId());
            stmt.setTimestamp(3, orderTable.getOrderDate());
            stmt.setDouble(4, orderTable.getTotalAmount());
            stmt.setString(5, orderTable.getStatus());
            stmt.setString(6, orderTable.getPaymentMethod());
            stmt.setInt(7, orderTable.getOrderId());

            int i = stmt.executeUpdate();
            System.out.println(i);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteOrderTable(int id) {

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
    public OrderTable getOrderTable(int id) {

        Connection connection = DBConnection.getConnection();
        OrderTable orderTable = null;

        try {
            PreparedStatement stmt = connection.prepareStatement(SELECT_QUERY);

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                orderTable = getOrderTableByResultSet(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderTable;
    }

    @Override
    public List<OrderTable> getAllOrderTables() {

        Connection connection = DBConnection.getConnection();
        List<OrderTable> list = new ArrayList<>();

        try {
            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery(SELECT_ALL_QUERY);

            while (rs.next()) {
                OrderTable orderTable = getOrderTableByResultSet(rs);
                list.add(orderTable);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static OrderTable getOrderTableByResultSet(ResultSet rs) throws SQLException {

        int orderId = rs.getInt("orderId");
        int userId = rs.getInt("userId");
        int restaurantId = rs.getInt("restaurantId");
        Timestamp orderDate = rs.getTimestamp("orderDate");
        double totalAmount = rs.getDouble("totalAmount");
        String status = rs.getString("status");
        String paymentMethod = rs.getString("paymentMethod");

        return new OrderTable(
                orderId,
                userId,
                restaurantId,
                orderDate,
                totalAmount,
                status,
                paymentMethod
        );
    }

    private static final String SELECT_BY_USER_QUERY =
            "SELECT * FROM ordertable WHERE userId = ? ORDER BY orderDate DESC";

    @Override
    public List<OrderTable> getOrdersByUserId(int userId) {
        Connection connection = DBConnection.getConnection();
        List<OrderTable> list = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(SELECT_BY_USER_QUERY);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(getOrderTableByResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}