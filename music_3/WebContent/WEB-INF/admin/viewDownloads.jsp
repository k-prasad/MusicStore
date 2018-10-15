<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Download Report</title>
</head>
<body>

	<h2>
		<i>Downloads Report</i>
	</h2>
	<table>
		<tr>
			<td><b>Site User Name</b></td>
			<td><b>Download Date</b></td>
			<td><b>Product Code</b></td>
			<td><b>Track Number</b></td>
			<td><b>Track Title</b></td>
			<td><b>Sample Filename</b></td>
		</tr>

		<c:forEach var="download" items="${downloadreports}">
			<tr>
				<td>${download.getUser().getFirstname()}</td>
				<td>${download.downloadDate}</td>
				<td>
					${download.getTrack().getProduct().getCode()}</td>
				<td>
					${download.getTrack().getTrackNumber()}</td>
				<td>
					${download.getTrack().getTitle()}</td>
				<td>
					${download.getTrack().getSampleFilename()}</td>
			</tr>
		</c:forEach>
	</table>
	<br>

</body>
</html>