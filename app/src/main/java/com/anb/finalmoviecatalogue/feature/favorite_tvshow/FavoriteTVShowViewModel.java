package com.anb.finalmoviecatalogue.feature.favorite_tvshow;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.anb.finalmoviecatalogue.data.model.Favorite;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class FavoriteTVShowViewModel extends ViewModel {

    private Context context;
    private final MutableLiveData<ArrayList<Favorite>> response = new MutableLiveData<>();
    private ArrayList<Favorite> tvshow = new ArrayList<>();

    FavoriteTVShowViewModel(Context context) {
        this.context = context;
        loadFavoriteMovie();
    }

    MutableLiveData<ArrayList<Favorite>> getResponse() {
        return response;
    }

    private void setData(ArrayList<Favorite> movieResponse) {
        response.setValue(movieResponse);
    }

    void loadFavoriteMovie() {
        Realm realm = Realm.getDefaultInstance();
        try {
            tvshow.clear();
            RealmResults<Favorite> favorites = realm.where(Favorite.class).equalTo("type", "tvshow").findAll();
            if (favorites != null && favorites.size()>0){
                tvshow.addAll(favorites);
            }
            setData(tvshow);
        } finally {
        }
    }
}
