package com.anb.finalmoviecatalogue.feature.tvshow;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.annotation.NonNull;

public class TVShowViewModelFactory implements ViewModelProvider.Factory {

    private Context context;

    public TVShowViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TVShowViewModel.class)) {
            return (T) new TVShowViewModel(context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
