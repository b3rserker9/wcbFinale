<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setBundle basename="messages" />
<%@ page session="true"%>

<html>
	<head>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="description" content="Wiki Course Builder">
		<meta name="author" content="Andrea Tarantini, Alessandra Milita">
		<meta name="keyword" content="Wikipedia, Roma Tre">
		<title><spring:message code="label.pages.home.title"></spring:message></title>
	</head>
	<body>
		<div class="container">
	            <h1 class="alert alert-success">
					<spring:message code="message.regSucc"></spring:message>
				</h1>
				<a href="<c:url value="/login.html" />"><spring:message
						code="label.login"></spring:message></a>
		</div>
	</body>
</html>