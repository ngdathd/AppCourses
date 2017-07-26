package com.example.hdt.appcourses.retrofit;

import com.example.hdt.appcourses.objects.ListItemCourses;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IListItemCourses {

    @GET("/api/custom/courses")
    Call<ListItemCourses> getItemCourses();
}
