package com.example.ibrahim.popularmoviesstage2.adapter.reviews;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ibrahim.popularmoviesstage2.R;
import com.example.ibrahim.popularmoviesstage2.data.model.Review;

import java.util.List;

/**
 * Created by ibrahim on 3/12/18.
 */

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewViewHolder> {
    List<Review> mList;

    public ReviewListAdapter(List<Review> list) {
        mList = list;
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_list_item, parent, false);
        return new ReviewViewHolder(root);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        holder.bindViewHolder(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public void swapList(List<Review> list) {
        mList = list;
        notifyDataSetChanged();
    }

    public boolean isNullList() {
        return mList == null;
    }
}
