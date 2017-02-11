package com.irgroup11.educationalbridge3.presentation;

import com.irgroup11.educationalbridge3.data.Course;
import com.irgroup11.educationalbridge3.rest.RESTClient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
//import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;

@Named(value = "mainView")
@SessionScoped
public class MainView implements Serializable {

    private List<Course> results = new ArrayList<>();
    private String course = "";
    private final RESTClient restClient;

    public MainView() {
        this.restClient = new RESTClient();
    }

//    public void addTestResults () {
//        results.add(new Course("Video1", "https://www.youtube.com/watch?v=3i2l98oCvmI", "This is a test result 1. There will be the decription of a Coursera video", "Course1"));
//        results.add(new Course("Video2", "https://www.youtube.com/watch?v=AHKiGl02NmU&index=6&list=PLE7j5FieXSTdn665136s-ip8nFrIG4iw-", "This is a test result 2. There will be the decription of a Coursera video", "Course2"));
//        results.add(new Course("Video3", "https://www.youtube.com/watch?v=KKYl6jKh7Uw&index=25&list=PLE7j5FieXSTdn665136s-ip8nFrIG4iw-", "This is a test result 3. There will be the decription of a Coursera video", "Course3"));
//    }
    
    public void searchForCourseVideos () {
        //results.clear();
        this.results = restClient.getResults(course);
        //addTestResults();
    }

    public List<Course> getResults() {
        return results;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}
