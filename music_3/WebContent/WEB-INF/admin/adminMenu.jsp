<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Admin Menu</title>
</head>
<body>
	<h1>Admin Menu</h1>
	<c:url var="initDbUrl" value="initDB.html" />
	<c:url var="processInvoicesUrl" value="processInvoices.html" />
	<c:url var="reportsUrl" value="displayReports.html" />
	<c:url var="logoutUrl" value="logout.html" />
	
	<form action="${initDbUrl}" method="get">
		<input type="submit" value="Initialize DB"><br />
	</form>
	<form action="${processInvoicesUrl}" method="get">
		<input type="submit" value="Process Invoices"><br />
	</form>
	<form action="${reportsUrl}" method="get">
		<input type="submit" value="Display Reports"><br />
	</form>
		<form action="${logoutUrl}" method="get">
		<input type="submit" value="Logout"><br />
	</form>

</body>
</html>