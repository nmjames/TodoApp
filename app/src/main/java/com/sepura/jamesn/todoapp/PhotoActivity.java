package com.sepura.jamesn.todoapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PhotoActivity extends AppCompatActivity {


    @BindView(R.id.imageView) ImageView imageView;

    static final int CAPTURE_IMAGE = 1;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_photo);
        ButterKnife.bind(this);

        tts = new TextToSpeech(this, onInitListener);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if( (requestCode == CAPTURE_IMAGE) && (resultCode == RESULT_OK)){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @OnClick(R.id.takePhotoButton)
    void onPhotoClicked() {
        dispatchImageCaptureIntent();
    }

    private void dispatchImageCaptureIntent(){
        Intent imageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if( imageCaptureIntent.resolveActivity(getPackageManager())!= null ){
            startActivityForResult(imageCaptureIntent, CAPTURE_IMAGE);

        }
        speak("take photo");
    }



    private void speak(String text){
        if(isLoaded){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                tts.speak("max headroom", TextToSpeech.QUEUE_FLUSH,null,null);
            } else {
                tts.speak("take photo", TextToSpeech.QUEUE_FLUSH,null);
            }
        } else {
            Log.e("error", "tts not initialised");
        }
    }
}
