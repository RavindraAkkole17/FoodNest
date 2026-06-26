package com.tap.Servlet;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Map;

import com.tap.DAOImpl.OrderItemDAOImpl;
import com.tap.DAOImpl.OrderTableDAOImpl;
import com.tap.DAOImpl.UserDAOImpl;
import com.tap.Model.Cart;
import com.tap.Model.CartItem;
import com.tap.Model.OrderItem;
import com.tap.Model.OrderTable;
import com.tap.Model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		User loggedInUser = (User) session.getAttribute("user");
		Cart cart = (Cart) session.getAttribute("cart");
		
		if (loggedInUser == null) {
			resp.sendRedirect("login.html");
			return;
		}
		
		if (cart == null || cart.getItems().isEmpty()) {
			resp.sendRedirect("cart.jsp");
			return;
		}
		
		String address = req.getParameter("address");
		String paymentMethod = req.getParameter("paymentMethod");
		String razorpayPaymentId = req.getParameter("razorpay_payment_id");
		
		// Update user address in database and session if it's new or modified
		if (address != null && !address.trim().isEmpty() && !address.equals(loggedInUser.getAddress())) {
			loggedInUser.setAddress(address);
			UserDAOImpl userDAOImpl = new UserDAOImpl();
			userDAOImpl.updateUser(loggedInUser);
			session.setAttribute("user", loggedInUser);
		}
		
		Integer restaurantId = (Integer) session.getAttribute("restaurantId");
		if (restaurantId == null) {
			// Find restaurantId from the first cart item if it's missing in session
			for (CartItem item : cart.getItems().values()) {
				restaurantId = item.getRestaurantId();
				break;
			}
		}
		
		double subtotal = 0;
		for (CartItem item : cart.getItems().values()) {
			subtotal += item.getPrice() * item.getQty();
		}
		double grandTotal = subtotal + 40; // Delivery fee
		
		String status = "Pending";
		String finalPaymentMethod = paymentMethod;
		if (razorpayPaymentId != null && !razorpayPaymentId.trim().isEmpty()) {
			status = "Paid";
			finalPaymentMethod = paymentMethod + " (" + razorpayPaymentId + ")";
		}
		
		// Place order in OrderTable
		OrderTable order = new OrderTable(
			loggedInUser.getUserId(),
			restaurantId != null ? restaurantId : 0,
			new Timestamp(System.currentTimeMillis()),
			grandTotal,
			status,
			finalPaymentMethod
		);
		
		OrderTableDAOImpl orderTableDAOImpl = new OrderTableDAOImpl();
		int orderId = orderTableDAOImpl.addOrderTable(order);
		
		if (orderId > 0) {
			// Set order ID in the model to pass to confirmation page
			order.setOrderId(orderId);
			
			// Place order items
			OrderItemDAOImpl orderItemDAOImpl = new OrderItemDAOImpl();
			for (CartItem item : cart.getItems().values()) {
				OrderItem orderItem = new OrderItem(
					orderId,
					item.getMenuId(),
					item.getQty(),
					item.getPrice() * item.getQty()
				);
				orderItemDAOImpl.addOrderItem(orderItem);
			}
			
			// Clear the cart
			cart.clear();
			session.removeAttribute("restaurantId"); // Reset restaurant selection
			
			// Save order in session for order confirmation page
			session.setAttribute("lastOrder", order);
			
			resp.sendRedirect("orderConfirmation.jsp");
		} else {
			// Error placing order
			resp.sendRedirect("cart.jsp");
		}
	}
}
