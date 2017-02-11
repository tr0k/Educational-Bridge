package elasticsearch;

import data.MOOCVideoTags;
import data.StudyGuideCourse;
import java.util.List;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

import java.util.Map;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import static org.elasticsearch.index.query.QueryBuilders.disMaxQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;

/**
 * Created by tr0k on 2016-03-10.
 */
public class SearchMOOCImpl implements ISearch {

    private final Client client;
    // The search will be executed on these fields.
    private final String[] fields = {
        MOOCVideoTags.VIDEO_TITLE.toString(),
        MOOCVideoTags.VIDEO_DESC.toString(),
        MOOCVideoTags.COURSE_TITLE.toString(),
        MOOCVideoTags.COURSE_INFO.toString()
    };

    public SearchMOOCImpl(Client client) {
        this.client = client;
    }

    @Override
    public Map<String, Object> get(String index, String type, String id) {
        GetResponse getResponse = client.prepareGet(index, type, id).execute().actionGet();
        return getResponse.getSource();
    }

    @Override
    public SearchResponse search(String index, String type, StudyGuideCourse course, int from) {
        return tunedMultiMatchQuerySearch(index, type, course, from);
    }

    public SearchResponse termQuerySearch(String index, String type, String field, String value) {
        SearchResponse response = client.prepareSearch(index)
                .setTypes(type)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.termQuery(field, value))
                .setFrom(0).setSize(60).setExplain(true)
                .execute()
                .actionGet();

        return response;
    }

    public SearchResponse multiMatchQuerySearch(String index, String type, StudyGuideCourse course, int from) {

        String query = course.getCourseTitle() + " " + course.getCourseContent() + " " + course.getCourseStudyGoals();

        SearchResponse response = client.prepareSearch(index)
                .setTypes(type)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.multiMatchQuery(query, fields))
                .setFrom(from).setSize(10).setExplain(true)
                .execute()
                .actionGet();

        return response;
    }

    public SearchResponse tunedMultiMatchQuerySearch(String index, String type, StudyGuideCourse course, int from) {

        // mooc course title boost
        fields[2] += "^10";

        // query with study guide course title is boosted
        MultiMatchQueryBuilder titleQuery = QueryBuilders.multiMatchQuery(course.getCourseTitle(), fields).boost(2f);
        MultiMatchQueryBuilder contentQuery = QueryBuilders.multiMatchQuery(course.getCourseContent(), fields);
        MultiMatchQueryBuilder goalsQuery = QueryBuilders.multiMatchQuery(course.getCourseStudyGoals(), fields);

        QueryBuilder query = disMaxQuery()
                .add(titleQuery)
                .add(contentQuery)
                .add(goalsQuery);
//                .boost(1.2f)
//                .tieBreaker(0.7f);

        SearchResponse response = client.prepareSearch(index)
                .setTypes(type)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(query)
                .setSize(100).setExplain(true)
                .execute()
                .actionGet();

        return response;
    }

    @Override
    public void updateDoc() {
        //TODO
    }

    @Override
    public void deleteDoc() {
        //TODO
    }
}
