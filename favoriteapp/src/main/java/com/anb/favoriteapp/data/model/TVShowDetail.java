package com.anb.favoriteapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TVShowDetail implements Parcelable {

    @SerializedName("backdrop_path")
    private String backdrop_path;

    @SerializedName("created_by")
    private ArrayList<CreatedBy> created_by;

    @SerializedName("episode_run_time")
    private ArrayList<Integer> episode_run_time;

    @SerializedName("first_air_date")
    private String first_air_date;

    @SerializedName("genres")
    private ArrayList<Genre> genres;

    @SerializedName("homepage")
    private String homepage;

    @SerializedName("id")
    private String id;

    @SerializedName("last_air_date")
    private String last_air_date;

    @SerializedName("name")
    private String name;

    @SerializedName("number_of_episodes")
    private int number_of_episodes;

    @SerializedName("number_of_seasons")
    private int number_of_seasons;

    @SerializedName("original_language")
    private String original_language;

    @SerializedName("original_name")
    private String original_name;

    @SerializedName("overview")
    private String overview;

    @SerializedName("popularity")
    private double popularity;

    @SerializedName("poster_path")
    private String poster_path;

    @SerializedName("production_companies")
    private ArrayList<ProductionCompanies> production_companies;

    @SerializedName("seasons")
    private ArrayList<Season> seasons;

    @SerializedName("status")
    private String status;

    @SerializedName("type")
    private String type;

    @SerializedName("vote_average")
    private double vote_average;

    @SerializedName("vote_count")
    private int vote_count;

    public String genreToString(){
        String stri = "";
        for (Genre genre: genres ) {
            stri = stri + genre.getName();
            stri = stri + (!genre.getName().equals(genres.get(genres.size()-1).getName()) ? ", " : "");
        }
        return stri;
    }

    public TVShowDetail() {
    }

    public TVShowDetail(String backdrop_path, ArrayList<CreatedBy> created_by, ArrayList<Integer> episode_run_time, String first_air_date, ArrayList<Genre> genres, String homepage, String id, String last_air_date, String name, int number_of_episodes, int number_of_seasons, String original_language, String original_name, String overview, double popularity, String poster_path, ArrayList<ProductionCompanies> production_companies, ArrayList<Season> seasons, String status, String type, double vote_average, int vote_count) {
        this.backdrop_path = backdrop_path;
        this.created_by = created_by;
        this.episode_run_time = episode_run_time;
        this.first_air_date = first_air_date;
        this.genres = genres;
        this.homepage = homepage;
        this.id = id;
        this.last_air_date = last_air_date;
        this.name = name;
        this.number_of_episodes = number_of_episodes;
        this.number_of_seasons = number_of_seasons;
        this.original_language = original_language;
        this.original_name = original_name;
        this.overview = overview;
        this.popularity = popularity;
        this.poster_path = poster_path;
        this.production_companies = production_companies;
        this.seasons = seasons;
        this.status = status;
        this.type = type;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public ArrayList<CreatedBy> getCreated_by() {
        return created_by;
    }

    public void setCreated_by(ArrayList<CreatedBy> created_by) {
        this.created_by = created_by;
    }

    public ArrayList<Integer> getEpisode_run_time() {
        return episode_run_time;
    }

    public void setEpisode_run_time(ArrayList<Integer> episode_run_time) {
        this.episode_run_time = episode_run_time;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Genre> genres) {
        this.genres = genres;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLast_air_date() {
        return last_air_date;
    }

    public void setLast_air_date(String last_air_date) {
        this.last_air_date = last_air_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber_of_episodes() {
        return number_of_episodes;
    }

    public void setNumber_of_episodes(int number_of_episodes) {
        this.number_of_episodes = number_of_episodes;
    }

    public int getNumber_of_seasons() {
        return number_of_seasons;
    }

    public void setNumber_of_seasons(int number_of_seasons) {
        this.number_of_seasons = number_of_seasons;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public ArrayList<ProductionCompanies> getProduction_companies() {
        return production_companies;
    }

    public void setProduction_companies(ArrayList<ProductionCompanies> production_companies) {
        this.production_companies = production_companies;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(ArrayList<Season> seasons) {
        this.seasons = seasons;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.backdrop_path);
        dest.writeList(this.created_by);
        dest.writeList(this.episode_run_time);
        dest.writeString(this.first_air_date);
        dest.writeTypedList(this.genres);
        dest.writeString(this.homepage);
        dest.writeString(this.id);
        dest.writeString(this.last_air_date);
        dest.writeString(this.name);
        dest.writeInt(this.number_of_episodes);
        dest.writeInt(this.number_of_seasons);
        dest.writeString(this.original_language);
        dest.writeString(this.original_name);
        dest.writeString(this.overview);
        dest.writeDouble(this.popularity);
        dest.writeString(this.poster_path);
        dest.writeTypedList(this.production_companies);
        dest.writeTypedList(this.seasons);
        dest.writeString(this.status);
        dest.writeString(this.type);
        dest.writeDouble(this.vote_average);
        dest.writeInt(this.vote_count);
    }

    protected TVShowDetail(Parcel in) {
        this.backdrop_path = in.readString();
        this.created_by = new ArrayList<CreatedBy>();
        in.readList(this.created_by, CreatedBy.class.getClassLoader());
        this.episode_run_time = new ArrayList<Integer>();
        in.readList(this.episode_run_time, Integer.class.getClassLoader());
        this.first_air_date = in.readString();
        this.genres = in.createTypedArrayList(Genre.CREATOR);
        this.homepage = in.readString();
        this.id = in.readString();
        this.last_air_date = in.readString();
        this.name = in.readString();
        this.number_of_episodes = in.readInt();
        this.number_of_seasons = in.readInt();
        this.original_language = in.readString();
        this.original_name = in.readString();
        this.overview = in.readString();
        this.popularity = in.readDouble();
        this.poster_path = in.readString();
        this.production_companies = in.createTypedArrayList(ProductionCompanies.CREATOR);
        this.seasons = in.createTypedArrayList(Season.CREATOR);
        this.status = in.readString();
        this.type = in.readString();
        this.vote_average = in.readDouble();
        this.vote_count = in.readInt();
    }

    public static final Creator<TVShowDetail> CREATOR = new Creator<TVShowDetail>() {
        @Override
        public TVShowDetail createFromParcel(Parcel source) {
            return new TVShowDetail(source);
        }

        @Override
        public TVShowDetail[] newArray(int size) {
            return new TVShowDetail[size];
        }
    };
}
