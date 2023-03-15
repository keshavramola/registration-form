package com.registration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public LoginServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uemail = request.getParameter("username");
        String upassword = request.getParameter("password");
        HttpSession session = request.getSession();
        RequestDispatcher rd = null;
        
        try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mywebapp","root","test");
		    Statement stmnt = con.createStatement();
		    ResultSet result = stmnt.executeQuery("select * from users where uemail = '"+uemail+"' and upassword = '"+upassword+"'");
		    
		    if(result.next()) {
		    	session.setAttribute("name", result.getString("uname"));
		    	rd = request.getRequestDispatcher("index.jsp");
		    }else {
		    	request.setAttribute("status", "failed");
		        rd = request.getRequestDispatcher("login.jsp");	
		    }
		    rd.forward(request, response);
			
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}
