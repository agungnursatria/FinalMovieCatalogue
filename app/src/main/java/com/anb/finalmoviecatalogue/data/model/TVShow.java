package com.anb.finalmoviecatalogue.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TVShow implements Parcelable {

    @SerializedName("original_name")
    private String original_name;

    @SerializedName("genre_ids")
    private ArrayList<Integer> genre_ids;

    @SerializedName("name")
    private String name;

    @SerializedName("popularity")
    private double popularity;

    @SerializedName("origin_country")
    private ArrayList<String> origin_country;

    @SerializedName("vote_count")
    private int vote_count;

    @SerializedName("first_air_date")
    private String first_air_date;

    @SerializedName("backdrop_path")
    private String backdrop_path;

    @SerializedName("original_language")
    private String original_language;

    @SerializedName("id")
    private String id;

    @SerializedName("vote_average")
    private double vote_average;

    @SerializedName("overview")
    private String overview;

    @SerializedName("poster_path")
    private String poster_path;

    public TVShow() {}

    public TVShow(String original_name, ArrayList<Integer> genre_ids, String name, double popularity, ArrayList<String> origin_country, int vote_count, String first_air_date, String backdrop_path, String original_language, String id, double vote_average, String overview, String poster_path) {
        this.original_name = original_name;
        this.genre_ids = genre_ids;
        this.name = name;
        this.popularity = popularity;
        this.origin_country = origin_country;
        this.vote_count = vote_count;
        this.first_air_date = first_air_date;
        this.backdrop_path = backdrop_path;
        this.original_language = original_language;
        this.id = id;
        this.vote_average = vote_average;
        this.overview = overview;
        this.poster_path = poster_path;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }

    public ArrayList<Integer> getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(ArrayList<Integer> genre_ids) {
        this.genre_ids = genre_ids;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public ArrayList<String> getOrigin_country() {
        return origin_country;
    }

    public void setOrigin_country(ArrayList<String> origin_country) {
        this.origin_country = origin_country;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.original_name);
        dest.writeList(this.genre_ids);
        dest.writeString(this.name);
        dest.writeDouble(this.popularity);
        dest.writeStringList(this.origin_country);
        dest.writeInt(this.vote_count);
        dest.writeString(this.first_air_date);
        dest.writeString(this.backdrop_path);
        dest.writeString(this.original_language);
        dest.writeString(this.id);
        dest.writeDouble(this.vote_average);
        dest.writeString(this.overview);
        dest.writeString(this.poster_path);
    }

    protected TVShow(Parcel in) {
        this.original_name = in.readString();
        this.genre_ids = new ArrayList<Integer>();
        in.readList(this.genre_ids, Integer.class.getClassLoader());
        this.name = in.readString();
        this.popularity = in.readDouble();
        this.origin_country = in.createStringArrayList();
        this.vote_count = in.readInt();
        this.first_air_date = in.readString();
        this.backdrop_path = in.readString();
        this.original_language = in.readString();
        this.id = in.readString();
        this.vote_average = in.readDouble();
        this.overview = in.readString();
        this.poster_path = in.readString();
    }

    public static final Creator<TVShow> CREATOR = new Creator<TVShow>() {
        @Override
        public TVShow createFromParcel(Parcel source) {
            return new TVShow(source);
        }

        @Override
        public TVShow[] newArray(int size) {
            return new TVShow[size];
        }
    };
}
