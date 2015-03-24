package com.studios.entropy.nojusticenopeace;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View;


import java.io.IOException;


public abstract class VideoCapture extends NJNPActivity implements OnClickListener, SurfaceHolder.Callback {

    MediaRecorder vidRecorder;
    SurfaceHolder holder;
    boolean recording = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        vidRecorder = new MediaRecorder();
        initRecorder();
        setContentView(R.layout.activity_video_capture);

        SurfaceView cameraView = (SurfaceView) findViewById(R.id.CameraView);
        holder = cameraView.getHolder();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        cameraView.setClickable(true);
        cameraView.setOnClickListener(VideoCapture.this);
    }

    public void initRecorder() {
        vidRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
        vidRecorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);

        CamcorderProfile cpHigh = CamcorderProfile
                .get(CamcorderProfile.QUALITY_HIGH);
        vidRecorder.setProfile(cpHigh);
        vidRecorder.setOutputFile("/sdcard/NoJusticeNoPeace/videocapture_example.mp4");
        vidRecorder.setMaxDuration(10000); // 10 seconds
        vidRecorder.setMaxFileSize(5000000); // Approximately 5 megabytes
    }

    private void prepareRecorder() {
        vidRecorder.setPreviewDisplay(holder.getSurface());

        try {
            vidRecorder.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
            finish();
        } catch (IOException e) {
            e.printStackTrace();
            finish();
        }
    }

    public void onClick(View v) {
        if (recording) {
            vidRecorder.stop();
            recording = false;

            // Let's initRecorder so we can record again
            initRecorder();
            prepareRecorder();
        } else {
            recording = true;
            vidRecorder.start();
        }
    }

    public void surfaceCreated(SurfaceHolder holder) {
        prepareRecorder();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        if (recording) {
            vidRecorder.stop();
            recording = false;
        }
        vidRecorder.release();
        finish();
    }
}
