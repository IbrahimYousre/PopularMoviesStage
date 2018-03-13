package com.example.ibrahim.popularmoviesstage2;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.ibrahim.popularmoviesstage2.api.MovieDbApiClientSingleton;
import com.example.ibrahim.popularmoviesstage2.api.util.JsonParsingUtil;
import com.example.ibrahim.popularmoviesstage2.data.model.Review;

import org.json.JSONException;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ibrahim on 3/12/18.
 */

public class DetailViewModel extends AndroidViewModel {
    MutableLiveData<List<String>> liveTrailersList;
    MutableLiveData<List<Review>> liveReviewsList;

    public DetailViewModel(@NonNull Application application) {
        super(application);
        liveTrailersList = new MutableLiveData<>();
        liveReviewsList = new MutableLiveData<>();
    }

    public MutableLiveData<List<String>> getLiveTrailersList() {
        return liveTrailersList;
    }

    public MutableLiveData<List<Review>> getLiveReviewsList() {
        return liveReviewsList;
    }

    public void loadMovieDetail(long id) {
        MovieDbApiClientSingleton.getApiClient()
                .getMovieDetails(id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    String jsonString = response.body().string();
                    // note empty value means a network call has returned with an empty list
                    // a null value means still loading
                    try {
                        liveTrailersList.setValue(JsonParsingUtil.parseTrailers(jsonString));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        liveTrailersList.setValue(Collections.<String>emptyList());
                    }
                    try {
                        liveReviewsList.setValue(JsonParsingUtil.parseReviews(jsonString));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        liveReviewsList.setValue(Collections.<Review>emptyList());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    liveTrailersList.setValue(Collections.<String>emptyList());
                    liveReviewsList.setValue(Collections.<Review>emptyList());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {

            }
        });
    }
}
