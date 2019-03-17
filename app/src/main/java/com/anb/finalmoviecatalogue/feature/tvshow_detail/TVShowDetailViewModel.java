package com.anb.finalmoviecatalogue.feature.tvshow_detail;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.widget.Toast;

import com.anb.finalmoviecatalogue.data.api.RetroServer;
import com.anb.finalmoviecatalogue.data.model.Favorite;
import com.anb.finalmoviecatalogue.data.model.TVShowDetail;
import com.anb.finalmoviecatalogue.utils.Constant;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;

public class TVShowDetailViewModel extends ViewModel {

    private final CompositeDisposable disposable = new CompositeDisposable();
    private final MutableLiveData<TVShowDetail> tvshowResponse = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isFavorite = new MutableLiveData<>();
    private Context context;

    TVShowDetailViewModel(Context context, String id){
        this.context = context;
        loadTVShow(id);
    }

    @Override
    protected void onCleared() {
        disposable.clear();
    }

    MutableLiveData<TVShowDetail> getMovieResponse() {
        return tvshowResponse;
    }
    MutableLiveData<Boolean> getIsFavorite() {
        return isFavorite;
    }

    public void changeFavState(){
        TVShowDetail tvShowDetail = tvshowResponse.getValue();
        if (tvShowDetail != null){
            Realm realm = Realm.getDefaultInstance();
            try {
                if (isFavorite.getValue() == false){
                    realm.executeTransaction(realm1 -> {
                        Favorite favorite = realm1.createObject(Favorite.class, tvShowDetail.getId());
                        favorite.setPoster_path(tvShowDetail.getPoster_path());
                        favorite.setType("tvshow");
                        realm1.insertOrUpdate(favorite);
                    });
                } else {
                    realm.executeTransaction(realm1 -> {
                        Favorite favorite = realm1.where(Favorite.class)
                                .equalTo("id", tvShowDetail.getId())
                                .equalTo("type", "tvshow")
                                .findFirst();
                        if (favorite != null){
                            favorite.deleteFromRealm();
                        }
                    });
                }
            } finally {
                realm.close();
            }
        }
        isFavorite.setValue(!isFavorite.getValue());
    }

    private void onErrorMovie(Throwable e) {
        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        tvshowResponse.setValue(null);
    }
    private void setDataMovie(TVShowDetail tvShowDetail) {
        tvshowResponse.setValue(tvShowDetail);
        Realm realm = Realm.getDefaultInstance();
        try {
            RealmResults<Favorite> results = realm.where(Favorite.class)
                    .equalTo("id", tvShowDetail.getId())
                    .equalTo("type", "tvshow")
                    .findAll();
            Boolean valid = results.size()>0;
            isFavorite.setValue(valid);
        } finally {
            realm.close();
        }
    }

    void loadTVShow(String id){
        disposable.add(
                RetroServer
                        .getRequestService()
                        .getTVShowDetail(id, Constant.API_KEY)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::setDataMovie, this::onErrorMovie)
        );
    }
}
