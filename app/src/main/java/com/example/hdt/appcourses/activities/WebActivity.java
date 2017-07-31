package com.example.hdt.appcourses.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.hdt.appcourses.R;
import com.example.hdt.appcourses.common.Constants;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class WebActivity extends AppCompatActivity
        implements Constants {

    private static final String TAG = WebActivity.class.getSimpleName();
    private WebView mWebView;
    private MaterialProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        findViewByIds();
        initComponents();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy: ");
        super.onDestroy();
    }

    private void findViewByIds() {
        mWebView = (WebView) findViewById(R.id.wv_courses);
        mProgressBar = (MaterialProgressBar) findViewById(R.id.pr_bar);
    }

    private void initComponents() {
        mWebView.getSettings().setJavaScriptEnabled(true);

        Intent receiver = getIntent();
        mWebView.loadUrl(receiver.getStringExtra(KEY_URL));
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                Log.i(TAG, "shouldOverrideUrlLoading: " + url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }
}