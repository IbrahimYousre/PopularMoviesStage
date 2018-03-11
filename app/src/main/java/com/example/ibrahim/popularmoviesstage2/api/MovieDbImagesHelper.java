package com.example.ibrahim.popularmoviesstage2.api;

/**
 * Created by ibrahim on 3/12/18.
 */

public class MovieDbImagesHelper {
    public static String getBackdropUrl(String path) {
        return "http://image.tmdb.org/t/p/w342/" + path;
    }

    public static String getPosterUrl(String path) {
        return "http://image.tmdb.org/t/p/w185/" + path;
    }
}
