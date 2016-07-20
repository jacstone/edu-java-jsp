<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

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
	<%
		int uId = (Integer) request.getAttribute("UserId");
		if ((Integer) uId == null) {
	%>
	Object did not pass
	<%
		;
		} else {
	%>
	<%-- Loading the Information about the User --%>
	<%
		UserDAO udao = new UserDAO();
			UserObj u = udao.get(uId);
	%>
	<h1>
		Welcome
		<%=u.getUserName()%>!
	</h1>
	<h2>Petition Drive User Page</h2>
	<br>
	<br>
	<%
		;
		}
	%>

</body>
</html>