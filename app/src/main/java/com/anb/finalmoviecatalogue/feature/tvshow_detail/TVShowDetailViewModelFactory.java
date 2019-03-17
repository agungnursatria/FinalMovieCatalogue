package com.anb.finalmoviecatalogue.feature.tvshow_detail;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.annotation.NonNull;

public class TVShowDetailViewModelFactory implements ViewModelProvider.Factory {

    private Context context;
    private String id;

    public TVShowDetailViewModelFactory(Context context, String id) {
        this.context = context;
        this.id = id;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TVShowDetailViewModel.class)) {
            return (T) new TVShowDetailViewModel(context,id);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
