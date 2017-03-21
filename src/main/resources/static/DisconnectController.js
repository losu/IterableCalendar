'use strict';

angular.module('DisconnectModule',[])
    .controller('DisconnectController',['$scope','$rootScope',
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