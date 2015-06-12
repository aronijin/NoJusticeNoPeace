package com.studios.entropy.nojusticenopeace;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.studios.entropy.nojusticenopeace.helpers.NJNPConstants;

/**
 * Created by Nathan Heard on 4/5/2015.
 */
public class NJNPBroadcastReceiver extends BroadcastReceiver {

    private static final String NJNP_BROADCAST_RECEIVER_TAG = "NJNPBroadcastReceiver";
    private static Context applicationContext;
    private static boolean audioActionStatus;
    private static boolean videoActionStatus;
    private static boolean frontCameraActionStatus;
    private static boolean smsActionStatus;
    private static boolean emailActionStatus;
    private static boolean dropboxActionStatus;
    private static boolean localActionStatus;

    @Override
    public void onReceive(Context context, Intent intent) {

        this.applicationContext = context;
        deteremineBroadcastAction(intent);
        Log.i(NJNP_BROADCAST_RECEIVER_TAG, "Received: " + intent.getAction());
        isLastAction();
    }

    public void rebitrhNotification() {
        NotificationCompat.Builder mBuilder = NJNPNotificationBuilder.buildNotification(applicationContext);
        NotificationManager mNotifyMgr = (NotificationManager) applicationContext.getSystemService(applicationContext.NOTIFICATION_SERVICE);

        // Actually build the notification
        mNotifyMgr.notify(NJNPConstants.mNotificationId, mBuilder.build());

        // Reset action statuses
        setAudioActionStatus(false);
        setVideoActionStatus(false);
        setEmailActionStatus(false);
        setSmsActionStatus(false);
        setDropboxActionStatus(false);
        setFrontCameraActionStatus(false);
        setLocalActionStatus(false);
    }

    private void isLastAction() {
        if (isAudioActionStatus() && isVideoActionStatus() && isEmailActionStatus() && isSmsActionStatus() && isDropboxActionStatus() && isFrontCameraActionStatus() && isLocalActionStatus()) {
            rebitrhNotification();
            Log.i(NJNP_BROADCAST_RECEIVER_TAG, "Notification reborn!");
        }
    }

    private void deteremineBroadcastAction(Intent intent) {
        if (intent.getAction().equals(NJNPConstants.ACTION_AUDIO)) {
            setAudioActionStatus(true);
        }
        if (intent.getAction().equals(NJNPConstants.ACTION_VIDEO)) {
            setVideoActionStatus(true);
        }
        if (intent.getAction().equals(NJNPConstants.ACTION_EMAIL)) {
            setEmailActionStatus(true);
        }
        if (intent.getAction().equals(NJNPConstants.ACTION_SMS)) {
            setSmsActionStatus(true);
        }
        if (intent.getAction().equals(NJNPConstants.ACTION_DROPBOX)) {
            setDropboxActionStatus(true);
        }
        if (intent.getAction().equals(NJNPConstants.ACTION_FRONTCAMERA)) {
            setFrontCameraActionStatus(true);
        }
        if (intent.getAction().equals(NJNPConstants.ACTION_LOCAL)) {
            setLocalActionStatus(true);
        }
    }

    public static boolean isAudioActionStatus() {
        return audioActionStatus;
    }

    public static void setAudioActionStatus(boolean audioActionStatus) {
        NJNPBroadcastReceiver.audioActionStatus = audioActionStatus;
    }

    public static boolean isVideoActionStatus() {
        return videoActionStatus;
    }

    public static void setVideoActionStatus(boolean videoActionStatus) {
        NJNPBroadcastReceiver.videoActionStatus = videoActionStatus;
    }

    public static boolean isFrontCameraActionStatus() {
        return frontCameraActionStatus;
    }

    public static void setFrontCameraActionStatus(boolean frontCameraActionStatus) {
        NJNPBroadcastReceiver.frontCameraActionStatus = frontCameraActionStatus;
    }

    public static boolean isSmsActionStatus() {
        return smsActionStatus;
    }

    public static void setSmsActionStatus(boolean smsActionStatus) {
        NJNPBroadcastReceiver.smsActionStatus = smsActionStatus;
    }

    public static boolean isEmailActionStatus() {
        return emailActionStatus;
    }

    public static void setEmailActionStatus(boolean emailActionStatus) {
        NJNPBroadcastReceiver.emailActionStatus = emailActionStatus;
    }

    public static boolean isDropboxActionStatus() {
        return dropboxActionStatus;
    }

    public static void setDropboxActionStatus(boolean dropboxActionStatus) {
        NJNPBroadcastReceiver.dropboxActionStatus = dropboxActionStatus;
    }

    public static boolean isLocalActionStatus() {
        return localActionStatus;
    }

    public static void setLocalActionStatus(boolean localActionStatus) {
        NJNPBroadcastReceiver.localActionStatus = localActionStatus;
    }

}
