package com.kessi.photovideomaker.activities.intro;

import static com.kessi.photovideomaker.activities.MainActivity.checkPermissions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
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

        viewPager = findViewById(R.id.view_pager);
        tabIndicator = findViewById(R.id.tab_layout);
        rl_bottom = findViewById(R.id.rl_bottom);
        btn_skip = findViewById(R.id.btn_skip);

//        ArrayList<IntroItem> arrayList = new ArrayList<>();
//        arrayList.add(new IntroItem("Make amazing slides anytime", R.drawable.intro_1));
//        arrayList.add(new IntroItem("Many awesome effects and musics", R.drawable.intro_2));
//        arrayList.add(new IntroItem("All is set Let's start", R.drawable.intro_3));

//        adapter = new IntroViewPagerAdapter(this, arrayList, () ->{
//
//            if (!checkPermissions(IntroActivity.this, permissionsList)) {
//                ActivityCompat.requestPermissions(IntroActivity.this, permissionsList, 21);
//            } else {
//                KSUtil.fromAlbum = false;
//                Intent mIntent = new Intent(IntroActivity.this, ImagePickerActivity.class);
//                mIntent.putExtra(ImagePickerActivity.KEY_LIMIT_MAX_IMAGE, 30);
//                mIntent.putExtra(ImagePickerActivity.KEY_LIMIT_MIN_IMAGE, 4);
//                startActivity(mIntent);
//            }
//        });

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 21){
            if (checkPermissions(this, permissionsList)){
                KSUtil.fromAlbum = false;
                Intent mIntent = new Intent(IntroActivity.this, ImagePickerActivity.class);
                mIntent.putExtra(ImagePickerActivity.KEY_LIMIT_MAX_IMAGE, 30);
                mIntent.putExtra(ImagePickerActivity.KEY_LIMIT_MIN_IMAGE, 4);
                startActivity(mIntent);
            }
        }
    }
}