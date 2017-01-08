<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="header.jsp" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Create Employee</title>
<link href="<c:url value="/resources/stylesheets/style.css" />" rel="stylesheet">
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css">
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
<link href="<c:url value="/resources/stylesheets/bootstrap-responsive.css" />" rel="stylesheet">
<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
<script src="http://code.jquery.com/ui/1.9.2/jquery-ui.js"></script>
<link href="http://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.11.4/jquery-ui.css" rel="stylesheet" >

<script type="text/javascript">
   
    $(document).ready(function() {
    	$("#billingStartDate").datepicker({dateFormat : 'yy-mm-dd'});
        $("#billingEndDate").datepicker({dateFormat : 'yy-mm-dd'});
      $('#createEmployee').submit(function(event) {
    	  
    	  var empName = $('#empName').val();
    	  var empNumber = $('#empNumber').val();
    	  var billingStartDate = $('#billingStartDate').val();
    	  var billingEndDate = $('#billingEndDate').val();
    	  var won = $('#won').val();
    	  var email = $('#email').val();
    	  var phone = $('#phone').val();
    	  var unitPrice = $('#unitPrice').val();
    	  var activeStatus = "true";
    	  var json = {"empName" : empName, 
    			  "empNumber" : empNumber, 
    			  "billingStartDate": billingStartDate, 
    			  "billingEndDate" : billingEndDate,
    			  "won" : won,
    			  "email" : email,
    			  "phone" : phone,
    			  "unitPrice" : unitPrice,
    			  "activeStatus" : activeStatus};
    	  
        $.ajax({
        	url: $("#createEmployee").attr( "action"),
        	data: JSON.stringify(json),
        	type: "POST",
        	
        	beforeSend: function(xhr) {
        		xhr.setRequestHeader("Accept", "application/json");
        		xhr.setRequestHeader("Content-Type", "application/json");
        	},
        	success: function() {
        		var respContent = "";
		  		respContent += "<span class='success'>" + ${message} + "</span>";
        		$("#employeeFromResponse").html(respContent);   		
        	}
        });
         
        event.preventDefault();
      });
       
    });   
  </script>
</head>
<body>
<div id = "container">
<div id="employeeForm">
<div id="employeeFromResponse"></div>  
<form:form id="createEmployee" action="${pageContext.request.contextPath}/v1/createEmployee" commandName="employee">
<table>
<tbody>
<tr>
<td>Employee name : </td><td><form:input path="empName" /></td>
</tr>
<tr>
<td>Employee number : </td><td><form:input path="empNumber" /></td>
</tr>
<tr>
<td>Billing Start Date : </td><td><form:input path="billingStartDate" id = "billingStartDate"/></td>
</tr>
<tr>
<td>Billing End Date : </td><td><form:input path="billingEndDate" id = "billingEndDate"/></td>
</tr>
<tr>
<td>WON number : </td><td><form:input path="won" /></td>
</tr>
<tr>
<td>Email Id : </td><td><form:input path="email" /></td>
</tr>
<tr>
<td>Phone number : </td><td><form:input path="phone" /></td>
</tr>
<tr>
<td>Unit price : </td><td><form:input path="unitPrice" /></td>
</tr>
<tr>
<td><input type="submit" value="Create" /></td><td><input type="reset" value="Reset"></td>
</tr>
</tbody>
</table>
</form:form>
<!--  <a href="${pageContext.request.contextPath}/index.html">Home page</a> -->
</div>
</div>
</body>
</html>