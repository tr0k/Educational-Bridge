package data;

import database.Database;
import database.UserFeedback;
import dataimport.ElasticSearchMain;
import elasticsearch.ISearch;
import elasticsearch.ISearchClientService;
import elasticsearch.SearchMOOCImpl;
import elasticsearch.SearchTransportClientService;
import org.elasticsearch.client.Client;
import org.elasticsearch.search.SearchHit;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ws.rs.QueryParam;
import org.elasticsearch.action.search.SearchResponse;
import secondRanking.FinalRankingEngine;

/**
 * Root resource (exposed at "searchresults" path)
 * Created by tr0k on 2016-03-19.
 */
@Path("searchresults")
public class SearchResults {
    @GET
    @Path("/{courseCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public VideoList getResults(@PathParam("courseCode") String courseCode, @QueryParam("from") int from, @QueryParam("feedbackWeight") float feedbackWeight) {
        
        //Create ES Client and connect
        ISearchClientService clientService = new SearchTransportClientService();
        Client client = clientService.getClient();
        

        // Get study guide course by id (course code == id)
        ISearch searchClient = new SearchMOOCImpl(client);
        Map<String, Object> courseSource = searchClient.get(ElasticSearchMain.INDEX, ElasticSearchMain.STUDY_GUIDE_TYPE, courseCode);
        StudyGuideCourse course = new StudyGuideCourse(
                courseCode,
                courseSource.get(StudyGuideCourseTags.TITLE.toString()).toString(),
                courseSource.get(StudyGuideCourseTags.CONTENT.toString()).toString(),
                courseSource.get(StudyGuideCourseTags.STUDY_GOALS.toString()).toString(),
                courseSource.get(StudyGuideCourseTags.URL.toString()).toString());

        // Query for relevant videos
        VideoList videos = searchMooc(searchClient, course, from);
        
        List<UserFeedback> userFeedbackList = Database.selectUserFeedbackByCoursId(courseCode);
        
        FinalRankingEngine fre = new FinalRankingEngine();
        fre.rankVideos(videos, userFeedbackList, feedbackWeight);
        
        videos.filterVideos(10, from);
        
//        Database.insertFeedback(courseCode, "blabla", 1, 0);
//        Database.selectUserFeedback();

        return videos;
    }
    
    private VideoList searchMooc (ISearch searchClient, StudyGuideCourse course, int from) {
//        String queryString = createQueryString(course);
        VideoList courses = new VideoList();
        courses.setResultList(new ArrayList<>());
        
        // The multi match query is used for search over multiple fields.
        SearchResponse response = searchClient.search(ElasticSearchMain.INDEX, ElasticSearchMain.MOOC_TYPE, course, from);
        SearchHit[] results = response.getHits().getHits();
     
        courses.setSearchTime(response.getTook().getMillis());
        courses.setNumberOfHits(response.getHits().getTotalHits());
        
        for (SearchHit hit : results) {
            Map<String, Object> source = hit.getSource();

            MOOCVideo moocVideo = new MOOCVideo(
                    source.get(MOOCVideoTags.VIDEO_TITLE.toString()).toString(),
                    source.get(MOOCVideoTags.VIDEO_URL.toString()).toString(),
                    source.get(MOOCVideoTags.VIDEO_DESC.toString()).toString(),
                    source.get(MOOCVideoTags.COURSE_TITLE.toString()).toString(),
                    source.get(MOOCVideoTags.COURSE_INFO.toString()).toString(),
                    source.get(MOOCVideoTags.COURSE_URL.toString()).toString(),
                    hit.getId(),
                    hit.getScore(),
                    0, // this will be user score maybe
                    0); // this will be final score maybe

            courses.getResultList().add(moocVideo);
        }
        
        return courses;

    }
    
//    private String createQueryString (StudyGuideCourse course) {
//        return course.getCourseTitle() + " " + course.getCourseContent() + " " + course.getCourseStudyGoals();
//    }
}
