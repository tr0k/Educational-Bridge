//package elasticsearch;
//
//import data.MOOCVideoTags;
//import dataimport.ElasticSearchMain;
//import org.elasticsearch.client.Client;
//import org.elasticsearch.search.SearchHit;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import java.util.Map;
//import org.elasticsearch.action.search.SearchResponse;
//
///**
// * Created by tr0k on 2016-03-12.
// */
//public class SearchMOOCImplTest {
//
//    private static ISearch search;
//
//    @BeforeClass
//    public static void setupElasticClient(){
//        ISearchClientService clientService = new SearchTransportClientService();
//        Client client = clientService.getClient();
//        search = new SearchMOOCImpl(client);
//    }
//
//    @Test
//    public void testGet() throws Exception {
//
//    }
//
//    @Test
//    public void testSearch() throws Exception {
//        SearchResponse response = search.search(ElasticSearchMain.INDEX, ElasticSearchMain.MOOC_TYPE, "Information Retrieval", 10);
//        SearchHit[] results = response.getHits().getHits();
//        
//        System.out.println("Current results: " + results.length);
//        for (SearchHit hit : results) {
//            System.out.println("------------------------------");
//            Map<String, Object> result = hit.getSource();
//            System.out.println(result);
//        }
//    }
//
//    @Test
//    public void testUpdateDoc() throws Exception {
//
//    }
//
//    @Test
//    public void testDelteDoc() throws Exception {
//
//    }
//}