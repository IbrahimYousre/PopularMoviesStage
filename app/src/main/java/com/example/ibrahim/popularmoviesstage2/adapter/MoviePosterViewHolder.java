package com.example.ibrahim.popularmoviesstage2.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ibrahim.popularmoviesstage2.R;
import com.example.ibrahim.popularmoviesstage2.api.MovieDbImagesHelper;
import com.example.ibrahim.popularmoviesstage2.data.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.Locale;

/**
 * Created by ibrahim on 3/12/18.
 */

public class MoviePosterViewHolder extends RecyclerView.ViewHolder {
    ImageView posterImageView;
    TextView ratingTextView;

    public MoviePosterViewHolder(final View itemView, final MovieListAdapter.MovieSelected callback) {
        super(itemView);
        posterImageView = itemView.findViewById(R.id.posterImageView);
        ratingTextView = itemView.findViewById(R.id.ratingTextView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onClick((Movie) itemView.getTag());
            }
        });
    }

    public void bindViewHolder(Movie movie) {
        Picasso.with(itemView.getContext())
                .load(MovieDbImagesHelper.getPosterUrl(movie.posterPath))
                .placeholder(R.drawable.placeholder)
                .into(posterImageView);
        ratingTextView.setText(String.format(Locale.US, "%.1f", movie.voteAverage));
        itemView.setTag(movie);
    }
}
