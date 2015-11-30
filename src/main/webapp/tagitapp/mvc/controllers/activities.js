angular.module('app.controllers.activities', []).
    controller('ActivityPollcreateCtrl', ['$scope', '$routeParams', '$http', function ($scope, $routeParams, $http) {
        var options = ["option1", "option2"];

        $scope.options = options;

        $scope.addoption = function () {
            for (var i = 0; i <= options.length; i++) {
                if (options[i] == undefined) {
                    $scope.options.push("option" + (i + 1));
                    break;
                }
            }
        }

        $scope.submitpoll = function () {
            var optionarray = [];

            $scope.poll.to = [];
            $scope.poll.to = $scope.todomain.replace(/\s/g, '').split(",");

            if ($scope.polltype == "option" || "option-quiz") {
                for (var i = 0; i < options.length; i++) {
                    var tmp = $scope[options[i]];
                    if (tmp != undefined) {
                        optionarray.push(tmp);
                    }
                }
            }

            $scope.poll.visibility = true ? 'private' : 'public';
            $scope.poll.code = 9221;
            $scope.poll.options = optionarray;
            $http.post('polls/create', $scope.poll).
                success(function (data, status, headers, config) {
                    if (data['code'] == 2221) {
                        $(".success_space").append('<div class="alert alert-success alert-dismissible" role="alert"> <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>Awesome, your poll has been created, with ID' + data['poll'] + '</div>');
                    }
                });

        }

    }]);