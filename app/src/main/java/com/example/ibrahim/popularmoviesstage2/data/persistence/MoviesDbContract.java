package com.example.ibrahim.popularmoviesstage2.data.persistence;

import android.provider.BaseColumns;

/**
 * Created by ibrahim on 3/11/18.
 */

public final class MoviesDbContract {

    private MoviesDbContract() {
    }

    public static class FavoriteMoviesTable implements BaseColumns {

        public static final String TABLE_NAME = "favorite_movies";

        public static final String COLUMN_API_ID = "api_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_BACKDROP_PATH = "backdrop_path";
        public static final String COLUMN_RELEASE_YEAR = "release_year";
        public static final String COLUMN_DURATION = "duration";
        public static final String COLUMN_RATING = "rating";
        public static final String COLUMN_STORY = "story";
    }
}
