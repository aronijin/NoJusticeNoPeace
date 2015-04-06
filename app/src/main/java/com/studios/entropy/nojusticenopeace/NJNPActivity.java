package com.studios.entropy.nojusticenopeace;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.ToggleButton;

import com.studios.entropy.nojusticenopeace.helpers.NJNPConstants;
import com.studios.entropy.nojusticenopeace.models.SettingsModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;

/**
 *
 * Class used to set desired settings for generated notification activation
 *
 * @authior Nathanial Heard, Joseph Herndon
 *
 */
public class NJNPActivity extends ActionBarActivity {

    private static final String NJNP_TAG = "NJNPActivity";
    private static final BroadcastReceiver NJNPReceiver = new NJNPBroadcastReceiver();

    private static Switch audioToggleBtn;
    private static Switch videoToggleBtn;
    private static CheckBox frontCameraCheckboxBtn;
    private static Switch smsToggleBtn;
    private static Switch emailToggleBtn;
    private static Switch dropboxToggleBtn;
    private static Switch keepOnDeviceToggleBtn;
    private static ToggleButton startToggleBtn;

    private static EditText audioEditText;
    private static EditText videoEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_njnp);
        Log.i(NJNP_TAG, "Starting No Justice No Peace!");

        // Grab UI components and attach action listeners
        audioToggleBtn = (Switch) this.findViewById(R.id.audio_toggle_btn);
        videoToggleBtn = (Switch) this.findViewById(R.id.video_toggle_btn);
        frontCameraCheckboxBtn = (CheckBox) this.findViewById(R.id.front_camera_checkbox_btn);
        smsToggleBtn = (Switch) this.findViewById(R.id.sms_toggle_btn);
        emailToggleBtn = (Switch) this.findViewById(R.id.email_toggle_btn);
        dropboxToggleBtn = (Switch) this.findViewById(R.id.dropbox_toggle_btn);
        keepOnDeviceToggleBtn = (Switch) this.findViewById(R.id.keep_on_device_toggle_btn);
        startToggleBtn = (ToggleButton) this.findViewById(R.id.start_toggle_btn);

        audioToggleBtn.setOnCheckedChangeListener(onAudioToggle);
        videoToggleBtn.setOnCheckedChangeListener(onVideoToggle);
        frontCameraCheckboxBtn.setOnCheckedChangeListener(onFrontCameraCheckbox);
        smsToggleBtn.setOnCheckedChangeListener(onSMSToggle);
        emailToggleBtn.setOnCheckedChangeListener(onEmailToggle);
        dropboxToggleBtn.setOnCheckedChangeListener(onDropboxToggle);
        keepOnDeviceToggleBtn.setOnCheckedChangeListener(onKeepOnDeviceToggle);
        startToggleBtn.setOnCheckedChangeListener(onStartToggle);

        audioEditText = (EditText) this.findViewById(R.id.audio_edit_text);
        videoEditText = (EditText) this.findViewById(R.id.video_edit_text);

        audioEditText.addTextChangedListener(audioTextWatcher);
        videoEditText.addTextChangedListener(videoTextWatcher);

        if(areSettingsPresent()) {
            Log.i(NJNP_TAG, "Loading previous settings...");
            loadSettings();
        } else {
            // Create directory
            Log.i(NJNP_TAG, "Creating new settings...");
            File NJNPDirectory = new File(NJNPConstants.DIRECTORY_PATH);
            NJNPDirectory.mkdirs();
        }
    }

    private void loadSettings() {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(NJNPConstants.DIRECTORY_PATH + NJNPConstants.SETTINGS_FOLDER + NJNPConstants.SETTINGS_FILE_NAME);
            ObjectInputStream is = new ObjectInputStream(fis);
            SettingsModel settingsModel = (SettingsModel) is.readObject();
            is.close();
            fis.close();

            //Update UI components
            audioToggleBtn.setChecked(settingsModel.isAudioState());
            videoToggleBtn.setChecked(settingsModel.isVideoState());
            frontCameraCheckboxBtn.setChecked(settingsModel.isFrontCameraState());
            smsToggleBtn.setChecked(settingsModel.isSmsState());
            emailToggleBtn.setChecked(settingsModel.isEmailState());
            dropboxToggleBtn.setChecked(settingsModel.isDropboxState());
            keepOnDeviceToggleBtn.setChecked(settingsModel.isLocalState());
            startToggleBtn.setChecked(settingsModel.isStartState());

            audioEditText.setText(settingsModel.getAudioDuration() + "");
            videoEditText.setText(settingsModel.getVideoDuration() + "");

        } catch (FileNotFoundException e) {
            Log.e(NJNP_TAG, "File Not Found Exception when loading settings: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e(NJNP_TAG, "Class Not Found Exception when loading settings: " + e.getMessage());
        } catch (OptionalDataException e) {
            Log.e(NJNP_TAG, "Option Data Exception when loading settings: " + e.getMessage());
        } catch (StreamCorruptedException e) {
            Log.e(NJNP_TAG, "Stream Corruption Exception when loading settings: " + e.getMessage());
        } catch (IOException e) {
            Log.e(NJNP_TAG, "IO Exception when loading settings: " + e.getMessage());
        }
    }

    private void saveSettings() {
        //Save state of settings to settings object
        SettingsModel settingsModel = new SettingsModel();
        settingsModel.saveState(NJNPActivity.this);

        File NJNPSettingsDirectory = new File(NJNPConstants.DIRECTORY_PATH + NJNPConstants.SETTINGS_FOLDER);
        NJNPSettingsDirectory.mkdirs();

        File file = new File(NJNPSettingsDirectory.getPath(), NJNPConstants.SETTINGS_FILE_NAME);

        FileOutputStream fos = null;
        ObjectOutputStream os = null;
        try {
            fos = new FileOutputStream(file);
            os = new ObjectOutputStream(fos);
            os.writeObject(settingsModel);
            os.close();
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e(NJNP_TAG, "File not found exception when saving settings: " + e.getMessage());
        } catch (IOException e) {
            Log.e(NJNP_TAG, "IO Exception when saving settings: " + e.getMessage());
        }

    }

    private boolean areSettingsPresent() {
        File settingsFile = getSettingsFile();
        if(settingsFile.exists()) {
            return true;
        }
        return false;
    }

    private File getSettingsFile() {
        return new File(NJNPConstants.DIRECTORY_PATH + NJNPConstants.SETTINGS_FOLDER + NJNPConstants.SETTINGS_FILE_NAME);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_njn, menu);
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

    TextWatcher audioTextWatcher =  new TextWatcher(){
        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() > 0) {
                NJNPNotificationBuilder.setAudioDurationMin(Integer.valueOf(s.toString()));
            } else {
                NJNPNotificationBuilder.setAudioDurationMin(NJNPConstants.DEFAULT_DURATION);
            }

            saveSettings();
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after){

        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count){

        }
    };

    TextWatcher videoTextWatcher =  new TextWatcher(){
        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() > 0) {
                NJNPNotificationBuilder.setAudioDurationMin(Integer.valueOf(s.toString()));
            } else {
                NJNPNotificationBuilder.setAudioDurationMin(NJNPConstants.DEFAULT_DURATION);
            }

            saveSettings();
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after){

        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count){

        }
    };

    CompoundButton.OnCheckedChangeListener onAudioToggle = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            NJNPNotificationBuilder.setAudioStatus(isChecked);

            saveSettings();
        }
    };

    CompoundButton.OnCheckedChangeListener onVideoToggle = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            NJNPNotificationBuilder.setVideoStatus(isChecked);

            saveSettings();
        }
    };

    CompoundButton.OnCheckedChangeListener onFrontCameraCheckbox = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            NJNPNotificationBuilder.setFrontCameraStatus(isChecked);

            saveSettings();
        }
    };


    CompoundButton.OnCheckedChangeListener onSMSToggle = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            NJNPNotificationBuilder.setSmsStatus(isChecked);

            saveSettings();
        }
    };

    CompoundButton.OnCheckedChangeListener onEmailToggle = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            NJNPNotificationBuilder.setEmailStatus(isChecked);

            saveSettings();
        }
    };

    CompoundButton.OnCheckedChangeListener onDropboxToggle = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            NJNPNotificationBuilder.setDropboxStatus(isChecked);

            saveSettings();
        }
    };

    CompoundButton.OnCheckedChangeListener onKeepOnDeviceToggle = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            NJNPNotificationBuilder.setLocalStatus(isChecked);

            saveSettings();
        }
    };

    CompoundButton.OnCheckedChangeListener onStartToggle = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            NotificationManager mNotifyMgr = (NotificationManager) NJNPActivity.this.getSystemService(NOTIFICATION_SERVICE);

            if (isChecked) {

                // Register Broadcast Receiver
                IntentFilter filter = new IntentFilter();
                filter.addAction(NJNPConstants.ACTION_AUDIO);
                filter.addAction(NJNPConstants.ACTION_VIDEO);
                filter.addAction(NJNPConstants.ACTION_EMAIL);
                filter.addAction(NJNPConstants.ACTION_SMS);
                filter.addAction(NJNPConstants.ACTION_DROPBOX);
                filter.addAction(NJNPConstants.ACTION_FRONTCAMERA);
                filter.addAction(NJNPConstants.ACTION_LOCAL);
                registerReceiver(NJNPReceiver, filter);

                // Create Notification
                NotificationCompat.Builder mBuilder = NJNPNotificationBuilder.buildNotification(NJNPActivity.this);
                mNotifyMgr.notify(NJNPConstants.mNotificationId, mBuilder.build());
            } else if (!isChecked) {
                // Cancel Notification and Unregister Broadcast Receiver
                mNotifyMgr.cancel(NJNPConstants.mNotificationId);
                unregisterReceiver(NJNPReceiver);
            }
        }
    };
}
