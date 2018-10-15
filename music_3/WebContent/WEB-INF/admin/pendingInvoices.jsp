<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Pending Invoices</title>
</head>
<body>
	<h1>Pending Invoices (if any)</h1>

	<h2>Choose an invoice to process.</h2>

	<ul>
		<c:forEach items="${unProcessedInvoices}" var="invoice">
			<c:url value="viewInvoice.html" var="processInvoicesUrl">
				<c:param name="invoiceId" value="${invoice.invoiceId}" />
			</c:url>
			<li>${invoice.invoiceId}--${invoice.userFullName} ${invoice.invoiceDate} -
				${invoice.totalAmount}  <a href="${processInvoicesUrl}">Process</a>
			</li>
		</c:forEach>
	</ul>
<br> <a href="<c:url value = 'adminWelcome.html'/>"> Back to Admin Menu</a>
</body>
</html>