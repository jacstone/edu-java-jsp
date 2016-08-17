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

import com.codeforanyone.edujavajsp.database.DataDAO;
import com.codeforanyone.edujavajsp.database.MemberDAO;
import com.codeforanyone.edujavajsp.database.PetitionDAO;
import com.codeforanyone.edujavajsp.database.RoleDAO;
import com.codeforanyone.edujavajsp.database.UserDAO;
import com.codeforanyone.edujavajsp.model.DataObj;
import com.codeforanyone.edujavajsp.model.MemberNotFoundException;
import com.codeforanyone.edujavajsp.model.MemberObj;
import com.codeforanyone.edujavajsp.model.PetitionNotFoundException;
import com.codeforanyone.edujavajsp.model.PetitionObj;
import com.codeforanyone.edujavajsp.model.RoleNotFoundException;
import com.codeforanyone.edujavajsp.model.RoleObj;
import com.codeforanyone.edujavajsp.model.UserNotFoundException;
import com.codeforanyone.edujavajsp.model.UserObj;

@SuppressWarnings("serial")
public class DNU_PetitionDataServlet extends HttpServlet {
	static Logger log = LoggerFactory.getLogger(DNU_PetitionDataServlet.class);

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
				pw.println("<h2 align=center>" + u.getUserName() + " - " + r.getName() + "'s Data List</h2><br><br>");
				pw.println("<strong>Actions</strong>");
				pw.println("<ul>");
				pw.println("<li><a href=/modifyData?type=search>Search</a>");
				pw.println("<li><a href=/modifyData?type=add>Add Data</a>");
				pw.println("<li><a href=/modifyData?type=update>Update Row</a>");
				pw.println("<li><a href=/modifyData?type=delete>Delete Row</a>");
				pw.println("<li><a href=/modifyData?type=role>Role Page</a>");
				pw.println("</ul>");
				List<DataObj> dlist;
				if(r.getName().equalsIgnoreCase("volunteer")){
					dlist = ddao.list(p.getId(), u.getId());
					pw.println("<br><br><strong>Data Collected by You</strong><br>");
					
				}else{
					dlist = ddao.list(p.getId());
					pw.println("<br><br><strong>Data Entered:</strong><br>");

				}
				if (dlist.size() > 0) {
					pw.println("<table style=\"width:100%\">");
					pw.println(
							"<tr><td>Id Number</td><td>Date</td><td>Time</td><td>Signatures Collected</td><td>Address</td>");
					int totalSigs = 0;
					for (int i = 0; i < dlist.size(); i++) {
						DataObj dtemp = dlist.get(i);
						totalSigs += dtemp.getSignatures();
						pw.println("<tr><td>" + dtemp.getId() + "</td><td>" + dtemp.getDate() + "</td><td>"
								+ dtemp.getStartTime() + " - " + dtemp.getStopTime() + "</td><td>"
								+ dtemp.getSignatures() + "</td><td>" + dtemp.getAddress() + ", " + dtemp.getCity()
								+ ", " + dtemp.getState() + " " + dtemp.getZip() + "</td>");
					}
					pw.println("</table>");
					pw.println("Total number of signatures gathered is " + totalSigs + ".");
				} else {
					pw.println("You have not entered any data.");
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