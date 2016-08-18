<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="MainStyle.css">
<%@ page import="com.codeforanyone.edujavajsp.model.*"%>
<title>Petition Drives Login</title>
</head>
<body>
<h1>Petition Drives</h1>
<h2>Successful drives need organization</h2>


<div style="border: 1px solid #DDDDDD; padding: 10px;">
<form method="get" action="/login">
<p>Login</p>
<p>User Name: <input type="text" name="username" size="30"></p>
<p>Password: <input type="password" name="password" size="30"></p>
<p><input type=submit value="LogIn"></p>
</form>
</div>


</body>
</html>