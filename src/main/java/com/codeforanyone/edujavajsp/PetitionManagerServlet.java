package com.codeforanyone.edujavajsp;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.codeforanyone.edujavajsp.database.MemberDAO;
import com.codeforanyone.edujavajsp.database.PetitionDAO;
import com.codeforanyone.edujavajsp.database.RoleDAO;
import com.codeforanyone.edujavajsp.model.MemberNotFoundException;
import com.codeforanyone.edujavajsp.model.MemberObj;
import com.codeforanyone.edujavajsp.model.PetitionNotFoundException;
import com.codeforanyone.edujavajsp.model.PetitionObj;
import com.codeforanyone.edujavajsp.model.RoleNotFoundException;
import com.codeforanyone.edujavajsp.model.UserObj;

/**
 * Servlet implementation class PetitionManagerServlet
 */

public class PetitionManagerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public PetitionManagerServlet() {
		super();
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
			
		if(req.getParameter("page").equals("home")){
				homepage(req,res);
		}
		else{
			res.sendRedirect("Oops.html");
		}
		
	}
	private void homepage(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		/*
		 * HttpSession should contain prior to redirect:
		 * userObj (info about user)
		 * petitionObj (info about petition)
		 * memberObj (info about user relation to petition)
		 * roleName (name of the role the user has on this petition)
		 */
		
		HttpSession session = req.getSession();
		
		MemberDAO mdao = new MemberDAO();
		PetitionDAO pdao = new PetitionDAO();
		RoleDAO rdao = new RoleDAO();
		MemberObj m = null;
		PetitionObj p = null;
		
		UserObj u = (UserObj) session.getAttribute("userObj");
		
		try {
			m = mdao.findMember(u.getId(), Integer.valueOf(req.getParameter("id")));
			p = pdao.get(Integer.valueOf(req.getParameter("id")));

			//returns the name of the role that is associated with the member obj
			session.setAttribute("roleName", rdao.get(m.getRoleId()).getName());

		} catch (NumberFormatException e) {
			res.sendRedirect("Oops.html");
			e.printStackTrace();
		} catch (SQLException e) {
			res.sendRedirect("Oops.html");
			e.printStackTrace();
		} catch (MemberNotFoundException e) {
			res.sendRedirect("Oops.html");
			e.printStackTrace();
		} catch (PetitionNotFoundException e) {
			res.sendRedirect("Oops.html");
			e.printStackTrace();
		} catch (RoleNotFoundException e) {
			res.sendRedirect("Oops.html");
			e.printStackTrace();
		}
		 
		session.setAttribute("memberObj", m);
		session.setAttribute("petitionObj", p);
		req.getRequestDispatcher("/WEB-INF/phome.jsp").forward(req, res);
	
	}

}
