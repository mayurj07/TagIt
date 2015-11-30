angular.module('app.controllers.user', []).

  controller('UserGroupsCtrl', ['$scope', '$routeParams', '$http', '$upload', function($scope, $routeParams, $http, $upload) {
    
    $scope.currentTab = 'PENDING';
    $scope.pendingList = [];
    $scope.blackList = {
      'count': 0,
      'keyword': '',
      'list': []
    };
    $scope.whiteList = {
      'count': 0,
      'keyword': '',
      'list': []
    };
    $scope.edit = {
      'domain': '',
      'company': ''
    };
    $scope.listType = '';
    $scope.uploadFile = '';
    
    var currentGroupId = '';
    
    $scope.refreshPendingList = function() {
      $http.get('user/groups/pending/list').
        success(function(data, status, headers, config) {
          $scope.pendingList = data;
        });
    };
    
    $scope.refreshListCount = function(list) {
      $http.get('user/groups/' + list + '/count').
        success(function(data, status, headers, config) {
          if(list == 'white') {
            $scope.whiteList.count = data['cnt'];
          } else {
            $scope.blackList.count = data['cnt'];
          }
        });
    };
    
    $scope.addItemToList = function() {
      $http.post('user/groups/' + $scope.currentTab + '/add', $scope.edit).
        success(function(data, status, headers, config) {
          if(data) {
            $scope.refreshListCount($scope.currentTab);
            $('#editList').modal('toggle');
          }
        });
    };
    
    $scope.search = function() {
      var keyword = $scope.currentTab == 'white' ? $scope.whiteList.keyword : $scope.blackList.keyword;
      if(keyword) {
        var data = {'keyword': keyword};
        $http.post('user/groups/' + $scope.currentTab + '/search', data).
          success(function(data, status, headers, config) {
            if($scope.currentTab == 'white') {
              $scope.whiteList.list = data;
            } else {
              $scope.blackList.list = data;
            }
          });
      }
    };
    
    $scope.putToList = function(gid, domain, list) {
      currentGroupId = gid;
      $scope.listType = list;
      $scope.edit.domain = domain;
    };
    
    $scope.verifyItem = function() {
      var data = {'company': $scope.edit.company};
      $http.post('user/groups/' + $scope.listType + '/verify/' + currentGroupId, data).
        success(function(data, status, headers, config) {
          $scope.refreshPendingList();
          $('#putToList').modal('toggle');
        });
    };
    
    $scope.uploadFileSelect = function(file) {
      $scope.uploadFile = file;
    };
    
    $scope.uploadList = function() {
      if(!$scope.uploadFile) {
        return;
      }
      $upload.upload({
        url: 'user/groups/' + $scope.currentTab + '/upload',
        file: $scope.uploadFile,
      }).success(function(data, status, headers, config) {
        $scope.refreshListCount($scope.currentTab);
        $('#uploadList').modal('toggle');
        $scope.uploadFile = '';
      });
    };

    $scope.removeItem = function(id) {
      $http.get('user/groups/remove/' + id).
        success(function(data, status, headers, config) {
          $scope.refreshPendingList();
        });
    };
    
    $scope.searchInputKeyPress = function(event) {
      if(event.keyCode == 13) {
        $scope.search();
      }
    };
    
    $scope.renderPendingList = function() {
      $scope.currentTab = 'pending';
      $scope.refreshPendingList();
    };
    
    $scope.renderWhiteList = function() {
      $scope.currentTab = 'white';
      $scope.refreshListCount('white');
    };
    
    $scope.renderBlackList = function() {
      $scope.currentTab = 'black';
      $scope.refreshListCount('black');
    };

  }]).

  controller('UserUsersCtrl', ['$scope', '$routeParams', '$http', function($scope, $routeParams, $http) {
    $scope.keyword = '';
    $scope.user = { 'email': '', 'password': '' };
    $scope.search = function() {
      var keyword = btoa('{}');
      if($scope.keyword) {
        keyword = btoa('{"$text":{"$search": "' + $scope.keyword + '"}}');
      }
      var fields = btoa('{"email": true, "created_at": true, "last_signin_at": true, "devices": true}');
      var options = btoa('{}');
      $http.get('db/messenger/table/user_profiles/search/' + keyword + '/' + fields + '/' + options).
        success(function(data, status, headers, config) {
          $scope.users = data;
        });
    };
    $scope.add = function() {
      if($scope.user.email && $scope.user.password) {
      $http.post('user/users/new', $scope.user).
        success(function(data, status, headers, config) {
          
        });
      }
      $scope.user = { 'email': '', 'password': '' };
      $('#editPanel').modal('toggle');
    };
    $scope.showMessages = function(email) {
      $scope.selectedUser = email;
      $scope.outMessages = [];
      $scope.inMessages = [];
      if(email) {
        var keyword = btoa('{"from": "' + email +'"}');
        var fields = btoa('{"to": true, "created_at": true, "received_at": true}');
        var options = btoa('{}');
        $http.get('db/messenger/table/threads/search/' + keyword + '/' + fields + '/' + options).
          success(function(data, status, headers, config) {
            $scope.outMessages = data;
          });
          
        keyword = btoa('{"to": "' + email +'"}');
        fields = btoa('{"from": true, "created_at": true, "received_at": true}');
        $http.get('db/messenger/table/threads/search/' + keyword + '/' + fields + '/' + options).
          success(function(data, status, headers, config) {
            $scope.inMessages = data;
          });
      }
      $('#messagePanel').modal('toggle');
    };
    
    $scope.search();
  }]).

  controller('UserUnconfirmedUsersCtrl', ['$scope', '$routeParams', '$http', function($scope, $routeParams, $http) {
    $scope.keyword = '';
    $scope.search = function() {
      var keyword = btoa('{}');
      if($scope.keyword) {
        keyword = btoa('{"$text":{"$search": "' + $scope.keyword + '"}}');
      }
      var fields = btoa('{"email": true, "created_at": true}');
      var options = btoa('{}');
      $http.get('db/messenger/table/unconfirmed_users/search/' + keyword + '/' + fields + '/' + options).
        success(function(data, status, headers, config) {
          $scope.users = data;
        });
    };
    
    $scope.search();
  }]);