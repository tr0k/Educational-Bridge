package com.example;

import java.util.ArrayList;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent to
     * the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
//    @GET
//    @Produces(MediaType.TEXT_PLAIN)
//    public String getIt() {
//        return "Got it!";
//    }
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public ResultItems getRersults() {
        
        ResultItems list = new ResultItems();
        list.setResultList(new ArrayList<ResultItem>());
        
        list.getResultList().add(new ResultItem("Video8", "blablabla", 5, "link jak krava"));
        list.getResultList().add(new ResultItem("Video9", "buhehe", 4, "link jak prase"));
        list.getResultList().add(new ResultItem("Video10", "muhehe", 1, "link jak voele debile vole"));
        
        return list;
    }
}
