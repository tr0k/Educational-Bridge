package Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "feedback")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserFeedbackJSON {
    private String courseCode;
    private String moocVideoId;
    private int feedbackValue;

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

    public int getFedbackValue() {
        return feedbackValue;
    }

    public void setFedbackValue(int fedbackValue) {
        this.feedbackValue = fedbackValue;
    }
}
