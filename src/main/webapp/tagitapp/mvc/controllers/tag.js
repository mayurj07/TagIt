/**
 * Created by mjain on 12/2/15.
 */


angular.module('app.controllers.tag', []).
controller('TagCtrl', function($scope, $uibModal, $log, $routeParams, $http, $cookies) {
    var vm = this;
    $scope.animationsEnabled = true;
    var userCookie = $cookies.getObject('tagit');
    var parsedUserCookie = JSON.parse(userCookie);
    var userId = parsedUserCookie.userid;
    var showAllTags=[];
    var label = [];
    var data = [];

    vm.getAllTags = function(){
        $http.get('../../../tag/getAll/user/' + userId)
            .success(function(alltags){
                var tagKeys = Object.keys(alltags);


                for (var i=0; i<tagKeys.length; i++)
                {
                    showAllTags.push(
                        {
                            "tagName":tagKeys[i],
                            "tagCount":alltags[tagKeys[i]]
                        });

                    label.push(tagKeys[i]);
                    data.push(alltags[tagKeys[i]]);
                }

                vm.mytags = showAllTags;
                vm.labels = label;
                vm.data =  [data];


                vm.config = {
                    "colours": [{
                        "fillColor": "rgba(224, 108, 112, 1)",
                        "strokeColor": "rgba(207,100,103,1)",
                        "pointColor": "rgba(220,220,220,1)",
                        "pointStrokeColor": "#fff",
                        "pointHighlightFill": "#fff",
                        "pointHighlightStroke": "rgba(151,187,205,0.8)"
                    }]
                }

            })
            .error(function (error) {
                console.log(error);
            });
    };
});
