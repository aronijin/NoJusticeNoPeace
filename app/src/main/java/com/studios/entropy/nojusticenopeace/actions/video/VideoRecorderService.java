package com.studios.entropy.nojusticenopeace.actions.video;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.media.MediaRecorder;
import android.os.IBinder;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.studios.entropy.nojusticenopeace.models.NJNPConstants;

public class VideoRecorderService extends Service {

    // Static variables
    private static final String NJNP_VIDEO_RECORDER_SERVICE_TAG = "RecorderService";
    private static SurfaceHolder mSurfaceHolder;
    private static SurfaceView mSurfaceView;
    private static Camera mServiceCamera;
    private static boolean mRecordingStatus;
    private static MediaRecorder mMediaRecorder;

    @Override
    public void onCreate() {
        super.onCreate();

        mRecordingStatus = false;
        mSurfaceView = VideoCaptureActivity.surfaceView;
        mServiceCamera = VideoCaptureActivity.mCamera;
        mSurfaceHolder = VideoCaptureActivity.surfaceViewHolder;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        if (mRecordingStatus == false) {
            startRecording();
        }

        return START_STICKY;
    }

    public boolean startRecording(){
        try {

            mServiceCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
            Camera.Parameters params = mServiceCamera.getParameters();
            mServiceCamera.setParameters(params);
            Camera.Parameters p = mServiceCamera.getParameters();
//            mServiceCamera.setDisplayOrientation(90);

            final List<Size> listSize = p.getSupportedPreviewSizes();
            Size mPreviewSize = listSize.get(2);
            Log.v(NJNP_VIDEO_RECORDER_SERVICE_TAG, "Video: width = " + mPreviewSize.width
                    + " and height = " + mPreviewSize.height);
            p.setPreviewSize(mPreviewSize.width, mPreviewSize.height);
            p.setPreviewFormat(PixelFormat.YCbCr_420_SP);
            mServiceCamera.setParameters(p);

            try {
                mServiceCamera.setPreviewDisplay(mSurfaceHolder);
                mServiceCamera.startPreview();
            }
            catch (IOException e) {
                Log.e(NJNP_VIDEO_RECORDER_SERVICE_TAG, e.getMessage());
                e.printStackTrace();
            }

            // TODO work on quality
            mServiceCamera.unlock();

            mMediaRecorder = new MediaRecorder();
            mMediaRecorder.setCamera(mServiceCamera);
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
            mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT);

            SimpleDateFormat sdf = new SimpleDateFormat(NJNPConstants.DATE_FORMAT, Locale.US);
            mMediaRecorder.setOutputFile(NJNPConstants.DIRECTORY_PATH + NJNPConstants.VIDEO_FOLDER + NJNPConstants.VIDEO_FILE_NAME + sdf.format(new Date()) + ".mp4");

//            CamcorderProfile cpHigh = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
//            mMediaRecorder.setProfile(cpHigh);

            mMediaRecorder.setVideoSize(mPreviewSize.width, mPreviewSize.height);
            mMediaRecorder.setVideoFrameRate(60);
            mMediaRecorder.setVideoEncodingBitRate(3000000);
            mMediaRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());

            mMediaRecorder.prepare();
            mMediaRecorder.start();
            Log.i(NJNP_VIDEO_RECORDER_SERVICE_TAG, "Recording has started!");

            mRecordingStatus = true;

            return true;
        } catch (IllegalStateException e) {
            Log.d(NJNP_VIDEO_RECORDER_SERVICE_TAG, "Illegal State Exception with message: " + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            Log.d(NJNP_VIDEO_RECORDER_SERVICE_TAG, "IO Exception with message: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        stopRecording();
        mRecordingStatus = false;
        this.stopSelf();
    }

    public void stopRecording() {
        try {
            mServiceCamera.reconnect();
        } catch (IOException e) {
            Log.e(NJNP_VIDEO_RECORDER_SERVICE_TAG, "IO Exception on stopping recording, error: " + e.getMessage());
        }
        mMediaRecorder.stop();
        mMediaRecorder.reset();

        mServiceCamera.stopPreview();
        mMediaRecorder.release();

        mServiceCamera.release();
        mServiceCamera = null;
        Log.i(NJNP_VIDEO_RECORDER_SERVICE_TAG, "Recording has stopped.");
    }
}
