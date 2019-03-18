package com.anb.finalmoviecatalogue.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.anb.finalmoviecatalogue.data.db.DatabaseContract.FavoriteColumns;
import com.anb.finalmoviecatalogue.data.model.Favorite;

import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

import static com.anb.finalmoviecatalogue.data.db.DatabaseContract.AUTHORITY;
import static com.anb.finalmoviecatalogue.data.db.DatabaseContract.FavoriteColumns.TABLE_FAVORITE_MOVIE;
import static com.anb.finalmoviecatalogue.data.db.DatabaseContract.FavoriteColumns.TABLE_FAVORITE_TVSHOW;

public class FavoriteProvider extends ContentProvider {

    private static final int FAVORITE_MOVIE = 1;
    private static final int FAVORITE_MOVIE_ID = 2;
    private static final int FAVORITE_TVSHOW = 3;
    private static final int FAVORITE_TVSHOW_ID = 4;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, TABLE_FAVORITE_MOVIE, FAVORITE_MOVIE);
        sUriMatcher.addURI(AUTHORITY,
                TABLE_FAVORITE_MOVIE+ "/#",
                FAVORITE_MOVIE_ID);
        sUriMatcher.addURI(AUTHORITY, TABLE_FAVORITE_TVSHOW, FAVORITE_TVSHOW);
        sUriMatcher.addURI(AUTHORITY,
                TABLE_FAVORITE_TVSHOW+ "/#",
                FAVORITE_TVSHOW_ID);
    }


    @Override
    public boolean onCreate() {
        Realm.init(Objects.requireNonNull(getContext()));
        RealmConfiguration config = new RealmConfiguration.Builder()
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] strings, String s, String[] strings1, String s1) {
        //Get Realm Instance
        Realm realm = Realm.getDefaultInstance();
        MatrixCursor myCursor = new MatrixCursor( new String[]{FavoriteColumns._ID
                , FavoriteColumns.NAME
                , FavoriteColumns.POSTER
                , FavoriteColumns.TYPE
        });

        try {
            switch(sUriMatcher.match(uri)){
                case FAVORITE_MOVIE:
                    RealmResults<Favorite> favoriteRealmResults = realm.where(Favorite.class).equalTo("type", "movie").findAll();
                    for (Favorite favMovie : favoriteRealmResults) {
                        Object[] rowData = new Object[]{favMovie.getId(), favMovie.getName(), favMovie.getPoster_path(), favMovie.getType()};
                        myCursor.addRow(rowData);
                        Log.v("RealmDB", favMovie.toString());
                    }
                    break;
                case FAVORITE_MOVIE_ID:
                    String id = uri.getPathSegments().get(1);
                    Favorite favMovie = realm.where(Favorite.class).equalTo("id", id).equalTo("type", "movie").findFirst();
                    if (favMovie != null) {
                        myCursor.addRow(new Object[]{favMovie.getId(), favMovie.getName(), favMovie.getPoster_path(), favMovie.getType()});
                        Log.v("RealmDB", favMovie.toString());
                    }
                    break;
                case FAVORITE_TVSHOW:
                    favoriteRealmResults = realm.where(Favorite.class).equalTo("type", "tvshow").findAll();
                    for (Favorite favTV : favoriteRealmResults) {
                        Object[] rowData = new Object[]{favTV.getId(), favTV.getName(), favTV.getPoster_path(), favTV.getType()};
                        myCursor.addRow(rowData);
                        Log.v("RealmDB", favTV.toString());
                    }
                    break;
                case FAVORITE_TVSHOW_ID:
                    String id_tvshow = uri.getPathSegments().get(1);
                    Favorite fav_tvshow = realm.where(Favorite.class).equalTo("id", id_tvshow).equalTo("type", "tvshow").findFirst();
                    if (fav_tvshow != null) {
                        myCursor.addRow(new Object[]{fav_tvshow.getId(), fav_tvshow.getName(), fav_tvshow.getPoster_path(), fav_tvshow.getType()});
                        Log.v("RealmDB", fav_tvshow.toString());
                    }
                    break;
                default:
                    throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
            myCursor.setNotificationUri(Objects.requireNonNull(getContext()).getContentResolver(),uri);

        } finally {
            realm.close();
        }
        return myCursor;
    }


    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        Uri returnUri;
        Realm realm = Realm.getDefaultInstance();
        try {
            switch (sUriMatcher.match(uri)){
                case FAVORITE_MOVIE:
                case FAVORITE_TVSHOW:
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            Favorite fav = realm.createObject(Favorite.class, contentValues.get(FavoriteColumns._ID).toString());
                            fav.setName(contentValues.get(FavoriteColumns.NAME).toString());
                            fav.setPoster_path(contentValues.get(FavoriteColumns.POSTER).toString());
                            fav.setType(contentValues.get(FavoriteColumns.TYPE).toString());
                            realm.insertOrUpdate(fav);
                        }
                    });
                    returnUri = ContentUris.withAppendedId(
                            (sUriMatcher.match(uri) == FAVORITE_MOVIE_ID)? FavoriteColumns.CONTENT_URI_MOVIE: FavoriteColumns.CONTENT_URI_TVSHOW,
                            Long.parseLong(contentValues.get(FavoriteColumns._ID).toString()));
                    break;
                default:
                    throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        } finally {
            realm.close();
        }
        return returnUri;
    }


    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        Realm realm = Realm.getDefaultInstance();
        int updated = 0;

        try {
            String id;
            Favorite fav;
            String type = (sUriMatcher.match(uri) == FAVORITE_MOVIE_ID)? "movie": "tvshow";
            id = uri.getPathSegments().get(1);
            fav = realm.where(Favorite.class).equalTo("id", id).equalTo("type", type).findFirst();
            realm.beginTransaction();
            if (fav != null) {
                fav.setName(contentValues.get(FavoriteColumns.NAME).toString());
                fav.setPoster_path(contentValues.get(FavoriteColumns.POSTER).toString());
                fav.setType(contentValues.get(FavoriteColumns.TYPE).toString());
                updated++;
            }
            realm.commitTransaction();
        } finally {
            realm.close();
        }

        if (updated > 0) {
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        }
        return updated;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] strings) {
        int count = 0;
        Realm realm = Realm.getDefaultInstance();
        try {
            String type;
            switch (sUriMatcher.match(uri)) {
                case FAVORITE_MOVIE:
                case FAVORITE_TVSHOW:
                    type = (sUriMatcher.match(uri) == FAVORITE_MOVIE)? "movie": "tvshow";
                    selection = (selection == null) ? "1" : selection;
                    RealmResults<Favorite> favoriteRealmResults = realm.where(Favorite.class).equalTo(selection, strings[0]).equalTo("type", type).findAll();
                    realm.beginTransaction();
                    favoriteRealmResults.deleteAllFromRealm();
                    count++;
                    realm.commitTransaction();
                    break;
                case FAVORITE_MOVIE_ID:
                case FAVORITE_TVSHOW_ID:
                    type = (sUriMatcher.match(uri) == FAVORITE_MOVIE_ID)? "movie": "tvshow";
                    String id = String.valueOf(ContentUris.parseId(uri));
                    Favorite fav = realm.where(Favorite.class).equalTo("id", id).equalTo("type", type).findFirst();
                    realm.beginTransaction();
                    if (fav != null) {
                        fav.deleteFromRealm();
                        count++;
                    }
                    realm.commitTransaction();
                    break;
                default:
                    throw new IllegalArgumentException("Illegal delete URI");
            }

        } finally {
            realm.close();
        }

        if (count > 0) {
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        }

        return count;
    }

}