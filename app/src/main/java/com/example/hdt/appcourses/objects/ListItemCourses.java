package com.example.hdt.appcourses.objects;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListItemCourses {

    @SerializedName("ListItem")
    private List<ItemCourse> itemCourses;

    public List<ItemCourse> getItemCourses() {
        return itemCourses;
    }
}
