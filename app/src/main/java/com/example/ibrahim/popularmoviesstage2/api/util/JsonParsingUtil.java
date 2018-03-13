package com.example.ibrahim.popularmoviesstage2.api.util;

import com.example.ibrahim.popularmoviesstage2.data.model.Review;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ibrahim on 3/13/18.
 */

public class JsonParsingUtil {
    public static List<String> parseTrailers(String jsonString) throws JSONException {
        JSONObject json = new JSONObject(jsonString);
        JSONArray moviesJsonArray =
                json.getJSONObject("videos").getJSONArray("results");
        List<String> trailersList = new ArrayList<>(moviesJsonArray.length());
        for (int i = 0; i < moviesJsonArray.length(); i++) {
            JSONObject trailerJsonObject = moviesJsonArray.getJSONObject(i);
            if (trailerJsonObject.getString("site").equals("YouTube")) {
                trailersList.add(trailerJsonObject.getString("key"));
            }
        }
        return trailersList;
    }

    public static List<Review> parseReviews(String jsonString) throws JSONException {
        JSONObject json = new JSONObject(jsonString);
        JSONArray reviewsJsonArray =
                json.getJSONObject("reviews").getJSONArray("results");
        List<Review> reviewsList = new ArrayList<>(reviewsJsonArray.length());
        for (int i = 0; i < reviewsJsonArray.length(); i++) {
            JSONObject reviewJsonObject = reviewsJsonArray.getJSONObject(i);
            Review review = new Review();
            review.author = reviewJsonObject.getString("author");
            review.content = reviewJsonObject.getString("content");
            reviewsList.add(review);
        }
        return reviewsList;
    }
}
