package com.example.ibrahim.popularmoviesstage2;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.ibrahim.popularmoviesstage2.adapter.reviews.ReviewListAdapter;
import com.example.ibrahim.popularmoviesstage2.adapter.trailers.TrailerListAdapter;
import com.example.ibrahim.popularmoviesstage2.api.MovieDbImagesHelper;
import com.example.ibrahim.popularmoviesstage2.data.model.Movie;
import com.example.ibrahim.popularmoviesstage2.data.model.Review;
import com.example.ibrahim.popularmoviesstage2.data.persistence.MoviesDbContract;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity
        implements TrailerListAdapter.TrailerSelected {
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
    FloatingActionButton fab;

    RecyclerView trailersRecyclerView;
    RecyclerView reviewsRecyclerView;

    boolean isFavorite;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        Movie movie = getIntent().getParcelableExtra(MOVIE_EXTRA);
        bindViews();
        displayMove(movie);

        viewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        viewModel.loadMovieDetail(movie.id);

        viewModel.liveTrailersList.observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> trailersKeysList) {
                trailersRecyclerView.setAdapter(
                        new TrailerListAdapter(DetailActivity.this, trailersKeysList));
            }
        });

        viewModel.liveReviewsList.observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(@Nullable List<Review> reviewList) {
                reviewsRecyclerView.setAdapter(
                        new ReviewListAdapter(reviewList));
            }
        });

        isFavorite = getContentResolver().query(
                MoviesDbContract.FavoriteMoviesTable.buildUriWithApiId(movie.id),
                null, null, null, null) != null;
        syncFabImage();
    }

    private void displayMove(final Movie movie) {
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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFavorite) {
                    removeFromFavorite(movie.id);
                    isFavorite = false;
                } else {
                    addToFavorite(movie);
                    isFavorite = true;
                }
                syncFabImage();
            }
        });
    }

    private void syncFabImage() {
        if (isFavorite) {
            fab.setImageResource(R.drawable.ic_heart);
        } else {
            fab.setImageResource(R.drawable.ic_heart_outline);
        }
    }

    private void addToFavorite(Movie movie) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MoviesDbContract.FavoriteMoviesTable._ID, movie.id);
        contentValues.put(MoviesDbContract.FavoriteMoviesTable.COLUMN_TITLE, movie.title);
        contentValues.put(MoviesDbContract.FavoriteMoviesTable.COLUMN_POSTER_PATH, movie.posterPath);
        contentValues.put(MoviesDbContract.FavoriteMoviesTable.COLUMN_BACKDROP_PATH, movie.backdropPath);
        contentValues.put(MoviesDbContract.FavoriteMoviesTable.COLUMN_RELEASE_YEAR, movie.releaseDate);
        contentValues.put(MoviesDbContract.FavoriteMoviesTable.COLUMN_RATING, movie.voteAverage);
        contentValues.put(MoviesDbContract.FavoriteMoviesTable.COLUMN_STORY, movie.overview);
        getContentResolver().insert(
                MoviesDbContract.FavoriteMoviesTable.CONTENT_URI, contentValues);
    }

    private void removeFromFavorite(long id) {
        getContentResolver().delete(
                MoviesDbContract.FavoriteMoviesTable.buildUriWithApiId(id),
                null, null);
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
        fab = findViewById(R.id.fab);
        trailersRecyclerView = findViewById(R.id.trailersRecyclerView);
        reviewsRecyclerView = findViewById(R.id.reviewsRecyclerView);
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

    @Override
    public void onClick(String key) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + key));
        if (appIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(appIntent);
        } else {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + key));
            startActivity(intent);
        }
    }
}
