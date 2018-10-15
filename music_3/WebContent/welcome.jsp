<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
  <head>
    <title>Music Store System</title>
  </head>

<body>
	<c:url var="adminWelcome" value="/adminController/adminWelcome.html"/>
	<c:url var="userWelcome" value="userWelcome.html"/>
	<c:url var="listVarsURL" value='/adminController/listVariables.html' />
	<c:url var="logoutURL" value='/adminController/logout.html' />

	<a href="${adminWelcome}">Admin Service</a>
	<br>
	<a href="${userWelcome}">User Service</a>
	<br>
	<a href="${listVarsURL}">List Variables</a>
	<br>
	<a href="${logoutURL}">Logout</a>
	<br>

</body>
</html>


