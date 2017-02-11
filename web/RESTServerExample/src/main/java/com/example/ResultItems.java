package com.example;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "resultList")
@XmlAccessorType (XmlAccessType.FIELD)
public class ResultItems {
    @XmlElement(name="resultItem")
    private List<ResultItem> resultList;
 
    public List<ResultItem> getResultList() {
        return resultList;
    }
 
    public void setResultList(List<ResultItem> resultList) {
        this.resultList = resultList;
    }
}
