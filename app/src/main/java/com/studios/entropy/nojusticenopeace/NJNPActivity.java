package com.studios.entropy.nojusticenopeace;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.ToggleButton;

import java.io.File;

/**
 *
 * @authior Nathanial Heard, Joseph Herndon
 *
 */
public class NJNPActivity extends ActionBarActivity {

    private static final String NJNP_TAG = "NJNPActivity";

    private static boolean audioStatus;
    private static boolean videoStatus;
    private static boolean frontCameraStatus;
    private static boolean smsStatus;
    private static boolean emailStatus;
    private static boolean dropboxStatus;
    private static boolean localStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_njnp);

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

    CompoundButton.OnCheckedChangeListener onAudioToggle = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            audioStatus = isChecked;
        }
    };

    CompoundButton.OnCheckedChangeListener onVideoToggle = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            videoStatus = isChecked;
        }
    };

    CompoundButton.OnCheckedChangeListener onFrontCameraCheckbox = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            frontCameraStatus = isChecked;
        }
    };


    CompoundButton.OnCheckedChangeListener onSMSToggle = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            smsStatus = isChecked;
        }
    };

    CompoundButton.OnCheckedChangeListener onEmailToggle = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            emailStatus = isChecked;
        }
    };

    CompoundButton.OnCheckedChangeListener onDropboxToggle = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            dropboxStatus = isChecked;
        }
    };

    CompoundButton.OnCheckedChangeListener onKeepOnDeviceToggle = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            localStatus = isChecked;
        }
    };

    CompoundButton.OnCheckedChangeListener onStartToggle = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            NotificationManager mNotifyMgr = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);

            if (isChecked) {
                // Create notification and add to view
                NotificationCompat.Builder mBuilder = NJNPNotificationBuilder.buildNotification(NJNPActivity.this);
                mNotifyMgr.notify(NJNPConstants.mNotificationId, mBuilder.build());
            } else if (!isChecked) {
                NotificationCompat.Builder mBuilder = NJNPNotificationBuilder.buildNotification(NJNPActivity.this);
                mNotifyMgr.cancel(NJNPConstants.mNotificationId);
            }
        }
    };
}
