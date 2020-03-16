package com.fishco.moviefinder.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fishco.moviefinder.R;
import com.fishco.moviefinder.model.Result;
import com.fishco.moviefinder.util.Constants;
import com.fishco.moviefinder.util.DebugLogger;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Result> movieList;
    private MovieClickListener movieClickListener;

    public MovieAdapter(List<Result> movieList, MovieClickListener movieClickListener) {
        this.movieList = movieList;
        this.movieClickListener = movieClickListener;
    }

    public interface MovieClickListener {
        void openMovieDetails(Result movieItem);
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item_layout, parent, false);
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        String imageUrl = Constants.MOVIE_POSTER_LINK + movieList.get(position).getPosterPath();

        DebugLogger.logDebug("Image Url : " + imageUrl);

        Glide.with(holder.itemView.getContext())
                .applyDefaultRequestOptions(RequestOptions.centerCropTransform())
                .load(imageUrl)
                .into(holder.moviePosterImageView);

        holder.movieTitleTextView.setText(movieList.get(position).getTitle());
        holder.releaseDateTextView.setText(movieList.get(position).getReleaseDate());
        holder.itemView.setOnClickListener(view -> {
            movieClickListener.openMovieDetails(movieList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.movie_poster_imageview)
        ImageView moviePosterImageView;

        @BindView(R.id.movie_title_textview)
        TextView movieTitleTextView;

        @BindView(R.id.release_date_textview)
        TextView releaseDateTextView;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
