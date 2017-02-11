package elasticsearch;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;

/**
 * Created by tr0k on 2016-03-10.
 */
public class SearchClientService implements ISearchClientService{

    /**
     * Getting client instance.
     * @return client instance
     */
    public Client getClient() {
        //Getting client instance
        String elasticPath = "lib\\2.2.0\\elasticsearch-2.2.0";
        Node node = NodeBuilder.nodeBuilder()
                .settings(Settings.builder()
                        .put("path.home", elasticPath)
                        .build()).client(true).node();

        return node.client();
    }
}
