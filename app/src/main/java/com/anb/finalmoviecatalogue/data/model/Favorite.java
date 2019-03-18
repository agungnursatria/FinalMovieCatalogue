package com.anb.finalmoviecatalogue.data.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.anb.finalmoviecatalogue.data.db.DatabaseContract;
import com.anb.finalmoviecatalogue.data.db.DatabaseContract.FavoriteColumns;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

import static android.provider.BaseColumns._ID;
import static com.anb.finalmoviecatalogue.data.db.DatabaseContract.getColumnString;

public class Favorite extends RealmObject implements Parcelable {

    @PrimaryKey
    private String id;

    private String name;

    private String poster_path;

    private String type;

    public Favorite(String id, String name, String poster_path, String type) {
        this.id = id;
        this.name = name;
        this.poster_path = poster_path;
        this.type = type;
    }

    public Favorite() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public Favorite(Cursor cursor) {
        this.id = getColumnString(cursor, FavoriteColumns._ID);
        this.name = getColumnString(cursor, FavoriteColumns.NAME);
        this.poster_path = getColumnString(cursor, FavoriteColumns.POSTER);
        this.type = getColumnString(cursor, FavoriteColumns.TYPE);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.poster_path);
        dest.writeString(this.type);
    }

    protected Favorite(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.poster_path = in.readString();
        this.type = in.readString();
    }

    public static final Parcelable.Creator<Favorite> CREATOR = new Parcelable.Creator<Favorite>() {
        @Override
        public Favorite createFromParcel(Parcel source) {
            return new Favorite(source);
        }

        @Override
        public Favorite[] newArray(int size) {
            return new Favorite[size];
        }
    };
}
