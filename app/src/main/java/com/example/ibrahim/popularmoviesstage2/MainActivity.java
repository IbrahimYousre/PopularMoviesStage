package com.example.ibrahim.popularmoviesstage2;

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
import android.widget.Toast;

import com.example.ibrahim.popularmoviesstage2.adapter.MovieListAdapter;
import com.example.ibrahim.popularmoviesstage2.data.model.Movie;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieListAdapter.MovieSelected {

    MainViewModel viewModel;
    RecyclerView recyclerView;
    MovieListAdapter favAdapter;
    MovieListAdapter popularAdapter;
    MovieListAdapter topRatedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        recyclerView = findViewById(R.id.recyclerView);

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
                        recyclerView.setAdapter(popularAdapter);
                        viewModel.fetchPopularMovies().observe(MainActivity.this, new Observer<List<Movie>>() {
                            @Override
                            public void onChanged(@Nullable List<Movie> list) {
                                popularAdapter.swapList(list);
                            }
                        });
                        return true;
                    case R.id.navigation_top_rated:
                        recyclerView.setAdapter(topRatedAdapter);
                        viewModel.fetchTopRatedMovies().observe(MainActivity.this, new Observer<List<Movie>>() {
                            @Override
                            public void onChanged(@Nullable List<Movie> list) {
                                topRatedAdapter.swapList(list);
                            }
                        });
                        return true;
                    case R.id.navigation_fav:
                        recyclerView.setAdapter(favAdapter);
                        viewModel.fetchFavoriteMovies().observe(MainActivity.this, new Observer<List<Movie>>() {
                            @Override
                            public void onChanged(@Nullable List<Movie> list) {
                                favAdapter.swapList(list);
                            }
                        });
                        return true;
                }
                return false;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.navigation_popular);
    }

    Toast toast;

    @Override
    public void onClick(Movie movie) {
        startActivity(new Intent(this, DetailActivity.class)
                .putExtra(DetailActivity.MOVIE_EXTRA, (Parcelable) movie));
    }
}
