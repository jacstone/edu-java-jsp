package com.codeforanyone.edujavajsp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codeforanyone.edujavajsp.database.UserDAO;
import com.codeforanyone.edujavajsp.model.UserNotFoundException;
import com.codeforanyone.edujavajsp.model.UserObj;

public class UserLandingPage extends HttpServlet {
	static Logger log = LoggerFactory.getLogger(UserLandingPage.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.debug("Received a doGet() request");
		resp.setContentType("text/html"); // What does it do if you set
											// "text/plain" ? Try it!

		PrintWriter pw = resp.getWriter();
		pw.println("<h2>WELCOME! </h2>");
/*		if (req.getParameter("username") != null) {
			UserDAO udao = new UserDAO();

			try {
				UserObj u = udao.findUser(req.getParameter("username"));
				if (udao.isCorrectPW(req.getParameter("password"), u)) {
					pw.println("PW matches!");
				} else {
					resp.sendRedirect("badLogIn.html");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UserNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else {
			pw.println("<h1>Hello!</h1>");
		}

*/		pw.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.debug("Received a doPost() request");
		doGet(req, resp);
	}

}
