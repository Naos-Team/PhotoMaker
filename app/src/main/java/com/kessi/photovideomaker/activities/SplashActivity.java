package com.kessi.photovideomaker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.kessi.photovideomaker.R;
import com.kessi.photovideomaker.activities.intro.IntroActivity;
import com.kessi.photovideomaker.util.Animatee;
import com.kessi.photovideomaker.util.KSUtil;
import com.kessi.photovideomaker.util.Render;

import org.apache.commons.io.comparator.LastModifiedFileComparator;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class SplashActivity extends AppCompatActivity {

    ImageView icon, bgIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            openMainActivity();
//            Animatee.animateSplit(SplashActivity.this);
            finish();
        }, 1500);
    }

    public void openMainActivity() {
        String folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                + "/" + getResources().getString(R.string.app_name);
        File file = new File(folder);

        int count = 0;

        if (file.isDirectory()) {
            File[] listFile = file.listFiles();
            Arrays.sort(listFile, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
            for (int i = 0; i < listFile.length; i++) {

                if (listFile[i].getAbsolutePath().contains(".mp4")) {
                    count++;
                }

            }

            if(count > 0){
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }else{
                startActivity(new Intent(SplashActivity.this, IntroActivity.class));
            }


        }else{
            startActivity(new Intent(SplashActivity.this, IntroActivity.class));
        }
//        startActivity(new Intent(SplashActivity.this, MainActivity.class));
    }
}
