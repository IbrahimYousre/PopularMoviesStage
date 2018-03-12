package com.example.ibrahim.popularmoviesstage2;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ibrahim.popularmoviesstage2.adapter.MovieListAdapter;
import com.example.ibrahim.popularmoviesstage2.data.model.Movie;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieListAdapter.MovieSelected {

    MainViewModel viewModel;
    RecyclerView recyclerView;
    MovieListAdapter favAdapter;
    MovieListAdapter popularAdapter;
    MovieListAdapter topRatedAdapter;
    TextView emptyView;
    ProgressBar progressBar;
    MovieListAdapter currentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        recyclerView = findViewById(R.id.recyclerView);
        emptyView = findViewById(R.id.emptyView);
        progressBar = findViewById(R.id.progressBar);

        favAdapter = new MovieListAdapter(this, null);
        popularAdapter = new MovieListAdapter(this, null);
        topRatedAdapter = new MovieListAdapter(this, null);


        setUpBottomNavigation();
    }

    private void setUpBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_popular:
                        loadLiveDataIntoAdapter(viewModel.fetchPopularMovies(), popularAdapter);
                        return true;
                    case R.id.navigation_top_rated:
                        loadLiveDataIntoAdapter(viewModel.fetchTopRatedMovies(), topRatedAdapter);
                        return true;
                    case R.id.navigation_fav:
                        loadLiveDataIntoAdapter(viewModel.fetchFavoriteMovies(), favAdapter);
                        return true;
                }
                return false;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.navigation_popular);
    }

    void loadLiveDataIntoAdapter(LiveData<List<Movie>> liveData, final MovieListAdapter adapter) {
        recyclerView.setAdapter(adapter);
        currentAdapter = adapter;
        updateLoadingAndEmptyState(adapter);
        liveData.observe(MainActivity.this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> list) {
                adapter.swapList(list);
                if (currentAdapter == adapter) {
                    updateLoadingAndEmptyState(adapter);
                }
            }
        });
    }

    void updateLoadingAndEmptyState(MovieListAdapter adapter) {
        if (adapter.isNullList()) {
            progressBar.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        } else if (adapter.getItemCount() == 0) {
            progressBar.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
            emptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(Movie movie) {
        startActivity(new Intent(this, DetailActivity.class)
                .putExtra(DetailActivity.MOVIE_EXTRA, (Parcelable) movie));
    }
}
