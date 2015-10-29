package com.studios.entropy.nojusticenopeace.actions.audio;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.studios.entropy.nojusticenopeace.models.NJNPConstants;

import java.io.File;
import java.io.IOException;

/**
 * @author Nathan Heard on 4/5/2015.
 */
public class AudioAsyncThread extends AsyncTask<Integer, Integer, String> {

    private static final String AUDIO_ASYNC_RUNNER_TAG = "AudioAsyncRunner";
    private static Context applicationContext;

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

//        Intent audioCompletionIntent = new Intent(NJNPConstants.ACTION_AUDIO);
//        applicationContext.sendBroadcast(audioCompletionIntent);
//        Log.i(AUDIO_ASYNC_RUNNER_TAG, "Broadcast audio completion!");
    }

    @Override
    protected void onPreExecute() {
        File NJNPAudioDirectory = new File(NJNPConstants.DIRECTORY_PATH + NJNPConstants.AUDIO_FOLDER);
        if (!NJNPAudioDirectory.exists()) {
            NJNPAudioDirectory.mkdirs();
        }
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
        AudioAsyncThread.applicationContext = applicationContext;
    }
}