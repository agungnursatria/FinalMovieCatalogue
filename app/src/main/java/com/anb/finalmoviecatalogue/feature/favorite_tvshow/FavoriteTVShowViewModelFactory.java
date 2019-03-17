package com.anb.finalmoviecatalogue.feature.favorite_tvshow;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.annotation.NonNull;

public class FavoriteTVShowViewModelFactory implements ViewModelProvider.Factory {

    private Context context;

    public FavoriteTVShowViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(FavoriteTVShowViewModel.class)) {
            return (T) new FavoriteTVShowViewModel(context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
