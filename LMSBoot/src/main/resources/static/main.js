var lms = angular.module('lms', []);

lms.controller('adminController', function($scope, $http){
    $http({
        method: "get",
        url: "viewAuthors"
    }).then(function(success){
        $scope.authors = success.data;
        console.log($scope.authors);
    });
});