<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="java.util.List,com.tap.Model.Menu"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Menu</title>

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">

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

/* Header */
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

/* Menu Grid */
.menu-container {
	width: 95%;
	margin: 30px auto;
	display: grid;
	grid-template-columns: repeat(auto-fill, minmax(320px, 1fr));
	gap: 25px;
}

/* Menu Card */
.menu-card {
	background: #1f1f1f;
	border-radius: 15px;
	overflow: hidden;
	box-shadow: 0 4px 15px rgba(0, 0, 0, 0.4);
	transition: all 0.3s ease;
}

.menu-card:hover {
	transform: translateY(-8px);
	box-shadow: 0 8px 20px rgba(255, 107, 53, 0.35);
}

.menu-card img {
	width: 100%;
	height: 220px;
	object-fit: cover;
}

/* Content */
.menu-content {
	padding: 18px;
}

.menu-content h3 {
	color: white;
	margin-bottom: 10px;
	font-size: 22px;
}

.desc {
	color: #bdbdbd;
	font-size: 14px;
	line-height: 1.5;
	min-height: 45px;
	margin-bottom: 12px;
}

.price {
	color: #ff6b35;
	font-size: 24px;
	font-weight: bold;
	margin-bottom: 10px;
}

.category {
	display: inline-block;
	background: #333;
	color: white;
	padding: 6px 12px;
	border-radius: 20px;
	font-size: 13px;
	margin-bottom: 10px;
}

.available {
	color: #4caf50;
	font-weight: bold;
	margin-top: 10px;
}

.not-available {
	color: #f44336;
	font-weight: bold;
	margin-top: 10px;
}

/* Button */
.btn {
	width: 100%;
	margin-top: 15px;
	background: #ff6b35;
	color: white;
	border: none;
	padding: 12px;
	border-radius: 8px;
	cursor: pointer;
	font-size: 16px;
	font-weight: bold;
	transition: 0.3s;
}

.btn:hover {
	background: #ff824d;
}

.no-items {
	text-align: center;
	margin-top: 100px;
	font-size: 22px;
	color: #aaa;
}
</style>

</head>
<body>

	<div class="header">
		<h1>
			<i class="fas fa-utensils"></i> Restaurant Menu
		</h1>
		<div class="nav-links">
			<a href="home"><i class="fas fa-home"></i> Home</a>
			<a href="cart.jsp"><i class="fas fa-shopping-cart"></i> Cart</a>
			<%
			com.tap.Model.User loggedInUser = (com.tap.Model.User) session.getAttribute("user");
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
List<Menu> allMenu = (List<Menu>)request.getAttribute("allMenu");
%>

	<% if(allMenu != null && !allMenu.isEmpty()) { %>

	<div class="menu-container">

		<%
for(Menu menu : allMenu){
%>

		<div class="menu-card">

			<img src="<%= menu.getPath() %>" alt="<%= menu.getItemName() %>">

			<div class="menu-content">

				<h3><%= menu.getItemName() %></h3>

				<p class="desc">
					<%= menu.getDescription() %>
				</p>

				<div class="price">
					₹<%= menu.getPrice() %>
				</div>

				<div class="category">
					<%= menu.getCategory() %>
				</div>

				<% if(menu.isAvailable()) { %>

				<p class="available">
					<i class="fas fa-check-circle"></i> Available
				</p>

				<% } else { %>

				<p class="not-available">
					<i class="fas fa-times-circle"></i> Out of Stock
				</p>

				<% } %>
				<form action="callCartServlet">
				<input type = "hidden" name = "menuId" value="<%=menu.getMenuID()%>">
				<input type = "hidden" name="qty" value="1" >
				<input type = "hidden" name = "restaurantId" value="<%=menu.getRestaurantID()%>">
				<input type = "hidden" name = "action" value="add">

					<button class="btn">
						<i class="fas fa-cart-plus"></i> Add To Cart
					</button>
				</form>


			</div>

		</div>

		<%
}
%>

	</div>

	<% } else { %>

	<div class="no-items">
		<i class="fas fa-utensils"></i> No Menu Items Available
	</div>

	<% } %>

</body>
</html>