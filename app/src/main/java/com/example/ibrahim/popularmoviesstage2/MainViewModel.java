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

import java.util.ArrayList;
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
    MutableLiveData<List<Movie>> liveFavoriteMoviesList;

    public MainViewModel(@NonNull Application application) {
        super(application);
        livePopularMoviesList = new MutableLiveData<>();
        liveTopRatedMoviesList = new MutableLiveData<>();
        liveFavoriteMoviesList = new MutableLiveData<>();
    }

    public MutableLiveData<List<Movie>> getLivePopularMoviesList() {
        return livePopularMoviesList;
    }

    public MutableLiveData<List<Movie>> getLiveTopRatedMoviesList() {
        return liveTopRatedMoviesList;
    }

    public MutableLiveData<List<Movie>> getLiveFavoriteMoviesList() {
        return liveFavoriteMoviesList;
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

    public LiveData<List<Movie>> fetchFavoriteMovies() {
        new LoadFavoriteMoviesTask(this.getApplication().getContentResolver(), liveFavoriteMoviesList).execute();
        return liveFavoriteMoviesList;
    }

    static class LoadFavoriteMoviesTask extends AsyncTask<Void, Void, List<Movie>> {
        @SuppressLint("StaticFieldLeak")
        ContentResolver mResolver;
        MutableLiveData<List<Movie>> mLiveList;

        LoadFavoriteMoviesTask(ContentResolver resolver, MutableLiveData<List<Movie>> liveCursor) {
            mResolver = resolver;
            mLiveList = liveCursor;
        }

        @Override
        protected List<Movie> doInBackground(Void... voids) {
            Cursor cursor = mResolver.query(
                    MoviesDbContract.FavoriteMoviesTable.CONTENT_URI,
                    null, null, null, null);
            if (cursor == null) return null;
            List<Movie> list = new ArrayList<>(cursor.getCount());
            while (cursor.moveToNext()) {
                Movie movie = new Movie();
                movie.id = cursor.getLong(
                        cursor.getColumnIndex(MoviesDbContract.FavoriteMoviesTable._ID));
                movie.posterPath = cursor.getString(
                        cursor.getColumnIndex(MoviesDbContract.FavoriteMoviesTable.COLUMN_POSTER_PATH));
                movie.backdropPath = cursor.getString(
                        cursor.getColumnIndex(MoviesDbContract.FavoriteMoviesTable.COLUMN_BACKDROP_PATH));
                movie.releaseDate = cursor.getString
                        (cursor.getColumnIndex(MoviesDbContract.FavoriteMoviesTable.COLUMN_RELEASE_YEAR));
                movie.duration = cursor.getString(
                        cursor.getColumnIndex(MoviesDbContract.FavoriteMoviesTable.COLUMN_DURATION));
                movie.voteAverage = Float.parseFloat(cursor.getString(
                        cursor.getColumnIndex(MoviesDbContract.FavoriteMoviesTable.COLUMN_RATING)));
                movie.overview = cursor.getString(
                        cursor.getColumnIndex(MoviesDbContract.FavoriteMoviesTable.COLUMN_STORY));
                list.add(movie);
            }
            cursor.close();
            return list;
        }

        @Override
        protected void onPostExecute(List<Movie> list) {
            super.onPostExecute(list);
            mLiveList.setValue(list);
        }
    }
}
