package ro.ase.grupa1094.Parsere;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ro.ase.grupa1094.Clase.Feedback;

public class FeedbackParser {

    private static final String MESAJ = "mesaj";
    private static final String RATING = "rating";
    private static final String DATA = "data";
    private static final String USER_ID = "userId";

    public static List<Feedback> parsareJson(String json) {
        try {
            JSONArray jsonArray = new JSONArray(json);
            return parsareFeedbackuri(jsonArray);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Feedback> parsareFeedbackuri(JSONArray jsonArray) throws JSONException {
        List<Feedback> feedbackuri = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            feedbackuri.add(parsareFeedback(jsonArray.getJSONObject(i)));
        }
        return feedbackuri;
    }

    private static Feedback parsareFeedback(JSONObject jsonObject) throws JSONException {
        String mesaj = jsonObject.getString(MESAJ);
        int rating = jsonObject.getInt(RATING);
        String data = jsonObject.getString(DATA);
        int userId = jsonObject.getInt(USER_ID);

        Feedback feedback = new Feedback(mesaj, data, rating);
       // feedback.setRating(rating);
        feedback.setUserId(userId);
        return feedback;
    }
}
