package data;

import org.junit.Test;

/**
 * Created by tr0k on 2016-03-24.
 */
public class MOOCCourseTagsTest {

    @Test
    public void testTags() throws Exception {
        String[] tags = MOOCVideoTags.tags();
        System.out.println(tags[0].toString());
    }
}