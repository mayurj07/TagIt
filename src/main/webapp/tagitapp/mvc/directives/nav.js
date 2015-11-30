angular.module('app.directives.nav', ['app.services.ui'])
  .directive('nav', ['UI', '$location', function(UI, $location) {
    return {
      restrict: 'E',
      replace: true,
      transclude: true,
      template: '<ul class="nav navbar-nav"></ul>',
      scope: false,
      link : function (scope, element, attrs) {
        var url = $location.$$url.substring(1);
        var el = $(element);
        var id = el.attr('id');
        var type = 'NAV';
        if(url) {
          var urlPieces = url.split('/');
          UI.notifyInputChange(type, id, urlPieces[urlPieces.length - 1]);
        } else {
          var selected = el.find('li:first');
          if(selected && selected.length > 0) {
            var e = $(selected[0]);
            UI.notifyInputChange(type, id, e.data('value'));
          }
        }
        
        UI.onInputChanged(type, id, function(val) {
          el.find('li').each(function(i, e) {
            var e = $(e);
            var v = e.data('value');
            if(v == val) {
              el.find('li.active').removeClass('active');
              var parentItem = e.parents('li.dropdown');
              if(parentItem.length == 1) {
                parentItem.addClass('active');
              } else {
                e.addClass('active');
              }
            }
          });
        });
        
        el.click(function(e) {
          var target = $(e.target);
          if(!target.is('a')) {
            return;
          }
          var li = target.parent('li');
          if(!li.hasClass('dropdown')) {
            UI.notifyInputChange(type, id, li.data('value'));
          } else {
            li.click();
          }
        });
      }
    };
  }]);