package com.holiestar.toolkit.component.media;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;

/**
 * Created by user on 5/1/2015.
 */
public class UrlMediaPlayer {
    private String TAG=getClass().getSimpleName();
    private Context context;

    //Obj
    private MediaPlayer mPlayer;
    private Handler handlerTimer;

    //parameters
    private boolean isPrepared = false;
    private boolean isError=false;
    private String url;
    private boolean autoPlay=false;

    private OnMediaEvent onMediaEvent;
    private int duration=0;

    public void setOnMediaEvent(OnMediaEvent onMediaEvent) {
        this.onMediaEvent = onMediaEvent;
    }

    public interface OnMediaEvent {
        public abstract void OnProgressChanged(float progress, int h, int m, int s, int totalS);

        public abstract void onStartLoading();

        public abstract void OnStop();

        public abstract void OnPause();

        public abstract void OnPlay();

        public abstract void OnInfo(int h, int m, int s, int totalS);

        public abstract void onError(MediaPlayer mp, int what, int extra);

        public abstract void onEnded();
    }

    public UrlMediaPlayer(Context context) {
        this.context = context;
        this.handlerTimer = new Handler();
    }


    public void load(String url) {
        Log.i(TAG, "load");
        isPrepared = false;
        duration=0;
        this.url = url;
        releasePlayer();
        mPlayer = new MediaPlayer();
        mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                isPrepared = true;
                if (onMediaEvent != null) {
                    duration=mp.getDuration();
//                    int mSec = mp.getDuration();
                    int mSec= duration;
                    int totalS = mSec / 1000;
                    int h = totalS / 60 / 60;
                    int m = (totalS - h * 3600) / 60;
                    int s = (totalS - h * 3600 - m * 60);
                    onMediaEvent.OnInfo(h, m, s, totalS);
                }
                if(autoPlay){
                    play();
                }


            }
        });
        mPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.i(TAG, "onError");
                isError=true;
                if (onMediaEvent == null) {
                    return false;
                }
                onMediaEvent.onError(mp, what, extra);
                return false;
            }
        });
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.i(TAG, "onCompletion");
                timerStop();
                if (onMediaEvent == null) {
                    return;
                }
                onMediaEvent.onEnded();
            }
        });

        try {
            if(onMediaEvent != null) {
                onMediaEvent.onStartLoading();
            }
            mPlayer.setDataSource(url);
            mPlayer.prepareAsync();
            isError=false;
        } catch (Exception e) {
            isError=true;
            Log.i(TAG, "setDataSource failed:" + url);
        }
    }

    public void playPause(){
        if(isError){
            load(url);
            return;
        }
        if(isPlaying()){
            pause();
        }else{
            play();
        }
    }

    public boolean isPlaying(){
        if(mPlayer!=null){
            return mPlayer.isPlaying();
        }else{
            return false;
        }
    }

    public void play() {
        if (mPlayer != null && isPrepared) {
            mPlayer.start();
            timerStart();
            if (onMediaEvent != null) {
                onMediaEvent.OnPlay();
            }
        }
    }

    public void seekTo(Float percentage){
        if (mPlayer != null && isPrepared && duration!=0) {
            mPlayer.seekTo((int) ((mPlayer.getDuration() * percentage) / 100));
        }
    }

    public void seekTo(Integer mSec){
        if (mPlayer != null && isPrepared && duration!=0) {
            mPlayer.seekTo(mSec);
        }
    }

    public void pause() {
        if (mPlayer != null && isPrepared && mPlayer.isPlaying()) {
            mPlayer.pause();
            timerStop();
            if (onMediaEvent != null) {
                onMediaEvent.OnPause();
            }
        }
    }

    public void stop() {
        if (mPlayer != null && isPrepared) {
            mPlayer.stop();
            timerStop();
            if (onMediaEvent != null) {
                onMediaEvent.OnStop();
            }
        }
    }

    public String getTimeText(float Percentage){
        if (mPlayer != null && isPrepared && duration!=0) {
            int mSec = (int) ((duration*Percentage)/100);
            int totalS = (Percentage==100?0:1)+mSec / 1000;
            int h = totalS / 60 / 60;
            int m = (totalS - h * 3600) / 60;
            int s = (totalS - h * 3600 - m * 60);
            return getMediaPlayTime(h,m,s);
        }
        return getMediaPlayTime(0,0,0);
    }

    public String getTimeRemainText(float Percentage){
        if (mPlayer != null && isPrepared && duration!=0) {
//            int mSec = mPlayer.getDuration()-(int) ((mPlayer.getDuration()*Percentage)/100);
            int mSec = duration-(int) ((mPlayer.getDuration()*Percentage)/100);
            int totalS = (Percentage==100?0:1)+mSec / 1000;
//            int totalS = mSec / 1000;
            int h = totalS / 60 / 60;
            int m = (totalS - h * 3600) / 60;
            int s = (totalS - h * 3600 - m * 60);
            return getMediaPlayTime(h,m,s);
        }
        return getMediaPlayTime(0,0,0);
    }

    public String getTotalTimeText(){
        if (mPlayer != null && isPrepared && duration!=0) {
//            int mSec = mPlayer.getDuration();
            int mSec = duration;
            int totalS = mSec / 1000;
            int h = totalS / 60 / 60;
            int m = (totalS - h * 3600) / 60;
            int s = (totalS - h * 3600 - m * 60);
            return getMediaPlayTime(h,m,s);
        }
        return getMediaPlayTime(0,0,0);
    }


    public void releasePlayer() {
        timerStop();
        if (mPlayer != null) {
            if (mPlayer.isPlaying()) {
                mPlayer.stop();
            }
            mPlayer.release();
            mPlayer = null;
        }

    }

    private void timerStart() {
        Log.i(TAG, "timerStart");
        handlerTimer.removeCallbacksAndMessages(null);
        handlerTimer.postDelayed(timerTick, 1000);
    }

    private void timerStop() {
        Log.i(TAG, "timerStop");
        handlerTimer.removeCallbacks(timerTick);
    }

    private Runnable timerTick = new Runnable() {
        @Override
        public void run() {
            if(mPlayer!=null && isPrepared && onMediaEvent != null ){
                int mSec = mPlayer.getCurrentPosition();
                int totalS = mSec / 1000;
                int h = totalS / 60 / 60;
                int m = (totalS - h * 3600) / 60;
                int s = (totalS - h * 3600 - m * 60);
                float progress= (((float)mPlayer.getCurrentPosition()/(float)duration)*100);
//                Log.i("timerTick","h:"+h+"   m:"+m+"   s:"+s);
                onMediaEvent.OnProgressChanged(progress,h, m, s, totalS);
            }
            handlerTimer.postDelayed(this, 1000);
        }
    };


    public boolean isAutoPlay() {
        return autoPlay;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setAutoPlay(boolean autoPlay) {
        this.autoPlay = autoPlay;
    }

    public String getUrl() {
        return url;
    }

    private String getMediaPlayTime(int h, int m, int s) {
        if (h != 0) {
            return String.format("%02d:%02d:%02d", h, m, s);
        } else {
            return String.format("%02d:%02d", m, s);
        }
    }
}
