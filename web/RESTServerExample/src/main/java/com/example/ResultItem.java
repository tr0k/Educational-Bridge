package com.example;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "resultItem")
@XmlAccessorType (XmlAccessType.FIELD)
public class ResultItem {
    
    private String title;
    private String decription;
    private int userRating;
    private String linkToSource; //URL object?

    public ResultItem() {
    }

    public ResultItem(String title, String decription, int userRating, String linkToSource) {
        this.title = title;
        this.decription = decription;
        this.userRating = userRating;
        this.linkToSource = linkToSource;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public int getUserRating() {
        return userRating;
    }

    public void setUserRating(int userRating) {
        this.userRating = userRating;
    }

    public String getLinkToSource() {
        return linkToSource;
    }

    public void setLinkToSource(String linkToSource) {
        this.linkToSource = linkToSource;
    }
}
