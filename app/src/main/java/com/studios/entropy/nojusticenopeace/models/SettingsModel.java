package com.studios.entropy.nojusticenopeace.models;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.ToggleButton;

import com.studios.entropy.nojusticenopeace.NJNPStartActivity;
import com.studios.entropy.nojusticenopeace.R;

import java.io.Serializable;

/**
 * @author Nathan Heard on 4/5/2015.
 */
public class SettingsModel implements Serializable {

    private boolean audioState;
    private boolean videoState;
    private boolean emailState;
    private boolean smsState;
    private boolean dropboxState;
    private int audioDuration;
    private int videoDuration;

    public SettingsModel() {
        this.setAudioState(false);
        this.setVideoState(false);
        this.setEmailState(false);
        this.setSmsState(false);
        this.setDropboxState(false);
    }


    public void saveState(NJNPStartActivity activity) {
        Switch audioToggleBtn = (Switch) activity.findViewById(R.id.audio_toggle_btn);
        Switch videoToggleBtn = (Switch) activity.findViewById(R.id.video_toggle_btn);
        Switch smsToggleBtn = (Switch) activity.findViewById(R.id.sms_toggle_btn);
        Switch emailToggleBtn = (Switch) activity.findViewById(R.id.email_toggle_btn);
        Switch dropboxToggleBtn = (Switch) activity.findViewById(R.id.dropbox_toggle_btn);
        ToggleButton startToggleBtn = (ToggleButton) activity.findViewById(R.id.start_toggle_btn);
        EditText audioEditText = (EditText) activity.findViewById(R.id.audio_edit_text);
        EditText videoEditText = (EditText) activity.findViewById(R.id.video_edit_text);
        if (audioEditText.getText().toString().length() <= 0) {
            audioEditText.setText(String.valueOf(NJNPConstants.DEFAULT_DURATION));
        }
        if (videoEditText.getText().toString().length() <= 0) {
            videoEditText.setText(String.valueOf(NJNPConstants.DEFAULT_DURATION));
        }

        this.setAudioState(audioToggleBtn.isChecked());
        this.setVideoState(videoToggleBtn.isChecked());
        this.setEmailState(emailToggleBtn.isChecked());
        this.setSmsState(smsToggleBtn.isChecked());
        this.setDropboxState(dropboxToggleBtn.isChecked());
        this.setAudioDuration(Integer.valueOf(audioEditText.getText().toString()));
        this.setVideoDuration(Integer.valueOf(videoEditText.getText().toString()));
    }

    public boolean isAudioState() {
        return audioState;
    }

    public void setAudioState(boolean audioState) {
        this.audioState = audioState;
    }

    public boolean isVideoState() {
        return videoState;
    }

    public void setVideoState(boolean videoState) {
        this.videoState = videoState;
    }

    public boolean isEmailState() {
        return emailState;
    }

    public void setEmailState(boolean emailState) {
        this.emailState = emailState;
    }

    public boolean isSmsState() {
        return smsState;
    }

    public void setSmsState(boolean smsState) {
        this.smsState = smsState;
    }

    public boolean isDropboxState() {
        return dropboxState;
    }

    public void setDropboxState(boolean dropboxState) {
        this.dropboxState = dropboxState;
    }

    public int getAudioDuration() {
        return audioDuration;
    }

    public void setAudioDuration(int audioDuration) {
        this.audioDuration = audioDuration;
    }

    public int getVideoDuration() {
        return videoDuration;
    }

    public void setVideoDuration(int videoDuration) {
        this.videoDuration = videoDuration;
    }
}
