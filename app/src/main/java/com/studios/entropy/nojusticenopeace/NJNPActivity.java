package com.studios.entropy.nojusticenopeace;

import android.media.MediaRecorder;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.ToggleButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @authior Nathanial Heard, Joesph Herndon
 *
 */
public class NJNPActivity extends ActionBarActivity {

    private static final String NJNP_TAG = "NJNPActivity";
    private static final String DIRECTORY_PATH = "/sdcard/NoJusticeNoPeace/";
    private static final String AUDIO_FILE_NAME = "audio_file_";
    private static final String DATE_FORMAT = "yyyy_MM_dd";

    private static boolean audioStatus;
    private static boolean videoStatus;
    private static boolean smsStatus;
    private static boolean emailStatus;
    private static boolean dropboxStatus;
    private static boolean localStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_njnp);

        // Create directory
        File NJNPDirectory = new File(DIRECTORY_PATH);
        NJNPDirectory.mkdirs();

        // Grab UI components
        Switch audioToggleBtn = (Switch) findViewById(R.id.audio_toggle_btn);
        Switch videoToggleBtn = (Switch) findViewById(R.id.video_toggle_btn);
        Switch smsToggleBtn = (Switch) findViewById(R.id.sms_toggle_btn);
        Switch emailToggleBtn = (Switch) findViewById(R.id.email_toggle_btn);
        Switch dropboxToggleBtn = (Switch) findViewById(R.id.dropbox_toggle_btn);
        Switch keepOnDeviceToggleBtn = (Switch) findViewById(R.id.keep_on_device_toggle_btn);
        ToggleButton startToggleBtn = (ToggleButton) findViewById(R.id.start_toggle_btn);

        audioToggleBtn.setOnClickListener(onAudioToggle);
        videoToggleBtn.setOnClickListener(onVideoToggle);
        smsToggleBtn.setOnClickListener(onSMSToggle);
        emailToggleBtn.setOnClickListener(onEmailToggle);
        dropboxToggleBtn.setOnClickListener(onDropboxToggle);
        keepOnDeviceToggleBtn.setOnClickListener(onKeepOnDeviceToggle);
        startToggleBtn.setOnClickListener(onStartToggle);
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

    View.OnClickListener onAudioToggle = new View.OnClickListener() {
        public void onClick(View view) {
            boolean audioOn = ((Switch) view).isChecked();
            audioStatus = audioOn;
            Log.i(NJNP_TAG, "Audio: " + audioStatus);
            if (audioOn) {
                // Enable vibrate
            } else {
                // Disable vibrate
            }
        }
    };

    View.OnClickListener onVideoToggle = new View.OnClickListener() {
        public void onClick(View view) {
            boolean videoOn = ((Switch) view).isChecked();
            videoStatus = videoOn;

            if (videoOn) {
                // Enable vibrate
            } else {
                // Disable vibrate
            }
        }
    };

    View.OnClickListener onSMSToggle = new View.OnClickListener() {
        public void onClick(View view) {
            boolean smsOn = ((Switch) view).isChecked();
            smsStatus = smsOn;

            if (smsOn) {
                // Enable vibrate
            } else {
                // Disable vibrate
            }
        }
    };

    View.OnClickListener onEmailToggle = new View.OnClickListener() {
        public void onClick(View view) {
            boolean emailOn = ((Switch) view).isChecked();
            emailStatus = emailOn;

            if (emailOn) {
                // Enable vibrate
            } else {
                // Disable vibrate
            }
        }
    };

    View.OnClickListener onDropboxToggle = new View.OnClickListener() {
        public void onClick(View view) {
            boolean dropboxOn = ((Switch) view).isChecked();
            dropboxStatus = dropboxOn;

            if (dropboxOn) {
                // Enable vibrate
                //TODO implement dropbox login
            } else {
                // Disable vibrate
            }
        }
    };

    View.OnClickListener onKeepOnDeviceToggle = new View.OnClickListener() {
        public void onClick(View view) {
            boolean localOn = ((Switch) view).isChecked();
            localStatus = localOn;

            if (localOn) {
                // Enable vibrate
            } else {
                // Disable vibrate
            }
        }
    };

    View.OnClickListener onStartToggle = new View.OnClickListener() {
        public void onClick(View view) {
            boolean startOn = ((ToggleButton) view).isChecked();

            Log.i(NJNP_TAG, "Start: " + startOn);
            if (startOn) {
                // Enable vibrate
                runAudio();
                runVideo();
                runSMS();
                runEmail();
                runDropbox();
                runKeepOnDevice();
            } else {
                // Disable vibrate
            }
        }
    };

    private void runAudio() {
        if (audioStatus) {
            //TODO implement audio recording
            MediaRecorder recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.US);

            recorder.setOutputFile(DIRECTORY_PATH + AUDIO_FILE_NAME + sdf.format(new Date()) + ".3gp");
            try {
                recorder.prepare();
            } catch (IOException e) {
                Log.e(NJNP_TAG, "Error on prepare record: " + e.getMessage());
            }
            recorder.start();   // Recording is now started
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                Log.e(NJNP_TAG, "Error on thread sleep: " + e.getMessage());
            }
            recorder.stop();
            recorder.reset();   // You can reuse the object by going back to setAudioSource() step
            recorder.release();
        }
    }

    private void runVideo() {
        if (videoStatus) {
            //TODO implement video recording
        }
    }

    private void runSMS() {
        if (smsStatus) {
            //TODO implement sms sending
        }
    }

    private void runEmail() {
        if (emailStatus) {
            //TODO implement email sending
        }
    }

    private void runDropbox() {
        if (dropboxStatus) {
            //TODO implement dropbox saving
        }
    }

    private void runKeepOnDevice() {
        if (localStatus) {
            //TODO implement local saving
        }
    }
}
