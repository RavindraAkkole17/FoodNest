<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.tap.Model.OrderTable, com.tap.Model.OrderItem, com.tap.Model.Menu, com.tap.Model.User, com.tap.DAOImpl.OrderItemDAOImpl, com.tap.DAOImpl.MenuDAOImpl" %>
<%
User loggedInUser = (User) session.getAttribute("user");
if (loggedInUser == null) {
	response.sendRedirect("login.html");
	return;
}

List<OrderTable> orderList = (List<OrderTable>) request.getAttribute("orderList");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My Orders - FoodNest</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
<style>
* {
	margin: 0;
	padding: 0;
	box-sizing: border-box;
	font-family: 'Poppins', sans-serif;
}

body {
	background: #121212;
	color: white;
	min-height: 100vh;
}

/* Header Navbar */
.header {
	background: #1e1e1e;
	padding: 15px 30px;
	display: flex;
	justify-content: space-between;
	align-items: center;
	border-bottom: 2px solid #ff6b35;
	position: sticky;
	top: 0;
	z-index: 100;
}

.header h1 {
	color: #ff6b35;
	font-size: 28px;
	display: flex;
	align-items: center;
	gap: 10px;
}

.header .nav-links {
	display: flex;
	gap: 20px;
	align-items: center;
}

.header .nav-links a {
	color: white;
	text-decoration: none;
	font-weight: 500;
	transition: 0.3s;
}

.header .nav-links a:hover {
	color: #ff6b35;
}

.header .btn-nav {
	background: #ff6b35;
	color: white;
	border: none;
	padding: 8px 16px;
	border-radius: 20px;
	cursor: pointer;
	font-size: 14px;
	font-weight: bold;
	transition: 0.3s;
}

.header .btn-nav:hover {
	background: #ff824d;
}

/* Main Container */
.history-container {
	width: 90%;
	max-width: 1000px;
	margin: 40px auto;
}

.page-title {
	font-size: 28px;
	margin-bottom: 30px;
	color: #ff6b35;
	display: flex;
	align-items: center;
	gap: 12px;
}

.order-card {
	background: #1e1e1e;
	border-radius: 15px;
	border: 1px solid #2f2f2f;
	margin-bottom: 25px;
	overflow: hidden;
	box-shadow: 0 4px 15px rgba(0, 0, 0, 0.4);
}

.order-header {
	background: #252525;
	padding: 15px 25px;
	display: flex;
	justify-content: space-between;
	align-items: center;
	flex-wrap: wrap;
	gap: 15px;
	border-bottom: 1px solid #2f2f2f;
}

.order-info-group {
	display: flex;
	gap: 25px;
}

.info-item h4 {
	font-size: 12px;
	color: #aaa;
	text-transform: uppercase;
	margin-bottom: 4px;
}

.info-item p {
	font-size: 14px;
	font-weight: 600;
}

.order-status {
	font-size: 14px;
	font-weight: bold;
	padding: 6px 14px;
	border-radius: 20px;
}

.status-paid {
	background: rgba(40, 167, 69, 0.15);
	color: #28a745;
	border: 1px solid #28a745;
}

.status-pending {
	background: rgba(255, 193, 7, 0.15);
	color: #ffc107;
	border: 1px solid #ffc107;
}

/* Order items list */
.order-body {
	padding: 20px 25px;
}

.food-item {
	display: flex;
	justify-content: space-between;
	align-items: center;
	padding: 12px 0;
	border-bottom: 1px solid #2c2c2c;
}

.food-item:last-child {
	border-bottom: none;
}

.food-details {
	display: flex;
	align-items: center;
	gap: 15px;
}

.food-details img {
	width: 50px;
	height: 50px;
	object-fit: cover;
	border-radius: 8px;
}

.food-name {
	font-weight: 600;
	font-size: 15px;
}

.food-quantity {
	color: #aaa;
	font-size: 14px;
	margin-top: 3px;
}

.food-total {
	font-weight: bold;
	font-size: 15px;
}

/* Empty State */
.no-orders {
	text-align: center;
	padding: 80px 20px;
	background: #1e1e1e;
	border-radius: 15px;
	border: 1px solid #2f2f2f;
	box-shadow: 0 4px 15px rgba(0, 0, 0, 0.4);
}

