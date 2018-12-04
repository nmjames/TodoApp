package com.sepura.jamesn.todoapp;

import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SpeechActivity extends AppCompatActivity {

    private TextToSpeech tts;
    private boolean isLoaded = false;
    private TextToSpeech.OnInitListener onInitListener = new TextToSpeech.OnInitListener() {
        @Override
        public void onInit(int status) {
            if (status == TextToSpeech.SUCCESS) {
                tts.setLanguage((Locale.getDefault()));
                isLoaded = true;
            }
        }
    };

    @BindView(R.id.speechText) TextView speechView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech);
        ButterKnife.bind(this);
        tts = new TextToSpeech(this, onInitListener);
    }

    @OnClick(R.id.speechButton)
    void onSpeechlicked() {
        String string = speechView.getText().toString();
        speak(string);
    }

    private void speak(String string){
        if(isLoaded){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                tts.speak(string,TextToSpeech.QUEUE_FLUSH,null,null);
            } else {
                tts.speak("take photo", TextToSpeech.QUEUE_FLUSH,null);
            }
        } else {
            Log.e("error", "tts not initialised");
        }
    }
}
