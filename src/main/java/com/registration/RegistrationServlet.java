package com.registration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public RegistrationServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
		String uname = request.getParameter("name");
	    String uemail = request.getParameter("email");
	    String upassword = request.getParameter("pass");
	    String umobile = request.getParameter("contact");
	    RequestDispatcher rd = null;
	    Connection con = null;
	   try {
		   Class.forName("com.mysql.cj.jdbc.Driver");
		   con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mywebapp","root","test");
           PreparedStatement ps = con.prepareStatement("insert into users(uname,uemail,upassword,umobile) values(?,?,?,?)");
           ps.setString(1, uname);
           ps.setString(2, uemail);
           ps.setString(3, upassword);
           ps.setString(4, umobile);
           
           int count = ps.executeUpdate();
		   
		   rd = request.getRequestDispatcher("registration.jsp");
		   
		   if(count > 0) {
			  request.setAttribute("status", "success"); 
		   }else {
			  request.setAttribute("status", "failed");
		   }
		   rd.forward(request, response);
	} catch (Exception e) {
        e.printStackTrace();
	}
	   finally {
		   try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	   }
	}

}
