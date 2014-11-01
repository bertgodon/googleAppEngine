
var kassaController = angular.module('kassaController', []);

kassaController.controller('kassaController', ['$scope', '$window', function ($scope, $window) {
	$scope.is_backend_ready = false;
	$scope.loading = false;

	$scope.order;
	$window.init= function() {
		$scope.$apply($scope.load_orderservice_lib);
	};
	
	$scope.load_orderservice_lib = function() {
		gapi.client.load('orderendpoint', 'v1', function(){
			  $scope.is_backend_ready = true;
			  $scope.$apply();
			  $scope.loadOrder();
		}, '/_ah/api');
	};
	
	$scope.loadOrder = function() {
        gapi.client.orderendpoint.getInitialOrder().execute(function(resp) {
                if (!resp.code) {
                		$scope.order = resp.result ;
                		$scope.$apply();
                }
	     });
	};
	$scope.plusOne = function(item){
		item.quantity = item.quantity + 1;
		item.total = item.quantity * item.drink.price;
		$scope.calculateTotal();
	};
	$scope.plusFive = function(item){
		item.quantity = item.quantity + 5;
		item.total = item.quantity * item.drink.price;
		$scope.calculateTotal();
	};
	
	$scope.reset = function(){
		for(var item of $scope.order.orderItems){
			item.quantity = 0;
			item.total = 0;
		}
		$scope.calculateTotal();
	}
	
	$scope.calculateTotal = function(){
		var count = 0;
		for(var item of $scope.order.orderItems){
			count = count + item.total;
		}
		$scope.order.totalAmount = count;
	}
	$scope.sendOrder = function() {
		$scope.loading = true;

		gapi.client.orderendpoint.updateOrder($scope.order).execute(function(resp) {
            if (!resp.code) {
            	$scope.order = resp.result ;
            	$scope.loading = false;
            	$scope.$apply();
            }
     });
	};
	
//	$scope.sendOrder = function() {
//		alert('sendOrder');
//	    var path = '/order'+ '?order=' + 'bert';
//	    var xhr = new XMLHttpRequest();
//	    xhr.open('POST', path, true);
//	    xhr.send();
//	};
}]);


kassaController.directive('beurs-kassa-item', function(){
	return {
		restrict : 'E',
		templateUrl : 'html/beurs-kassa-item.html'
	};
});
