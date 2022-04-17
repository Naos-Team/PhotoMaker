package com.kessi.photovideomaker.activities;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.kessi.photovideomaker.KessiApplication;
import com.kessi.photovideomaker.R;
import com.kessi.photovideomaker.activities.kessiimagepicker.activity.ImagePickerActivity;
import com.kessi.photovideomaker.activities.myalbum.MyAlbumActivity;
import com.kessi.photovideomaker.activities.myalbum.MyVideoAdapter;
import com.kessi.photovideomaker.activities.swap.SwapperActivity;
import com.kessi.photovideomaker.util.AdManager;
import com.kessi.photovideomaker.util.Animatee;
import com.kessi.photovideomaker.util.KSUtil;
import com.kessi.photovideomaker.util.Render;

import org.apache.commons.io.comparator.LastModifiedFileComparator;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    String[] permissionsList = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

//    ImageView mainBg, icon, btnStart, btnAllVideo, rateIV, shareIV, privacyIV, moreIV;
    LinearLayout btnLay, btnStart, btnMore;
    DrawerLayout drawer;
    NavigationView navigationView;
    ImageView iv_hamburger;
    int FLAG_VIDEO = 21;
    ArrayList<String> videoPath;
    RecyclerView rv;
    MyVideoAdapter videoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Glide.with(this)
//                .load(R.drawable.f_bg)
//                .into(mainBg);
//        icon = findViewById(R.id.icon);
//        Glide.with(this)
//                .load(R.drawable.sp_logo)
//                .into(icon);

            // Set Animation

        videoPath = new ArrayList<>();

//        Render render = new Render(MainActivity.this);
//        render.setAnimation(KSUtil.Bubble(icon));
//        render.start();

        init();

        videoLoader();

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawer.closeDrawer(GravityCompat.START);
        switch (item.getItemId()){
            case R.id.nav_more_app:
                moreApp();
                break;
            case R.id.nav_privacy:
                startActivityes(new Intent(MainActivity.this, PrivacyActivity.class), 0);
                break;
            case R.id.nav_rate:
                rateUs();
                break;
            case R.id.nav_share:
                shareApp();
                break;
        }
        return true;
    }

    void init() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        KessiApplication.VIDEO_HEIGHT = displaymetrics.widthPixels;
        KessiApplication.VIDEO_WIDTH = displaymetrics.widthPixels;

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        iv_hamburger = findViewById(R.id.iv_hamburger);
        iv_hamburger.setOnClickListener(view -> drawer.openDrawer(GravityCompat.START));

        btnStart = findViewById(R.id.btn_start);
        btnStart.setOnClickListener((view1 -> {
            KSUtil.Bounce(this, btnStart);
                new Handler().postDelayed(() -> {
                    if (!checkPermissions(MainActivity.this, permissionsList)) {
                        ActivityCompat.requestPermissions(MainActivity.this, permissionsList, 21);
                    } else {
                        KSUtil.fromAlbum = false;
                        Intent mIntent = new Intent(MainActivity.this, ImagePickerActivity.class);
                        mIntent.putExtra(ImagePickerActivity.KEY_LIMIT_MAX_IMAGE, 30);
                        mIntent.putExtra(ImagePickerActivity.KEY_LIMIT_MIN_IMAGE, 4);
                        startActivity(mIntent);
                        Animatee.animateSlideUp(MainActivity.this);
                    }
                },100);
        }));

        btnMore = findViewById(R.id.btn_more);
        btnMore.setOnClickListener((v) -> {
            if (!checkPermissions(this, permissionsList)) {
                ActivityCompat.requestPermissions(this, permissionsList, 22);
            } else {
                KSUtil.fromAlbum = true;
                AdManager.adCounter = AdManager.adDisplayCounter;
                if (!AdManager.isloadFbMAXAd) {
                    AdManager.showInterAd(MainActivity.this,new Intent(MainActivity.this, MyAlbumActivity.class), 0);
                } else {
                    AdManager.showMaxInterstitial(MainActivity.this,new Intent(MainActivity.this, MyAlbumActivity.class), 0);
                }
            }
        });

