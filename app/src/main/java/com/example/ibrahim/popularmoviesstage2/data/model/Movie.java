package com.example.ibrahim.popularmoviesstage2.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Movie implements Serializable, Parcelable {

    @SerializedName("id")
    @Expose
    public long id;
    @SerializedName("vote_average")
    @Expose
    public float voteAverage;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("poster_path")
    @Expose
    public String posterPath;
    @SerializedName("backdrop_path")
    @Expose
    public String backdropPath;
    @SerializedName("overview")
    @Expose
    public String overview;
    @SerializedName("release_date")
    @Expose
    public String releaseDate;
    @SerializedName("duration")
    @Expose
    public String duration;

    public final static Parcelable.Creator<Movie> CREATOR = new Creator<Movie>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return (new Movie[size]);
        }

    };

    private final static long serialVersionUID = -2692851914400425098L;

    private Movie(Parcel in) {
        this.id = ((long) in.readValue((long.class.getClassLoader())));
        this.voteAverage = ((float) in.readValue((float.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.posterPath = ((String) in.readValue((String.class.getClassLoader())));
        this.backdropPath = ((String) in.readValue((String.class.getClassLoader())));
        this.overview = ((String) in.readValue((String.class.getClassLoader())));
        this.releaseDate = ((String) in.readValue((String.class.getClassLoader())));
        this.duration = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Movie() {
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(voteAverage);
        dest.writeValue(title);
        dest.writeValue(posterPath);
        dest.writeValue(backdropPath);
        dest.writeValue(overview);
        dest.writeValue(releaseDate);
        dest.writeValue(duration);
    }

    public int describeContents() {
        return 0;
    }

}