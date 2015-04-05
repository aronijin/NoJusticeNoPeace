package com.studios.entropy.nojusticenopeace;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
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

    private static boolean audioStatus;
    private static boolean videoStatus;
    private static boolean frontCameraStatus;
    private static boolean smsStatus;
    private static boolean emailStatus;
    private static boolean dropboxStatus;
    private static boolean localStatus;

    private static int audioDurationMin;
    private static int videoDurationMin;

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
        audioStatus = false;
        videoStatus = false;
        frontCameraStatus = false;
        smsStatus = false;
        emailStatus = false;
        dropboxStatus = false;
        localStatus = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {

            Bundle extras = intent.getExtras();
            if (extras != null) {
                // Grab all statuses
                audioStatus = extras.getBoolean("audioStatus");
                videoStatus = extras.getBoolean("videoStatus");
                frontCameraStatus = extras.getBoolean("frontCameraStatus");
                smsStatus = extras.getBoolean("smsStatus");
                emailStatus = extras.getBoolean("emailStatus");
                dropboxStatus = extras.getBoolean("dropboxStatus");
                localStatus = extras.getBoolean("localStatus");

                // Grab all durations
                audioDurationMin = extras.getInt("audioDurationMin");
                videoDurationMin = extras.getInt("videoDurationMin");
            }
        }
        Log.i(NJNP_Background_TAG, "Starting NJNPBackgroundService");

        // Setup notificaiton manager
        NotificationManager notificationManager = (NotificationManager) NJNPBackgroundService.this.getSystemService(NOTIFICATION_SERVICE);

        // Run async task to perform desired operations
        if (audioStatus) {
            AsyncTask<Integer, Integer, String> asyncTask = new AudioAsyncRunner();
            ((AudioAsyncRunner)asyncTask).setApplicationContext(NJNPBackgroundService.this);
            ((AudioAsyncRunner)asyncTask).setmNotifyMgr(notificationManager);
            asyncTask.executeOnExecutor(Executors.newSingleThreadExecutor(), audioDurationMin);
        }

        if (videoStatus) {
            AsyncTask<Integer, Integer, String> asyncTask = new VideoAsyncRunner();
            ((VideoAsyncRunner)asyncTask).setApplicationContext(NJNPBackgroundService.this);
            ((VideoAsyncRunner)asyncTask).setmNotifyMgr(notificationManager);
            asyncTask.executeOnExecutor(Executors.newSingleThreadExecutor(), videoDurationMin);
        }

        //TODO front camera async task

        //TODO sms async task

        //TODO email async task

        //TODO dropbox async task

        //TODO local async task

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(NJNP_Background_TAG, "Destroying NJNPBackgroundService.");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
