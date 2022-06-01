<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
    uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>

<head>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
	<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
	    <h1 id="error" class="alert alert-danger">
	        <spring:message code="message.logoutError"></spring:message>
	    </h1>
	</c:if>
	<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
	<meta http-equiv="Refresh" content="3;url=login.html">
	<meta name="description" content="Wiki Course Builder">
	<meta name="author" content="Andrea Tarantini, Alessandra Milita">
	<meta name="keyword" content="Wikipedia, Roma Tre">
	<title><spring:message code="label.pages.home.title"></spring:message></title>
</head>

<body>
    <div class="container">
           
            <c:if test="${param.logSucc == true}">
                <h1 id="success" class="alert alert-info">
                    <spring:message code="message.logoutSucc"></spring:message>
                </h1>
            </c:if>
            <a class="btn btn-primary" href="<c:url value="login.html" />"><spring:message
                    code="label.form.loginLink"></spring:message></a>
    </div>
</body>

</html>