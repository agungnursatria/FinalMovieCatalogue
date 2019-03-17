package com.anb.finalmoviecatalogue.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class VideoResponse {

    @SerializedName("id")
    private Integer id;
    @SerializedName("results")
    private ArrayList<Video> results = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ArrayList<Video> getResults() {
        return results;
    }

    public void setResults(ArrayList<Video> results) {
        this.results = results;
    }

}
