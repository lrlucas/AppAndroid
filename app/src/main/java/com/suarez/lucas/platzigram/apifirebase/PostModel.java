package com.suarez.lucas.platzigram.apifirebase;

import com.google.gson.annotations.SerializedName;

public class PostModel {
    @SerializedName("iud")
    private String uid;

    @SerializedName("author")
    private String author;

    @SerializedName("imageUrl")
    private String imageUrl;

    @SerializedName("timestampCreated")
    private double timestampCreated;

    public PostModel(String uid, String author, String imageUrl, double timestampCreated) {
        this.uid = uid;
        this.author = author;
        this.imageUrl = imageUrl;
        this.timestampCreated = timestampCreated;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getTimestampCreated() {
        return timestampCreated;
    }

    public void setTimestampCreated(double timestampCreated) {
        this.timestampCreated = timestampCreated;
    }
}
