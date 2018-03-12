package com.example.ibrahim.popularmoviesstage2.adapter;

import android.database.Cursor;

import com.example.ibrahim.popularmoviesstage2.data.model.Movie;
import com.example.ibrahim.popularmoviesstage2.data.persistence.MoviesDbContract.FavoriteMoviesTable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ibrahim on 3/12/18.
 */

public class MovieListCursorAdapter extends MovieListAdapter {
    Cursor mCursor;
    Map<Integer, Movie> map;

    public MovieListCursorAdapter(MovieSelected callback, Cursor cursor) {
        super(callback);
        this.mCursor = cursor;
    }

    @Override
    protected Movie getMovie(int position) {
        if (map.containsKey(position)) return map.get(position);
        mCursor.moveToPosition(position);
        Movie movie = new Movie();
        movie.id = mCursor.getLong(
                mCursor.getColumnIndex(FavoriteMoviesTable._ID));
        movie.posterPath = mCursor.getString(
                mCursor.getColumnIndex(FavoriteMoviesTable.COLUMN_POSTER_PATH));
        movie.backdropPath = mCursor.getString(
                mCursor.getColumnIndex(FavoriteMoviesTable.COLUMN_BACKDROP_PATH));
        movie.releaseDate = mCursor.getString
                (mCursor.getColumnIndex(FavoriteMoviesTable.COLUMN_RELEASE_YEAR));
        movie.duration = mCursor.getString(
                mCursor.getColumnIndex(FavoriteMoviesTable.COLUMN_DURATION));
        movie.voteAverage = Float.parseFloat(mCursor.getString(
                mCursor.getColumnIndex(FavoriteMoviesTable.COLUMN_RATING)));
        movie.overview = mCursor.getString(
                mCursor.getColumnIndex(FavoriteMoviesTable.COLUMN_STORY));
        map.put(position, movie);
        return movie;
    }

    @Override
    public int getItemCount() {
        return mCursor == null ? 0 : mCursor.getCount();
    }

    public void swapCursor(Cursor cursor) {
        if (mCursor != null) {
            mCursor.close();
        }
        mCursor = cursor;
        map = new HashMap<>();
        notifyDataSetChanged();
    }
}
