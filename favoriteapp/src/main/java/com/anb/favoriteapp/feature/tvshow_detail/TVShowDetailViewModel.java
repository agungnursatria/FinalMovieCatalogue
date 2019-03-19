package com.anb.favoriteapp.feature.tvshow_detail;

import android.annotation.SuppressLint;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

import com.anb.favoriteapp.data.api.RetroServer;
import com.anb.favoriteapp.data.model.TVShowDetail;
import com.anb.favoriteapp.utils.Constant;

import java.lang.ref.WeakReference;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.anb.favoriteapp.data.db.DatabaseContract.FavoriteColumns.CONTENT_URI_TVSHOW;
import static com.anb.favoriteapp.data.db.DatabaseContract.FavoriteColumns.NAME;
import static com.anb.favoriteapp.data.db.DatabaseContract.FavoriteColumns.POSTER;
import static com.anb.favoriteapp.data.db.DatabaseContract.FavoriteColumns.TYPE;
import static com.anb.favoriteapp.data.db.DatabaseContract.FavoriteColumns._ID;

public class TVShowDetailViewModel extends ViewModel {

    private final CompositeDisposable disposable = new CompositeDisposable();
    private final MutableLiveData<TVShowDetail> tvshowResponse = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isFavorite = new MutableLiveData<>();
    @SuppressLint("StaticFieldLeak") private Context context;
    private Uri mUri;

    TVShowDetailViewModel(Context context, String id){
        this.context = context;
        loadTVShow(id);
    }

    @Override
    protected void onCleared() {
        disposable.clear();
    }

    void loadFavoriteTVShowWithId(Context context, String id){
        new AsyncFavTVShowWithID(context, id).execute();
    }
    MutableLiveData<TVShowDetail> getMovieResponse() {
        return tvshowResponse;
    }
    MutableLiveData<Boolean> getIsFavorite() {
        return isFavorite;
    }

    void changeFavState(){
        TVShowDetail tvShowDetail = tvshowResponse.getValue();
        if (tvShowDetail != null && isFavorite.getValue() != null){
            if (!isFavorite.getValue()) {
                ContentValues values = new ContentValues();
                values.put(_ID, tvShowDetail.getId());
                values.put(NAME, tvShowDetail.getName());
                values.put(POSTER, tvShowDetail.getPoster_path());
                values.put(TYPE, "tvshow");
                context.getContentResolver().insert(CONTENT_URI_TVSHOW, values);
            } else {
                mUri = CONTENT_URI_TVSHOW
                        .buildUpon()
                        .appendPath(String.valueOf(tvShowDetail.getId()))
                        .build();
                context.getContentResolver().delete(mUri, null, null);
            }
            isFavorite.setValue(!isFavorite.getValue());
        }
    }

    private void onErrorMovie(Throwable e) {
        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        tvshowResponse.setValue(null);
    }
    private void setDataMovie(TVShowDetail tvShowDetail) {
        tvshowResponse.setValue(tvShowDetail);
        loadFavoriteTVShowWithId(context, tvShowDetail.getId());
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

    public class AsyncFavTVShowWithID extends AsyncTask<Void, Void, Cursor> {
        private WeakReference<Context> weakContext;
        String id;

        AsyncFavTVShowWithID(Context context, String id) {
            this.weakContext = new WeakReference<>(context);
            this.id = id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            Context context = weakContext.get();
            mUri = CONTENT_URI_TVSHOW
                    .buildUpon()
                    .appendPath(String.valueOf(id))
                    .build();
            return context.getContentResolver().query(mUri, null,null,null,null);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            isFavorite.setValue(cursor.getCount()>0);
        }
    }
}
