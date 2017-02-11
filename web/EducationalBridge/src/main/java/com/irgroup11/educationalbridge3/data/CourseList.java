package com.irgroup11.educationalbridge3.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by tr0k on 2016-03-19.
 */
@XmlRootElement(name = "resultList")
@XmlAccessorType(XmlAccessType.FIELD)
public class CourseList {
    @XmlElement(name="resultItem")
    private List<Course> resultList;

    public List<Course> getResultList() {
        return resultList;
    }

    public void setResultList(List<Course> resultList) {
        this.resultList = resultList;
    }

}
