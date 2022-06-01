<!DOCTYPE html>
<html lang="en">
    <%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec"
    uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setBundle basename="messages" />
<fmt:setLocale value="en_US" scope="session"/>
<%@ page session="true"%>
<c:if test="${param.error != null}">
    <c:choose>
    	<c:when
            test="${param.error == 'UserNotFound'}">
            <div class="alert alert-error">
               <b></b><spring:message code="message.userNotFound"></spring:message></b>
            </div>
        </c:when>
        <c:when
            test="${SPRING_SECURITY_LAST_EXCEPTION.message == 'User is disabled'}">
            <div class="alert alert-error">
                <b><spring:message code="auth.message.disabled"></spring:message></b>
            </div>
        </c:when>
        <c:when
            test="${SPRING_SECURITY_LAST_EXCEPTION.message == 'User account has expired'}">
            <div class="alert alert-error">
                <b><spring:message code="auth.message.expired"></spring:message></b>
            </div>
        </c:when>
        <c:when
            test="${SPRING_SECURITY_LAST_EXCEPTION.message == 'blocked'}">
            <div class="alert alert-error">
               <b><spring:message code="auth.message.blocked"></spring:message></b>
            </div>
        </c:when>
        <c:otherwise>
            <div class="alert alert-error">
            <!-- <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/> -->
                <b><spring:message code="message.badCredentials"></spring:message></b>
            </div>
        </c:otherwise>
    </c:choose>
</c:if>

<head>
	
	<!-- start: Meta -->
	<meta charset="utf-8">
	<title>WikiCourseBuilder</title>
	<meta name="description" content="Wiki Course Builder">
	<meta name="author" content="Andrea Tarantini, Alessandra Milita">
	<meta name="keyword" content="Wikipedia, Roma Tre">
	<!-- end: Meta -->
	
	<!-- start: Mobile Specific -->
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- end: Mobile Specific -->
	
	<!-- start: CSS -->
	<link id="bootstrap-style" href="/resources/css/bootstrap.min.css" rel="stylesheet">
	<link href="/resources/css/bootstrap-responsive.min.css" rel="stylesheet">
	<link id="base-style" href="/resources/css/style.css" rel="stylesheet">
	<link id="base-style-responsive" href="/resources/css/style-responsive.css" rel="stylesheet">
	<link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800&subset=latin,cyrillic-ext,latin-ext' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" type="text/css" href="/resources/css/demo.css" />
    <link rel="stylesheet" type="text/css" href="/resources/css/style2.css" />
	<link rel="stylesheet" type="text/css" href="/resources/css/animate-custom.css" />
	<link rel="stylesheet" type="text/css" href="/resources/css/custom2.css" />
	<link rel="stylesheet" type="text/css" href="/resources/css/progressbar.css" />
	
	<!-- end: CSS -->
	

	<!-- The HTML5 shim, for IE6-8 support of HTML5 elements -->
	<!--[if lt IE 9]>
	  	<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
		<link id="ie-style" href="/resources/css/ie.css" rel="stylesheet">
	<![endif]-->
	
	<!--[if IE 9]>
		<link id="ie9style" href="/resources/css/ie9.css" rel="stylesheet">
	<![endif]-->
		
	<!-- start: Favicon -->
	<link rel="shortcut icon" href="/resources/img/favicon.ico">
	<!-- end: Favicon -->
	
    <c:if test="${param.message != null}">
		<div class="alert alert-info">
			<c:set var="aux" value="${param.message}" />
			<c:choose>
		    	<c:when test="${not fn:containsIgnoreCase(aux, '.message')}">
		    		<b><spring:message code="${param.message}"></spring:message></b>
		    	</c:when>
 				<c:otherwise>
 					<b>${param.message}</b>
		    	</c:otherwise>
		    </c:choose>
		</div>
	</c:if>
		    
</head>

<body id="loginBody">

