<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
                    "http://www.w3.org/TR/html4/loose.dtd">
<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Music Store :: Product Details</title>
</head>
<body>
<H1>Product Details</H1>
<H2>${product.description}</H2>

<c:url var="listenURL" value="${listenNextUrl}" />
<c:url var="addToCartUrl" value="cart.html">
	<c:param name="addItem" value="true"/>
</c:url>

<ul>
	<li>Product Code: ${product.code}</li>
	<li>Price: ${product.price}</li>
	<li><a href="${listenURL}">Listen to samples</a></li>
	<li><a href="${addToCartUrl}">Add to Cart</a></li>
</ul>

	<c:url var="catalogURL" value="catalog.html" />
	<c:url var="cartUrl" value="cart.html" />
	<c:url var="userWelcomeUrl" value="userWelcome.html" />
<UL>	
	<LI><A href="${catalogURL}">Browse Catalog </a> </LI>
	<LI><A HREF="${cartURL}">View Cart</A> </LI>
	<LI><A href="${userWelcomeUrl}">User Home </a> </LI>
</UL>

</body>
</html>
