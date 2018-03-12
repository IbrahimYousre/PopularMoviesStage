package com.example.ibrahim.popularmoviesstage2.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ibrahim.popularmoviesstage2.R;
import com.example.ibrahim.popularmoviesstage2.data.model.Movie;

import java.util.List;

/**
 * Created by ibrahim on 3/12/18.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MoviePosterViewHolder> {
    MovieSelected mCallback;
    List<Movie> mList;

    public interface MovieSelected {
        void onClick(Movie movie);
    }

    public MovieListAdapter(MovieSelected callback, List<Movie> list) {
        mCallback = callback;
        mList = list;
    }

    @Override
    public MoviePosterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.poster_list_item, parent, false);
        return new MoviePosterViewHolder(root, mCallback);
    }

    @Override
    public void onBindViewHolder(MoviePosterViewHolder holder, int position) {
        holder.bindViewHolder(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public void swapList(List<Movie> list) {
        mList = list;
        notifyDataSetChanged();
    }

    public boolean isNullList() {
        return mList == null;
    }
}
