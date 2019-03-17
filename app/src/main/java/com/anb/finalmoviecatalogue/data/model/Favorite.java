package com.anb.finalmoviecatalogue.data.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Favorite extends RealmObject {

    @PrimaryKey
    private String id;

    private String poster_path;

    private String type;

    public Favorite(String id, String poster_path, String type) {
        this.id = id;
        this.poster_path = poster_path;
        this.type = type;
    }

    public Favorite() {
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
}
