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
	<link id="bootstrap-style" href="/resources/css/bootstrap.min.css"
		rel="stylesheet">
	<link href="/resources/css/bootstrap-responsive.min.css"
		rel="stylesheet">
	<link id="base-style" href="/resources/css/style.css" rel="stylesheet">
	<link id="base-style-responsive" href="/resources/css/style-responsive.css" rel="stylesheet">
	<link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800&subset=latin,cyrillic-ext,latin-ext'
		rel='stylesheet' type='text/css'>
	<link rel="stylesheet" href="/resources/css/jquery.appendGrid-1.5.2.css" />
	<link rel="stylesheet" type="text/css" href="/resources/css/custom2.css" />

	<!-- end: CSS -->

	<!-- The HTML5 shim, for IE6-8 support of HTML5 elements -->
	<!--[if lt IE 9]>
	  	<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
		<link id="ie-style" href="css/ie.css" rel="stylesheet">
	<![endif]-->

	<!--[if IE 9]>
		<link id="ie9style" href="css/ie9.css" rel="stylesheet">
	<![endif]-->

	<!-- start: Favicon -->
	<link rel="shortcut icon" href="/resources/img/favicon.ico">
	<!-- end: Favicon -->

	<c:if test="${param.message != null && param.message=='compilationRequired'}">
		<div class="alert alert-error"><b><h2>You need to compile Teaching Style survey before using System.</h2></b></div>
	</c:if>

</head>

