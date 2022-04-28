package com.naosteam.slideshowmaker.activities.photoframe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.naosteam.slideshowmaker.R;
import com.naosteam.slideshowmaker.activities.BgTemplateDetailsActivity;
import com.naosteam.slideshowmaker.util.KSUtil;

import java.util.ArrayList;

import vcarry.data.Background_Template;

public class BackgroundFrameActivity extends AppCompatActivity {

    RecyclerView rv;
    AdapterBackgroundFrame adapter;
    ArrayList<Background_Template> arrayList_bg;
    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_background_frame);

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

}