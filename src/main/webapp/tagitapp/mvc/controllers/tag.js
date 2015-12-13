/**
 * Created by mjain on 12/2/15.
 */


angular.module('app.controllers.tag', []).
controller('TagCtrl', function($scope, $uibModal, $log, $routeParams, $http, $cookies) {
    $scope.status = {};
    var tgself = this;
    var showAllTags=[];
    tgself.status = {};

    $scope.animationsEnabled = true;
    var userCookie = $cookies.getObject('tagit');
    var parsedUserCookie = JSON.parse(userCookie);
    var userId = parsedUserCookie.userid;


    tgself.getAllTags = function(){
        $http.get('../../../tag/getAll/user/' + userId)
            .success(function(alltags){
                var tagKeys = Object.keys(alltags);

                for (var i=0; i<tagKeys.length; i++)
                {
                    showAllTags.push(
                        {
                            "tagName":tagKeys[i],
                            "tagCount":alltags[tagKeys[i]]
                        }

                    );

                }

                tgself.mytags = showAllTags;
            })
            .error(function (error) {
                console.log(error);
            });
    };



});
