package com.anb.finalmoviecatalogue.feature.tvshow;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.widget.Toast;

import com.anb.finalmoviecatalogue.data.api.RetroServer;
import com.anb.finalmoviecatalogue.data.model.TVShowResponse;
import com.anb.finalmoviecatalogue.utils.Constant;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class TVShowViewModel extends ViewModel {

    private Context context;
    private final CompositeDisposable disposable = new CompositeDisposable();
    private final MutableLiveData<TVShowResponse> response = new MutableLiveData<>();

    TVShowViewModel(Context context){
        this.context = context;
        loadMovie();
    }

    @Override
    protected void onCleared() {
        disposable.clear();
    }

    MutableLiveData<TVShowResponse> getResponse() {
        return response;
    }

    private void onError(Throwable e) {
        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        response.setValue(new TVShowResponse());
    }

    private void setData(TVShowResponse tvShowResponse) {
        response.setValue(tvShowResponse);
    }

    void loadMovie(){
        disposable.add(
                RetroServer
                        .getRequestService()
                        .getTVShow(Constant.API_KEY, Constant.LANGUAGE)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::setData, this::onError)
        );
    }
}
