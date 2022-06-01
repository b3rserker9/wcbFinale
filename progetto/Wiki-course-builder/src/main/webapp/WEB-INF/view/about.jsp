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
				<!-- end: Main Menu -->
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
						<li><a href="#">About</a></li>
					</ul>
					<div class="container-fluid-full">
						<div class="row-fluid" style="text-align:justify">
							<h1 style="text-align:center">Wiki Course Builder: a System for Retrieving and Sequencing Didactic Materials from Wikipedia</h1>
							<br /><br />
							<p>The designing and delivering of a new online course 
							is a crucial task for teachers that have to face two main problems: 
							building, or retrieving, and sequencing learning materials. 
							Retrieving learning materials requires a great effort and waste 
							of time, while sequencing them requires an accurate didactic 
							project. On the other hand, thanks to the Internet, teachers and 
							instructional designers today can search and retrieve learning 
							materials from Learning Objects Repositories freely available on 
							the Web, such as Mertlot or Ariadne. With this project we investigate 
							the possibility of using the Wikipedia free encyclopedia, which is the 
							biggest repository of educational material, visited daily 
							by about sixty million people.
							</p> 
							<br />
							<p>So here we propose a system, called <b>Wiki Course Builder</b>, 
							which helps teachers to retrieve Wikipedia HTML pages and 
							sequence them on the basis of their out-coming links. We address 
							the above-mentioned two critical issues for supporting teachers 
							to retrieve and sequence new learning materials with a little effort. 
							In our system, through a suitable GUI, first the user can input one or 
							more keywords concerning the topic she is working on. Subsequently, 
							through the Wikipedia APIs, the system analyzes the most relevant documents, 
							returned by the embedded search engine, together with a proposal of a first 
							sequencing of them. The concept of relevance of a document is related to 
							the set of teaching styles tags, associated to it, following the Grasha model. 
							The sequencing is performed by the system basing on the links among 
							the retrieved Wikipedia pages as stored in the Wikipedia repository 
							and on the Grasha teaching styles. Each time a teacher selects a document, 
							it is tagged with the teachers teaching styles, represented by a weighted vector. 
							As time goes by, this form of knowledge is acquired and exploited so that 
							the repository's content is automatically filtered. 
							In other words, a social filtering on Wikipedia is put in practice 
							by clustering the documents depending on the teachers preferences. 
							</p> 
							<br />
							<p> When the user registers to the system, she is required to fill
								in a form with some personal data. It is in this step that the
								user is required to take the Grasha-Riechmann Teaching Style
								Survey. It consists of 40 5-points Likert-scale questions, that aim at modeling
								the teacher by means of the following five dimensions:
							</p>
							<div>
								<ul>
									<li><b>Expert:</b> the teacher has the knowledge and the experience
										that students need;
									</li>
									<li><b>Formal authority:</b> the teacher maintains her institutional
										role;
									</li>
									<li><b>Personal model:</b> the teacher bases her teaching by
										personal example and establishes a model for thinking
										and acting;	
									</li>
									<li><b>Facilitator:</b> the teacher emphasizes personal interactions
										between students and teacher;
									</li>
									<li><b>Delegator:</b> the teacher develops student's ability so 
										that they can act autonomously.
									</li>
								</ul>
							</div>
							<br />
							<p> In this way, when the user registers to the system,
								it is modelled by the set of the aforesaid five dimensions.
							</p>
							<p> The teacher can insert her concept-term(s), i.e.,
								the term(s) representing the concepts that she wants to
								teach and launch the search engine on the Wikipedia
								database that will return a sequence of Wikipedia
								pages. Each Wikipedia HTML page returned by Wikipedia is 
								filtered both according to various information retrieval 
								mechanisms and using a social-based filtering process.
							</p>
							<div>
								<ul>
									<li><b>TF-IDF: </b> the retrieved pages are filtered by means 
										of the cosine similarity between the query formed by the Terms Manager 
										module and the HTML pages, with a TFxIDF terms-weighting technique, 
										together with the use of the vector model, a classic technique used in the 
										information retrieval area to rank documents.
									</li>
									<li><b>Latent Semantic Index: </b> the vector model is optimized using the 
										Singular Value Decomposition technique. The goal is to project the representation 
										from a term perspective to a semantic one, based on the definition of concepts. 
										Both documents and queries are represented by a vector and the pages are filtered 
										using the cosine similarity method, as seen in TF-IDF.
									</li>
								
									<li><b>Information Gain: </b> the term-document matrix, that represent the weight 
										of each term in each document of the corpus, is computed taking into account 
										the relevance of terms in the whole corpus. The more a term is rare (i.e. it occurs 
										few times), the higher is its score. The matrix is than represented in a vector model 
										and a cosine similarity is performed, as shown in the other ranking algorithms.
									</li>
									<li><b>Social-based filtering process: </b> the retrieved pages are
										filtered using the teaching styles of the teacher who
										launched the query, that is a teacher-style based metric
										used to rank the retrieved pages. This mechanism
										is based on the fact that every time the teacher uses a
										retrieved page in her course, that page is tagged with
										the teaching style of the teacher that selected it. In this
										way, by the use of the system, each selected Wikipedia
										page will be stored in a local database with a set of 5-ples. 
										The metric used to perform the distance between the user teaching
										styles and a generic document is based on the euclidian distance.
									</li>
								</ul>
							</div>
							<p> The user has at her disposition an interactive Google-like environment
								where, for each retrieved page, she can see the complete page
								(<b>View Details function</b>) or launch the course building process
								(<b>Start Building function</b>). The second function starts the process of course 
								building from the root page that is performed through the following steps:
							</p>
							<div>
								<ul>
									<li>The Wikipedia root page is parsed with the use of
										suitable Wikipedia API. All the linked pages are
										evaluated and the most promising linked page is
										selected on the basis of its reputation in the community. 
										This process starts again with a predefined tree depth;
									</li>
									<li>All the sequenced retrieved pages are displayed according 
										to the methods listed above. Each method generates a sequenced 
										topic that is presented to the user in two ways: in an ordered 
										table and in a graphic visualization. The user can interact with 
										both representations, selecting articles to add to his topic, 
										displaying articles in a new browser window and changing the proposed 
										sequencing order. The user can mix and match articles retrieved with 
										various sequencing algorithms, adding them to her topic. He can also 
										add articles from the graph representation.
									</li>
									<li>Finally the user can save her course.	
									</li>
								</ul>
							</div>
						</div>
					</div>
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
	<!-- <script src="/resources/js/retina.js"></script>-->
	<script src="/resources/js/custom.js"></script>
	<!-- end: JavaScript-->
</body>
</html>