.no-orders i {
	font-size: 80px;
	color: #ff6b35;
	margin-bottom: 20px;
}

.no-orders h2 {
	font-size: 26px;
	margin-bottom: 10px;
}

.no-orders p {
	color: #aaa;
	margin-bottom: 30px;
}

.browse-btn {
	display: inline-block;
	background: #ff6b35;
	color: white;
	text-decoration: none;
	padding: 12px 30px;
	border-radius: 25px;
	font-weight: bold;
	transition: 0.3s;
}

.browse-btn:hover {
	background: #ff7d4d;
}
</style>
</head>
<body>

	<!-- Header Navbar -->
	<div class="header">
		<h1>
			<i class="fas fa-utensils"></i> FoodNest
		</h1>
		<div class="nav-links">
			<a href="home"><i class="fas fa-home"></i> Home</a>
			<a href="cart.jsp"><i class="fas fa-shopping-cart"></i> Cart</a>
			<span class="user-greeting" style="font-weight: 600; color: #ff6b35; margin-left: 10px;">
				Hello, <%= loggedInUser.getUserName() %>
			</span>
			<a href="logout"><button class="btn-nav" style="background: #dc3545;">Logout</button></a>
		</div>
	</div>

	<div class="history-container">
		<h2 class="page-title"><i class="fas fa-history"></i> My Order & Payment History</h2>
		
		<%
		if (orderList == null || orderList.isEmpty()) {
		%>
			<div class="no-orders">
				<i class="fas fa-receipt"></i>
				<h2>No Order History Found</h2>
				<p>It looks like you haven't placed any orders yet. Start exploring delicious food now!</p>
				<a href="home" class="browse-btn">Order Food Now</a>
			</div>
		<%
		} else {
			OrderItemDAOImpl orderItemDAOImpl = new OrderItemDAOImpl();
			MenuDAOImpl menuDAOImpl = new MenuDAOImpl();
			
			for (OrderTable order : orderList) {
				boolean isPaid = "Paid".equalsIgnoreCase(order.getStatus());
		%>
				<div class="order-card">
					<!-- Card Header: Order summary metadata -->
					<div class="order-header">
						<div class="order-info-group">
							<div class="info-item">
								<h4>Order Placed</h4>
								<p><%= order.getOrderDate() %></p>
							</div>
							<div class="info-item">
								<h4>Total Amount</h4>
								<p style="color: #ff6b35;">₹<%= order.getTotalAmount() %></p>
							</div>
							<div class="info-item">
								<h4>Payment Method</h4>
								<p><%= order.getPaymentMethod() %></p>
							</div>
							<div class="info-item">
								<h4>Order ID</h4>
								<p>#<%= order.getOrderId() %></p>
							</div>
						</div>
						
						<span class="order-status <%= isPaid ? "status-paid" : "status-pending" %>">
							<i class="fas <%= isPaid ? "fa-check-circle" : "fa-clock" %>"></i>
							<%= order.getStatus() %>
						</span>
					</div>
					
					<!-- Card Body: Ordered Food Items -->
					<div class="order-body">
						<%
						List<OrderItem> items = orderItemDAOImpl.getOrderItemsByOrderId(order.getOrderId());
						if (items != null) {
							for (OrderItem item : items) {
								Menu menu = menuDAOImpl.getMenu(item.getMenuId());
						%>
								<div class="food-item">
									<div class="food-details">
										<img src="<%= (menu != null && menu.getPath() != null && !menu.getPath().isEmpty()) ? menu.getPath() : "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?auto=format&fit=crop&w=100&q=80" %>" alt="Food Item">
										<div>
											<div class="food-name"><%= menu != null ? menu.getItemName() : "Unknown Item (ID: " + item.getMenuId() + ")" %></div>
											<div class="food-quantity">Quantity: <%= item.getQuantity() %></div>
										</div>
									</div>
									<div class="food-total">₹<%= item.getItemTotal() %></div>
								</div>
						<%
							}
						}
						%>
					</div>
				</div>
		<%
			}
		}
		%>
	</div>

</body>
</html>
