var beursControllers = angular.module('beursControllers', []);

beursControllers.controller('beursController', ['$scope', '$window', function ($scope, $window) {
	$scope.is_backend_ready = false;
	$scope.drinks = [];
	$scope.update = function(drink) {
		gapi.client.drinkService.add(drink).execute(function(){
			listDrinks();
		});
		
	};
	$scope.deleteDrink = function(id) {
		gapi.client.drinkService.deleteById({'id': id}).execute();
		listDrinks();
	};
	$scope.createNew = function() {
		var emptyDrink;
		$scope.drinks.push(emptyDrink);
	};
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
	beursControllers.directive('beurs-item', function(){
		return {
			restrict : 'E',
			templateUrl : 'beurs-item.html'
		};
	});
	beursControllers.directive('bert', function(){
		return {
			restrict : 'E',
			templateUrl : 'html/beurs-item.html'
		};
	});
	
	
	function serviceInit(){
	    angular.element(document).ready(function() {
	    	window.init();
	    });
	}