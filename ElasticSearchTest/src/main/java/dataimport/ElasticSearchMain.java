package dataimport;

import csv.CSVCourseParser;
import csv.CSVParserBlackboard;
import csv.CSVParserMOOC;
import data.*;
import elasticsearch.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Client;

import java.util.List;
import java.util.Map;

/**
 * Created by tr0k on 2016-03-10.
 */
public class ElasticSearchMain {

    public static final String[] MOOC_TAGS = MOOCVideoTags.tags();
    public static final String[] STUDY_GUIDE_TAGS = StudyGuideCourseTags.tags();
//    private static String index = "moocs";
    public static final String INDEX = "educational_bridge";
//    private static String indexType = "mooc";
    public static final String MOOC_TYPE = "mooc_video";
    public static final String STUDY_GUIDE_TYPE = "study_guide_course";
    public static final String ID = "4";

    public static void main(String[] args) {
        //Create ES Client and connect
        ISearchClientService clientService = new SearchTransportClientService();
        Client client = clientService.getClient();

        getDataFromCSV(client);
//        printSpecifiedDataFromElasticSearch(client);

        client.close();
    }

    private static void printSpecifiedDataFromElasticSearch(Client client) {
        //Getting and printing searched MOOC
        ISearch search = new SearchMOOCImpl(client);
        Map<String, Object> searchDoc = search.get(INDEX, MOOC_TYPE, ID);
        printJsonDocument(searchDoc, client);
    }

    private static void getDataFromCSV(Client client) {
        IIndexOps indexOps = new IndexOpsImpl(client);

        //Get MOOCs from CSV
        String pathToCsv = "..\\data\\items.csv";

        List<MOOCVideo> moocsCourseList = (List<MOOCVideo>) getCourseFromCSV(pathToCsv, new CSVParserMOOC());

        //Add MOOCs to elasticsearch
        for (MOOCVideo course : moocsCourseList) {
            //put index
            indexOps.putIndex(INDEX, MOOC_TYPE, indexOps.putJsonDocument(MOOC_TAGS, course.getListOfFields()));
        }

        //Get courses from blackboard
        pathToCsv = "..\\data\\courses.csv";

        List<StudyGuideCourse> blackboardCourseList = (List<StudyGuideCourse>) getCourseFromCSV(pathToCsv, new CSVParserBlackboard());
//        String[] blackboardTags = StudyGuideCourseTags.tags();

        //Add courses from blackboard to elasticsearch
        for (StudyGuideCourse course : blackboardCourseList) {
            //put index
//            indexOps.putIndex(index, studyGuidType, indexOps.putJsonDocument(studyGuideTags, course.getListOfFields()));
            indexOps.putIndex(INDEX, STUDY_GUIDE_TYPE, course.getCourseId(), indexOps.putJsonDocument(STUDY_GUIDE_TAGS, course.getListOfFields()));
        }
    }

    /**
     * Get course content from csv file separated with comma.
     * @param pathToCsv Path to csv file
     * @return List of courses from file.
     */
    private static List<? extends ESEntity> getCourseFromCSV(final String pathToCsv, CSVCourseParser csvCourseParser) {
        CSVFormat format = CSVFormat.EXCEL.withHeader().withDelimiter(',');

        CSVParser parser = csvCourseParser.parseFile(pathToCsv, format);
        return csvCourseParser.getCourseList(parser);
    }

    //Prints JSON docuemnt
    public static void printJsonDocument(Map<String, Object> source, Client clientInstance)
    {
        GetResponse getResponse = clientInstance.prepareGet(INDEX, MOOC_TYPE, ID).execute().actionGet();
        System.out.println("------------------------------");
        System.out.println(MOOC_TAGS[0] +" "+ getResponse.getIndex());
        System.out.println(MOOC_TAGS[1] +" "+ getResponse.getType());
        System.out.println(MOOC_TAGS[2] +" "+ getResponse.getId());
        System.out.println(MOOC_TAGS[3] +" "+ getResponse.getVersion());
        System.out.println(source);
        System.out.println("------------------------------");
    }
}