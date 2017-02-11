package com.irgroup11.educationalbridge3.rest;

import com.irgroup11.educationalbridge3.data.Course;
import com.irgroup11.educationalbridge3.data.CourseList;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;

public class RESTClient {

    public List<Course> getResults(String course) {
        Client client = ClientBuilder.newClient(new ClientConfig().register(LoggingFilter.class));
        WebTarget webTarget = client.target("http://localhost:8282/elasticsearchenginehub/searchresults").path(course);

        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_XML);
        //Response response = invocationBuilder.post(Entity.entity(course, MediaType.TEXT_PLAIN));
        Response response = invocationBuilder.get();

//        ResultItem resultItem = response.readEntity(ResultItem.class);
        CourseList results = response.readEntity(CourseList.class);
        List<Course> listOfResults = results.getResultList();

//        String testResult = response.readEntity(String.class);
//        System.out.println(testResult);
//        Employees employees = response.readEntity(Employees.class);
//List<Employee> listOfEmployees = employees.getEmployeeList();
        return listOfResults;

    }
}
