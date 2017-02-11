package Data;

import database.Database;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("/feedback")
public class Feedback {
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addFeedback (UserFeedbackJSON feedback) {
        Database.addFeedback(feedback);
    }
    
}
