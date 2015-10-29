package com.studios.entropy.nojusticenopeace;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.studios.entropy.nojusticenopeace.actions.video.VideoRecorderService;
import com.studios.entropy.nojusticenopeace.models.NJNPConstants;
import com.studios.entropy.nojusticenopeace.models.SettingsModel;
import com.studios.entropy.nojusticenopeace.services.NJNPBackgroundService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;

/**
 * @author Nathan Heard. 10/28/2015
 *
 * Class used to read all predefined settings and start background services
 *
 */
public class NJNPActionStartActivity extends Activity {

    private final static String TAG_NJNP_ACTION_START_ACTIVITY = "NJNPActionStartActivity";
    private boolean audioStatus;
    private boolean videoStatus;
    private boolean smsStatus;
    private boolean emailStatus;
    private boolean dropboxStatus;
    private int audioDurationMin;
    private int videoDurationMin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_start);

        Log.i(TAG_NJNP_ACTION_START_ACTIVITY, "Starting Action Start Activity!");

        Intent backgroundIntent = new Intent(getApplicationContext(), NJNPBackgroundService.class);
        backgroundIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        getSettingValues();

        backgroundIntent.putExtra("audioStatus", audioStatus);
        backgroundIntent.putExtra("videoStatus", videoStatus);
        backgroundIntent.putExtra("smsStatus", smsStatus);
        backgroundIntent.putExtra("emailStatus", emailStatus);
        backgroundIntent.putExtra("dropboxStatus", dropboxStatus);
        backgroundIntent.putExtra("audioDurationMin", audioDurationMin);
        backgroundIntent.putExtra("videoDurationMin", videoDurationMin);
        startService(backgroundIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_action_start, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getSettingValues() {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(NJNPConstants.DIRECTORY_PATH + NJNPConstants.SETTINGS_FOLDER + NJNPConstants.SETTINGS_FILE_NAME);
            ObjectInputStream is = new ObjectInputStream(fis);
            SettingsModel settingsModel = (SettingsModel) is.readObject();
            is.close();
            fis.close();

            //Get all needed values
            audioStatus = settingsModel.isAudioState();
            videoStatus = settingsModel.isVideoState();
            smsStatus = settingsModel.isSmsState();
            emailStatus = settingsModel.isEmailState();
            dropboxStatus = settingsModel.isDropboxState();

            audioDurationMin = settingsModel.getAudioDuration();
            videoDurationMin = settingsModel.getVideoDuration();

        } catch (FileNotFoundException e) {
            Log.e(TAG_NJNP_ACTION_START_ACTIVITY, "File Not Found Exception when loading settings: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e(TAG_NJNP_ACTION_START_ACTIVITY, "Class Not Found Exception when loading settings: " + e.getMessage());
        } catch (OptionalDataException e) {
            Log.e(TAG_NJNP_ACTION_START_ACTIVITY, "Option Data Exception when loading settings: " + e.getMessage());
        } catch (StreamCorruptedException e) {
            Log.e(TAG_NJNP_ACTION_START_ACTIVITY, "Stream Corruption Exception when loading settings: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG_NJNP_ACTION_START_ACTIVITY, "IO Exception when loading settings: " + e.getMessage());
        }
    }
}
