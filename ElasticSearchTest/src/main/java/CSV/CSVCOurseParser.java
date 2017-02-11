package csv;

import data.ESEntity;
import org.apache.commons.csv.CSVParser;

import java.util.List;

/**
 * Parser for retriving courses from csv files
 * Created by tr0k on 2016-03-24.
 */
public abstract class CSVCourseParser implements ICSVParser {
    public abstract List<? extends ESEntity> getCourseList(CSVParser csvParser);
}
