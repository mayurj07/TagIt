angular.module('app.controllers.feedbacks', []).
  controller('FeedbacksCtrl', ['$scope', '$routeParams', '$http', function($scope, $routeParams, $http) {

    var refreshList = function() {
      $http.get('db/admin/table/feedbacks/list').
        success(function(data, status, headers, config) {
          $scope.entities = data;
        });
    };
    
    $scope.remove = function(id) {
      if(confirm("Delete this feedback?")) {
        $("#main-content").mask("Deleting...");
        console.log('delete feedback id: '+id);
        $http.get('db/admin/table/feedbacks/delete/' + id).
          success(function(data, status, headers, config) {
            $("#main-content").unmask();
            if(data) {
              setTimeout(refreshList, 100);
              alert('Feedback delete successful!');
            } else {
              alert('Feedback delete fail!');
            }
          });
      }
    };
    
    refreshList();
  }]);