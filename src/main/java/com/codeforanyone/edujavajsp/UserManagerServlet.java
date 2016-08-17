package com.codeforanyone.edujavajsp;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.codeforanyone.edujavajsp.database.MemberDAO;
import com.codeforanyone.edujavajsp.database.PetitionDAO;
import com.codeforanyone.edujavajsp.database.UserDAO;
import com.codeforanyone.edujavajsp.model.MemberObj;
import com.codeforanyone.edujavajsp.model.PetitionNotFoundException;
import com.codeforanyone.edujavajsp.model.PetitionObj;
import com.codeforanyone.edujavajsp.model.UserNotFoundException;
import com.codeforanyone.edujavajsp.model.UserObj;

/**
 * Servlet implementation class UserManagerServlet
 */
@WebServlet({ "/UserManagerServlet", "/user" })
public class UserManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserManagerServlet() {
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
		HttpSession session = req.getSession();
		UserDAO udao = new UserDAO();
		MemberDAO mdao = new MemberDAO();
		PetitionDAO pdao = new PetitionDAO();
		UserObj uobj = null;
		List<MemberObj> mobj = null;
		PetitionObj [] pobj = null;
		int userId = (Integer)session.getAttribute("UserId");
		try {
			uobj = udao.get(userId);
			mobj = mdao.searchByUserId(userId);
			pobj = new PetitionObj [mobj.size()];
			for(int i = 0; i<mobj.size(); i++){
				//finds the petition object from the member object in the list by using the petition id within the member object
				pobj[i] = pdao.get(mobj.get(i).getPetitionId());
			}
		} catch (SQLException e) {
			res.sendRedirect("Oops.html");
			e.printStackTrace();
		} catch (UserNotFoundException e) {
			res.sendRedirect("Oops.html");
			e.printStackTrace();
		} catch (PetitionNotFoundException e) {
			res.sendRedirect("Oops.html");
			e.printStackTrace();
		}
		session.removeAttribute("UserId");
		session.setAttribute("userObj", uobj);
		session.setAttribute("petitionObjAry", pobj);
		req.getRequestDispatcher("/WEB-INF/uhome.jsp").forward(req, res);
	
	}

}
