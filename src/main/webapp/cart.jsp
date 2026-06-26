<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Map, com.tap.Model.Cart, com.tap.Model.CartItem, com.tap.Model.User" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Your Cart - FoodNest</title>
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
.cart-container {
	width: 90%;
	max-width: 1200px;
	margin: 40px auto;
	display: grid;
	grid-template-columns: 2fr 1fr;
	gap: 30px;
}

@media(max-width: 992px) {
	.cart-container {
		grid-template-columns: 1fr;
	}
}

/* Left Section: Cart Items */
.cart-items-section {
	background: #1e1e1e;
	border-radius: 15px;
	padding: 25px;
	box-shadow: 0 4px 15px rgba(0, 0, 0, 0.4);
	border: 1px solid #2f2f2f;
}

.cart-title {
	font-size: 24px;
	margin-bottom: 20px;
	border-bottom: 1px solid #333;
	padding-bottom: 15px;
	color: #ff6b35;
}

.cart-item {
	display: flex;
	align-items: center;
	justify-content: space-between;
	padding: 15px 0;
	border-bottom: 1px solid #2b2b2b;
}

.cart-item img {
	width: 80px;
	height: 80px;
	object-fit: cover;
	border-radius: 10px;
}

.item-details {
	flex: 1;
	margin-left: 20px;
}

.item-name {
	font-size: 18px;
	font-weight: 600;
	margin-bottom: 5px;
}

.item-price {
	color: #ff6b35;
	font-size: 16px;
	font-weight: bold;
}

/* Quantity controls */
.quantity-controls {
	display: flex;
	align-items: center;
	gap: 15px;
	background: #2b2b2b;
	padding: 6px 12px;
	border-radius: 20px;
	border: 1px solid #333;
}

.quantity-controls a {
	color: white;
	text-decoration: none;
	font-weight: bold;
	font-size: 18px;
	transition: 0.2s;
}

.quantity-controls a:hover {
	color: #ff6b35;
}

.qty-display {
	font-size: 16px;
	font-weight: bold;
	min-width: 20px;
	text-align: center;
}

.item-total {
	font-size: 18px;
	font-weight: bold;
	margin-left: 25px;
	min-width: 80px;
	text-align: right;
}

.remove-btn {
	color: #dc3545;
	font-size: 20px;
	margin-left: 20px;
	cursor: pointer;
	transition: 0.3s;
}

.remove-btn:hover {
	color: #ff4d5a;
	transform: scale(1.1);
}

/* Right Section: Summary */
.summary-section {
	background: #1e1e1e;
	border-radius: 15px;
	padding: 25px;
	box-shadow: 0 4px 15px rgba(0, 0, 0, 0.4);
	border: 1px solid #2f2f2f;
	height: fit-content;
}

.summary-title {
	font-size: 22px;
	margin-bottom: 20px;
	border-bottom: 1px solid #333;
	padding-bottom: 15px;
	color: #ff6b35;
}

.summary-row {
	display: flex;
	justify-content: space-between;
	margin-bottom: 15px;
	font-size: 16px;
}

.total-row {
	font-size: 20px;
	font-weight: bold;
	border-top: 1px solid #333;
	padding-top: 15px;
	margin-top: 15px;
	color: white;
}

.checkout-btn {
	display: block;
	width: 100%;
	background: #28a745;
	color: white;
	text-align: center;
	padding: 14px;
	border-radius: 8px;
	font-weight: bold;
	font-size: 16px;
	text-decoration: none;
	margin-top: 25px;
	cursor: pointer;
	border: none;
	transition: 0.3s;
}

.checkout-btn:hover {
	background: #2fb94d;
}

/* Buttons actions */
.cart-actions {
	display: flex;
	gap: 15px;
	margin-top: 25px;
}

.action-btn {
	flex: 1;
	text-align: center;
	padding: 12px;
	border-radius: 8px;
	font-weight: bold;
	text-decoration: none;
	font-size: 15px;
	transition: 0.3s;
}

.add-more-btn {
	background: #ff6b35;
	color: white;
}

.add-more-btn:hover {
	background: #ff824d;
}

.browse-btn {
	background: #333;
	color: white;
	border: 1px solid #444;
}

.browse-btn:hover {
	background: #444;
}

/* Empty Cart State */
.empty-cart {
	text-align: center;
	padding: 60px 20px;
}

.empty-cart i {
	font-size: 80px;
	color: #ff6b35;
	margin-bottom: 20px;
}

.empty-cart h2 {
	font-size: 28px;
	margin-bottom: 10px;
}

