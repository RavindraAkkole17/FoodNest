package com.tap.DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tap.DAO.OrderItemDAO;
import com.tap.Model.OrderItem;
import com.tap.utility.DBConnection;

public class OrderItemDAOImpl implements OrderItemDAO {

    private static final String INSERT_QUERY =
            "INSERT INTO orderitem (orderId, menuId, quantity, itemTotal) VALUES (?, ?, ?, ?)";

    private static final String SELECT_QUERY =
            "SELECT * FROM orderitem WHERE orderItemId = ?";

    private static final String SELECT_ALL_QUERY =
            "SELECT * FROM orderitem";

    private static final String UPDATE_QUERY =
            "UPDATE orderitem SET orderId=?, menuId=?, quantity=?, itemTotal=? WHERE orderItemId=?";

    private static final String DELETE_QUERY =
            "DELETE FROM orderitem WHERE orderItemId=?";

    @Override
    public void addOrderItem(OrderItem orderItem) {

        Connection connection = DBConnection.getConnection();

        try {
            PreparedStatement stmt = connection.prepareStatement(INSERT_QUERY);

            stmt.setInt(1, orderItem.getOrderId());
            stmt.setInt(2, orderItem.getMenuId());
            stmt.setInt(3, orderItem.getQuantity());
            stmt.setDouble(4, orderItem.getItemTotal());

            int i = stmt.executeUpdate();
            System.out.println(i);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateOrderItem(OrderItem orderItem) {

        Connection connection = DBConnection.getConnection();

        try {
            PreparedStatement stmt = connection.prepareStatement(UPDATE_QUERY);

            stmt.setInt(1, orderItem.getOrderId());
            stmt.setInt(2, orderItem.getMenuId());
            stmt.setInt(3, orderItem.getQuantity());
            stmt.setDouble(4, orderItem.getItemTotal());
            stmt.setInt(5, orderItem.getOrderItemId());

            int i = stmt.executeUpdate();
            System.out.println(i);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteOrderItem(int id) {

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
    public OrderItem getOrderItem(int id) {

        Connection connection = DBConnection.getConnection();
        OrderItem orderItem = null;

        try {
            PreparedStatement stmt = connection.prepareStatement(SELECT_QUERY);

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                orderItem = getOrderItemByResultSet(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderItem;
    }

    @Override
    public List<OrderItem> getAllOrderItems() {

        Connection connection = DBConnection.getConnection();
        List<OrderItem> list = new ArrayList<>();

        try {
            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery(SELECT_ALL_QUERY);

            while (rs.next()) {
                OrderItem orderItem = getOrderItemByResultSet(rs);
                list.add(orderItem);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static OrderItem getOrderItemByResultSet(ResultSet rs) throws SQLException {

        int orderItemId = rs.getInt("orderItemId");
        int orderId = rs.getInt("orderId");
        int menuId = rs.getInt("menuId");
        int quantity = rs.getInt("quantity");
        double itemTotal = rs.getDouble("itemTotal");

        return new OrderItem(
                orderItemId,
                orderId,
                menuId,
                quantity,
                itemTotal
        );
    }

    private static final String SELECT_BY_ORDER_QUERY =
            "SELECT * FROM orderitem WHERE orderId = ?";

    @Override
    public List<OrderItem> getOrderItemsByOrderId(int orderId) {
        Connection connection = DBConnection.getConnection();
        List<OrderItem> list = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(SELECT_BY_ORDER_QUERY);
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(getOrderItemByResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}