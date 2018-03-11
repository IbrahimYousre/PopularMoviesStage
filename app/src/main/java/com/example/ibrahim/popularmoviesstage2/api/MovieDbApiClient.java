package com.example.ibrahim.popularmoviesstage2.api;

import com.example.ibrahim.popularmoviesstage2.data.model.ResponsePage;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by ibrahim on 3/12/18.
 */

public interface MovieDbApiClient {
    String API_BASE_URL = "https://api.themoviedb.org/3/";

    @GET("movie/popular")
    Call<ResponsePage> getPopularMovies();

    @GET("movie/top_rated")
    Call<ResponsePage> getTopRatedMovies();
}
