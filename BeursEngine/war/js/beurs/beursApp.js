var beursApp = angular.module('beursApp', [])

	.controller('beursController', ['$scope', '$window', function ($scope, $window) {
	
		$scope.is_backend_ready = false;
		this.drinks = [];
		$window.init= function() {
			$scope.$apply($scope.load_drinkservice_lib);
		};
	
		$scope.load_drinkservice_lib = function() {
			gapi.client.load('drinkService', 'v1', function(){
				  $scope.is_backend_ready = true;
				  $scope.$apply();
				  listDrinks();
			}, '/_ah/api');
		};
		
		function listDrinks() {
	        gapi.client.drinkService.getAllDrinks().execute(function(resp) {
	                if (!resp.code) {
	                		$scope.drinks = resp.items ;
	                		$scope.$apply();
	                }
	        });
		};
	}]);
	beursApp.directive('beurs-item', function(){
		return {
			restrict : 'E',
			templateUrl : 'beurs-item.html'
		};
	});
	beursApp.directive('bert', function(){
		return {
			restrict : 'E',
			templateUrl : 'beurs-item.html'
		};
	});

function serviceInit(){
    angular.element(document).ready(function() {
    window.init();
     });
}
