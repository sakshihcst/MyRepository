<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Create WON</title>
<link href="<c:url value="/resources/stylesheets/style.css" />" rel="stylesheet">
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css">
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
<link href="<c:url value="/resources/stylesheets/bootstrap-responsive.css" />" rel="stylesheet">
<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
<script src="http://code.jquery.com/ui/1.9.2/jquery-ui.js"></script>
<link href="http://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.11.4/jquery-ui.css" rel="stylesheet" >

<script type="text/javascript">
   
    $(document).ready(function() {
    	$("#wonStartDate").datepicker({dateFormat : 'yy-mm-dd'});
        $("#wonEndDate").datepicker({dateFormat : 'yy-mm-dd'});
      $('#createWON').submit(function(event) {
    	  
    	  var won = $('#won').val();
    	  var wonStartDate = $('#wonStartDate').val();
    	  var wonEndDate = $('#wonEndDate').val();
    	  var wonOwnerId = $('#wonOwnerId').val();
    	  var wonLocation = $('#wonLocation').val();
    	  var sowNumber = $('#sowNumber').val();
    	  var activeStatus = "true";
    	  var json = {"won" : won,
    			  "wonStartDate": wonStartDate, 
    			  "wonEndDate" : wonEndDate,
    			  "wonOwnerId" : wonOwnerId,
    			  "wonLocation" : wonLocation,
    			  "activeStatus" : activeStatus,
    			  "sowNumber" : sowNumber};
    	  
        $.ajax({
        	url: $("#createWON").attr( "action"),
        	data: JSON.stringify(json),
        	type: "POST",
        	
        	beforeSend: function(xhr) {
        		xhr.setRequestHeader("Accept", "application/json");
        		xhr.setRequestHeader("Content-Type", "application/json");
        	},
        	success: function() {
        		var respContent = "";
		  		respContent += "<span class='success'>" + ${message} + "</span>";
        		$("#wonFromResponse").html(respContent);   		
        	}
        });
         
        event.preventDefault();
      });
       
    });   
  </script>
</head>
<body>
<div id = "container">
<div id="wonForm">
<div id="wonFromResponse"></div>  
<form:form id="createWON" action="${pageContext.request.contextPath}/v1/createWON" commandName="wonDetails">
<table>
<tbody>
<tr>
<td>WON# : </td><td><form:input path="won" /></td>
</tr>
<tr>
<td>WON Start Date : </td><td><form:input path="wonStartDate" id = "wonStartDate"/></td>
</tr>
<tr>
<td>WON End Date : </td><td><form:input path="wonEndDate" id = "wonEndDate"/></td>
</tr>
<tr>
<td>WON owner id : </td><td><form:input path="wonOwnerId" /></td>
</tr>
<tr>
<td>WON location : </td><td><form:input path="wonLocation" /></td>
</tr>
<tr>
<td>SOW Number : </td><td><form:input path="sowNumber" /></td>
</tr>
<tr>
<td><input type="submit" value="Create" /></td><td><input type="reset" value="Reset"></td>
</tr>
</tbody>
</table>
</form:form>
</div>
</div>
</body>
</html>