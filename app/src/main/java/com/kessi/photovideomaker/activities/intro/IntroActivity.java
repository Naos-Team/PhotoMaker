package com.kessi.photovideomaker.activities.intro;

import static android.provider.Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION;
import static com.kessi.photovideomaker.activities.MainActivity.checkPermissions;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.kessi.photovideomaker.KessiApplication;
import com.kessi.photovideomaker.R;
import com.kessi.photovideomaker.activities.MainActivity;
import com.kessi.photovideomaker.activities.kessiimagepicker.activity.ImagePickerActivity;
import com.kessi.photovideomaker.activities.myalbum.MyAlbumActivity;
import com.kessi.photovideomaker.util.Animatee;
import com.kessi.photovideomaker.util.KSUtil;

import java.util.ArrayList;

public class IntroActivity extends AppCompatActivity {

    ViewPager viewPager;
    IntroViewPagerAdapter adapter;
    TabLayout tabIndicator;
    RelativeLayout rl_bottom;
    TextView btn_skip;

    String[] permissionsList = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        KessiApplication.VIDEO_HEIGHT = displaymetrics.widthPixels;
        KessiApplication.VIDEO_WIDTH = displaymetrics.widthPixels;

        viewPager = findViewById(R.id.view_pager);
        tabIndicator = findViewById(R.id.tab_layout);
        rl_bottom = findViewById(R.id.rl_bottom);
        btn_skip = findViewById(R.id.btn_skip);

        ArrayList<IntroItem> arrayList = new ArrayList<>();
        arrayList.add(new IntroItem("Make amazing slides anytime", R.drawable.intro_1));
        arrayList.add(new IntroItem("Many awesome effects and musics", R.drawable.intro_2));
        arrayList.add(new IntroItem("All is set Let's start", R.drawable.intro_3));

        adapter = new IntroViewPagerAdapter(this, arrayList, () ->{
            new Handler().postDelayed(() -> {

                KSUtil.fromAlbum = false;
                Intent mIntent = new Intent(IntroActivity.this, ImagePickerActivity.class);
                mIntent.putExtra(ImagePickerActivity.KEY_LIMIT_MAX_IMAGE, 30);
                mIntent.putExtra(ImagePickerActivity.KEY_LIMIT_MIN_IMAGE, 4);
                startActivity(mIntent);
//
//                if (!checkPermissions(IntroActivity.this, permissionsList)) {
//                    ActivityCompat.requestPermissions(IntroActivity.this, permissionsList, 21);
//
//                } else {
//
//
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !Environment.isExternalStorageManager()) {
//                        openAccessFileDialog();
//                    }else{
//                        KSUtil.fromAlbum = false;
//                        Intent mIntent = new Intent(IntroActivity.this, ImagePickerActivity.class);
//                        mIntent.putExtra(ImagePickerActivity.KEY_LIMIT_MAX_IMAGE, 30);
//                        mIntent.putExtra(ImagePickerActivity.KEY_LIMIT_MIN_IMAGE, 4);
//                        startActivity(mIntent);
//                    }
//                }

            },100);
        });

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position < 2){
                    tabIndicator.animate()
                            .alpha(1f)
                            .setDuration(300)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationStart(Animator animation) {
                                    tabIndicator.setVisibility(View.VISIBLE);
                                }
                            });

                    btn_skip.animate()
                            .alpha(1f)
                            .setDuration(300)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationStart(Animator animation) {
                                    btn_skip.setVisibility(View.VISIBLE);
                                }
                            });
                }else {
                    tabIndicator.animate()
                            .alpha(0f)
                            .setDuration(300)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    tabIndicator.setVisibility(View.GONE);
                                }
                            });
                    btn_skip.animate()
                            .alpha(0f)
                            .setDuration(300)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    btn_skip.setVisibility(View.GONE);
                                }
                            });
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        btn_skip.setOnClickListener((v) ->{
            viewPager.setCurrentItem(2, true);
        });

        tabIndicator.setupWithViewPager(viewPager);
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

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !Environment.isExternalStorageManager()) {
                    openAccessFileDialog();
                }else{
                    KSUtil.fromAlbum = false;
                    Intent mIntent = new Intent(IntroActivity.this, ImagePickerActivity.class);
                    mIntent.putExtra(ImagePickerActivity.KEY_LIMIT_MAX_IMAGE, 30);
                    mIntent.putExtra(ImagePickerActivity.KEY_LIMIT_MIN_IMAGE, 4);
                    startActivity(mIntent);
                }


            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 11){
            if (!checkPermissions(IntroActivity.this, permissionsList)) {
                ActivityCompat.requestPermissions(IntroActivity.this, permissionsList, 21);
            } else {
                KSUtil.fromAlbum = false;
                Intent mIntent = new Intent(IntroActivity.this, ImagePickerActivity.class);
                mIntent.putExtra(ImagePickerActivity.KEY_LIMIT_MAX_IMAGE, 30);
                mIntent.putExtra(ImagePickerActivity.KEY_LIMIT_MIN_IMAGE, 4);
                startActivity(mIntent);
                Animatee.animateSlideUp(IntroActivity.this);
            }
        }
    }
}