<body onload="apply_values()">

	<sec:authentication var="principal" property="principal" />

	<!-- start: Header -->
	<div class="navbar">
		<div class="navbar-inner">
			<div class="container-fluid">
				<a class="btn btn-navbar" data-toggle="collapse"
					data-target=".top-nav.nav-collapse,.sidebar-nav.nav-collapse">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
				</a> <a class="brand" href="homepage.html"><span><img
						src="/resources/img/logor3.png" alt="testo" /></span></a> <a class="titolo"
					href="homepage.html"><span> <img
						src="/resources/img/title.png" alt="Wiki Course Builder"
						class="auto-style1" /></span></a>


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
					<li><i class="icon-home"></i> <a href="homepage.html">Home</a> <i
						class="icon-angle-right"></i></li>
					<li><a href="#">Teaching Style Survey</a></li>
				</ul>

				<div class="row-fluid">

					<SCRIPT LANGUAGE="JavaScript">
			
			function check_answers()
			{
				var score1 = ((document.survey.q1.value*1) + (document.survey.q6.value*1) + (document.survey.q11.value*1) + (document.survey.q16.value*1) + (document.survey.q21.value*1) + (document.survey.q26.value*1) + (document.survey.q31.value*1) + (document.survey.q36.value*1))/8;
				var score2 = ((document.survey.q2.value*1) + (document.survey.q7.value*1) + (document.survey.q12.value*1) + (document.survey.q17.value*1) + (document.survey.q22.value*1) + (document.survey.q27.value*1) + (document.survey.q32.value*1) + (document.survey.q37.value*1))/8;
				var score3 = ((document.survey.q3.value*1) + (document.survey.q5.value*1) + (document.survey.q13.value*1) + (document.survey.q18.value*1) + (document.survey.q23.value*1) + (document.survey.q28.value*1) + (document.survey.q33.value*1) + (document.survey.q38.value*1))/8;
				var score4 = ((document.survey.q4.value*1) + (document.survey.q10.value*1) + (document.survey.q14.value*1) + (document.survey.q19.value*1) + (document.survey.q24.value*1) + (document.survey.q29.value*1) + (document.survey.q34.value*1) + (document.survey.q39.value*1))/8;
				var score5 = ((document.survey.q5.value*1) + (document.survey.q10.value*1) + (document.survey.q15.value*1) + (document.survey.q20.value*1) + (document.survey.q25.value*1) + (document.survey.q30.value*1) + (document.survey.q35.value*1) + (document.survey.q40.value*1))/8;
			
				if ((document.survey.q1.value<1)||(document.survey.q2.value<1)||(document.survey.q3.value<1)||(document.survey.q4.value<1)||(document.survey.q5.value<1)||(document.survey.q6.value<1)||(document.survey.q7.value<1)||(document.survey.q8.value<1)||(document.survey.q9.value<1)||(document.survey.q10.value<1)||(document.survey.q11.value<1)||(document.survey.q12.value<1)||(document.survey.q13.value<1)||(document.survey.q14.value<1)||(document.survey.q15.value<1)||(document.survey.q16.value<1)||(document.survey.q17.value<1)||(document.survey.q18.value<1)||(document.survey.q19.value<1)||(document.survey.q20.value<1)||(document.survey.q21.value<1)||(document.survey.q22.value<1)||(document.survey.q23.value<1)||(document.survey.q24.value<1)||(document.survey.q25.value<1)||(document.survey.q26.value<1)||(document.survey.q27.value<1)||(document.survey.q28.value<1)||(document.survey.q29.value<1)||(document.survey.q30.value<1)||(document.survey.q31.value<1)||(document.survey.q32.value<1)||(document.survey.q33.value<1)||(document.survey.q34.value<1)||(document.survey.q35.value<1)||(document.survey.q36.value<1)||(document.survey.q37.value<1)||(document.survey.q38.value<1)||(document.survey.q39.value<1)||(document.survey.q40.value<1)){
						messageDialog('Warning', "You did not answer all the questions. Click 'OK' and answer all questions, then click on 'Score Survey' again.");
					return
				} else {
					if (score1<2.8){
						var score1_range = "Low"
					}else{
						if (score1>3.8){
							var score1_range = "High"
						} else {
							var score1_range = "Moderate"
						}
					}
					if (score2<1.9){
						var score2_range = "Low"
					} else {
						if (score2>3.1) {
							var score2_range = "High"
						} else {
							var score2_range = "Moderate"
						}
					}
					if (score3<2.8) {
						var score3_range = "Low"
					} else {
						if (score3>3.4) {
							var score3_range = "High"
						} else {
							var score3_range = "Moderate"
						}
					}
					if (score4<3.0) {
						var score4_range = "Low"
					} else {
						if (score4>4.0) {
							var score4_range = "High"
						} else {
							var score4_range = "Moderate"
						}
					}
					if (score5<1.8) {
						var score5_range = "Low"
					} else  {
						if (score5>2.8) {
							var score5_range = "High"
						} else {
							var score5_range = "Moderate"
						}
					}
				
				document.results.expert.value = score1;
				document.results.formalauthority.value = score2;
				document.results.personalmodel.value = score3;
				document.results.facilitator.value = score4;
				document.results.delegator.value = score5;
				document.results.expert_range.value = score1_range;
				document.results.formalauthority_range.value = score2_range;
				document.results.personalmodel_range.value = score3_range;
				document.results.facilitator_range.value = score4_range;
				document.results.delegator_range.value = score5_range;
					}	
			}
			
			function apply_values() {
				//load user data
				var formData= $('form[name="results"]');
				$.post("<c:url value="/user/loadTeachingStyle"/>", function(data){
			        if(data !=null && data !=''){
			        	document.results.expert.value = data.expert;
						document.results.formalauthority.value = data.formalauthority;
						document.results.personalmodel.value = data.personalmodel;
						document.results.facilitator.value = data.facilitator;
						document.results.delegator.value = data.delegator;
						document.results.expert_range.value = data.expert_range;
						document.results.formalauthority_range.value = data.formalauthority_range;
						document.results.personalmodel_range.value = data.personalmodel_range;
						document.results.facilitator_range.value = data.facilitator_range;
						document.results.delegator_range.value = data.delegator_range;
						
						document.results.discipline.value= data.discipline;
						document.results.level.value= data.level;
						document.results.race.value= data.race;
						document.results.gender.value= data.gender;
						document.results.teacherrank.value= data.teacherrank;
						
						document.survey.q1.value= data.q1;
						document.survey.q2.value= data.q2;
						document.survey.q3.value= data.q3;
						document.survey.q4.value= data.q4;
						document.survey.q5.value= data.q5;
						document.survey.q6.value= data.q6;
						document.survey.q7.value= data.q7;
						document.survey.q8.value= data.q8;
						document.survey.q9.value= data.q9;
						document.survey.q10.value= data.q10;
						document.survey.q11.value= data.q11;
						document.survey.q12.value= data.q12;
						document.survey.q13.value= data.q13;
						document.survey.q14.value= data.q14;
						document.survey.q15.value= data.q15;
						document.survey.q16.value= data.q16;
						document.survey.q17.value= data.q17;
						document.survey.q18.value= data.q18;
						document.survey.q19.value= data.q19;
						document.survey.q20.value= data.q20;
						document.survey.q21.value= data.q21;
						document.survey.q22.value= data.q22;
						document.survey.q23.value= data.q23;
						document.survey.q24.value= data.q24;
						document.survey.q25.value= data.q25;
						document.survey.q26.value= data.q26;
						document.survey.q27.value= data.q27;
						document.survey.q28.value= data.q28;
						document.survey.q29.value= data.q29;
						document.survey.q30.value= data.q30;
						document.survey.q31.value= data.q31;
						document.survey.q32.value= data.q32;
						document.survey.q33.value= data.q33;
						document.survey.q34.value= data.q34;
						document.survey.q35.value= data.q35;
						document.survey.q36.value= data.q36;
						document.survey.q37.value= data.q37;
						document.survey.q38.value= data.q38;
						document.survey.q39.value= data.q39;
						document.survey.q40.value= data.q40;
			        }
			    })
			    .fail(function(data) {
			    	//DO NOTHING
		   		});
			}
			
			function empty_fields() {
				var expert = document.results.expert.value;
				var formalauthority = document.results.formalauthority.value;
				var personalmodel = document.results.personalmodel.value;
				var facilitator = document.results.facilitator.value;
				var delegator = document.results.delegator.value;
				
				var discipline = document.results.discipline.value;
				var level = document.results.level.value;
				var race = document.results.race.value;
				var gender = document.results.gender.value;
				var teacherrank = document.results.teacherrank.value;
				
				if ((expert.length<1)||(formalauthority.length<1)||(personalmodel.length<1)||(facilitator.length<1)||(delegator.length<1)||(discipline=='0')||(level== '0')||(race=='0')||(gender=='0')||(teacherrank=='0')) {
			      	messageDialog('Warning', "You must score your survey first before submitting results.");
			      	return false
				}
				return true;
			}
			
			function checkentry(num) {
				if (num=="") {
					return false
					}
				else {
					if (num!=1&&num!=2&&num!=3&&num!=4&&num!=5) {
						messageDialog('Warning', "You must enter a number between 1 and 5.");
						return false
					}
				}
			}
			
			</SCRIPT>
					<div align="center">
						<p></p>
						<table width="640" cellpadding="0" cellspacing="0" border="0">
							<tr>
								<td align="left" valign="top">
									<div align="center">
										<p>
											<font face="Arial,Helvetica,Geneva,Swiss,SunSans-Regular"
												size="+3" color="#8b0000"><b>Teaching Style
													Survey</b></font><font
												face="Arial,Helvetica,Geneva,Swiss,SunSans-Regular"
												size="+3"><b><br> </b></font><font
												face="Arial,Helvetica,Geneva,Swiss,SunSans-Regular"><b>(Grasha-Riechmann)</b><br>
												<br> </font>
										</p>
									</div>
								</td>
						</table>
						<table style="width: 500px">
							<tr>
								<td>
									<div
										style="font-family: Arial, Helvetica, sans-serif; text-align: justify">
										The following is a Grasha-Riechmann teaching style survey.
										Respond to each of the items below in terms of how you teach.
										If you teach some courses differently than others, respond in
										terms only of one specific course. Fill out another survey for
										the course(s) that you teach in a different style. Try to
										answer as honestly and as objectively as you can. Resist the
										temptation to respond as you believe you should or ought to
										think or behave, or in terms of what you believe is the
										expected or proper thing to do.</div>
								</td>
							</tr>
						</table>
						<p>
							<font face="Arial,Helvetica,Geneva,Swiss,SunSans-Regular">Respond
								to questions below by using the following rating scale:</font>
						</p>
						<p></p>
						<b>1 = strongly disagree | 2 = moderately disagree | 3 =
							undecided |<br> 4 = moderately agree | 5 = strongly agree
						</b><br> <br>
						<form name="survey">
							<div align="center">
								<p></p>
								<table border="1" cellpadding="4" cellspacing="2" width="650"
									bgcolor="#faebd7">
									<tr>
										<td>
											<table width="500" border="1" bgcolor="#faebd7">
												<tr>
													<td width="3%" align="right" valign="top">1.</td>
													<td width="80%">Facts, concepts, and principles are
														the most important things that students should acquire.</td>
													<td width="17%" align="center"><font size="-1"><b>Response:</b></font>
														<input style="width:10px;" type="text" name="q1" maxlength="1" size="1"
														onblur="checkentry(document.survey.q1.value)"/></td>
												</tr>
												<tr>
													<td align="right" valign="top" width="3%">2.</td>
													<td width="80%">I set high standards for students in
														this class..</td>
													<td align="center" width="17%"><font size="-1"><b>Response:</b></font>
														<input style="width:10px;" type="text" name="q2" maxlength="1" size="1"
														onblur="checkentry(document.survey.q2.value)"/></td>
												</tr>
												<tr>
													<td align="right" valign="top" width="3%">3.</td>
													<td width="80%">What I say and do models appropriate
														ways for students to think about issues in the content.</td>
													<td align="center" width="17%"><font size="-1"><b>Response:</b></font>
														<input style="width:10px;" type="text" name="q3" maxlength="1" size="1"
														onblur="checkentry(document.survey.q3.value)"/></td>
												</tr>
												<tr>
													<td align="right" valign="top" width="3%">4.</td>
													<td width="80%">My teaching goals and methods address
														a variety of student learning styles.</td>
													<td align="center" width="17%"><font size="-1"><b>Response:</b></font>
														<input style="width:10px;" type="text" name="q4" maxlength="1" size="1"
														onblur="checkentry(document.survey.q4.value)"/></td>
												</tr>
												<tr>
													<td align="right" valign="top" width="3%">5.</td>
													<td width="80%">Students typically work on course
														projects alone with little supervision from me.</td>
													<td align="center" width="17%"><font size="-1"><b>Response:</b></font>
														<input style="width:10px;" type="text" name="q5" maxlength="1" size="1"
														onblur="checkentry(document.survey.q5.value)"/></td>
												</tr>
												<tr>
													<td align="right" valign="top" width="3%">6.</td>
													<td width="80%">Sharing my knowledge and expertise
														with students is very important to me.</td>
													<td align="center" width="17%"><font size="-1"><b>Response:</b></font>
														<input style="width:10px;" type="text" name="q6" maxlength="1" size="1"
														onblur="checkentry(document.survey.q6.value)"/></td>
												</tr>
												<tr>
													<td align="right" valign="top" width="3%">7.</td>
													<td width="80%">I give students negative feedback when
														their performance is unsatisfactory.</td>
													<td align="center" width="17%"><font size="-1"><b>Response:</b></font>
														<input style="width:10px;" type="text" name="q7" maxlength="1" size="1"
														onblur="checkentry(document.survey.q7.value)"/></td>
												</tr>
												<tr>
													<td align="right" valign="top" width="3%">8.</td>
													<td width="80%">Activities in this class encourage
														students to develop their own ideas about content issues.</td>
													<td align="center" width="17%"><font size="-1"><b>Response:</b></font>
														<input style="width:10px;" type="text" name="q8" maxlength="1" size="1"
														onblur="checkentry(document.survey.q8.value)"/></td>
												</tr>
												<tr>
													<td align="right" valign="top" width="3%">9.</td>
													<td width="80%">I spend time consulting with students
														on how to improve their work on individual and/or group
														projects.</td>
													<td align="center" width="17%"><font size="-1"><b>Response:</b></font>
														<input style="width:10px;" type="text" name="q9" maxlength="1" size="1"
														onblur="checkentry(document.survey.q9.value)"/></td>
												</tr>
												<tr>
													<td align="right" valign="top" width="3%">10.</td>
													<td width="80%">Activities in this class encourage
														students to develop their own ideas about content issues.</td>
													<td align="center" width="17%"><font size="-1"><b>Response:</b></font>
														<input style="width:10px;" type="text" name="q10" maxlength="1" size="1"
														onblur="checkentry(document.survey.q10.value)"/></td>
												</tr>
												<tr>
													<td align="right" valign="top" width="3%">11.</td>
													<td width="80%">What I have to say about a topic is
														important for students to acquire a broader perspective on
														the issues in that area.</td>
													<td align="center" width="17%"><font size="-1"><b>Response:</b></font>
														<input style="width:10px;" type="text" name="q11" maxlength="1" size="1"
														onblur="checkentry(document.survey.q11.value)"/></td>
												</tr>
												<tr>
													<td align="right" valign="top" width="3%">12.</td>
													<td width="80%">Students would describe my standards
														and expectations as somewhat strict and rigid.</td>
													<td align="center" width="17%"><font size="-1"><b>Response:</b></font>
														<input style="width:10px;" type="text" name="q12" maxlength="1" size="1"
														onblur="checkentry(document.survey.q12.value)"/></td>
												</tr>
												<tr>
													<td align="right" valign="top" width="3%">13.</td>
													<td width="80%">I typically show students how and what
														to do in order to master course content.</td>
													<td align="center" width="17%"><font size="-1"><b>Response:</b></font>
														<input style="width:10px;" type="text" name="q13" maxlength="1" size="1"
														onblur="checkentry(document.survey.q13.value)"/></td>
												</tr>
												<tr>
													<td align="right" valign="top" width="3%">14.</td>
													<td width="80%">Small group discussions are employed
														to help students develop their ability to think
														critically.</td>
													<td align="center" width="17%"><font size="-1"><b>Response:</b></font>
														<input style="width:10px;" type="text" name="q14" maxlength="1" size="1"
														onblur="checkentry(document.survey.q14.value)"/></td>
												</tr>
												<tr>
													<td align="right" valign="top" width="3%">15.</td>
													<td width="80%">Students design one of more
														self-directed learning experiences.</td>
													<td align="center" width="17%"><font size="-1"><b>Response:</b></font>
														<input style="width:10px;" type="text" name="q15" maxlength="1" size="1"
														onblur="checkentry(document.survey.q15.value)"/></td>
												</tr>
												<tr>
													<td align="right" valign="top" width="3%">16.</td>
													<td width="80%">I want students to leave this course
														well prepared for further work in this area.</td>
													<td align="center" width="17%"><font size="-1"><b>Response:</b></font>
														<input style="width:10px;" type="text" name="q16" maxlength="1" size="1"
														onblur="checkentry(document.survey.q16.value)"/></td>
												</tr>
												<tr>
													<td align="right" valign="top" width="3%">17.</td>
													<td width="80%">It is my responsibility to define what
														students must learn and how they should learn it.</td>
													<td align="center" width="17%"><font size="-1"><b>Response:</b></font>
														<input style="width:10px;" type="text" name="q17" maxlength="1" size="1"
														onblur="checkentry(document.survey.q17.value)"/></td>
												</tr>
												<tr>
													<td align="right" valign="top" width="3%">18.</td>
													<td width="80%">Examples from my personal experiences
														often are used to illustate points about the material.</td>
													<td align="center" width="17%"><font size="-1"><b>Response:</b></font>
														<input style="width:10px;" type="text" name="q18" maxlength="1" size="1"
														onblur="checkentry(document.survey.q18.value)"/></td>
												</tr>
												<tr>
													<td align="right" valign="top" width="3%">19.</td>
													<td width="80%">I guide students' work on course
														projects by asking questions, exploring options, and
														suggesting alternative ways to do things.</td>
													<td align="center" width="17%"><font size="-1"><b>Response:</b></font>
														<input style="width:10px;" type="text" name="q19" maxlength="1" size="1"
														onblur="checkentry(document.survey.q19.value)"/></td>
												</tr>
												<tr>
													<td align="right" valign="top" width="3%">20.</td>
													<td width="80%">Developing the ability of students to
														think and work independently is an important goal.</td>
													<td align="center" width="17%"><font size="-1"><b>Response:</b></font>
														<input style="width:10px;" type="text" name="q20" maxlength="1" size="1"
														onblur="checkentry(document.survey.q20.value)"/></td>
												</tr>
											</table>
										</td>
										<td bgcolor="#ffffe8"><b><font size="-1">1 =
													strongly disagree </font></b>
											<p>
												<b><font size="-1">2 = moderately disagree </font></b>
											</p>
											<p>
												<b><font size="-1">3 = undecided</font></b>
											</p>
											<p>
												<b><font size="-1">4 = moderately agree </font></b>
											</p>
											<p>
												<b><font size="-1">5 = strongly agree<br> <br>
														<br> <br> <br> <br> <br> <br>
														<br> 1 = strongly disagree
												</font></b>
											</p>
											<p>
												<b><font size="-1">2 = moderately disagree </font></b>
											</p>
											<p>
												<b><font size="-1">3 = undecided</font></b>
											</p>
											<p>
												<b><font size="-1">4 = moderately agree </font></b>
											</p>
											<p>
												<b><font size="-1">5 = strongly agree<br> <br>
														<br> <br> <br> <br> <br> <br>
														1 = strongly disagree
												</font></b>
											</p>
											<p>
												<b><font size="-1">2 = moderately disagree </font></b>
											</p>
											<p>
												<b><font size="-1">3 = undecided</font></b>
											</p>
											<p>
												<b><font size="-1">4 = moderately agree </font></b>
											</p>
											<p>
												<b><font size="-1">5 = strongly agree</font></b>
											</p></td>
									</tr>
								</table>
								<p>
									<b>1 = strongly disagree | 2 = moderately disagree | 3 =
										undecided |<br> 4 = moderately agree | 5 = strongly agree
									</b>
								</p>
								<p></p>
								<table border="1" cellpadding="6" cellspacing="2" width="650"
									bgcolor="#faebd7">
									<tr>
										<td>
											<table width="500" border="1" bgcolor="#faebd7">
												<tr>
													<td width="3%" align="right" valign="top">21.</td>
													<td width="80%">Lecturing is a significant part of how
														I teach each of the class sessions.</td>
													<td width="17%" align="center"><font size="-1"><b>Response:</b></font>
														<input style="width:20px;" type="text" name="q21" maxlength="1" size="1"
														onblur="checkentry(document.survey.q21.value)"/></td>
												</tr>
												<tr>
													<td align="right" valign="top" width="3%">22.</td>
													<td width="80%">I provide very clear guidelines for
														how I want tasks completed in this course.</td>
													<td align="center" width="17%"><font size="-1"><b>Response:</b></font>
														<input style="width:20px;" type="text" name="q22" maxlength="1" size="1"
														onblur="checkentry(document.survey.q22.value)"/></td>
												</tr>
												<tr>
													<td align="right" valign="top" width="3%">23.</td>
													<td width="80%">I often show students how they can use
														various principles and concepts.</td>
													<td align="center" width="17%"><font size="-1"><b>Response:</b></font>
														<input style="width:20px;" type="text" name="q23" maxlength="1" size="1"
														onblur="checkentry(document.survey.q23.value)"/></td>
												</tr>
												<tr>
													<td align="right" valign="top" width="3%">24.</td>
													<td width="80%">Course activities encourage students
														to take initiative and responsibility for their learning.</td>
													<td align="center" width="17%"><font size="-1"><b>Response:</b></font>
														<input style="width:20px;" type="text" name="q24" maxlength="1" size="1"
														onblur="checkentry(document.survey.q24.value)"/></td>
												</tr>
												<tr>
													<td align="right" valign="top" width="3%">25.</td>
													<td width="80%">Students take responsibility for
														teaching part of the class sessions.</td>
													<td align="center" width="17%"><font size="-1"><b>Response:</b></font>
														<input style="width:20px;" type="text" name="q25" maxlength="1" size="1"
														onblur="checkentry(document.survey.q25.value)"/></td>
												</tr>
												<tr>
													<td align="right" valign="top" width="3%">26.</td>
													<td width="80%">My expertise is typically used to
														resolve disagreements about content issues.</td>
													<td align="center" width="17%"><font size="-1"><b>Response:</b></font>
														<input style="width:20px;" type="text" name="q26" maxlength="1" size="1"
														onblur="checkentry(document.survey.q26.value)"/></td>
												</tr>
												<tr>
													<td align="right" valign="top" width="3%">27.</td>
													<td width="80%">This course has very specific goals
														and objectives that I want to accomplish.</td>
													<td align="center" width="17%"><font size="-1"><b>Response:</b></font>
														<input style="width:20px;" type="text" name="q27" maxlength="1" size="1"
														onblur="checkentry(document.survey.q27.value)"/></td>
												</tr>
												<tr>
													<td align="right" valign="top" width="3%">28.</td>
													<td width="80%">Students receive frequent verbal
														and/or written comments on their performance.</td>
													<td align="center" width="17%"><font size="-1"><b>Response:</b></font>
														<input style="width:20px;" type="text" name="q28" maxlength="1" size="1"
														onblur="checkentry(document.survey.q28.value)"/></td>
												</tr>
												<tr>
													<td align="right" valign="top" width="3%">29.</td>
													<td width="80%">I solicit student advice about how and
														what to teach in this course.</td>
													<td align="center" width="17%"><font size="-1"><b>Response:</b></font>
														<input style="width:20px;" type="text" name="q29" maxlength="1" size="1"
														onblur="checkentry(document.survey.q29.value)"/></td>
												</tr>
												<tr>
													<td align="right" valign="top" width="3%">30.</td>
													<td width="80%">Students set their own pace for
														completing independent and/or group projects.</td>
													<td align="center" width="17%"><font size="-1"><b>Response:</b></font>
														<input style="width:20px;" type="text" name="q30" maxlength="1" size="1"
														onblur="checkentry(document.survey.q30.value)"/></td>
												</tr>
												<tr>
													<td align="right" valign="top" width="3%">31.</td>
													<td width="80%">Students might describe me as a
														&quot;storehouse of knowledge&quot; who dispenses the
														fact, principles, and concepts they need.</td>
													<td align="center" width="17%"><font size="-1"><b>Response:</b></font>
														<input style="width:20px;" type="text" name="q31" maxlength="1" size="1"
														onblur="checkentry(document.survey.q31.value)"/></td>
												</tr>
												<tr>
													<td align="right" valign="top" width="3%">32.</td>
													<td width="80%">My expectations for what I want
														students to do in this class are clearly defined in the
														syllabus.</td>
													<td align="center" width="17%"><font size="-1"><b>Response:</b></font>
														<input style="width:20px;" type="text" name="q32" maxlength="1" size="1"
														onblur="checkentry(document.survey.q32.value)"/></td>
												</tr>
												<tr>
													<td align="right" valign="top" width="3%">33.</td>
													<td width="80%">Eventually, many students begin to
														think like me about course content.</td>
													<td align="center" width="17%"><font size="-1"><b>Response:</b></font>
														<input style="width:20px;" type="text" name="q33" maxlength="1" size="1"
														onblur="checkentry(document.survey.q33.value)"/></td>
												</tr>
												<tr>
													<td align="right" valign="top" width="3%">34.</td>
													<td width="80%">Students can make choices among
														activities in order to complete course requirements.</td>
													<td align="center" width="17%"><font size="-1"><b>Response:</b></font>
														<input style="width:20px;" type="text" name="q34" maxlength="1" size="1"
														onblur="checkentry(document.survey.q34.value)"/></td>
												</tr>
												<tr>
													<td align="right" valign="top" width="3%">35.</td>
													<td width="80%">My approach to teaching is similar to
														a manager of a work group who delegates tasks and
														responsibilities to subordinates.</td>
													<td align="center" width="17%"><font size="-1"><b>Response:</b></font>
														<input style="width:20px;" type="text" name="q35" maxlength="1" size="1"
														onblur="checkentry(document.survey.q35.value)"/></td>
												</tr>
												<tr>
													<td align="right" valign="top" width="3%">36.</td>
													<td width="80%">There is more material in this course
														than I have time available to cover it.</td>
													<td align="center" width="17%"><font size="-1"><b>Response:</b></font>
														<input style="width:20px;" type="text" name="q36" maxlength="1" size="1"
														onblur="checkentry(document.survey.q36.value)"/></td>
												</tr>
												<tr>
													<td align="right" valign="top" width="3%">37.</td>
													<td width="80%">My standards and expectations help
														students develop the discipline the need to learn.</td>
													<td align="center" width="17%"><font size="-1"><b>Response:</b></font>
														<input style="width:20px;" type="text" name="q37" maxlength="1" size="1"
														onblur="checkentry(document.survey.q37.value)"/></td>
												</tr>
												<tr>
													<td align="right" valign="top" width="3%">38.</td>
													<td width="80%">Students might describe me as a
														&quot;coach&quot; who works closely with someone to
														correct problems in how they think and behave.</td>
													<td align="center" width="17%"><font size="-1"><b>Response:</b></font>
														<input style="width:20px;" type="text" name="q38" maxlength="1" size="1"
														onblur="checkentry(document.survey.q38.value)"/></td>
												</tr>
												<tr>
													<td align="right" valign="top" width="3%">39.</td>
													<td width="80%">I give students a lot of personal
														support and encouragement to do well in this course.</td>
													<td align="center" width="17%"><font size="-1"><b>Response:</b></font>
														<input style="width:20px;" type="text" name="q39" maxlength="1" size="1"
														onblur="checkentry(document.survey.q39.value)"/></td>
												</tr>
												<tr>
													<td align="right" valign="top" width="3%">40.</td>
													<td width="80%">I assume the role of a resource person
														who is available to students whenever they need help.</td>
													<td align="center" width="17%"><font size="-1"><b>Response:</b></font>
														<input style="width:20px;" type="text" name="q40" maxlength="1" size="1"
														onblur="checkentry(document.survey.q40.value)"/></td>
												</tr>
											</table>
										</td>
										<td bgcolor="#ffffe8"><b><font size="-1">1 =
													strongly disagree </font></b>
											<p>
												<b><font size="-1">2 = moderately disagree </font></b>
											</p>
											<p>
												<b><font size="-1">3 = undecided</font></b>
											</p>
											<p>
												<b><font size="-1">4 = moderately agree </font></b>
											</p>
											<p>
												<b><font size="-1">5 = strongly agree<br> <br>
														<br> <br> <br> <br> <br> <br>
														<br> 1 = strongly disagree
												</font></b>
											</p>
											<p>
												<b><font size="-1">2 = moderately disagree </font></b>
											</p>
											<p>
												<b><font size="-1">3 = undecided</font></b>
											</p>
											<p>
												<b><font size="-1">4 = moderately agree </font></b>
											</p>
											<p>
												<b><font size="-1">5 = strongly agree<br> <br>
														<br> <br> <br> <br> <br> <br>
														1 = strongly disagree
												</font></b>
											</p>
											<p>
												<b><font size="-1">2 = moderately disagree </font></b>
											</p>
											<p>
												<b><font size="-1">3 = undecided</font></b>
											</p>
											<p>
												<b><font size="-1">4 = moderately agree </font></b>
											</p>
											<p>
												<b><font size="-1">5 = strongly agree</font></b>
											</p></td>
									</tr>
								</table>
								<p></p>
							</div>
							<p>
								<font size="-3"
									face="Arial,Helvetica,Geneva,Swiss,SunSans-Regular">Copyright
									1976, 1987, 1990, 1996 by Anthony F. Grasha and Sheryl
									Riechmann-Hruska, University of Cincinnati, Cincinnati, Oh
									45221</font>
							</p>
							<p></p>
							<p align="center">
								<font face="Arial,Helvetica,Geneva,Swiss,SunSans-Regular">Click
									&quot;Score Survey &quot; and your results will appear below.</font>
							</p>
							<div align="center">
								<p>
									<input type="button" value="Score Survey"
										onclick="check_answers()">
									<!--<input type="reset" name="reset" value="Clear Survey">-->
								</p>
							</div>
							<center>
								<b><font face="Verdana, Arial, Helvetica, sans-serif">The
										results of your teaching style survey are as follows:</font></b>
							</center>
						</form>
					</div>
					<p>
					<form name="results" action="/" method="POST" enctype="utf8">
						<div align="center">
							<input type="hidden" name="subject" value="Teaching Style Survey">
							<table border=1 bgcolor="#faebd7">
								<tr>
									<td align="center"><input style="width:100px;" type="text" name="expert"
										size="3" maxlength="3" align="middle"><br> <font
										face="Verdana, Arial, Helvetica, sans-serif" size="-1">expert</font><br>
										<b><input style="width:100px;" type="text" name="expert_range" size="13"
											maxlength="13"> </b></td>
									<td align="center"><input style="width:100px;" type="text"
										name="formalauthority" size="3" maxlength="3" align="middle"><br>
										<font size="-1" face="Verdana, Arial, Helvetica, sans-serif">formalauthority</font><br>
										<b><input style="width:100px;" type="text" name="formalauthority_range"
											size="13" maxlength="13"> </b></td>
									<td align="center"><input style="width:100px;" type="text" name="personalmodel"
										size="3" maxlength="3"><br> <font size="-1"
										face="Verdana, Arial, Helvetica, sans-serif">personalmodel</font><br>
										<b><input style="width:100px;" type="text" name="personalmodel_range" size="13"
											maxlength="13"> </b></td>
									<td align="center"><input style="width:100px;" type="text" name="facilitator"
										size="3" maxlength="3"><br> <font size="-1"
										face="Verdana, Arial, Helvetica, sans-serif">facilitator</font><br>
										<b><input style="width:100px;" type="text" name="facilitator_range" size="13"
											maxlength="13"> </b></td>
									<td align="center"><input style="width:100px;" type="text" name="delegator"
										size="3" maxlength="3"><br> <font size="-1"
										face="Verdana, Arial, Helvetica, sans-serif">delegator</font><br>
										<b><input style="width:100px;" type="text" name="delegator_range" size="13"
											maxlength="13"> </b></td>
								</tr>
							</table>
						</div>
						<p>
							<!--Get name, email, and class-->
						</p>
						<div align="center">
							<p>
								<font face="Arial,Helvetica,Geneva,Swiss,SunSans-Regular"
									size="+1">Please provide the following information.</font><font
									face="Arial,Helvetica,Geneva,Swiss,SunSans-Regular" size="+1"><br>
								</font>
							</p>
							<table border="0" cellpadding="0" cellspacing="6" width="122" bgcolor="#faebd7">
								<tr valign="top" align="left">
									<td><font
										face="Arial,Helvetica,Geneva,Swiss,SunSans-Regular" size="-1">Discipline</font><font
										face="Verdana, Arial, Helvetica, sans-serif">: <select
											name="discipline">
												<option selected value="0"></option>
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
										</select></font></td>
									<td><font
										face="Arial,Helvetica,Geneva,Swiss,SunSans-Regular" size="-1">Level
											of Course: <select name="level">
												<option selected value="0"></option>
												<option value="Freshman">Freshman</option>
												<option value="Sophomore">Sophomore</option>
												<option value="Junior">Junior</option>
												<option value="Senior">Senior</option>
												<option value="Grad Student">Grad Student</option>
												<option value="Other">Other</option>
										</select>
									</font></td>
								</tr>
								<tr valign="top" align="left">
									<td><font
										face="Arial,Helvetica,Geneva,Swiss,SunSans-Regular" size="-1">Academic
											Rank:<select name="teacherrank">
												<option selected value="0"></option>
												<option value="Adjunct">Adjunct</option>
												<option value="Instructor">Instructor</option>
												<option value="Assistant Professor">Assistant Professor</option>
												<option value="Associate Professor">Associate Professor</option>
												<option value="Professor">Professor</option>
												<option value="Other">Other</option>
												<option value="Unknown">Unknown</option>
										</select>
									</font></td>
									<td><font
										face="Arial,Helvetica,Geneva,Swiss,SunSans-Regular" size="-1">Race:
											<select name="race">
												<option selected value="0"></option>
												<option value="African American">African American</option>
												<option value="White">White</option>
												<option value="Hispanic">Hispanic</option>
												<option value="Asian">Asian</option>
												<option value="Native American">Native American</option>
												<option value="Other">Other</option>
										</select>
									</font></td>
								</tr>
								<tr valign="top" align="left">
									<td><font
										face="Arial,Helvetica,Geneva,Swiss,SunSans-Regular" size="-1">Gender:
											<select name="gender">
												<option selected value="0"></option>
												<option value="Female">Female</option>
												<option value="Male">Male</option>
										</select>
									</font></td>
									<td></td>
								</tr>
							</table>
							<br>
						</div>

						<div align="center">
							<p class="signin button"> 
								<!--Submit form-->
								<INPUT NAME="submit" TYPE="submit" value="Submit">
							</p>
						</div>
					</form>

				</div>
			</div>
		</div>
	</div>

	<div class="clearfix"></div>

	<footer>
		<p>
			<span style="text-align: left; float: left">&copy; 2015 <a
				href="http://didattica.dia.uniroma3.it/http://didattica.dia.uniroma3.it/"
				alt="Roma Tre University">Roma Tre University</a></span>
		</p>
	</footer>
	
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
	<!-- <script src="/resources/js/retina.js"></script>-->
	<script src="/resources/js/custom.js"></script>
	<script src="/resources/js/commons.js"></script>
	
	<script type="text/javascript">
		$(document).ready(function () {
			$('form[name="results"]').submit(function(event) {
				register(event);
			});
		});
		
		function register(event){
			event.preventDefault();
			
			var chk = empty_fields();
			if(!chk)
				return;
			
		    var formData= $('form[name="results"]').serialize();
		    var surveyData= $('form[name="survey"]').serialize();
		    formData = formData + '&' +surveyData;
		    
		    $.post("<c:url value="/user/saveTeachingStyle"/>", formData, function(data){
		        if(data.message == "success"){
		        	messageDialog('Operation done', 'Teaching style informations correctly saved.');
		        }
		    })
		    .fail(function(data) {
		    	if(data.responseJSON.error != null && data.responseJSON.message != null){
		    		alert(data.responseJSON.message);
		    	}
		    });
		}
	</script>
	
</body>
</html>
