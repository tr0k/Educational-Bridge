package elasticsearch;

import data.StudyGuideCourse;
import java.util.Map;
import org.elasticsearch.action.search.SearchResponse;

/**
 * Delivers operations on search engine.
 * Created by tr0k on 2016-03-10.
 */
public interface ISearch {
    Map<String, Object> get(String index, String type, String id);
    SearchResponse search(String index, String type, StudyGuideCourse course, int from);
//    SearchHit[] search(String index, String type, String field, String value);
//    SearchResponse multiMatchQuerySearch(String index, String type, String[] fields, String queryText, int from);
    void updateDoc();
    void deleteDoc();
}
