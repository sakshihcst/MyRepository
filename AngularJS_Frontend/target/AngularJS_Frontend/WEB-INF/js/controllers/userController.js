app.controller('loginController', ['$scope', function ($scope,$http) {

}]);

app.controller('registerController', ['$scope', function ($scope) {
	$http({
        method : "POST",
        url : "welcome.htm"
    }).then(function mySucces(response) {
        $scope.message = response.data;
    }, function myError(response) {
        $scope.message = response.statusText;
    });
}])