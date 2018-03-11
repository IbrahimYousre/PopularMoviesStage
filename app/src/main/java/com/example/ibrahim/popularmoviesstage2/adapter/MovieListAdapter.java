package com.example.ibrahim.popularmoviesstage2.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ibrahim.popularmoviesstage2.R;
import com.example.ibrahim.popularmoviesstage2.data.model.Movie;

/**
 * Created by ibrahim on 3/12/18.
 */

public abstract class MovieListAdapter extends RecyclerView.Adapter<MoviePosterViewHolder> {

    MovieSelected mCallback;

    public interface MovieSelected {
        void onClick(Movie movie);
    }

    public MovieListAdapter(MovieSelected callback) {
        this.mCallback = callback;
    }

    @Override
    public MoviePosterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.poster_list_item, parent, false);
        return new MoviePosterViewHolder(root, mCallback);
    }

    @Override
    public void onBindViewHolder(MoviePosterViewHolder holder, int position) {
        holder.bindViewHolder(getMovie(position));
    }

    abstract protected Movie getMovie(int position);

    @Override
    abstract public int getItemCount();
}