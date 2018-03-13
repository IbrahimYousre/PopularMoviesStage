package com.example.ibrahim.popularmoviesstage2.adapter.trailers;

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

public class TrailerListAdapter extends RecyclerView.Adapter<TrailerViewHolder> {
    TrailerSelected mCallback;
    List<String> mList;

    public interface TrailerSelected {
        void onClick(String key);
    }

    public TrailerListAdapter(TrailerSelected callback, List<String> list) {
        mCallback = callback;
        mList = list;
    }

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trailer_list_item, parent, false);
        return new TrailerViewHolder(root, mCallback);
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {
        holder.bindViewHolder(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public void swapList(List<String> list) {
        mList = list;
        notifyDataSetChanged();
    }

    public boolean isNullList() {
        return mList == null;
    }
}
