<!DOCTYPE html>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
    uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setBundle basename="messages" />
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
			${param.message}
		</div>
	</c:if>
</head>

<body>
<sec:authentication var="principal" property="principal" />
<!-- start: Header -->
	<div class="navbar">
		<div class="navbar-inner">
			<div class="container-fluid">
				<a class="btn btn-navbar" data-toggle="collapse" data-target=".top-nav.nav-collapse,.sidebar-nav.nav-collapse">
				</a>
				<a class="brand" href="homepage.html"><span><img src="/resources/img/logor3.png" alt="Roma Tre University" /></span></a>
				<a class="titolo" href="homepage.html"><span>
				<img src="/resources/img/title.png" alt="Wiki Course Builder" class="auto-style1"/></span></a>

				<sec:authorize access="isAuthenticated()">
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
			    </sec:authorize>				
			</div>
		</div>
	</div>
	
	<sec:authorize access="isAuthenticated()">
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
	</sec:authorize>
    
	<sec:authorize access="!isAuthenticated()">
        <div class="container-fluid-full">
			<div class="row-fluid">
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
    </sec:authorize>
    
				<div id="content" class="span10">
					<ul class="breadcrumb">
						<li>
							<i class="icon-home"></i>
							<a href="homepage.html">Home</a> 
							<i class="icon-angle-right"></i>
						</li>
						<li><a href="#">Contacts</a></li>
					</ul>
					<div class="container-fluid-full">
						<div class="row-fluid" style="text-align:justify">
							<h1 style="text-align:center">Artificial Intelligence Lab</h1>
							<br /><br />
							<p>	The Artificial Intelligence (AI) program at Roma Tre University comprises a multidisciplinary group of researchers 
							conducting investigations on methods and tools for intelligent system development. The research activity comprises of 
							both formal approaches and theoretically grounded investigations, exploration and empirical-experimental techniques to 
							create, analyze and assess the conceived systems.
							</p>	 
							<br />	 
							<h2>People involved in this Project:</h2>	
							<div>
								<ul>
									<li><b>Carla Limongelli</b> - PhD, Associate Professor
									</li>
									<li><b>Fabio Gasparetti</b> - PhD, Assistant Professor
									</li>
									<li><b>Filippo Sciarrone</b> - PhD, Collaborator
									</li>
									<li><b>Andrea Tarantini </b> - Master's Degree Student
									</li>
									<li><b>Alessandra Milita </b> - Master's Degree Student
									</li>
                                                                        <li><b>Andrea Giardi </b> - Master's Degree Student
									</li>
									
								</ul>
							</div>
							<br /><br />
							<h2>Contact Us</h2>
							
							<p>Roma Tre University - Department of Department of Engineering <br />
							Artificial Intelligence Laboratory<br />
							Via della Vasca Navale 79, I - 00146 Rome, Italy<br />
							</p>
							<br />
							<p>
							Fax: +39 06 5733.3612<br />
							e-Mail: <a href="mailto:ai@dia.uniroma3.it" ><u>ai@dia.uniroma3.it</u> </a><br />
							Web Site: <a href="http://ai-lab-03.dia.uniroma3.it/"> <u>AI @ RomaTre</u> </a><br />
							</p>
							<br />
						</div>
					</div>
				</div>
			</div>
		</div>
	
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
	<!-- <script src="/resources/js/retina.js"></script>-->
	<script src="/resources/js/custom.js"></script>
	<!-- end: JavaScript-->
</body>
</html>
