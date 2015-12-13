/**
 * Created by mjain on 12/12/15.
 */

angular.module('app.controllers.notebook').
factory('getBookmarks', function ($http, $cookies) {

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
