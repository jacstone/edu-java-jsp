<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	//Information about the user
	UserObj u = (UserObj) session.getAttribute("userObj");
	pageContext.setAttribute("u", u, PageContext.PAGE_SCOPE);

	//Information about the petition
	PetitionObj p = (PetitionObj) session.getAttribute("petitionObj");
	pageContext.setAttribute("p", p, PageContext.PAGE_SCOPE);

	//Relation between user and petition
	MemberObj m = (MemberObj) session.getAttribute("memberObj");
	pageContext.setAttribute("m", m, PageContext.PAGE_SCOPE);

	String roleName = (String) session.getAttribute("roleName");
	pageContext.setAttribute("roleName", roleName, PageContext.PAGE_SCOPE);
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="MainStyle.css">
<%@ page import="com.codeforanyone.edujavajsp.model.*"%>
<title>Petition: <c:out value="${p.name}">Unknown Petition</c:out></title>
</head>
<body>
	<h1>
		<c:out value="${p.name}">Unknown Petition</c:out>
	</h1>
	<h2>
		<c:out value="${u.userName}">Guest</c:out>
		-
		<c:out value="${roleName}">Unknown Role</c:out>'s Page
	</h2>

	<div>
		<p>Actions</p>
		<ul>
			<c:choose>
				<c:when test="${roleName == 'Organizer' or roleName == 'Admin'}">
					<li>Petition Data</li>
					<li>Member List</li>
					<li>Export Data</li>
				</c:when>
				<c:when test="${roleName == 'Coordinator'}">
					<li>Petition Data</li>
					<li>Member List</li>
				</c:when>
				<c:when test="${roleName == 'Volunteer'}">
					<li>Petition Data</li>
				</c:when>
				<c:otherwise>
					<li>Petition Data</li>
				</c:otherwise>
			</c:choose>
		</ul>
	</div>
	<p>Queries to show</p>
</body>
</html>