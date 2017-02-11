package data;

/**
 * Intended for storing courses from blackboard.
 * Created by tr0k on 2016-03-24.
 */
public class StudyGuideCourse extends ESEntity{
    private String courseId;
    private String courseTitle;
    private String courseContent;
    private String courseStudyGoals;
    private String courseURL;

    public StudyGuideCourse() {
    }

    public StudyGuideCourse(String courseId, String courseTitle, String courseContent,
                            String courseStudyGoals, String courseURL) {
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.courseContent = courseContent;
        this.courseStudyGoals = courseStudyGoals;
        this.courseURL = courseURL;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseContent() {
        return courseContent;
    }

    public void setCourseContent(String courseContent) {
        this.courseContent = courseContent;
    }

    public String getCourseStudyGoals() {
        return courseStudyGoals;
    }

    public void setCourseStudyGoals(String courseStudyGoals) {
        this.courseStudyGoals = courseStudyGoals;
    }

    public String getCourseURL() {
        return courseURL;
    }

    public void setCourseURL(String courseURL) {
        this.courseURL = courseURL;
    }

    @Override
    public String[] getListOfFields() {
        return new String[] {courseId, courseTitle, courseContent, courseStudyGoals, courseURL};
    }
}
