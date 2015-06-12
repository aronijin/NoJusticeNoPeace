package com.studios.entropy.nojusticenopeace;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;


import com.studios.entropy.nojusticenopeace.helpers.NJNPConstants;
import com.studios.entropy.nojusticenopeace.helpers.VideoRecorderService;

import java.io.IOException;

//
//Created by Joseph Herndon on 3/24/15.
//
public class NJNPVideoCaptureActivity extends Activity implements SurfaceHolder.Callback {

    // Static Variables
    private static final String NJNP_VIDEO_CAPTURE_ACTIVITY_TAG = "Video Capture";
    public static Camera mCamera;
    public static SurfaceView surfaceView;
    public static SurfaceHolder surfaceViewHolder;
    private static int videoDuration;

    // Non-static Variables
    private Intent videoCaptureIntent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_capture);

        clearCamera();

        surfaceView = (SurfaceView) findViewById(R.id.CameraView);
        surfaceViewHolder = surfaceView.getHolder();
        surfaceViewHolder.addCallback(NJNPVideoCaptureActivity.this);

        // Setup surface view
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        Intent intent = getIntent();
        videoDuration = intent.getIntExtra(NJNPConstants.VIDEO_DURATION_EXTRA, 1);
        convertMinToMilliSec();
    }

    public void surfaceCreated(SurfaceHolder holder) {
        try {
            if (mCamera != null) {
                mCamera.setPreviewDisplay(surfaceViewHolder);
                mCamera.startPreview();
            }
        } catch (IOException e) {
            Log.e(NJNP_VIDEO_CAPTURE_ACTIVITY_TAG, "Error on surface created, error: " + e.getMessage());
        }

        videoCaptureIntent = new Intent(NJNPVideoCaptureActivity.this, VideoRecorderService.class);
        videoCaptureIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        videoCaptureIntent.putExtra(NJNPConstants.VIDEO_DURATION_EXTRA, videoDuration);
        startService(videoCaptureIntent);

        Thread thread = new Thread() {
            @Override
            public void run() {
                int i = 0;
                while(i < videoDuration) {
                    i++;
                    try {
                        sleep(1000);
                    } catch (InterruptedException err) {
                        Log.e(NJNP_VIDEO_CAPTURE_ACTIVITY_TAG, "Interrupted Exception with message: " + err.getMessage());
                    }
                }
                finish();
            }
        };

        thread.start();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        try {
            if (mCamera != null) {
                mCamera.setPreviewDisplay(surfaceViewHolder);
                mCamera.startPreview();
            }
        } catch (IOException e) {
            Log.e(NJNP_VIDEO_CAPTURE_ACTIVITY_TAG, "Error on surface changed, error: " + e.getMessage());
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        super.onDestroy();

        stopService(videoCaptureIntent);
        clearCamera();

        Intent videoCompletionIntent = new Intent(NJNPConstants.ACTION_VIDEO);
        sendBroadcast(videoCompletionIntent);
        Log.i(NJNP_VIDEO_CAPTURE_ACTIVITY_TAG, "Broadcast video completion!");
    }

    private void clearCamera(){
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }

    private int convertMinToMilliSec() {
        // TODO Actually convert to min with * 60, but for testing lets stay with seconds
        videoDuration = videoDuration * 10;
        return videoDuration;
    }

}
