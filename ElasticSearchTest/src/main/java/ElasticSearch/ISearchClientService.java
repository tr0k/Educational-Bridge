package elasticsearch;

import org.elasticsearch.client.Client;

/**
 * Interface for getting client for working with elastic search engine.
 * Created by tr0k on 2016-03-10.
 */
public interface ISearchClientService {
    /**
     * @return search engine client instance
     */
    Client getClient();

}
