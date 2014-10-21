var beursApp = angular.module('beursApp', []);

beursApp.controller('beursController', function ($scope, $window) {
	$window.init= function() {
		  $scope.$apply($scope.load_drinkservice_lib);
	};

	$scope.load_drinkservice_lib = function() {
		  gapi.client.load('drinkService', 'v1', function() {
		    $scope.is_backend_ready = true;
		  });
		};
  this.drinks = [
    {'name': 'Jupiler',
     'price': '1.33',
     'soldOut': 'false',
     'imageUrl': 'images/pils_small.jpg'
     },
	 {'name': 'Kwaremont',
	 'price': '2.33',
	 'soldOut': 'false',
	 'imageUrl': 'images/kwaremont_small.jpg'
	 },
	 {'name': 'Vedette',
	 'price': '1.42',
	 'soldOut': 'false',
	 'imageUrl': 'images/vedette_small.jpg'
	 }
  ];
});

function init() {
	window.init();
}
