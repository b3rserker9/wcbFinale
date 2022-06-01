<!DOCTYPE html>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
    uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page session="true"%>
<html lang="en">
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
		
</head>

<body>
	<sec:authentication var="principal" property="principal" />

		<!-- start: Header -->
	<div class="navbar">
		<div class="navbar-inner">
			<div class="container-fluid">
				<a class="btn btn-navbar" data-toggle="collapse" data-target=".top-nav.nav-collapse,.sidebar-nav.nav-collapse">
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</a>
				<a class="brand" href="homepage.html"><span><img src="/resources/img/logor3.png" alt="Roma Tre University" /></span></a>
				<a class="titolo" href="homepage.html"><span>
				<img src="/resources/img/title.png" alt="Wiki Course Builder" class="auto-style1"/></span></a>

								
				<!-- start: Header Menu -->
				<div class="nav-no-collapse header-nav">
					<ul class="nav pull-right">
						<!-- start: User Dropdown -->
						<li class="dropdown"><a class="btn dropdown-toggle" data-toggle="dropdown" href="#"> 
						<i	class="halflings-icon white user"></i> ${principal.firstName} ${principal.lastName} <span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li class="dropdown-menu-title"><span>Account Settings</span></li>
							<li><a href="/teachingStyle.html"><i class="halflings-icon user"></i> Teaching Style</a></li>
							<li><a href="/changePassword.html"><i class="halflings-icon wrench"></i> Change Password</a></li>
							<li><a href="<c:url value="/j_spring_security_logout" />"><spring:message code="label.pages.logout"></spring:message></a> </li>
						</ul></li>
						<!-- end: User Dropdown -->
					</ul>
				</div>
				<!-- end: Header Menu -->
				
			</div>
		</div>
	</div>
	<!-- start: Header -->
	
	<div class="container-fluid-full">
		<div class="row-fluid">
				
			<!-- start: Main Menu -->
			<div id="sidebar-left" class="span2">
				<div class="nav-collapse sidebar-nav">
					<ul class="nav nav-tabs nav-stacked main-menu">
						<li><a href="homepage.html"><i class="icon-home"></i><span class="hidden-tablet"> Welcome</span></a></li>	
						<li><a href="about.html"><i class="icon-eye-open"></i><span class="hidden-tablet"> About</span></a></li>
						<li><a href="community.html"><i class="icon-cogs"></i><span class="hidden-tablet"> Community</span></a></li>
						<li><a href="contacts.html"><i class="icon-envelope-alt"></i><span class="hidden-tablet"> Contacts</span></a></li>
						<li><a href="search.html"><i class="icon-edit"></i><span class="hidden-tablet"> Start Building</span></a></li>
						<li><a href="mycourses.html"><i class="icon-align-justify"></i><span class="hidden-tablet"> My Courses</span></a></li>
						<li><a href="<c:url value="/j_spring_security_logout" />"><i class="icon-lock"></i><span class="hidden-tablet"><spring:message code="label.pages.logout"></spring:message></span></a></li>
					</ul>
				</div>
			</div>
			<!-- end: Main Menu -->
			
			<noscript>
				<div class="alert alert-block span10">
					<h4 class="alert-heading">Warning!</h4>
					<p>You need to have <a href="http://en.wikipedia.org/wiki/JavaScript" target="_blank">JavaScript</a> enabled to use this site.</p>
				</div>
			</noscript>
			
			<!-- start: Content -->
			<div id="content" class="span10">
				<ul class="breadcrumb">
					<li>
						<i class="icon-home"></i>
						<a href="homepage.html">Home</a> 
						<i class="icon-angle-right"></i>
					</li>
					<li><a href="#">Password change</a></li>
				</ul>
				
				<div id="errormsg" class="alert alert-error" style="display:none"></div>
				<div id="successmsg" class="alert alert-info" style="display:none"></div>
				<div id="changePwdContainer">
					<div class="box span12">
						<div class="box-header" data-original-title>
							<h2><i class="halflings-icon edit"></i><span class="break"></span><spring:message code="message.changePassword"></spring:message></h2>
							<div class="box-icon">
								<!-- no icons -->
							</div>
						</div>
						<div class="box-content">
							<form class="form-horizontal" id="changePwdForm" method="POST">
							  <fieldset>
							 	 <div class="control-group">
									<label class="control-label" for="oldpass"><spring:message code="label.user.oldPassword"></spring:message></label>
									<div class="controls" style="width:50%;">
									  <input class="span6 typeahead" style="width:100%;" required="required" id="oldpass" name="oldpassword" type="password" value=""/>
									  <label id="errorOldPwd" class="alert alert-error" style="display:none">Please, insert a valid password</label>
									</div>
								  </div>
								  <br /><br />
								  <div class="control-group">
									<label class="control-label" for="pass"><spring:message code="label.user.newPassword"></spring:message></label>
									<div class="controls" style="width:50%;">
									  <input class="span6 typeahead form-control" style="width:100%;" required="required" id="pass" name="password" type="password" value="" />
									  <label id="errorPwd" class="alert alert-error" style="display:none">Please, insert a valid password</label>
									</div>
								  </div>
								  <div class="control-group">
									<label class="control-label" for="passConfirm"><spring:message code="label.user.confirmPass"></spring:message></label>
									<div class="controls" style="width:50%;">
									  <input class="span6 typeahead" style="width:100%;" required="required" id="passConfirm" name="passwordconfirm" type="password" value="" />
									  <label id="error" class="alert alert-error" style="display:none"><spring:message code="PasswordMatches.user"></spring:message></label>
									</div>
								  </div>
							  </fieldset>
							</form>
							<div class="form-actions">
							  <button class="btn btn-primary" id="submit-btn" onClick="savePass()">Apply</button>
							  <button id="cancelAddCourseButton" class="btn" onClick="exit()">Cancel</button>
							</div>   
						</div>
					</div><!--/span-->
				</div><!--/row-->
			
			</div>
		</div>
	</div>
			
	<div class="clearfix"></div>
	<footer>
		<p>
			<span style="text-align:left;float:left">&copy; 2015 <a href="http://didattica.dia.uniroma3.it/" alt="Roma Tre University">Roma Tre University</a></span>
		</p>
	</footer>
	
	<!-- start: JavaScript-->
		<script src="/resources/js/jquery-1.9.1.min.js"></script>
		<script src="/resources/js/jquery-migrate-1.0.0.min.js"></script>
		<script src="/resources/js/jquery-ui-1.10.0.custom.min.js"></script>
		<script src="/resources/js/jquery.ui.touch-punch.js"></script>
		<script src="/resources/js/modernizr.js"></script>
		<script src="/resources/js/bootstrap.min.js"></script>
		
		<script src="/resources/js/jquery.cookie.js"></script>
		<script src='/resources/js/fullcalendar.min.js'></script>
		<script src='/resources/js/jquery.dataTables.min.js'></script>
		<script src="/resources/js/excanvas.js"></script>
		<script src="/resources/js/jquery.flot.js"></script>
		<script src="/resources/js/jquery.flot.pie.js"></script>
		<script src="/resources/js/jquery.flot.stack.js"></script>
		<script src="/resources/js/jquery.flot.resize.min.js"></script>
		<script src="/resources/js/jquery.chosen.min.js"></script>
		<script src="/resources/js/jquery.uniform.min.js"></script>
		<script src="/resources/js/jquery.cleditor.min.js"></script>
		<script src="/resources/js/jquery.noty.js"></script>
		<script src="/resources/js/jquery.elfinder.min.js"></script>
		<script src="/resources/js/jquery.raty.min.js"></script>
		<script src="/resources/js/jquery.iphone.toggle.js"></script>
		<script src="/resources/js/jquery.uploadify-3.1.min.js"></script>
		<script src="/resources/js/jquery.gritter.min.js"></script>
		<script src="/resources/js/jquery.imagesloaded.js"></script>
		<script src="/resources/js/jquery.masonry.min.js"></script>
		<script src="/resources/js/jquery.knob.modified.js"></script>
		<script src="/resources/js/jquery.sparkline.min.js"></script>
		<script src="/resources/js/counter.js"></script>
		<script src="/resources/js/custom.js"></script>
		<script src="/resources/js/pwstrength.js"></script>
		
		
		<script type="text/javascript">
		
			$(document).ready(function() {
				"use strict";
				var options = {
					    common: {
					    	minChar:8,
					    	onKeyUp: checkPass,
					    },
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
				 $('#pass').pwstrength(options);
			});
			
			function checkPass(event, result){
			    if ( result && result.score < 40 ) {
			      $('#submit-btn').prop('disabled', true);
			    } else {
			      $('#submit-btn').prop('disabled', false);
			    }
			  }
			
			function savePass(){
				$("#errorOldPwd").hide();
				$("#errorPwd").hide();
				$("#error").hide();
				
				var oldPass = $("#oldpass").val();
			    var pass = $("#pass").val();
			    var passConfirm = $("#passConfirm").val();
			    
			    if(oldPass==""){
			    	 $("#errorOldPwd").show();
			    	 return;
			    }
			    if(pass==""){
			    	$("#errorPwd").show();
			    	 return;
			    }
			    var valid = pass == passConfirm;
			    if(!valid) {
			      $("#error").show();
			      return;
			    }

			    $.post("<c:url value="/user/updatePassword"></c:url>",{password: pass, oldpassword: $("#oldpass").val()} ,function(data){
			    	$("#successmsg").show().html("<b><h2>Password was successfully changed.</h2></b>");
			    })
			    .fail(function(data) {
			    	if(jQuery.parseJSON(data.responseText).message=="message.invalidOldPassword"){
			    		$("#errormsg").show().html("<b><h2>Old password is not valid.</h2></b>");
			    	}
			    });
			}
			
			function exit(){
				window.location.href = "<c:url value="/homepage.html"></c:url>"
			}
		
		</script>
		
	<!-- end: JavaScript-->
	
</body>
</html>
