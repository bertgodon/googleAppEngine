var beursApp = angular.module('beursApp', ['ngRoute', 'beursController', 'kassaController']);

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
                        when('/kassa', {
                            templateUrl: 'html/order.html',
                            controller: 'kassaController'
                          }).
                          when('/order', {
                              templateUrl: 'html/order.html',
                              controller: 'kassaController'
                            }).
                        otherwise({
                          redirectTo: '/'
                        });
                    }]);