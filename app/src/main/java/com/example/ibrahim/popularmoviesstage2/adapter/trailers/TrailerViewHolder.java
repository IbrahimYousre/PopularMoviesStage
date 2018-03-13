package com.example.ibrahim.popularmoviesstage2.adapter.trailers;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.ibrahim.popularmoviesstage2.R;
import com.example.ibrahim.popularmoviesstage2.api.MovieDbImagesHelper;
import com.squareup.picasso.Picasso;

/**
 * Created by ibrahim on 3/12/18.
 */

public class TrailerViewHolder extends RecyclerView.ViewHolder {
    ImageView thumbnailImageView;

    public TrailerViewHolder(final View itemView, final TrailerListAdapter.TrailerSelected callback) {
        super(itemView);
        thumbnailImageView = itemView.findViewById(R.id.thumbnailImageView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onClick((String) itemView.getTag());
            }
        });
    }

    public void bindViewHolder(String key) {
        Picasso.with(itemView.getContext())
                .load(MovieDbImagesHelper.getTrailerThumbnail(key))
                .into(thumbnailImageView);
        itemView.setTag(key);
    }
}
