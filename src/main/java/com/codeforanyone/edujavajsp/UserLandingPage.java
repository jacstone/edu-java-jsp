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
public class UserLandingPage extends HttpServlet {
	static Logger log = LoggerFactory.getLogger(UserLandingPage.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.debug("Received a doGet() request");
		resp.setContentType("text/html"); // What does it do if you set

		// "text/plain" ? Try it!

		PrintWriter pw = resp.getWriter();
		pw.println("<head>");
		pw.println("<title>PD User Page</title>");
		pw.println("</head>");
		if (req.getParameter("id") != null) {
			UserDAO udao = new UserDAO();
			MemberDAO mdao = new MemberDAO();
			PetitionDAO pdao = new PetitionDAO();

			try {
				UserObj u = udao.get(Integer.valueOf(req.getParameter("id")));
				pw.println("<body>");
				pw.println("<h1 align=center>WELCOME " + u.getUserName() + "!</h1>");
				pw.println("<h2 align=center>Petition Drive User Page</h2><br><br>");

				List<MemberObj> mlist = mdao.searchByUserId(u.getId());
				int mlsize = mlist.size(); // size of member obj list
				pw.println("<strong>Petitions</strong>");
				if (mlsize > 0) {
					pw.println("<ul>");
					for (int i = 0; i < mlsize; i++) {
						MemberObj mtemp = (MemberObj) mlist.get(i);
						PetitionObj ptemp = pdao.get(mtemp.getPetitionId());
									//id is petition_id and id2 is user_id
						pw.println("<li> <a href=\"petition?id="+ptemp.getId()+"&id2="+u.getId()+"\">" + ptemp.getName()+"</a>");

					}
					pw.println("</ul>");
				} else {
					pw.println("<p>No Petitions Found. Please help a petition drive!</p>");
				}
				pw.println("</body>");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UserNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (PetitionNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			pw.println("<h1>Hello!</h1>");
		}

		pw.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.debug("Received a doPost() request");
		doGet(req, resp);
	}

}
