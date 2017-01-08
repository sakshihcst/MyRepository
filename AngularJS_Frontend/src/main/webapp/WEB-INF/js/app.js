var app = angular.module('myApp',['ngRoute']);

app.config(function ($routeProvider) {
	$routeProvider
	.when('/login', {
		templateUrl: '/views/login.html',
		controller: 'loginController'
	})
	.when('/register', {
		templateUrl: '/views/register.html',
		controller: 'registerController'
	})
	.otherwise({ redirectTo: '/' });
});