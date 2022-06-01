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
    
    
 			<div id="content" class="span10">
				<ul class="breadcrumb">
					<li>
						<i class="icon-home"></i>
						<a href="homepage.html">Home</a> 
						<i class="icon-angle-right"></i>
					</li>
					<li><a href="#">Community</a></li>
				</ul>
				<div class="container-fluid-full">
					<div class="row-fluid" style="text-align:justify">
						<div id="showGraphContainer" hidden="true">
							<div class="box span12">
								<div class="box-header" data-original-title>
									<h2><i class="halflings-icon info-sign"></i><span class="break"></span>Graph - Community of Practice</h2>
									<div class="box-icon">
										<a href="#" id="buttonHideGraph" class="btn-save"><i title="Hide Graph" class="halflings-icon remove"></i></a>
									</div>
									<input type="hidden" id="graphVal"/>
									<input type="hidden" id="legendColorsResult"/>
								</div>
								<div id="leftGraphInfoContainer" style="float: left; width:18%; height: 700px; display: inline-block; overflow: auto; background:rgb(250,250,250);">
								</div>
								<div id="upperGraphContainer" style="float: left; width:82%; height: 80px; display: inline-block; background:rgb(250,250,250);">
									<div id="graphLegend" style="float: right; width:40%; display: inline-block;"></div>
									
									<div style="float: right; width:20%; display: inline-block; text-align: center;">
										<span style="font-size: 14px; margin-left:0em; margin-top: 0em; margin-bottom: 0em;"><b>Focus on attitude: </b></span><br /><br />
										<select id="teaching-attitude" style="width:140px; height:26px; font-size: 12px;">
									    	<option selected value="all">All</option>
											<option value="expert">Expert</option>
											<option value="formalAuthority">Formal autority</option>
											<option value="personalModel">Personal model</option>
											<option value="facilitator">Facilitator</option>
											<option value="delegator">Delegator</option>
									    </select>						
									</div>
									
									<div style="float: right; width:20%; display: inline-block; text-align: center;">
										<span style="font-size: 14px; margin-left:0em; margin-top: 0em; margin-bottom: 0em;"><b>Filter by discipline: </b></span><br /><br />
										<select id="node-discipline" style="width:140px; height:26px; font-size: 12px;">
									    	<option selected value="All">All Disciplines</option>
											<option value="Arts/Music/Theater">Arts/Music/Theater</option>
											<option value="Applied Sciences">Applied Sciences</option>
											<option value="Applied Studies">Applied Studies</option>
											<option value="Business Admin.">Business Admin.</option>
											<option value="Communication/Journalism">Communication/Journalism</option>
											<option value="Education">Education</option>
											<option value="Foreign Language">Foreign Languages</option>
											<option value="Humanities">Humanities</option>
											<option value="Math/Computer Science">Math/Computer Science</option>
											<option value="Physical/Biological Sciences">Physical/Biological Sciences</option>
											<option value="Social Studies">Social Studies</option>
									    </select>						
									</div>
									
									<div style="float: right; width:20%; display: inline-block; text-align: center;">
										<span style="font-size: 14px; margin-left:0em; margin-top: 0em; margin-bottom: 0em;"><b>Filter by node hits: </b></span>&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="min-degree-val">[1]</span></b><br /><br />
										<span id="min-degree-value">1</span>&nbsp;&nbsp;<input id="min-degree" type="range" min="1" max="1" value="1" style="width:140px; font-size: 12px;">&nbsp;&nbsp;<span id="max-degree-value">1</span>
									</div>
								</div>
								<div id="innerShowGraphContainer" class="box-content" style="float: left; width:78%; height: 590px; display: inline-block; background:rgb(250,250,250);">
								</div>
							</div>
						</div>
						<h1 style="text-align:center">Wiki Course Builder: the Community of Practice</h1>
						<br />
						<p>In this section is shown the Graph representing the knowledge base generated by the <b>Wiki Course Builder's Community of Practice.</b><br/><br/>
						The graph shows all articles chosen, at least, in one course and their interconnections within the course.<br/> 
						Nodes represent Wikipedia articles that appear in one or more courses. Each node is weighted with the number of times it has been chosen as part of a topic.<br/>
						A link between two nodes is created if both nodes appear within the same topic. Each link is weighted with the number of times this 
						connection is selected in different courses.<br/>
						The color of a node determines the prevalent teaching style associated with that node: the graph's legend shows the color-style matching.<br/>
						It's possible to view subsections of the graph identified by Disciplines, by selecting the desired one in the drop down list.
						</p> 
						<br />
						<a href="#" id="buttonShowGraph"><i title="Show Graph" style="font-size: 16px; color:blue"><u>Click here to build and visualize graph.</u></i></a>
						<br /><br />
					</div>
				</div>
    		</div>
		</div><!--/.fluid-container-->
	</div><!--/fluid-row-->
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
	<script src="/resources/js/jquery-loader.js"></script>
	<script src="/resources/js/graph/sigma.min.js"></script>
	<script src="/resources/js/graph/sigma.parsers.gexf.min.js"></script>
	<script src="/resources/js/graph/sigma.plugins.filter.js"></script>
	
	<script type="text/javascript">
		var _ = {
			$: function (id) {
				return document.getElementById(id);
			}
		}
		
		$("#buttonShowGraph").click(function() {
			loadGraph();
		});
	
		$("#buttonHideGraph").click(function() {
			$("#showGraphContainer").hide();
		});
		
		function getTeachingAttitudeSelectedValue(){
			return _.$('teaching-attitude').value;
		}
		
		function loadGraph(){
			$loaderOptions = {
	            autoCheck: 32,
	            size: '32',
	            bgColor: '#FFF',
	            bgOpacity: 0.5,
	            fontColor: '#000', 
	            title: 'Loading community graph detail. Please wait...',
	            isOnly: true,
	            imgUrl: '/resources/img/loading[size].gif'
	        };
			
			$.loader.open($loaderOptions);
			
			$.getJSON("/networks/getHittingPagesGraph", {}, function(r){
				if(r && r.gexf){
					$('#graphVal').val(r.gexf);
					drawGraph();
				}
				
				if(r && r.legendColors){
					$('#legendColorsResult').val(JSON.stringify(r.legendColors));
				}
				
				drawLegend();
				
			    $.loader.close();
			})
			.fail(function(data) {
				$.loader.close();
		    	if(data.responseJSON.error != null && data.responseJSON.message != null){
		    		alert(data.responseJSON.message);
		    	}
		    });
		}
		
		function drawLegend(){
			var legendContainer = $('#graphLegend');
			var attitudeValue = _.$('teaching-attitude').value;
			var attitudeLabel = $('#teaching-attitude option:selected').text();
			
			legendContainer.empty();
			
			var title = '';
			if(attitudeValue=='all'){
				title = 'Predominant teaching style attribute:';
			}
			else{
				title = 'Predominant <i>"'+attitudeLabel+'"</i> attitude:';
			}
			
			var html='<span style="font-size: 14px; margin-left:3em; margin-top: 0em; margin-bottom: 0em;"><b>'+title+'</b></span><br /><table><tr>';
			
			var legendInfos = jQuery.parseJSON($('#legendColorsResult').val())[attitudeValue];

			var j=1;
			for(i=0; i<legendInfos.length; i++){
				html += '<td><span style="font-size: 10px; margin-left:3em; margin-top: 0em; margin-bottom: 0em; background-color:'+legendInfos[i].color+';">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span style="font-size: 10px; margin-left:0.5em;">'+legendInfos[i].label+'</span></td>';
				if(j==3 && i<(legendInfos.length-1)){
					html+='</tr><tr>'
					j=1;
				}
				else{
					j++;
				}
			}
			
			html +='</tr></table>';
			
			legendContainer.append(html);
		}
		
		function drawGraph() {
			//sigma graph and filters
			var sg;
			var filter;
			//graphic configuration
			var gConfigs = {
				minNodeSize : 1,
				maxNodeSize : 50,
				minEdgeSize : 1,
				maxEdgeSize : 5,
				fadeColor : '#eee',
				fadeBorderColor : '#ccc'
			}
			
			var gexfXml = $.parseXML( $("#graphVal").val() );
			
			if(!gexfXml || gexfXml=='')
				return;
			
			$("#innerShowGraphContainer").empty();
			$("#leftGraphInfoContainer").empty();
			$("#showGraphContainer").show();
			
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
			
			function applyMinDegreeFilter(e) {
			    var v = e.target.value;
			    _.$('min-degree-val').textContent = '['+v+']';
			    
			    filter
			      .undo('min-degree')
			      .nodesBy(function(n) {
			        return v<1 || n.attributes.contributes >= v;
			      }, 'min-degree')
			      .apply();
			}
			
			function applyDisciplineFilter(e) {
			    var c = e.target[e.target.selectedIndex].value;
			    filter
			      .undo('node-discipline')
			      .nodesBy(function(n) {
			        return !c.length || 'All' === c || n.attributes.discipline === c;
			      }, 'node-discipline')
			      .apply();
			}
			
			_.$('min-degree').addEventListener("input", applyMinDegreeFilter);
		    _.$('min-degree').addEventListener("change", applyMinDegreeFilter);
		    _.$('node-discipline').addEventListener("change", applyDisciplineFilter);
		    
		    
		    function applyAttitudeSelection(e) {
			    var c = e.target[e.target.selectedIndex].value;

			    sg.graph.nodes().forEach(function(n) {
			    	if(n.attributes.all_colors){
			    		n.attributes.colors = JSON.stringify(jQuery.parseJSON(n.attributes.all_colors)[c]);
			    		n.attributes.originalColors = n.attributes.colors;
			    	}  
			    	else
			        	n.originalColor = n.color;
			    });
			    
			    sg.dispatchEvent('clickStage'); //reset node selection
			    sg.refresh();
			    
			    drawLegend();
			}
			_.$('teaching-attitude').addEventListener("change", applyAttitudeSelection);
		    
		    
		    function getPinColor(node){
		    	if(node.attributes && node.attributes.colors){
		    		var size = 10;
		    		var topMargin = '6px';
		    		var bottomMargin = '0';
		    		var leftMargin = '10px';
		    		var rightMargin = '5px';
		    		
		    		var colorArray = jQuery.parseJSON(node.attributes.colors); 
					var slices = colorArray.length;
					var width = size/colorArray.length;
					var html = '';
					
					for(i=0; i<slices; i++){
						//top right bottom left		
						var margin = topMargin;
						if(i==(slices-1))
							margin += " "+rightMargin;
						else
							margin += " 0";
						margin += " "+bottomMargin;
						if(i==0)
							margin += " "+leftMargin;
						else
							margin += " 0";
						
						html += "<div style=\"float: left; width: "+width+"px; height: "+size+"px; margin: "+margin+"; background: "+colorArray[i]+";\"></div>";
					}
					return html;
		    	}
		    	else{
		    		return "<div style=\"float: left; width: "+size+"px; height: "+size+"px; margin: "+margin+"; background: "+node.color+";\"></div>";
		    	}
		    }
		    
			function fillInfoPanel(node, neighbors, allNodes){
				var leftContainer = $("#leftGraphInfoContainer");
				leftContainer.empty();
				//leftContainer.append( "<p style=\"font-size: 12px; margin-top: 0em; margin-bottom: 0em;\"><b>Page:</b></p>" ).append("<a href=\""+node.attributes.url+"\" target=\"_blank\"</a><p style=\"font-size: 12px; color: darkgreen; margin-left:2em; margin-top: 0em; margin-bottom: 0em;\"><u>"+node.label+"</u></p>" );
				leftContainer.append( "<p style=\"font-size: 12px; margin-top: 0em; margin-bottom: 0em;\"><b>Page:</b></p>" ).append("<p style=\"font-size: 12px; margin-left:2em; margin-top: 0em; margin-bottom: 0em;\">"+getPinColor(node)+"<a style=\"font-size: 12px; color: darkgreen;\" href=\""+node.attributes.url+"\" target=\"_blank\"><u>"+node.label+"</u></a></p>" );
				
				if(node.attributes.teachingStyle){
					var tsObj = jQuery.parseJSON(node.attributes.teachingStyle);
					leftContainer.append( "<p style=\"font-size: 12px; margin-top: 1em; margin-bottom: 0em;\"><b>Teaching Style:</b></p>" )
					leftContainer.append( "<p style=\"font-size: 10px; margin-left:2em; margin-top: 0em; margin-bottom: 0em;\"><b>Expert </b>&nbsp;"+tsObj.expert+"</p>" );
					leftContainer.append( "<p style=\"font-size: 10px; margin-left:2em; margin-top: 0em; margin-bottom: 0em;\"><b>Formal authority </b>&nbsp;"+tsObj.formalAuthority+"</p>" );
					leftContainer.append( "<p style=\"font-size: 10px; margin-left:2em; margin-top: 0em; margin-bottom: 0em;\"><b>Personal model </b>&nbsp;"+tsObj.personalModel+"</p>" );
					leftContainer.append( "<p style=\"font-size: 10px; margin-left:2em; margin-top: 0em; margin-bottom: 0em;\"><b>Facilitator </b>&nbsp;"+tsObj.facilitator+"</p>" );
					leftContainer.append( "<p style=\"font-size: 10px; margin-left:2em; margin-top: 0em; margin-bottom: 0em;\"><b>Delegator </b>&nbsp;"+tsObj.delegator+"</p>" );
					leftContainer.append( "<p style=\"font-size: 10px; margin-left:2em; margin-top: 1em; margin-bottom: 0em;\"><b>Discipline </b>&nbsp;"+tsObj.primaryDiscipline+"</p>" );
					leftContainer.append( "<p style=\"font-size: 10px; margin-left:2em; margin-top: 0em; margin-bottom: 0em;\"><b>Hits </b>&nbsp;"+node.attributes.contributes+"</p>" );
				}
				
				leftContainer.append( "<p style=\"font-size: 12px; margin-top: 1em; margin-bottom: 0em;\"><b>Linked to:</b></p>" ).append("<p style=\"font-size: 10px; margin-left:2em; margin-top: 0em; margin-bottom: 0em;\"></p>");
				
				allNodes.forEach(function(n) {
		          if (neighbors[n.id]){
		        	  //var $newLink = $( "<a href=\"javascript:void(0);\"</a><p style=\"font-size: 10px; color: blue; margin-left:2em; margin-top: 0em; margin-bottom: 0em;\">"+n.label+"</p>" );
		        	  var $newLink = $( "<p style=\"font-size: 10px; margin-left:2em; margin-top: 0em; margin-bottom: 0em;\">"+getPinColor(n)+"<a style=\"font-size: 10px; color: blue;\" href=\"javascript:void(0);\">"+n.label+"</a></p>" );
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
		        	  if(n.attributes.originalColors)
		        		  n.attributes.colors = n.attributes.originalColors;
		        	  else
		                  n.color = n.originalColor;
		          }
		          else{
		        	  if(n.attributes.originalColors)
		        		  n.attributes.colors = '["'+gConfigs.fadeColor+'"]';
		        	  else
		                  n.color = gConfigs.fadeColor;
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

			  var cx = node[prefix + 'x'];
			  var cy = node[prefix + 'y'];
			  var radius = node[prefix + 'size'];
			  
			  if(node.attributes && node.attributes.colors){
				  var colorArray = jQuery.parseJSON(node.attributes.colors); 
				  var slices = colorArray.length;
				  var angle = ((2*Math.PI) / slices);
				  
				  for(i=0; i<slices; i++){
					  context.beginPath();
					  context.fillStyle = colorArray[i];
					  context.moveTo(cx,cy);
					  context.arc(
						  cx,
						  cy,
						  radius,
						  angle * (i+0.5),
						  angle * (i+1.5),
						  false
					  );
					  context.lineTo(cx,cy);
					  context.closePath();
					  context.fill();
				  }
			  }
			  else{
				  context.beginPath();
				  context.fillStyle = node.color || settings('defaultNodeColor');
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
			  
			  // Adding a border
			  context.beginPath();
			  context.arc(
			    node[prefix + 'x'],
			    node[prefix + 'y'],
			    node[prefix + 'size'],
			    0,
			    Math.PI * 2,
			    true
			  );
			  
			  context.lineWidth = 2;
			  context.strokeStyle = "rgb(255,255,255)";
			  context.stroke();
			}
			
			sigma.canvas.edges.def = function(edge, source, target, context, settings) {
				
			    context.beginPath();
			    
			    context.moveTo(source.x, source.y);
			    if (true) {
					var x2,y2,x3,y3,x4,y4,x5,y5;
					x2 = source.x;
					y2 = source.y;
			        if (( source.x == target.x ) && ( source.y == target.y )) {
			            x3 = source.x + 2.8 * source.r;
			            y3 = source.y - source.r;
			            x4 = source.x;
			            y4 = source.y + 2.8 * source.r;
				    	x5 = source.x + 1;
				    	y5 = source.y;
			        } else {
			            x3 = .3 * target.y - .3 * source.y + .8 * source.x + .2 * target.x;
			            y3 = .8 * source.y + .2 * target.y - .3 * target.x + .3 * source.x;
			            x4 = .3 * target.y - .3 * source.y + .2 * source.x + .8 * target.x;
			            y4 = .2 * source.y + .8 * target.y - .3 * target.x + .3 * source.x;
				    	x5 = target.x;
				    	y5 = target.y;
			        }
			        context.bezierCurveTo(x3,y3,x4,y4,x5,y5);
			        context.strokeStyle = edge.color;
			        context.closePath();
			        context.stroke();
			    } else {
			    	context.lineTo(target.x,target.y);
			    	context.strokeStyle = edge.color;
			    	context.closePath();
			    	context.stroke();
			    }
			}
			
			sigma.parsers.gexf(
				gexfXml, { 
				  renderer : {
				  	type:'canvas',
				  	container: document.getElementById('innerShowGraphContainer')
				  },
     			  settings: {
     				defaultNodeType: 'border',
         			minNodeSize: 1,
         			maxNodeSize: 12,
         			minArrowSize: 3,
         			drawLabels : true,
         			//labelSize : 'fixed',
         			//labelThreshold : 8,
         			//autoRescale : true
         			scalingMode : 'outside'
     			  }
			  },
			  function(s) {
				  filter = new sigma.plugins.filter(s);
				  sg = s;
				  var maxDegree = 0;
				  
			      s.graph.nodes().forEach(function(n) {
			    	if(n.attributes.all_colors){
			    		n.attributes.colors = JSON.stringify(jQuery.parseJSON(n.attributes.all_colors)[_.$('teaching-attitude').value]);
			    		n.attributes.originalColors = n.attributes.colors;
			    	}  
			    	else
			        	n.originalColor = n.color;
			        //n.size = s.graph.degree(n.id);
			        n.size = Math.min(gConfigs.maxNodeSize, (gConfigs.minNodeSize + n.attributes.contributes));
			        maxNodeSize: gConfigs.maxNodeSize,
			        
			        maxDegree = Math.max(maxDegree, n.attributes.contributes);
			      });
			      s.graph.edges().forEach(function(e) {
			        e.originalColor = e.color;
			        e.size = e.weight;
			        e.type = 'curve';
			      });
			      
			      _.$('min-degree').max = maxDegree;
			      _.$('max-degree-value').textContent = maxDegree;

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
			        	if(n.attributes.originalColors)
			        		n.attributes.colors = n.attributes.originalColors;
			        	else
			        		n.color = n.originalColor;
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
		
	</script>
	
</body>
</html>
