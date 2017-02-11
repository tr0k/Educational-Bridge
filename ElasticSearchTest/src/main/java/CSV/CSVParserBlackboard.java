package csv;

import data.StudyGuideCourse;
import data.StudyGuideCourseTags;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Parser intended for courses from blackboard.
 * Created by tr0k on 2016-03-24.
 */
public class CSVParserBlackboard extends CSVCourseParser {
    /**
     * Parse csv file.
     * @param pFile
     * @param format
     * @return
     */
    public CSVParser parseFile(String pFile, CSVFormat format) {
        CSVParser csvParser = null;

        try {
            csvParser = new CSVParser(new FileReader(pFile), format);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return csvParser;
    }

    public List<StudyGuideCourse> getCourseList(CSVParser csvParser){
        List<StudyGuideCourse> courses = new ArrayList<StudyGuideCourse>();

        for (CSVRecord csvRecord : csvParser) {
            StudyGuideCourse course = new StudyGuideCourse();
            course.setCourseId(csvRecord.get(StudyGuideCourseTags.ID));
            course.setCourseTitle(csvRecord.get(StudyGuideCourseTags.TITLE));
            course.setCourseContent(csvRecord.get(StudyGuideCourseTags.CONTENT));
            course.setCourseStudyGoals(csvRecord.get(StudyGuideCourseTags.STUDY_GOALS));
            course.setCourseURL(csvRecord.get(StudyGuideCourseTags.URL));
            courses.add(course);
        }
        return courses;
    }
}
