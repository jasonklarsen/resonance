'use strict';

var resonance = angular.module('resonance', ['resonance-routes']);

resonance.controller('TimeCtrl', function TimeCtrl($scope, $http, ServiceRoutes) {
  
  var route = ServiceRoutes.serviceRoute;

  $http.get(route).success(function(data) {
    $scope.theTime = data.sysTime;
  }); 
});