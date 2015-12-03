/**
 * Created by mjain on 12/2/15.
 */


angular.module('app.controllers.tag', []).
controller('TagCtrl', function($scope, $uibModal, $log, $routeParams, $http, $cookies) {
    console.log("in the tag controller");
    $scope.status = {};
    var tgself = this;
    var showAllTags=[];
    tgself.status = {};
//$scope.items = ['item1', 'item2', 'item3'];

    $scope.animationsEnabled = true;
    var userCookie = $cookies.getObject('tagit');
    var parsedUserCookie = JSON.parse(userCookie);
    var userId = parsedUserCookie.userid;


    tgself.getAllTags = function(){
// alert(" in get tags");
        $http.get('../../../tag/getAll/user/1')
            .success(function(alltags){
//alert(JSON.parse(alltags));
                console.log(Object.keys(alltags));
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

                console.log(alltags);
                tgself.mytags = showAllTags;
            })
            .error(function (error) {
                console.log(error);
            });
    };



});
