package com.naosteam.slideshowmaker.activities.photoframe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.naosteam.slideshowmaker.R;
import com.naosteam.slideshowmaker.activities.BgTemplateDetailsActivity;
import com.naosteam.slideshowmaker.activities.myalbum.PhotoAlbumActivity;
import com.naosteam.slideshowmaker.util.AdManager;
import com.naosteam.slideshowmaker.util.KSUtil;

import java.util.ArrayList;

import vcarry.data.Background_Template;

public class BackgroundFrameActivity extends AppCompatActivity {

    RecyclerView rv;
    AdapterBackgroundFrame adapter;
    ArrayList<Background_Template> arrayList_bg;
    ImageView btnBack;
    LinearLayout ll_adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_background_frame);

        LoadAds();

        btnBack = (ImageView) findViewById(R.id.back);
        btnBack.setOnClickListener(v -> {
            KSUtil.Bounce(this, btnBack);
            onBackPressed();
        });

        arrayList_bg = new ArrayList<>(Background_Template.getTemplate_data(BackgroundFrameActivity.this));


        adapter = new AdapterBackgroundFrame(arrayList_bg, new BackgroundFrameClickListener() {
            @Override
            public void onClick(int pos) {
                BgTemplateDetailsActivity.setBackground_template(arrayList_bg.get(pos));
                startActivity(new Intent(BackgroundFrameActivity.this, BgTemplateDetailsActivity.class));
            }
        });
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
    }

    private void LoadAds(){
        AdManager.initAd(BackgroundFrameActivity.this);
        ll_adView = findViewById(R.id.ll_adView8);
        AdManager.loadAdmobBanner(this, ll_adView);
    }

}