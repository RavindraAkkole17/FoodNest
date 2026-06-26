package com.tap.DAO;

import java.util.List;
import com.tap.Model.OrderTable;

public interface OrderTableDAO {

    int addOrderTable(OrderTable orderTable);

    void updateOrderTable(OrderTable orderTable);

    void deleteOrderTable(int id);

    OrderTable getOrderTable(int id);

    List<OrderTable> getAllOrderTables();

    List<OrderTable> getOrdersByUserId(int userId);
}