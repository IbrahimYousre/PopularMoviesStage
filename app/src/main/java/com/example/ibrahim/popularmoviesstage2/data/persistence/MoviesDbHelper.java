package com.example.ibrahim.popularmoviesstage2.data.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ibrahim on 3/11/18.
 */

public class MoviesDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "movies_db";
    public static final int DATABASE_VERSION = 3;

    public MoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + MoviesDbContract.FavoriteMoviesTable.TABLE_NAME + "(" +
                MoviesDbContract.FavoriteMoviesTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MoviesDbContract.FavoriteMoviesTable.COLUMN_API_ID + " INTEGER NOT NULL, " +
                MoviesDbContract.FavoriteMoviesTable.COLUMN_TITLE + " TEXT NOT NULL, " +
                MoviesDbContract.FavoriteMoviesTable.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
                MoviesDbContract.FavoriteMoviesTable.COLUMN_BACKDROP_PATH + " TEXT NOT NULL, " +
                MoviesDbContract.FavoriteMoviesTable.COLUMN_RELEASE_YEAR + " TEXT NOT NULL, " +
                MoviesDbContract.FavoriteMoviesTable.COLUMN_DURATION + " TEXT DEFAULT 'Unkown', " +
                MoviesDbContract.FavoriteMoviesTable.COLUMN_RATING + " TEXT NOT NULL, " +
                MoviesDbContract.FavoriteMoviesTable.COLUMN_STORY + " TEXT NOT NULL, " +
                MoviesDbContract.FavoriteMoviesTable.COLUMN_ADDITION_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "CONSTRAINT api_id_unique UNIQUE (" + MoviesDbContract.FavoriteMoviesTable.COLUMN_API_ID + ")" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MoviesDbContract.FavoriteMoviesTable.TABLE_NAME);
        onCreate(db);
    }
}
