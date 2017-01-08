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
<title>Create Shipping Information</title>
<link href="<c:url value="/resources/stylesheets/style.css" />" rel="stylesheet">
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css">
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
<link href="<c:url value="/resources/stylesheets/bootstrap-responsive.css" />" rel="stylesheet">
<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
<script src="http://code.jquery.com/ui/1.9.2/jquery-ui.js"></script>
<link href="http://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.11.4/jquery-ui.css" rel="stylesheet" >

<script type="text/javascript">

    $(document).ready(function() {
      $('#createShipping').submit(function(event) {
    	  var shipToName = $('#shipToName').val();
    	  var shipToAddress = $('#shipToAddress').val();
    	  var shipToCity = $('#shipToCity').val();
    	  var shipToState = $('#shipToState').val();
    	  var shipToZip = $('#shipToZip').val();
    	  var activeStatus = "true";
    	  var json = {"shipToName" : shipToName,
    			  "shipToAddress" : shipToAddress,
    			  "shipToCity": shipToCity, 
    			  "shipToState" : shipToState,
    			  "shipToZip" : shipToZip,
    			  "activeStatus" : activeStatus};
    	  
        $.ajax({
        	url: $("#createShipping").attr( "action"),
        	data: JSON.stringify(json),
        	type: "POST",
        	
        	beforeSend: function(xhr) {
        		xhr.setRequestHeader("Accept", "application/json");
        		xhr.setRequestHeader("Content-Type", "application/json");
        	},
        	success: function() {
        		var respContent = "";
		  		respContent += "<span class='success'>" + ${message} + "</span>";
        		$("#shippingFromResponse").html(respContent);   		
        	}
        });
         
        event.preventDefault();
      });
       
    });   
  </script>
</head>
<body>
<div id = "container">
<div id="shippingForm">
<div id="shippingFromResponse"></div>  
<form:form id="createShipping" action="${pageContext.request.contextPath}/v1/createShipping" commandName="shippingDetails">
<table>
<tbody>
<tr>
<td>Name : </td><td><form:input path="shipToName" /></td>
</tr>
<tr>
<td>Address : </td><td><form:input path="shipToAddress" /></td>
</tr>
<tr>
<td>City : </td><td><form:input path="shipToCity" /></td>
</tr>
<tr>
<td>State : </td><td><form:input path="shipToState"/></td>
</tr>
<tr>
<td>Zip : </td><td><form:input path="shipToZip" /></td>
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