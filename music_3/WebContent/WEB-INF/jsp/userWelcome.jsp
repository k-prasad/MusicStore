<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
                    "http://www.w3.org/TR/html4/loose.dtd">
<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Music Store</title>
</head>
<body>
<H1>Welcome to the Music Store!</H1>
<H2>Where would you like to go?</H2>

	<c:url var="catalogURL" value="catalog.html" />
	<c:url var="cartURL" value="cart.html" />
<UL>	
	<LI><A href="${catalogURL}">Browse Catalog </a> </LI>
	<LI><A HREF="${cartURL}">View Cart</A> </LI>
	<LI><A HREF="welcome.html">Back to Site Homepage</A></LI>
</UL>

</body>
</html>
