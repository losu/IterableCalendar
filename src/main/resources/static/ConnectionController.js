'use strict';
angular.module('ConnectionModule',[])
    .controller('ConnectionController', ['$scope','$rootScope','$http', '$timeout',
            function($scope, $rootScope, $http, $timeout) {

    var host = "http://" + window.location.host;
    var stompClient = null;

    $rootScope.$on('Disconnect', function(){
           $scope.disconnect();
    });

    $scope.$on('$destroy', function(){
        $scope.disconnect();
        $scope.$destroy();
    });

    $scope.websocket='';
    $scope.event='';
    $scope.label = '';
    $scope.hideLabel = true;

    $scope.connect = function(data, callback, path) {
        var socket = new SockJS(host+'/gs-guide-websocket');
        stompClient = Stomp.over(socket);
         stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/events/get/'+data, function (event) {
                $scope.$apply(function () {
                    $scope.event = JSON.parse(event.body).path + ': ' +
                    JSON.parse(event.body).eventType;
                });
            });
          callback(path,data);
        });
    }

    $scope.startWatching = function(path, endpoint){
        $http({
            method: 'POST',
            url: host+'/app/start/'+endpoint,
            data: path,
            withCredentials: true
            }).then(function successCallback(response) {
                    $scope.websocket=endpoint;
             }, function errorCallback(response) {
                    $scope.label=response.data;
                    console.log(response.toString());
                    $scope.hideLabel=false;
                    $timeout(function () {$scope.hideLabel = true}, 1000);
             });
        }

    $scope.obtainEndPoint = function(path){
        $http({
            method: 'GET',
            url: host + '/app/obtainEndPoint',
            withCredentials: true
            }).then(function successCallback(response) {
                $scope.connect(response.data, $scope.startWatching, path);
               // $scope.connect(response.data);//, $scope.startWatching, path, response.data);
            }, function errorCallback(response) {
                $scope.label=response.data;
                console.log(response.toString());
                $scope.hideLabel=false;
                $timeout(function () {$scope.hideLabel = true}, 1000);
            });
        }

    $scope.disconnect = function(){

        if (stompClient != null) {
            stompClient.disconnect();
            stompClient = null;
        }
        $http({
            method: 'POST',
            url: host + '/app/stop/' + $scope.websocket,
            data: '',
            withCredentials: true
            }).then(function successCallback(response) {
                console.log("disconnected from " + $scope.websocket)
            }, function errorCallback(response) {
                $scope.label=response.data;
                $scope.hideLabel=false;
                $timeout(function () {$scope.hideLabel = true}, 1000);
            });
    }
}]);