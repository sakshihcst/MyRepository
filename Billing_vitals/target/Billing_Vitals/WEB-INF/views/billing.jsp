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
<title>Create Billing Information</title>
<link href="<c:url value="/resources/stylesheets/style.css" />" rel="stylesheet">
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css">
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
<link href="<c:url value="/resources/stylesheets/bootstrap-responsive.css" />" rel="stylesheet">
<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
<script src="http://code.jquery.com/ui/1.9.2/jquery-ui.js"></script>
<link href="http://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.11.4/jquery-ui.css" rel="stylesheet" >

<script type="text/javascript">

    $(document).ready(function() {
      $('#createBilling').submit(function(event) {
    	  var customerOB10 = $('#customerOB10').val();
    	  var billToCustomerNumber = $('#billToCustomerNumber').val();
    	  var deliveryNoteNumber = $('#deliveryNoteNumber').val();
    	  var customerName = $('#customerName').val();
    	  var customerAddress = $('#customerAddress').val();
    	  var customerCity = $('#customerCity').val();
    	  var customerState = $('#customerState').val();
    	  var customerZipCode = $('#customerZipCode').val();
    	  var activeStatus = "true";
    	  var json = {"customerOB10" : customerOB10,
    			  "billToCustomerNumber" : billToCustomerNumber,
    			  "deliveryNoteNumber": deliveryNoteNumber, 
    			  "customerName" : customerName,
    			  "customerAddress" : customerAddress,
    			  "customerCity": customerCity, 
    			  "customerState" : customerState,
    			  "customerZipCode" : customerZipCode,
    			  "activeStatus" : activeStatus};
    	  
        $.ajax({
        	url: $("#createBilling").attr( "action"),
        	data: JSON.stringify(json),
        	type: "POST",
        	
        	beforeSend: function(xhr) {
        		xhr.setRequestHeader("Accept", "application/json");
        		xhr.setRequestHeader("Content-Type", "application/json");
        	},
        	success: function() {
        		var respContent = "";
		  		respContent += "<span class='success'>" + ${message} + "</span>";
        		$("#billingFromResponse").html(respContent);   		
        	}
        });
         
        event.preventDefault();
      });
       
    });   
  </script>
</head>
<body>
<div id = "container">
<div id="billingForm">
<div id="billingFromResponse"></div>  
<form:form id="createBilling" action="${pageContext.request.contextPath}/v1/createBilling" commandName="billingDetails">
<table>
<tbody>
<tr>
<td>Customer OB10 : </td><td><form:input path="customerOB10" /></td>
</tr>
<tr>
<td>Bill Number : </td><td><form:input path="billToCustomerNumber" /></td>
</tr>
<tr>
<td>Delivery Note Number : </td><td><form:input path="deliveryNoteNumber"/></td>
</tr>
<tr>
<td>Customer Name : </td><td><form:input path="customerName"/></td>
</tr>
<tr>
<td>Address : </td><td><form:input path="customerAddress" /></td>
</tr>
<tr>
<td>City : </td><td><form:input path="customerCity"/></td>
</tr>
<tr>
<td>State : </td><td><form:input path="customerState"/></td>
</tr>
<tr>
<td>ZipCode : </td><td><form:input path="customerZipCode" /></td>
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