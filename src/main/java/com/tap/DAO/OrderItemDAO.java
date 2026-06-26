package com.tap.DAO;

import java.util.List;
import com.tap.Model.OrderItem;

public interface OrderItemDAO {

    void addOrderItem(OrderItem orderItem);

    void updateOrderItem(OrderItem orderItem);

    void deleteOrderItem(int id);

    OrderItem getOrderItem(int id);

    List<OrderItem> getAllOrderItems();

    List<OrderItem> getOrderItemsByOrderId(int orderId);
}