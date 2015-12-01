
angular.module('app.controllers.bookmark', []).
controller('BookmarkCtrl', function($scope, $uibModal, $log, $routeParams, $http) {
    //$scope.status = {};
    var vm = this;
    vm.status = {};
    //$scope.items = ['item1', 'item2', 'item3'];

    $scope.animationsEnabled = true;

    var sharedNotebooks = [];

    vm.getAllNotebooksOwned = function(){
        $http.get('../../../notebook/getAll/user/1')
            .success(function(allNotebooks){
                //$log.info(allNotebooks);
                vm.myNotebooks = allNotebooks;
            })
            .error(function (error) {
                console.log(error);
            });
    };

    vm.getAllNotebooksSharedWithMe = function(){
        $http.get('../../../notebook/getShared/user/1')
            .success(function(allNotebooks){
                for(var i=0; i<allNotebooks.length; i++){
                    $http.get('../../../user/' + allNotebooks[i].owner_id)
                        .success(function(user){
                            $log.info(user.name);

                            sharedNotebooks.push({
                                "bookname": allNotebooks[0].name,
                                "notebookid": allNotebooks[0].notebookid,
                                "ownerId": allNotebooks[0].owner_id,
                                "ownerName": user.name,
                                "ownerEmail": user.email
                            });
                        })
                        .error(function (error) {
                            console.log(error);
                        });
                }
                vm.sharedNotebooks = sharedNotebooks;

            })
            .error(function (error) {
                console.log(error);
            });
    };

    vm.updateNotebookName = function(newName, nbId){
        $http.put('../../../notebook/'+ nbId , { "name": newName, "owner_id": "1"})
            .success(function(updatedNotebook){
                //$log.info(updatedNotebook);
            })
            .error(function (error) {
                console.log(error);
            });
    };

    vm.open = function (size) {

        var modalInstance = $uibModal.open({
            animation: $scope.animationsEnabled,
            templateUrl: 'createNotebookModal.html',
            controller: 'createNotebookModalCtrl',
            size: size,
            resolve: {
                items: function () {
                    return $scope.items;
                }
            }
        });

        modalInstance.result.then(function (allNotebooks) {
            vm.myNotebooks = allNotebooks;
            //$log.info(allNotebooks);
        }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        });
    };
});


angular.module('app.controllers.notebook').controller('createNotebookModalCtrl', function ($scope, $uibModalInstance, $log, $http, items) {

    //$scope.items = items;
    //$scope.selected = {
    //    item: $scope.items[0]
    //};

    $scope.createNotebook = function(nbName){
        $log.info(nbName);
        $http.post('../../../notebook', {"name": nbName, "owner_id": "1"})
            .success(function(newNotebook){
                $log.info(newNotebook);

                $http.get('../../../notebook/getAll/user/1')
                    .success(function(allNotebooks){
                        //$log.info(allNotebooks);
                        $uibModalInstance.close(allNotebooks);
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