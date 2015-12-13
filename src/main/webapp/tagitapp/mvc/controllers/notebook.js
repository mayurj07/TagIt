angular.module('app.controllers.notebook', []).
controller('NotebookCtrl', function($scope, $uibModal, $log, $routeParams, $http, $cookies, $location, $route, $rootScope) {
    var vm = this;
    $scope.animationsEnabled = true;
    $scope.shareNotebookid;
    var sharedNotebooks = [];
    var userCookie = $cookies.getObject('tagit');
    var parsedUserCookie = JSON.parse(userCookie);
    var userId = parsedUserCookie.userid;

    vm.getAllNotebooksOwned = function(){
        $http.get('../../../notebook/getAll/user/' + userId)
            .success(function(allNotebooks){
                //$log.info(allNotebooks);
                //get count of bookmarks for the notebook
                /*for(var i=0; i<allNotebooks.length; i++){
                    $log.info(allNotebooks[i].notebookid);

                    $http.get('../../../bookmark/getCount/notebook/' + allNotebooks[i].notebookid)
                        .success(function(bookmarkCount){
                            $log.info("Count: " +bookmarkCount);
                            allNotebooks[i].bookmarkCount = bookmarkCount;
                        })
                        .error(function (error) {
                            console.log(error);
                        });
                }*/

                vm.myNotebooks = allNotebooks;
            })
            .error(function (error) {
                console.log(error);
            });
    };

    vm.getAllNotebooksSharedWithMe = function(){
        $http.get('../../../notebook/getShared/user/' + userId)
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
        $http.put('../../../notebook/'+ nbId , { "name": newName, "owner_id": userId})
            .success(function(updatedNotebook){
                //$log.info(updatedNotebook);
            })
            .error(function (error) {
                console.log(error);
            });
    };

    vm.addBookmark = function(bookmarkName, bookmarkDescription, notebookId){
        $http.post('../../../bookmark', {"bookmarkName": bookmarkName, "bookmarkDescription": bookmarkDescription, "notebookId": notebookId})
            .success(function(newBookmark){
                $log.info(newBookmark);

                //show count of all bookmarks
                /*$http.get('../../../bookmark/getCount/notebook/' + notebookId)
                    .success(function(bookmarkCount){
                        $log.info(bookmarkCount);
                    })
                    .error(function (error) {
                        console.log(error);
                    });*/
            })
            .error(function (error) {
                console.log(error);
            });
    };

    vm.deleteNotebook = function(notebookId){
        $http.delete('../../../notebook/' + notebookId)
            .success(function(deletedNB){
                $log.info(deletedNB);

                $route.reload();
            })
            .error(function (error) {
                console.log(error);
            });
    };

    vm.showBookmarksForNotebook = function(notebookId){
        $rootScope.selectedNotebook = notebookId;
        $location.path('/getMyBookmarks');
    };

    vm.initBookmarksView = function () {
        $http.get('../../../bookmark/getAll/' + $rootScope.selectedNotebook)
            .success(function(allBookmarks){
                $log.info(allBookmarks);
                vm.allBookmarksForNotebook = allBookmarks;
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

    vm.openShareModal = function (notebookid) {
        var modalInstance = $uibModal.open({
            animation: $scope.animationsEnabled,
            templateUrl: 'shareNotebookModal.html',
            controller: 'shareNotebookModalCtrl',
            size: 'sm',
            resolve: {
                shareNotebookid: function () {
                    return notebookid;
                }
            }
        });

        modalInstance.result.then(function (response) {
            //vm.myNotebooks = allNotebooks;
            $log.info(response);
        }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        });
    };
});



angular.module('app.controllers.notebook')
    .controller('shareNotebookModalCtrl', function ($scope, $uibModalInstance, $log, $http, $cookies, shareNotebookid) {

        var userCookie = $cookies.getObject('tagit');
        var parsedUserCookie = JSON.parse(userCookie);
        var userId = parsedUserCookie.userid;

        $scope.shareNotebook = function(userEmail, access){

            if(access == undefined)
                access = false;

            $http.post('../../../share/' + userId, { "shareWithEmailId": userEmail, "shareNotebookId" : shareNotebookid, "write" : access })
                .success(function(response){
                    if(response.shareId == undefined){
                        alert(response.message);
                    }else{
                        alert("Share Email notification sent to " + userEmail);
                    }
                    $uibModalInstance.close(response);
                })
                .error(function (error) {
                    alert(error.message);
                    console.log(error);
                });
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    });



angular.module('app.controllers.notebook')
    .controller('createNotebookModalCtrl', function ($scope, $uibModalInstance, $log, $http, $cookies) {

    var userCookie = $cookies.getObject('tagit');
    var parsedUserCookie = JSON.parse(userCookie);
    var userId = parsedUserCookie.userid;

    $scope.createNotebook = function(nbName){
        //$log.info(nbName);
        $http.post('../../../notebook', {"name": nbName, "owner_id": userId})
            .success(function(newNotebook){
                $log.info(newNotebook);

                $http.get('../../../notebook/getAll/user/' + userId)
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




