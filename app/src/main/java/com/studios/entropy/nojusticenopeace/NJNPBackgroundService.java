package com.studios.entropy.nojusticenopeace;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *
 * Class used to run all desired asynchronous tasks for
 * No Justice No Peace upon user activation
 *
 * Created by Nathan Heard on 3/23/2015.
 */
public class NJNPBackgroundService extends Service {

    private static final String NJNP_Background_TAG = "NJNPBackgroundActivity";
    private MediaRecorder recorder;

    /**
     * Creates an IntentService.
     *
     */
    public NJNPBackgroundService() {
        new AudioAsyncRunner().execute("");
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
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
