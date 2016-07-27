<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="java.io.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
<%@ page import="javax.servlet.*"%>
<%@ page import="org.slf4j.*"%>
<%@ page import="com.codeforanyone.edujavajsp.model.*"%>
<%@ page import="com.codeforanyone.edujavajsp.database.*"%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>PD User Page</title>
</head>
<body>
<c:set var="userName" value="${param.username}"/>
<!--
PrintWriter pw = resp.getWriter();
if (req.getParameter("username") != null) {
	UserDAO udao = new UserDAO();

	try {
		UserObj u = udao.findUser(req.getParameter("username"));
		if (udao.isCorrectPW(req.getParameter("password"), u)) {
			pw.println("PW matches!");
			req.setAttribute("UserId", (Integer)u.getId());
			pw.print("<br> u.getId(): " + u.getId() );
			req.getRequestDispatcher("/home").forward(req, resp);
		} else {
			resp.sendRedirect("badLogIn.html");
		}
	} catch (SQLException e) {
		e.printStackTrace();
	} catch (UserNotFoundException e) {
		resp.sendRedirect("badLogIn.html");
		e.printStackTrace();
	}
	
} else {
	pw.println("<h1>Hello!</h1>");
}

pw.close();
}
 -->
	<h1>
		Welcome <c:out value="${userName}"/>

	</h1>
	<h2>Petition Drive User Page</h2>
	<br>
	<br>


</body>
</html>