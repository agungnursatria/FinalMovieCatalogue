package com.anb.finalmoviecatalogue.feature.favorite_movie;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.annotation.NonNull;

public class FavoriteMovieViewModelFactory implements ViewModelProvider.Factory {

    private Context context;

    public FavoriteMovieViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(FavoriteMovieViewModel.class)) {
            return (T) new FavoriteMovieViewModel(context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
