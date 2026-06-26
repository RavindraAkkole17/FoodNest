package com.tap.Servlet;

import java.io.IOException;
import java.util.List;

import com.tap.DAOImpl.MenuDAOImpl;
import com.tap.Model.Menu;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/menu")

public class MenuServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int restaurantId = Integer.parseInt(req.getParameter("restaurantId"));
		MenuDAOImpl menuDAOImpl = new MenuDAOImpl();
		List<Menu> allMenu = menuDAOImpl.getAllMenu(restaurantId);
		req.setAttribute("allMenu", allMenu);
		RequestDispatcher rd = req.getRequestDispatcher("/menu.jsp");
		rd.forward(req, resp);
		
	}

}
