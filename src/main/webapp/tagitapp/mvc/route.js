angular.module('app', [
    'ngRoute',
    'ngStorage',
    'ngResource',
    'ngCookies',
    'ui.bootstrap',
    'ui.tinymce',
    'ui.ace',
    'angularFileUpload',

    'app.services.ui',
    'app.directives.nav',
    'app.controllers.home',
    'app.controllers.user',
    'app.controllers.login',
    'app.controllers.notebook',
    'app.controllers.bookmark'
]).config(['$routeProvider', function ($routeProvider) {

        $routeProvider
            .when('/home', {
                templateUrl: 'mvc/views/home.html',
                controller: 'HomeCtrl'
            })
            .when('/login', {
                templateUrl: 'mvc/views/login.html',
                controller: 'loginCtrl'
            })
            .when('/signup', {
                templateUrl: 'mvc/views/signup.html',
                controller: 'loginCtrl'
            })
            .when('/notebooks/owned', {
                templateUrl: 'mvc/views/notebooks-owned.html',
                controller: 'NotebookCtrl'
            })
            .when('/notebooks/shared', {
                templateUrl: 'mvc/views/notebooks-shared.html',
                controller: 'NotebookCtrl'
            })
            .when('/getMyBookmarks', {
                templateUrl: 'mvc/views/myBookmarks.html',
                controller: 'NotebookCtrl'
            })
            .when('/bookmarks', {
                templateUrl: 'mvc/views/bookmark.html',
                controller: 'BookmarkCtrl'
            })
            .otherwise({
                redirectTo: '/login'
            });
    }])

    .run(function ($rootScope, $location, AuthenticationModel, $window, $route) {

        $rootScope.$on('$routeChangeStart', function (event, next, current) {

            var nextPath = $location.path();
            //console.log(nextPath);
            if (!AuthenticationModel.isSignedIn() && nextPath != '/signup') {
                $location.path('/login');
            }
            else if(AuthenticationModel.isSignedIn() && ( nextPath == '/login' || nextPath == '/signup')){
                $location.path('/home');
            }

        });


        /* $rootScope.$on('$stateChangeSuccess', function(event, toState, toParams, fromState, fromParams){
         if (!AuthenticationModel.isSignedIn() ) {
         $location.path('/trial');
         }
         else if (!AuthenticationModel.isSignedIn() && toState.name != 'signup' && toState.name != 'login' && toState.name != 'verifyEmail') {
         $state.go('login', {reload: true});
         }
         });*/

    });