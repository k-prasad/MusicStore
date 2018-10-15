<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
                    "http://www.w3.org/TR/html4/loose.dtd">
<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Music Store :: Register</title>
</head>
<body>
<H1>Register</H1>
<h2>Create a user account or sign in</h2>

		<!-- nextPageURL: listen.html or invoice.html-->
		<c:url var="nextPageUrl" value="${nextPageURL}"/>
		<form method="get" action="${nextPageUrl}">
			First name: <input type="text" name="firstName"> <br />
			Last name: <input type="text" name="lastName"> <br />
			Email Address: <input type="text" name="email"> <br />
			<input type="submit" value="Register">
		</form>


	<c:url var="catalogURL" value="catalog.html" />
	<c:url var="userWelcomeUrl" value="userWelcome.html" />
	<c:url var="cartURL" value="cart.html" />
<UL>	
	<LI><A HREF="${cartURL}">View Cart</A> </LI>
	<LI><A href="${catalogURL}">Browse Catalog </a> </LI>
	<LI><A href="${userWelcomeUrl}">User Home </a> </LI>
</UL>

</body>
</html>
