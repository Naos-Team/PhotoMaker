package com.kessi.photovideomaker.activities;

import static android.provider.Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.kessi.photovideomaker.KessiApplication;
import com.kessi.photovideomaker.R;
import com.kessi.photovideomaker.activities.kessiimagepicker.activity.ImagePickerActivity;
import com.kessi.photovideomaker.activities.myalbum.MyAlbumActivity;
import com.kessi.photovideomaker.activities.myalbum.MyVideoAdapter;
import com.kessi.photovideomaker.activities.photoframe.BackgroundFrameActivity;
import com.kessi.photovideomaker.util.AdManager;
import com.kessi.photovideomaker.util.Animatee;
import com.kessi.photovideomaker.util.KSUtil;

import org.apache.commons.io.comparator.LastModifiedFileComparator;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    String[] permissionsList = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

//    ImageView mainBg, icon, btnStart, btnAllVideo, rateIV, shareIV, privacyIV, moreIV;
    LinearLayout btnLay, btnSlide, btnMoreSlide, btnMorePhoto, btn_frame;
    TextView tv_empty, tv_empty_photo;
    DrawerLayout drawer;
    NavigationView navigationView;
    ImageView iv_hamburger;
    int FLAG_VIDEO = 21;
    int RETURN_CODE = 14;
    ArrayList<String> videoPath, photoPath;
    RecyclerView rv, rv_photo;
    MyVideoAdapter videoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoPath = new ArrayList<>();

        init();

        videoLoader();
    }

    private void checkAndroid11AccessPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                openAccessFileDialog();
            }
        }
    }

    private void openAccessFileDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Message");
        builder.setMessage("Please give permission to this app to access all file!");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent();
                i.setAction(ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivityForResult(i, 11);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
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

        rv = findViewById(R.id.rv);
        rv_photo = findViewById(R.id.rv_photo);
        tv_empty = findViewById(R.id.tv_empty);
        tv_empty_photo = findViewById(R.id.tv_empty_photo);
        iv_hamburger = findViewById(R.id.iv_hamburger);
        iv_hamburger.setOnClickListener(view -> drawer.openDrawer(GravityCompat.START));

        btnSlide = findViewById(R.id.btn_slide);
        btnSlide.setOnClickListener((view1 -> {
            KSUtil.Bounce(this, btnSlide);
                new Handler().postDelayed(() -> {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !Environment.isExternalStorageManager()) {
                        openAccessFileDialog();
                    }else{
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
                    }


                },100);
        }));

        btn_frame = findViewById(R.id.btn_frame);
        btn_frame.setOnClickListener(v ->{
            KSUtil.Bounce(this, btn_frame);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !Environment.isExternalStorageManager()) {
                openAccessFileDialog();
            }else{
                if (!checkPermissions(MainActivity.this, permissionsList)) {
                    ActivityCompat.requestPermissions(MainActivity.this, permissionsList, 21);

                } else {
                    Intent mIntent = new Intent(MainActivity.this, BackgroundFrameActivity.class);
                    startActivity(mIntent);
                    Animatee.animateSlideUp(MainActivity.this);
                }
            }
        });

        btnMoreSlide = findViewById(R.id.btn_more_slide);
        btnMoreSlide.setOnClickListener((v) -> {

            if (!checkPermissions(this, permissionsList)) {
                ActivityCompat.requestPermissions(this, permissionsList, 22);
            } else {
                KSUtil.fromAlbum = true;
                AdManager.adCounter = AdManager.adDisplayCounter;
                if (!AdManager.isloadFbMAXAd) {
                    AdManager.showInterAd(MainActivity.this,new Intent(MainActivity.this, MyAlbumActivity.class), RETURN_CODE);
                } else {
                    AdManager.showMaxInterstitial(MainActivity.this,new Intent(MainActivity.this, MyAlbumActivity.class), RETURN_CODE);
                }
            }
        });

        btnMorePhoto = findViewById(R.id.btn_more_photo);
        btnMorePhoto.setOnClickListener(v->{

        });
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
        videoAdapter = new MyVideoAdapter(false, videoPath, this, (v, position) -> {
            Intent intent = new Intent(this, VideoPlayerActivity.class);
            intent.putExtra("video_path", videoPath.get(position));
            startActivityes(intent, FLAG_VIDEO);
        });

        rv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(videoAdapter);

    }

    public void getFromStorage() {
        String folder_slide = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                + "/" + getResources().getString(R.string.app_name);
        String folder_photo = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                + "/" + getResources().getString(R.string.app_name)
                + "/Image";

        File photo_file =  new File(folder_photo);

        if(!photo_file.exists()){
            photo_file.mkdir();
        }

        File file_slide = new File(folder_slide);
        File file_photo = new File(folder_photo);

        videoPath = new ArrayList<String>();
        photoPath = new ArrayList<String>();

        if (file_slide.isDirectory()) {
            if (file_slide.listFiles()!=null){
                File[] listFile = file_slide.listFiles();
                Arrays.sort(listFile, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
                for (int i = 0; i < listFile.length; i++) {

                    if (listFile[i].getAbsolutePath().contains(".mp4") && videoPath.size() < 6) {
                        videoPath.add(listFile[i].getAbsolutePath());
                    }

                }

                if(videoPath.isEmpty()){
                    rv.setVisibility(View.GONE);
                    tv_empty.setVisibility(View.VISIBLE);
                }else{
                    rv.setVisibility(View.VISIBLE);
                    tv_empty.setVisibility(View.GONE);
                }
            }
        }

        if (file_photo.isDirectory()) {
            if (file_photo.listFiles()!=null){
                File[] listFile = file_photo.listFiles();
                Arrays.sort(listFile, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
                for (int i = 0; i < listFile.length; i++) {

                    if (listFile[i].getAbsolutePath().contains(".png") && photoPath.size() < 6) {
                        videoPath.add(listFile[i].getAbsolutePath());
                    }

                }

                if(photoPath.isEmpty()){
                    rv_photo.setVisibility(View.GONE);
                    tv_empty_photo.setVisibility(View.VISIBLE);
                }else{
                    rv_photo.setVisibility(View.VISIBLE);
                    tv_empty_photo.setVisibility(View.GONE);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 11){
            if (checkPermissions(this, permissionsList)){
                KSUtil.fromAlbum = false;
                Intent mIntent = new Intent(MainActivity.this, ImagePickerActivity.class);
                mIntent.putExtra(ImagePickerActivity.KEY_LIMIT_MAX_IMAGE, 30);
                mIntent.putExtra(ImagePickerActivity.KEY_LIMIT_MIN_IMAGE, 4);
                Animatee.animateSlideUp(MainActivity.this);
                startActivity(mIntent);
            }
        }
        else if(resultCode == RESULT_CANCELED && requestCode == RETURN_CODE){
            videoLoader();
        }
    }

    public interface onClickPhotoListener{
        void onEdit(int position);
        void onDelete(int position);
    }
}
