<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tap.Model.OrderTable, com.tap.Model.User" %>
<%
User loggedInUser = (User) session.getAttribute("user");
if (loggedInUser == null) {
	response.sendRedirect("login.html");
	return;
}

OrderTable lastOrder = (OrderTable) session.getAttribute("lastOrder");
if (lastOrder == null) {
	response.sendRedirect("home");
	return;
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Order Confirmed - FoodNest</title>
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
	display: flex;
	flex-direction: column;
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

/* Content Container */
.confirmation-container {
	flex: 1;
	display: flex;
	justify-content: center;
	align-items: center;
	padding: 40px 20px;
}

.confirmation-card {
	background: #1e1e1e;
	border-radius: 20px;
	padding: 40px;
	max-width: 600px;
	width: 100%;
	text-align: center;
	box-shadow: 0 10px 30px rgba(0, 0, 0, 0.5);
	border: 1px solid #2f2f2f;
}

.success-icon {
	width: 100px;
	height: 100px;
	background: rgba(40, 167, 69, 0.1);
	border: 3px solid #28a745;
	color: #28a745;
	border-radius: 50%;
	display: flex;
	justify-content: center;
	align-items: center;
	font-size: 50px;
	margin: 0 auto 25px;
	animation: popIn 0.5s ease-out;
}

@keyframes popIn {
	0% {
		transform: scale(0.5);
		opacity: 0;
	}
	100% {
		transform: scale(1);
		opacity: 1;
	}
}

.confirmation-card h2 {
	font-size: 30px;
	color: #28a745;
	margin-bottom: 10px;
	font-weight: bold;
}

.confirmation-card p {
	color: #aaa;
	font-size: 16px;
	margin-bottom: 30px;
}

/* Order Details Table */
.order-details-table {
	background: #222;
	border-radius: 12px;
	padding: 20px;
	margin-bottom: 35px;
	text-align: left;
	border: 1px solid #333;
}

.details-row {
	display: flex;
	justify-content: space-between;
	padding: 10px 0;
	border-bottom: 1px solid #2d2d2d;
	font-size: 15px;
}

.details-row:last-child {
	border-bottom: none;
}

.details-label {
	color: #aaa;
	font-weight: 500;
}

.details-value {
	font-weight: 600;
	color: white;
}

.highlight-value {
	color: #ff6b35;
	font-weight: bold;
}

/* Home Button */
.home-btn {
	display: inline-block;
	background: #ff6b35;
	color: white;
	text-decoration: none;
	padding: 14px 40px;
	border-radius: 30px;
	font-weight: bold;
	font-size: 16px;
	transition: 0.3s;
	box-shadow: 0 4px 15px rgba(255, 107, 53, 0.25);
}

.home-btn:hover {
	background: #ff7d4d;
	transform: translateY(-2px);
	box-shadow: 0 6px 20px rgba(255, 107, 53, 0.35);
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
			<a href="orderHistory" style="margin-left: 10px; margin-right: 5px;"><i class="fas fa-history"></i> History</a>
			<span class="user-greeting" style="font-weight: 600; color: #ff6b35; margin-left: 10px;">
				Hello, <%= loggedInUser.getUserName() %>
			</span>
		</div>
	</div>

	<div class="confirmation-container">
		<div class="confirmation-card">
			<div class="success-icon">
				<i class="fas fa-check"></i>
			</div>
			
			<h2>Order Confirmed!</h2>
			<p>Thank you for ordering with FoodNest. Your meal is being prepared and will be delivered shortly!</p>
			
			<div class="order-details-table">
				<div class="details-row">
					<span class="details-label">Order ID</span>
					<span class="details-value highlight-value">#<%= lastOrder.getOrderId() %></span>
				</div>
				<div class="details-row">
					<span class="details-label">Date & Time</span>
					<span class="details-value"><%= lastOrder.getOrderDate() %></span>
				</div>
				<div class="details-row">
					<span class="details-label">Delivery Address</span>
					<span class="details-value" style="max-width: 300px; text-align: right; word-wrap: break-word;"><%= loggedInUser.getAddress() %></span>
				</div>
				<div class="details-row">
					<span class="details-label">Payment Method</span>
					<span class="details-value"><%= lastOrder.getPaymentMethod() %></span>
				</div>
				<div class="details-row">
					<span class="details-label">Order Status</span>
					<span class="details-value" style="color: #ffc107;"><%= lastOrder.getStatus() %></span>
				</div>
				<div class="details-row">
					<span class="details-label">Total Amount</span>
					<span class="details-value highlight-value">₹<%= lastOrder.getTotalAmount() %></span>
				</div>
			</div>
			
			<a href="home" class="home-btn"><i class="fas fa-home"></i> Back to Homepage</a>
		</div>
	</div>

</body>
</html>
