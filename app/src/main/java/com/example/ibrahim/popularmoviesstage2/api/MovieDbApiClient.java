package com.example.ibrahim.popularmoviesstage2.api;

import com.example.ibrahim.popularmoviesstage2.data.model.ResponsePage;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by ibrahim on 3/12/18.
 */

public interface MovieDbApiClient {
    String API_BASE_URL = "https://api.themoviedb.org/3/";

    @GET("movie/popular")
    Call<ResponsePage> getPopularMovies(@Query("page") int page);

    @GET("movie/top_rated")
    Call<ResponsePage> getTopRatedMovies(@Query("page") int page);

    @GET("movie/{id}?append_to_response=videos%2Creviews")
    Call<ResponseBody> getMovieDetails(@Path("id") long movieId);
}
