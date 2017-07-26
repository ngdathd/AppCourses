package com.example.hdt.appcourses.objects;

import com.google.gson.annotations.SerializedName;

public class ItemCourse {

    @SerializedName("Url")
    private String url;

    @SerializedName("Description")
    private String des;

    @SerializedName("Title")
    private String title;

    @SerializedName("Banner")
    private String banner;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }
}
