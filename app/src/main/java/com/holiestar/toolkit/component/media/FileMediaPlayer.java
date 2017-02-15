package com.holiestar.toolkit.component.media;

import android.media.MediaPlayer;

/**
 * Created by SsuChi on 8/24/2015.
 */
public class FileMediaPlayer {
    private String TAG = getClass().getSimpleName();
    private MediaPlayer mediaPlayer;

    private OnSimpleEvent onSimpleEvent;

    private interface OnEvent {
        void OnPlayComplete();
        void OnPrepared();
    }

    public static class OnSimpleEvent implements OnEvent {
        @Override
        public void OnPlayComplete() {}

        @Override
        public void OnPrepared() {}
    }

    public void setOnSimpleEvent(OnSimpleEvent onSimpleEvent) {
        this.onSimpleEvent = onSimpleEvent;
    }

    public void stop() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
        }
    }

    public void play(String filePath) {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (onSimpleEvent != null) {
                    onSimpleEvent.OnPlayComplete();
                }
            }
        });
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                if (onSimpleEvent != null) {
                    onSimpleEvent.OnPrepared();
                }
                mediaPlayer.start();
            }
        });
        try {
            mediaPlayer.setDataSource(filePath);
            mediaPlayer.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public float getProgress() {
        if (mediaPlayer != null) {
            float progress = (((float) mediaPlayer.getCurrentPosition() / (float) mediaPlayer.getDuration()));
            return progress;
        } else {
            return 0;
        }
    }
}
