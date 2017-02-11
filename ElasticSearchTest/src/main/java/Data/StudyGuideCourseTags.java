package data;

/**
 * Columns describing course from blackboard.
 * Created by tr0k on 2016-03-24.
 */
public enum StudyGuideCourseTags {
    ID ("ID"),
    TITLE ("TITLE"),
    CONTENT ("CONTENT"),
    STUDY_GOALS ("STUDY_GOALS"),
    URL ("URL");
    
//    ID ("course id"),
//    TITLE ("course title"),
//    CONTENT ("course content"),
//    STUDY_GOALS ("course study goals"),
//    URL ("course url");

    private final String text;

    /**
     * @param text
     */
    private StudyGuideCourseTags(final String text) {
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
        StudyGuideCourseTags[] tags = values();
        String[] names = new String[tags.length];

        for (int i = 0; i < tags.length; i++) {
            names[i] = tags[i].name();
        }

        return names;
    }
}