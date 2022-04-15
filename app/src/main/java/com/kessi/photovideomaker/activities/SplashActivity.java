package com.kessi.photovideomaker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.kessi.photovideomaker.R;
import com.kessi.photovideomaker.util.Animatee;
import com.kessi.photovideomaker.util.KSUtil;
import com.kessi.photovideomaker.util.Render;

public class SplashActivity extends AppCompatActivity {

    ImageView icon, bgIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        icon = findViewById(R.id.icon);
        bgIV = findViewById(R.id.bgIV);
        Glide.with(this)
                .load(R.drawable.sp_logo)
                .into(icon);

        Glide.with(this)
                .load(R.drawable.splash_bg)
                .into(bgIV);

        Render render = new Render(SplashActivity.this);
        render.setAnimation(KSUtil.Bubble(icon));
        render.start();


        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            Animatee.animateSplit(SplashActivity.this);
            finish();
        }, 1500);
    }
}
