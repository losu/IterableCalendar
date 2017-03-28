'use strict';


var App = angular.module('App',['ConnectionModule','DisconnectModule','FileModule']);

 App.directive('listenerEventsDirective', function() {
        return{
            restrict: 'E',
            scope: {
                event: '='
            },
            link: function(scope, element) {
                scope.$watch('event', function(newValue, oldValue) {
                    if(newValue){
                        element.append("<tr><td>" + newValue + "</td></tr>");
                    }
                });
            }
        }
    });

App.directive('watcher', function() {
    return {
        scope: {
            onRemove:"&"
        },
        link: function($scope, $element, $attrs, $transclude) {
            $scope.remove = function() {
                //$element.remove();
                $scope.$destroy();
            }
        },
        templateUrl: 'WatcherDirective.html'
    };
});