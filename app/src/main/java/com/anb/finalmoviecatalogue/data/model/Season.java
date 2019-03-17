package com.anb.finalmoviecatalogue.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Season implements Parcelable {

    @SerializedName("air_date")
    private String air_date;
    @SerializedName("episode_count")
    private int episode_count;
    @SerializedName("id")
    private String id;
    @SerializedName("overview")
    private String overview;
    @SerializedName("poster_path")
    private String poster_path;
    @SerializedName("season_number")
    private int season_number;

    public Season() {
    }

    public Season(String air_date, int episode_count, String id, String overview, String poster_path, int season_number) {
        this.air_date = air_date;
        this.episode_count = episode_count;
        this.id = id;
        this.overview = overview;
        this.poster_path = poster_path;
        this.season_number = season_number;
    }

    public String getAir_date() {
        return air_date;
    }

    public void setAir_date(String air_date) {
        this.air_date = air_date;
    }

    public int getEpisode_count() {
        return episode_count;
    }

    public void setEpisode_count(int episode_count) {
        this.episode_count = episode_count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getSeason_number() {
        return season_number;
    }

    public void setSeason_number(int season_number) {
        this.season_number = season_number;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.air_date);
        dest.writeInt(this.episode_count);
        dest.writeString(this.id);
        dest.writeString(this.overview);
        dest.writeString(this.poster_path);
        dest.writeInt(this.season_number);
    }

    protected Season(Parcel in) {
        this.air_date = in.readString();
        this.episode_count = in.readInt();
        this.id = in.readString();
        this.overview = in.readString();
        this.poster_path = in.readString();
        this.season_number = in.readInt();
    }

    public static final Creator<Season> CREATOR = new Creator<Season>() {
        @Override
        public Season createFromParcel(Parcel source) {
            return new Season(source);
        }

        @Override
        public Season[] newArray(int size) {
            return new Season[size];
        }
    };
}
