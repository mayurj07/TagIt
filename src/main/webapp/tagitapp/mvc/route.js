var app = angular.module('app', [
    'ngRoute',
    'ngStorage',
    'ngResource',
    'ui.bootstrap',
    'ui.tinymce',
    'ui.ace',
    'angularFileUpload',

    'app.services.ui',
    'app.filters',
    'app.directives.nav',
    'app.directives.fileupload',
    'app.controllers.home',
    'app.controllers.nodes',
    'app.controllers.user',
    'app.controllers.messenger',
    'app.controllers.mail',
    'app.controllers.report',
    'app.controllers.notebook',
    'app.controllers.feedbacks'
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
        .when('/notebooks/owned', {
            templateUrl: 'mvc/views/notebooks-owned.html',
            controller: 'NotebookCtrl'
        })
        .when('/notebooks/shared', {
            templateUrl: 'mvc/views/notebooks-shared.html',
            controller: 'NotebookCtrl'
        })
        .when('/user/users', {
            templateUrl: 'mvc/views/user-users.html',
            controller: 'UserUsersCtrl'
        })
        .when('/user/unconfirmed-users', {
            templateUrl: 'mvc/views/user-unconfirmed.html',
            controller: 'UserUnconfirmedUsersCtrl'
        })
        .when('/user/groups', {
            templateUrl: 'mvc/views/user-groups.html',
            controller: 'UserGroupsCtrl'
        })
        .when('/messenger/system-messages', {
            templateUrl: 'mvc/views/messenger-system-messages.html',
            controller: 'MessengerSystemMessagesCtrl'
        })
        .when('/messenger/admin-messages', {
            templateUrl: 'mvc/views/messenger-admin-messages.html',
            controller: 'MessengerAdminMessagesCtrl'
        })
        .when('/messenger/sys-messages', {
            templateUrl: 'mvc/views/messenger-sys-messages.html',
            controller: 'MessengerSysMessagesCtrl'
        })
        .when('/messenger/polls', {
            templateUrl: 'mvc/views/messenger-polls.html',
            controller: 'MessengerPollsCtrl'
        })
        .when('/messenger/filters', {
            templateUrl: 'mvc/views/messenger-filters.html',
            controller: 'MessengerFiltersCtrl'
        })
        .when('/messenger/triggers', {
            templateUrl: 'mvc/views/messenger-triggers.html',
            controller: 'MessengerTriggersCtrl'
        })
        .when('/messenger/jobs', {
            templateUrl: 'mvc/views/messenger-jobs.html',
            controller: 'MessengerJobsCtrl'
        })
        .when('/mail/templates', {
            templateUrl: 'mvc/views/mail-templates.html',
            controller: 'MailTemplatesCtrl'
        })
        .when('/feedbacks', {
            templateUrl: 'mvc/views/feedbacks.html',
            controller: 'FeedbacksCtrl'
        })
        .when('/report/overview', {
            templateUrl: 'mvc/views/report-overview.html',
            controller: 'ReportOverviewCtrl'
        })
        .otherwise({
            redirectTo: '/home'
        });
}]);