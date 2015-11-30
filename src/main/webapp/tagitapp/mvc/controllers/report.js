angular.module('app.controllers.report', []).
  controller('ReportOverviewCtrl', ['$scope', '$routeParams', '$http', function($scope, $routeParams, $http) {
    var keyword = btoa('{"type": "VALID_USERS", ""}');
    var fields = btoa('{"to": true, "created_at": true, "received_at": true}');
    var options = btoa('{}');
    $http.get('db/admin/table/reports/search/' + keyword + '/' + fields + '/' + options).
      success(function(data, status, headers, config) {
        $scope.outMessages = data;
      });
    $http.get('data/new-confirmed-users-day.json').
      success(function(data, status, headers, config) {
        data['element'] = 'new-confirmed-users-day-chart';
        Morris.Line(data);
      });
  }]);