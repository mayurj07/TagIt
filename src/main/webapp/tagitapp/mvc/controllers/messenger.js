angular.module('app.controllers.messenger', [])

    .controller('MessengerSystemMessagesCtrl', ['$scope', '$routeParams', '$http', function ($scope, $routeParams, $http) {
        $scope.scopes = ['PRIVATE', 'DOMAINS', 'GLOBAL'];
        $scope.entity = {};

        var resetEntity = $scope.resetEntity = function () {
            $scope.entity = {'scope': 'PRIVATE'};
        };

        $scope.send = function () {
            $http.post('system-messages/send', $scope.entity).
                success(function (data, status, headers, config) {
                    alert(data);
                    resetEntity();
                });
        };
        resetEntity();
    }])

    .controller('MessengerSysMessagesCtrl', ['$scope', '$routeParams', '$http', function ($scope, $routeParams, $http) {
        $scope.scopes = ['DOMAINS', 'GLOBAL', 'PRIVATE'];
        $scope.entity = {};

        var resetEntity = $scope.resetEntity = function () {
            $scope.entity = {'scope': 'DOMAINS'};
        };

        var submit = function () {
            $http.post('sys-messages/send', $scope.entity).
                success(function (data, status, headers, config) {
                    alert(data);
                    resetEntity();
                });
        };

        $scope.send = function () {
            var file = $("#sys-image-upload").get(0);
            if (file.files && file.files[0]) {
                var FR = new FileReader();
                FR.onload = function (e) {
                    $scope.entity['image'] = e.target.result;
                    submit();
                };
                FR.readAsDataURL(file.files[0]);
            } else {
                submit();
            }
        };

        resetEntity();
    }])

    .controller('MessengerAdminMessagesCtrl', ['$scope', '$routeParams', '$http', function ($scope, $routeParams, $http) {
        $scope.scopes = ['multi-domain', 'global'];
        $scope.types = ['score', 'option', 'option-quiz'];
        $scope.entity = {};

        var resetEntity = $scope.resetEntity = function () {
            $scope.entity = {'scope': 'global', 'type': 'score'};
        };

        var submit = function () {
            $http.post('polls/create/', $scope.entity).
                success(function (data, status, headers, config) {
                    resetEntity();
                    alert(data);
                });
        };

        $scope.send = function () {
            var file = $("#image-upload").get(0);
            if (file.files && file.files[0]) {
                var FR = new FileReader();
                FR.onload = function (e) {
                    $scope.entity['image'] = e.target.result;
                    submit();
                };
                FR.readAsDataURL(file.files[0]);
            } else {
                submit();
            }
        };
        resetEntity();
    }])

    .controller('MessengerPollsCtrl', ['$scope', '$routeParams', '$http', function ($scope, $routeParams, $http) {
        $scope.scopes = ['multi-domain', 'global'];
        $scope.types = ['score', 'option', 'option-quiz'];
        $scope.entity = {};
        var resetEntity = $scope.resetEntity = function () {
            $scope.entity = {'scope': 'global', 'type': 'score'};
        };

        var submit = function () {
            $http.post('polls/create/', $scope.entity).
                success(function (data, status, headers, config) {
                    //resetEntity();
                    alert(data);
                });
        };

        $scope.send = function () {
            var file = $("#image-upload").get(0);
            if (file.files && file.files[0]) {
                var FR = new FileReader();
                FR.onload = function (e) {
                    $scope.entity['image'] = e.target.result;
                    submit();
                };
                FR.readAsDataURL(file.files[0]);
            } else {
                submit();
            }
        };
        resetEntity();
    }])

    .controller('MessengerFiltersCtrl', ['$scope', '$routeParams', '$http', function ($scope, $routeParams, $http) {
        $scope.updateMode = false;
        $scope.entity = {};

        var refreshList = function () {
            $http.get('db/admin/table/messengerFilters/list').
                success(function (data, status, headers, config) {
                    $scope.entities = data;
                });
        };

        var resetEntity = function () {
            $scope.entity.name = '';
            $scope.entity.script = "function trigger(context, params, cb) {\n\tcb(data);\n\t// cb(false);\n}";
        };

        $scope.renderUpdate = function (id) {
            $http.get('db/admin/table/messengerFilters/get/' + id).
                success(function (data, status, headers, config) {
                    if (!data) {
                        alert('Filter not found!');
                    } else {
                        $scope.entity = {
                            '_id': data['_id'],
                            'name': data['name'],
                            'script': data['script']
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
            if (confirm("Delete this filter?")) {
                $("#main-content").mask("Deleting...");
                $http.get('db/admin/table/messengerFilters/delete/' + $scope.entity['_id']).
                    success(function (data, status, headers, config) {
                        $("#main-content").unmask();
                        if (data) {
                            setTimeout(refreshList, 100);
                            setTimeout(resetEntity, 10);
                            $scope.updateMode = false;
                            alert('Filter delete successful!');
                        } else {
                            alert('Filter delete fail!');
                        }
                    });
            }
        };

        $scope.save = function () {
            $("#main-content").mask("Saving...");
            if ($scope.updateMode) {
                $http.post('db/admin/table/messengerFilters/update', $scope.entity).
                    success(function (data, status, headers, config) {
                        $("#main-content").unmask();
                        if (data) {
                            setTimeout(refreshList, 100);
                            alert('Filter update successful!');
                        } else {
                            alert('Filter update fail!');
                        }
                    });
            } else {
                $http.post('db/admin/table/messengerFilters/new', $scope.entity).
                    success(function (data, status, headers, config) {
                        $("#main-content").unmask();
                        if (data) {
                            setTimeout(refreshList, 100);
                            setTimeout(resetEntity, 10);
                            alert('Filter create successful!');
                        } else {
                            alert('Filter create fail!');
                        }
                    });
            }
        };

        refreshList();
        resetEntity();
    }])

    .controller('MessengerJobsCtrl', ['$scope', '$routeParams', '$http', function ($scope, $routeParams, $http) {

        $scope.updateMode = false;
        $scope.entity = {};
        $scope.types = ['SCRIPT', 'SYS_MSG', 'EMAIL'];

        $scope.isType = function (type) {
            return $scope.entity.type == type;
        };

        $scope.showFilterSelect = function () {
            $http.get('db/admin/table/messengerFilters/list').
                success(function (data, status, headers, config) {
                    $scope.filters = data;
                    $('#filterSelect').modal('toggle');
                });
        };

        $scope.selectFilter = function (id, txt) {
            $scope.entity.filter = id;
            $scope.entity.filterText = txt;
            $('#filterSelect').modal('toggle');
        };

        $scope.resetFilterSelect = function () {
            $scope.entity.filter = '';
            $scope.entity.filterText = '';
        };

        $scope.showTriggerSelect = function () {
            $http.get('db/admin/table/messengerTriggers/list').
                success(function (data, status, headers, config) {
                    $scope.triggers = data;
                    $('#triggerSelect').modal('toggle');
                });
        };

        $scope.selectTrigger = function (id, txt) {
            $scope.entity.trigger = id;
            $scope.entity.triggerText = txt;
            $('#triggerSelect').modal('toggle');
        };

        $scope.resetTriggerSelect = function (id, txt) {
            $scope.entity.trigger = '';
            $scope.entity.triggerText = '';
        };

        $scope.showEmailTemplateSelect = function () {
            $http.get('db/mailman/table/templates/list').
                success(function (data, status, headers, config) {
                    $scope.emailTemplates = data;
                    $('#emailTemplateSelect').modal('toggle');
                });
        };

        $scope.selectEmailTemplates = function (id, txt) {
            $scope.entity.emailTemplate = id;
            $scope.entity.emailTemplateText = txt;
            $('#emailTemplateSelect').modal('toggle');
        };

        $scope.resetEmailTemplateSelect = function () {
            $scope.entity.emailTemplate = '';
            $scope.entity.emailTemplateText = '';
        };

        var refreshList = $scope.refreshList = function () {
            $http.get('db/admin/table/messengerJobs/list').
                success(function (data, status, headers, config) {
                    $scope.entities = data;
                });
        };

        var resetEntity = function () {
            $scope.entity.name = '';
            $scope.entity.status = 'STOP';
            $scope.entity.repeat = true;
            $scope.entity.interval = '';
            $scope.entity.expectStopAt = '';
            $scope.entity.type = 'SCRIPT';
            $scope.entity.filter = '';
            $scope.entity.filterText = '';
            $scope.entity.filterParams = '{}';
            $scope.entity.trigger = '';
            $scope.entity.filterText = '';
            $scope.entity.triggerParams = '{}';
            $scope.entity.script = 'function job(context, data, cb) {\n\n}';
            $scope.entity.withFullSync = false;
            $scope.entity.url = '';
            $scope.entity.ref = '';
            $scope.entity.emailTemplate = '';
            $scope.entity.emailTemplateText = '';
            $scope.entity.emailTemplateParams = '{}';
            $scope.entity.successCount = 0;
            $scope.entity.failCount = 0;
        };

        $scope.renderUpdate = function (id) {
            $http.get('db/admin/table/messengerJobs/get/' + id).
                success(function (data, status, headers, config) {
                    if (!data) {
                        alert('Job not found!');
                    } else {
                        $scope.entity = {
                            '_id': data['_id'],
                            'name': data['name'],
                            'status': data['status'],
                            'type': data['type'],
                            'repeat': data['repeat'],
                            'interval': data['interval'],
                            'expectStopAt': data['expectStopAt'],
                            'filterParams': data['filterParams'],
                            'triggerParams': data['triggerParams'],
                            'emailTemplateParams': data['emailTemplateParams'],
                            'successCount': data['successCount'],
                            'failCount': data['failCount']
                        };

                        if (data['filter']) {
                            $http.get('db/admin/table/messengerFilters/get/' + data['filter']).
                                success(function (data, status, headers, config) {
                                    if (!data) {
                                        $scope.entity['filter'] = '';
                                        $scope.entity['filterText'] = '';
                                    } else {
                                        $scope.entity['filter'] = data['_id'];
                                        $scope.entity['filterText'] = data['name'];
                                    }
                                });
                        }

                        if (data['trigger']) {
                            $http.get('db/admin/table/messengerTriggers/get/' + data['trigger']).
                                success(function (data, status, headers, config) {
                                    if (!data) {
                                        $scope.entity['trigger'] = '';
                                        $scope.entity['triggerText'] = '';
                                    } else {
                                        $scope.entity['trigger'] = data['_id'];
                                        $scope.entity['triggerText'] = data['name'];
                                    }
                                });
                        }

                        if (data['type'] == 'SYS_MSG') {
                            $scope.entity.withFullSync = data['withFullSync'];
                            $scope.entity.url = data['url'];
                            $scope.entity.ref = data['ref'];
                        } else if (data['type'] == 'EMAIL') {
                            if (data['emailTemplate']) {
                                $http.get('db/mailman/table/templates/get/' + data['emailTemplate']).
                                    success(function (data, status, headers, config) {
                                        if (!data) {
                                            $scope.entity['emailTemplate'] = '';
                                            $scope.entity['emailTemplateText'] = '';
                                        } else {
                                            $scope.entity['emailTemplate'] = data['_id'];
                                            $scope.entity['emailTemplateText'] = data['subject'];
                                        }
                                    });
                            }
                        } else if (data['type'] == 'SCRIPT') {
                            $scope.entity.script = data['script'];
                        }
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
            if (confirm("Delete this job?")) {
                $("#main-content").mask("Deleting...");
                $http.get('db/admin/table/messengerJobs/delete/' + $scope.entity['_id']).
                    success(function (data, status, headers, config) {
                        $("#main-content").unmask();
                        if (data) {
                            setTimeout(refreshList, 100);
                            setTimeout(resetEntity, 10);
                            $scope.updateMode = false;
                            alert('Job delete successful!');
                        } else {
                            alert('Job delete fail!');
                        }
                    });
            }
        };

        $scope.save = function () {
            var data = {};
            data.name = $scope.entity.name;
            data.status = $scope.entity.status;
            data.repeat = $scope.entity.repeat;
            data.interval = $scope.entity.interval;
            data.expectStopAt = $scope.entity.expectStopAt;
            data.type = $scope.entity.type;
            data.filter = $scope.entity.filter;
            data.filterParams = $scope.entity.filterParams;
            data.trigger = $scope.entity.trigger;
            data.triggerParams = $scope.entity.triggerParams;
            data.successCount = $scope.entity.successCount;
            data.failCount = $scope.entity.failCount;
            if (data.type == 'SYS_MSG') {
                data.withFullSync = $scope.entity.withFullSync;
                data.url = $scope.entity.url;
                data.ref = $scope.entity.ref;
            } else if (data.type == 'EMAIL') {
                data.emailTemplate = $scope.entity.emailTemplate;
                data.emailTemplateParams = $scope.entity.emailTemplateParams;
            } else if (data.type == 'SCRIPT') {
                data.script = $scope.entity.script;
            }
            if ($scope.updateMode) {
                data['_id'] = $scope.entity['_id'];
            }
            $("#main-content").mask("Saving...");
            if ($scope.updateMode) {
                $http.post('db/admin/table/messengerJobs/update', data).
                    success(function (data, status, headers, config) {
                        $("#main-content").unmask();
                        if (data) {
                            setTimeout(refreshList, 100);
                            alert('Job update successful!');
                        } else {
                            alert('Job update fail!');
                        }
                    });
            } else {
                $http.post('db/admin/table/messengerJobs/new', data).
                    success(function (data, status, headers, config) {
                        $("#main-content").unmask();
                        if (data) {
                            setTimeout(refreshList, 100);
                            setTimeout(resetEntity, 10);
                            alert('Job create successful!');
                        } else {
                            alert('Job create fail!');
                        }
                    });
            }
        };

        $scope.startJob = function (job) {
            var data = {
                '_id': job['_id'],
                'status': 'REQUEST_START'
            };
            $http.post('db/admin/table/messengerJobs/update', data);
            resetEntity();
            $scope.updateMode = false;
            setTimeout(refreshList, 800);
        };

        $scope.stopJob = function (job) {
            var data = {
                '_id': job['_id'],
                'status': 'REQUEST_STOP'
            };
            $http.post('db/admin/table/messengerJobs/update', data);
            resetEntity();
            $scope.updateMode = false;
            setTimeout(refreshList, 800);
        };

        resetEntity();
        refreshList();
    }])

    .controller('MessengerTriggersCtrl', ['$scope', '$routeParams', '$http', function ($scope, $routeParams, $http) {

        $scope.updateMode = false;
        $scope.entity = {};

        $scope.testCase = {};

        var refreshList = function () {
            $http.get('db/admin/table/messengerTriggers/list').
                success(function (data, status, headers, config) {
                    $scope.entities = data;
                });
        };

        var resetEntity = function () {
            $scope.entity.name = '';
            $scope.entity.script = "function trigger(params, now) {\n\treturn false;\n}";
        };

        $scope.renderUpdate = function (id) {
            $http.get('db/admin/table/messengerTriggers/get/' + id).
                success(function (data, status, headers, config) {
                    if (!data) {
                        alert('Filter not found!');
                    } else {
                        $scope.entity = {
                            '_id': data['_id'],
                            'name': data['name'],
                            'script': data['script']
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
            if (confirm("Delete this trigger?")) {
                $("#main-content").mask("Deleting...");
                $http.get('db/admin/table/messengerTriggers/delete/' + $scope.entity['_id']).
                    success(function (data, status, headers, config) {
                        $("#main-content").unmask();
                        if (data) {
                            setTimeout(refreshList, 100);
                            setTimeout(resetEntity, 10);
                            $scope.updateMode = false;
                            alert('Trigger delete successful!');
                        } else {
                            alert('Trigger delete fail!');
                        }
                    });
            }
        };

        $scope.save = function () {
            $("#main-content").mask("Saving...");
            if ($scope.updateMode) {
                $http.post('db/admin/table/messengerTriggers/update', $scope.entity).
                    success(function (data, status, headers, config) {
                        $("#main-content").unmask();
                        if (data) {
                            setTimeout(refreshList, 100);
                            alert('Trigger update successful!');
                        } else {
                            alert('Trigger update fail!');
                        }
                    });
            } else {
                $http.post('db/admin/table/messengerTriggers/new', $scope.entity).
                    success(function (data, status, headers, config) {
                        $("#main-content").unmask();
                        if (data) {
                            setTimeout(refreshList, 100);
                            setTimeout(resetEntity, 10);
                            alert('Trigger create successful!');
                        } else {
                            alert('Trigger create fail!');
                        }
                    });
            }
        };

        $scope.runTest = function () {
            var lastSentAt = moment($scope.testCase.lastSentAt, 'YYYY-MM-DD HH:mm:ss');
            var now = moment();
            try {
                var func = function () {
                    return eval('[' + $scope.entity.script + ']')[0];
                }();
                alert("Result: \n" + func.call(null, now, lastSentAt));
            } catch (err) {
                alert("Error: \n" + err);
            }
        };

        resetEntity();
        refreshList();
    }]);
