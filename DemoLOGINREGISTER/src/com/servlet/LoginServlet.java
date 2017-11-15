package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public LoginServlet() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter printWriter = response.getWriter();
		System.out.println("IN GET");
		String name = request.getParameter("name");
		String pass = request.getParameter("pass");
		boolean flag = true;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/loginregister","root","root");
			
			PreparedStatement preparedStatement = connection.prepareStatement("select * from user where name=? and pass=?");
			preparedStatement.setString(1, name);
			preparedStatement.setString(2,pass);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			flag = resultSet.next();
			
			if (flag) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("/welcome.html");
				dispatcher.forward(request, response);
			} else {
				printWriter.println("INVALID LOGIN");
				RequestDispatcher dispatcher = request.getRequestDispatcher("/login.html");
				dispatcher.include(request, response);
			}
			connection.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
