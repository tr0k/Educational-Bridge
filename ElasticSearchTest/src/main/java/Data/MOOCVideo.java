package data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Storing data from coursera.org.
 * Created by tr0k on 2016-03-10.
 */
@XmlRootElement(name = "resultItem")
@XmlAccessorType(XmlAccessType.FIELD)
public class MOOCVideo extends ESEntity{
    private String videoTitle;
    private String videoURL;
    private String videoDesc;
    private String courseTitle;
    private String courseInfo;
    private String courseURL;
    private String id;
    private float ESRank;
    private float userRank;
    private float finalRank;

    public MOOCVideo() {
    }

    public MOOCVideo(String videoTitle, String videoURL, String videoDesc, String courseTitle, String courseInfo, String courseURL, String id, float ESRank,
            float userRank, float finalRank) {
        this.videoTitle = videoTitle;
        this.videoURL = videoURL;
        this.videoDesc = videoDesc;
        this.courseTitle = courseTitle;
        this.courseInfo = courseInfo;
        this.courseURL = courseURL;
        this.id = id;
        this.ESRank = ESRank;
        this.userRank = userRank;
        this.finalRank = finalRank;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseInfo() {
        return courseInfo;
    }

    public void setCourseInfo(String courseInfo) {
        this.courseInfo = courseInfo;
    }

    public String getCourseURL() {
        return courseURL;
    }

    public void setCourseURL(String courseURL) {
        this.courseURL = courseURL;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getVideoDesc() {
        return videoDesc;
    }

    public void setVideoDesc(String videoDesc) {
        this.videoDesc = videoDesc;
    }

    public float getESRank() {
        return ESRank;
    }

    public void setESRank(float ESRank) {
        this.ESRank = ESRank;
    }

    public float getUserRank() {
        return userRank;
    }

    public void setUserRank(float userRank) {
        this.userRank = userRank;
    }

    public float getFinalRank() {
        return finalRank;
    }

    public void setFinalRank(float finalRank) {
        this.finalRank = finalRank;
    }

    @Override
    public String[] getListOfFields(){
        return new String[] {videoTitle, videoURL, videoDesc, courseTitle, courseInfo, courseURL};//, id};
    }
}
