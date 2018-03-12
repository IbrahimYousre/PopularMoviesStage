package com.example.ibrahim.popularmoviesstage2.adapter;

import com.example.ibrahim.popularmoviesstage2.data.model.Movie;

import java.util.List;

/**
 * Created by ibrahim on 3/12/18.
 */

public class MovieListCollectionAdapter extends MovieListAdapter {
    List<Movie> mList;

    public MovieListCollectionAdapter(MovieSelected callback, List<Movie> list) {
        super(callback);
        mList = list;
    }

    @Override
    protected Movie getMovie(int position) {
        return mList.get(position);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public void swapList(List<Movie> list) {
        mList = list;
        notifyDataSetChanged();
    }
}
