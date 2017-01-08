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
<title>Create SOW Information</title>
<link href="<c:url value="/resources/stylesheets/style.css" />" rel="stylesheet">
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css">
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
<link href="<c:url value="/resources/stylesheets/bootstrap-responsive.css" />" rel="stylesheet">
<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
<script src="http://code.jquery.com/ui/1.9.2/jquery-ui.js"></script>
<link href="http://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.11.4/jquery-ui.css" rel="stylesheet" >

<script type="text/javascript">
   
    $(document).ready(function() {
    	$("#sowStartDate").datepicker({dateFormat : 'yy-mm-dd'});
        $("#sowEndDate").datepicker({dateFormat : 'yy-mm-dd'});
      $('#createSOW').submit(function(event) {
    	  var sowNumber = $('#sowNumber').val();
    	  var sowName = $('#sowName').val();
    	  var sowStartDate = $('#sowStartDate').val();
    	  var sowEndDate = $('#sowEndDate').val();
    	  var purchaseOrderNumber = $('#purchaseOrderNumber').val();
    	  var activeStatus = "true";
    	  var json = {"sowNumber" : sowNumber,
    			  "sowName" : sowName,
    			  "sowStartDate": sowStartDate, 
    			  "sowEndDate" : sowEndDate,
    			  "purchaseOrderNumber" : purchaseOrderNumber,
    			  "activeStatus" : activeStatus};
    	  
        $.ajax({
        	url: $("#createSOW").attr( "action"),
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
<div id="sowForm">
<div id="sowFromResponse"></div>  
<form:form id="createSOW" action="${pageContext.request.contextPath}/v1/createSOW" commandName="sowDetails">
<table>
<tbody>
<tr>
<td>SOW Number : </td><td><form:input path="sowNumber" /></td>
</tr>
<tr>
<td>SOW Name : </td><td><form:input path="sowName" /></td>
</tr>
<tr>
<td>SOW Start Date : </td><td><form:input path="sowStartDate" id = "sowStartDate"/></td>
</tr>
<tr>
<td>SOW End Date : </td><td><form:input path="sowEndDate" id = "sowEndDate"/></td>
</tr>
<tr>
<td>Purchase Order# : </td><td><form:input path="purchaseOrderNumber" /></td>
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