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

        // Run async task to perform desired operations
        if (audioStatus) {
            AsyncTask<Integer, Integer, String> asyncTask = new AudioAsyncRunner();
            ((AudioAsyncRunner)asyncTask).setApplicationContext(NJNPBackgroundService.this);
            asyncTask.executeOnExecutor(Executors.newSingleThreadExecutor(), audioDurationMin);
        } else {
            Intent flagAudioIntent = new Intent(NJNPConstants.ACTION_AUDIO);
            sendBroadcast(flagAudioIntent);
        }

        if (videoStatus) {
            AsyncTask<Integer, Integer, String> asyncTask = new VideoAsyncRunner();
            ((VideoAsyncRunner)asyncTask).setApplicationContext(NJNPBackgroundService.this);
            asyncTask.executeOnExecutor(Executors.newSingleThreadExecutor(), videoDurationMin);
        } else {
            Intent flagVideoIntent = new Intent(NJNPConstants.ACTION_VIDEO);
            sendBroadcast(flagVideoIntent);
        }

        //TODO email async task

        // Be sure to add broadcast flag
        Intent flagEmailIntent = new Intent(NJNPConstants.ACTION_EMAIL);
        sendBroadcast(flagEmailIntent);

        //TODO sms async task

        // Be sure to add broadcast flag
        Intent flagSMSIntent = new Intent(NJNPConstants.ACTION_SMS);
        sendBroadcast(flagSMSIntent);

        //TODO dropbox async task

        // Be sure to add broadcast flag
        Intent flagDropboxIntent = new Intent(NJNPConstants.ACTION_DROPBOX);
        sendBroadcast(flagDropboxIntent);

        //TODO front camera async task

        // Be sure to add broadcast flag
        Intent flagFrontCameraIntent = new Intent(NJNPConstants.ACTION_FRONTCAMERA);
        sendBroadcast(flagFrontCameraIntent);

        //TODO local async task
        // Be sure to add broadcast flag
        Intent flagLocalIntent = new Intent(NJNPConstants.ACTION_LOCAL);
        sendBroadcast(flagLocalIntent);

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