<!-- start: Header -->
	<div class="navbar">
		<div class="navbar-inner">
			<div class="container-fluid">
				<a class="btn btn-navbar" data-toggle="collapse" data-target=".top-nav.nav-collapse,.sidebar-nav.nav-collapse"> </a>
				<a class="brand" href="homepage.html"><span><img src="/resources/img/logor3.png" alt="Roma Tre University" /></span></a>
				<a class="titolo" href="homepage.html"><span style="top:50px;">
				<img src="/resources/img/title.png" alt="Wiki Course Builder" class="auto-style1"/></span></a>

			</div>
		</div>
	</div>
	<!-- start: Header -->
	
	<!-- <div class="container-fluid-full">
		<div class="row-fluid"> -->
				
			<!-- start: Main Menu -->
			<div id="sidebar-left" class="span2">
				<div class="nav-collapse sidebar-nav">
					<ul class="nav nav-tabs nav-stacked main-menu">
						<li><a href="login.html"><i class="icon-lock"></i><span class="hidden-tablet"> Login Page</span></a></li>
						<li><a href="about.html"><i class="icon-eye-open"></i><span class="hidden-tablet"> About</span></a></li>
						<li><a href="contacts.html"><i class="icon-envelope-alt"></i><span class="hidden-tablet"> Contacts</span></a></li>
					</ul>
				</div>
			</div>
			<!-- end: Main Menu -->
			
			<br />
                    <a class="hiddenanchor" id="tologin"></a>
                    <a class="hiddenanchor" id="toresetpassword"></a>
                    <a class="hiddenanchor" id="toregister"></a>
                   
                    
                    <div id="wrapper">
                        <div id="login" class="animate form defaultform">
                            <form name="loginForm" action="j_spring_security_check" method='POST' > 
                                <h1>Log in</h1>
                                <p>
                                    <label for="username" class="uname" data-icon="" ><spring:message code="label.user.email"></spring:message></label>
                                    <input type='text' name='j_username' required="required" oninvalid="setCustomValidity('<spring:message code="message.username"></spring:message>')" onchange="try{setCustomValidity('')}catch(e){}" placeholder="" value='' />
                                </p>
                                <p> 
                                    <label for="loginPassword" class="youpasswd" data-icon="p"><spring:message code="label.form.loginPass"></spring:message></label>
                                    <input id="loginPassword" class="form-control" name="j_password" required="required" oninvalid="setCustomValidity('<spring:message code="message.password"></spring:message>')" onchange="try{setCustomValidity('')}catch(e){}" type="password" placeholder="" /> 
                                </p>
                                <p class="login button"> 
                                    <input type="submit" value=<spring:message code="label.form.submit"></spring:message> /> 
								</p>
								<p class="keeplogin" style="float:right;">
									<a href="#toresetpassword" class="to_register"><i>Forgot password</i></a>
								</p>
                                <p class="change_link">
									<spring:message code="label.notamember"></spring:message>
									<a href="#toregister" class="to_register"><spring:message code="label.form.loginSignUp"></spring:message></a>
								</p>
                            </form>
                        </div>
						
                        <div id="register" class="animate form">
                            <!-- <form  action="mysuperscript.php" autocomplete="on">  -->
                            <form name="registerForm" action="/" method="POST" enctype="utf8">
                                <h1> Sign up </h1>
                                <p> 
                                    <label for="firstName" class="uname" data-icon=""><spring:message code="label.user.firstName"></spring:message></label>
                                    <input id="firstName" name="firstName" required="required" type="text" placeholder="insert first name" oninvalid="setCustomValidity('<spring:message code="message.generic.field"></spring:message>')" onchange="try{setCustomValidity('')}catch(e){}" />
                                    <span id="firstNameError" class="alert alert-danger col-sm-4" style="display:none"></span>
                                </p>
                                <p> 
                                    <label for="lastName" class="uname" data-icon=""><spring:message code="label.user.lastName"></spring:message></label>
                                    <input id="lastName" name="lastName" required="required" type="text" placeholder="insert last name" oninvalid="setCustomValidity('<spring:message code="message.generic.field"></spring:message>')" onchange="try{setCustomValidity('')}catch(e){}" />
                                    <span id="lastNameError" class="alert alert-danger col-sm-4" style="display:none"></span>
                                </p>
                                <p> 
                                    <label for="email" class="youmail" data-icon=""><spring:message code="label.user.email"></spring:message></label>
                                    <input id="email" class="form-control" name="email" required="required" type="email" placeholder="insert e-mail" oninvalid="setCustomValidity('<spring:message code="message.badEmail"></spring:message>')" onchange="try{setCustomValidity('')}catch(e){}" />
                                    <span id="emailError" class="alert alert-danger col-sm-4" style="display:none"></span>
                                </p>
                                <p> 
                                    <label for="password" class="youpasswd" data-icon="p"><spring:message code="label.user.password"></spring:message></label>
                                    <input id="password" class="form-control" name="password" required="required" type="password" placeholder="insert password" oninvalid="setCustomValidity('<spring:message code="message.generic.field"></spring:message>')" onchange="try{setCustomValidity('')}catch(e){}"/>
                                    <span id="passwordError" class="alert alert-danger col-sm-4" style="display:none"></span>
                                </p>
                                <p> 
                                    <label for="matchPassword" class="youpasswd" data-icon="p"><spring:message code="label.user.confirmPass"></spring:message></label>
                                    <input id="matchPassword" class="form-control" name="matchingPassword" required="required" type="password" placeholder="Please confirm your password" oninvalid="setCustomValidity('<spring:message code="message.generic.field"></spring:message>')" onchange="try{setCustomValidity('')}catch(e){}"/>
                                    <span id="globalError" class="alert alert-danger col-sm-4" style="display:none"></span>
                                </p>
                                <p class="signin button"> 
									<input type="submit" value="<spring:message code="label.form.register"></spring:message>"/> 
								</p>
                                <p class="change_link">  
									<spring:message code="label.alreadyamember"></spring:message>
									<a href="#tologin" class="to_register"><spring:message code="label.form.loginLink"></spring:message></a>
								</p>
                            </form>
                        </div>
						
						<div id="resetpassword" class="animate form">
                            <form name="resetPasswordForm" action="j_spring_security_check" method='POST' > 
                                <h1>Reset Password</h1> 
                                <p>
                                    <label for="emailForReset" class="uname" data-icon="" ><spring:message code="label.user.email"></spring:message></label>
                                    <input type='text' id="emailForReset" name='jres_username' required="required" oninvalid="setCustomValidity('<spring:message code="message.username"></spring:message>')" onchange="try{setCustomValidity('')}catch(e){}" placeholder="" value='' />
                                </p>
                                <p class="reset button"> 
                                    <input type="submit" value="Send" /> 
								</p>
								<p class="change_link">  
									<a href="#tologin" class="to_register"><spring:message code="label.form.loginLink"></spring:message></a>
								</p>
                            </form>
                        </div>
                        
                    </div>

