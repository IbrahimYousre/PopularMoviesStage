package com.example.ibrahim.popularmoviesstage2;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.ibrahim.popularmoviesstage2.api.MovieDbImagesHelper;
import com.example.ibrahim.popularmoviesstage2.data.model.Movie;
import com.example.ibrahim.popularmoviesstage2.data.model.Review;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {
    public static final String MOVIE_EXTRA = "movie";

    DetailViewModel viewModel;

    ImageView backdropImageView;
    ImageView posterImageView;
    TextView titleTextView;
    TextView releaseTextView;
    TextView durationTextView;
    TextView ratingTextView;
    TextView storyTextView;
    RatingBar ratingBar;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        viewModel = ViewModelProviders.of(this).get(DetailViewModel.class);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        bindViews();

        Movie movie = getIntent().getParcelableExtra(MOVIE_EXTRA);

        Picasso.with(this)
                .load(MovieDbImagesHelper.getBackdropUrl(movie.backdropPath))
                .into(backdropImageView);
        Picasso.with(this)
                .load(MovieDbImagesHelper.getPosterUrl(movie.posterPath))
                .into(posterImageView);
        titleTextView.setText(movie.title);
        releaseTextView.setText(movie.releaseDate.trim().substring(0, 4));
        durationTextView.setText("");
        ratingTextView.setText(String.format(Locale.US, "%.1f", movie.voteAverage));
        ratingBar.setRating(movie.voteAverage / 2);
        storyTextView.setText(movie.overview);

        viewModel.loadMovieDetail(movie.id);

        viewModel.liveTrailersList.observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> trailersKeysList) {
                Log.d("liveTrailersList", trailersKeysList.size() + "");
            }
        });

        viewModel.liveReviewsList.observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(@Nullable List<Review> reviewList) {
                Log.d("liveReviewsList", reviewList.size() + "");
            }
        });
    }

    private void bindViews() {
        backdropImageView = findViewById(R.id.backdropImageView);
        posterImageView = findViewById(R.id.posterImageView);
        titleTextView = findViewById(R.id.titleTextView);
        releaseTextView = findViewById(R.id.releaseTextView);
        durationTextView = findViewById(R.id.durationTextView);
        ratingTextView = findViewById(R.id.ratingTextView);
        storyTextView = findViewById(R.id.storyTextView);
        ratingBar = findViewById(R.id.ratingBar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
