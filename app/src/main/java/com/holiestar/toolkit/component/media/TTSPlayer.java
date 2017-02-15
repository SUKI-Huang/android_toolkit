package com.holiestar.toolkit.component.media;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;

import java.util.Locale;

/**
 * Created by SsuChi on 8/24/2015.
 */
public class TTSPlayer implements TextToSpeech.OnInitListener {
    private static TTSPlayer ttsPlayer;
    private final String TAG=getClass().getSimpleName();
    private Context context;
    private TextToSpeech tts;
    private Boolean available;
    
    public static void initialize(Context context){
        ttsPlayer=new TTSPlayer(context.getApplicationContext());
    }
    
    private static TTSPlayer getInstance(){
        return ttsPlayer;
    }

    private TTSPlayer(Context context) {
        this.context = context;
    }

    public boolean play(String str) {
        if (!available) {
            Log.e("Google TTS", "not available on this language");
            return false;
        }
        if(tts!=null){
            tts.speak(str, TextToSpeech.QUEUE_FLUSH, null);
            return true;
        }
        return false;
    }


    public void init() {
        if(tts!=null){
            return;
        }
        this.tts = new TextToSpeech(context, this);
        this.tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String s) {
                Log.i(TAG,"TTS\tStart");
            }

            @Override
            public void onDone(String s) {
                Log.i(TAG,"TTS\tDone");
                shutdown();
            }

            @Override
            public void onError(String s) {
                Log.e(TAG,"TTS\tonError:"+s);
            }
        });
    }

    private void shutdown(){
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            available=tts.isLanguageAvailable(Locale.US)==TextToSpeech.LANG_COUNTRY_AVAILABLE;
            tts.setLanguage(Locale.US);
            tts.setSpeechRate(0.7f);
        } else {
            available = false;
        }
    }
}
