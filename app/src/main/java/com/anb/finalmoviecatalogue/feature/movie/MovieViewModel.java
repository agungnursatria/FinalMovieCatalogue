package com.anb.finalmoviecatalogue.feature.movie;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.widget.Toast;

import com.anb.finalmoviecatalogue.data.api.RetroServer;
import com.anb.finalmoviecatalogue.data.model.MovieResponse;
import com.anb.finalmoviecatalogue.utils.Constant;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MovieViewModel extends ViewModel {

    private final CompositeDisposable disposable = new CompositeDisposable();
    private final MutableLiveData<MovieResponse> response = new MutableLiveData<>();
    private Context context;

    MovieViewModel(Context context){
        this.context = context;
        loadMovie();
    }

    @Override
    protected void onCleared() {
        disposable.clear();
    }

    MutableLiveData<MovieResponse> getResponse() {
        return response;
    }

    private void onError(Throwable e) {
        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        response.setValue(new MovieResponse());
    }

    private void setData(MovieResponse movieResponse) {
        response.setValue(movieResponse);
    }

    void loadMovie(){
        disposable.add(
                RetroServer
                        .getRequestService()
                        .getMovie(Constant.API_KEY, Constant.LANGUAGE)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::setData, this::onError)
        );
    }
}
