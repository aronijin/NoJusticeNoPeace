package com.studios.entropy.nojusticenopeace;

import android.media.MediaRecorder;
import android.os.CountDownTimer;
import android.util.Log;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import com.studios.entropy.nojusticenopeace.NJNPConstants;

/**
 * Created by Nathan Heard on 3/23/2015.
 */
public class AudioHelper {

    private static MediaRecorder recorder;

    public static void runAudio() throws IOException{
        recorder = new MediaRecorder();

        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        SimpleDateFormat sdf = new SimpleDateFormat(NJNPConstants.DATE_FORMAT, Locale.US);

        recorder.setOutputFile(NJNPConstants.DIRECTORY_PATH + NJNPConstants.AUDIO_FILE_NAME + sdf.format(new Date()) + ".3gp");
        recorder.prepare();

        recorder.start();   // Recording is now started
    }

    public static void stopAudio() {
        recorder.stop();
        recorder.release();
    }

}
