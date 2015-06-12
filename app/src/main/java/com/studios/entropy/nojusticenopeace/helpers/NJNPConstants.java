package com.studios.entropy.nojusticenopeace.helpers;

import android.content.BroadcastReceiver;
import android.os.Environment;

import com.studios.entropy.nojusticenopeace.NJNPBroadcastReceiver;

/**
 * Created by Nathan Heard on 3/23/2015.
 *
 * Used to store all global constant variables used through the No Justice No Peace Mobile Application
 *
 */
public class NJNPConstants {

    public static final BroadcastReceiver NJNPBroadcastReceiver = new NJNPBroadcastReceiver();
    public static final String DIRECTORY_PATH = "/sdcard/NoJusticeNoPeace/";
    public static final String AUDIO_FOLDER = "Audio/";
    public static final String AUDIO_FILE_NAME = "audio_file_";
    public static final String VIDEO_FOLDER = "Video/";
    public static final String VIDEO_FILE_NAME = "video_file_";
    public static final String SETTINGS_FOLDER = "Settings/";
    public static final String SETTINGS_FILE_NAME = "settings.njnp";
    public static final String DATE_FORMAT = "yyyy_MM_dd_hh_mm_ss";
    public static final String VIDEO_DURATION_EXTRA = "VideoDurationExtra";
    public static final int mNotificationId = 001;
    public static final int DEFAULT_DURATION = 1;


    public static final String ACTION_AUDIO = "android.intent.action.ASYNC_AUDIO";
    public static final String ACTION_VIDEO = "android.intent.action.ASYNC_VIDEO";
    public static final String ACTION_SMS = "android.intent.action.ASYNC_SMS";
    public static final String ACTION_EMAIL = "android.intent.action.ASYNC_EMAIL";
    public static final String ACTION_DROPBOX = "android.intent.action.ASYNC_DROPBOX";
    public static final String ACTION_FRONTCAMERA = "android.intent.action.ASYNC_FRONTCAMERA";
    public static final String ACTION_LOCAL = "android.intent.action.ASYNC_LOCAL";

}
