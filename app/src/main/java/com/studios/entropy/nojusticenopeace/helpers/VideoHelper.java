package com.studios.entropy.nojusticenopeace.helpers;

import android.media.MediaRecorder;
import java.io.IOException;

/**
 * Created by Nathan Heard on 4/5/2015.
 */
public class VideoHelper {

    private static MediaRecorder recorder;

    /**
     * method used to create and run MediaRecorder for video recording
     *
     * @throws IOException for audio file path storage location
     */
    public static void runVideo() throws IOException{
        recorder = new MediaRecorder();

        //TODO create video recorder
    }

    /**
     * method used to stop and free MediaRecorder from video recording
     *
     */
    public static void stopVideo() {

        recorder = null;
        //TODO destroy video recorder
    }
}
