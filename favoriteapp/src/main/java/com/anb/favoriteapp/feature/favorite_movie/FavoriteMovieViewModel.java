package com.anb.favoriteapp.feature.favorite_movie;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import com.anb.favoriteapp.data.model.Favorite;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.anb.favoriteapp.data.db.DatabaseContract.FavoriteColumns.CONTENT_URI_MOVIE;
import static com.anb.favoriteapp.data.db.DatabaseContract.FavoriteColumns.NAME;
import static com.anb.favoriteapp.data.db.DatabaseContract.FavoriteColumns.POSTER;
import static com.anb.favoriteapp.data.db.DatabaseContract.FavoriteColumns.TYPE;
import static com.anb.favoriteapp.data.db.DatabaseContract.FavoriteColumns._ID;
import static com.anb.favoriteapp.data.db.DatabaseContract.getColumnString;

public class FavoriteMovieViewModel extends ViewModel {

    private Context context;
    private final MutableLiveData<ArrayList<Favorite>> response = new MutableLiveData<>();

    FavoriteMovieViewModel(Context context) {
        this.context = context;
        loadFavoriteMovie(context);
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
            return context.getContentResolver().query(CONTENT_URI_MOVIE, null,null,null,null);
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
                                    getColumnString(cursor, _ID),
                                    getColumnString(cursor, NAME),
                                    getColumnString(cursor, POSTER),
                                    getColumnString(cursor, TYPE)
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
