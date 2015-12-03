
angular.module('app.controllers.bookmark', []).
controller('BookmarkCtrl', function($scope, $uibModal, $log, $routeParams, $http, $cookies) {
    var am = this;
    $scope.animationsEnabled = true;

    var userCookie = $cookies.getObject('tagit');
    var parsedUserCookie = JSON.parse(userCookie);
    var userId = parsedUserCookie.userid;
    var sharedNotebooks = [];

    am.userId = parsedUserCookie.userid;

    am.getAllBookmark = function(){
        $http.get('../../../bookmark/user/' + userId)
            .success(function(allbookmarks){

                console.log(allbookmarks);
                am.mybookmarks = allbookmarks;
            })
            .error(function (error) {
                console.log(error);
            });
    };

    am.updateBookmark = function(newName, bookmarkId){
        $http.put('../../../bookmark/'+ bookmarkId, { "name": newName, "owner_id": userId})
            .success(function(updatedBookmark){
                //$log.info(updatedNotebook);
            })
            .error(function (error) {
                console.log(error);
            });
    };


    am.addTag = function(bookmarkId, tagName){
        $http.post('../../../tag', { "tagName": tagName, "bookmarkID": bookmarkId, "tag_userid": userId})
            .success(function(newTag){
                $log.info(newTag);
            })
            .error(function (error) {
                console.log(error);
            });
    };


    am.open = function (size) {

        var modalInstance = $uibModal.open({
            animation: $scope.animationsEnabled,
            templateUrl: 'createBookmarkModal.html',
            controller: 'createBookmarkModalCtrl',
            size: size,
            resolve: {
                items: function () {
                    return $scope.items;
                }
            }
        });

        modalInstance.result.then(function (allBookmarks) {
            am.mybookmarks = allBookmarks;
            //$log.info(allNotebooks);
        }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        });
    };
});


angular.module('app.controllers.bookmark').controller('createBookmarkModalCtrl', function ($scope, $uibModalInstance, $log, $http, $cookies) {

    var userCookie = $cookies.getObject('tagit');
    var parsedUserCookie = JSON.parse(userCookie);
    var userId = parsedUserCookie.userid;

    $scope.createBookmark = function(bmName){
        system.out.println("alert");
        $log.info(bmName);
        $http.post('../../../bookmark', {"name":bmName, "owner_id": userId})
            .success(function(newBookmark){
                $log.info(newBookmark);

                $http.get('../../../bookmark/getAll/user/' + userId)
                    .success(function(allBookmarks){
                        //$log.info(allNotebooks);
                        $uibModalInstance.close(allBookmarks);
                    })
                    .error(function (error) {
                        console.log(error);
                    });

            })
            .error(function (error) {
                console.log(error);
            });
    };


    //$scope.ok = function () {
    //    $uibModalInstance.close($scope.selected.item);
    //};

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});