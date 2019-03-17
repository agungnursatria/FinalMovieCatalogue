package com.anb.finalmoviecatalogue.feature.movie;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.annotation.NonNull;

public class MovieViewModelFactory implements ViewModelProvider.Factory {

    private Context context;

    public MovieViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MovieViewModel.class)) {
            return (T) new MovieViewModel(context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
