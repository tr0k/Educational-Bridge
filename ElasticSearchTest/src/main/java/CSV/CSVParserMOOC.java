package csv;

import data.MOOCVideo;
import data.MOOCVideoTags;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Parser intended for MOOCs.
 * Created by tr0k on 2016-03-10.
 */
public class CSVParserMOOC extends CSVCourseParser {

    public CSVParser parseFile(String pFile, CSVFormat format) {
        CSVParser csvParser = null;

        try {
            csvParser = new CSVParser(new FileReader(pFile), format);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return csvParser;
    }

    public List<MOOCVideo> getCourseList(CSVParser csvParser){
        List<MOOCVideo> courses = new ArrayList<MOOCVideo>();

        for (CSVRecord csvRecord : csvParser) {
            MOOCVideo course = new MOOCVideo();
            course.setVideoURL(csvRecord.get(MOOCVideoTags.VIDEO_URL));
            course.setVideoTitle(csvRecord.get(MOOCVideoTags.VIDEO_TITLE));
            course.setVideoDesc(csvRecord.get(MOOCVideoTags.VIDEO_DESC));
            course.setCourseTitle(csvRecord.get(MOOCVideoTags.COURSE_TITLE));
            course.setCourseInfo(csvRecord.get(MOOCVideoTags.COURSE_INFO));
            course.setCourseURL(csvRecord.get(MOOCVideoTags.COURSE_URL));
            courses.add(course);
        }
        return courses;
    }
}
