package com.example.mymoviess.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymoviess.R;
import com.example.mymoviess.Reviews;

import java.util.ArrayList;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder>  {

    private ArrayList<Reviews> reviews;

    public ReviewsAdapter(ArrayList<Reviews> reviews) {
        this.reviews = reviews;
    }

    public ReviewsAdapter() {
    }

    public ArrayList<Reviews> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Reviews> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);

        return new ReviewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsViewHolder holder, int position) {
        holder.authorName.setText(reviews.get(position).getAuthor());
        holder.authorComment.setText(reviews.get(position).getContent());

    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    class ReviewsViewHolder extends RecyclerView.ViewHolder{
        private TextView authorName;
        private TextView authorComment;
        public ReviewsViewHolder(@NonNull View itemView) {
            super(itemView);

            authorName = itemView.findViewById(R.id.textViewAuthorName);
            authorComment = itemView.findViewById(R.id.textViewComment);
        }
    }
}
