package com.fishco.moviefinder.util;

import com.fishco.moviefinder.adapter.MovieAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class MovieTouchHelperCallBack extends ItemTouchHelper.SimpleCallback {

    private MovieAdapter movieAdapter;

    public MovieTouchHelperCallBack(MovieAdapter movieAdapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.movieAdapter = movieAdapter;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

    }
}
