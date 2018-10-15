<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html">
<html>
<head>
<title>View Invoices</title>
</head>
<body>
<I>View Invoices</I><br/> <br/>

<b>Date:</b>"${pInvoice.invoiceDate}"<br/><br/>
<b>Email Address:</b>"${pInvoice.getUser().getEmailAddress()}"<br/><br/>
			
<table>
	<tr> 
  		<td><b>Quantity</b></td>
  		<td><b>Description</b></td>
  		<td><b>Product Price</b></td>
	</tr>

	<c:forEach var="lineitems" items="${pInvoice.getLineItems()}">
	<tr>
  		<td>${lineitems.getQuantity()}</td>
  		<td> ${lineitems.getProduct().getDescription()}</td>
  		<td> ${lineitems.getProduct().getPrice()}</td>
  	</tr>
</c:forEach>

</table>

<br/><br/>
  	
<form action="processInvoice.html" method="post">
	<input type="hidden" name="pId" value="${pInvoice.invoiceId}">
	<input type="submit" value="Process Invoice">
</form>
  	


</body>
</html>