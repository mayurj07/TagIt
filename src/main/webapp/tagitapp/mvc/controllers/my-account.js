/**
 * Created by harkirat singh on 12/2/2015.
 */
angular.module('app.controllers.my-account', []).
controller('MyAccountCtrl', function($scope, $uibModal, $log, $routeParams, $http, $cookies, $location) {
    var vm = this;
    $scoharedNotebooks = [];
    $scope.allBookmarksForNotebook = [];
    var userCookie = $cookies.getObject('tagit');
    var parsedUserCookie = JSON.parse(userCookie);
    var userId = parsedUserCookie.userid;

    vm.user = parsedUserCookie;


});
