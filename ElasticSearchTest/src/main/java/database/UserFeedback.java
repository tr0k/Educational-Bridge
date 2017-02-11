package database;

public class UserFeedback {
    private String courseCode;
    private String moocVideoId;
    private int positiveVotes;
    private int negativeVotes;

    public UserFeedback(String courseCode, String moocVideoId, int positiveVotes, int negativeVotes) {
        this.courseCode = courseCode;
        this.moocVideoId = moocVideoId;
        this.positiveVotes = positiveVotes;
        this.negativeVotes = negativeVotes;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getMoocVideoId() {
        return moocVideoId;
    }

    public void setMoocVideoId(String moocVideoId) {
        this.moocVideoId = moocVideoId;
    }

    public int getPositiveVotes() {
        return positiveVotes;
    }

    public void setPositiveVotes(int positiveVotes) {
        this.positiveVotes = positiveVotes;
    }

    public int getNegativeVotes() {
        return negativeVotes;
    }

    public void setNegativeVotes(int negativeVotes) {
        this.negativeVotes = negativeVotes;
    }
}
