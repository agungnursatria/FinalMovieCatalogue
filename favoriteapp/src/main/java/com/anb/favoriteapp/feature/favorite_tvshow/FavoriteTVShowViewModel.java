package com.anb.favoriteapp.feature.favorite_tvshow;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import com.anb.favoriteapp.data.db.DatabaseContract;
import com.anb.favoriteapp.data.model.Favorite;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.anb.favoriteapp.data.db.DatabaseContract.getColumnString;

public class FavoriteTVShowViewModel extends ViewModel {

    private Context context;
    private final MutableLiveData<ArrayList<Favorite>> response = new MutableLiveData<>();
    private ArrayList<Favorite> tvshow = new ArrayList<>();

    FavoriteTVShowViewModel(Context context) {
        this.context = context;
        loadFavoriteMovie(context);
//        loadFavoriteMovie();
    }

    void loadFavoriteMovie(Context context){
        new AsyncFavMovie(context).execute();
    }

    MutableLiveData<ArrayList<Favorite>> getResponse() {
        return response;
    }

    private void setData(ArrayList<Favorite> movieResponse) {
        response.setValue(movieResponse);
    }

//    void loadFavoriteMovie() {
//        Realm realm = Realm.getDefaultInstance();
//        try {
//            tvshow.clear();
//            RealmResults<Favorite> favorites = realm.where(Favorite.class).equalTo("type", "tvshow").findAll();
//            if (favorites != null && favorites.size()>0){
//                tvshow.addAll(favorites);
//            }
//            setData(tvshow);
//        } finally {
//        }
//    }

    public class AsyncFavMovie extends AsyncTask<Void, Void, Cursor> {
        private WeakReference<Context> weakContext;
        ArrayList<Favorite> favorites;

        AsyncFavMovie(Context context) {
            this.weakContext = new WeakReference<>(context);
            favorites = new ArrayList<>();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            Context context = weakContext.get();
            return context.getContentResolver().query(DatabaseContract.FavoriteColumns.CONTENT_URI_TVSHOW, null,null,null,null);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            favorites.clear();
            if (cursor != null){
                try {
                    if (cursor.moveToFirst()){
                        do {
                            Favorite fav = new Favorite(
                                    getColumnString(cursor, DatabaseContract.FavoriteColumns._ID),
                                    getColumnString(cursor, DatabaseContract.FavoriteColumns.NAME),
                                    getColumnString(cursor, DatabaseContract.FavoriteColumns.POSTER),
                                    getColumnString(cursor, DatabaseContract.FavoriteColumns.TYPE)
                            );
                            favorites.add(fav);
                        } while (cursor.moveToNext());
                    }
                } catch (Exception e){
                    setData(favorites);
                    cursor.close();
                } finally {
                    setData(favorites);
                    cursor.close();
                }
            }
        }
    }
}
