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

import java.io.File;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_njnp);

        Log.i(NJNP_TAG, "Starting No Justice No Peace App");

        // Create directory
        File NJNPDirectory = new File(NJNPConstants.DIRECTORY_PATH);
        NJNPDirectory.mkdirs();

        // Grab UI components
        Switch audioToggleBtn = (Switch) this.findViewById(R.id.audio_toggle_btn);
        Switch videoToggleBtn = (Switch) this.findViewById(R.id.video_toggle_btn);
        CheckBox frontCameraCheckboxBtn = (CheckBox) this.findViewById(R.id.front_camera_checkbox_btn);
        Switch smsToggleBtn = (Switch) this.findViewById(R.id.sms_toggle_btn);
        Switch emailToggleBtn = (Switch) this.findViewById(R.id.email_toggle_btn);
        Switch dropboxToggleBtn = (Switch) this.findViewById(R.id.dropbox_toggle_btn);
        Switch keepOnDeviceToggleBtn = (Switch) this.findViewById(R.id.keep_on_device_toggle_btn);
        ToggleButton startToggleBtn = (ToggleButton) this.findViewById(R.id.start_toggle_btn);

        audioToggleBtn.setOnCheckedChangeListener(onAudioToggle);
        videoToggleBtn.setOnCheckedChangeListener(onVideoToggle);
        frontCameraCheckboxBtn.setOnCheckedChangeListener(onFrontCameraCheckbox);
        smsToggleBtn.setOnCheckedChangeListener(onSMSToggle);
        emailToggleBtn.setOnCheckedChangeListener(onEmailToggle);
        dropboxToggleBtn.setOnCheckedChangeListener(onDropboxToggle);
        keepOnDeviceToggleBtn.setOnCheckedChangeListener(onKeepOnDeviceToggle);
        startToggleBtn.setOnCheckedChangeListener(onStartToggle);


        EditText audioEditText = (EditText) this.findViewById(R.id.audio_edit_text);
        EditText videoEditText = (EditText) this.findViewById(R.id.video_edit_text);

        audioEditText.addTextChangedListener(audioTextWatcher);
        videoEditText.addTextChangedListener(videoTextWatcher);
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
        }
    };

    CompoundButton.OnCheckedChangeListener onVideoToggle = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            NJNPNotificationBuilder.setVideoStatus(isChecked);
        }
    };

    CompoundButton.OnCheckedChangeListener onFrontCameraCheckbox = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            NJNPNotificationBuilder.setFrontCameraStatus(isChecked);
        }
    };


    CompoundButton.OnCheckedChangeListener onSMSToggle = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            NJNPNotificationBuilder.setSmsStatus(isChecked);
        }
    };

    CompoundButton.OnCheckedChangeListener onEmailToggle = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            NJNPNotificationBuilder.setEmailStatus(isChecked);
        }
    };

    CompoundButton.OnCheckedChangeListener onDropboxToggle = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            NJNPNotificationBuilder.setDropboxStatus(isChecked);
        }
    };

    CompoundButton.OnCheckedChangeListener onKeepOnDeviceToggle = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            NJNPNotificationBuilder.setLocalStatus(isChecked);
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
