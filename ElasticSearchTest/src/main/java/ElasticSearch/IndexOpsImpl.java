package elasticsearch;

import org.elasticsearch.client.Client;

import java.util.HashMap;
import java.util.Map;


/**
 * Delivers operations for indexes in elastic search engine.
 * Created by tr0k on 2016-03-10.
 */
public class IndexOpsImpl implements IIndexOps {

    private Client client;

    public IndexOpsImpl(Client client) {
        this.client = client;
    }

    /**
     * Type a JSON document into a specific index.
     * @param tags
     * @param content
     * @return
     */
    public Map<String, Object> putJsonDocument(String[] tags, String[] content) {
        if(tags.length!=content.length)
            throw new IllegalArgumentException();

        Map<String, Object> jsonDocument = new HashMap<String, Object>();

        for(int i = 0; i < tags.length && i < content.length; ++i){
           jsonDocument.put(tags[i], content[i]);
        }
        return jsonDocument;
    }


    /**
     * Generate index with standard settings.
     * @param index
     * @param type
     * @param id
     * @param jsonDoc
     */
    public void putIndex(String index, String type, String id, Map<String, Object> jsonDoc) {
        client.prepareIndex(index, type, id)
                .setSource(jsonDoc).execute().actionGet();
    }

    /**
     * Generate index with standard settings.
     * @param index
     * @param type
     * @param jsonDoc
     */
    public void putIndex(String index, String type, Map<String, Object> jsonDoc) {
        client.prepareIndex(index, type)
                .setSource(jsonDoc).execute().actionGet();
    }


}
