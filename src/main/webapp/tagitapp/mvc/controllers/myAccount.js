
angular.module('app.controllers.myaccount', []).
controller('MyAccountCtrl', function($scope, $uibModal, $log, $routeParams, $http, $cookies) {
    var userCookie = $cookies.getObject('tagit');
    var parsedUserCookie = JSON.parse(userCookie);

    $scope.getUser = function(){
        console.log(parsedUserCookie);
        console.log(parsedUserCookie.name);
        $scope.user = parsedUserCookie;

    };
});