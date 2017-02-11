package elasticsearch;

import java.util.Map;

/**
 * Delivers operations for indexes in elastic search engine
 * Created by tr0k on 2016-03-10.
 */
public interface IIndexOps {
    /**
     * Type a JSON document into a specific index.
     * @param tags
     * @param content
     * @return mapped index
     */
    public Map<String, Object> putJsonDocument(String[] tags, String[] content);

    public void putIndex(String index, String type, String id, Map<String, Object> jsonDoc);
    public void putIndex(String index, String type, Map<String, Object> jsonDoc);
}
