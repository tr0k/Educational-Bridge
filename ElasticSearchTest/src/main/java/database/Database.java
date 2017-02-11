package database;

import Data.UserFeedbackJSON;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {

    private static String dbURL = "jdbc:derby://localhost:1527/edubridge;create=true;user=eduuser;password=test123";
    private static String tableName = "user_feedback";
    // jdbc Connection
    private static Connection conn = null;
    private static Statement stmt = null;
    
    private static String COURSE_CODE = "course_code";
    private static String MOOC_VIDEO_ID = "mooc_video_id";
    private static String POSITIVE_VOTES = "positive_votes";
    private static String NEGATIVE_VOTES = "negative_votes";

    public void init() {
        createConnection();
    }

    private void createConnection() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            conn = DriverManager.getConnection(dbURL);
        } catch (Exception except) {
            except.printStackTrace();
        }
    }

    public static void addFeedback(UserFeedbackJSON feedback) {
        try {
            stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery(
                    "select * from " + tableName +
                            " where " + COURSE_CODE + "='" + feedback.getCourseCode() +
                            "' and " + MOOC_VIDEO_ID + "='" + feedback.getMoocVideoId() + "'");

            if (results.next()) {
                int positiveVotes = results.getInt(3);
                int negativeVotes = results.getInt(4);

                if (feedback.getFedbackValue() > 0) {
                    updateFeedback(feedback.getCourseCode(), feedback.getMoocVideoId(), positiveVotes + feedback.getFedbackValue(), negativeVotes);
                } else {
                    updateFeedback(feedback.getCourseCode(), feedback.getMoocVideoId(), positiveVotes, negativeVotes - feedback.getFedbackValue());
                }
            } else if (feedback.getFedbackValue() > 0) {
                insertFeedback(feedback.getCourseCode(), feedback.getMoocVideoId(), feedback.getFedbackValue(), 0);
            } else {
                insertFeedback(feedback.getCourseCode(), feedback.getMoocVideoId(), 0, -feedback.getFedbackValue());
            }
            results.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void updateFeedback(String courseCode, String moocVideoId, int positiveVotes, int negativeVotes) {
        try {

            stmt = conn.createStatement();
            stmt.execute("update " + tableName +
                    " set " + POSITIVE_VOTES + "=" + positiveVotes + "," + NEGATIVE_VOTES + "=" + negativeVotes +
                    " where " + COURSE_CODE + "='" + courseCode + "' and " + MOOC_VIDEO_ID + "='" + moocVideoId + "'");
            stmt.close();
        } catch (SQLException sqlExcept) {
            sqlExcept.printStackTrace();
        }
    }

    public static void insertFeedback(String courseCode, String moocVideoId, int positiveVotes, int negativeVotes) {
        try {

            stmt = conn.createStatement();
            stmt.execute("insert into " + tableName + " values ('"
                    + courseCode + "','" + moocVideoId + "'," + positiveVotes + "," + negativeVotes + ")");
            stmt.close();
        } catch (SQLException sqlExcept) {
            sqlExcept.printStackTrace();
        }
    }

    public static List<UserFeedback> selectUserFeedbackByCoursId(String courseId) {
        List<UserFeedback> userFeedbackList = new ArrayList<>();
        try {
            stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery("select * from " + tableName + " where " + COURSE_CODE + "='" + courseId + "'");

            while (results.next()) {
                String courseCode = results.getString(1);
                String moocVideoId = results.getString(2);
                int positiveVotes = results.getInt(3);
                int negativeVotes = results.getInt(4);
                userFeedbackList.add(new UserFeedback(courseCode, moocVideoId, positiveVotes, negativeVotes));
            }
            results.close();
            stmt.close();
        } catch (SQLException sqlExcept) {
            sqlExcept.printStackTrace();
        }
        return userFeedbackList;
    }

    public static List<UserFeedback> selectUserFeedback() {
        List<UserFeedback> userFeedbackList = new ArrayList<>();
        try {
            stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery("select * from " + tableName);
            ResultSetMetaData rsmd = results.getMetaData();

            while (results.next()) {
                String courseCode = results.getString(1);
                String moocVideoId = results.getString(2);
                int positiveVotes = results.getInt(3);
                int negativeVotes = results.getInt(4);
                userFeedbackList.add(new UserFeedback(courseCode, moocVideoId, positiveVotes, negativeVotes));
            }
            results.close();
            stmt.close();
        } catch (SQLException sqlExcept) {
            sqlExcept.printStackTrace();
        }
        return userFeedbackList;
    }

    public static void shutdown() {
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                DriverManager.getConnection(dbURL + ";shutdown=true");
                conn.close();
            }
        } catch (SQLException sqlExcept) {

        }

    }

}
