
var kassaController = angular.module('kassaController', []);

kassaController.controller('kassaController', ['$scope', '$window', function ($scope, $window) {
	$scope.drinks = [];

	$scope.load_drinkservice_lib = function() {
		gapi.client.load('drinkService', 'v1', function(){
			  $scope.is_backend_ready = true;
			  $scope.$apply();
		}, '/_ah/api');
	};
	
	$scope.listDrinks = function() {
        gapi.client.drinkService.getAllDrinks().execute(function(resp) {
                if (!resp.code) {
                		$scope.drinks = resp.items ;
                		$scope.$apply();
                }
	     });
	};

	$scope.sendOrder = function() {
		alert('sendOrder');
	    var path = '/order'+ '?order=' + 'bert';
	    var xhr = new XMLHttpRequest();
	    xhr.open('POST', path, true);
	    xhr.send();
	};
}]);