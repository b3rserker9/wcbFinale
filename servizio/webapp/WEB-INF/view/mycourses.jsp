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
					<li><a href="#">My Courses</a></li>
				</ul>
				
				<div id="addCourseContainer" hidden="true">
					<div class="box span12">
						<div class="box-header" data-original-title>
							<h2><i class="halflings-icon edit"></i><span class="break"></span>Add a new course</h2>
							<div class="box-icon">
								<!-- no icons -->
							</div>
						</div>
						<div class="box-content">
							<form class="form-horizontal" id="addCourseForm">
							  <fieldset>
							 	 <div class="control-group">
									<label class="control-label" for="courseTitle">Title</label>
									<div class="controls">
									  <input class="span6 typeahead" required="required" id="courseTitle" type="text" value="" oninvalid="setCustomValidity('Please, insert a valid title')" onchange="try{setCustomValidity('')}catch(e){}">
									</div>
								  </div>
								
								<div class="control-group hidden-phone">
								  <label class="control-label" for="courseDescription">Description</label>
								  <div class="controls">
									<textarea id="courseDescription" required="required" rows="4" oninvalid="setCustomValidity('Please, insert a valid description')" onchange="try{setCustomValidity('')}catch(e){}"></textarea>
								  </div>
								</div>
								<div class="form-actions">
								  <button type="submit" class="btn btn-primary">Apply</button>
								  <button id="cancelAddCourseButton" type="reset" class="btn">Cancel</button>
								</div>
							  </fieldset>
							</form>   
						</div>
					</div><!--/span-->
				</div><!--/row-->
			
				<div id="modifyCourseContainer" hidden="true">
					<div class="box span12">
						<div class="box-header" data-original-title>
							<h2><i class="halflings-icon edit"></i><span class="break"></span>Modify course</h2>
							<div class="box-icon">
								<!-- no icons -->
							</div>
						</div>
						<div class="box-content">
							<form class="form-horizontal" id="modifyCourseForm">
							  <fieldset>
							 	 <div class="control-group">
									<label class="control-label" for="modCourseTitle">Title</label>
									<div class="controls">
									  <input class="span6 typeahead" required="required" id="modCourseTitle" type="text" value="" oninvalid="setCustomValidity('Please, insert a valid title')" onchange="try{setCustomValidity('')}catch(e){}">
									</div>
								  </div>
								
								<div class="control-group hidden-phone">
								  <label class="control-label" for="modCourseDescription">Description</label>
								  <div class="controls">
									<textarea id="modCourseDescription" required="required" rows="4" oninvalid="setCustomValidity('Please, insert a valid description')" onchange="try{setCustomValidity('')}catch(e){}"></textarea>
								  </div>
								</div>
								
								<input id="modCourseId" type="hidden" value="">
								<input id="modCourseRow" type="hidden" value="">
								<input id="modCourseTimestamp" type="hidden" value="">
								<input id="modCourseDate" type="hidden" value="">
								
								<div class="form-actions">
								  <button type="submit" class="btn btn-primary">Apply</button>
								  <button id="cancelModifyCourseButton" type="reset" class="btn">Cancel</button>
								</div>
							  </fieldset>
							</form>   
						</div>
					</div><!--/span-->
				</div><!--/row-->

				<div id="courseListContainer">
					<div class="box span12">
						<div class="box-header" data-original-title>
							<h2><i class="halflings-icon tasks"></i><span class="break"></span>My Courses</h2>
							<div class="box-icon">
								<a href="#" id ="addCourseButton" class="btn-add"><i title="Add a new course" class="halflings-icon plus"></i></a>
								<a href="#" id = "saveCourseButton" class="btn-save"><i title="Save all" class="halflings-icon hdd"></i></a>
							</div>
						</div>
						<div class="box-content">
							<table id ="coursesTable" class="table table-striped table-bordered bootstrap-datatable">
							  <thead>
								  <tr>
								      <th hidden="true">id</th>
								      <th hidden="true">timestamp</th>
									  <th width=9%>Created</th>
									  <th width=25%>Title</th>
									  <th width=35%>Description</th>
									  <th width=8%>Status</th>
									  <th width=23%>Actions</th>
								  </tr>
							  </thead>   
							  <tbody id="coursesTableBody">
							  	<!-- dynamic content build -->
							  </tbody>
						  </table>     
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
	<iframe id="my_iframe" style="display:none;"></iframe>
	<!-- start: JavaScript-->
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
		<script src="/resources/js/commons.js"></script>
		<script src="/resources/js/jquery-loader.js"></script>

                <script src="/resources/js/jquery-ui.js"></script>
		<script type="text/javascript">
                   var courseId;
                    var dialogFile, formFile, typefile,     
      allFieldsFile = $( [] ).add( typefile ),
      tipsFile = $( ".validateTipsFile" );

