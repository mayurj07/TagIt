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
            $timeout(function(){ $location.path('/home');},1);
        }).error(function (data) {
            AuthenticationModel.removeUser();
            $location.path('/login');
            AuthenticationModel.errorMessage = data.message;
        });
    };

    $scope.signUp = function (name, email, password, country, state) {

        console.log("email: " + email);
        return $http.post('../../../user/signup', {
            name: name,
            email: email,
            password: password,
            country: country,
            state: state
        }).success(function(data) {
            if(data){
                AuthenticationModel.errorMessage = "Verification sent on your email. Please verify your email account.";
                alert("Verification sent on your email. Please verify your email account.");
            }
        }).error(function (data) {
            alert(data.message);
            AuthenticationModel.errorMessage = data.message;
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
