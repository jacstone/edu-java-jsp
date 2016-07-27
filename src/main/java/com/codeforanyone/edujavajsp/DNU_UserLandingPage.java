package com.codeforanyone.edujavajsp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codeforanyone.edujavajsp.database.MemberDAO;
import com.codeforanyone.edujavajsp.database.PetitionDAO;
import com.codeforanyone.edujavajsp.database.UserDAO;
import com.codeforanyone.edujavajsp.model.MemberObj;
import com.codeforanyone.edujavajsp.model.PetitionNotFoundException;
import com.codeforanyone.edujavajsp.model.PetitionObj;
import com.codeforanyone.edujavajsp.model.UserNotFoundException;
import com.codeforanyone.edujavajsp.model.UserObj;

@SuppressWarnings("serial")
public class DNU_UserLandingPage extends HttpServlet {
	static Logger log = LoggerFactory.getLogger(DNU_UserLandingPage.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.debug("Received a doGet() request");
		resp.setContentType("text/html"); // What does it do if you set

		// "text/plain" ? Try it!

		if (req.getParameter("id") != null) {
			UserDAO udao = new UserDAO();
			MemberDAO mdao = new MemberDAO();
			PetitionDAO pdao = new PetitionDAO();

			try {
				UserObj u = udao.get(Integer.valueOf(req.getParameter("id")));

				List<MemberObj> mlist = mdao.searchByUserId(u.getId());
				int mlsize = mlist.size(); // size of member obj list
				req.getRequestDispatcher("/home").forward(req, resp);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UserNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.debug("Received a doPost() request");
		doGet(req, resp);
	}

}
