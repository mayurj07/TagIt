angular.module('app.directives.fileupload',['app.services.ui'])
.directive('fileread', ['UI', '$location', function(UI, $location) {
    return {
        scope: {
            fileread: "="
        },
        link: function (scope, element, attributes) {
            element.bind("change", function (changeEvent) {
                var reader = new FileReader();
                reader.onload = function (loadEvent) {
                     $('#imagesrc').attr('src', loadEvent.target.result);
                    scope.$apply(function () {
                        scope.fileread = loadEvent.target.result;

                    });
                }
                reader.readAsDataURL(changeEvent.target.files[0]);
            });
        }
    }
}]);