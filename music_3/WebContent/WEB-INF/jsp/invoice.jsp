<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
                    "http://www.w3.org/TR/html4/loose.dtd">
<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Music Store :: Invoice</title>
</head>
<body>
<H1>Invoice</H1>
<H2>Thanks for your order!</H2>

<ul>
	<li>Invoice number: ${invoice.invoiceId}</li>
	<li>Total price: ${invoice.totalAmount}</li>
	<!-- It would be nice to show lineitems, but we are not supporting them in InvoiceData -->
</ul>

	<c:url var="catalogUrl" value="catalog.html" />
	<c:url var="cartUrl" value="cart.html" />
	<c:url var="userWelcomeUrl" value="userWelcome.html" />
<UL>	
	<LI><A href="${catalogUrl}">Browse Catalog </a> </LI>
	<LI><A HREF="${cartUrl}">View Cart</A> </LI>
	<LI><A href="${userWelcomeUrl}">User Home </a> </LI>
</UL>

</body>
</html>
