package com.anb.favoriteapp.data.model;

import com.google.gson.annotations.SerializedName;

public class CreatedBy {
    @SerializedName("id")
    private String id;
    @SerializedName("credit_id")
    private String credit_id;
    @SerializedName("gender")
    private int gender;
    @SerializedName("profile_path")
    private String profile_path;

    public CreatedBy() {
    }

    public CreatedBy(String id, String credit_id, int gender, String profile_path) {
        this.id = id;
        this.credit_id = credit_id;
        this.gender = gender;
        this.profile_path = profile_path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCredit_id() {
        return credit_id;
    }

    public void setCredit_id(String credit_id) {
        this.credit_id = credit_id;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }
}
