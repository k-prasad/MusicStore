<!DOCTYPE HTML>
<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Music Store :: Listen</title>
</head>
<body>
<H1>Listen to Tracks from ${product.description}</H1>
<H2>Choose an mp3 to listen to.</H2>

	
	<table>
		<tr>
			<td><b>Track Title</b></td>
			<td><b>Click to Listen</b></td>
		</tr>
		<c:forEach items="${product.orderedTracks}" var="track">
			<tr>
				<!-- the following didn't work with download.html (some kind of browser problem) -->
				<c:url value="download.do" var="downloadURL">
					<c:param name="trackNum" value="${track.trackNumber}" />
				</c:url>
				<td><b>${track.title}</b></td>
				<td><audio controls preload="none">
						<source src="${downloadURL}" />
					</audio></td>
			</tr>
		</c:forEach>
	</table>
	
	<c:url var="addToCartUrl" value="cart.html">
		<c:param name="addItem" value="true" />
	</c:url>
	<a href="${addToCartUrl}">Add this CD to Cart</a>
	
	<c:url var="catalogUrl" value="catalog.html" />
	<c:url var="cartUrl" value="cart.html" />
	<c:url var="userWelcomeUrl" value="userWelcome.html" />
	<c:url var="productUrl" value="product.html" >
		<c:param name="productCode" value="${product.code}" />
	</c:url>
	<UL>
		<LI><A HREF="${productUrl}">Back to Product Page</a></li>
		<LI><A href="${catalogUrl}">Browse Catalog </a></LI>
		<LI><A HREF="${cartUrl}">View Cart</A></LI>
		<LI><A href="${userWelcomeUrl}">User Home </a></LI>
	</UL>

</body>
</html>
