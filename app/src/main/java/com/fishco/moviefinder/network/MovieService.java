package com.fishco.moviefinder.network;

import com.fishco.moviefinder.model.MovieResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieService {
    //https://api.themoviedb.org/3/discover/movie?api_key=53dec3bc6d8957c489f6a69e2f1f74c5&primary_release_year=2020&sort_by=revenue.desc
    @GET("/3/discover/movie")
    public Call<MovieResult> getLatestMovies(@Query("api_key") String apiKey,
                                             @Query("primary_release_year") int releaseYear,
                                             @Query("sort_by") String sortBy);
}
