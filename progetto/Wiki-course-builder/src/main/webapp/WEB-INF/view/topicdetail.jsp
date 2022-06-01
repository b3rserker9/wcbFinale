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
	<link rel="stylesheet" type="text/css" href="/resources/css/custom2.css" />
	<link rel="stylesheet" type="text/css" href="/resources/css/loaderStyle.css" />
	<link rel="stylesheet" type="text/css" href="/resources/css/select2.min.css" />
	
	
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
					<li>
						<a href="mycourses.html">My Courses</a>
						<i class="icon-angle-right"></i>
					</li>
					<li>
						<a id="courseDetailLink" href="#">Course Detail</a>
						<i class="icon-angle-right"></i>
					</li>
					<li>
						<a href="#">Topic Detail</a>
					</li>
				</ul>
				
				
				<!-- Div dedicato al pannello di selezione del corso e del topic -->
				<!-- <div class="row-fluid sortable" id="chooseCourseContainer"> -->
				<div id="chooseCourseContainer">
					<div class="box span12">
						<div class="box-header" data-original-title>
							<h2><i class="halflings-icon edit"></i><span class="break"></span>Course topic</h2>
							<div class="box-icon">
								<!-- no icons -->
							</div>
						</div>
						<div class="box-content">
							<form class="form-horizontal">
							  <fieldset>
								  <div class="control-group">
									<label class="control-label" for="courseTitle">Course name</label>
									<div class="controls">
									  <input class="span6 typeahead" required="required" id="courseTitle" type="text" value="" disabled="true" />
									</div>
								  </div>
								  
								  <div class="control-group">
									<label class="control-label" for="topicTitle">Topic title</label>
									<div class="controls">
									  <input class="span6 typeahead" required="required" id="topicTitle" type="text" value="" oninvalid="setCustomValidity('Please, insert a valid topic')" onchange="try{setCustomValidity('')}catch(e){}">
									</div>
								  </div>
								  
								  <input id="topicId" type="hidden" value="">
								  <input id="courseId" type="hidden" value="">
							  </fieldset>
							</form>   
						</div>
					</div><!--/span-->
				</div><!--/row-->
				
				<!-- Div dedicato al rendering del grafo -->
				<div id="showGraphContainer" hidden="true">
					<div class="box span12">
						<div class="box-header" data-original-title>
							<h2><i class="halflings-icon info-sign"></i><span class="break"></span>click the wiki page node to add it to the topic</h2>
							<div class="box-icon">
								<a href="#" id="buttonHideGraph" class="btn-save"><i title="Hide Graph" class="halflings-icon remove"></i></a>
							</div>
							<input type="hidden" id="graphVal"/>
						</div>
						<div id="leftGraphInfoContainer" class="box-content" style="float: left; width:20%; height: 600px; display: inline-block; overflow: auto;">
						</div>
						<div id="innerShowGraphContainer" class="box-content" style="float: left; width:75%; height: 600px; display: inline-block;">
						</div>
					</div>
				</div>
				
				<!-- div nuovo per il grafo dei prerequisiti -->
					<div id="showGraphContainer2" hidden="true">
					<div class="box span12">
						<div class="box-header" data-original-title>
                                                    <h2><i class="halflings-icon info-sign"></i><span class="break"></span><font color="red"><strong>Suggerimenti per il sequencing</strong></font><font id="readySug" color="red"><strong> - NOT READY!!! Under processing...</strong></font></h2>
							<div class="box-icon">
								<a href="#" id="buttonHideGraph2" class="btn-save"><i title="Hide Graph" class="halflings-icon remove"></i></a>
							</div>
							<input type="hidden" id="graphVal"/>
						</div>
						
						<div id="innerShowGraphContainer2" class="box-content" style="float: left; width:75%; height: 600px; display: inline-block;">
						</div>
					</div>
				</div>



                                <a id="showMetricSel" href="#" onclick="showMetrics();" >Show Metrics Details</a>
                                <a id="hideMetricSel" href="#" onclick="hideMetrics();" style="display:none">Hide Metrics Details</a>
				<!-- scelta del corso -->
				<div id="wrapper">
					<form action="" method="post">
                                            
						<!-- <div class="row-fluid sortable" id="buildListContainer"> -->
						<div id="buildListContainer">
							<div class="box span12">
								<div class="box-header" data-original-title>
									<h2><i class="halflings-icon tasks"></i><span class="break"></span>Wiki Pages</h2>
									<div class="box-icon">
										<a href="#" id="buttonShowGraph" class="btn-save"><i title="Show Graph and add wiki page" class="halflings-icon info-sign"></i></a>
                                                                                <!--<a href="#" id="buttonShowGraph2" class="btn-save"><i title="Show Sequencing Graph" class="halflings-icon info-sign"></i></a>-->
										<a href="#" id="buttonRemoveSelected" class="btn-save"><i title="Remove selected articles" class="halflings-icon trash"></i></a>
                                                                                <a href="#" id = "saveTopicPdfButton" class="btn-save"><i title="Export File of topic" class="halflings-icon file"></i></a>
                                                                                <a href="#" id = "updateTopicTXT" class="btn-save"><i title="Export File of topic" class="halflings-icon refresh"></i></a>
										<a href="#" id = "saveTopicButton" class="btn-save"><i title="Save topic" class="halflings-icon hdd"></i></a>
									</div>
								</div>
								<div class="info">
									click on the title to select the related wiki page
								</div>
								<div class="box-content">
									<table id ="topicTable" class="table table-hover table-bordered bootstrap-datatable">
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
									  <tbody id="topicTableBody">
									  </tbody>
								  </table>     
								</div>
								<input id="salvatopic2" type="button" value="save topic">
							</div>
						
						</div><!--/row-->
					</form>
				</div>
			</div>
		</div>
	</div>
	<iframe id="my_iframe" style="display:none;"></iframe>
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
	<script src="/resources/js/graph/sigma.parsers.gexf.min.js"></script>
	<script src="/resources/js/graph/sigma.layout.forceAtlas2.min.js"></script>
	<script src="/resources/js/graph/sigma.layout.noverlap.js"></script>
	<script src="/resources/js/commons.js"></script>
	
        <script src="/resources/js/jquery-ui.js"></script>
        <script src="/resources/js/jquery.barrating.js"></script>
  <script type="text/javascript">
      var completeSequencing=false;
      var s2=undefined;
      var risultati;
      var pageLinkMode=2;
      var pageLinkReverse=0;
      var pageLinkDouble=0;
      var pageLinked=new Array();
      var pageLinkedRandom=new Array();
      var selezionati;
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
				selectedEdgeSize2 : 6,
				fadeColor : '#eee',
				fadeBorderColor : '#ccc'
			};
                        
      function showMetrics(){
          document.getElementById("showMetricSel").style.display="none";
           document.getElementById("hideMetricSel").style.display="";
           
           
        
           $('#topicTable').DataTable().column(3).visible(true);
           $('#topicTable').DataTable().column(4).visible(true);
           $('#topicTable').DataTable().column(5).visible(true);
           $('#topicTable').DataTable().column(6).visible(true);
           
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
           
           $('#topicTable').DataTable().column(3).visible(false);
           $('#topicTable').DataTable().column(4).visible(false);
           $('#topicTable').DataTable().column(5).visible(false);
           $('#topicTable').DataTable().column(6).visible(false);
        
      }
      function eventFire(el, etype){
        if (el.fireEvent) {
          el.fireEvent('on' + etype);
        } else {
          var evObj = document.createEvent('Events');
          evObj.initEvent(etype, true, false);
          el.dispatchEvent(evObj);
        }
    }
      function download(url) {
        document.getElementById('my_iframe').src = url;
    }
    function pageLinkAll(){
        completeSequencing=false;  
        document.getElementById("readySug").innerHTML="<strong> - NOT READY!!! Under processing...</strong>";
        //console.log("allPageLink"+pageLinked[key]);
        var lll= risultati.length;
        lll=lll*lll;
        var pgs= [];
        var obj={};
        var offset=29;
        for(var indx in risultati){
            
                    var t1=risultati[indx].title;
                    
                    //console.log("allPageLink"+t1);
                    
                    var url1=risultati[indx].unescapedUrl;
                    
                    var title1=url1.substring(offset);
                    
                    title1=title1.replace("/","");
                    
                    /*
                        
                        if(obj[t1]===undefined){
                            obj[t1]=1;
                            pgs.push(title1);
                        }
            
            */
            pgs.push(title1);           
        }
        
       // var ritorno={};
         $.getJSON('/plugin/pageLinkAll', {titles: JSON.stringify(pgs), lang:language}, function(r){
         
                 pageLinkAllFinish(r);
                //}})
                })
                .fail(function(data) {
                    //return false;
            console.log("error");
                });
                
                
                    
                
                
                
                
                
       
    }
    function pageLinkAllFinish(ritorno){
        if(ritorno!==undefined){
                        var result=ritorno.results;
                        for(var i in result){

                            var retu=false;
                            if(result[i]!==undefined){
                                var key=result[i].title1+"___"+result[i].title2;
                             //console.log(key);
                                var ris = result[i].result;
                                if(ris===0||ris===1){
                                    retu= true;
                                    pageLinked[key]=retu;
                                }
                                else if(ris===2){
                                    retu= false;
                                    pageLinked[key]=retu;
                                }

                                else{
                                    retu=lanciaDado();
                                    pageLinkedRandom[key]=retu;
                                }

                            }
                            else{
                                console.log("pageLinkAll NO result");
                                retu=lanciaDado();
                                pageLinkedRandom[key]=retu;
                            }
                            //console.log(key+"              "+ JSON.stringify(pageLinked[key])+"     "+JSON.stringify(pageLinkedRandom[key]));
                    }
                    /*
                    else{
                        console.log("pageLinkAll NO result");
                        retu=lanciaDado();
                        pageLinkedRandom[key]=retu;
                    }
            */
           //console.log(key+"       "+pageLinked[key]);
           //return true;
           
                }
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
          completeSequencing=true;      
        if(s2!==undefined){
            
            document.getElementById("readySug").innerHTML="";
            s2.refresh();
            drawGraph2();
        }
        return true;
    }
    function allPageLink(){
        completeSequencing=false;  
        document.getElementById("readySug").innerHTML="<strong> - NOT READY!!! Under processing...</strong>";
        //console.log("allPageLink"+pageLinked[key]);
        var lll= risultati.length;
        lll=lll*(lll);
        var cont=0;
        for(var indx in risultati){
            for(var indy in risultati){
                if(indx!==indy){
                    var t1=risultati[indx].title;
                    var t2=risultati[indy].title;
                    //console.log("allPageLink"+t1);
                    var key=t1+"___"+t2;
                    if(pageLinked[key]===undefined){
                        
                        //pageLinked[key]=isPageLink(resit[indx].unescapedUrl, resit[indy].unescapedUrl);
                        isPageLink(risultati[indx].unescapedUrl, risultati[indy].unescapedUrl);
                        
                    }
                    
                }
                lll--;
                
            }
        }
        completeSequencing=true;
        if(s2!==undefined){
            document.getElementById("readySug").innerHTML="";
            s2.refresh();
            drawGraph2();
        }
        
       
    }
      function lanciaDado(){
                var dado=Math.random();
                    if(dado > 0.8){
                        return true;
                    }
                    else{
                        return false;
                    }
            }
            
            function isPageLink(url1, url2){
                var lll= risultati.length;
                lll=lll*(lll-1);
                //example url => http://en.wikipedia.org/wiki/Java_(programming_language)
                var offset=29;
                var title1=url1.substring(offset);
                var title2=url2.substring(offset);
                title1=title1.replace("/","");
                title2=title2.replace("/","");
                $.getJSON('/plugin/pageLink', {title1: title1, title2: title2, lang:language}, function(r){
             /*  $.ajax({
  dataType: "json",
  url: '/plugin/pageLink',
  data: {title1: title1, title2: title2, lang:language},
  async: true, 
  success: function(r) {*/
                    //var results = r;
                    //console.log("pageLink r returned => "+JSON.stringify(r));
                    var key=title1+"___"+title2;
                     //console.log(key);
                    var retu=false;
                    if(r!==undefined){
                        var ris = r.result;
                        if(ris===0||ris===1){
                            retu= true;
                            pageLinked[key]=retu;
                        }
                        else if(ris===2){
                            retu= false;
                            pageLinked[key]=retu;
                        }
                        
                        else{
                            retu=lanciaDado();
                            pageLinkedRandom[key]=retu;
                        }
            
                    }
                    else{
                        console.log("pageLink NO result");
                        retu=lanciaDado();
                        pageLinkedRandom[key]=retu;
                    }
                     
                    //console.log(JSON.stringify(pageLinked[key])+"     "+JSON.stringify(pageLinkedRandom[key]));
                    return retu;

                //}})
                })
                .fail(function(data) {
                    return lanciaDado();
                });
            }
      
      
      
      var user = "${principal.email}";
      var topicId = '${param.topicId}';
      var courseId = '${param.courseId}';
      
            var language="";
			var tsScelti={};
			var tfScelti={};
			var lsiScelti={};
			var igScelti={};
  var feedback=-1;
  var statistica;
  var topicDataId;
  var dialog, form,      
      allFields = $( [] ).add( feedback ),
      tips = $( ".validateTips" );
      
      
      var dialogFile, formFile, typefile,     
      allFieldsFile = $( [] ).add( typefile ),
      tipsFile = $( ".validateTipsFile" );
 
       
       
    function updateTopicTXT(){
        if (confirm("Are you sure you want to update all wikipedia pages of topic?")){
        $.post("/course/topic/pages/update", {topicId: topicId}, function(r){
                                            var res = jQuery.parseJSON(r);
                                            if(res.success===true){
                                                
                                                  messageDialog('Operation Done', 'Wikipedia Pages of Topic is correcly update!');


                                            }
                                        })
                                        .fail(function(data) {
                                            if(data.responseJSON.error !== null && data.responseJSON.message !== null){
                                                    alert(data.responseJSON.message);
                                            }
                                        });
                                    }
    }   
    
    
    function createPdf(){
        $.post("/course/topic/export/pdf", {topicId: topicId}, function(r){
                                            var res = jQuery.parseJSON(r);
                                            if(res.success===true){
                                                var pathPdf=res.pathFile;
                                                //window.open(pathPdf, "_blank");
                                                download(pathPdf);    


                                            }
                                        })
                                        .fail(function(data) {
                                            if(data.responseJSON.error !== null && data.responseJSON.message !== null){
                                                    alert(data.responseJSON.message);
                                            }
                                        });
    }
    function createTxt(type){
        $.post("/course/topic/export/txt", {topicId: topicId, clean: type}, function(r){
                                            var res = jQuery.parseJSON(r);
                                            if(res.success===true){
                                                var pathTxt=res.pathFile;
                                                //window.open(pathPdf, "_blank");
                                                download(pathTxt);    


                                            }
                                        })
                                        .fail(function(data) {
                                            if(data.responseJSON.error !== null && data.responseJSON.message !== null){
                                                    alert(data.responseJSON.message);
                                            }
                                        });
    }
      
      
 function saveStat(){
     
   
      $.post("/stats/save", {stat: JSON.stringify(statistica)}, function(r){
                                    console.log("save stat ");
                                })
                                .fail(function(data) {

                                });
                                 
                                
                                
		    		window.open("/topicdetail.html?courseId="+courseId+"&topicId="+topicDataId, "_self");
                            }
  $( function() {
    
    
    
    
    
    
    
    
  
		
                    
                    
                    
                    
                    
                    
                    function updateTipsFile( t ) {
      tipsFile
        .text( t )
        .addClass( "ui-state-highlight" );
      setTimeout(function() {
        tipsFile.removeClass( "ui-state-highlight", 1500 );
      }, 500 );
    }
 
 	
    function getDoc() {
      var valid = true;
      allFieldsFile.removeClass( "ui-state-error" );
 		typefile=$( "#typefile" ).val();
                dialogFile.dialog( "close" );
                getDocAux();
                    
                
      return valid;
    }
    function getDocAux() {
                
                if(typefile==="pdf"){
                    createPdf();
                }
                else if(typefile==="clean txt"){
                    createTxt("0");
                }
                else if(typefile==="raw txt"){
                    createTxt("1");
                }
        
    }
    function getDocument(t) {
      var valid = true;
      allFieldsFile.removeClass( "ui-state-error" );
 		//t=$( "#typefile" ).val();
                if(t==="pdf"){
                    createPdf();
                }
                else if(t==="clean txt"){
                    createTxt("0");
                }
                else if(t==="raw txt"){
                    createTxt("1");
                }
                    
                
      return valid;
    }
    
    function getDoc1() {
      var valid = true;
      allFieldsFile.removeClass( "ui-state-error" );
 		
                    createPdf();
                                   
                
      return valid;
    }
    function getDoc2() {
      var valid = true;
      allFieldsFile.removeClass( "ui-state-error" );
 		
                    createTxt("0");
                                   
                
      return valid;
    }
    function getDoc3() {
      var valid = true;
      allFieldsFile.removeClass( "ui-state-error" );
 		
                    createTxt("1");
                                   
                
      return valid;
    }
    /*
    dialog = $( "#dialog-form" ).dialog({
      autoOpen: false,
      height: 400,
      width: 350,
      modal: true,
      buttons: {
        "PDF": getDoc1,
        "Clean TXT": getDoc2,
        "Raw TXT": getDoc3,
        Cancel: function() {
          dialog.dialog( "close" );
        }
      },
      close: function() {
        form[ 0 ].reset();
        allFields.removeClass( "ui-state-error" );
      }
    });
        */
    
    dialogFile = $( "#dialogFile-form" ).dialog({
      autoOpen: false,
      height: 250,
      width: 300,
      modal: true,
      buttons: {
        "Confirm": getDoc,
        Cancel: function() {
          dialogFile.dialog( "close" );
        }
      },
      close: function() {
        formFile[ 0 ].reset();
        allFieldsFile.removeClass( "ui-state-error" );
      }
    });
 
    formFile = dialogFile.find( "form" ).on( "submit", function( event ) {
      event.preventDefault();
      getDoc();
    });
    
    
    
    
    
    
    
    
    
    
    
 
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
            
            
            
            
            
            
            
            
            
            

            

		
			
			$loaderOptions = {
	            autoCheck: 32,
	            size: '32',
	            bgColor: '#FFF',
	            bgOpacity: 0.5,
	            fontColor: '#000', 
	            title: 'Loading topic detail. Please wait...',
	            isOnly: true,
	            imgUrl: '/resources/img/loading[size].gif'
	        };
			
			
			$.loader.open($loaderOptions);
			
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
			
			$('#topicTable tbody').on( 'click', '#buttonView', function () {
				var rowData =  dataTable.DataTable().row($(this).parents('tr')).data();
		    	window.open(rowData[7]);
		    } );
			
			$('#topicTable tbody').on( 'click', '#buttonRemove', function () {
				var title =  dataTable.DataTable().row($(this).parents('tr')).data()[2];
				if (confirm("Are you sure you want to remove page \""+title+"\" ?")){
					dataTable.DataTable().row( $(this).parents('tr') ).remove().draw( false );
                                        var risultados=[];
                                        for(var i in risultati){
                                            var ri=risultati[i];
                                            if(ri.title!==title){
                                                
                                                risultados.push(ri);
                                            }
                                           
                                        }
                                        risultati=risultados;
                                        eventFire(document.getElementById("buttonHideGraph"), 'click');
                                        eventFire(document.getElementById("buttonHideGraph2"), 'click'); 
                                        //drawGraph2();
                                        
				}
		    } );
			
			$('#topicTable tbody').on('click', '#buttonUp', function(e){
			    var index = dataTable.DataTable().row($(this).parents('tr')).index();
			    if ((index-1) >= 0) {
			        var data = dataTable.fnGetData();
			        dataTable.fnClearTable();
			        data.splice((index-1), 0, data.splice(index,1)[0]);
			        dataTable.fnAddData(data);
			    }
			});
			$('#topicTable tbody').on('click', '#buttonDown', function(e){
				var index = dataTable.DataTable().row($(this).parents('tr')).index();
			    if ((index+1) >= 0) {
			        var data = dataTable.fnGetData();
			        dataTable.fnClearTable();
			        data.splice((index+1), 0, data.splice(index,1)[0]);
			        dataTable.fnAddData(data);
			    }
			});
			
			function initDataTable() {
				dataTable = $('#topicTable').dataTable(tableOptions);
			}
			
			initDataTable();
			
			function loadTableData(r){
				var results = r.items;

				if(results.length){
					
					dataTable.fnDestroy();
					initDataTable();
					dataTable.DataTable().clear();
					for(var i=0;i<results.length;i++){
						dataTable.DataTable().row.add(new addItemToTable(results[i]));
					}
					dataTable.DataTable().draw();
				}
			}
			
			$.getJSON("/course/topic/detail", {topicId: topicId}, function(r){
                            //console.log(JSON.stringify(r.topic.resultItems));
                            if(r.plm){
                                pageLinkMode=r.plm;
                            }
                            if(r.plr){
                                pageLinkReverse=r.plr;
                            }
                            if(r.pld){
                                pageLinkDouble=r.pld;
                            }
				if(r.topic){
                                    risultati=r.topic.resultItems;
					loadTableData(r.topic);
					if(r.topic.id){
						$('#topicId').val(r.topic.id);
                                            }
					if(r.topic.title){
						$('#topicTitle').val(r.topic.title);
                                            }
					if(r.topic.gexf){
						$('#graphVal').val(r.topic.gexf);
                                            }
                                        if(r.topic.lang){
                                                language=r.topic.lang;
                                            }
                                           
                                        if(pageLinkMode<2){
                                                    allPageLink();
                                                }
                                        else if(pageLinkMode>=2){
                                                    pageLinkAll();
                                                }
				}
				if(r.course && r.course.id && r.course.title){
					
					$('#courseId').val(r.course.id);
					$('#courseTitle').val(r.course.title);
					
					$('#courseDetailLink').click(function() {
						window.open("/coursedetail.html?courseId="+r.course.id+"&courseTitle="+r.course.title, "_self");
					});
				}
				
			    $.loader.close();
			})
			.fail(function(data) {
				$.loader.close();
		    	if(data.responseJSON.error !== null && data.responseJSON.message !== null){
		    		alert(data.responseJSON.message);
		    	}
		    });
			
			function addItemToTable(item){
				var arr = [];

				
                                arr.push(item.title);//arr.push(item.pageId);
				arr.push(item.buildPosition);
				arr.push(item.title);
				arr.push(item.ts_rank);
				arr.push(item.tfidf_rank);
				arr.push(item.lsi_rank);
				arr.push(item.ig_rank);
				arr.push(item.unescapedUrl);
				//arr.push({});
                                arr.push(item.selectionInfo);
				
				return arr;
			}
		});
		
		function getAddedNodes(){
			var result = {};
			$('#topicTableBody tr').each(function (index, value) {
		    	
		    	var rowData =  $('#topicTable').DataTable().row(index).data();
		    	if(rowData && typeof(rowData) !== "undefined"){
		    		result[rowData[2]]=true;
		    	}
		    });
			return result;
		}
		function drawGraph2(){
                    document.getElementById('innerShowGraphContainer2').innerHTML="";
                    	s2 = new sigma({
										  renderers: [
										    {
										      container: document.getElementById('innerShowGraphContainer2'),
										      type: 'canvas' // sigma.renderers.canvas works as well
										    }
										  ],
										     			  settings: {
										     				defaultNodeType: 'border',
										     				minEdgeSize: gConfigs.normalEdgeSize,
										         			maxEdgeSize: gConfigs.selectedEdgeSize,
										         			minArrowSize: gConfigs.selectedEdgeSize+3
										     			  }
										});
                                                                           

					//prova sug. sequencing

						var couple = new Array();
		       var k=0;
		       var archi2=new Array();
		       var edgeid=0;
		       selezionati.forEach(function(c) {
		       		var j = c;
		       		s2.graph.addNode(j);
                                //console.log(JSON.stringify(j.attributes));
		       		selezionati.forEach(function(m) {
                                    //console.log("PRINT node.Id: "+JSON.stringify(m));
		       			if(j.id !== m.id){
		       				//var dado=Math.random();
		       				//if(dado > 0.8){
                                                var offset=29;
                                                var title1=j.attributes.unescapedUrl.substring(offset);
                                                var title2=m.attributes.unescapedUrl.substring(offset);
                                                title1=title1.replace("/","");
                                                title2=title2.replace("/","");
                                                
                                                var key=title1+"___"+title2;
                                                var keyInv=title2+"___"+title1;
                                                if(pageLinkReverse>0){
                                                    keyInv=title1+"___"+title2;
                                                    key=title2+"___"+title1;
                                                }
                                                
                                                var control=false;//lanciaDado();
                                                if(pageLinked[key]!==undefined){
                                                    if(pageLinkDouble>0){
                                                        control=pageLinked[key];                                           
                                                        
                                                    }
                                                    else{//elimina doppie frecce
                                                        if(pageLinked[key]!==pageLinked[keyInv]){
                                                            control=pageLinked[key];                                                            
                                                        }
                                                        else{
                                                            control=false;
                                                        }
                                                    }
                                                
                                                }
                                                else{
                                                    if(pageLinkedRandom[key]!==undefined){
                                                        if(pageLinkDouble>0){//accetta le doppie frecce
                                                            control=pageLinkedRandom[key];
                                                        }
                                                        else{//elimina doppie frecce
                                                            if(pageLinkedRandom[key]!==pageLinkedRandom[keyInv]){
                                                                    control=pageLinkedRandom[key];
                                                            }
                                                            else{
                                                                control=false;
                                                            }
                                                        }
                                                    }
                                                }
                                               //console.log("-------------->"+ key+"               "+pageLinked[key]+"   "+pageLinkedRandom[key]);
                                                if(control){//true crea arco
                                                //if(isPageLink(j.attributes.unescapedUrl,m.attributes.unescapedUrl)){
		       					var  edge= 
    							{	
    								
     								 id: 'xxx'+edgeid,
      								source: j.id,
      								target: m.id
   								 };
   				edge.color='rgb(100,0,0)';
   				edge.originalColor='rgb(100,0,0)';
			    		  edge.type="arrow";
			    		  edge.size=gConfigs.selectedEdgeSize2;
			    		  edge.tipo="nuovo";
			    		  edgeid++;
		       					archi2.push(edge);
		       				}

		       			}
		       		});
		       });
		       //console.log(JSON.stringify(archi2));
		       
		       /*
		       var edge;
		        var edgeid=0;
		        var c;
		       for(var t=0;t<couple.length;t++){
		       	
		        c=Math.random();
		        
		       	if(c>0.99 && edgeid < 60){
		       		var  edge= 
    							{	
    								
     								 id: 'xyz'+edgeid,
      								source: couple[t][0].id,
      								target: couple[t][1].id
   								 };
   				edge.color='rgb(100,0,0)';
   				edge.originalColor='rgb(100,0,0)';
			    		  edge.type="arrow";
			    		  edge.size=gConfigs.selectedEdgeSize2-3;
			    		  edge.tipo="nuovo";
			    		  //s2.graph.addEdge(edge);
			    		  edgeid++;
		       	}
		       	
		       }
				*/

				archi2.forEach(function(e) {
					var d=e;
					d.size="600";
		       		s2.graph.addEdge(d);
					});

					//fine prova
			        
					s2.startForceAtlas2();


			        s2.refresh();
			        setTimeout(function() { s2.stopForceAtlas2(); }, 3000);
                }
		function drawGraph() {
			//sigma graph
			var sg;
			//graphic configuration
			
			
			var gexfXml = $.parseXML( $("#graphVal").val() );
			
			if(!gexfXml || gexfXml==='')
				return;
			$("#innerShowGraphContainer2").empty();
			$("#innerShowGraphContainer").empty();
			$("#leftGraphInfoContainer").empty();
			$("#showGraphContainer").show();
			$("#showGraphContainer2").show();
			
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
				
				leftContainer.append( "<p style=\"font-size: 12px; margin-top: 0em; margin-bottom: 0em;\"><b>Scores:</b></p>" )
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
						arr.push(node.label);//node.attributes.pageId);
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
						
						$('#topicTable').DataTable().row.add(arr);
						$('#topicTable').DataTable().draw();
						
						node.color=gConfigs.selectedByUserColor;
						node.borderColor=gConfigs.selectedByUserBorderColor;
						node.size=gConfigs.selectedNodeSize;
						node.borderWidth=gConfigs.selectedNodeBorderSize;
						node.originalColor = node.color;
						node.originalBorderColor = node.borderColor;
                                                var nodeR={title:node.label,unescapedUrl:node.attributes.unescapedUrl};
                                                risultati.push(nodeR);
                                                eventFire(document.getElementById("buttonHideGraph2"), 'click'); 
                                                if(pageLinkMode<2){
                                                    allPageLink();
                                                }
                                                else if(pageLinkMode>=2){
                                                    pageLinkAll();
                                                }
                                                
						for(var j=0;j<addedByUser.length-2;j++){
		       		 	alert(addedByUser.length);
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
		        
/*
var selezionati=new Array();
			      		var cont=0;
			      		var edge;
		        var edgeid=0;
		       sg.graph.nodes().forEach(function(n) {
			      	
		        var array=["GNU Compiler for Java","x86","General-purpose language","Object-oriented programming","instance (computer science)","SPARC","Lisp (programming language)"];
			    			      	  if(array.indexOf(n.label) != -1 ){
			      	  	
			      	  	selezionati[cont]=n;
			      	  	cont++;
			      	  	n.originalColor='rgb(100,0,0)';
			      	  	n.color='rgb(100,0,0)';
			      	  	n.size=10;
			      	  	n.tipo="nuovo";
			      	  }
			      	  	
			      	  });
		       console.log(selezionati);
		       
		       for(var i=0;i<selezionati.length-2;i++){
		       		var  edge= 
    							{	
    								
     								 id: 'xxx'+edgeid,
      								source: selezionati[i].id,
      								target: selezionati[i+1].id
   								 };
   				edge.color='rgb(100,0,0)';
   				edge.originalColor='rgb(100,0,0)';
			    		  edge.type="arrow";
			    		  edge.size=gConfigs.selectedEdgeSize2;
			    		  sg.graph.addEdge(edge);
			    		  edgeid++;
		       }
				*/

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
				  sg = s;



			      // We first need to save the original colors of our
			      // nodes and edges, like this:
			      var selectedNodes = {};	  
			      var addedByUser = getAddedNodes();

			      //modifica grafo dei selezionati


			      var array=new Array();
			      var ind=0;


			      $("#topicTableBody > tr").each(function(){

			      		array[ind]=this.firstChild.innerText;
			      		ind++;
			      		
			      });
			      var archi=new Array();
			      selezionati=new Array();			      
			      var selezionati2=new Array();
			      		var cont=0;
			      		var edge;
		        var edgeid=0;
		       sg.graph.nodes().forEach(function(n) {
			      	
		        
			    			      	  if(array.indexOf(n.label) !== -1 ){
			      	  	
			      	  	selezionati[cont]=n;

			      	  	cont++;
			      	  	n.originalColor='rgb(100,0,0)';
			      	  	n.color='rgb(100,0,0)';
			      	  	n.size=10;
			      	  	n.tipo="nuovo";
			      	  }
			      	  	
			      	  });
		       


		       for(var i=0;i<array.length-1;i++){
		       		var id1,id2;
		       		sg.graph.nodes().forEach(function(n) {
		       			if(n.label===array[i])
		       				id1=n.id;
		       			if(n.label===array[i+1])
		       				id2=n.id;
		       		});
		       		var  edge= 
    							{	
    								
     								 id: 'xxx'+edgeid,
      								source: id1,
      								target: id2
   								 };
   				edge.color='rgb(100,0,0)';
   				edge.originalColor='rgb(100,0,0)';
			    		  edge.type="arrow";
			    		  edge.size=gConfigs.selectedEdgeSize2;
			    		  edge.tipo="nuovo";
			    		  sg.graph.addEdge(edge);
			    		  archi.push(edge);
			    		  edgeid++;
		       }

		       //fine modifica

		       //modifica prova per il futuro
		       	/*

		       var couple = new Array();
		       var k=0;
		       s.graph.nodes().forEach(function(n) {
		       		
		       		s.graph.nodes().forEach(function(m) {
		       			if(n.id != m.id){
		       				couple[k]=[n,m];
		       				k++
		       			}
		       		});
		       });
		       
		       s.graph.edges().forEach(function(e) {
		       		s.graph.dropEdge(e.id);
		       });


		       var edge;
		        var edgeid=0;
		        var c;
		       for(var t=0;t<couple.length;t++){
		       	
		        c=Math.random();
		        
		       	if(c>0.99 && edgeid < 60){
		       		var  edge= 
    							{	
    								
     								 id: 'xyz'+edgeid,
      								source: couple[t][0].id,
      								target: couple[t][1].id
   								 };
   				edge.color='rgb(100,0,0)';
   				edge.originalColor='rgb(100,0,0)';
			    		  edge.type="arrow";
			    		  edge.size=gConfigs.selectedEdgeSize2-3;
			    		  edge.tipo="nuovo";
			    		  sg.graph.addEdge(edge);
			    		  edgeid++;
		       	}
		       	console.log(edgeid);
		       }

		       	*/
		       	//modifica prova per il futuro


			    //var arrayCammino=new array(5);
			      s.graph.nodes().forEach(function(n) {

			      		
			        if(n.label in addedByUser){
			        	n.color=gConfigs.selectedByUserColor;
			        	n.borderColor=gConfigs.selectedByUserBorderColor;
			        	n.size=gConfigs.selectedNodeSize;
			        	n.borderWidth=gConfigs.selectedNodeBorderSize;
			        	selectedNodes[''+n.id]=true;
			        	
			        	//arrayCammino[array.indexOf(n.label)]=n;
			        }
			        
			        else{
			        	n.color=gConfigs.normalColor;
			        	n.borderColor=gConfigs.normalBorderColor;
			        	n.size=gConfigs.normalNodeSize;
			        	n.borderWidth=gConfigs.normalNodeBorderSize;
			        }

			        if(n.tipo==="nuovo"){
			        	n.color='rgb(100,0,0)';
			        	n.borderColor='rgb(100,0,0)';
			        	n.size=gConfigs.selectedNodeSize;
			        	n.borderWidth='rgb(100,0,0)';
			        }

			        n.originalColor = n.color;
			        n.originalBorderColor = n.borderColor;
			      });
			    
			      s.graph.edges().forEach(function(e) {
			    	  if((e.source in selectedNodes) && (e.target in selectedNodes)){
			    		  e.color=gConfigs.selectedByUserColor;
			    		  e.size=gConfigs.selectedEdgeSize;
			    	  }
			    	  else{
			    		  e.color=gConfigs.normalColor;
			    		  e.size=gConfigs.normalEdgeSize;
			    	  }
			    	  if(e.tipo==="nuovo"){
			        	e.color='rgb(100,0,0)';
			    		  e.size=6;
			        	
			        }
			        e.originalColor = e.color;
			      });
					
				
                                
			        
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
		
		$("#buttonShowGraph").click(function() {
			drawGraph();
                        drawGraph2();
                        //console.log(completeSequencing);
                        if(completeSequencing){
                            
                            document.getElementById("readySug").innerHTML="";
                            
                        }
                        /*
                        else{
                            document.getElementById("readySug").innerHTML="<strong> - NOT READY!!! Under processing...</strong>";
                        }*/
		});
                /*
                $("#buttonShowGraph2").click(function() {
			//drawGraph();
                        drawGraph2();
		});
		*/
		$("#buttonHideGraph").click(function() {
			$("#showGraphContainer").hide();
		});
		$("#buttonHideGraph2").click(function() {
			$("#showGraphContainer2").hide();
			$("#innerShowGraphContainer2").html("");

		});
       			
		$('#topicTableBody').on( 'click', 'tr', function () {
	        $(this).toggleClass('selected');
	    } );
		
		$("#buttonRemoveSelected").click(function() {
			
			//retrieving all selected rows
			var rowIndexes = $('#topicTable').DataTable().rows('.selected')[0];
			console.log(rowIndexes);
                        console.log(JSON.stringify(rowIndexes));
			if(rowIndexes.length>0){
				if (confirm("Are you sure you want to remove selected pages ?")){
					$('#topicTable').DataTable().rows(rowIndexes).remove().draw( false );
                                        for(var index in rowIndexes){
                                            
                                            
                                            var rowData =  $('#topicTable').DataTable().row(index).data();
                                    if(rowData && typeof(rowData) !== "undefined"){
                                            
                                            var title=rowData[2];
                                            var risultados=[];
                                            for(var i in risultati){
                                                var ri=risultati[i];
                                                if(ri.title!==title){

                                                    risultados.push(ri);
                                                }

                                            }
                                        }
                                        
                                        risultati=risultados;
                                    }
                                        eventFire(document.getElementById("buttonHideGraph"), 'click');
                                        eventFire(document.getElementById("buttonHideGraph2"), 'click'); 
                                        
                                }
			}
			else{
				messageDialog('Warning', 'There aren\'t selected rows to remove');
			}
		});
		$("#saveTopicPdfButton").click(function() {
                    dialogFile.dialog( "open" );
                    /*
                    $.post("/course/topic/export/pdf", {topicId: topicId}, function(r){
                                            var res = jQuery.parseJSON(r);
                                            if(res.success===true){
                                                var pathPdf=res.pathFile;
                                                //window.open(pathPdf, "_blank");
                                                download(pathPdf);    


                                            }
                                        })
                                        .fail(function(data) {
                                            if(data.responseJSON.error !== null && data.responseJSON.message !== null){
                                                    alert(data.responseJSON.message);
                                            }
                                        });
                    
                    */
                });
                $("#updateTopicTXT").click(function() {
                    updateTopicTXT();
                });
		$("#saveTopicButton").click(function() {
			//retrieving course title
			
			//var courseId = -1;
			var courseName = '';
			//var topicId = -1;
			var topicName = '';
			
			var startingPage = '';
			var queryConstraints = '';
			
			topicId = $('#topicId').val();
			topicName = $('#topicTitle').val().trim();
			
			if(topicName ===''){
				messageDialog('Warning', 'Define a valid topic title.');
				return;
			}
			
			
			
			
			//retrieving all pages
			var allPages = [];
			var actualPos = 1;

		    $('#topicTableBody tr').each(function (index, value) {
		    	
		    	var page = {};

		    	var rowData =  $('#topicTable').DataTable().row(index).data();
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
			    	page.selectionInfo = rowData[8];
			    	allPages.push(page);
		    	
			    	actualPos++;		
		    	}
				
		    });
		    var user = "${principal.email}";
                    //console.log(user);
			
                    
			
			
			
		    $.post("/course/topic/save", {courseId: courseId, courseName: courseName, topicId: topicId, topicName: topicName ,queryConstraints: queryConstraints, resultItems: JSON.stringify(allPages), graph: $('#graphVal').val(), lang: language}, function(r){
		    	var res = jQuery.parseJSON(r);
		    	if(res.success===true){
		    		statistica={type: "TopicDetailStat", stat: {userEmail: user, courseId: courseId, courseName: courseName, topicId: res.data.id, topicName: topicName, queryConstraints: queryConstraints, lang: language, pages: allPages }};
                                
                                topicDataId=res.data.id;
                                dialog.dialog( "open" );
                                /*
                                messageDialog('Operation done', 'Topic was correctly saved.');
                                
                                $.post("/stats/save", {stat: JSON.stringify(statistica)}, function(r){
                                    console.log("save stat ");
                                })
                                .fail(function(data) {

                                });
                                */
		    	}
		    })
		    .fail(function(data) {
		    	if(data.responseJSON.error !== null && data.responseJSON.message !== null){
		    		alert(data.responseJSON.message);
		    	}
		    });

		    //drawGraph();
                    //drawGraph2();
	    });
		
		$("#salvatopic2").click(function() {
			//retrieving course title
			
			//var courseId = -1;
			var courseName = '';
			//var topicId = -1;
			var topicName = '';
			
			var startingPage = '';
			var queryConstraints = '';
			
			topicId = $('#topicId').val();
			topicName = $('#topicTitle').val().trim();
			
			if(topicName ===''){
				messageDialog('Warning', 'Define a valid topic title.');
				return;
			}
			
			
			
			
			//retrieving all pages
			var allPages = [];
			var actualPos = 1;

		    $('#topicTableBody tr').each(function (index, value) {
		    	
		    	var page = {};

		    	var rowData =  $('#topicTable').DataTable().row(index).data();
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
			    	page.selectionInfo = rowData[8];
			    	allPages.push(page);
		    	
			    	actualPos++;		
		    	}
				
		    });
		    
			
			
			
			
			
		    $.post("/course/topic/save", {courseId: courseId, courseName: courseName, topicId: topicId, topicName: topicName ,queryConstraints: queryConstraints, resultItems: JSON.stringify(allPages), graph: $('#graphVal').val(), lang: language}, function(r){
		    	var res = jQuery.parseJSON(r);
		    	if(res.success===true){
                                
		    		statistica={type: "TopicDetailStat", stat: {userEmail: user, courseId: courseId, courseName: courseName, topicId: res.data.id, topicName: topicName, queryConstraints: queryConstraints, lang: language, pages: allPages }};
                                
                                topicDataId=res.data.id;
                                dialog.dialog( "open" );
                                /*
                                messageDialog('Operation done', 'Topic was correctly saved.');
                                
                                $.post("/stats/save", {stat: JSON.stringify(statistica)}, function(r){
                                    console.log("save stat ");
                                })
                                .fail(function(data) {

                                });
                                */
		    	}
		    })
		    .fail(function(data) {
		    	if(data.responseJSON.error !== null && data.responseJSON.message !== null){
		    		alert(data.responseJSON.message);
		    	}
		    });

		    //drawGraph();
                    //drawGraph2();
	    });
		
		$("#buttonRemoveSelected").append("Remove selected wikiPage ");
		$("#saveTopicButton").append("Save Topic");
                $("#saveTopicPdfButton").append("Export File of Topic");
                $("#updateTopicTXT").append("Update wikiPages of Topic");
		$("#buttonShowGraph").append("Show graphs & Add wikiPages");
                //$("#buttonShowGraph2").append("sequencing graph");
		
		
			$(".span6:even").append("click on the title to select the related wiki page");
		
		
		$(function() {
      $('#feed').barrating({
        theme: 'fontawesome-stars'
      });
   });
	</script>
        
        
    <div id="dialog-form" title="TopicDetail Feedback">
  <p class="validateTips">Operation done, Topic was correctly saved.<br><br>Please, leave a feedback</p><br>
 
  <form>
    <fieldset>
      <select id="feed" name="feed">
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
        
  
        
        <div id="dialogFile-form" title="Selection file type">
  <p class="validateTipsFile">Select file type to export.<br></p><br>
 
  <form>
    <fieldset>
        <select class="typefile" id="typefile" name="typefile">
          <option value="pdf">pdf</option>
          <option value="clean txt">clean txt</option>
          <option value="raw txt">raw txt</option>
        </select>
      <!-- Allow form submission with keyboard without duplicating the dialog button -->
      <input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
    </fieldset>
  </form>
</div>
        
        
</body>
</html>
