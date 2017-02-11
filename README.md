# Educational Bridge #

Project implemented in association with my colleagues from the Educational Bridge team for the course Information Retrieval at TU Delft.
Co-authors of the project:
- [Philipp Kogelnik](https://github.com/kogelnikp)
- [Jaroslav Ševčík](https://github.com/Jarda8)

## Downloading and running Elasticsearch on Windows:


1. Download and install ElasticSearch for example packaged in ZIP format from following website: https://www.elastic.co/downloads/elasticsearch</br>
2. Run elasticsearch.bat located in the bin folder from a command window</br>
3. If you don't have a Java runtime installed or correctly configured you will see a message saying "JAVA_HOME environment variable must be set!" . To fix that first download and install Java and then ensure that you have a JAVA_HOME environment variable configured correctly.
</br>
</br>
<u>Steps to Configure the WordNet in ElasticSearch:</u></br>
After installing the elasticsearch , you need to configure the WordNet to access the synonyms.</br>
Step 1: Create a directory called “analysis” in the elasticsearch config directory.</br>
Step 2: Download the Wordnet Zip file (the Prolog version WNprolog-3.0.tar.gz) from internet.</br>
Step 3: Extract the Zip file.</br>
Step 4: Copy the “wn_s.pl” file from the Wordnet extracted folder and Paste to elasticsearch “analysis” folder.</br>
Step 5: Start the elasticsearch server.</br>
ElasticSearch Synonyms Filter using WordNet, use following script to add it to the given index:

    PUT /blackboard
    {
    "settings" : {
    "analysis" : {
      "analyzer" : {
        "synonym" : {
        "tokenizer" : "lowercase",
        "filter" : ["synonym"]
        }
       },
       "filter" : {
       "synonym" : {
       "type" : "synonym",
       "format": "wordnet",
       "synonyms_path": "analysis/wn_s.pl"
       }
      }
     }
    }
    }

    ## Running the REST server (our elasticsearch app)

    Step 1: Run the ElasticSearch server - go to the ES installation directory -> bin -> elasticsearch.bat
    Step 2: Build the application - go to the ElasticSearchTest directory, run "mvn clean install" (you have to have maven installed and in your PATH)
    Step 3: Run the application: run "mvn exec:java" from the same folder

    You can test the app with this http request: GET http://localhost:8282/elasticsearchenginehub/searchresults/IN4325. It should return "relevant" videos for IR.
