'use strict';

angular.module('FileModule',[])
    .controller('FileController',['$scope','$rootScope',
            function($scope, $rootScope ) {

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


                 },
                 error: function(){
                     alert('error')
                 }
             });
         }

}]);