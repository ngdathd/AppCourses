package com.example.hdt.appcourses.broadcast;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.example.hdt.appcourses.R;
import com.example.hdt.appcourses.activities.WebActivity;
import com.example.hdt.appcourses.common.Constants;
import com.example.hdt.appcourses.objects.ItemCourse;
import com.example.hdt.appcourses.util.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import static android.content.Context.NOTIFICATION_SERVICE;

public class ShowNotifyReceiver extends BroadcastReceiver
        implements Constants {

    private static final String TAG = ShowNotifyReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        createBigNotification(context);
    }

    private void createBigNotification(Context context) {
        if (Util.isConnected(context)) {
            Log.i(TAG, "createBigNotification: ");
            new generatePictureStyleNotification(context).execute();
        }
    }

    private class generatePictureStyleNotification extends AsyncTask<String, Void, Bitmap> {

        private Context context;
        private ItemCourse itemCourse;

        private generatePictureStyleNotification(Context context) {
            super();
            this.context = context;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            SharedPreferences preferences = context.getSharedPreferences(ITEMCOURSES, Context.MODE_PRIVATE);
            String stringExtra = preferences.getString("ITEM_SHARE", "");
            Type type = new TypeToken<List<ItemCourse>>() {
            }.getType();
            Gson gson = new Gson();
            List<ItemCourse> itemCourses = gson.fromJson(stringExtra, type);

            int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY); // Get hour in 24 hour format
            Log.i(TAG, "onReceive: " + hour);
            if (hour == 6 || hour == 10 || hour == 16 || hour == 20) {
                itemCourse = itemCourses.get(new Random().nextInt(itemCourses.size()));
            }

            InputStream in;
            try {
                URL url = new URL(this.itemCourse.getBanner());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                in = connection.getInputStream();
                return BitmapFactory.decodeStream(in);
            } catch (NullPointerException | IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            Log.i(TAG, "onPostExecute: 1");
            try {
                Intent resultIntent = new Intent(context, WebActivity.class);
                resultIntent.putExtra(KEY_URL, itemCourse.getUrl());
                PendingIntent resultPendingIntent =
                        PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                Notification n = new Notification.Builder(context)
                        .setContentTitle(itemCourse.getTitle())
                        .setContentText(itemCourse.getDes())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setAutoCancel(true)
                        .setContentIntent(resultPendingIntent)
                        .setStyle(new Notification.BigPictureStyle().bigPicture(result))
                        .build();
                Log.i(TAG, "onPostExecute: 2");
                n.defaults = Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE;

                NotificationManager notificationManager =
                        (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

                notificationManager.notify(0, n);
            } catch (NullPointerException e) {
                e.getMessage();
            }
        }
    }
}
