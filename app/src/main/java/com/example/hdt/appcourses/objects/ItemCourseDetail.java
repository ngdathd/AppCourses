package com.example.hdt.appcourses.objects;

public class ItemCourseDetail {
    private String url;
    private String banner;
    private String title;

    public ItemCourseDetail(String url, String banner, String title) {
        this.url = url;
        this.banner = banner;
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
