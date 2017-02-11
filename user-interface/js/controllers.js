/*
 * Calaca - Search UI for Elasticsearch
 * https://github.com/romansanchez/Calaca
 * http://romansanchez.me
 * @rooomansanchez
 * 
 * v1.2.0
 * MIT License
 */

/* Calaca Controller
 *
 * On change in search box, search() will be called, and results are bind to scope as results[]
 *
*/

var edubridgeControllers = angular.module('edubridgeControllers', []);

edubridgeControllers.controller('videoRecommendationsCtrl', ['recommendationService', '$scope', '$routeParams', '$http', '$location', 'feedbackService',
    function (recommendations, $scope, $routeParams, $http, $location, feedbackService) {
        $scope.courseid = $routeParams.courseid;

        $scope.coursequery = $location.search().query;
        $scope.coursename = $location.search().coursename;

        // settings sliders
        $scope.feedbackWeightSlider = {
            value: 0.3,
            options: {
                floor: 0,
                step: 0.01,
                precision: 2,
                ceil: 1
            }
        };

        //Init empty array
        $scope.results = [];

        //Init offset
        $scope.offset = 0;

        $scope.votes = [];

        var paginationTriggered;
        var maxResultsSize = CALACA_CONFIGS.size;
        var searchTimeout;


        $scope.delayedSearch = function(mode) {
            clearTimeout(searchTimeout);
            searchTimeout = setTimeout(function() {
                $scope.search(mode)
            }, CALACA_CONFIGS.search_delay);
        };

        //On search, reinitialize array, then perform search and load results
        $scope.search = function(m){

            $scope.results = [];
            $scope.offset = m == 0 ? 0 : $scope.offset;//Clear offset if new query
            $scope.loading = m == 0 ? false : true;//Reset loading flag if new query

            if(m == -1 && paginationTriggered) {
                if ($scope.offset - maxResultsSize >= 0 ) $scope.offset -= maxResultsSize;
            }
            if(m == 1 && paginationTriggered) {
                $scope.offset += maxResultsSize;
            }
            $scope.paginationLowerBound = $scope.offset + 1;
            $scope.paginationUpperBound = ($scope.offset == 0) ? maxResultsSize : $scope.offset + maxResultsSize;
            $scope.loadResults(m);
        };

        //Load search results into array
        $scope.loadResults = function(m) {

            recommendations.search($scope.courseid, $scope.feedbackWeightSlider.value, m, $scope.offset).then(function(a) {

                //Load results
                var i = 0;
                for(;i < a.hits.length; i++){
                    $scope.results.push(a.hits[i]);
                }

                //Set time took
                $scope.timeTook = a.timeTook;

                //Set total number of hits that matched query
                $scope.hits = a.hitsCount;

                //Pluralization
                $scope.resultsLabel = ($scope.hits != 1) ? "results" : "result";

                //Check if pagination is triggered
                paginationTriggered = $scope.hits > maxResultsSize;

                //Set loading flag if pagination has been triggered
                if(paginationTriggered) {
                    $scope.loading = true;
                }
            });
        };

        $scope.paginationEnabled = function() {
            return paginationTriggered ? true : false;
        };

        $scope.backToCourseSelection = function() {
            $location.path('/').search({query: $scope.coursequery});
        };

        $scope.voteUp = function(id) {
            var voteValue = 1;

            $scope.votes[id] = voteValue;
            feedbackService.feedback(id, $scope.courseid, voteValue);
        };

        $scope.voteDown = function(id) {
            var voteValue = -1;

            $scope.votes[id] = voteValue;
            feedbackService.feedback(id, $scope.courseid, voteValue);
        };

        $scope.search($scope.courseid);

    }]);





edubridgeControllers.controller('courseSearchCtrl', ['blackboardService', '$scope', '$location',
    function(bbCourses, $scope, $location){

        //Init empty array
        $scope.results = [];

        //Init offset
        $scope.offset = 0;

        var paginationTriggered;
        var maxResultsSize = CALACA_CONFIGS.size;
        var searchTimeout;

        $scope.delayedSearch = function(mode) {
            clearTimeout(searchTimeout);
            searchTimeout = setTimeout(function() {
                $scope.search(mode)
            }, CALACA_CONFIGS.search_delay);
        };

        //On search, reinitialize array, then perform search and load results
        $scope.search = function(m){
            $scope.results = [];
            $scope.offset = m == 0 ? 0 : $scope.offset;//Clear offset if new query
            $scope.loading = m == 0 ? false : true;//Reset loading flag if new query

            if(m == -1 && paginationTriggered) {
                if ($scope.offset - maxResultsSize >= 0 ) $scope.offset -= maxResultsSize;
            }     
            if(m == 1 && paginationTriggered) {
                $scope.offset += maxResultsSize;
            }
            $scope.paginationLowerBound = $scope.offset + 1;
            $scope.paginationUpperBound = ($scope.offset == 0) ? maxResultsSize : $scope.offset + maxResultsSize;
            $scope.loadResults(m);
        };

        //Load search results into array
        $scope.loadResults = function(m) {
            bbCourses.search($scope.query, m, $scope.offset).then(function(a) {

                //Load results
                var i = 0;
                for(;i < a.hits.length; i++){
                    $scope.results.push(a.hits[i]);
                }

                //Set time took
                $scope.timeTook = a.timeTook;

                //Set total number of hits that matched query
                $scope.hits = a.hitsCount;

                //Pluralization
                $scope.resultsLabel = ($scope.hits != 1) ? "results" : "result";

                //Check if pagination is triggered
                paginationTriggered = $scope.hits > maxResultsSize;

                //Set loading flag if pagination has been triggered
                if(paginationTriggered) {
                    $scope.loading = true;
                }
            });
        };

        $scope.paginationEnabled = function() {
            return paginationTriggered ? true : false;
        };

        $scope.selectCourse = function(id, heading) {
            $location.path('results/' + encodeURIComponent(id)).search({query: encodeURIComponent($scope.query), coursename: heading});
        };

        //search if query is already given
        if($location.search().query) {
            $scope.query = decodeURIComponent($location.search().query);
            $scope.search(1);
        }
    }]
);