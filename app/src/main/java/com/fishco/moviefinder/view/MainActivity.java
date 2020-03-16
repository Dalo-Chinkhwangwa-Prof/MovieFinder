package com.fishco.moviefinder.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;

import com.fishco.moviefinder.R;
import com.fishco.moviefinder.adapter.MovieAdapter;
import com.fishco.moviefinder.util.Constants;
import com.fishco.moviefinder.util.MovieTouchHelperCallBack;
import com.fishco.moviefinder.model.MovieResult;
import com.fishco.moviefinder.model.Result;
import com.fishco.moviefinder.network.MovieRetrofitInstance;
import com.fishco.moviefinder.util.DebugLogger;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieClickListener {

    @BindView(R.id.movie_recyclerview)
    RecyclerView movieRecyclerView;

    private MovieAdapter movieAdapter;
    private MovieRetrofitInstance movieRetrofitInstance = new MovieRetrofitInstance();
    private MovieDetailsFragment movieDetailsFragment = new MovieDetailsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getMovies();
    }

    private void getMovies() {
        movieRetrofitInstance.getThisYearsMovies(2019)
                .enqueue(new Callback<MovieResult>() {
                    @Override
                    public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                        if (response.isSuccessful() && response.body() != null && response.body().getResults() != null)
                            setMovies(response.body().getResults());
                        else
                            DebugLogger.logError(new Exception("results were null or empty."));
                    }

                    @Override
                    public void onFailure(Call<MovieResult> call, Throwable t) {
                        DebugLogger.logError(new Exception(t));
                    }
                });

    }

    private void setMovies(List<Result> movieList) {

        movieAdapter = new MovieAdapter(movieList, this);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new MovieTouchHelperCallBack(movieAdapter));
        itemTouchHelper.attachToRecyclerView(movieRecyclerView);

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(movieRecyclerView);

        movieRecyclerView.setAdapter(movieAdapter);
        movieRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    public void openMovieDetails(Result movieItem) {
        Bundle movieBundle = new Bundle();
        movieBundle.putSerializable(Constants.MOVIE_KEY, movieItem);

        movieDetailsFragment.setArguments(movieBundle);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.movie_details_frame, movieDetailsFragment)
                .addToBackStack(movieDetailsFragment.getTag())
                .commit();

    }
}
