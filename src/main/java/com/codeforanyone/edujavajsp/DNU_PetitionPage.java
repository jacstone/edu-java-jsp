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

import com.codeforanyone.edujavajsp.database.DataDAO;
import com.codeforanyone.edujavajsp.database.MemberDAO;
import com.codeforanyone.edujavajsp.database.PetitionDAO;
import com.codeforanyone.edujavajsp.database.RoleDAO;
import com.codeforanyone.edujavajsp.database.UserDAO;
import com.codeforanyone.edujavajsp.model.MemberNotFoundException;
import com.codeforanyone.edujavajsp.model.MemberObj;
import com.codeforanyone.edujavajsp.model.PetitionNotFoundException;
import com.codeforanyone.edujavajsp.model.PetitionObj;
import com.codeforanyone.edujavajsp.model.RoleNotFoundException;
import com.codeforanyone.edujavajsp.model.RoleObj;
import com.codeforanyone.edujavajsp.model.UserNotFoundException;
import com.codeforanyone.edujavajsp.model.UserObj;

@SuppressWarnings("serial")
public class DNU_PetitionPage extends HttpServlet {
	static Logger log = LoggerFactory.getLogger(DNU_PetitionPage.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.debug("Received a doGet() request");
		resp.setContentType("text/html"); // What does it do if you set

		// "text/plain" ? Try it!

		PrintWriter pw = resp.getWriter();
		if (req.getParameter("id") != null && req.getParameter("id2") != null) {
			UserDAO udao = new UserDAO();
			MemberDAO mdao = new MemberDAO();
			PetitionDAO pdao = new PetitionDAO();
			RoleDAO rdao = new RoleDAO();
			DataDAO ddao = new DataDAO();

			try {
				UserObj u = udao.get(Integer.valueOf(req.getParameter("id2")));
				PetitionObj p = pdao.get(Integer.valueOf(req.getParameter("id")));
				MemberObj m = mdao.findMember(u.getId(), p.getId());
				RoleObj r = rdao.get(m.getRoleId());
				pw.println("<head>");
				pw.println("<title>PD - " + p.getName() + "</title>");
				pw.println("</head>");
				pw.println("<body>");
				pw.println("<h1 align=center>" + p.getName() + "</h1>");
				pw.println("<h2 align=center>" + u.getUserName() + " - " + r.getName() + "'s Page</h2><br><br>");
				pw.println("<strong>Actions</strong>");
				pw.println("<ul>");
				pw.println("<li><a href=/petitionMember?id="+p.getId()+"&id2="+u.getId()+">Member List</a>");
				pw.println("<li><a href=/petitionData?id="+p.getId()+"&id2="+u.getId()+">Petition Data</a>");
				pw.println("<li><a href=/petitionExport?id="+p.getId()+"&id2="+u.getId()+">Export Data</a>");
				pw.println("</ul>");
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
			} catch (MemberNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RoleNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			pw.println("<h1>Hello! Sorry for the inconvience but something went wrong.</h1>");
		}

		pw.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.debug("Received a doPost() request");
		doGet(req, resp);
	}

}