//        btnStart = findViewById(R.id.btnStart);
//        btnStart.setOnClickListener(this);
//
//        btnAllVideo = findViewById(R.id.btnAllVideo);
//        btnAllVideo.setOnClickListener(this);
//
//        rateIV = findViewById(R.id.rateIV);
//        rateIV.setOnClickListener(this);
//
//        shareIV = findViewById(R.id.shareIV);
//        shareIV.setOnClickListener(this);
//
//        privacyIV = findViewById(R.id.privacyIV);
//        privacyIV.setOnClickListener(this);
//
//        moreIV = findViewById(R.id.moreIV);
//        moreIV.setOnClickListener(this);

//        FrameLayout nativeContainer = findViewById(R.id.nativeContainer);
//        FrameLayout nativeContainerMAX = findViewById(R.id.nativeContainerMAX);
//        if (!AdManager.isloadFbMAXAd) {
//            //admob
//            AdManager.initAd(MainActivity.this);
//            AdManager.loadInterAd(MainActivity.this);
//            AdManager.loadNativeAd(MainActivity.this, nativeContainer);
//        } else {
//            //MAX + Fb banner Ads
//            AdManager.initMAX(MainActivity.this);
//            AdManager.maxInterstital(MainActivity.this);
//            AdManager.loadNativeMAX(MainActivity.this, nativeContainerMAX);
//        }
    }

    public void videoLoader() {
        getFromStorage();
        rv = (RecyclerView) findViewById(R.id.rv);
        videoAdapter = new MyVideoAdapter(videoPath, this, (v, position) -> {

            Intent intent = new Intent(this, VideoPlayerActivity.class);
            intent.putExtra("video_path", videoPath.get(position));
            startActivityes(intent, FLAG_VIDEO);
        });

        rv.setLayoutManager(new GridLayoutManager(this, 2));
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(videoAdapter);

    }

    public void getFromStorage() {
        String folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                + "/" + getResources().getString(R.string.app_name);
        File file = new File(folder);
        videoPath = new ArrayList<String>();
        if (file.isDirectory()) {
            if (file.listFiles()!=null){
                File[] listFile = file.listFiles();
                Arrays.sort(listFile, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
                for (int i = 0; i < listFile.length; i++) {

                    if (listFile[i].getAbsolutePath().contains(".mp4")) {
                        videoPath.add(listFile[i].getAbsolutePath());
                    }

                }
            }
        }
    }


    void startActivityes(Intent intent, int reqCode) {
        if (!AdManager.isloadFbMAXAd) {
            AdManager.adCounter++;
            AdManager.showInterAd(MainActivity.this, intent, reqCode);
        } else {
            AdManager.adCounter++;
            AdManager.showMaxInterstitial(MainActivity.this, intent, reqCode);
        }
    }

    public void moreApp() {
        startActivity(new Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/dev?id=7081479513420377164&hl=en")));
    }

    public void shareApp() {
        String shareBody = "https://play.google.com/store/apps/details?id="
                + getApplicationContext().getPackageName();

        Intent sharingIntent = new Intent(
                Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent
                .putExtra(Intent.EXTRA_SUBJECT,
                        "(This app is for making beautiful video from photos. Open it in Google Play Store to Download the Application)");

        sharingIntent.putExtra(Intent.EXTRA_TEXT,
                shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    public void rateUs() {
        Uri uri = Uri.parse("market://details?id="
                + getApplicationContext().getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY
                | Intent.FLAG_ACTIVITY_NEW_DOCUMENT
                | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id="
                            + getApplicationContext().getPackageName())));
        }
    }


    public static boolean checkPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 21){
            if (checkPermissions(this, permissionsList)){
                KSUtil.fromAlbum = false;
                Intent mIntent = new Intent(MainActivity.this, ImagePickerActivity.class);
                mIntent.putExtra(ImagePickerActivity.KEY_LIMIT_MAX_IMAGE, 30);
                mIntent.putExtra(ImagePickerActivity.KEY_LIMIT_MIN_IMAGE, 4);
                Animatee.animateSlideUp(MainActivity.this);
                startActivity(mIntent);
            }
        }
        if(requestCode == 22){
            KSUtil.fromAlbum = true;
            startActivityes(new Intent(MainActivity.this, MyAlbumActivity.class), 0);
            Animatee.animateSlideUp(MainActivity.this);
        }
    }

}
