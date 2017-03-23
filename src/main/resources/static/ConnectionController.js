'use strict';
angular.module('ConnectionModule',[])
    .controller('ConnectionController', ['$scope','$rootScope','$http',
            function($scope, $rootScope, $http) {


 var stompClient = null;

    $rootScope.$on('Disconnect', function(){
           $scope.disconnect();
        //   $scope.remove();
    });

    $scope.$on('$destroy', function(){
        $scope.disconnect();
        $scope.$destroy();
    });

    $scope.event='';

    $scope.connect = function(data) {
        var socket = new SockJS('http://localhost:8080/gs-guide-websocket');
        stompClient = Stomp.over(socket);
         stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/events/get/'+data, function (event) {
                $scope.$apply(function () {
                    $scope.event = JSON.parse(event.body).path + ': ' +
                    JSON.parse(event.body).eventType;
                });
            });
        });
    }

    $scope.startWatching = function(path){
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/app/start',
            crossDomain: true,
            contentType: 'application/text; charset=UTF-8',
            data: path,
            dataType: 'json',
            success: function(data) {
        //                        $scope.obtainEndPoint(data);
                $scope.connect(data);

            },
            error: function(){
                alert('error')
            }
        });
    }

    $scope.disconnect = function() {
        if (stompClient != null) {
            stompClient.disconnect();
            stompClient = null;
        }
        console.log('Disconnected');
    }

}]);