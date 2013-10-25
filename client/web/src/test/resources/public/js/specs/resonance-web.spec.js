'use strict';

describe('resonance', function() {

  beforeEach(module('resonance'));

  describe('TimeCtrl', function() {

    var $scope, $http, TimeCtrl, httpGet;
    var data = { sysTime: "<insert time here>" };
    var rr = { serviceRoute: "http://happyplace" };

    beforeEach(inject(function() {
      httpGet = jasmine.createSpyObj("httpGet", ['success']);
      $http = { get: function() { return httpGet; }};
      spyOn($http, 'get').andReturn(httpGet);

      $scope = {};
    }));

    it('should ....', inject(function($controller) {
      TimeCtrl = $controller("TimeCtrl", { $scope: $scope, $http: $http, ServiceRoutes: rr });

      expect($http.get).toHaveBeenCalledWith(rr.serviceRoute);

      var successCallback = httpGet.success.mostRecentCall.args[0]
      successCallback(data);

      expect($scope.theTime).toEqual(data.sysTime);
    }));
  })
});

