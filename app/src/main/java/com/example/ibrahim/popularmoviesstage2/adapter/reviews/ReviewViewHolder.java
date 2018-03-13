package com.example.ibrahim.popularmoviesstage2.adapter.reviews;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.ibrahim.popularmoviesstage2.R;
import com.example.ibrahim.popularmoviesstage2.data.model.Review;

/**
 * Created by ibrahim on 3/12/18.
 */

public class ReviewViewHolder extends RecyclerView.ViewHolder {

    TextView reviewerNameTextView;
    TextView reviewTextView;

    public ReviewViewHolder(final View itemView) {
        super(itemView);
        reviewerNameTextView = itemView.findViewById(R.id.reviewerNameTextView);
        reviewTextView = itemView.findViewById(R.id.reviewTextView);
    }

    public void bindViewHolder(Review review) {
        reviewerNameTextView.setText(review.author);
        reviewTextView.setText(review.content);
    }
}
