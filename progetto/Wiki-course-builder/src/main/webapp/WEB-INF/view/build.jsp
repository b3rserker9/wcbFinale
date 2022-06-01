<!DOCTYPE html>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
    uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page session="true"%>
<html lang="en">
<head>
	
	<!-- start: Meta -->
	<meta charset="utf-8">
	<title>WikiCourseBuilder</title>
	<meta name="description" content="Wiki Course Builder">
	<meta name="author" content="Andrea Tarantini, Alessandra Milita, Andrea Giardi">
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
	<link rel="stylesheet" type="text/css" href="/resources/css/loaderStyle.css" />
	<link rel="stylesheet" type="text/css" href="/resources/css/select2.min.css" />
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
	<style type="text/css">
			.auto-style1 {
				margin-top: 12px;
				margin-left: 50px;
			}
                        .selects_columns {
                            background-color: #F0FFFF;
                            }
	</style>
        
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css">
        <link rel="stylesheet" href="/resources/css/fontawesome-stars.css">
        
        
		
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
				<a class="brand" href="homepage.html"><span><img src="/resources/img/logor3.png" alt="testo" /></span></a>
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
					<li><a href="#">Build results</a></li>
				</ul>
				
				
				<!-- Div dedicato al pannello di selezione del corso e del topic -->
				<!-- <div class="row-fluid sortable" id="chooseCourseContainer"> -->
				<div id="chooseCourseContainer">
					<div class="box span12">
						<div class="box-header" data-original-title>
							<h2><i class="halflings-icon edit"></i><span class="break"></span>Select course topic</h2>
							<div class="box-icon">
								<!-- no icons -->
							</div>
						</div>
						<div class="box-content">
							<form class="form-horizontal" id="addCourseForm">
							  <fieldset>
								  <div class="control-group">
									<label class="control-label" for="courseTitle">Choose a course</label>
									<div class="controls">
									  <select class="span6 typeahead" id="courseTitleSelect" class="js-example-placeholder-single"></select>
									  <!-- &nbsp;&nbsp;<a class="btn btn-success" id="buttonCreateNewCourse" href="#"><i>Create</i></a> -->
									</div>
								  </div>
								  <div class="control-group" id="newCourseButtonContainer">
								  	<a href="#" id="buttonCreateNewCourse"><i title="Create a new course" style="font-size: 16px; color:blue; margin-left:120px;"><u>... or click here to define a new course if it doesn't exist yet.</u></i></a>
								  </div>
								  <div class="control-group" id="newCourseTitleContainer" hidden="true">
									<label class="control-label" for="newCourseTitle">Create new course</label>
									<div class="controls">
									  <input class="span6 typeahead" required="required" id="newCourseTitle" type="text" value="" oninvalid="setCustomValidity('Please, insert a valid course name')" onchange="try{setCustomValidity('');}catch(e){}">
									   &nbsp;&nbsp;<a class="btn btn-danger" id="cancelCreateNewCourse" href="#"><i>Cancel</i></a>
									</div>
								  </div>
								  <div class="control-group"></div>
								  <div class="control-group">
									<label class="control-label" for="topicTitle">Define new topic</label>
									<div class="controls">
									  <input class="span6 typeahead" required="required" id="topicTitle" type="text" value="" oninvalid="setCustomValidity('Please, insert a valid topic')" onchange="try{setCustomValidity('');}catch(e){}">
									  &nbsp;&nbsp;<a class="btn btn-success" id="globalButtonTopicSave" href="#"><i>Save Topic</i></a>
									</div>
								  </div>
							  </fieldset>
							</form>   
						</div>
					</div><!--/span-->
				</div><!--/row-->
				
				<!-- Div dedicato al rendering del grafo -->
				<div id="showGraphContainer" hidden="true">
					<div class="box span12">
						<div class="box-header" data-original-title>
							<h2><i class="halflings-icon info-sign"></i><span class="break"></span>Graph</h2>
							<div class="box-icon">
								<a href="#" id="buttonHideGraph" class="btn-save"><i title="Hide Graph" class="halflings-icon remove"></i></a>
							</div>
							<input type="hidden" id="graphVal"/>
						</div>
						<div id="leftGraphInfoContainer" style="float: left; width:18%; height: 700px; display: inline-block; overflow: auto; background:rgb(250,250,250);">
						</div>
						<div id="upperGraphContainer" style="float: left; width:82%; height: 78px; display: inline-block; background:rgb(250,250,250);">
							<div id="graphLegend" style="float: right; width:45%; display: inline-block; margin-top: 1em;"></div>
						</div>
						<div id="innerShowGraphContainer" class="box-content" style="float: left; width:78%; height: 590px; display: inline-block;">
						</div>
					</div>
				</div>
                              
				<a id="showMetric" href="#" onclick="switchTab(0);" >Show Metrics Details</a>
                                <a id="hideMetric" href="#" onclick="switchTab(1);" style="display:none">Hide Metrics Details</a>
                               <br>
                               <br>
                                <!--
                                <a href="#" id="showMetric" href="#" onclick="switchTab(0);"><i title="Show Metric Detail" style="color:red"><u>Show Metrics Details</u></i></a>
                                <a href="#" id="hideMetric" href="#" onclick="switchTab(1);" style="display:none"><i title="Hide Metric Detail" style="color:red"><u>Hide Metrics Details</u></i>
				-->
                                <div id="tabs">                      
                                     
					<div class="box span12">
						<ul id="buildTabs">                                                    
                                                    <li><a id="gen_metr" href="#" title="result-general">General</a></li>                                                    
                                                    <li><a id="ts_metr" style="display:none" href="#" title="result-ts" onclick="switchCurrentTab('ts_metr');">T.S. Distance</a></li>
						    <li><a id="tfidf_metr" style="display:none" href="#" title="result-tfidf" onclick="switchCurrentTab('tfidf_metr');">TF-IDF</a></li>
						    <li><a id="lsi_metr" style="display:none" href="#" title="result-lsi" onclick="switchCurrentTab('lsi_metr');">LSI</a></li>
						    <li><a id="ig_metr" style="display:none" href="#" title="result-ig" onclick="switchCurrentTab('ig_metr');">I.G.</a></li>
						</ul>
						
						<div id="tabContent"> 
							<div id="result-general" class="tabDiv" >
								<div class="box-header" data-original-title>
									<h2><i class="halflings-icon tasks"></i><span class="break"></span>Suggested items</h2>
									<div class="box-icon">
										<a href="#" id="buttonShowGraphGeneral" class="btn-save"><i title="Show Graph" style="color:red"><u>Show Graph</u></i></a>
									</div>
								</div>
								<div class="box-content">
									<table id ="generalSeqTable" class="table table-hover table-bordered bootstrap-datatable">
									  <thead>
										  <tr>
										      <th hidden="true">pageId</th>
										      <th hidden="true">buildPosition</th>
											  <th>Title</th>
											  <th hidden="true">T.S. Distance</th>
											  <th hidden="true">TF-IDF (Cos. Sim.)</th>
											  <th hidden="true">LSI (Cos. Sim.)</th>
											  <th hidden="true">I.G. (Cos. Sim.)</th>
											  <th hidden="true">unescapedUrl</th>
                                                                                          <th style="width: 150px;">Feedback</th>
											  <th style="width: 100px;">Actions</th>
										  </tr>
									  </thead>   
									  <tbody id="generalSeqTableBody">
									  </tbody>
								  </table>     
								</div>
							</div>
                                                        <div id="result-ts" class="tabDiv" style="display:none">
								<div class="box-header" data-original-title>
									<h2><i class="halflings-icon tasks"></i><span class="break"></span>Suggested items - Teaching Style Distance</h2>
									<div class="box-icon">
										<a href="#" id="buttonShowGraphTS" class="btn-save"><i title="Show Graph" style="color:red"><u>Show Graph</u></i></a>
									</div>
								</div>
								<div class="box-content">
									<table id ="tsSeqTable" class="table table-hover table-bordered bootstrap-datatable">
									  <thead>
										  <tr>
										      <th hidden="true">pageId</th>
										      <th hidden="true">buildPosition</th>
											  <th>Title</th>
											  <th style="width: 120px;">T.S. Distance</th>
											  <th style="width: 120px;">TF-IDF (Cos. Sim.)</th>
											  <th style="width: 120px;">LSI (Cos. Sim.)</th>
											  <th style="width: 120px;">I.G. (Cos. Sim.)</th>
											  <th hidden="true">unescapedUrl</th>
                                                                                          <th style="width: 100px;">Feedback T.S.</th>
											  <th style="width: 100px;">Actions</th>
										  </tr>
									  </thead>   
									  <tbody id="tsSeqTableBody">
									  </tbody>
								  </table>     
								</div>
							</div>
							<div id="result-tfidf" class="tabDiv" style="display:none">
								<div class="box-header" data-original-title>
									<h2><i class="halflings-icon tasks"></i><span class="break"></span>Suggested items - TF-IDF</h2>
									<div class="box-icon">
										<a href="#" id="buttonShowGraphTFIDF" class="btn-save"><i title="Show Graph" style="color:red"><u>Show Graph</u></i></a>
									</div>
								</div>
								<div class="box-content">
									<table id ="tfidfSeqTable" class="table table-hover table-bordered bootstrap-datatable">
									  <thead>
										  <tr>
										      <th hidden="true">pageId</th>
										      <th hidden="true">buildPosition</th>
											  <th>Title</th>
											  <th style="width: 120px;">T.S. Distance</th>
											  <th style="width: 120px;">TF-IDF (Cos. Sim.)</th>
											  <th style="width: 120px;">LSI (Cos. Sim.)</th>
											  <th style="width: 120px;">I.G. (Cos. Sim.)</th>
											  <th hidden="true">unescapedUrl</th>
                                                                                          <th style="width: 100px;">Feedback TF-IDF</th>
											  <th style="width: 100px;">Actions</th>
										  </tr>
									  </thead>   
									  <tbody id="tfidfSeqTableBody">
									  </tbody>
								  </table>     
								</div>
							</div>
							<div id="result-lsi" class="tabDiv" style="display:none">
								<div class="box-header" data-original-title>
									<h2><i class="halflings-icon tasks"></i><span class="break"></span>Suggested items - LSI</h2>
									<div class="box-icon">
										<a href="#" id="buttonShowGraphLSI" class="btn-save"><i title="Show Graph" style="color:red"><u>Show Graph</u></i></a>
									</div>
								</div>
								<div class="box-content">
									<table id ="lsiSeqTable" class="table table-hover table-bordered bootstrap-datatable">
									  <thead>
										  <tr>
										      <th hidden="true">pageId</th>
										      <th hidden="true">buildPosition</th>
											  <th>Title</th>
											  <th style="width: 120px;">T.S. Distance</th>
											  <th style="width: 120px;">TF-IDF (Cos. Sim.)</th>
											  <th style="width: 120px;">LSI (Cos. Sim.)</th>
											  <th style="width: 120px;">I.G. (Cos. Sim.)</th>
											  <th hidden="true">unescapedUrl</th>
                                                                                          <th style="width: 100px;">Feedback LSI</th>
											  <th style="width: 100px;">Actions</th>
										  </tr>
									  </thead>   
									  <tbody id="lsiSeqTableBody">
									  </tbody>
								  </table>     
								</div>
							</div>
							<div id="result-ig" class="tabDiv" style="display:none">
								<div class="box-header" data-original-title>
									<h2><i class="halflings-icon tasks"></i><span class="break"></span>Suggested items - Information Gain</h2>
									<div class="box-icon">
										<a href="#" id="buttonShowGraphIG" class="btn-save"><i title="Show Graph" style="color:red"><u>Show Graph</u></i></a>
									</div>
								</div>
								<div class="box-content">
									<table id ="igSeqTable" class="table table-hover table-bordered bootstrap-datatable">
									  <thead>
										  <tr>
										      <th hidden="true">pageId</th>
										      <th hidden="true">buildPosition</th>
											  <th>Title</th>
											  <th style="width: 120px;">T.S. Distance</th>
											  <th style="width: 120px;">TF-IDF (Cos. Sim.)</th>
											  <th style="width: 120px;">LSI (Cos. Sim.)</th>
											  <th style="width: 120px;">I.G. (Cos. Sim.)</th>
											  <th hidden="true">unescapedUrl</th>
                                                                                          <th style="width: 100px;">Feedback I.G.</th>
											  <th style="width: 100px;">Actions</th>
										  </tr>
									  </thead>   
									  <tbody id="igSeqTableBody">
									  </tbody>
								  </table>     
								</div>
							</div>
						</div>
					</div>
				</div>
                                		
                                <a id="showMetricSel" href="#" onclick="showMetrics();" >Show Metrics Details</a>
                                <a id="hideMetricSel" href="#" onclick="hideMetrics();" style="display:none">Hide Metrics Details</a>
				<!-- scelta del corso -->
				<div id="wrapper">
					<form action="" method="post">
                                            <!--
                                            <a href="#" id="showMetricSel" href="#" onclick="showMetrics();"><i title="Show Metric Detail" style="color:red"><u>Show Metrics Details</u></i></a>
                                            <a href="#" id="hideMetricSel" href="#" onclick="hideMetrics();" style="display:none"><i title="Hide Metric Detail" style="color:red"><u>Show Metrics Details</u></i></a>
                                            -->
                                            
                                            	
                                            
						<div id="buildListContainer">
                                                    
							<div class="box span12">
								<div class="box-header" data-original-title>
									<h2><i class="halflings-icon tasks"></i><span class="break"></span>Selected items</h2>
									<div class="box-icon">
										<!-- <a href="#" id="buttonShowGraph" class="btn-save"><i title="Show Graph" class="halflings-icon info-sign"></i></a> -->
										<a href="#" id="buttonRemoveSelected" class="btn-save"><i title="Remove selected articles" class="halflings-icon trash"></i></a>
										<a href="#" id = "saveTopicButton" class="btn-save"><i title="Save topic" class="halflings-icon hdd"></i></a>
										<!-- <a href="#" class="btn-setting"><i class="halflings-icon wrench"></i></a>
										<a href="#" class="btn-minimize"><i class="halflings-icon chevron-up"></i></a>-->
									</div>
								</div>
								
								<div class="box-content">
									<table id ="buildResultTable" class="table table-hover table-bordered bootstrap-datatable">
									  <thead>
										  <tr>
										      <th hidden="true">pageId</th>
										      <th hidden="true">buildPosition</th>
											  <th width=34%>Title</th>
											  <th id="col_ts" style="display:none" width=12%>T.S. Distance</th>
											  <th id="col_tfidf" style="display:none" width=12%>TF-IDF (Cos. Sim.)</th>
											  <th id="col_lsi" style="display:none" width=12%>LSI (Cos. Sim.)</th>
											  <th id="col_ig" style="display:none" width=12%>I.G. (Cos. Sim.)</th>
											  <th hidden="true">unescapedUrl</th>
											  <th hidden="true">selectionInfo</th>
											  <th width=18%>Actions</th>
										  </tr>
									  </thead>   
									  <tbody id="buildResultTableBody">
									  </tbody>
								  </table>     
								</div>
							</div>
						</div><!--/row-->
					</form>
				</div>
			</div>
		</div>
	</div>
	
	<script src="/resources/js/jquery-1.9.1.min.js"></script>
	<script src="/resources/js/bootstrap.min.js"></script>
	<script src="/resources/js/jquery-migrate-1.0.0.min.js"></script>
	<script src="/resources/js/jquery-ui-1.10.0.custom.min.js"></script>
	<script src="/resources/js/jquery.ui.touch-punch.js"></script>
	<script src="/resources/js/modernizr.js"></script>
	
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
	<script src="/resources/js/jquery.tablesorter.js"></script>
	<script src="/resources/js/jquery-loader.js"></script>
	<script src="/resources/js/select2.min.js"></script>
	<script src="/resources/js/graph/sigma.min.js"></script>
	<!-- <script src="/resources/js/graph/sigma.parsers.json.min.js"></script> -->
	<script src="/resources/js/graph/sigma.parsers.gexf.min.js"></script>
	<!-- <script src="/resources/js/graph/sigma.layout.forceAtlas2.min.js"></script> -->
	<script src="/resources/js/commons.js"></script>
	
        <script src="/resources/js/jquery-ui.js"></script>
        <script src="/resources/js/jquery.barrating.js"></script>
  <script type="text/javascript">
      var tabCurrent="ts_metr";
      //hideMetrics();
      function showMetrics(){
          document.getElementById("showMetricSel").style.display="none";
           document.getElementById("hideMetricSel").style.display="";
        
           $('#buildResultTable').DataTable().column(3).visible(true);
           $('#buildResultTable').DataTable().column(4).visible(true);
           $('#buildResultTable').DataTable().column(5).visible(true);
           $('#buildResultTable').DataTable().column(6).visible(true);
           
           document.getElementById("col_ts").style.display="";
           document.getElementById("col_tfidf").style.display="";
           document.getElementById("col_ig").style.display="";
           document.getElementById("col_lsi").style.display="";
			
      }
      function hideMetrics(){
          document.getElementById("showMetricSel").style.display="";
           document.getElementById("hideMetricSel").style.display="none";
           
           document.getElementById("col_ts").style.display="none";
           document.getElementById("col_tfidf").style.display="none";
           document.getElementById("col_ig").style.display="none";
           document.getElementById("col_lsi").style.display="none";
           
           $('#buildResultTable').DataTable().column(3).visible(false);
           $('#buildResultTable').DataTable().column(4).visible(false);
           $('#buildResultTable').DataTable().column(5).visible(false);
           $('#buildResultTable').DataTable().column(6).visible(false);
        
      }
      function switchCurrentTab(t){
          tabCurrent=t;
      }
      //document.getElementById("result_lsi").style.display="none";
      //document.getElementById("lsi_metr").style.display="none";
      function switchTab(tabState){
          if(tabState===0){
              /*
            //document.getElementById("result_general").style.display="none";
             document.getElementById("result_ts").style.display="";
             document.getElementById("result_lsi").style.display="";
             document.getElementById("result_ig").style.display="";
             document.getElementById("result_tfidf").style.display=""; 
            */
           document.getElementById("showMetric").style.display="none";
           document.getElementById("hideMetric").style.display="";
           //document.getElementById("result_general").style.display="none";
           
           
           
            document.getElementById("gen_metr").style.display="none";
            
            
            
             document.getElementById("ts_metr").style.display="";
             document.getElementById("lsi_metr").style.display="";
             document.getElementById("ig_metr").style.display="";
             document.getElementById("tfidf_metr").style.display="";
             
             document.getElementById("result-ts").style.display="";
             document.getElementById("result-lsi").style.display="";
             document.getElementById("result-ig").style.display="";
             document.getElementById("result-tfidf").style.display="";
             
             
            eventFire(document.getElementById(tabCurrent), 'click'); 
            //document.getElementById("current").id="";
             
            //tabState=1;
          }
          else{
             /*
             //document.getElementById("result_general").style.display="";
             document.getElementById("result_ts").style.display="none";
             document.getElementById("result_lsi").style.display="none";
             document.getElementById("result_ig").style.display="none";
             document.getElementById("result_tfidf").style.display="none";
             */
            document.getElementById("showMetric").style.display="";
           document.getElementById("hideMetric").style.display="none";
           
              document.getElementById("gen_metr").style.display="";
              
              document.getElementById("result-ts").style.display="none";
             document.getElementById("result-lsi").style.display="none";
             document.getElementById("result-ig").style.display="none";
             document.getElementById("result-tfidf").style.display="none";
              
              document.getElementById("ts_metr").style.display="none";
             document.getElementById("lsi_metr").style.display="none";
             document.getElementById("ig_metr").style.display="none";
             document.getElementById("tfidf_metr").style.display="none";
             
             
             
             
             eventFire(document.getElementById('gen_metr'), 'click');
             //document.getElementById("current").id="";
             
              //tabState=0;
          }
      }
      var generalDT;
                        var tsDT;
			var tfidfDT;
			var lsiDT;
			var igDT;
      
      
      var user = "${principal.email}";
                    //console.log(user);
            var language="";
                        var generalScelti={};
			var tsScelti={};
			var tfScelti={};
			var lsiScelti={};
			var igScelti={};
  var feedback=-1;
  var numPagesSuggested=1;
  var pagesFeedback={};
  pagesFeedback.general=[];
  pagesFeedback.ts=[];
  pagesFeedback.tfidf=[];
  pagesFeedback.lsi=[];
  pagesFeedback.ig=[];
 
  var saved=-1;
  var statistica;
  var topicDataId;
  var courseDataId;
  var dialog, form,      
      allFields = $( [] ).add( feedback ),
      tips = $( ".validateTips" );
      function eventFire(el, etype){
        if (el.fireEvent) {
          el.fireEvent('on' + etype);
        } else {
          var evObj = document.createEvent('Events');
          evObj.initEvent(etype, true, false);
          el.dispatchEvent(evObj);
        }
}
      
 function saveStat(){
     
   
      $.post("/stats/save", {stat: JSON.stringify(statistica)}, function(r){
                                    //console.log("save stat ");
                                })
                                .fail(function(data) {

                                });
                                 
                                
                                
		    		window.open("/topicdetail.html?courseId="+courseDataId+"&topicId="+topicDataId, "_self");
                            }
  $( function() {
    
    function setFeedAllTab(feedb,t){
          //$('#'+divstr+' tbody').find('#'+rowData[1]+"-feed").val();
          var fff=feedb;//$('#generalSeqTable tbody').find('#'+rowData[1]+"-feed").val();
          
                    var allData =  tsDT.DataTable().rows().data();
                    for(var jj in allData){
                      var rowData=allData[jj];
                        if(rowData[2]===t){
                            $('#tsSeqTable tbody').find('#'+rowData[1]+"-feed").val(fff);
                            $('#tsSeqTable tbody').find('#'+rowData[1]+"-feed").barrating('set',fff);
                             break;
                        }
                 
                    }
                    allData =  tfidfDT.DataTable().rows().data();
                    for(var jj in allData){
                      var rowData=allData[jj];
                        if(rowData[2]===t){
                            $('#tfidfSeqTable tbody').find('#'+rowData[1]+"-feed").val(fff);
                            $('#tfidfSeqTable tbody').find('#'+rowData[1]+"-feed").barrating('set',fff);
                             break;
                        }
                    }
                    allData =  igDT.DataTable().rows().data();
                    for(var jj in allData){
                      var rowData=allData[jj];
                        if(rowData[2]===t){
                            $('#igSeqTable tbody').find('#'+rowData[1]+"-feed").val(fff);
                            $('#igSeqTable tbody').find('#'+rowData[1]+"-feed").barrating('set',fff);
                             break;
                        }
                    }
                    allData =  lsiDT.DataTable().rows().data();
                    for(var jj in allData){
                      var rowData=allData[jj];
                        if(rowData[2]===t){
                            $('#lsiSeqTable tbody').find('#'+rowData[1]+"-feed").val(fff);
                            $('#lsiSeqTable tbody').find('#'+rowData[1]+"-feed").barrating('set',fff);
                             break;
                        }
                    }
             
      }
    
 
    function updateTips( t ) {
      tips
        .text( t )
        .addClass( "ui-state-highlight" );
      setTimeout(function() {
        tips.removeClass( "ui-state-highlight", 1500 );
      }, 500 );
    }
 
 	
    function addFeed() {
      var valid = true;
      allFields.removeClass( "ui-state-error" );
 		feedback=$( "#feed" ).val();
                //console.log("feed "+feedback);
                if(feedback===""){
                    feedback=0;
                }
                statistica.stat.feedback=feedback;
                saveStat();
      return valid;
    }
 
    dialog = $( "#dialog-form" ).dialog({
      autoOpen: false,
      height: 400,
      width: 350,
      modal: true,
      buttons: {
        "Confirm": addFeed,
        Cancel: function() {
          dialog.dialog( "close" );
          saveStat();
        }
      },
      close: function() {
        form[ 0 ].reset();
        allFields.removeClass( "ui-state-error" );
      }
    });
 
    form = dialog.find( "form" ).on( "submit", function( event ) {
      event.preventDefault();
      addFeed();
    });
 /*
    $( "#create-feedback" ).button().on( "click", function() {
      dialog.dialog( "open" );
    });*/

			//loader init
			$loaderOptions = {
	            autoCheck: 32,
	            size: '32',
	            bgColor: '#FFF',
	            bgOpacity: 0.9,
	            fontColor: '#000', 
	            title: 'Executing build. Please wait...',
	            isOnly: true,
	            imgUrl: '/resources/img/loading[size].gif'
	        };
			
			//init result tabs
			$("#tabContent .tabDiv").hide(); // Initially hide all content
		    $("#buildTabs li:first").attr("id","current"); // Activate first tab
		    $("#tabContent .tabDiv:first").fadeIn(); // Show first tab content
		    $('#buildTabs a').click(function(e) {
		    	$("#showGraphContainer").hide();
		        e.preventDefault();        
		        $("#tabContent .tabDiv").hide(); //Hide all content
		        $("#buildTabs li").attr("id",""); //Reset id's
		        $(this).parent().attr("id","current"); // Activate this
		        $('#' + $(this).attr('title')).fadeIn(); // Show content for current tab
		    });
			
		    //course management functions
			$.getJSON("/course/getAll", function(r){
				var courseDataList = [];
				courseDataList.push({id:'', text: ''});
				
				var results = r.data;
				if(results.length){
					for(var i=0;i<results.length;i++){
						courseDataList.push({id: results[i].id, text: results[i].title, description: results[i].description});
					}
				}
				$("#courseTitleSelect").select2({
				   placeholder: "Select a course...",
				   //allowClear: true,
				   data: courseDataList,
				   templateResult: formatCourseSelect
				});
				
			})
		    .fail(function(data) {
		    	if(data.responseJSON.error !== null && data.responseJSON.message !== null){
		    		alert(data.responseJSON.message);
		    	}
		    });
			function formatCourseSelect (course) {
			  if (!course.id) { 
				  return course.text; 
			  }
			  var $course = $(
				'<span><b>' + course.text + '</b></span><br/><span><i>' + course.description + '</i></span>'
			  );
			  return $course;
			};
			var title=("${param.pageTitle}");//.replace(/'/g,'%27');
			
                        var constraints = ("${param.searchConstraints}");//.replace(/'/g,'%27');
			var depth = '${param.depth}';
                        language = '${param.lang}';
                        if(language===undefined||language===null||language===""){
                            language="en";
                        }
			$.loader.open($loaderOptions);
			
			/******************* tables for sequencing results *************************/
			
			
                        
                        var seqDTOptionsGeneral = {
				"order": [[ 2, "desc" ]],
				"sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span12'i><'span12 center'p>>",
				"bPaginate" : false,
				"bSort" : false,
				"bFilter" : false,
				"bInfo" : false,
				"oLanguage": 
				{
					"sLengthMenu": "_MENU_ records per page"
				},
                                
     
                                
		        "columnDefs": [
                            { className: "selects_columns", "targets": [ 5 ] },
		            {
		                "targets": [ 0 ],
		                "visible": false,
		                "searchable": false
		            },
		            {
		                "targets": [ 1 ],
		                "visible": false,
		                "searchable": false
		            },
                            {
		                "targets": [ 3 ],
		                "visible": false,
		                "searchable": false
		            },
                            {
		                "targets": [ 4 ],
		                "visible": false,
		                "searchable": false
		            },
                            {
		                "targets": [ 5 ],
		                "visible": false,
		                "searchable": false
		            },
                            {
		                "targets": [ 6 ],
		                "visible": false,
		                "searchable": false
		            },
		            {
		                "targets": [ 7 ],
		                "visible": false,
		                "searchable": false
		            },
		            {
		                "targets": [ 9 ],
		                "data": null,
		                "defaultContent":   '<a class="btn btn-success" id="buttonView" title="Open article page"><i class="halflings-icon white zoom-in"></i></a>&nbsp;'+
											'<a class="btn btn-info" id="buttonAdd" title="Add this page to favorites"><i class="halflings-icon white plus"></i></a>'
		            }
		        ]
		    };
                        
                        var seqDTOptionsIG = {
				"order": [[ 2, "desc" ]],
				"sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span12'i><'span12 center'p>>",
				"bPaginate" : false,
				"bSort" : false,
				"bFilter" : false,
				"bInfo" : false,
				"oLanguage": 
				{
					"sLengthMenu": "_MENU_ records per page"
				},
                                
     
                                
		        "columnDefs": [
                            { className: "selects_columns", "targets": [ 6 ] },
                            /*{ className: "selects_columns", "targets": [ 2 ] },
                            { className: "selects_columns", "targets": [ 8 ] },*/
		            {
		                "targets": [ 0 ],
		                "visible": false,
		                "searchable": false
		            },
		            {
		                "targets": [ 1 ],
		                "visible": false,
		                "searchable": false
		            },
		            {
		                "targets": [ 7 ],
		                "visible": false,
		                "searchable": false
		            },
		            {
		                "targets": [ 9 ],
		                "data": null,
		                "defaultContent":   '<a class="btn btn-success" id="buttonView" title="Open article page"><i class="halflings-icon white zoom-in"></i></a>&nbsp;'+
											'<a class="btn btn-info" id="buttonAdd" title="Add this page to favorites"><i class="halflings-icon white plus"></i></a>'
		            }
		        ]
		    };
                        
                        
                        var seqDTOptionsLSI = {
				"order": [[ 2, "desc" ]],
				"sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span12'i><'span12 center'p>>",
				"bPaginate" : false,
				"bSort" : false,
				"bFilter" : false,
				"bInfo" : false,
				"oLanguage": 
				{
					"sLengthMenu": "_MENU_ records per page"
				},
                                
     
                                
		        "columnDefs": [
                            { className: "selects_columns", "targets": [ 5 ] },
		            {
		                "targets": [ 0 ],
		                "visible": false,
		                "searchable": false
		            },
		            {
		                "targets": [ 1 ],
		                "visible": false,
		                "searchable": false
		            },
		            {
		                "targets": [ 7 ],
		                "visible": false,
		                "searchable": false
		            },
		            {
		                "targets": [ 9 ],
		                "data": null,
		                "defaultContent":   '<a class="btn btn-success" id="buttonView" title="Open article page"><i class="halflings-icon white zoom-in"></i></a>&nbsp;'+
											'<a class="btn btn-info" id="buttonAdd" title="Add this page to favorites"><i class="halflings-icon white plus"></i></a>'
		            }
		        ]
		    };
			
			var seqDTOptionsTFIDF = {
				"order": [[ 2, "desc" ]],
				"sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span12'i><'span12 center'p>>",
				"bPaginate" : false,
				"bSort" : false,
				"bFilter" : false,
				"bInfo" : false,
				"oLanguage": 
				{
					"sLengthMenu": "_MENU_ records per page"
				},
                                
     
                                
		        "columnDefs": [
                            { className: "selects_columns", "targets": [ 4 ] },
		            {
		                "targets": [ 0 ],
		                "visible": false,
		                "searchable": false
		            },
		            {
		                "targets": [ 1 ],
		                "visible": false,
		                "searchable": false
		            },
		            {
		                "targets": [ 7 ],
		                "visible": false,
		                "searchable": false
		            },
		            {
		                "targets": [ 9 ],
		                "data": null,
		                "defaultContent":   '<a class="btn btn-success" id="buttonView" title="Open article page"><i class="halflings-icon white zoom-in"></i></a>&nbsp;'+
											'<a class="btn btn-info" id="buttonAdd" title="Add this page to favorites"><i class="halflings-icon white plus"></i></a>'
		            }
		        ]
		    };
                    
                    
                    var seqDTOptionsTS = {
				"order": [[ 2, "desc" ]],
				"sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span12'i><'span12 center'p>>",
				"bPaginate" : false,
				"bSort" : false,
				"bFilter" : false,
				"bInfo" : false,
				"oLanguage": 
				{
					"sLengthMenu": "_MENU_ records per page"
				},
                                
     
                                
		        "columnDefs": [
                            { className: "selects_columns", "targets": [ 3 ] },
		            {
		                "targets": [ 0 ],
		                "visible": false,
		                "searchable": false
		            },
		            {
		                "targets": [ 1 ],
		                "visible": false,
		                "searchable": false
		            },
		            {
		                "targets": [ 7 ],
		                "visible": false,
		                "searchable": false
		            },
		            {
		                "targets": [ 9 ],
		                "data": null,
		                "defaultContent":   '<a class="btn btn-success" id="buttonView" title="Open article page"><i class="halflings-icon white zoom-in"></i></a>&nbsp;'+
											'<a class="btn btn-info" id="buttonAdd" title="Add this page to favorites"><i class="halflings-icon white plus"></i></a>'
		            }
		        ]
		    };
                    $('#generalSeqTable tbody').on( 'change', '.feed', function () {
				var rowData =  generalDT.DataTable().row($(this).parents('tr')).data();
                                
                                var feedb =$('#generalSeqTable tbody').find('#'+rowData[1]+"-feed").val();
                                /*
                                if(feedb===""){
                                    feedb=0;
                                }
        */
                 
                            /*    var arrTbl=[];
                                arrTbl.push("tsSeqTable");
                                arrTbl.push("tfidfSeqTable");
                                arrTbl.push("lsiSeqTable");
                                arrTbl.push("igSeqTable");*/
                                setFeedAllTab(feedb,rowData[2]);
                                pagesFeedback.general.push(rowData[1]);
		    } );
                        $('#tsSeqTable tbody').on( 'change', '.feed', function () {
				var rowData =  tsDT.DataTable().row($(this).parents('tr')).data();
                                pagesFeedback.ts.push(rowData[1]);
		    } );
			$('#tfidfSeqTable tbody').on( 'change', '.feed', function () {
				var rowData =  tfidfDT.DataTable().row($(this).parents('tr')).data();
                                pagesFeedback.tfidf.push(rowData[1]);
		    } );
			$('#lsiSeqTable tbody').on( 'change', '.feed', function () {
				var rowData =  lsiDT.DataTable().row($(this).parents('tr')).data();
                                pagesFeedback.lsi.push(rowData[1]);
		    } );
			$('#igSeqTable tbody').on( 'change', '.feed', function () {
				var rowData =  igDT.DataTable().row($(this).parents('tr')).data();
                                pagesFeedback.ig.push(rowData[1]);
		    } );
                        
                        
                                      
                        
                        
                        
                        $('#generalSeqTable tbody').on( 'click', '#buttonView', function () {
				var rowData =  generalDT.DataTable().row($(this).parents('tr')).data();
		    	window.open(rowData[7]);
		    } );
			$('#tsSeqTable tbody').on( 'click', '#buttonView', function () {
				var rowData =  tsDT.DataTable().row($(this).parents('tr')).data();
		    	window.open(rowData[7]);
		    } );
			$('#tfidfSeqTable tbody').on( 'click', '#buttonView', function () {
				var rowData =  tfidfDT.DataTable().row($(this).parents('tr')).data();
		    	window.open(rowData[7]);
		    } );
			$('#lsiSeqTable tbody').on( 'click', '#buttonView', function () {
				var rowData =  lsiDT.DataTable().row($(this).parents('tr')).data();
		    	window.open(rowData[7]);
		    } );
			$('#igSeqTable tbody').on( 'click', '#buttonView', function () {
				var rowData =  igDT.DataTable().row($(this).parents('tr')).data();
		    	window.open(rowData[7]);
		    } );
                    
                    
                    
                    $('#generalSeqTable tbody').on( 'click', '#buttonAdd', function () {
				var rowData =  generalDT.DataTable().row($(this).parents('tr')).data();
				var addedByUser = getAddedNodes();
                                //console.log("ver "+$(this).parents('tr').toString());
				if(!(rowData[2] in addedByUser)){
					$('#buildResultTable').DataTable().row.add(rowData);
					$('#buildResultTable').DataTable().draw();
                                        //var feedData =$('#tsSeqTable tbody').find('#'+rowData[1]+"-feed").val();
					//console.log("-------------------------->"+feedData);
					messageDialog('Operation done', "Page \""+rowData[2]+"\" added to list.");
					generalScelti[rowData[2]]={title: rowData[2], ts_rank: rowData[3], tfidf_rank: rowData[4], lsi_rank: rowData[5] , ig_rank: rowData[6]};
				}
		    } );
			$('#tsSeqTable tbody').on( 'click', '#buttonAdd', function () {
				var rowData =  tsDT.DataTable().row($(this).parents('tr')).data();
				var addedByUser = getAddedNodes();
				if(!(rowData[2] in addedByUser)){
					$('#buildResultTable').DataTable().row.add(rowData);
					$('#buildResultTable').DataTable().draw();
                                        //var feedData =$('#tsSeqTable tbody').find('#'+rowData[1]+"-feed").val();
					//console.log("-------------------------->"+feedData);
					messageDialog('Operation done', "Page \""+rowData[2]+"\" added to list.");
					tsScelti[rowData[2]]={title: rowData[2], ts_rank: rowData[3], tfidf_rank: rowData[4], lsi_rank: rowData[5] , ig_rank: rowData[6]};
				}
		    } );
			$('#tfidfSeqTable tbody').on( 'click', '#buttonAdd', function () {
				var rowData =  tfidfDT.DataTable().row($(this).parents('tr')).data();
				var addedByUser = getAddedNodes();
				if(!(rowData[2] in addedByUser)){
					$('#buildResultTable').DataTable().row.add(rowData);
					$('#buildResultTable').DataTable().draw();
					
					messageDialog('Operation done', "Page \""+rowData[2]+"\" added to list.");
					tfScelti[rowData[2]]={title: rowData[2], ts_rank: rowData[3], tfidf_rank: rowData[4], lsi_rank: rowData[5] , ig_rank: rowData[6]};
				}
		    } );
			$('#lsiSeqTable tbody').on( 'click', '#buttonAdd', function () {
				var rowData =  lsiDT.DataTable().row($(this).parents('tr')).data();
				var addedByUser = getAddedNodes();
				if(!(rowData[2] in addedByUser)){
					$('#buildResultTable').DataTable().row.add(rowData);
					$('#buildResultTable').DataTable().draw();
					
					messageDialog('Operation done', "Page \""+rowData[2]+"\" added to list.");
					lsiScelti[rowData[2]]={title: rowData[2], ts_rank: rowData[3], tfidf_rank: rowData[4], lsi_rank: rowData[5] , ig_rank: rowData[6]};
				}
		    } );
			$('#igSeqTable tbody').on( 'click', '#buttonAdd', function () {
				var rowData =  igDT.DataTable().row($(this).parents('tr')).data();
				var addedByUser = getAddedNodes();
				if(!(rowData[2] in addedByUser)){
					$('#buildResultTable').DataTable().row.add(rowData);
					$('#buildResultTable').DataTable().draw();
					
					messageDialog('Operation done', "Page \""+rowData[2]+"\" added to list.");
					igScelti[rowData[2]]={title: rowData[2], ts_rank: rowData[3], tfidf_rank: rowData[4], lsi_rank: rowData[5] , ig_rank: rowData[6]};
				}
		    } );
			
			function initSeqDTS() {
                            generalDT = $('#generalSeqTable').dataTable(seqDTOptionsGeneral);
				tsDT = $('#tsSeqTable').dataTable(seqDTOptionsTS);
				tfidfDT = $('#tfidfSeqTable').dataTable(seqDTOptionsTFIDF);
				lsiDT = $('#lsiSeqTable').dataTable(seqDTOptionsLSI);
				igDT = $('#igSeqTable').dataTable(seqDTOptionsIG);
                                
			}
			initSeqDTS();
			
			/******************* table that contains user collected items *************************/
			var dataTable;
			var tableOptions = {
				"order": [[ 2, "desc" ]],
				"sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span12'i><'span12 center'p>>",
				"bPaginate" : false,
				"bSort" : false,
				"oLanguage": 
				{
					"sLengthMenu": "_MENU_ records per page"
				},
		        "columnDefs": [
		            {
		                "targets": [ 0 ],
		                "visible": false,
		                "searchable": false
		            },
		            {
		                "targets": [ 1 ],
		                "visible": false,
		                "searchable": false
		            },
                            
                            {
		                "targets": [ 3 ],
		                "visible": false,
		                "searchable": false
		            },
                            {
		                "targets": [ 4 ],
		                "visible": false,
		                "searchable": false
		            },
                            {
		                "targets": [ 5 ],
		                "visible": false,
		                "searchable": false
		            },
                            {
		                "targets": [ 6 ],
		                "visible": false,
		                "searchable": false
		            },
                            
		            {
		                "targets": [ 7 ],
		                "visible": false,
		                "searchable": false
		            },
		            {
		                "targets": [ 8 ],
		                "visible": false,
		                "searchable": false
		            },
		            {
		                "targets": [ 9 ],
		                "data": null,
		                "defaultContent":   '<a class="btn btn-success" id="buttonView" title="Open article page"><i class="halflings-icon white zoom-in"></i></a>&nbsp;'+
											'<a class="btn btn-info" id="buttonUp" title="Move article up"><i class="halflings-icon white arrow-up"></i></a>&nbsp;'+
											'<a class="btn btn-info" id="buttonDown" title="Move article down"><i class="halflings-icon white arrow-down"></i></a>&nbsp;'+
											'<a class="btn btn-danger" id="buttonRemove" title="Remove article"><i class="halflings-icon white trash"></i></a>'
		            }
		        ]
		    };
			
			$('#buildResultTable tbody').on( 'click', '#buttonView', function () {
				var rowData =  dataTable.DataTable().row($(this).parents('tr')).data();
		    	window.open(rowData[7]);
		    } );
			
			$('#buildResultTable tbody').on( 'click', '#buttonRemove', function () {
				var title =  dataTable.DataTable().row($(this).parents('tr')).data()[2];
				if (confirm("Are you sure you want to remove page \""+title+"\" ?")){
                                    delete generalScelti[title];
                                    delete tsScelti[title];
                                    delete tfScelti[title];
                                    delete lsiScelti[title];
                                    delete igScelti[title];
					dataTable.DataTable().row( $(this).parents('tr') ).remove().draw( false );
				}
		    } );
			
			$('#buildResultTable tbody').on('click', '#buttonUp', function(e){
			    var index = dataTable.DataTable().row($(this).parents('tr')).index();
			    if ((index-1) >= 0) {
			        var data = dataTable.fnGetData();
			        dataTable.fnClearTable();
			        data.splice((index-1), 0, data.splice(index,1)[0]);
			        dataTable.fnAddData(data);
			    }
			});
			$('#buildResultTable tbody').on('click', '#buttonDown', function(e){
				var index = dataTable.DataTable().row($(this).parents('tr')).index();
			    if ((index+1) >= 0) {
			        var data = dataTable.fnGetData();
			        dataTable.fnClearTable();
			        data.splice((index+1), 0, data.splice(index,1)[0]);
			        dataTable.fnAddData(data);
			    }
			});
			
			function initDataTable() {
				dataTable = $('#buildResultTable').dataTable(tableOptions);
			}
			
			initDataTable();
			
			function loadTableData(table, r, selectionInfos, tableDivName, seqDTOptions){
				var results = r;

				if(results.length){
					
					table.fnDestroy();
					table = $('#'+tableDivName).dataTable(seqDTOptions);
					table.DataTable().clear();
					for(var i=0;i<results.length;i++){
						table.DataTable().row.add(new addItemToTable(results[i], selectionInfos));
					}
					table.DataTable().draw();
				}
			}
                        /*
			console.log(encodeURIComponent(title));
                        console.log(decodeURIComponent(title));
                        console.log(title);
                        */
			$.getJSON("/build/fromPage", {pageTitle: title, searchConstraints: constraints, depth: depth, lang: language}, function(r){
				
				if(r.data.ts_rank){
                                        numPagesSuggested=r.data.ts_rank.length;
					loadTableData(tsDT, r.data.ts_rank, r.selectionInfos, 'tsSeqTable', seqDTOptionsTS);	
				}
				if(r.data.tfidf_rank){
                                        numPagesSuggested=r.data.tfidf_rank.length;
					loadTableData(tfidfDT, r.data.tfidf_rank, r.selectionInfos, 'tfidfSeqTable', seqDTOptionsTFIDF);
				}	
				if(r.data.lsi_rank){
                                        numPagesSuggested=r.data.lsi_rank.length;
					loadTableData(lsiDT, r.data.lsi_rank, r.selectionInfos, 'lsiSeqTable', seqDTOptionsLSI);
				}
				if(r.data.ig_rank){
                                        numPagesSuggested=r.data.ig_rank.length;
					loadTableData(igDT, r.data.ig_rank, r.selectionInfos, 'igSeqTable', seqDTOptionsIG);
				}
                                //add general 
                                if(r.data.general){
                                        numPagesSuggested=r.data.general.length;
					loadTableData(generalDT, r.data.general, r.selectionInfos, 'generalSeqTable', seqDTOptionsGeneral);
				}
                                
				
				$('#graphVal').val(r.graph.gexf);
				
				$.loader.close();
                                
                                
                                
                                
                                for(var x=0;x<numPagesSuggested;x++){
      //for(var x=0;x<5;x++){
         //var feedData =$('#igSeqTable tbody').find('#'+rowData[1]+"-feed").val();
          $('#tsSeqTable tbody').find('#'+x+"-feed").barrating({
        theme: 'fontawesome-stars'
      });
      $('#tfidfSeqTable tbody').find('#'+x+"-feed").barrating({
        theme: 'fontawesome-stars'
      });
      $('#lsiSeqTable tbody').find('#'+x+"-feed").barrating({
        theme: 'fontawesome-stars'
      });
      $('#igSeqTable tbody').find('#'+x+"-feed").barrating({
        theme: 'fontawesome-stars'
      });
      $('#generalSeqTable tbody').find('#'+x+"-feed").barrating({
        theme: 'fontawesome-stars'
      });
  }
  var npT=numPagesSuggested*4;
                                for(var x=numPagesSuggested;x<npT;x++){
                                $('#generalSeqTable tbody').find('#'+x+"-feed").barrating({
        theme: 'fontawesome-stars'
      });
        }
			})
			.fail(function(data) {
				$.loader.close();
                        if(data.responseJSON!==undefined){
                            if(data.responseJSON.error !== null && data.responseJSON.message !== null){
                                    alert(data.responseJSON.message);
                            }
                        }
		    });
			function addItemToTable(item, selectionInfos){
				var arr = [];
				//arr.push(item.pageId);
                                arr.push(item.title);
				arr.push(item.buildPosition);
				arr.push(item.title);
				arr.push(item.ts_rank);
				arr.push(item.tfidf_rank);
				arr.push(item.lsi_rank);
				arr.push(item.ig_rank);
				arr.push(item.unescapedUrl);
                                arr.push('<select class="feed" id="'+item.buildPosition+'-feed" name="'+item.buildPosition+'-feed"><option value=""></option><option value="1">1</option><option value="2">2</option><option value="3">3</option><option value="4">4</option><option value="5">5</option></select>');
                               
				
				var selInfo = {};
				if(selectionInfos && selectionInfos.length){
					for(var i=0;i<selectionInfos.length;i++){
						if(item.title===selectionInfos[i].title){
							selInfo = selectionInfos[i].selectionInfo;
							break;
						}
					}
				}
				arr.push(selInfo);
				
				return arr;
			}
		});
       			
		$('#buttonCreateNewCourse').on('click', function(e){
			$('#newCourseButtonContainer').hide();
			$('#newCourseTitleContainer').show();
			$('#courseTitleSelect').val('').trigger("change");
			$('#courseTitleSelect').prop("disabled", true);
		});
		
		$('#cancelCreateNewCourse').on('click', function(e){
			$('#newCourseTitleContainer').hide();
			$('#newCourseButtonContainer').show();
			$('#courseTitleSelect').val('').trigger("change");
			$('#courseTitleSelect').prop("disabled", false);
		});
		
		$('#buildResultTableBody').on( 'click', 'tr', function () {
	        $(this).toggleClass('selected');
	    } );
		
		$("#buttonRemoveSelected").click(function() {
			
			//retrieving all selected rows
			var rowIndexes = $('#buildResultTable').DataTable().rows('.selected')[0];
			
			if(rowIndexes.length>0){
                            //console.log("Remove log: "+JSON.stringify($('#buildResultTable').DataTable().rows(rowIndexes).data()));
                            var titolo=$('#buildResultTable').DataTable().rows(rowIndexes).data()[0][2];
                            //console.log(titolo);
				if (confirm("Are you sure you want to remove selected pages ?")){
                                    delete generalScelti[titolo];
                                    delete tsScelti[titolo];
                                    delete tfScelti[titolo];
                                    delete lsiScelti[titolo];
                                    delete igScelti[titolo];
					$('#buildResultTable').DataTable().rows(rowIndexes).remove().draw( false );
                                        
				}
			}
			else{
				messageDialog('Warning', 'There aren\'t selected rows to remove');
			}
		});
		
		function getAddedNodes(){
			var result = {};
			$('#buildResultTableBody tr').each(function (index, value) {
		    	
		    	var rowData =  $('#buildResultTable').DataTable().row(index).data();
		    	if(rowData && typeof(rowData) !== "undefined"){
		    		result[rowData[2]]=true;
		    	}
		    });
			return result;
		}
		
		function drawGraphLegend(selectedByUserColor, selectedByRankingColor){
			var legendContainer = $('#graphLegend');
			
			legendContainer.empty();
			
			var html='<span style="font-size: 14px; margin-left:2em; margin-top: 0em; margin-bottom: 0em;"><b>Node colors legend:</b></span><br /><table><tr>';
			html += '<td><span style="font-size: 10px; margin-left:3em; margin-top: 0em; margin-bottom: 0em; background-color:'+selectedByRankingColor+';">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span style="font-size: 10px; margin-left:0.5em;">Selected by Ranking process</span></td>';
			html += '<td><span style="font-size: 10px; margin-left:3em; margin-top: 0em; margin-bottom: 0em; background-color:'+selectedByUserColor+';">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span style="font-size: 10px; margin-left:0.5em;">Selected by user</span></td>';
			html +='</tr></table>';
			
			legendContainer.append(html);
		}
		
		function drawGraph(rankType) {
			//sigma graph
			var sg;
			//graphic configurations
			var gConfigs = {
				selectedByRankColor	: 'rgb(185,17,0)',
				selectedByRankBorderColor : 'rgb(125,15,0)',
				selectedByUserColor : 'rgb(49,125,182)',
				selectedByUserBorderColor : 'rgb(43,108,120)',
				selectedNodeSize : 10,
				selectedNodeBorderSize : 3,
				normalColor : 'rgb(128,128,128)',
				normalBorderColor : 'rgb(90,90,90)',
				normalNodeSize : 6,
				normalNodeBorderSize : 2,
				normalEdgeSize : 1,
				selectedEdgeSize : 3,
				fadeColor : '#eee',
				fadeBorderColor : '#ccc'
			}
			
			$("#innerShowGraphContainer").empty();
			$("#leftGraphInfoContainer").empty();
			$("#showGraphContainer").show();
			drawGraphLegend(gConfigs.selectedByUserColor, gConfigs.selectedByRankColor);
			
			var gexfXml = $.parseXML( $("#graphVal").val() );
		
			if(!sigma.classes.graph.hasMethod('neighbors')){
				sigma.classes.graph.addMethod('neighbors', function(nodeId) {
					
				    var k,
				        neighbors = {},
				        index = this.allNeighborsIndex[nodeId] || {};
	
				    for (k in index)
				      neighbors[k] = this.nodesIndex[k];
	
				    return neighbors;
    			});
				
			}

			function fillInfoPanel(node, neighbors, allNodes){
				var leftContainer = $("#leftGraphInfoContainer");
				leftContainer.empty();
				leftContainer.append( "<p style=\"font-size: 12px; margin-top: 0em; margin-bottom: 0em;\"><b>Page:</b></p>" ).append( "<p style=\"font-size: 10px; margin-left:2em; margin-top: 0em; margin-bottom: 0em;\">"+node.label+"</p>" );
				
				var $addToTableLink = $( "<a href=\"javascript:void(0);\"</a><p style=\"font-size: 10px; color: darkgreen; margin-left:0em; margin-top: 0em; margin-bottom: 0em;\"><i><b><u>Add this page to list</u></b></i></p>" );
				leftContainer.append($addToTableLink);
				
				leftContainer.append( "<p style=\"font-size: 12px; margin-top: 0em; margin-bottom: 0em;\"><b>Scores:</b></p>" );
				leftContainer.append( "<p style=\"font-size: 10px; margin-left:2em; margin-top: 0em; margin-bottom: 0em;\"><b>T.S. Distance:</b>&nbsp;"+node.attributes.ts_rank+"</p>" );
				leftContainer.append( "<p style=\"font-size: 10px; margin-left:2em; margin-top: 0em; margin-bottom: 0em;\"><b>TF-IDF (cos. sim.):</b>&nbsp;"+node.attributes.tfidf_rank+"</p>" );
				leftContainer.append( "<p style=\"font-size: 10px; margin-left:2em; margin-top: 0em; margin-bottom: 0em;\"><b>LSI (cos. sim.):</b>&nbsp;"+node.attributes.lsi_rank+"</p>" );
				leftContainer.append( "<p style=\"font-size: 10px; margin-left:2em; margin-top: 0em; margin-bottom: 0em;\"><b>I.G. (cos. sim.):</b>&nbsp;"+node.attributes.ig_rank+"</p>" );
				
				leftContainer.append( "<p style=\"font-size: 12px; margin-top: 0em; margin-bottom: 0em;\"><b>URL:</b></p>" ).append( "<a href=\""+node.attributes.url+"\" target=\"_blank\"</a><p style=\"font-size: 10px; color: blue; margin-left:2em; margin-top: 0em; margin-bottom: 0em;\">"+node.attributes.unescapedUrl+"</p>" );
				leftContainer.append( "<p style=\"font-size: 12px; margin-top: 0em; margin-bottom: 0em;\"><b>Linked to:</b></p>" ).append("<p style=\"font-size: 10px; margin-left:2em; margin-top: 0em; margin-bottom: 0em;\"></p>");
				
				$addToTableLink.click({node: node}, function(e){
					var node = e.data.node;
					
					var addedByUser = getAddedNodes();
					if(!(node.label in addedByUser)){
						var arr = [];
						//arr.push(node.attributes.pageId);
                                                arr.push(node.label);
						arr.push(-1);
						arr.push(node.label);
						arr.push(node.attributes.ts_rank);
						arr.push(node.attributes.tfidf_rank);
						arr.push(node.attributes.lsi_rank);
						arr.push(node.attributes.ig_rank);
						arr.push(node.attributes.unescapedUrl);
						if(node.attributes.selectionInfo)
							arr.push(node.attributes.selectionInfo);
						else
							arr.push({});
						
						$('#buildResultTable').DataTable().row.add(arr);
						$('#buildResultTable').DataTable().draw();
						
						
						if(!node.attributes.ts_rank_selected && !node.attributes.tfidf_rank_selected && 
								!node.attributes.lsi_rank_selected && !node.attributes.ig_rank_selected){
							node.color=gConfigs.selectedByUserColor;
							node.borderColor=gConfigs.selectedByUserBorderColor;
							node.size=gConfigs.selectedNodeSize;
							node.borderWidth=gConfigs.selectedNodeBorderSize;
							node.originalColor = node.color;
							node.originalBorderColor = node.borderColor;
					     }
						
						sg.refresh();
						
						messageDialog('Operation done', "Page \""+node.label+"\" added to list.");
					}
	        	});
				
				allNodes.forEach(function(n) {
		          if (neighbors[n.id]){
		        	  
		        	  var $newLink = $( "<a href=\"javascript:void(0);\"</a><p style=\"font-size: 10px; color: blue; margin-left:2em; margin-top: 0em; margin-bottom: 0em;\">"+n.label+"</p>" );
		        	  leftContainer.append($newLink);
		        	  
		        	  $newLink.click({node: n}, function(e){
		        		  nodeSelection(e);
		        	  });

		          }
		        });
			}
			
			function nodeSelection(e){
				var node = e.data.node;
                  	var nodeId = node.id,
	            toKeep = sg.graph.neighbors(nodeId);
		        toKeep[nodeId] = node;

		        sg.graph.nodes().forEach(function(n) {
		          if (toKeep[n.id]){
		            n.color = n.originalColor;
		            n.borderColor = n.originalBorderColor;
		          }
		          else{
		            n.color = gConfigs.fadeColor;
		          	n.borderColor = gConfigs.fadeBorderColor;
		          }
		        });

		        sg.graph.edges().forEach(function(e) {
		          if (toKeep[e.source] && toKeep[e.target])
		            e.color = e.originalColor;
		          else
		            e.color = gConfigs.fadeColor;
		        });

		        // Since the data has been modified, we need to
		        // call the refresh method to make the colors
		        // update effective.
		        sg.refresh();
		        
		        fillInfoPanel(node, toKeep, sg.graph.nodes());
		    }
				
			sigma.canvas.nodes.border = function(node, context, settings) {

			  // Bit technical, determining the prefix on which the renderer must act
			  var prefix = settings('prefix') || '';

			  // Creating a circle and filling it with the desired color
			  // This is plain canvas
			  context.fillStyle = node.color || settings('defaultNodeColor');
			  context.beginPath();
			  context.arc(
			    node[prefix + 'x'],
			    node[prefix + 'y'],
			    node[prefix + 'size'],
			    0,
			    Math.PI * 2,
			    true
			  );

			  context.closePath();
			  context.fill();
			}

			sigma.canvas.nodes.def = function(node, context, settings) {
			  var prefix = settings('prefix') || '';

			  context.fillStyle = node.color || settings('defaultNodeColor');
			  context.beginPath();
			  context.arc(
			    node[prefix + 'x'],
			    node[prefix + 'y'],
			    node[prefix + 'size'],
			    0,
			    Math.PI * 2,
			    true
			  );

			  context.closePath();
			  context.fill();

			  // Adding a border
			  context.lineWidth = node.borderWidth || gConfigs.normalNodeBorderSize;
			  context.strokeStyle = node.borderColor || gConfigs.normalBorderColor;
			  context.stroke();
			}
			
			sigma.parsers.gexf(
			  gexfXml, { 
				  renderer : {
				  	type:'canvas',
				  	//container: 'innerShowGraphContainer',
				  	container: document.getElementById('innerShowGraphContainer')
				  },
     			  settings: {
     				defaultNodeType: 'border',
     				minEdgeSize: gConfigs.normalEdgeSize,
     			    maxEdgeSize: gConfigs.selectedEdgeSize,
     			    minArrowSize: gConfigs.selectedEdgeSize+3
     			  }
			  },
			  function(s) {
				  //s.settings.embedObjects({defaultNodeType: 'border'});
				  sg = s;
			      // We first need to save the original colors of our
			      // nodes and edges, like this:
			    	  
			      var addedByUser = getAddedNodes();
			    	  
			      s.graph.nodes().forEach(function(n) {
			        if(
			        	(rankType==='ts_rank' && n.attributes && n.attributes.ts_rank_selected) ||
			        	(rankType==='tfidf_rank' && n.attributes && n.attributes.tfidf_rank_selected) ||
			        	(rankType==='lsi_rank' && n.attributes && n.attributes.lsi_rank_selected) ||
			        	(rankType==='ig_rank' && n.attributes && n.attributes.ig_rank_selected) ||
                                        (rankType==='general' && n.attributes && (n.attributes.ig_rank_selected||n.attributes.ts_rank_selected||n.attributes.tfidf_rank_selected||n.attributes.lsi_rank_selected))
			          ){
			        	n.color=gConfigs.selectedByRankColor;
			        	n.borderColor=gConfigs.selectedByRankBorderColor;
			        	n.size=gConfigs.selectedNodeSize;
			        	n.borderWidth=gConfigs.selectedNodeBorderSize;
			        }
			        else if(n.label in addedByUser){
			        	n.color=gConfigs.selectedByUserColor;
			        	n.borderColor=gConfigs.selectedByUserBorderColor;
			        	n.size=gConfigs.selectedNodeSize;
			        	n.borderWidth=gConfigs.selectedNodeBorderSize;
			        }
			        else{
			        	n.color=gConfigs.normalColor;
			        	n.borderColor=gConfigs.normalBorderColor;
			        	n.size=gConfigs.normalNodeSize;
			        	n.borderWidth=gConfigs.normalNodeBorderSize;
			        }
			        n.originalColor = n.color;
			        n.originalBorderColor = n.borderColor;
			      });
			  
			      var importantEdges = [];
			      var pnt=0;
			      s.graph.edges().forEach(function(e) {
			    	  if(
			        	  (rankType==='ts_rank' && e.attributes && e.attributes.ts_rank_selected) ||
			        	  (rankType==='tfidf_rank' && e.attributes && e.attributes.tfidf_rank_selected) ||
			        	  (rankType==='lsi_rank' && e.attributes && e.attributes.lsi_rank_selected) ||
			        	  (rankType==='ig_rank' && e.attributes && e.attributes.ig_rank_selected)||
                                            (rankType==='general' && e.attributes && (e.attributes.ig_rank_selected||e.attributes.ts_rank_selected||e.attributes.tfidf_rank_selected||e.attributes.lsi_rank_selected))
                                          ){
			    		  e.color=gConfigs.selectedByRankColor;
			    		  e.type="arrow";
			    		  e.size=gConfigs.selectedEdgeSize;
			    		  e.originalColor = e.color;
			    		  importantEdges[pnt] = e;
			    		  pnt++;
			    	  }
			    	  else{
			    		  e.color=gConfigs.normalColor;
			    		  e.size=gConfigs.normalEdgeSize;
			    		  e.originalColor = e.color;
			    	  }
			      });

			      //for edge overlapping
			      for(i=0; i<importantEdges.length; i++){
			    	  try {
			    	 	 s.graph.dropEdge(importantEdges[i].id);
			    	  }catch(err) {
			    		  //do nothing
			    	  }
			      }
			      
			      for(i=0; i<importantEdges.length; i++){
			    	  try {
			    		  s.graph.addEdge(importantEdges[i]);
			    	  }catch(err) {
			    		  //do nothing
			    	  }
			      }
			      
			      s.refresh();
			      
			      // When a node is clicked, we check for each node
			      // if it is a neighbor of the clicked one. If not,
			      // we set its color as grey, and else, it takes its
			      // original color.
			      // We do the same for the edges, and we only keep
			      // edges that have both extremities colored.
			    
			      s.bind('clickNode', nodeSelection);

			      // When the stage is clicked, we just color each
			      // node and edge with its original color.
			      s.bind('clickStage', function(e) {
			        s.graph.nodes().forEach(function(n) {
			          n.color = n.originalColor;
			          n.borderColor = n.originalBorderColor;
			        });

			        s.graph.edges().forEach(function(e) {
			          e.color = e.originalColor;
			        });
			        
			        // Same as in the previous event:
			        s.refresh();
			        
			        $("#leftGraphInfoContainer").empty();
			      });
			    }
			);
		}
		$("#buttonShowGraphGeneral").click(function() {
			drawGraph('general');
		});
		
		$("#buttonShowGraphTS").click(function() {
			drawGraph('ts_rank');
		});
		
		$("#buttonShowGraphTFIDF").click(function() {
			drawGraph('tfidf_rank');
		});
				
		$("#buttonShowGraphLSI").click(function() {
			drawGraph('lsi_rank');
		});
		
		$("#buttonShowGraphIG").click(function() {
			drawGraph('ig_rank');
		});
		
		$("#buttonHideGraph").click(function() {
			$("#showGraphContainer").hide();
		});
		
		$("#saveTopicButton, #globalButtonTopicSave").click(function() {
			//retrieves course title
			var courseId = '';
			var courseName = '';
			var topicId = '';
			var topicName = '';
			
                        var staringPage=("${param.pageTitle}");//.replace(/'/g,'%27');
			
                        var queryConstraints = ("${param.searchConstraints}");//.replace(/'/g,'%27');
			
			
			
			if($('#courseTitleSelect').prop('disabled') === true){
				courseId = '-1';
				courseName = $('#newCourseTitle').val().trim();
			}
			else{
				courseId = $("#courseTitleSelect option:selected").val();
				courseName = $("#courseTitleSelect option:selected").text();
			}
			
			if(typeof(courseId) === "undefined" || typeof(courseName) === "undefined" || courseId ==='' || courseName===''){
				messageDialog('Warning', 'Choose an existing course or define a new one.');
				return;
			}
				
			topicName = $('#topicTitle').val().trim();
			
			if(topicName ===''){
				messageDialog('Warning', 'Define a valid topic title.');
				return;
			}
			
			//retrieves all pages
			var allPages = [];
			var actualPos = 1;

		    $('#buildResultTableBody tr').each(function (index, value) {
		    	
		    	var page = {};

		    	var rowData =  $('#buildResultTable').DataTable().row(index).data();
		    	
		    	if(rowData && typeof(rowData) !== "undefined"){
		    		page.pageId = rowData[0];
			    	page.buildPosition = rowData[1];
			    	page.title = rowData[2];
			    	page.userPosition = actualPos;
			    	page.ts_rank = rowData[3];
			    	page.tfidf_rank = rowData[4];
			    	page.lsi_rank = rowData[5];
			    	page.ig_rank = rowData[6];
			    	page.unescapedUrl = rowData[7];
                                var sin=rowData[9];
                                if(sin===undefined){
                                    sin={};
                                }
			    	page.selectionInfo= sin;
			    	allPages.push(page);
		    	
			    	actualPos++;	   
		    	}
		    });
		    
                    var divstr="";
                    var generalDTable = [];
			actualPos = 1;
                        divstr="generalSeqTable";
		    $('#'+divstr+'Body tr').each(function (index, value) {
		    	
		    	var page = {};

		    	var rowData =  $('#'+divstr).DataTable().row(index).data();
		    	
		    	if(rowData && typeof(rowData) !== "undefined"){
		    		page.pageId = rowData[0];
			    	page.buildPosition = rowData[1];
			    	page.title = rowData[2];
			    	page.userPosition = actualPos;
			    	page.ts_rank = rowData[3];
			    	page.tfidf_rank = rowData[4];
			    	page.lsi_rank = rowData[5];
			    	page.ig_rank = rowData[6];
			    	page.unescapedUrl = rowData[7];
			    	var sin=rowData[9];
                                if(sin===undefined){
                                    sin={};
                                }
			    	page.selectionInfo= sin;
                                
                                var feedData =-1;
                                if(jQuery.inArray( rowData[1], pagesFeedback.general )>=0){
                                    feedData =$('#'+divstr+' tbody').find('#'+rowData[1]+"-feed").val();
                                    if(feedData===""){
                                        feedData=0;
                                    }
                                }
                                page.pageFeedback=feedData;
                                
			    	generalDTable.push(page);
		    	
			    	actualPos++;	   
		    	}
		    });
                    
                    
                    divstr="";
                    var tsDTable = [];
			actualPos = 1;
                        divstr="tsSeqTable";
		    $('#'+divstr+'Body tr').each(function (index, value) {
		    	
		    	var page = {};

		    	var rowData =  $('#'+divstr).DataTable().row(index).data();
		    	
		    	if(rowData && typeof(rowData) !== "undefined"){
		    		page.pageId = rowData[0];
			    	page.buildPosition = rowData[1];
			    	page.title = rowData[2];
			    	page.userPosition = actualPos;
			    	page.ts_rank = rowData[3];
			    	page.tfidf_rank = rowData[4];
			    	page.lsi_rank = rowData[5];
			    	page.ig_rank = rowData[6];
			    	page.unescapedUrl = rowData[7];
			    	var sin=rowData[9];
                                if(sin===undefined){
                                    sin={};
                                }
			    	page.selectionInfo= sin;
                                
                                var feedData =-1;
                                if(jQuery.inArray( rowData[1], pagesFeedback.ts )>=0){
                                    feedData =$('#'+divstr+' tbody').find('#'+rowData[1]+"-feed").val();   
                                    if(feedData===""){
                                        feedData=0;
                                    }
                                }
                                page.pageFeedback=feedData;
                                
			    	tsDTable.push(page);
		    	
			    	actualPos++;	   
		    	}
		    });
                    
                    
                    
                    
                    var tfidfDTable = [];
			actualPos = 1;
                        divstr="tfidfSeqTable";
		    $('#'+divstr+'Body tr').each(function (index, value) {
		    	
		    	var page = {};

		    	var rowData =  $('#'+divstr).DataTable().row(index).data();
		    	
		    	if(rowData && typeof(rowData) !== "undefined"){
		    		page.pageId = rowData[0];
			    	page.buildPosition = rowData[1];
			    	page.title = rowData[2];
			    	page.userPosition = actualPos;
			    	page.ts_rank = rowData[3];
			    	page.tfidf_rank = rowData[4];
			    	page.lsi_rank = rowData[5];
			    	page.ig_rank = rowData[6];
			    	page.unescapedUrl = rowData[7];
			    	var sin=rowData[9];
                                if(sin===undefined){
                                    sin={};
                                }
			    	page.selectionInfo= sin;
		    	
                                var feedData =-1;
                                if(jQuery.inArray( rowData[1], pagesFeedback.tfidf )>=0){
                                    feedData =$('#'+divstr+' tbody').find('#'+rowData[1]+"-feed").val();   
                                    if(feedData===""){
                                        feedData=0;
                                    }
                                }
                                page.pageFeedback=feedData;
                        
                                
			    	tfidfDTable.push(page);
                                
			    	actualPos++;	   
		    	}
		    });
                    
                    
                    var lsiDTable = [];
			actualPos = 1;
                        divstr="lsiSeqTable";
		    $('#'+divstr+'Body tr').each(function (index, value) {
		    	
		    	var page = {};

		    	var rowData =  $('#'+divstr).DataTable().row(index).data();
		    	
		    	if(rowData && typeof(rowData) !== "undefined"){
		    		page.pageId = rowData[0];
			    	page.buildPosition = rowData[1];
			    	page.title = rowData[2];
			    	page.userPosition = actualPos;
			    	page.ts_rank = rowData[3];
			    	page.tfidf_rank = rowData[4];
			    	page.lsi_rank = rowData[5];
			    	page.ig_rank = rowData[6];
			    	page.unescapedUrl = rowData[7];
			    	var sin=rowData[9];
                                if(sin===undefined){
                                    sin={};
                                }
			    	page.selectionInfo= sin;
		    	
                                var feedData =-1;
                                if(jQuery.inArray( rowData[1], pagesFeedback.lsi )>=0){
                                    feedData =$('#'+divstr+' tbody').find('#'+rowData[1]+"-feed").val();   
                                    if(feedData===""){
                                        feedData=0;
                                    }
                                }
                                page.pageFeedback=feedData;
                                
                                
			    	lsiDTable.push(page);
			    	actualPos++;	   
		    	}
		    });

                    var igDTable = [];
			actualPos = 1;
                        divstr="igSeqTable";
		    $('#'+divstr+'Body tr').each(function (index, value) {
		    	
		    	//var page = [];
                        var page = {};

		    	var rowData =  $('#'+divstr).DataTable().row(index).data();
		    	
		    	if(rowData && typeof(rowData) !== "undefined"){
                            /*
		    		page.push(rowData[0]);
			    	page.push(rowData[1]);
			    	page.push(rowData[2]);
			    	page.push(actualPos);
			    	page.push(rowData[3]);
			    	page.push(rowData[4]);
			    	page.push(rowData[5]);
			    	page.push(rowData[6]);
			    	page.push(rowData[7]);
			    	page.push(rowData[9]);
                             */   
                                page.pageId = rowData[0];
			    	page.buildPosition = rowData[1];
			    	page.title = rowData[2];
			    	page.userPosition = actualPos;
			    	page.ts_rank = rowData[3];
			    	page.tfidf_rank = rowData[4];
			    	page.lsi_rank = rowData[5];
			    	page.ig_rank = rowData[6];
			    	page.unescapedUrl = rowData[7];
			    	var sin=rowData[9];
                                if(sin===undefined){
                                    sin={};
                                }
			    	page.selectionInfo= sin;
                                
                                
                                var feedData =-1;
                                if(jQuery.inArray( rowData[1], pagesFeedback.ig )>=0){
                                    feedData =$('#'+divstr+' tbody').find('#'+rowData[1]+"-feed").val();   
                                    if(feedData===""){
                                        feedData=0;
                                    }
                                }
                                //page.push(feedData);
                                page.pageFeedback=feedData;
		    	
                                
			    	igDTable.push(page);
			    	actualPos++;	   
		    	}
		    });
                    if(saved<0){
                        $.post("/course/topic/save", {courseId: courseId, courseName: courseName, topicId: topicId, topicName: topicName ,queryConstraints: queryConstraints, resultItems: JSON.stringify(allPages), graph: $('#graphVal').val(), lang: language}, function(r){
                            var res = jQuery.parseJSON(r);
                            if(res.success===true){
                                    //messageDialog('Operation done', 'Topic was correctly saved.');
                                    // loading saved data stored on topic detail page
                                    saved=true;
                                    courseId=res.data.courseId;
                                    topicDataId=res.data.id;
                                    courseDataId=res.data.courseId;
                                    statistica={type: "BuildTopicStat", stat: {general: generalDTable, ts: tsDTable, tfidf: tfidfDTable, lsi: lsiDTable, ig: igDTable ,userEmail: user, courseId: courseId, courseName: courseName, topicId: topicDataId, topicName: topicName ,queryConstraints: queryConstraints, lang: language, pages: allPages, rank: {general: Object.keys(generalScelti), ts_rank: Object.keys(tsScelti), tfidf_rank: Object.keys(tfScelti), lsi_rank: Object.keys(lsiScelti), ig_rank: Object.keys(igScelti)}}};

                                    dialog.dialog( "open" );


                            }
                        })
                        .fail(function(data) {
                            if(data.responseJSON.error !== null && data.responseJSON.message !== null){
                                    alert(data.responseJSON.message);
                            }
                        });
                        saved++;
                    }
	    });
		$(function() {
      $('.feed').barrating({
        theme: 'fontawesome-stars'
      });
      
      
      
   });
	</script>
        
        
    <div id="dialog-form" title="BuildTopic Feedback">
  <p class="validateTips">Operation done, Topic was correctly saved.<br><br>Please, leave a feedback</p><br>
 
  <form>
    <fieldset>
        <select class="feed" id="feed" name="feed">
          <option value=""></option>
          <option value="1">1</option>
          <option value="2">2</option>
          <option value="3">3</option>
          <option value="4">4</option>
          <option value="5">5</option>
        </select>
      <!-- Allow form submission with keyboard without duplicating the dialog button -->
      <input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
    </fieldset>
  </form>
</div>
</body>
</html>


