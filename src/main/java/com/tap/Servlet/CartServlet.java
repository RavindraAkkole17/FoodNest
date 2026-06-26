package com.tap.Servlet;

import java.io.IOException;

import com.tap.DAOImpl.MenuDAOImpl;
import com.tap.Model.Cart;
import com.tap.Model.CartItem;
import com.tap.Model.Menu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/callCartServlet")
public class CartServlet extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		Cart cart = (Cart)session.getAttribute("cart");
		
		String action = req.getParameter("action");
		
		if ("add".equals(action)) {
			String newRestIdStr = req.getParameter("restaurantId");
			if (newRestIdStr != null) {
				int newRestaurantId = Integer.parseInt(newRestIdStr);
				Integer sessionRestaurantId = (Integer)session.getAttribute("restaurantId");
				
				if (cart == null || sessionRestaurantId == null || sessionRestaurantId != newRestaurantId) {
					cart = new Cart();
					session.setAttribute("cart", cart);
					session.setAttribute("restaurantId", newRestaurantId);
				}
			}
		}
		
		if (cart == null) {
			cart = new Cart();
			session.setAttribute("cart", cart);
		}
		
		if (action != null) {
			if (action.equals("add")) {
				addItemToCart(req, cart);
			} else if (action.equals("update")) {
				updateItemToCart(req, cart);
			} else if (action.equals("delete")) {
				deleteItemToCart(req, cart);
			}
		}
		
		resp.sendRedirect("cart.jsp");
	}

	private void deleteItemToCart(HttpServletRequest req, Cart cart) {
		String menuIdStr = req.getParameter("menuId");
		if (menuIdStr != null) {
			int menuId = Integer.parseInt(menuIdStr);
			cart.removeItem(menuId);
		}
	}

	private void updateItemToCart(HttpServletRequest req, Cart cart) {
		String menuIdStr = req.getParameter("menuId");
		String qtyStr = req.getParameter("qty");
		if (menuIdStr != null && qtyStr != null) {
			int menuId = Integer.parseInt(menuIdStr);
			int qty = Integer.parseInt(qtyStr);
			cart.updateItem(menuId, qty);
		}
	}

	private void addItemToCart(HttpServletRequest req, Cart cart) {
		String menuIdStr = req.getParameter("menuId");
		String qtyStr = req.getParameter("qty");
		if (menuIdStr != null && qtyStr != null) {
			int menuId = Integer.parseInt(menuIdStr);
			int qty = Integer.parseInt(qtyStr);
			
			MenuDAOImpl menuDAOImpl = new MenuDAOImpl();
			Menu menu = menuDAOImpl.getMenu(menuId);
			
			if (menu != null) {
				CartItem cartItem = new CartItem(menuId, menu.getRestaurantID(), menu.getItemName(), menu.getPath(), menu.getPrice(), qty);
				cart.addItem(cartItem);
			}
		}
	}
}
