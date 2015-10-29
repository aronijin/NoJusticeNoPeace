package com.studios.entropy.nojusticenopeace.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.studios.entropy.nojusticenopeace.actions.audio.AudioAsyncThread;
import com.studios.entropy.nojusticenopeace.actions.email.EmailAsyncThread;
import com.studios.entropy.nojusticenopeace.models.NJNPConstants;
import com.studios.entropy.nojusticenopeace.actions.video.VideoCaptureActivity;

import java.io.File;
import java.util.concurrent.Executors;

/**
 * @author Nathan Heard on 3/23/2015.
 *
 * Class used to run all desired asynchronous tasks for
 * No Justice No Peace upon user activation
 *
 */
public class NJNPBackgroundService extends IntentService  {

    private static final String NJNP_BACKGROUND_SERVICE_TAG = "NJNPBackgroundActivity";
    private MediaRecorder recorder;

    private static boolean audioStatus;
    private static boolean videoStatus;
    private static boolean smsStatus;
    private static boolean emailStatus;
    private static boolean dropboxStatus;

    private static int audioDurationMin;
    private static int videoDurationMin;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public NJNPBackgroundService() {
        super(NJNP_BACKGROUND_SERVICE_TAG);
        Log.i(NJNP_BACKGROUND_SERVICE_TAG, "Constructor in NJNPBackgroundService is called.");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(NJNP_BACKGROUND_SERVICE_TAG, "Creating NJNPBackgroundService");
        audioStatus = false;
        videoStatus = false;
        smsStatus = false;
        emailStatus = false;
        dropboxStatus = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {

            Bundle extras = intent.getExtras();
            if (extras != null) {
                // Grab all statuses
                audioStatus = extras.getBoolean("audioStatus");
                videoStatus = extras.getBoolean("videoStatus");
                smsStatus = extras.getBoolean("smsStatus");
                emailStatus = extras.getBoolean("emailStatus");
                dropboxStatus = extras.getBoolean("dropboxStatus");

                // Grab all durations
                audioDurationMin = extras.getInt("audioDurationMin");
                videoDurationMin = extras.getInt("videoDurationMin");
                Log.i(NJNP_BACKGROUND_SERVICE_TAG, "video duration in background service: " + videoDurationMin);
            }
        }
        Log.i(NJNP_BACKGROUND_SERVICE_TAG, "Starting NJNPBackgroundService");

        // Run async task to perform desired operations
        if (audioStatus && !videoStatus) {
            startAudioThread();
        } else {
            Intent flagAudioIntent = new Intent(NJNPConstants.ACTION_AUDIO);
            sendBroadcast(flagAudioIntent);
        }

        if (videoStatus) {
            startVideoActivity();
        } else {
            Intent flagVideoIntent = new Intent(NJNPConstants.ACTION_VIDEO);
            sendBroadcast(flagVideoIntent);
        }

        if (emailStatus) {
            //TODO email async task
            startEmailThread();
        } else {
            // Be sure to add broadcast flag
            Intent flagEmailIntent = new Intent(NJNPConstants.ACTION_EMAIL);
            sendBroadcast(flagEmailIntent);
        }
        //TODO sms async task

        // Be sure to add broadcast flag
        Intent flagSMSIntent = new Intent(NJNPConstants.ACTION_SMS);
        sendBroadcast(flagSMSIntent);

        //TODO dropbox async task

        // Be sure to add broadcast flag
        Intent flagDropboxIntent = new Intent(NJNPConstants.ACTION_DROPBOX);
        sendBroadcast(flagDropboxIntent);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(NJNP_BACKGROUND_SERVICE_TAG, "Destroying NJNPBackgroundService.");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    /**
     * Method used to create and start asynchronous email thread
     */
    private void startEmailThread() {
        AsyncTask<String, Integer, String> asyncTask = new EmailAsyncThread();
        asyncTask.executeOnExecutor(Executors.newSingleThreadExecutor(), "String ONe");
    }

    /**
     * Method used to create and start asynchronous audio thread
     */
    private void startAudioThread() {
        AsyncTask<Integer, Integer, String> asyncTask = new AudioAsyncThread();
        //((NJNPAudioAsyncThread)asyncTask).setApplicationContext(NJNPBackgroundService.this);
        asyncTask.executeOnExecutor(Executors.newSingleThreadExecutor(), audioDurationMin);
    }

    /**
     * Method used to create and start video activity
     */
    private void startVideoActivity() {
        File NJNPVideoDirectory = new File(NJNPConstants.DIRECTORY_PATH + NJNPConstants.VIDEO_FOLDER);
        if (!NJNPVideoDirectory.exists()) {
            NJNPVideoDirectory.mkdirs();
        }

        boolean isCameraAvailable = checkCameraHardware(NJNPBackgroundService.this);
        if (isCameraAvailable) {
            Intent videoCaptureIntent = new Intent(NJNPBackgroundService.this, VideoCaptureActivity.class);
            videoCaptureIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            videoCaptureIntent.putExtra(NJNPConstants.VIDEO_DURATION_EXTRA, videoDurationMin);
            startActivity(videoCaptureIntent);
            Log.i(NJNP_BACKGROUND_SERVICE_TAG, "Video Started!");
        } else {
            //TODO inform user of no camera available
        }
    }

    /**
     *  Method used to check if this device has a camera
     */
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            return true;
        } else {
            return false;
        }
    }
}
