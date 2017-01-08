var app = angular.module('billingApp',['ngRoute','ngResource']);
app.config(function($routeProvider) {
  $routeProvider
  .when('/', {
    templateUrl: 'views/main.html'
    //controller: 'mainController'
  })
  .when('/employee', {
    templateUrl: 'employee.jsp'
    //controller: 'loginController'
  }).when('/won', {
	  templateUrl: 'views/won.jsp'
    //controller: 'signupController'
  })
  .when('/sow', {
    templateUrl: 'views/sow.jsp'
    //controller: 'fdController'
  })
  .when('/billing', {
    templateUrl: 'views/billing.jsp'
    //controller: 'profileController'
  })
  .when('/shipping', {
	  templateUrl: 'views/shipping.jsp'
    //controller: 'forgotController'
  })
  .when('/generateReport', {
    templateUrl: 'views/generateReport.jsp'
    //controller: 'portfolioController'
  }).when('/verifyInvoice', {
    templateUrl: 'views/verifyInvoice.jsp'
    //controller: 'tncController'
  })
  .otherwise({
    redirectTo: '/'
  });

});