<script src="<c:url value="/resources/js/jquery-1.9.1.min.js" />"></script> 
<script src="<c:url value="/resources/js/pwstrength.js" />"></script>
<script type="text/javascript">
$(document).ready(function () {
	if (document.location.hash == "" || document.location.hash == "#")
	    document.location.hash = "#tologin";
	
	$('form[name="registerForm"]').submit(function(event) {
		register(event);
	});
	
	$('form[name="resetPasswordForm"]').submit(function(event) {
		resetPass(event);
	});
	
	$(":password").keyup(function(){
		if($("#password").val() != $("#matchPassword").val()){
	        $("#globalError").show().html('<spring:message code="PasswordMatches.user"></spring:message>');
	    }else{
	    	$("#globalError").html("").hide();
	    }
	});
	
	options = {
		    common: {minChar:8},
		    ui: {
		    	showVerdictsInsideProgressBar:true,
		    	showErrors:true,
		    	errorMessages:{
		    		  wordLength: '<spring:message code="error.wordLength"/>',
		    		  wordNotEmail: '<spring:message code="error.wordNotEmail"/>',
		    		  wordSequences: '<spring:message code="error.wordSequences"/>',
		    		  wordLowercase: '<spring:message code="error.wordLowercase"/>',
		    		  wordUppercase: '<spring:message code="error.wordUppercase"/>',
		    	      wordOneNumber: '<spring:message code="error.wordOneNumber"/>',
		    		  wordOneSpecialChar: '<spring:message code="error.wordOneSpecialChar"/>'
		    		}
		    	}
		};
	 $('#password').pwstrength(options);
});

function register(event){
	event.preventDefault();
    $(".alert").html("").hide();
    $(".error-list").html("");
    if($("#password").val() != $("#matchPassword").val()){
    	$("#globalError").show().html('<spring:message code="PasswordMatches.user"></spring:message>');
    	return;
    }
    var formData= $('form[name="registerForm"]').serialize();
    $.post("<c:url value="/user/registration"/>",formData ,function(data){
        if(data.message == "success"){
            window.location.href = "<c:url value="/successRegister.html"></c:url>";
        }
        
    })
    .fail(function(data) {
        if(jQuery.parseJSON(data.responseText).error.indexOf("MailError") > -1)
        {
            window.location.href = "<c:url value="/emailError.html"></c:url>";
        }
        else if(jQuery.parseJSON(data.responseText).error == "UserAlreadyExist"){
            $("#emailError").show().html("User already exists.");
        }
        else if(jQuery.parseJSON(data.responseText).error.indexOf("InternalError") > -1){
            window.location.href = "<c:url value="/login.html"></c:url>" + "?message=" + jQuery.parseJSON(data.responseText).message;
        }
        else{
        	var errors = $.parseJSON(jQuery.parseJSON(data.responseText).message);
            $.each( errors, function( index,item ){
                $("#"+item.field+"Error").show().html(item.defaultMessage);
            });
            errors = $.parseJSON(jQuery.parseJSON(data.responseText).error);
            $.each( errors, function( index,item ){
                $("#globalError").show().append(item.defaultMessage+"<br>");
            });
        }
    });
}

function resetPass(event){
	event.preventDefault();
	
    var email = $("#emailForReset").val();
    $.post("<c:url value="/user/resetPassword"></c:url>",{email: email} ,function(data){
            window.location.href = "<c:url value="/login.html"></c:url>" + "?message=" + data.message;
    })
    .fail(function(data) {
    	if(jQuery.parseJSON(data.responseText).error.indexOf("MailError") > -1)
        {
            window.location.href = "<c:url value="/emailError.html"></c:url>";
        }
        else{
            window.location.href = "<c:url value="/login.html"></c:url>" + "?error=" + jQuery.parseJSON(data.responseText).error+"#toregister";
        }
    });
}


</script>
	
</body>
</html>
