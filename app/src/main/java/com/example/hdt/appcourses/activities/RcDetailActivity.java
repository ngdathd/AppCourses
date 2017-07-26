package com.example.hdt.appcourses.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.hdt.appcourses.R;
import com.example.hdt.appcourses.adapter.RcItemCourseAdapter;
import com.example.hdt.appcourses.common.Constants;
import com.example.hdt.appcourses.objects.ItemCourse;
import com.example.hdt.appcourses.objects.ItemCourseDetail;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RcDetailActivity extends AppCompatActivity
        implements RcItemCourseAdapter.ICourseAdapter, Constants {

    private static final String TAG = RcDetailActivity.class.getSimpleName();
    private List<ItemCourseDetail> itemCourseDetails;
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_rc);
        findViewByIds();
        initComponents();
        setActions();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home: {
                this.finish();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void findViewByIds() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_courses);
        mRecyclerView = (RecyclerView) findViewById(R.id.rc_detail_course);
    }

    private void initComponents() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        RcItemCourseAdapter rcItemCourseAdapter = new RcItemCourseAdapter(this, this);
        mRecyclerView.setAdapter(rcItemCourseAdapter);

        SharedPreferences preferences = getSharedPreferences(ITEMCOURSES, Context.MODE_PRIVATE);
        String stringExtra = preferences.getString("ITEM_SHARE", "");
        Gson gson = new Gson();
        Type type = new TypeToken<List<ItemCourse>>() {
        }.getType();
        List<ItemCourse> itemCourses = gson.fromJson(stringExtra, type);
        itemCourseDetails = new ArrayList<>();
        for (ItemCourse itemCourse : itemCourses) {
            itemCourseDetails.add(new ItemCourseDetail(
                    itemCourse.getUrl(),
                    itemCourse.getBanner(),
                    itemCourse.getTitle()));
        }
    }

    private void setActions() {

    }

    @Override
    public void onClickItem(int position) {
        ItemCourseDetail itemCourseDetail = itemCourseDetails.get(position);
        Log.i(TAG, "onClickItem: " + itemCourseDetail.getTitle());
    }

    @Override
    public int getItemCount() {
        return itemCourseDetails.size();
    }

    @Override
    public ItemCourseDetail getItemCourseDetail(int position) {
        return itemCourseDetails.get(position);
    }
}
