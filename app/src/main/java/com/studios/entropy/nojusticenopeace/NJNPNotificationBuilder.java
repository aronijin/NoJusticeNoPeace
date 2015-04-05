package com.studios.entropy.nojusticenopeace;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Nathan Heard on 4/4/2015.
 */
public class NJNPNotificationBuilder {

    public static NotificationCompat.Builder buildNotification(Context context) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.abc_btn_default_mtrl_shape)
                .setContentTitle("No Justice No Peace")
                .setContentText("Click me to start!")
                .setAutoCancel(true)
                .setOngoing(true);

        Intent resultIntent = new Intent(context, NJNPBackgroundService.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent resultPendingIntent = PendingIntent.getService(context, 0, resultIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        return mBuilder;
    }
}
