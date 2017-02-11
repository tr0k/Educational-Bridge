package data;

import java.util.AbstractList;
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
public class VideoList {
    @XmlElement(name="resultItem")
    private List<MOOCVideo> resultList;
    private long searchTime;
    private long numberOfHits;

    public List<MOOCVideo> getResultList() {
        return resultList;
    }

    public void setResultList(List<MOOCVideo> resultList) {
        this.resultList = resultList;
    }

    public long getSearchTime() {
        return searchTime;
    }

    public void setSearchTime(long searchTime) {
        this.searchTime = searchTime;
    }

    public long getNumberOfHits() {
        return numberOfHits;
    }

    public void setNumberOfHits(long numberOfHits) {
        this.numberOfHits = numberOfHits;
    }
    
    public void filterVideos(int size, int from) {
        resultList = resultList.subList(from, from+size);
    }

}
