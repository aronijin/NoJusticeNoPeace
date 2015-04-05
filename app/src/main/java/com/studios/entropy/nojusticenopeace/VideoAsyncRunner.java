package com.studios.entropy.nojusticenopeace;

import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.io.IOException;

/**
 * Created by Nathan Heard on 4/5/2015.
 */
public class VideoAsyncRunner extends AsyncTask<Integer, Integer, String> {

    private static final String VIDEO_ASYNC_RUNNER_TAG = "VideoAsyncRunner";
    private static Context applicationContext;
    private static NotificationManager mNotifyMgr;

    @Override
    protected String doInBackground(Integer... params) {

        Integer videoDurationMin = params[0];

        for (int i = 0; i < videoDurationMin; i++) {
            try {
                Thread.sleep(1000);
                Log.i(VIDEO_ASYNC_RUNNER_TAG, "i: " + i);
            } catch (InterruptedException e) {
                Thread.interrupted();
            }
        }
        return "Executed";
    }

    @Override
    protected void onPostExecute(String result) {
        VideoHelper.stopVideo();
        Log.i(VIDEO_ASYNC_RUNNER_TAG, "Video Stopped!");
        //rebitrhNotification();
        Log.i(VIDEO_ASYNC_RUNNER_TAG, "Notification Re-birthed!");
    }

    @Override
    protected void onPreExecute() {
        try {
            VideoHelper.runVideo();
            Log.i(VIDEO_ASYNC_RUNNER_TAG, "Video Started!");
        } catch (IOException e) {
            Log.i(VIDEO_ASYNC_RUNNER_TAG, "Error executing video recorder: " + e.getMessage());
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        // Used for updating progress
    }

    public void rebitrhNotification() {
        NotificationCompat.Builder mBuilder = NJNPNotificationBuilder.buildNotification(applicationContext);
        mNotifyMgr.notify(NJNPConstants.mNotificationId, mBuilder.build());
    }


    public static void setApplicationContext(Context applicationContext) {
        VideoAsyncRunner.applicationContext = applicationContext;
    }

    public static void setmNotifyMgr(NotificationManager mNotifyMgr) {
        VideoAsyncRunner.mNotifyMgr = mNotifyMgr;
    }

}