//  $( function() {
    
 $loaderOptions = {
	            autoCheck: 32,
	            size: '32',
	            bgColor: '#FFF',
	            bgOpacity: 0.5,
	            fontColor: '#000', 
	            title: 'Loading my courses. Please wait...',
	            isOnly: true,
	            imgUrl: '/resources/img/loading[size].gif'
	        };
			$.loader.open($loaderOptions);
    
                   
          function updateCourseTXT(){
         if (confirm("Are you sure you want to update all wikipedia pages of course?")){
        $.post("/course/pages/update", {courseId: courseId}, function(r){
                                            var res = jQuery.parseJSON(r);
                                            if(res.success===true){
                                                messageDialog('Operation Done', 'Wikipedia Pages of Course is correcly update!');  


                                            }
                                        })
                                        .fail(function(data) {
                                            if(data.responseJSON.error !== null && data.responseJSON.message !== null){
                                                    alert(data.responseJSON.message);
                                            }
                                        });
                                    }
    }         
                   
                   
                   
    function download(url) {
        document.getElementById('my_iframe').src = url;
        
       
    }
    function createPdf(){
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
    function createTxt(type){
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
			
			
			var dataTable;
			var tableOptions = {
				"order": [[ 1, "desc" ]],
				"sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span12'i><'span12 center'p>>",
				"sPaginationType": "bootstrap",
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
		                "targets": [ 6 ],
		                "data": null,
		                "defaultContent":   '<a class="btn btn-success" id="courseDetail" title="View course detail"><i class="halflings-icon white zoom-in"></i></a>&nbsp;'+
                                                        '<a class="btn btn-info" id="modifyCourseButton" title="Modify Course attributes"><i class="halflings-icon white edit"></i></a>&nbsp;'+
											'<a class="btn btn-info2" id="extractPdfButton" title="Export File of Course"><i class="halflings-icon white file"></i></a>&nbsp;'+
                                                                                        '<a class="btn btn-primary" id="updateCourseTXT" title="Update Wikipedia Pages of Course"><i class="halflings-icon white refresh"></i></a>&nbsp;'+
											'<a class="btn btn-danger" id="buttonRemove" title="Delete Course"><i class="halflings-icon white trash"></i></a>'
		            }
		        ]
		    };
			
                        
                        $('#coursesTable tbody').on( 'click', '#updateCourseTXT', function () {
				courseId = dataTable.DataTable().row( $(this).parents('tr')).data()[0];
				var courseTitle =  dataTable.DataTable().row( $(this).parents('tr')).data()[3];
				if(courseId !=='new'){
					updateCourseTXT();
				}
		    } );
                        
			$('#coursesTable tbody').on( 'click', '#courseDetail', function () {
				courseId = dataTable.DataTable().row( $(this).parents('tr')).data()[0];
				var courseTitle =  dataTable.DataTable().row( $(this).parents('tr')).data()[3];
				if(courseId !=='new'){
					window.open("/coursedetail.html?courseId="+courseId+"&courseTitle="+courseTitle, "_self");
				}
		    } );
			$('#coursesTable tbody').on( 'click', '#extractPdfButton', function () {
				courseId = dataTable.DataTable().row( $(this).parents('tr')).data()[0];
                                        
                                        //createPDF();
                                        dialogFile.dialog( "open" );
                                        //getDocAux();
		    } );
			$('#coursesTable tbody').on( 'click', '#buttonRemove', function () {
				if (confirm("Are you sure you want to delete this course?")){
					
                                        courseId = dataTable.DataTable().row( $(this).parents('tr')).data()[0];
                                        
                                        $.post("/course/remove", {courseId: courseId}, function(r){
                                            var res = jQuery.parseJSON(r);
                                            if(res.success===true){
                                                    
                                                    // loading saved data stored on topic detail page
                                                    //dataTable.DataTable().row( $(this).parents('tr') ).remove().draw( false );
                                                    
                                                    messageDialog('Operation done', 'Course was correctly deleted.');
                                                    location.reload();
                                                    


                                            }
                                        })
                                        .fail(function(data) {
                                            if(data.responseJSON.error !== null && data.responseJSON.message !== null){
                                                    alert(data.responseJSON.message);
                                            }
                                        });
                                        
                                        
                                        
				}
		    } );
			
			function initDataTable() {
				dataTable = $('#coursesTable').dataTable(tableOptions);
			}
			
			initDataTable();
			
			function loadTableData(r){
				var results = r.data;

				if(results.length){
					
					//$('#coursesTableBody').empty();
					dataTable.fnDestroy();
					initDataTable();
					dataTable.DataTable().clear();
					for(var i=0;i<results.length;i++){
						dataTable.DataTable().row.add(new addItemToTable(results[i], false));
					}
					dataTable.DataTable().draw();
				}
			}
			
			$.getJSON("/course/getAll", function(r){
                            $.loader.close();
				loadTableData(r);
			})
		    .fail(function(data) {
                        $.loader.close();
		    	if(data.responseJSON.error !== null && data.responseJSON.message !== null){
		    		alert(data.responseJSON.message);
		    	}
		    });
			
			
			$("#addCourseButton").click(function() {
		        $("#addCourseContainer").show();
		        $("#modifyCourseContainer").hide();
		    });
		
			$("#cancelAddCourseButton").click(function() {
		        $("#addCourseContainer").hide();
		        $('#courseTitle').val("");
				$('#courseDescription').val("");
		    });
		
			$('#addCourseForm').submit(function(event) {
				event.preventDefault();
				
				var date = getDate();
				var titleValue = $('#courseTitle').val();
				var descValue = $('#courseDescription').val();
				
				var item = {id:'new', timestamp: $.now(), date: date, title: titleValue, description: descValue};
				
				var rowNode = dataTable.DataTable().row.add(new addItemToTable(item, false)).draw().node();
				$( rowNode ).css( 'color', 'red' ).animate({ color: 'black' },3000 );
				
				$("#addCourseContainer").hide();
				$('#courseTitle').val("");
				$('#courseDescription').val("");
			});
			
			$('#coursesTable tbody').on( 'click', '#modifyCourseButton', function () {
				$("#addCourseContainer").hide();
			    $("#modifyCourseContainer").show();
					
			    var rowData = dataTable.DataTable().row($(this).parents('tr')).data();
			    
			    $('#modCourseId').val(rowData[0]);
			    $('#modCourseRow').val(dataTable.DataTable().row($(this).parents('tr')).index());
			    $('#modCourseTimestamp').val(rowData[1]);
			    $('#modCourseDate').val(rowData[2]);
			    
			    $('#modCourseTitle').val(rowData[3]);
				$('#modCourseDescription').val(rowData[4]);
		    } );
			
			$("#cancelModifyCourseButton").click(function() {
		        $("#modifyCourseContainer").hide();
		        $('#modCourseTitle').val("");
				$('#modCourseDescription').val("");
				$('#modCourseId').val("");
				$('#modCourseRow').val("");
				$('#modCourseTimestamp').val("");
				$('#modCourseDate').val("");
		    });
			
			$('#modifyCourseForm').submit(function(event) {
				event.preventDefault();
				
				var id = $('#modCourseId').val();
				var timestamp = $('#modCourseTimestamp').val();
				var date = $('#modCourseDate').val();
				
				var titleValue = $('#modCourseTitle').val();
				var descValue = $('#modCourseDescription').val();
				
				var rowToRemove = $('#modCourseRow').val();
				
				var item = {id: id, timestamp: timestamp, date: date, title: titleValue, description: descValue};
				
				dataTable.DataTable().row( rowToRemove ).remove().draw( false );
				
				var rowNode = dataTable.DataTable().row.add(new addItemToTable(item, true)).draw().node();
				$( rowNode ).css( 'color', 'red' ).animate({ color: 'black' },3000 );
				
				$("#modifyCourseContainer").hide();
				$('#modCourseTitle').val("");
				$('#modCourseDescription').val("");
				$('#modCourseId').val("");
				$('#modCourseRow').val("");
				$('#modCourseTimestamp').val("");
				$('#modCourseDate').val("");
			});
			
		
			$("#saveCourseButton").click(function() {
				var allCourses = [];

			    $('#coursesTableBody tr').each(function (index, value) {
			    	
			    	var course = {};

			    	var rowData = dataTable.DataTable().row(index).data();
			    	if(rowData && typeof(rowData) !== "undefined"){
			    		course.id = rowData[0];
				    	course.date = rowData[1];
				    	course.title = rowData[3];
				    	course.description = rowData[4];
				        allCourses.push(course);
			    	}
			    });
			 
			    $.post("/course/save", {courses: JSON.stringify(allCourses)}, function(r){
			    	var res = jQuery.parseJSON(r);
			    	if(res.success===true){
			    		messageDialog('Operation done', 'Courses were correctly saved.');
			    		loadTableData(res);
			    	}
			    })
			    .fail(function(data) {
			    	if(data.responseJSON.error !== null && data.responseJSON.message !== null){
			    		alert(data.responseJSON.message);
			    	}
			    });
		    });
			
			function getDate(){
				function addZ(n) {
					return n<10? '0'+n : ''+n;
				};
				var d = new Date();
				return d.getFullYear()+'/'+addZ(d.getMonth()+1)+'/'+addZ(d.getDate());
			}
		
			function addItemToTable(item, isModified){
				var arr = [];
				
				arr.push(item.id);
				arr.push(item.timestamp);
				arr.push(item.date);
				arr.push(item.title);
				arr.push(item.description);
				if(item.id==='new')
					arr.push('<span class="label label-success">New</span>');
				else if(isModified)
					arr.push('<span class="label label-warning">Modified</span>');
				else
					arr.push('');
				//arr.push('<a class="btn btn-success" href="#"><i class="halflings-icon white zoom-in"></i>'+
				//		'</a>&nbsp;<a class="btn btn-info" href="#"><i class="halflings-icon white edit"></i>'+
				//		'</a>&nbsp;<a class="btn btn-danger" id="buttonRemove" href="#"><i class="halflings-icon white trash"></i></a>');
				
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
