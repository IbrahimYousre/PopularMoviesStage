package com.example.ibrahim.popularmoviesstage2.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ibrahim on 3/12/18.
 */

public class ResponsePage {
    @SerializedName("page")
    @Expose
    public int page;
    @SerializedName("total_results")
    @Expose
    public int totalResults;
    @SerializedName("total_pages")
    @Expose
    public int totalPages;
    @SerializedName("results")
    @Expose
    public List<Movie> movieList;
}
