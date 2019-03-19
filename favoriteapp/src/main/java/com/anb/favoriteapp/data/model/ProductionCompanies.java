package com.anb.favoriteapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ProductionCompanies implements Parcelable {

    @SerializedName("id")
    private String id;

    @SerializedName("logo_path")
    private String logo_path;

    @SerializedName("name")
    private String name;

    @SerializedName("origin_country")
    private String origin_country;

    public ProductionCompanies() {
    }

    public ProductionCompanies(String id, String logo_path, String name, String origin_country) {
        this.id = id;
        this.logo_path = logo_path;
        this.name = name;
        this.origin_country = origin_country;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogo_path() {
        return logo_path;
    }

    public void setLogo_path(String logo_path) {
        this.logo_path = logo_path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrigin_country() {
        return origin_country;
    }

    public void setOrigin_country(String origin_country) {
        this.origin_country = origin_country;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.logo_path);
        dest.writeString(this.name);
        dest.writeString(this.origin_country);
    }

    protected ProductionCompanies(Parcel in) {
        this.id = in.readString();
        this.logo_path = in.readString();
        this.name = in.readString();
        this.origin_country = in.readString();
    }

    public static final Creator<ProductionCompanies> CREATOR = new Creator<ProductionCompanies>() {
        @Override
        public ProductionCompanies createFromParcel(Parcel source) {
            return new ProductionCompanies(source);
        }

        @Override
        public ProductionCompanies[] newArray(int size) {
            return new ProductionCompanies[size];
        }
    };
}
