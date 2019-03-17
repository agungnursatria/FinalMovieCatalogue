package com.anb.finalmoviecatalogue.feature.movie_detail;

import android.annotation.SuppressLint;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.widget.Toast;

import com.anb.finalmoviecatalogue.data.api.RetroServer;
import com.anb.finalmoviecatalogue.data.model.Favorite;
import com.anb.finalmoviecatalogue.data.model.MovieDetail;
import com.anb.finalmoviecatalogue.data.model.ReviewResponse;
import com.anb.finalmoviecatalogue.data.model.VideoResponse;
import com.anb.finalmoviecatalogue.utils.Constant;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;

public class MovieDetailViewModel extends ViewModel {

    private final CompositeDisposable disposable = new CompositeDisposable();
    private final MutableLiveData<MovieDetail> movieResponse = new MutableLiveData<>();
    private final MutableLiveData<ReviewResponse> reviewResponse = new MutableLiveData<>();
    private final MutableLiveData<VideoResponse> videoResponse = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isFavorite = new MutableLiveData<>();
    @SuppressLint("StaticFieldLeak") private Context context;

    MovieDetailViewModel(Context context, String id){
        this.context = context;
        loadMovie(id);
        loadReviews(id);
        loadVideos(id);
    }

    @Override
    protected void onCleared() {
        disposable.clear();
    }

    MutableLiveData<MovieDetail> getMovieResponse() {
        return movieResponse;
    }

    MutableLiveData<ReviewResponse> getReviewResponse() {
        return reviewResponse;
    }

    MutableLiveData<VideoResponse> getVideoResponse() {
        return videoResponse;
    }

    MutableLiveData<Boolean> getIsFavorite() {
        return isFavorite;
    }

    void changeFavState(){
        MovieDetail movieDetail = movieResponse.getValue();
        if (movieDetail != null){
            Realm realm = Realm.getDefaultInstance();
            try {
                if (!isFavorite.getValue()){
                    realm.executeTransaction(realm1 -> {
                        Favorite favorite = realm1.createObject(Favorite.class, movieDetail.getId());
                        favorite.setPoster_path(movieDetail.getPoster_path());
                        favorite.setName(movieDetail.getTitle());
                        favorite.setType("movie");
                        realm1.insertOrUpdate(favorite);
                    });
                } else {
                    realm.executeTransaction(realm1 -> {
                        Favorite favorite = realm1.where(Favorite.class)
                                .equalTo("id", movieDetail.getId())
                                .equalTo("type", "movie")
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
        movieResponse.setValue(null);
    }
    private void setDataMovie(MovieDetail movieDetail) {
        movieResponse.setValue(movieDetail);
        Realm realm = Realm.getDefaultInstance();
        try {
            RealmResults<Favorite> results = realm.where(Favorite.class)
                    .equalTo("id", movieDetail.getId())
                    .equalTo("type", "movie")
                    .findAll();
            Boolean valid = results.size()>0;
            isFavorite.setValue(valid);
        } finally {
            realm.close();
        }
    }
    private void onErrorVideos(Throwable e) {
        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        videoResponse.setValue(null);
    }
    private void setDataVideos(VideoResponse videoResponse) {
        this.videoResponse.setValue(videoResponse);
    }
    private void onErrorReviews(Throwable e) {
        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        reviewResponse.setValue(null);
    }
    private void setDataReviews(ReviewResponse reviews) {
        this.reviewResponse.setValue(reviews);
    }

    private void loadMovie(String id){
        disposable.add(
                RetroServer
                        .getRequestService()
                        .getMovieDetail(id, Constant.API_KEY)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::setDataMovie, this::onErrorMovie)
        );
    }

    private void loadVideos(String id){
        disposable.add(
                RetroServer
                        .getRequestService()
                        .getMovieVideos(id, Constant.API_KEY)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::setDataVideos, this::onErrorVideos)
        );
    }

    private void loadReviews(String id){
        disposable.add(
                RetroServer
                        .getRequestService()
                        .getMovieReviews(id, Constant.API_KEY)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::setDataReviews, this::onErrorReviews)
        );
    }

}
