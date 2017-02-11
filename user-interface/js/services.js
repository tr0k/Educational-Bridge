/*
 * Calaca - Search UI for Elasticsearch
 * https://github.com/romansanchez/Calaca
 * http://romansanchez.me
 * @rooomansanchez
 * 
 * v1.2.0
 * MIT License
 */



/* Service to Elasticsearch */
Calaca.factory('blackboardService', ['$q', 'esFactory', '$location', function($q, elasticsearch, $location){

    //Set default url if not configured
    CALACA_CONFIGS.url = (CALACA_CONFIGS.url.length > 0)  ? CALACA_CONFIGS.url : $location.protocol() + '://' +$location.host() + ":9200";

    var client = elasticsearch({ host: CALACA_CONFIGS.url });

    var search = function(query, mode, offset){

        var deferred = $q.defer();

        if (query.length == 0) {
            deferred.resolve({ timeTook: 0, hitsCount: 0, hits: [] });
            return deferred.promise;
        }

        client.search({
                "index": 'educational_bridge',
                "type": 'study_guide_course',
                "body": {
                    "size": CALACA_CONFIGS.size,
                    "from": offset,
                    "query": {
                        "bool": {
                            "should": [
                                //{"query": {"wildcard": {"ID": {"value": "*" + query + "*"}}}},
                                //{"query": {"wildcard": {"TITLE": {"value": query}}}}
                                {"match": {"ID": "*" + query + "*"}},
                                {"match": {"TITLE": query}}
                            ]
                        }
                    }
                }
        }).then(function(result) {

                var i = 0, hitsIn, hitsOut = [], source;
                hitsIn = (result.hits || {}).hits || [];
                for(;i < hitsIn.length; i++){
                    source = hitsIn[i]._source;
                    source._id = hitsIn[i]._id;
                    source._index = hitsIn[i]._index;
                    source._type = hitsIn[i]._type;
                    source._score = hitsIn[i]._score;
                    hitsOut.push(source);
                }
                deferred.resolve({ timeTook: result.took, hitsCount: result.hits.total, hits: hitsOut });
        }, deferred.reject);

        return deferred.promise;
    };

    return {
        "search": search
    };

    }]);


Calaca.factory('recommendationService', ['$q', 'esFactory', '$location', '$http', function($q, elasticsearch, $location, $http){

    CALACA_CONFIGS.url = (CALACA_CONFIGS.url.length > 0)  ? CALACA_CONFIGS.url : $location.protocol() + '://' +$location.host() + ":9200";
    var recommendationUrl = 'http://localhost:8282/elasticsearchenginehub/searchresults/';

    var client = elasticsearch({ host: CALACA_CONFIGS.url });

    var search = function(query, feedbackWeight, mode, offset){

        var deferred = $q.defer();

        if (query.length == 0) {
            deferred.resolve({ timeTook: 0, hitsCount: 0, hits: [] });
            return deferred.promise;
        }

        $http.get(
            recommendationUrl + query + "?from=" + offset + "&feedbackWeight=" + feedbackWeight
        ).then(function(result) {

            var i = 0, hitsIn, hitsOut = [], source;
            hitsIn = (result.data || {}).resultItem || [];
            for(;i < hitsIn.length; i++){
                source = hitsIn[i];
                source.domain = getHostName(source.courseURL);
                hitsOut.push(source);
            }
            deferred.resolve({ timeTook: result.data.searchTime, hitsCount: result.data.numberOfHits, hits: hitsOut });
        }, deferred.reject);

        return deferred.promise;
    };

    function getHostName(url) {
        var match = url.match(/:\/\/(www[0-9]?\.)?(.[^/:]+)/i);
        if (match != null && match.length > 2 && typeof match[2] === 'string' && match[2].length > 0) {
            return match[2];
        }
        else {
            return null;
        }
    }

    return {
        "search": search
    };
}]);

Calaca.factory('feedbackService', ['$q', '$location', '$http', function($q, $location, $http) {
    var feedbackUrl = 'http://localhost:8282/elasticsearchenginehub/feedback/';

    var feedback = function(videoId, courseId, value){
        //var deferred = $q.defer();

        var data = {
            moocVideoId: videoId,
            courseCode: courseId,
            feedbackValue: value
        };

        $http.post(
            feedbackUrl,
            data
        );
    };

    return {
        "feedback": feedback
    };
}]);

