/*
 * Calaca - Search UI for Elasticsearch
 * https://github.com/romansanchez/Calaca
 * http://romansanchez.me
 * @rooomansanchez
 * 
 * v1.2.0
 * MIT License
 */


/* Module */
window.Calaca = angular.module('calaca', ['elasticsearch', 'ngRoute', 'edubridgeControllers', 'rzModule'],
    ['$locationProvider', function($locationProvider){
        $locationProvider.html5Mode(true);
    }]
);

window.Calaca.config(['$routeProvider',
    function($routeProvider, $route) {
        //alert($location.path());
        //alert($route.originalPath);
        $routeProvider.
            when('/', {
                templateUrl: 'templates/course_search.html',
                controller: 'courseSearchCtrl'
            }).
            when('/results/:courseid', {
                templateUrl: 'templates/results.html',
                controller: 'videoRecommendationsCtrl'
            }).
            otherwise({
                redirectTo: '/'
            });
    }]);