<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<c:url value="/resources/stylesheets/style.css" />" rel="stylesheet">
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css">
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
<link href="<c:url value="/resources/stylesheets/bootstrap-responsive.css" />" rel="stylesheet">
<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
<script src="http://code.jquery.com/ui/1.9.2/jquery-ui.js"></script>
<link href="http://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.11.4/jquery-ui.css" rel="stylesheet" >
</head>
<body>
<div id = "reportsHeader">
	<div class="navbar navbar-default navbar-static-top" role="navigation" bs-navbar>
	  <div class="navbar-header">
	    <div class="navbar-brand">
	      <h1 style="margin-top: 0px;margin-bottom: 0px;font-size: 20px;"><i class="fa fa-university"></i>
	      <strong>Apple Reporting Dashboard</strong></h1>
	    </div>
	  </div>
	  <ul class="nav navbar-nav">
      <li id="employeeTab"><a href="${pageContext.request.contextPath}/">Employee Details</a></li>
      <li id="wonTab"><a href="${pageContext.request.contextPath}/v1/won">WON Details</a></li>
      <li id="sowTab"><a href="${pageContext.request.contextPath}/v1/sow">SOW Details</a></li>
      <li id="billingTab"><a href="${pageContext.request.contextPath}/v1/billing">Billing</a></li>
      <li id="shippingTab"><a href="${pageContext.request.contextPath}/v1/shipping">Shipping</a></li>
      <li id="generateReportTab"><a href="${pageContext.request.contextPath}/v1/generateReports">Generate Reports</a></li>
      <li id="verifyInvoiceTab"><a href="${pageContext.request.contextPath}/v1/verifyInvoice">Verify Invoice</a></li>
	  </ul>
	</div>
</div>
</body>
</html>