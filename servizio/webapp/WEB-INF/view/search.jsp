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
	<link rel="stylesheet" type="text/css" href="/resources/css/loaderStyle.css" />
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
					<li><a href="#">Search</a></li>
				</ul>

					<!-- search box-->
                   <div id="wrapper">
				    <form id="searchForm" method="post" onkeypress="return event.keyCode != 13;">
						<fieldset>
							<label for="s"><b><i>Topic terms</i></b></label>
				           	<input id="s" name="query" type="search" list="querySuggestions" autocomplete="off" placeholder="Insert here most relevant topic terms" style="width:80%;"/><h3 class="login button"><input type="submit" value="Submit"/></h3>
				           	<datalist id="querySuggestions"></datalist>
				           	<label for="sc"><b><i>Context terms</i></b></label>
				           	<input id="sc" name="terms" type="search" autocomplete="off" list="contextTermsSuggestions" placeholder="Insert here additional terms to refine your search" style="width:80%;"/>
                                                <datalist id="contextTermsSuggestions"></datalist>
                                                <label for="lg"><b><i>Language of topic</i></b></label>
				           	<select id="lg" name="lang" style="width:200px;">
                                                    <option selected="selected" value="en">English</option>
                                                    <option value="it">Italiano</option>
                                                    <option value="de">Deutsch</option>
                                                    <option value="fr">Francais</option>
                                                    <option value="sv">Sveska</option>
                                                    <option value="es">Espanol</option>
                                                    <option value="ru">Russian</option>
                                                    <option value="pl">Polski</option>
                                                    <option value="vi">Tieng Viet</option>
                                                    <option value="war">Winaray</option>
                                                    <option value="ceb">Cebuano</option>
                                                    <option value="zh">Chinese</option>
                                                    <option value="ja">Japanese</option>
                                                </select>
                                                              
                                                
                                                <label for="np"><b><i>Depth level search</i></b></label>
                                                <select id="np" name="depth" style="width:75px;">
                                                    <option value="1">1</option>
                                                    <option selected="selected" value="2">2</option>
                                                    <option value="3">3</option>
                                                </select>    
				           	
								<label for="resStat"><input hidden id="resStat" name="check" type="radio" value="TF_IDF" /><!-- Order Results By Statistic --></label>
								<label for="resTS"><input hidden id="resTS" name="check" type="radio" value="TEACHING_STYLE" /><!-- Order Results By Teaching Style --></label>
								<label for="resAll"><input hidden id="resAll" name="check" type="radio" value="ALL" checked /><!-- All --></label>
								<label for="resNoOrd"><input hidden id="resNoOrd" name="check" type="radio" value="NO_ONE" /><!-- No Ordering --></label>
						</fieldset>
				    </form>
					</div>
					<div id="resultsDiv" style="top: -150px;">
				    	<p id="totalResults"></p><br />
				    	<div id="resultContainer" classname="pageContainer" style="">
					    	<table id="resultTable" class="tablesorter" border="1" cellpadding="4px">
					    		<thead>
					    			<tr><th width=22%>Article</th><th width=32%>Description</th><th width=10%>T.S. Distance</th><th width=10%>TF-IDF</th><th width=10%>LSI</th><th width=10%>I.G.</th><th width=6%></th></tr>
					    			<!-- <tr><th width=24%>Article</th><th width=43%>Description</th><th width=13%>T.S. Distance</th><th width=7%>Cos. Similarity</th><th width=13%></th></tr>
					    			<tr><th width=21%>Article</th><th width=37%>Description</th><th width=13%>Tf-Idf</th><th width=7%>T.S. Rank</th><th width=6%>Similarity</th><th width=6%>Search Engine</th><th width=10%></th></tr> -->
					    		</thead>
					    		<tbody id="resultTableBody">
					    		</tbody>
					    	</table>
					    	<div class="clear"></div>
				    	</div>
				    	
				    </div>
				<!--searchbox end--> 
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
                <script charset="UTF-8" src="/resources/js/searchscript.js"></script>
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
		<script src="/resources/js/jquery.tablesorter.js"></script>
		<script src="/resources/js/jquery-loader.js"></script>
		
		
		<script type="text/javascript">
		$(document).ready(function() {
			
			//if user hasn't a compiled teaching style... 
			$.get("<c:url value="/user/checkTeachingStyleExistence"/>", function(data){
			    //...redirect to survey compilation page
		        if("true" !== data.message){
		        	window.location.href = "<c:url value="/teachingStyle.html"></c:url>" + "?message=compilationRequired";
		        }
		    })
		    .fail(function(data) {
		    	if(data.responseJSON.error !== null && data.responseJSON.message !== null){
		    		alert(data.responseJSON.message);
		    	}
		    });
			
			
			$("#resultTable").tablesorter({ headers: { 6: { sorter: false} } });
			$("#resultContainer").hide();
			$('input[name=query]').on("input", function(e) {
				var val = $('input[name=query]').val();
				if(val === "") 
					return;
				//could be used this to limit results
				if(val.length < 3)
					return;
				
				$.getJSON("/search/getQueryAndTermsSuggestions", {queryString: val}, function(res){
					var qList = $("#querySuggestions");
					qList.empty();
					if(res.suggestedQueries.length) {
						for(var i=0, len=res.suggestedQueries.length; i<len; i++) {
							var opt = $("<option></option>").attr("value", res.suggestedQueries[i].queryString);
							qList.append(opt);
						}
					}
					
					var ctList = $("#contextTermsSuggestions");
					ctList.empty();
					if(res.contextTerms.length) {
						for(var j=0, len2=res.contextTerms.length; j<len2; j++) {
							var opt2 = $("<option></option>").attr("value", res.contextTerms[j].terms);
							ctList.append(opt2);
						}
					}
					
				},"json");
			});
		});
		
		</script>
		
	<!-- end: JavaScript-->
	
</body>
</html>
