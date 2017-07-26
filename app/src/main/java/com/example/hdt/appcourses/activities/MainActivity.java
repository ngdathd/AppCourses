package com.example.hdt.appcourses.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.hdt.appcourses.R;
import com.example.hdt.appcourses.broadcast.ShowNotifyReceiver;
import com.example.hdt.appcourses.common.Constants;
import com.example.hdt.appcourses.objects.ItemCourse;
import com.example.hdt.appcourses.objects.ListItemCourses;
import com.example.hdt.appcourses.objects.MySliderView;
import com.example.hdt.appcourses.retrofit.ApiConnector;
import com.example.hdt.appcourses.retrofit.IListItemCourses;
import com.example.hdt.appcourses.util.Util;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements Constants, BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener, View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private SliderLayout mSlider;
    private LinearLayout mLnSee;
    private ImageView mDigtial;
    private ImageView mContent;
    private ImageView mBand;
    private ImageView mPrEvent;
    private ImageView mTrade;
    private ImageView mVideo;
    private NetworkBroadCast mBroadCast;
    private Gson mGson;
    private SharedPreferences preferences;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBroadcast();
        setContentView(R.layout.activity_main);
        findViewByIds();
        initComponents();
        setActions();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null != mSlider) {
            mSlider.startAutoCycle(4000, 4000, true);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mSlider != null) {
            mSlider.stopAutoCycle();
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mBroadCast);
        super.onDestroy();
    }

    //      BaseSliderView.OnSliderClickListener
    @Override
    public void onSliderClick(BaseSliderView slider) {
        Log.i(TAG, "onSliderClick: ");
        if (Util.isConnected(MainActivity.this)) {
            Intent intent = new Intent(this, WebActivity.class);
            intent.putExtra(KEY_URL, slider.getBundle().get(KEY_URL) + "");
            startActivity(intent);
        } else {
            Snackbar.make(findViewById(android.R.id.content), "Bạn đang offline", Snackbar.LENGTH_LONG).show();
        }
    }

    //      ViewPagerEx.OnPageChangeListener
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mSlider.startAutoCycle(4000, 4000, true);
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    //      View.OnClickListener
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ln_all: {
                if (Util.isConnected(MainActivity.this)) {
                    Intent intent = new Intent(this, RcDetailActivity.class);
                    startActivity(intent);
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Bạn đang offline", Snackbar.LENGTH_LONG).show();
                }
            }
            break;
            case R.id.iv_digital_marketing: {
                if (Util.isConnected(MainActivity.this)) {
                    Intent intent = new Intent(this, WebActivity.class);
                    intent.putExtra(KEY_URL, DIGITAL_MARKETING);
                    startActivity(intent);
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Bạn đang offline", Snackbar.LENGTH_LONG).show();
                }
            }
            break;
            case R.id.iv_content_marketing: {
                if (Util.isConnected(MainActivity.this)) {
                    Intent intent = new Intent(this, WebActivity.class);
                    intent.putExtra(KEY_URL, CONTENT_MARKETING);
                    startActivity(intent);
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Bạn đang offline", Snackbar.LENGTH_LONG).show();
                }
            }
            break;
            case R.id.iv_band: {
                if (Util.isConnected(MainActivity.this)) {
                    Intent intent = new Intent(this, WebActivity.class);
                    intent.putExtra(KEY_URL, BRAND);
                    startActivity(intent);
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Bạn đang offline", Snackbar.LENGTH_LONG).show();
                }
            }
            break;
            case R.id.iv_pr_event: {
                if (Util.isConnected(MainActivity.this)) {
                    Intent intent = new Intent(this, WebActivity.class);
                    intent.putExtra(KEY_URL, PR_EVENT);
                    startActivity(intent);
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Bạn đang offline", Snackbar.LENGTH_LONG).show();
                }
            }
            break;
            case R.id.iv_trade: {
                if (Util.isConnected(MainActivity.this)) {
                    Intent intent = new Intent(this, WebActivity.class);
                    intent.putExtra(KEY_URL, MODERN_MARKETING);
                    startActivity(intent);
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Bạn đang offline", Snackbar.LENGTH_LONG).show();
                }
            }
            break;
            case R.id.iv_video_marketing: {
                if (Util.isConnected(MainActivity.this)) {
                    Intent intent = new Intent(this, WebActivity.class);
                    intent.putExtra(KEY_URL, VIDEO_MARKETING);
                    startActivity(intent);
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Bạn đang offline", Snackbar.LENGTH_LONG).show();
                }
            }
            break;
            default:
                break;
        }
    }

    private void findViewByIds() {
        mSlider = (SliderLayout) findViewById(R.id.slider_courses);
        mLnSee = (LinearLayout) findViewById(R.id.ln_all);

        mDigtial = (ImageView) findViewById(R.id.iv_digital_marketing);
        mContent = (ImageView) findViewById(R.id.iv_content_marketing);
        mBand = (ImageView) findViewById(R.id.iv_band);
        mPrEvent = (ImageView) findViewById(R.id.iv_pr_event);
        mTrade = (ImageView) findViewById(R.id.iv_trade);
        mVideo = (ImageView) findViewById(R.id.iv_video_marketing);
    }

    private void initComponents() {
        mGson = new Gson();
        preferences = getSharedPreferences(ITEMCOURSES, MODE_PRIVATE);
        Intent intent = new Intent(MainActivity.this, ShowNotifyReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
        sendNotification();
    }

    private void initSlider() {
        IListItemCourses iListItemCourses = ApiConnector.getClient(BASE_URL).create(IListItemCourses.class);
        Call<ListItemCourses> listItemCoursesCall = iListItemCourses.getItemCourses();
        listItemCoursesCall.enqueue(new Callback<ListItemCourses>() {
            @Override
            public void onResponse(Call<ListItemCourses> call, Response<ListItemCourses> response) {
                if (response.isSuccessful()) {
                    for (ItemCourse itemCourse : response.body().getItemCourses()) {
                        MySliderView mySliderView = new MySliderView(MainActivity.this);
                        mySliderView.description(itemCourse.getDes())
                                .image(itemCourse.getBanner())
                                .setScaleType(TextSliderView.ScaleType.Fit)
                                .setOnSliderClickListener(MainActivity.this);

                        mySliderView.bundle(new Bundle());
                        mySliderView.getBundle().putString(KEY_URL, itemCourse.getUrl());
                        mySliderView.getBundle().putString(KEY_BANNER, itemCourse.getBanner());
                        mySliderView.getBundle().putString(KEY_DES, itemCourse.getDes());
                        mySliderView.getBundle().putString(KEY_TITLE, itemCourse.getTitle());
                        mSlider.addSlider(mySliderView);
                    }
                    String jSonItem = mGson.toJson(response.body().getItemCourses());
                    preferences.edit().putString("ITEM_SHARE", jSonItem).apply();
                }
            }

            @Override
            public void onFailure(Call<ListItemCourses> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());

            }
        });
        mSlider.setPresetTransformer(SliderLayout.Transformer.DepthPage);
        mSlider.setCustomIndicator((PagerIndicator) findViewById(R.id.custom_indicator));
        mSlider.addOnPageChangeListener(this);
    }

    private void initBroadcast() {
        mBroadCast = new NetworkBroadCast();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mBroadCast, intentFilter);
    }

    private void setActions() {
        mLnSee.setOnClickListener(this);
        mDigtial.setOnClickListener(this);
        mContent.setOnClickListener(this);
        mBand.setOnClickListener(this);
        mPrEvent.setOnClickListener(this);
        mTrade.setOnClickListener(this);
        mVideo.setOnClickListener(this);
    }

    private void sendNotification() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = 60 * 60 * 1000;
        manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + DELAY, interval, pendingIntent);
    }

    private boolean checkConnected() {
        if (!Util.isConnected(this)) {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            checkConnected();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            System.exit(0);
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Lỗi kết nối. Bạn có muốn thử lại ?")
                    .setPositiveButton("Kết nối", dialogClickListener)
                    .setNegativeButton("Hủy", dialogClickListener)
                    .setCancelable(false)
                    .show();

            return false;
        } else {
            return true;
        }
    }

    private class NetworkBroadCast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
                NetworkInfo networkInfo =
                        intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                if (networkInfo.isConnected()) {
                    findViewById(R.id.rl_course).setVisibility(View.VISIBLE);
                    initSlider();
                    mSlider.startAutoCycle();
                    Snackbar.make(findViewById(android.R.id.content), "Đã kết nối", Snackbar.LENGTH_LONG).show();
                }
            } else if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                NetworkInfo networkInfo =
                        intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI && !networkInfo.isConnected()) {
                    findViewById(R.id.rl_course).setVisibility(View.GONE);
                    checkConnected();
                    Snackbar.make(findViewById(android.R.id.content), "Đã ngắt kết nối", Snackbar.LENGTH_LONG).show();
                }
            }
        }
    }
}