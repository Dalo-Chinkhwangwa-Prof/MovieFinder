package com.fishco.moviefinder.view;

import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MovieDetailsFragment extends Fragment {

    @BindView(R.id.movie_title_textview)
    TextView movieTitleTextView;

    @BindView(R.id.movie_details_textview)
    TextView movieDetailsTextView;

    @BindView(R.id.movie_release_date_textview)
    TextView movieReleaseDateTextView;

    @BindView(R.id.details_movie_poster_imageview)
    ImageView moviePosterImageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.details_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        Result movieItem = (Result) getArguments().getSerializable(Constants.MOVIE_KEY);

        setUpView(movieItem);
    }

    @OnClick(R.id.close_fragment_imageview)
    public void closeMovieDetails(View view) {
        getActivity().getSupportFragmentManager().popBackStack();
    }

    private void setUpView(Result movieItem) {

        movieTitleTextView.setText(movieItem.getTitle());
        movieDetailsTextView.setText(movieItem.getOverview() + "");
        movieReleaseDateTextView.setText(movieItem.getReleaseDate());

        String moviePoster = Constants.MOVIE_POSTER_LINK + movieItem.getPosterPath();
        Glide.with(getContext())
                .applyDefaultRequestOptions(RequestOptions.circleCropTransform())
                .load(moviePoster)
                .into(moviePosterImageView);
    }
}
