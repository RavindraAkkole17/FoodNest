package com.tap.Servlet;

import java.io.IOException;
import java.util.List;

import com.tap.DAOImpl.UserDAOImpl;
import com.tap.Model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		
		UserDAOImpl userDAOImpl = new UserDAOImpl();
		List<User> users = userDAOImpl.getAllUser();
		User matchedUser = null;
		
		if (users != null && email != null && password != null) {
			for (User u : users) {
				if (email.equalsIgnoreCase(u.getEmail()) && password.equals(u.getPassword())) {
					matchedUser = u;
					break;
				}
			}
		}
		
		if (matchedUser != null) {
			HttpSession session = req.getSession();
			session.setAttribute("user", matchedUser);
			resp.sendRedirect("home");
		} else {
			resp.sendRedirect("login.html");
		}
	}
}
