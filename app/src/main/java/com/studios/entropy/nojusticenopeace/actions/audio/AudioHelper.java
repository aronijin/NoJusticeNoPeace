package com.studios.entropy.nojusticenopeace.actions.audio;

import android.media.MediaRecorder;

import com.studios.entropy.nojusticenopeace.models.NJNPConstants;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Nathan Heard on 3/23/2015.
 */
public class AudioHelper {

    private static MediaRecorder recorder;

    /**
     * method used to create and run MediaRecorder for audio recording
     *
     * @throws IOException for audio file path storage location
     */
    public static void runAudio() throws IOException{
        recorder = new MediaRecorder();

        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        SimpleDateFormat sdf = new SimpleDateFormat(NJNPConstants.DATE_FORMAT, Locale.US);

        recorder.setOutputFile(NJNPConstants.DIRECTORY_PATH + NJNPConstants.AUDIO_FOLDER + NJNPConstants.AUDIO_FILE_NAME + sdf.format(new Date()) + ".3gp");
        recorder.prepare();

        recorder.start();   // Audio recording now started
    }

    /**
     * method used to stop and free MediaRecorder from audio recording
     *
     */
    public static void stopAudio() {
        if (recorder != null) {
            recorder.stop();
            recorder.reset();
            recorder.release();
            recorder = null;
        }
    }
}
