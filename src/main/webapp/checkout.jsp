<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Map, com.tap.Model.Cart, com.tap.Model.CartItem, com.tap.Model.User" %>
<%
User loggedInUser = (User) session.getAttribute("user");
if (loggedInUser == null) {
	response.sendRedirect("login.html");
	return;
}

Cart cart = (Cart) session.getAttribute("cart");
if (cart == null || cart.getItems().isEmpty()) {
	response.sendRedirect("cart.jsp");
	return;
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Checkout - FoodNest</title>
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

/* Main Container */
.checkout-container {
	width: 90%;
	max-width: 1200px;
	margin: 40px auto;
	display: grid;
	grid-template-columns: 1.5fr 1fr;
	gap: 30px;
}

@media(max-width: 992px) {
	.checkout-container {
		grid-template-columns: 1fr;
	}
}

/* Left Section: Details & Payment */
.details-section {
	background: #1e1e1e;
	border-radius: 15px;
	padding: 25px;
	box-shadow: 0 4px 15px rgba(0, 0, 0, 0.4);
	border: 1px solid #2f2f2f;
}

.section-title {
	font-size: 22px;
	margin-bottom: 20px;
	border-bottom: 1px solid #333;
	padding-bottom: 12px;
	color: #ff6b35;
	display: flex;
	align-items: center;
	gap: 10px;
}

.form-group {
	margin-bottom: 20px;
}

.form-group label {
	display: block;
	margin-bottom: 8px;
	color: #cfcfcf;
	font-weight: 500;
}

.form-group input, .form-group textarea {
	width: 100%;
	padding: 12px 15px;
	border-radius: 8px;
	border: 1px solid #333;
	background: #2b2b2b;
	color: white;
	font-size: 15px;
	outline: none;
}

.form-group input:focus, .form-group textarea:focus {
	border-color: #ff6b35;
}

/* Payment selection */
.payment-methods {
	display: flex;
	flex-direction: column;
	gap: 12px;
	margin-top: 15px;
}

.payment-option {
	display: flex;
	align-items: center;
	background: #2b2b2b;
	padding: 15px;
	border-radius: 8px;
	border: 1px solid #333;
	cursor: pointer;
	transition: 0.3s;
}

.payment-option:hover {
	border-color: #ff6b35;
}

.payment-option input {
	margin-right: 15px;
	accent-color: #ff6b35;
	width: 18px;
	height: 18px;
	cursor: pointer;
}

.payment-option i {
	font-size: 20px;
	margin-right: 12px;
	color: #ff6b35;
	width: 25px;
	text-align: center;
}

.payment-text h4 {
	font-size: 15px;
	font-weight: 600;
	margin-bottom: 2px;
}

/* Right Section: Order Review */
.review-section {
	background: #1e1e1e;
	border-radius: 15px;
	padding: 25px;
	box-shadow: 0 4px 15px rgba(0, 0, 0, 0.4);
	border: 1px solid #2f2f2f;
	height: fit-content;
}

.review-item {
	display: flex;
	justify-content: space-between;
	padding: 12px 0;
	border-bottom: 1px solid #2b2b2b;
	font-size: 15px;
}

.review-item-name {
	color: #cfcfcf;
}

.review-item-price {
	font-weight: bold;
}

.summary-total {
	display: flex;
	justify-content: space-between;
	font-size: 20px;
	font-weight: bold;
	border-top: 1px solid #333;
	padding-top: 15px;
	margin-top: 15px;
	color: #ff6b35;
}

.place-order-btn {
	display: block;
	width: 100%;
	background: #28a745;
	color: white;
	text-align: center;
	padding: 15px;
	border-radius: 8px;
	font-weight: bold;
	font-size: 16px;
	text-decoration: none;
	margin-top: 25px;
	cursor: pointer;
	border: none;
	transition: 0.3s;
}

.place-order-btn:hover {
	background: #2fb94d;
}

.back-to-cart-btn {
	display: block;
	width: 100%;
	background: #333;
	color: white;
	text-align: center;
	padding: 12px;
	border-radius: 8px;
	font-weight: bold;
	font-size: 15px;
	text-decoration: none;
	margin-top: 12px;
	transition: 0.3s;
	border: 1px solid #444;
}

.back-to-cart-btn:hover {
	background: #444;
}
</style>
</head>
<body>

	<!-- Header Navbar -->
	<div class="header">
		<h1>
			<i class="fas fa-utensils"></i> FoodNest Checkout
		</h1>
		<div class="nav-links">
			<a href="home"><i class="fas fa-home"></i> Home</a>
			<a href="cart.jsp"><i class="fas fa-shopping-cart"></i> Cart</a>
			<a href="orderHistory" style="margin-left: 10px; margin-right: 5px;"><i class="fas fa-history"></i> History</a>
			<span class="user-greeting" style="font-weight: 600; color: #ff6b35; margin-left: 10px;">
				Hello, <%= loggedInUser.getUserName() %>
			</span>
		</div>
	</div>

	<%
	double subtotal = 0;
	Map<Integer, CartItem> items = cart.getItems();
	for (Map.Entry<Integer, CartItem> entry : items.entrySet()) {
		CartItem item = entry.getValue();
		subtotal += item.getPrice() * item.getQty();
	}
	double grandTotal = subtotal + 40; // Including delivery fee
	%>

	<div class="checkout-container">
		<!-- Left Side: Form Details -->
		<div class="details-section">
			<form id="checkout-form" action="checkout" method="POST">
				<input type="hidden" id="razorpay_payment_id" name="razorpay_payment_id">
				<h2 class="section-title"><i class="fas fa-truck"></i> Delivery Details</h2>
				
				<div class="form-group">
					<label for="name">Recipient Name</label>
					<input type="text" id="name" name="name" value="<%= loggedInUser.getUserName() %>" required>
				</div>
				
				<div class="form-group">
					<label for="email">Email Address</label>
					<input type="email" id="email" name="email" value="<%= loggedInUser.getEmail() %>" required readonly>
				</div>
				
				<div class="form-group">
					<label for="address">Delivery Address</label>
					<textarea id="address" name="address" rows="4" required><%= loggedInUser.getAddress() != null ? loggedInUser.getAddress() : "" %></textarea>
				</div>
				
				<h2 class="section-title" style="margin-top: 35px;"><i class="fas fa-credit-card"></i> Payment Method</h2>
				
				<div class="payment-methods">
					<label class="payment-option">
						<input type="radio" name="paymentMethod" value="Cash On Delivery" checked>
						<i class="fas fa-hand-holding-usd"></i>
						<div class="payment-text">
							<h4>Cash on Delivery (COD)</h4>
						</div>
					</label>
					
					<label class="payment-option">
						<input type="radio" name="paymentMethod" value="UPI">
						<i class="fas fa-mobile-alt"></i>
						<div class="payment-text">
							<h4>UPI / QR Code</h4>
						</div>
					</label>
					
					<label class="payment-option">
						<input type="radio" name="paymentMethod" value="Credit/Debit Card">
						<i class="fas fa-credit-card"></i>
						<div class="payment-text">
							<h4>Credit / Debit Card</h4>
						</div>
					</label>
				</div>
				
				<button type="submit" class="place-order-btn">Place Order (₹<%= grandTotal %>)</button>
				<a href="cart.jsp" class="back-to-cart-btn">Return to Cart</a>
			</form>
		</div>
		
		<!-- Right Side: Order Review -->
		<div class="review-section">
			<h2 class="section-title"><i class="fas fa-clipboard-list"></i> Review Order</h2>
			
			<%
			for (Map.Entry<Integer, CartItem> entry : items.entrySet()) {
				CartItem item = entry.getValue();
			%>
				<div class="review-item">
					<span class="review-item-name"><%= item.getName() %> (x<%= item.getQty() %>)</span>
					<span class="review-item-price">₹<%= item.getPrice() * item.getQty() %></span>
				</div>
			<%
			}
			%>
			
			<div class="review-item" style="margin-top: 15px;">
				<span class="review-item-name">Subtotal</span>
				<span>₹<%= subtotal %></span>
			</div>
			
			<div class="review-item">
				<span class="review-item-name">Delivery Fee</span>
				<span>₹40.00</span>
			</div>
			
			<div class="summary-total">
				<span>Grand Total</span>
				<span>₹<%= grandTotal %></span>
			</div>
		</div>
	</div>

	<!-- Include Razorpay Checkout script -->
	<script src="https://checkout.razorpay.com/v1/checkout.js"></script>
	<script>
		document.getElementById('checkout-form').addEventListener('submit', function(e) {
			var selectedPayment = document.querySelector('input[name="paymentMethod"]:checked').value;
			
			if (selectedPayment === 'UPI' || selectedPayment === 'Credit/Debit Card') {
				e.preventDefault(); // Stop form submission
				
				var grandTotal = <%= grandTotal %>;
				var totalInPaise = Math.round(grandTotal * 100);
				
				var options = {
					"key": "rzp_test_Sfpd6caKSNJFPG",
					"amount": totalInPaise,
					"name": "FoodNest Delivery",
					"description": "Order Payment",
					"image": "data:image/svg+xml,<svg xmlns=%22http://www.w3.org/2000/svg%22 viewBox=%220 0 100 100%22><text y=%22.9em%22 font-size=%2290%22>🍴</text></svg>",
					"handler": function (response) {
						document.getElementById('razorpay_payment_id').value = response.razorpay_payment_id;
						document.getElementById('checkout-form').submit();
					},
					"prefill": {
						"name": document.getElementById('name').value,
						"email": document.getElementById('email').value
					},
					"theme": {
						"color": "#ff6b35"
					}
				};
				
				var rzp = new Razorpay(options);
				rzp.on('payment.failed', function (response){
					alert("Payment failed: " + response.error.description);
				});
				rzp.open();
			}
		});
	</script>
</body>
</html>
