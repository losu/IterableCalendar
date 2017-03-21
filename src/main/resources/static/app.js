'use strict';


var App = angular.module('App',['ConnectionModule','DisconnectModule']);

App.controller('MenuController',['$scope','$rootScope',
        function($scope, $rootScope ) {

    $scope.closeWatchers = function(){
        $rootScope.$emit("Disconnect", {});
        // ConnectionController.disconnect();
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/app/stop',
            crossDomain: true,
            success: function(data) {
            },

            error: function(){
                alert("error")
            }
        });
    };

}]);

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
                $element.remove();
                $scope.$destroy();
            }
        },
        templateUrl: 'WatcherDirective.html'
    };
});


////var stompClient = null;
////
////function setConnected(connected) {
////    $("#connect").prop("disabled", connected);
////    $("#disconnect").prop("disabled", !connected);
////    if (connected) {
////        $("#conversation").show();
////    }
////    else {
////        $("#conversation").hide();
////    }
////    $("#greetings").html("<tr><td></td></tr>");
////}
////
////function connect() {
////    var socket = new SockJS('/gs-guide-websocket');
////    stompClient = Stomp.over(socket);
////    stompClient.connect({}, function (frame) {
////        setConnected(true);
////        console.log('Connected: ' + frame);
////        stompClient.subscribe('/topic/greetings', function (greeting) {
////           // showGreeting(JSON.parse(greeting.body).name);
////            showGreeting(JSON.parse(greeting.body).files);
////        });
////    });
////}
////
////function disconnect() {
////    if (stompClient != null) {
////        stompClient.disconnect();
////    }
////    setConnected(false);
////    console.log("Disconnected");
////}
////
////function sendName() {
////    stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
////}
////
////function showGreeting(message) {
////    $("#greetings").text( message);
////}
////
////$(function () {
////    $("form").on('submit', function (e) {
////        e.preventDefault();
////    });
////    $( "#connect" ).click(function() { connect(); });
////    $( "#disconnect" ).click(function() { disconnect(); });
////    $( "#send" ).click(function() { sendName(); });
////});