.empty-cart p {
	color: #aaa;
	margin-bottom: 30px;
}
</style>
</head>
<body>

	<!-- Header Navbar -->
	<div class="header">
		<h1>
			<i class="fas fa-shopping-cart"></i> FoodNest Cart
		</h1>
		<div class="nav-links">
			<a href="home"><i class="fas fa-home"></i> Home</a>
			<%
			User loggedInUser = (User) session.getAttribute("user");
			if (loggedInUser == null) {
			%>
				<a href="login.html"><button class="btn-nav">Login</button></a>
			<%
			} else {
			%>
				<span class="user-greeting" style="font-weight: 600; color: #ff6b35;">
					Hello, <%= loggedInUser.getUserName() %>
				</span>
				<a href="orderHistory" style="margin-left: 15px; margin-right: 5px;"><i class="fas fa-history"></i> History</a>
				<a href="logout"><button class="btn-nav" style="background: #dc3545;">Logout</button></a>
			<%
			}
			%>
		</div>
	</div>

	<%
	Cart cart = (Cart) session.getAttribute("cart");
	Integer restaurantId = (Integer) session.getAttribute("restaurantId");
	
	if (cart == null || cart.getItems().isEmpty()) {
	%>
		<div class="empty-cart">
			<i class="fas fa-shopping-basket"></i>
			<h2>Your Cart is Empty</h2>
			<p>Explore nearby popular restaurants and add items to your cart.</p>
			<a href="home" class="action-btn add-more-btn" style="display: inline-block; width: auto; padding: 12px 30px;">Browse Restaurants</a>
		</div>
	<%
	} else {
		double subtotal = 0;
		Map<Integer, CartItem> items = cart.getItems();
	%>
		<div class="cart-container">
			<!-- Left Column: Cart Items -->
			<div class="cart-items-section">
				<h2 class="cart-title">Selected Foods</h2>
				
				<%
				for (Map.Entry<Integer, CartItem> entry : items.entrySet()) {
					CartItem item = entry.getValue();
					double itemTotal = item.getPrice() * item.getQty();
					subtotal += itemTotal;
				%>
					<div class="cart-item">
						<img src="<%= item.getPath() != null && !item.getPath().isEmpty() ? item.getPath() : "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?auto=format&fit=crop&w=300&q=80" %>" alt="<%= item.getName() %>">
						
						<div class="item-details">
							<div class="item-name"><%= item.getName() %></div>
							<div class="item-price">₹<%= item.getPrice() %></div>
						</div>
						
						<!-- Quantity Controls -->
						<div class="quantity-controls">
							<a href="callCartServlet?action=update&menuId=<%= item.getMenuId() %>&qty=<%= item.getQty() - 1 %>"><i class="fas fa-minus"></i></a>
							<span class="qty-display"><%= item.getQty() %></span>
							<a href="callCartServlet?action=update&menuId=<%= item.getMenuId() %>&qty=<%= item.getQty() + 1 %>"><i class="fas fa-plus"></i></a>
						</div>
						
						<div class="item-total">₹<%= itemTotal %></div>
						
						<!-- Remove Button -->
						<a href="callCartServlet?action=delete&menuId=<%= item.getMenuId() %>" class="remove-btn">
							<i class="fas fa-trash-alt"></i>
						</a>
					</div>
				<%
				}
				%>
				
				<div class="cart-actions">
					<% if (restaurantId != null) { %>
						<a href="menu?restaurantId=<%= restaurantId %>" class="action-btn add-more-btn">
							<i class="fas fa-plus"></i> Add More Items
						</a>
					<% } %>
					<a href="home" class="action-btn browse-btn">
						<i class="fas fa-utensils"></i> Browse Restaurants
					</a>
				</div>
			</div>
			
			<!-- Right Column: Summary & Checkout -->
			<div class="summary-section">
				<h2 class="summary-title">Order Summary</h2>
				
				<div class="summary-row">
					<span>Subtotal</span>
					<span>₹<%= subtotal %></span>
				</div>
				
				<div class="summary-row">
					<span>Delivery Fee</span>
					<span>₹40.00</span>
				</div>
				
				<div class="summary-row total-row">
					<span>Grand Total</span>
					<span>₹<%= subtotal + 40 %></span>
				</div>
				
				<% if (loggedInUser != null) { %>
					<a href="checkout.jsp" class="checkout-btn">
						Proceed to Checkout <i class="fas fa-arrow-right"></i>
					</a>
				<% } else { %>
					<a href="login.html" class="checkout-btn" style="background: #ff6b35;">
						Login to Checkout <i class="fas fa-sign-in-alt"></i>
					</a>
				<% } %>
			</div>
		</div>
	<%
	}
	%>

</body>
</html>
