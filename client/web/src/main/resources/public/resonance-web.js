var resonance = angular.module('resonance', []);
resonance.controller('TimeCtrl', function TimeCtrl($scope, $http) {

  var route = serviceRoute // global to be cleaned up when serverRoutes.js is fixed
  
  $http.get(route).success(function(data) {
    $scope.theTime = data.sysTime;
  }); 
});