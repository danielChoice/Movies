package com.example.mymoviess.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymoviess.R;
import com.example.mymoviess.Trailer;

import java.util.ArrayList;


public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailersViewHolder> {

    private ArrayList<Trailer> trailers;
    private OnTrailerClickListener onTrailerClickListener;

    @NonNull
    @Override
    public TrailersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_item, parent, false);
        return new TrailersViewHolder(view);
    }

    public interface OnTrailerClickListener{
        void onTrailerClick(String url);
    }

    public void setOnTrailerClickListener(OnTrailerClickListener onTrailerClickListener) {
        this.onTrailerClickListener = onTrailerClickListener;
    }

    public ArrayList<Trailer> getTrailers() {
        return trailers;
    }

    public void setTrailers(ArrayList<Trailer> trailers) {
        this.trailers = trailers;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull TrailersViewHolder holder, int position) {
        holder.trailerName.setText(trailers.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    class TrailersViewHolder extends RecyclerView.ViewHolder{

        private TextView trailerName;

        public TrailersViewHolder(@NonNull final View itemView) {
            super(itemView);
            trailerName = itemView.findViewById(R.id.textViewTrailerName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onTrailerClickListener != null){
                        onTrailerClickListener.onTrailerClick(trailers.get(getAdapterPosition()).getKey());
                    }
                }
            });
        }
    }
}
