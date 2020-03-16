package com.fishco.moviefinder.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.fishco.moviefinder.R;
import com.fishco.moviefinder.adapter.MovieAdapter;
import com.fishco.moviefinder.util.Constants;
import com.fishco.moviefinder.util.MovieTouchHelperCallBack;
import com.fishco.moviefinder.model.MovieResult;
import com.fishco.moviefinder.model.Result;
import com.fishco.moviefinder.network.MovieRetrofitInstance;
import com.fishco.moviefinder.util.DebugLogger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieClickListener, PopupMenu.OnMenuItemClickListener {

    @BindView(R.id.movie_recyclerview)
    RecyclerView movieRecyclerView;

    @BindView(R.id.movie_year_textview)
    TextView movieYearTextView;

    private MovieAdapter movieAdapter;
    private MovieRetrofitInstance movieRetrofitInstance = new MovieRetrofitInstance();
    private MovieDetailsFragment movieDetailsFragment = new MovieDetailsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Date date = new Date();
        int dateYear = Integer.parseInt(new SimpleDateFormat("yyyy", Locale.US).format(date));
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new MovieTouchHelperCallBack(movieAdapter));
        itemTouchHelper.attachToRecyclerView(movieRecyclerView);

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(movieRecyclerView);
        getMovies(dateYear);
    }

    private void getMovies(int movieYear) {
        movieYearTextView.setText(getString(R.string.movie_year, movieYear));
        movieRetrofitInstance.getThisYearsMovies(movieYear)
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


    @OnClick(R.id.settings_imageview)
    public void onMenuOpen(View view) {
        MenuInflater menuInflater = new MenuInflater(this);
        PopupMenu popupMenu = new PopupMenu(this, view);
        menuInflater.inflate(R.menu.year_picker_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.item_2008:
                getMovies(2008);
                break;
            case R.id.item_2009:
                getMovies(2009);
                break;
            case R.id.item_2010:
                getMovies(2010);
                break;
            case R.id.item_2011:
                getMovies(2011);
                break;
            case R.id.item_2012:
                getMovies(2012);
                break;
            case R.id.item_2013:
                getMovies(2013);
                break;
            case R.id.item_2014:
                getMovies(2014);
                break;
            case R.id.item_2015:
                getMovies(2015);
                break;
            case R.id.item_2016:
                getMovies(2016);
                break;
            case R.id.item_2017:
                getMovies(2017);
                break;
            case R.id.item_2018:
                getMovies(2018);
                break;
            case R.id.item_2019:
                getMovies(2019);
                break;
            case R.id.item_2020:
                getMovies(2020);
                break;


        }

        return true;
    }
}
