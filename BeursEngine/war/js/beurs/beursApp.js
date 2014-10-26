var beursApp = angular.module('beursApp', ['ngRoute', 'beursControllers']);

beursApp.config(['$routeProvider',
                    function($routeProvider) {
                      $routeProvider.
                        when('/', {
                          templateUrl: 'html/list.html',
                          controller: 'beursController'
                        }).
                        when('/add', {
                          templateUrl: 'html/add.html',
                          controller: 'beursController'
                        }).
                        otherwise({
                          redirectTo: '/'
                        });
                    }]);