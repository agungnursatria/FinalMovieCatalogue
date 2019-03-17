package com.anb.finalmoviecatalogue.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TVShowResponse implements Parcelable {

    @SerializedName("page")
    private int page;
    @SerializedName("total_results")
    private int total_results;
    @SerializedName("total_pages")
    private int total_pages;
    @SerializedName("results")
    private ArrayList<TVShow> results;

    public TVShowResponse() {
        page = 0;
        total_results = 0;
        total_pages = 0;
        results = new ArrayList<>();
    }

    public TVShowResponse(int page, int total_results, int total_pages, ArrayList<TVShow> results) {
        this.page = page;
        this.total_results = total_results;
        this.total_pages = total_pages;
        this.results = results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public ArrayList<TVShow> getResults() {
        return results;
    }

    public void setResults(ArrayList<TVShow> results) {
        this.results = results;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.page);
        dest.writeInt(this.total_results);
        dest.writeInt(this.total_pages);
        dest.writeList(this.results);
    }

    protected TVShowResponse(Parcel in) {
        this.page = in.readInt();
        this.total_results = in.readInt();
        this.total_pages = in.readInt();
        this.results = new ArrayList<TVShow>();
        in.readList(this.results, TVShow.class.getClassLoader());
    }

    public static final Creator<TVShowResponse> CREATOR = new Creator<TVShowResponse>() {
        @Override
        public TVShowResponse createFromParcel(Parcel source) {
            return new TVShowResponse(source);
        }

        @Override
        public TVShowResponse[] newArray(int size) {
            return new TVShowResponse[size];
        }
    };
}
