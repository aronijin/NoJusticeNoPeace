package com.studios.entropy.nojusticenopeace;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.ToggleButton;

/**
 *
 * @authior Nathanial Heard, Joesph Herndon
 *
 */
public class NJNPActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_njnp);

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

            if (dropboxOn) {
                // Enable vibrate
            } else {
                // Disable vibrate
            }
        }
    };

    View.OnClickListener onKeepOnDeviceToggle = new View.OnClickListener() {
        public void onClick(View view) {
            boolean localOn = ((Switch) view).isChecked();

            if (localOn) {
                // Enable vibrate
            } else {
                // Disable vibrate
            }
        }
    };

    View.OnClickListener onStartToggle = new View.OnClickListener() {
        public void onClick(View view) {
            boolean startOn = ((Switch) view).isChecked();

            if (startOn) {
                // Enable vibrate
            } else {
                // Disable vibrate
            }
        }
    };
}
