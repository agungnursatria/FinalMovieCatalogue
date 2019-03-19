package com.anb.favoriteapp.feature.movie_detail;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.annotation.NonNull;

public class MovieDetailViewModelFactory implements ViewModelProvider.Factory {

    private Context context;
    private String id;

    public MovieDetailViewModelFactory(Context context, String id) {
        this.context = context;
        this.id = id;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MovieDetailViewModel.class)) {
            return (T) new MovieDetailViewModel(context,id);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
