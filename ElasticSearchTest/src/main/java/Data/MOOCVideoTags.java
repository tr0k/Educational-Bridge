package data;

/**
 * Columns describing coursera course.
 * Created by tr0k on 2016-03-19.
 */
public enum MOOCVideoTags {
    VIDEO_TITLE ("VIDEO_TITLE"),
    VIDEO_URL ("VIDEO_URL"),
    VIDEO_DESC ("VIDEO_DESC"),
    COURSE_TITLE ("COURSE_TITLE"),
    COURSE_INFO ("COURSE_INFO"),
    COURSE_URL ("COURSE_URL");
    
//    VIDEO_TITLE ("video title"),
//    VIDEO_URL ("video url"),
//    VIDEO_DESC ("video desc"),
//    COURSE_TITLE ("course title"),
//    COURSE_INFO ("course info"),
//    COURSE_URL ("course url");

    private final String text;

    /**
     * @param text
     */
    private MOOCVideoTags(final String text) {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }

    static public String[] tags(){
        MOOCVideoTags[] tags = values();
        String[] names = new String[tags.length];

        for (int i = 0; i < tags.length; i++) {
            names[i] = tags[i].name();
        }

        return names;
    }
}
