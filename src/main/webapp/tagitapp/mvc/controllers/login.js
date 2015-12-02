/**
 * Created by mjain on 11/30/15.
 */


angular.module('app.controllers.login', []).
controller('loginCtrl', function ($scope, $http, $location, $window, $log, AuthenticationModel, $route, $timeout) {

    $scope.username = null;
    $scope.password = null;
    $scope.name = null;

    $scope.AuthenticationModel = AuthenticationModel;

    $scope.signIn = function (username, password) {

        return $http.post('../../../user/login', {
            email: username,
            password: password
        }).success(function(data) {
            delete data.password;
            AuthenticationModel.setUser(data);
            $timeout(function(){
                $location.path('/home');
            },1);
        }).error(function (data) {
            AuthenticationModel.removeUser();
            $location.path('/login');
            AuthenticationModel.errorMessage = data;
        });
    };

    $scope.signUp = function (name, email, password, country, state) {
        return $http.post(ServerUrl + '/user', {
            name: name,
            email: email,
            password: password,
            country: country,
            state: state
        }).success(function(data) {
            AuthenticationModel.setUser(data.user);
            $state.go('app.page', {page: 'dashboard', child: null});
        }).error(function (data) {
            alert(data);
            AuthenticationModel.removeUser();
            AuthenticationModel.errorMessage = data;
        });
    };


    $scope.openSignup = function(){
        console.log("openSignup");
      $location.path('/signup');
    };
});


angular.module('app.controllers.login').
factory('AuthenticationModel', function ($http, $cookies) {

    this.user = $cookies.getObject('tagit');
    this.errorMessage = null;


    this.isSignedIn = function() {
        return !!this.user;
    };

    this.setUser = function(user) {
        this.errorMessage = null;
        this.user = user;
    };

    this.removeUser = function() {
        this.user = null;
        $cookies.remove('tagit', {path: '/'});
    };

    return this;

});
