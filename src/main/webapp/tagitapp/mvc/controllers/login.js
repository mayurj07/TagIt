/**
 * Created by mjain on 11/30/15.
 */

'use strict';

app.controller('loginCtrl', function ($scope, $http, $location, $window, ServerUrl, AuthenticationModel, $state, $log) {

    $scope.username = null;
    $scope.password = null;
    $scope.name = null;

    $scope.AuthenticationModel = AuthenticationModel;

    $scope.signIn = function (username, password) {

        $log.info(username + "" + password);
        return $http.post('../../../login', {
            email: username,
            password: password
        }).success(function(data) {
            AuthenticationModel.setUser(data.user);
            //$state.go('app.page', {page: 'dashboard', child: null});
        }).error(function (data) {
            AuthenticationModel.removeUser();
            AuthenticationModel.errorMessage = data;
        });
    };

    $scope.signUp = function (username, password, name) {
        return $http.post(ServerUrl + '/api/auth/signup', {
            username: username,
            password: password,
            name: name
        }).success(function(data) {
            AuthenticationModel.setUser(data.user);
            $state.go('app.page', {page: 'dashboard', child: null});
        }).error(function (data) {
            alert(data);
            AuthenticationModel.removeUser();
            AuthenticationModel.errorMessage = data;
        });
    };
});



app.factory('AuthenticationModel', function ($http, $cookies) {

    this.user = $cookies.getObject('user');
    this.errorMessage = null;


    this.isSignedIn = function() {
        return !!this.user;
    };

    this.setUser = function(user) {
        this.errorMessage = null;
        this.user = user;
        var now = new Date();
        var exp = new Date(now.getFullYear(), now.getMonth(), now.getDate()+1);  //expiration to 1 day
        $cookies.putObject('user', user, {expires: exp, path: '/'});
    };

    this.removeUser = function() {
        this.user = null;
        $cookies.remove('user', {path: '/'});
    };

    return this;

});
