'use strict';

angular.module('FileModule',[])
    .controller('FileController',['$scope','$rootScope',
            function($scope, $rootScope ) {

//    $scope.addFile = function(path){
//     $http({
//        method: 'POST',
//        url: 'http://localhost:8080/addFile',
//        data: path,
//        withCredentials: true
//        }).then(function successCallback(response) {
//                $scope.label=response.data;
//                $scope.hideLabel=false;
//                $timeout(function () {$scope.hideLabel = true}, 3000);
//             }, function errorCallback(response) {
//                $scope.label=response.data;
//                $scope.hideLabel=false;
//                $timeout(function () {$scope.hideLabel = true}, 3000);
//             });
//    }
    $scope.addFile = function(path){
             $.ajax({
                 type: 'POST',
                 url: 'http://localhost:8080/app/addFile',
                 crossDomain: true,
                 contentType: 'application/text; charset=UTF-8',
                 data: path,
                 dataType: 'json',
                 success: function(data) {
                 console.log('add file')
             //                        $scope.obtainEndPoint(data);
                    // $scope.connect(data);

                 },
                 error: function(){
                     alert('error')
                 }
             });
         }

}]);