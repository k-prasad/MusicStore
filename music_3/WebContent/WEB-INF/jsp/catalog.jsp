<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
                    "http://www.w3.org/TR/html4/loose.dtd">
<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Music Store :: Catalog</title>
</head>
<body>
<H1>Catalog</H1>
<H2>Choose a product to see more details</H2>

<ol>	
	<c:forEach items="${products}" var="product">
		<c:url value="product.html" var="productUrl">
			<c:param name="productCode" value="${product.code}" />
		</c:url>
		<LI><A href="${productUrl}">${product.description} </a> </LI>
	</c:forEach>
</ol>



	<c:url var="userWelcomeUrl" value="userWelcome.html" />
	<c:url var="cartURL" value="cart.html" />
<UL>	
	<LI><A href="${userWelcomeUrl}">User Home </a> </LI>
	<LI><A HREF="${cartURL}">View Cart</A> </LI>
</UL>

</body>
</html>
