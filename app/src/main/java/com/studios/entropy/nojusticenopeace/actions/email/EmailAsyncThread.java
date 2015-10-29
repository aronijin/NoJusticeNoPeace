package com.studios.entropy.nojusticenopeace.actions.email;

import android.os.AsyncTask;
import android.util.Log;

/**
 * @author Nathan Heard on 10/28/2015.
 */
public class EmailAsyncThread extends AsyncTask<String, Integer, String> {

    private static final String TAG_NJNP_EMAIL_THREAD = "EmailAsyncThread";

    @Override
    protected String doInBackground(String... params) {

        String toStr = params[0];
        Log.i(TAG_NJNP_EMAIL_THREAD, "String passed" + toStr);

        //TODO Send emails
        return "Executed";
    }

    @Override
    protected void onPostExecute(String result) {
        Log.i(TAG_NJNP_EMAIL_THREAD, "Emails sent");

//        Intent audioCompletionIntent = new Intent(NJNPConstants.ACTION_EMAIL);
//        applicationContext.sendBroadcast(audioCompletionIntent);
//        Log.i(TAG_NJNP_EMAIL_THREAD, "Broadcast email completion!");
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        // Used for updating progress
    }
}
