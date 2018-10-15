<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
                    "http://www.w3.org/TR/html4/loose.dtd">
<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Music Store :: Shopping Cart</title>
</head>
<body>
<H1>Shopping cart</H1>

<ul>
	<c:forEach items="${cart.items}" var="item">
		<li>${item.product.code} - ${item.product.description} - ${item.quantity} @ ${item.product.price} each</li>
	</c:forEach>

</ul>

	<c:url var="catalogURL" value="catalog.html" />
	<c:url var="checkoutURL" value="${invoiceNextUrl}" />
	<c:url var="userWelcomeUrl" value="userWelcome.html" />
<UL>	
	<LI><A HREF="${checkoutURL}">Checkout</A> </LI>
	<LI><A href="${catalogURL}">Browse Catalog </a> </LI>
	<LI><A href="${userWelcomeUrl}">User Home </a> </LI>
</UL>

</body>
</html>
