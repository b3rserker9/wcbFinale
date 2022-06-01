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
					<li>
						<a href="mycourses.html">My Courses</a>
						<i class="icon-angle-right"></i>
					</li>
					<li>
						<a href="#">Course Detail</a>
					</li>
				</ul>
				
				
				<!-- aggiunta dinamica dei topic -->
				<div id="topicListContainer">
					<!--<h1 style="text-align:left">Detail of course: <b><i></i></b></h1>-->
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
	<iframe id="my_iframe" style="display:none;"></iframe>
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
                <script src="/resources/js/jquery-loader.js"></script>
		<script src="/resources/js/counter.js"></script>
		<script src="/resources/js/custom.js"></script>
		<script src="/resources/js/jquery.tablesorter.js"></script>
		
		<script src="/resources/js/jquery-ui.js"></script>
		<script type="text/javascript">
                    var topicOrCourse="topic";
                    var topics;
                    var idTopic;
                    //var metricShow=false;
                var results;
                var container;
                //var tableName="";
                var courseTitle = '${param.courseTitle}';
                var courseId = '${param.courseId}';
              
              
              
                           var dialogFile, formFile, typefile,     
      allFieldsFile = $( [] ).add( typefile ),
      tipsFile = $( ".validateTipsFile" );

//  $( function() {
    
 
 
 function showMetrics(tableName){
     
          document.getElementById("showMetricSel_"+tableName).style.display="none";
           document.getElementById("hideMetricSel_"+tableName).style.display="";
     if(tableName!==""){   
           $('#table_'+tableName).DataTable().column(3).visible(true);
           $('#table_'+tableName).DataTable().column(4).visible(true);
           $('#table_'+tableName).DataTable().column(5).visible(true);
           $('#table_'+tableName).DataTable().column(6).visible(true);
       }
       //metricShow=true;
			
      }
      function hideMetrics(tableName){
          document.getElementById("showMetricSel_"+tableName).style.display="";
           document.getElementById("hideMetricSel_"+tableName).style.display="none";
        if(tableName!==""){  
           $('#table_'+tableName).DataTable().column(3).visible(false);
           $('#table_'+tableName).DataTable().column(4).visible(false);
           $('#table_'+tableName).DataTable().column(5).visible(false);
           $('#table_'+tableName).DataTable().column(6).visible(false);
       }
       //metricShow=false;
      }
 
    function updateCourseTXT(){
         if (confirm("Are you sure you want to update all wikipedia pages of course?")){
        $.post("/course/pages/update", {courseId: courseId}, function(r){
                                            var res = jQuery.parseJSON(r);
                                            if(res.success===true){
                                                //messageDialog('Operation Done', 'Wikipedia Pages of Course is correcly update!');  


                                            }
                                        })
                                        .fail(function(data) {
                                            if(data.responseJSON.error !== null && data.responseJSON.message !== null){
                                                    alert(data.responseJSON.message);
                                            }
                                        });
                                    }
    }
 
    function updateTopicTXT(){
        if (confirm("Are you sure you want to update all wikipedia pages of topic?")){
        $.post("/course/topic/pages/update", {topicId: idTopic}, function(r){
                                            var res = jQuery.parseJSON(r);
                                            if(res.success===true){
                                                //messageDialog('Operation Done', 'Wikipedia Pages of Topic is correcly update!');


                                            }
                                        })
                                        .fail(function(data) {
                                            if(data.responseJSON.error !== null && data.responseJSON.message !== null){
                                                    alert(data.responseJSON.message);
                                            }
                                        });
                                    }
    }
    
                   
                   
                function selectionCourseFile(){
                    topicOrCourse="course";
                    dialogFile.dialog( "open" );
                }   
                   
                   
    function download(url) {
        document.getElementById('my_iframe').src = url;
        
       
    }
    function exportTopicTXT(type){
        $.post("/course/topic/export/txt", {topicId: idTopic, clean: type}, function(r){
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
  
    function exportCourseTXT(type){
        $.post("/course/export/txt", {courseId: courseId, clean: type}, function(r){
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
  
		
                
    function exportCoursePDF(){
				
                                        
                                        $.post("/course/export/pdf", {courseId: courseId}, function(r){
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
                
                function exportTopicPDF(){
				
                                        
                                        $.post("/course/topic/export/pdf", {topicId: idTopic}, function(r){
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
    
    
    
    
    
                    
                
              
              
              
              
              
                
                function updateTopics(){
                    if (confirm("Are you sure you want to update course?")){
                        $.getJSON("course/topics/update", {topics: JSON.stringify(topics)}, function(r){

                                    


                            })
                        .fail(function(data) {
                            if(data.responseJSON.error !== null && data.responseJSON.message !== null){
                                    alert(data.responseJSON.message);
                            }
                        });
                    }
                }
                
                
                
                
		$(document).ready(function() {
         
        
        
        
                   
                    
                    
                    
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
                
            if(topicOrCourse==="course"){
                
                if(typefile==="pdf"){
                    exportCoursePDF();
                }
                else if(typefile==="clean txt"){
                    exportCourseTXT("0");
                }
                else if(typefile==="raw txt"){
                    exportCourseTXT("1");
                }
            }
            else{
                if(typefile==="pdf"){
                    exportTopicPDF();
                }
                else if(typefile==="clean txt"){
                    exportTopicTXT("0");
                }
                else if(typefile==="raw txt"){
                    exportTopicTXT("1");
                }
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
                    
              
       
        
        
        
        
        
                        function spostaSu(tid){
                            topics=[];
                    var results2=[];
                    var ind=-1;
                    if(results.length && results.length > 0){
                        for(var i=0;i<results.length;i++){
                            var idd = results[i].id;
                            if(idd===tid){
                                ind=i;
                            }
                        }
                    }
                    if(ind>0){
                        for(var i=0;i<results.length;i++){
                            if(i===ind-1){
                                results2[i]=results[ind];
                            }
                            else if(i===ind){
                                results2[i]=results[i-1];
                            }
                            else{
                                results2[i]=results[i];
                            }
                            topics[i]={id: results2[i].id+"", position: i+""};
                        }
                        results=results2;
                        printTables();
                    }
                }
                function spostaGiu(tid){
                    topics=[];
                    var results2=[];
                    var ind=-1;
                    if(results.length && results.length > 0){
                        for(var i=0;i<results.length;i++){
                            var idd = results[i].id;
                            if(idd===tid){
                                ind=i;
                            }
                        }
                    }
                    if(ind<results.length-1){
                        for(var i=0;i<results.length;i++){
                            if(i===ind+1){
                                results2[i]=results[ind];
                            }
                            else if(i===ind){
                                results2[i]=results[i+1];
                            }
                            else{
                                results2[i]=results[i];
                            }
                            topics[i]={id: results2[i].id+"", position: i+""};
                        }
                        results=results2;
                        printTables();
                    }
                    
                    
                }
                
                function printTables(){
                    container.hide();
                    container.empty();
                    
                    container.append('<h1 style="text-align:left">Detail of course: <b><i>'+courseTitle+'</i></b></h1>');
                    container.append('<a href="#" onclick="updateTopics();" id="updateCourse" class="btn-save"><i title="Save Course" class="halflings-icon hdd"></i>Save</a>&nbsp;&nbsp;&nbsp;');
                    container.append('<a href="#" onclick="selectionCourseFile();" id="exportCoursePDF" class="btn-save"><i title="Export File of Course" class="halflings-icon file"></i>Export File</a>&nbsp;&nbsp;&nbsp;');
                    container.append('<a href="#" onclick="updateCourseTXT();" id="updateCourseTXT" class="btn-save"><i title="Update Wikipedia Pages of Course" class="halflings-icon refresh"></i>Update WikiPages</a>&nbsp;&nbsp;&nbsp;<br><br>');
                /*    
                    container.append('<a href="#" onclick="showMetrics();" id="showMetricSel" class="btn-save"><i title="Show Metrics Details" class="halflings-icon refresh"></i>Show Metrics Details</a>&nbsp;&nbsp;&nbsp;');
                    container.append('<a href="#" onclick="hideMetrics();" id="hideMetricSel" class="btn-save"><i title="Hide Metrics Details" class="halflings-icon refresh"></i>Hide Metrics Details</a>&nbsp;&nbsp;&nbsp;<br><br>');
                */    
                                
                    if(results.length && results.length > 0){
					for(var i=0;i<results.length;i++){
						drawNewTopicTable(results[i], container);
					}
				}
				else{
					$('#topicListContainer').append('<p> This course doesn\'t contain any topic.</p>')
				}
                                container.show();
                }
                        
                        
                        $loaderOptions = {
	            autoCheck: 32,
	            size: '32',
	            bgColor: '#FFF',
	            bgOpacity: 0.5,
	            fontColor: '#000', 
	            title: 'Loading course detail. Please wait...',
	            isOnly: true,
	            imgUrl: '/resources/img/loading[size].gif'
	        };
			$.loader.open($loaderOptions);
                        
			
			container = $('#topicListContainer');
			//container.empty();
			
			$.getJSON("/course/detail", {courseId: courseId}, function(r){
				
				results = r.data;
                                /*
				if(results.length && results.length > 0){
					for(var i=0;i<results.length;i++){
						drawNewTopicTable(results[i], container);
					}
				}
				else{
					$('#topicListContainer').append('<p> This course doesn\'t contain any topic.</p>')
				}
				*/
                               $.loader.close();
                                printTables();
				
				
			})
		    .fail(function(data) {
                        $.loader.close();
		    	if(data.responseJSON.error !== null && data.responseJSON.message !== null){
		    		alert(data.responseJSON.message);
		    	}
		    });
			
			
			function drawNewTopicTable(topic, container) {
				
				var topicId = topic.id;
				var topicTitle = topic.title;
				
				var topicItems = topic.items;
				
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
			                "defaultContent":   '<a class="btn btn-success" id="buttonView" title="Open article page"><i class="halflings-icon white zoom-in"></i></a>'
			            }
			        ]
			    };
				
				
				var newTable = defineNewTable(topicId, topicTitle);
				
				container.append(newTable+'');
				
				tableName = '#table_'+topicId; 
				
				dataTable = $(tableName).dataTable(tableOptions);
				
				loadTableData(dataTable, topicItems);
                                hideMetrics(topicId);
				/*
                                if(!metricShow){
                                    hideMetrics();
                                }
                                else{
                                    showMetrics();
                                }
                                */
				$(tableName+' tbody').on( 'click', '#buttonView', function () {
					var rowData =  dataTable.DataTable().row($(this).parents('tr')).data();
			    	window.open(rowData[7]);
			    } );
				
                                
                                $('#updateTopicTXT_'+topicId).click(function(e){
					//exportTopicPDF(topicId);
                                        idTopic=topicId;
                                        
                                        updateTopicTXT();
				});
                                $('#exportTopicPDF_'+topicId).click(function(e){
					//exportTopicPDF(topicId);
                                        idTopic=topicId;
                                        topicOrCourse="topic";
                                        dialogFile.dialog( "open" );
				});
                                $('#btn-meno_'+topicId).click(function(e){
					spostaGiu(topicId);
				});
                                $('#btn-piu_'+topicId).click(function(e){
					spostaSu(topicId);
				});
                                
				$('#btn-min_'+topicId).click(function(e){
					e.preventDefault();
					var $target = $(this).parent().parent().next('.box-content');
					if($target.is(':visible')) $('i',$(this)).removeClass('chevron-up').addClass('chevron-down');
					else 					   $('i',$(this)).removeClass('chevron-down').addClass('chevron-up');
					$target.slideToggle();
				});
				
				$('#btn-modify_'+topicId).click(function(e){
					var rowData =  dataTable.DataTable().row($(this).parents('tr')).data();
					window.open("/topicdetail.html?courseId="+courseId+"&topicId="+topicId, "_self");
				});
                                
                                $('#buttonRemove_'+topicId).click(function(e){
                                    if (confirm("Are you sure you want to delete this topic?")){
					$.post("/course/topic/remove", {topicId: topicId}, function(r){
                                            var res = jQuery.parseJSON(r);
                                            if(res.success===true){
                                                    //messageDialog('Operation done', 'Course was correctly deleted.');
                                                    // loading saved data stored on topic detail page
                                                    //window.open("/coursedetail.html?courseId="+courseId+"&courseTitle="+courseTitle, "_self");
                                                    //dataTable.DataTable().row( $(this).parents('tr') ).remove().draw( false );
                                                    location.reload();


                                            }
                                        })
                                        .fail(function(data) {
                                            if(data.responseJSON.error !== null && data.responseJSON.message !== null){
                                                    alert(data.responseJSON.message);
                                            }
                                        });
                                    }
				});
				
			}
			
			function defineNewTable(topicId, topicTitle){
				
				var el = 
					'<div id="topicListContainer_'+topicId+'">'+
						'<div class="box span12">'+
							'<div class="box-header" data-original-title>'+
								'<h2><i class="halflings-icon tasks"></i><span class="break"></span>Topic: <b><i>'+topicTitle+'</i></b></h2>'+
								'<div class="box-icon">'+
                                                                '<a href="#" id="btn-min_'+topicId+'" class="btn-minimize"><i title="Expand/Collapse topic list" class="halflings-icon chevron-down"></i></a>'+
                                                                '<a href="#" id="btn-meno_'+topicId+'" class="btn-minimize"><i title="Move down this topic" class="halflings-icon arrow-down"></i></a>'+
                                                                        '<a href="#" id="btn-piu_'+topicId+'" class="btn-minimize"><i title="Move up this topic" class="halflings-icon arrow-up"></i></a>'+
									'<a href="#" id="buttonRemove_'+topicId+'" class="btn-save"><i title="Remove topic" class="halflings-icon trash"></i></a>'+
                                                                        '<a href="#" id="exportTopicPDF_'+topicId+'" class="btn-save"><i title="Export File of Topic" class="halflings-icon file"></i></a>'+
                                                                        '<a href="#" id="updateTopicTXT_'+topicId+'" class="btn-save"><i title="Update Wikipedia Pages of Topic" class="halflings-icon refresh"></i></a>'+
									'<a href="#" id="btn-modify_'+topicId+'"><i title="Modify topic" class="halflings-icon edit"></i></a>'+
                                                                        
                                                                        
                                                                        
								'</div>'+
							'</div>'+
							'<div class="box-content" hidden="true">'+
                                                        '<a id="showMetricSel_'+topicId+'" href="#" onclick="showMetrics('+topicId+');" >Show Metrics Details</a>'+
                                                        '<a id="hideMetricSel_'+topicId+'" href="#" onclick="hideMetrics('+topicId+');" style="display:none">Hide Metrics Details</a>'+
								'<table id ="table_'+topicId+'" class="table table-striped table-bordered bootstrap-datatable">'+
								  '<thead>'+
									  '<tr>'+
										  '<th hidden="true">pageId</th>'+
									      '<th hidden="true">buildPosition</th>'+
										  '<th width=39%>Title</th>'+
										  '<th width=12%>T.S. Distance</th>'+
										  '<th width=12%>TF-IDF (Cos. Sim.)</th>'+
										  '<th width=12%>LSI (Cos. Sim.)</th>'+
										  '<th width=12%>I.G. (Cos. Sim.)</th>'+
										  '<th hidden="true">unescapedUrl</th>'+
										  '<th hidden="true">selectionInfo</th>'+
										  '<th width=13%>Actions</th>'+
									  '</tr>'+
								  '</thead>'+   
								  '<tbody id="tableBody_'+topicId+'">'+
								  '</tbody>'+
							  '</table>'+    
							'</div>'+
						'</div>'+
					'</div>';
				
				return el;
			} 
			
			function loadTableData(dataTable, items){

				if(items.length){
					for(var i=0;i<items.length;i++){
						dataTable.DataTable().row.add(new addItemToTable(items[i]));
					}
					dataTable.DataTable().draw();
				}
			}
			
			function addItemToTable(item){
				var arr = [];
				
				arr.push(item.title);//item.pageId);
				arr.push(item.buildPosition);
				arr.push(item.title);
				arr.push(item.ts_rank);
				arr.push(item.tfidf_rank);
				arr.push(item.lsi_rank);
				arr.push(item.ig_rank);
				arr.push(item.unescapedUrl);
                                var sil={};
                                if(item.selectionInfo){
                                    sil = item.selectionInfo;
                                }
				arr.push(sil);
				return arr;
			}
			
		});
		
		</script>
		
	<!-- end: JavaScript-->

        
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
