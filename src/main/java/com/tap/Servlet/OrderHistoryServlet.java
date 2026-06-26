package com.tap.Servlet;

import java.io.IOException;
import java.util.List;

import com.tap.DAOImpl.OrderTableDAOImpl;
import com.tap.Model.OrderTable;
import com.tap.Model.User;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/orderHistory")
public class OrderHistoryServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		User loggedInUser = (User) session.getAttribute("user");
		
		if (loggedInUser == null) {
			resp.sendRedirect("login.html");
			return;
		}
		
		OrderTableDAOImpl orderTableDAOImpl = new OrderTableDAOImpl();
		List<OrderTable> orderList = orderTableDAOImpl.getOrdersByUserId(loggedInUser.getUserId());
		
		req.setAttribute("orderList", orderList);
		
		RequestDispatcher rd = req.getRequestDispatcher("/orderHistory.jsp");
		rd.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
