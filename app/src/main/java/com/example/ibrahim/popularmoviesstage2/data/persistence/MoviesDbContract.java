package com.example.ibrahim.popularmoviesstage2.data.persistence;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by ibrahim on 3/11/18.
 */
public final class MoviesDbContract {

    public static final String AUTHORITY = "com.example.ibrahim.popularmoviesstage2";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    private MoviesDbContract() {
    }

    public static class FavoriteMoviesTable implements BaseColumns {

        public static final String TABLE_NAME = "favorite_movies";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(TABLE_NAME).build();

        public static final String COLUMN_API_ID = "api_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_BACKDROP_PATH = "backdrop_path";
        public static final String COLUMN_RELEASE_YEAR = "release_year";
        public static final String COLUMN_DURATION = "duration";
        public static final String COLUMN_RATING = "rating";
        public static final String COLUMN_STORY = "story";
        public static final String COLUMN_ADDITION_TIMESTAMP = "add_fav_timestamp";

        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "/" + TABLE_NAME;
        // create cursor of base type item for single entry
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + AUTHORITY + "/" + TABLE_NAME;

        public static Uri buildUriWithApiId(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
