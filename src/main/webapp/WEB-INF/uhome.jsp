<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="MainStyle.css">
<%@ page import="com.codeforanyone.edujavajsp.model.*"%>
<title>Petition Drives Home</title>
</head>
<body>
	<%
		//Information about the user
		UserObj u = (UserObj) session.getAttribute("userObj");
		pageContext.setAttribute("u", u, PageContext.PAGE_SCOPE);
		
		//Contains list of petitions the user is a member of
		PetitionObj [] p = (PetitionObj []) session.getAttribute("petitionObjAry");
		pageContext.setAttribute("p", p, PageContext.PAGE_SCOPE);
	%>
	<h1>
		Welcome
		<c:out value="${u.userName}">Guest</c:out>!
	</h1>
	<h2>Petition Drive User's Page</h2>
	<br>
	<ul>
		<c:forEach var="p" items="${p}">
			<c:url value="/petition" var="purl">
				<c:param name="page" value="home"/>
				<c:param name="id" value="${p.id}"/>
			</c:url>
			<li><a href="${purl}"><c:out value="${p.name}"/></a>
		</c:forEach>
	</ul>
</body>
</html>