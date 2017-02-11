package com.irgroup11.educationalbridge3.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "resultItem")
@XmlAccessorType(XmlAccessType.FIELD)
public class Course {
    private String videoTitle;
    private String videoURL;
    private String videoDesc;
    private String courseTitle;
    private String courseInfo;
    private String courseURL;

    public Course() {
    }

    public Course(String videoTitle, String videoURL, String videoDesc, String courseTitle, String courseInfo, String courseURL) {
        this.videoTitle = videoTitle;
        this.videoURL = videoURL;
        this.videoDesc = videoDesc;
        this.courseTitle = courseTitle;
        this.courseInfo = courseInfo;
        this.courseURL = courseURL;
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

    public String[] getListOfFields(){
        return new String[] {videoTitle, videoURL, videoDesc, courseTitle, courseInfo, courseURL};
    }
}
