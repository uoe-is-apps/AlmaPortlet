/**
 * Created by rgood on 14/08/2015.
 */
/**
 * Created by rgood on 14/08/2015.
 */
(function(){
    var app = angular.module("libraryDetailsApp",[]);
    app.controller("libraryDetailsController",function($scope,$http)
     {
       $scope.errorMessage="";
       $http.get('/alma-portlet/rest/summary').success(function(data){
         $scope.libraryData = data;
       }).error(function(data,status,headers,config){
         console.error(data);
         console.error(status);
         if (data!=null&&data.message!=null)
         {
         $scope.errorMessage=data.message;
         }
         if ($scope.errorMessage=="")
         {
           $scope.errorMessage="There was a problem retrieving your library account details.";
         }
       });
     });
})();