angular.module('app.services.ui', [])
  .factory('UI', ['$http', function($http) {
    var UI = function() {
      this.inputs = {};
    };
    UI.prototype.initInputIfAbsent = function(type, id) {
      if(!this.inputs[type]) {
        this.inputs[type] = {};
      }
      if(!this.inputs[type][id]) {
        this.inputs[type][id] = {
          'val': null,
          'callbacks': []
        };
      }
    };
    UI.prototype.registerInput = function(type, id, handler) {
      this.initInputIfAbsent(type, id);
      var input =  this.inputs[type][id];
      var defaultBehivors = {
        'show': function() {
          this.element.show();
        },
        'hide': function() {
          this.element.hide();
        }
      };
      input['handler'] = $.extend(defaultBehivors, handler);
    },
    UI.prototype.getInput = function(type, id, handler) {
      this.initInputIfAbsent(type, id);
      var input =  this.inputs[type][id];
      return input['handler'];
    },
    UI.prototype.getInputs = function() {
      return this.inputs;
    },
    UI.prototype.notifyInputChange = function(type, id, val) {
      this.initInputIfAbsent(type, id);
      var input =  this.inputs[type][id];
      var old = input['val'];
      if(old != val) {
        // console.log([type, id, val].join(', '));
        input['val'] = val;
        (function(val, input, inputs) {
          setTimeout(function() {
            for(var i in input['callbacks']) {
              input['callbacks'][i](val, inputs);
            }
          }, 10);
        })(val, input, this.inputs);
      }
    };
    UI.prototype.onInputChanged = function(type, id, cb) {
      this.initInputIfAbsent(type, id);
      var input =  this.inputs[type][id];
      input['callbacks'].push(cb);
    };
    return new UI();
  }]);