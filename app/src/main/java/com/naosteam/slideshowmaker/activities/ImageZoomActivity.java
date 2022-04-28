package com.naosteam.slideshowmaker.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.naosteam.slideshowmaker.R;
import com.squareup.picasso.Picasso;
import com.xinlan.imageeditlibrary.editimage.view.imagezoom.ImageViewTouch;

public class ImageZoomActivity extends AppCompatActivity {

    ImageViewTouch imv_zoom;
    ImageView imv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_zoom);
        imv_back = findViewById(R.id.zoom_back);
        imv_zoom = findViewById(R.id.imv_zoom);

        imv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        imv_zoom.setScaleType(ImageView.ScaleType.FIT_CENTER);
        Intent intent = getIntent();
        Picasso.get()
                .load(Uri.parse(intent.getStringExtra("img")))
                .into(imv_zoom);

    }
}