package com.fishco.moviefinder.network;

import com.fishco.moviefinder.model.MovieResult;
import com.fishco.moviefinder.util.Constants;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieRetrofitInstance {

    private MovieService movieService;

    public MovieRetrofitInstance() {
        movieService = getMovieService(getRetrofitInstance());
    }

    private Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(Constants.MOVIES_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private MovieService getMovieService(Retrofit retrofitInstance) {
        return retrofitInstance.create(MovieService.class);
    }

    public Call<MovieResult> getThisYearsMovies(int releaseYear) {
        return movieService.getLatestMovies(
                Constants.API_KEY,
                releaseYear,
                Constants.SORT_BY
        );
    }
}
