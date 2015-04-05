package com.studios.entropy.nojusticenopeace;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.io.IOException;

/**
 * Created by Nathan Heard on 4/5/2015.
 */
public class AudioAsyncRunner extends AsyncTask<Integer, Integer, String> {

    private static final String AUDIO_ASYNC_RUNNER_TAG = "AudioAsyncRunner";
    private static Context applicationContext;
    private static NotificationManager mNotifyMgr;

    @Override
    protected String doInBackground(Integer... params) {

        Integer audioDurationMin = params[0];

        for (int i = 0; i < audioDurationMin; i++) {
            try {
                Thread.sleep(1000);
                Log.i(AUDIO_ASYNC_RUNNER_TAG, "i: " + i);
            } catch (InterruptedException e) {
                Thread.interrupted();
            }
        }
        return "Executed";
    }

    @Override
    protected void onPostExecute(String result) {
        AudioHelper.stopAudio();
        Log.i(AUDIO_ASYNC_RUNNER_TAG, "Audio Stopped!");

        Intent intent = new Intent(NJNPConstants.ACTION_AUDIO);
        applicationContext.sendBroadcast(intent);
        Log.i(AUDIO_ASYNC_RUNNER_TAG, "Broadcast audio completion!");
    }

    @Override
    protected void onPreExecute() {
        try {
            AudioHelper.runAudio();
            Log.i(AUDIO_ASYNC_RUNNER_TAG, "Audio Started!");
        } catch (IOException e) {
            Log.i(AUDIO_ASYNC_RUNNER_TAG, "Error executing audio recorder: " + e.getMessage());
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        // Used for updating progress
    }

    public static void setApplicationContext(Context applicationContext) {
        AudioAsyncRunner.applicationContext = applicationContext;
    }
}