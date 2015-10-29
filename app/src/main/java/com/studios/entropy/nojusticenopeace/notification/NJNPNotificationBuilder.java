package com.studios.entropy.nojusticenopeace.notification;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.studios.entropy.nojusticenopeace.R;
import com.studios.entropy.nojusticenopeace.services.NJNPBackgroundService;

/**
 *
 * Class used to create notification for user interaction
 *
 * Created by Nathan Heard on 4/4/2015.
 */
public class NJNPNotificationBuilder {

    private static final String NJNP_NOTIFICATION_BUILDER_TAG = "NJNPBroadcastReceiver";
    private static boolean audioStatus;
    private static boolean videoStatus;
    private static boolean frontCameraStatus;
    private static boolean smsStatus;
    private static boolean emailStatus;
    private static boolean dropboxStatus;
    private static boolean localStatus;

    private static int audioDurationMin;
    private static int videoDurationMin;

    /**
     * Method used to create the notification with desired user settings
     *
     * @param context
     * @return the newly built notification
     */
    public static NotificationCompat.Builder buildNotification(Context context) {

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.abc_btn_default_mtrl_shape)
                .setContentTitle("No Justice No Peace")
                .setContentText("Click me to start!")
                .setAutoCancel(true)
                .setOngoing(true);

        Intent backgroundIntent = new Intent(context, NJNPBackgroundService.class);
        backgroundIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        backgroundIntent.putExtra("audioStatus", audioStatus);
        backgroundIntent.putExtra("videoStatus", videoStatus);
        backgroundIntent.putExtra("frontCameraStatus", frontCameraStatus);
        backgroundIntent.putExtra("smsStatus", smsStatus);
        backgroundIntent.putExtra("emailStatus", emailStatus);
        backgroundIntent.putExtra("dropboxStatus", dropboxStatus);
        backgroundIntent.putExtra("localStatus", localStatus);

        if (audioDurationMin == 0) {
            audioDurationMin = 1;
        }
        if (videoDurationMin == 0) {
            videoDurationMin = 1;
        }
        backgroundIntent.putExtra("audioDurationMin", audioDurationMin);
        backgroundIntent.putExtra("videoDurationMin", videoDurationMin);


        PendingIntent resultPendingIntent = PendingIntent.getService(context, 0, backgroundIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

        Log.i(NJNP_NOTIFICATION_BUILDER_TAG, "Notification built!");

        return mBuilder;
    }

    public static void setAudioStatus(boolean audioStatus) {
        NJNPNotificationBuilder.audioStatus = audioStatus;
    }
    public static void setVideoStatus(boolean videoStatus) {
        NJNPNotificationBuilder.videoStatus = videoStatus;
    }

    public static void setFrontCameraStatus(boolean frontCameraStatus) {
        NJNPNotificationBuilder.frontCameraStatus = frontCameraStatus;
    }

    public static void setSmsStatus(boolean smsStatus) {
        NJNPNotificationBuilder.smsStatus = smsStatus;
    }

    public static void setEmailStatus(boolean emailStatus) {
        NJNPNotificationBuilder.emailStatus = emailStatus;
    }

    public static void setDropboxStatus(boolean dropboxStatus) {
        NJNPNotificationBuilder.dropboxStatus = dropboxStatus;
    }

    public static void setLocalStatus(boolean localStatus) {
        NJNPNotificationBuilder.localStatus = localStatus;
    }

    public static void setAudioDurationMin(int audioDurationMin) {
        NJNPNotificationBuilder.audioDurationMin = audioDurationMin;
    }

    public static void setVideoDurationMin(int videoDurationMin) {
        NJNPNotificationBuilder.videoDurationMin = videoDurationMin;
    }
}
