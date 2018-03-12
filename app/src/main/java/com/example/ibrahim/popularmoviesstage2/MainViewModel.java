package com.example.ibrahim.popularmoviesstage2;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.ibrahim.popularmoviesstage2.api.MovieDbApiClientSingleton;
import com.example.ibrahim.popularmoviesstage2.data.model.Movie;
import com.example.ibrahim.popularmoviesstage2.data.model.ResponsePage;
import com.example.ibrahim.popularmoviesstage2.data.persistence.MoviesDbContract;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ibrahim on 3/12/18.
 */

public class MainViewModel extends AndroidViewModel {

    MutableLiveData<List<Movie>> livePopularMoviesList;
    MutableLiveData<List<Movie>> liveTopRatedMoviesList;
    MutableLiveData<Cursor> liveCursor;

    public MainViewModel(@NonNull Application application) {
        super(application);
        livePopularMoviesList = new MutableLiveData<>();
        liveTopRatedMoviesList = new MutableLiveData<>();
        liveCursor = new MutableLiveData<>();
    }

    public MutableLiveData<List<Movie>> getLivePopularMoviesList() {
        return livePopularMoviesList;
    }

    public MutableLiveData<List<Movie>> getLiveTopRatedMoviesList() {
        return liveTopRatedMoviesList;
    }

    public MutableLiveData<Cursor> getLiveCursor() {
        return liveCursor;
    }

    public LiveData<List<Movie>> fetchPopularMovies() {
        MovieDbApiClientSingleton.getApiClient()
                .getPopularMovies().enqueue(new Callback<ResponsePage>() {
            @Override
            public void onResponse(@NonNull Call<ResponsePage> call, @NonNull Response<ResponsePage> response) {
                livePopularMoviesList.setValue(response.body().movieList);
            }

            @Override
            public void onFailure(@NonNull Call<ResponsePage> call, @NonNull Throwable t) {

            }
        });
        return livePopularMoviesList;
    }

    public LiveData<List<Movie>> fetchTopRatedMovies() {
        MovieDbApiClientSingleton.getApiClient()
                .getTopRatedMovies().enqueue(new Callback<ResponsePage>() {
            @Override
            public void onResponse(@NonNull Call<ResponsePage> call, @NonNull Response<ResponsePage> response) {
                liveTopRatedMoviesList.setValue(response.body().movieList);
            }

            @Override
            public void onFailure(@NonNull Call<ResponsePage> call, @NonNull Throwable t) {

            }
        });
        return liveTopRatedMoviesList;
    }

    public LiveData<Cursor> fetchFavoriteMovies() {
        new LoadFavoriteMoviesTask(this.getApplication().getContentResolver(), liveCursor).execute();
        return liveCursor;
    }

    static class LoadFavoriteMoviesTask extends AsyncTask<Void, Void, Cursor> {
        @SuppressLint("StaticFieldLeak")
        ContentResolver mResolver;
        MutableLiveData<Cursor> mLiveCursor;

        LoadFavoriteMoviesTask(ContentResolver resolver, MutableLiveData<Cursor> liveCursor) {
            mResolver = resolver;
            mLiveCursor = liveCursor;
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mResolver.query(
                    MoviesDbContract.FavoriteMoviesTable.CONTENT_URI,
                    null, null, null, null
            );
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            mLiveCursor.postValue(cursor);
        }
    }
}
