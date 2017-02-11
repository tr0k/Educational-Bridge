//package csv;
//
//import org.apache.commons.csv.CSVFormat;
//import org.apache.commons.csv.CSVParser;
//import org.apache.commons.csv.CSVRecord;
//
///**
// * Created by tr0k on 2016-03-12.
// */
//public class CSVParserMOOCTest {
//
//    @org.junit.Test
//    public void testParseFile() throws Exception {
//        CSVParserMOOC csvParserMOOC = new CSVParserMOOC();
//        String path = "C:\\Users\\Professional\\Desktop\\Erasmus\\Studies\\" +
//                      "Information Retrival\\Group Project\\Project\\testData.csv";
//        CSVFormat format = CSVFormat.RFC4180.withHeader().withDelimiter(',');
//
//        CSVParser parser = csvParserMOOC.parseFile(path, format);
//
//        for (CSVRecord csvRecord : parser) {
//            System.out.println("Video title: " + csvRecord.get("video title"));
//            System.out.println("Video URL: " + csvRecord.get("video url"));
//            System.out.println("Video Video description: " + csvRecord.get("video desc"));
//            System.out.println("MOOCCourse title: " + csvRecord.get("course title"));
//        }
//    }
//}