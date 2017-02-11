package csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

/**
 * csv files parser
 * Created by tr0k on 2016-03-10.
 */
public interface ICSVParser {
    CSVParser parseFile(String pFile, CSVFormat format);
}
