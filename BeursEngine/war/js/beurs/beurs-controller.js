var beursControllers = angular.module('beursController', []);

var token = "";

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
		gapi.client.load('channelService', 'v1', function(){
			getToken();
		}, '/_ah/api');
	};
	
	function getToken(){
		gapi.client.channelService.getToken().execute(function(resp) {
        	if (!resp.code) {
        		$scope.token = resp.items[0] ;
                $scope.$apply();
                openChannel($scope.token);
            }
	    });
	};

	function onOpened() {
        var path = '/opened';
        var xhr = new XMLHttpRequest();
        xhr.open('POST', path, true);
        xhr.send();
      };

      function onMessage(message) {
    	  var order = JSON.parse(message.data);
    	  for(var orderItem of order.orderItems){
    		  for(var beverage of $scope.drinks){
    			  if(beverage.id == orderItem.drink.id){
    				  beverage.price = orderItem.drink.price;
    			  }
    		  }
    	  }
    	  $scope.$apply();
      };
     
      function openChannel(token) {
        var channel = new goog.appengine.Channel(token);
        var handler = {
          'onopen': onOpened,
          'onmessage': onMessage,
          'onerror': function() {},
          'onclose': function() {}
        };
        var socket = channel.open(handler);
        socket.onopen = onOpened;
        socket.onmessage = onMessage;
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
	    window.init();
	};
	
