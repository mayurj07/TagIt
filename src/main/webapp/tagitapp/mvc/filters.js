angular.module('app.filters', []).filter('moment', [
    function () {
      return function (date, method) {
        if(!date) {
          return date;
        }
        var momented = moment(date);
        return momented[method].apply(momented, Array.prototype.slice.call(arguments, 2));
      };
    }
  ]);