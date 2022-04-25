package com.kessi.photovideomaker.activities.photoframe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import com.kessi.photovideomaker.R;
import com.kessi.photovideomaker.util.KSUtil;

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

        arrayList_bg = new ArrayList<>();
        //TODO: add item here

        adapter = new AdapterBackgroundFrame(arrayList_bg, new BackgroundFrameClickListener() {
            @Override
            public void onClick(int pos) {

            }
        });
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
    }

}