package com.example.hdt.appcourses.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;

import com.example.hdt.appcourses.common.Constants;

public class Util implements Constants {

    public static Bitmap getLargeBitmap(Resources resources, int idImg) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, idImg, options);

        int scale = 2;
        int widthImg = options.outWidth;
        int heightImg = options.outHeight;

        if (WIDTH_SCREEN >= widthImg && HEIGHT_SCREEN >= heightImg) {
            options.inSampleSize = 1;
        } else {
            while ((widthImg / scale) >= WIDTH_SCREEN && (heightImg / scale) >= HEIGHT_SCREEN) {
                scale += 2;
            }
            options.inSampleSize = scale;
        }
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(resources, idImg, options);
    }

    public static float convertDpToPixel(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static float convertPixelsToDp(float px) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
