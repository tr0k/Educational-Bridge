package secondRanking;

import data.MOOCVideo;
import data.VideoList;
import database.UserFeedback;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FinalRankingEngine {

    private List<MOOCVideo> videos;
    List<UserFeedback> feedbackList;
    List<Float> feedbackScores;

    public void rankVideos(VideoList moocVideos, List<UserFeedback> feedbackList, float userFeedbackWeight) {
        this.videos = moocVideos.getResultList();
        this.feedbackList = feedbackList;
        this.feedbackScores = new ArrayList<>();

        normalizeESScores();
        normalizeFeedbackScores();

        boolean isFeedback = false;

        for (MOOCVideo video : videos) {
            for (int i = 0; i < feedbackList.size(); i++) {
                if (feedbackList.get(i).getMoocVideoId().equals(video.getId())) {
                    isFeedback = true;
                    video.setUserRank(feedbackScores.get(i));
                    video.setFinalRank((1 - userFeedbackWeight) * video.getESRank() + userFeedbackWeight * feedbackScores.get(i));
                    break;
                }
            }
            if (isFeedback) {
                isFeedback = false;
            } else {
                video.setUserRank(0.5f);
                video.setFinalRank((1 - userFeedbackWeight) * video.getESRank() + userFeedbackWeight * 0.5f);
            }
        }
        sortVidosByRank();
    }

    private void normalizeESScores() {
        float maxScore = videos.get(0).getESRank();
        for (MOOCVideo video : videos) {
            video.setESRank(video.getESRank() / maxScore);
        }
    }

    private void normalizeFeedbackScores() {
        float sumOfScores;
        for (UserFeedback feedback : feedbackList) {
            sumOfScores = feedback.getNegativeVotes() + feedback.getPositiveVotes();
            feedbackScores.add(((feedback.getPositiveVotes() - feedback.getNegativeVotes()) / sumOfScores + 1) / 2);
        }
    }

    private void sortVidosByRank() {
        Collections.sort(videos, rankComparator);
    }

    public static final Comparator<MOOCVideo> rankComparator = new Comparator<MOOCVideo>() {

        @Override
        public int compare(MOOCVideo v1, MOOCVideo v2) {
            int result;
            float subst = v1.getFinalRank() - v2.getFinalRank();
            if (subst > 0) {
                result = -1;
            } else if (subst == 0) {
                result = 0;
            } else {
                result = 1;
            }
            return result;
        }

    };
}
