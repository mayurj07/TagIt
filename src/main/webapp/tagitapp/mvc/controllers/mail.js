angular.module('app.controllers.mail', []).
    controller('MailTemplatesCtrl', ['$scope', '$routeParams', '$http', function ($scope, $routeParams, $http) {

        $scope.updateMode = false;
        $scope.entity = {};
        $scope.testCase = {};
        $scope.templateParams = '';

        $scope.tinymceOptions = {
            relative_urls: false,
            convert_urls: false,
            plugins: [
                "advlist autolink lists link image charmap print preview anchor",
                "searchreplace visualblocks code",
                "insertdatetime media table contextmenu paste"
            ],
            toolbar: "insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image",
        };

        var refreshList = function () {
            $http.get('db/mailman/table/templates/list').
                success(function (data, status, headers, config) {
                    $scope.entities = data;
                });
        };

        var resetEntity = function () {
            $scope.entity.subject = '';
            $scope.entity.textBody = '';
            $scope.entity.htmlBody = '';
        };

        $scope.renderUpdate = function (id) {
            $http.get('db/mailman/table/templates/get/' + id).
                success(function (data, status, headers, config) {
                    if (!data) {
                        alert('Template not found!');
                    } else {
                        $scope.entity = {
                            '_id': data['_id'],
                            'subject': data['subject'],
                            'textBody': data['textBody'],
                            'htmlBody': data['htmlBody']
                        };
                        $scope.updateMode = true;
                    }
                });
        };

        $scope.cancelUpdate = function () {
            if (confirm("Canceling edit will drop any modifications. Are you sure?")) {
                resetEntity();
                $scope.updateMode = false;
            }
        };

        $scope.remove = function () {
            if (confirm("Delete this mail template?")) {
                $("#main-content").mask("Deleting...");
                $http.get('db/mailman/table/templates/delete/' + $scope.entity['_id']).
                    success(function (data, status, headers, config) {
                        $("#main-content").unmask();
                        if (data === 'OK') {
                            setTimeout(refreshList, 100);
                            setTimeout(resetEntity, 10);
                            $scope.updateMode = false;
                            alert('Template delete successful!');
                        } else {
                            alert('Template delete fail!');
                        }
                    });
            }
        };

        $scope.save = function () {
            $("#main-content").mask("Saving...");
            if ($scope.updateMode) {
                $http.post('db/mailman/table/templates/update', $scope.entity).
                    success(function (data, status, headers, config) {
                        $("#main-content").unmask();
                        if (data === 'OK') {
                            setTimeout(refreshList, 100);
                            alert('Template update successful!');
                        } else {
                            alert('Template update fail!');
                        }
                    });
            } else {
                $http.post('db/mailman/table/templates/new', $scope.entity).
                    success(function (data, status, headers, config) {
                        $("#main-content").unmask();
                        if (data === 'ERROR') {
                            alert('Template create fail!');
                        } else {
                            setTimeout(refreshList, 100);
                            setTimeout(resetEntity, 10);
                            alert('Template create successful!');
                        }
                    });
            }
        };

        $scope.send = function () {
            $("#testMailDialog").mask("Sending...");
            $http.post('db/mailman/table/templates/update', $scope.entity).
                success(function (data, status, headers, config) {
                    var req = {
                        'template': $scope.entity['_id'],
                        'to': $scope.testCase['email'],
                        'params': $scope.testCase['params']
                    };
                    //console.info(req);
                    $http.post('db/mailman/table/requests/new', req).
                        success(function (data, status, headers, config) {
                            //console.info(data);
                            $("#testMailDialog").unmask();
                            alert('Email sent successfully!');
                        });
                });
        };

        $scope.showTemplateID = function (id) {
            $scope.selectedTemplateID = id;
        };

        refreshList();

    }]);