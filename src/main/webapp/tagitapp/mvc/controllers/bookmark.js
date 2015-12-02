
angular.module('app.controllers.bookmark', []).
controller('BookmarkCtrl', function($scope, $uibModal, $log, $routeParams, $http) {
    //$scope.status = {};
    var am = this;
    am.status = {};
    //$scope.items = ['item1', 'item2', 'item3'];

    $scope.animationsEnabled = true;

    var sharedNotebooks = [];

    am.getAllBookmark = function(){
        $http.get('../../../bookmark/getAll/1')
            .success(function(allbookmarks){

                console.log(allbookmarks);
                am.mybookmarks = allbookmarks;
            })
            .error(function (error) {
                console.log(error);
            });
    };

    am.updateBookmark = function(newName, bookmarkId){
        $http.put('../../../bookmark/'+ bookmarkId, { "name": newName, "owner_id": "1"})
            .success(function(updatedBookmark){
                //$log.info(updatedNotebook);
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


angular.module('app.controllers.bookmark').controller('createBookmarkModalCtrl', function ($scope, $uibModalInstance, $log, $http, items) {

    //$scope.items = items;
    //$scope.selected = {
    //    item: $scope.items[0]
    //};

    $scope.createBookmark = function(bmName){
        system.out.println("alert");
        $log.info(bmName);
        $http.post('../../../bookmark', {"name":bmName, "owner_id": "1"})
            .success(function(newBookmark){
                $log.info(newBookmark);

                $http.get('../../../bookmark/getAll/user/1')
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