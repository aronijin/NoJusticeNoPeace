package com.studios.entropy.nojusticenopeace;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executors;

/**
 *
 * Class used to run all desired asynchronous tasks for
 * No Justice No Peace upon user activation
 *
 * Created by Nathan Heard on 3/23/2015.
 */
public class NJNPBackgroundService extends IntentService  {

    private static final String NJNP_Background_TAG = "NJNPBackgroundActivity";
    private MediaRecorder recorder;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public NJNPBackgroundService() {
        super(NJNP_Background_TAG);
        Log.i(NJNP_Background_TAG, "Constructor in NJNPBackgroundService is called.");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(NJNP_Background_TAG, "Creating NJNPBackgroundService");

        // Run async task to perform desired operations
        AsyncTask<String, Integer, String> asyncTask = new AudioAsyncRunner();
        asyncTask.executeOnExecutor(Executors.newSingleThreadExecutor());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(NJNP_Background_TAG, "Destroying NJNPBackgroundService.");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    private class AudioAsyncRunner extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {


            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(1000);
                    Log.i(NJNP_Background_TAG, "i: " + i);
                } catch (InterruptedException e) {
                    Thread.interrupted();
                }
            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            AudioHelper.stopAudio();
            Log.i(NJNP_Background_TAG, "Audio Stopped!");

            NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationCompat.Builder mBuilder = NJNPNotificationBuilder.buildNotification(NJNPBackgroundService.this);
            mNotifyMgr.notify(NJNPConstants.mNotificationId, mBuilder.build());

        }

        @Override
        protected void onPreExecute() {
            try {
                AudioHelper.runAudio();
                Log.i(NJNP_Background_TAG, "Audio Started!");
            } catch (IOException e) {
                Log.i(NJNP_Background_TAG, "Error executing audio recorder: " + e.getMessage());
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            // Used for updating progress
        }
    }
}
