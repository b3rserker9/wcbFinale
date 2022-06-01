<!DOCTYPE html>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
    uri="http://www.springframework.org/security/tags"%>
<%@ page session="false"%>
<html>
<head>
	<link href="/resources/css/bootstrap.min.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="/resources/css/progressbar.css" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="description" content="Wiki Course Builder">
	<meta name="author" content="Andrea Tarantini, Alessandra Milita">
	<meta name="keyword" content="Wikipedia, Roma Tre">
	<title><spring:message code="message.updatePassword"></spring:message></title>
</head>
<body>
	<sec:authorize access="hasRole('READ_PRIVILEGE')">
	    <div class="container">
	    	<div id="errormsg" class="alert alert-error" style="display:none"></div>
	        <div class="row">
	            <h1> <spring:message code="message.resetYourPassword"></spring:message> </h1>
	            <div style="width:50%;">
	                <br/>
	                    <label class="col-sm-2"><spring:message code="label.user.password"></spring:message></label>
	                    <span class="col-sm-5"><input class="form-control" style="width:100%;" id="pass" name="password" type="password" value="" /></span>
	                    <span class="col-sm-5"></span>
	                    <label id="errorPwd" class="alert alert-error" style="display:none">Please, insert a valid password</label>
					<br/><br/>
	                    <label class="col-sm-2"><spring:message code="label.user.confirmPass"></spring:message></label>
	                    <span class="col-sm-5"><input class="form-control" style="width:100%;" id="passConfirm" type="password" value="" /></span>
	                    <label id="error" class="alert alert-error" style="display:none"><spring:message code="PasswordMatches.user"></spring:message></label>
	                <br/><br/>
	                <button class="btn btn-primary" id="submit-btn" onClick="savePass()">
	                    <spring:message code="message.updatePassword"></spring:message>
	                </button>
	            </div>
	        </div>
	    </div>
	    
		<script src="/resources/js/jquery-1.11.1.min.js"></script>
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
			$("#errorPwd").hide();
			$("#error").hide();
			
		    var pass = $("#pass").val();
		    var passConfirm = $("#passConfirm").val();
		    
		    if(pass==""){
		    	$("#errorPwd").show();
		    	 return;
		    }
		    var valid = pass === passConfirm;
		    if(!valid) {
		      $("#error").show();
		      return;
		    }
		
		    $.post("<c:url value="/user/savePassword"></c:url>",{password: pass} ,function(data){
		        window.location.href = "<c:url value="/login.html"></c:url>" + "?message="+data.message;
			})
			.fail(function(data) {
				if(jQuery.parseJSON(data.responseText).message==="message.error")
					$("#errormsg").show().html('<b><h2><spring:message code="message.error"></spring:message></h2></b>');
				else
					$("#errormsg").show().html("<b><h2>"+jQuery.parseJSON(data.responseText).message+"</h2></b>");
			});
		}
		</script>
		
	</sec:authorize>
</body>
</html>