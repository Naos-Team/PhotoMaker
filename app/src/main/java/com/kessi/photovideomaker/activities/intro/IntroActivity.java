package com.kessi.photovideomaker.activities.intro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.kessi.photovideomaker.R;

import java.util.ArrayList;

public class IntroActivity extends AppCompatActivity {

    ViewPager viewPager;
    IntroViewPagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        viewPager = findViewById(R.id.view_pager);

        ArrayList<IntroItem> arrayList = new ArrayList<>();
        arrayList.add(new IntroItem("AAAA AAAA AAAA AAAA", R.drawable.ic_home));
        arrayList.add(new IntroItem("BB BBBB BBB BBB", R.drawable.ic_rate));
        arrayList.add(new IntroItem("CCCC CCCC CCCC CC", R.drawable.ic_other));


        adapter = new IntroViewPagerAdapter(this, arrayList);

        viewPager.setAdapter(adapter);
    }
}