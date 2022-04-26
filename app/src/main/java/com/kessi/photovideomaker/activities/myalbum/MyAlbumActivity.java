package com.kessi.photovideomaker.activities.myalbum;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kessi.photovideomaker.R;
import com.kessi.photovideomaker.activities.VideoPlayerActivity;
import com.kessi.photovideomaker.util.AdManager;
import com.kessi.photovideomaker.util.KSUtil;

import org.apache.commons.io.comparator.LastModifiedFileComparator;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class MyAlbumActivity extends AppCompatActivity {

    public static ArrayList<String> videoPath = new ArrayList<String>();

    RecyclerView videoListView;
    MyVideoAdapter videoAdapter;
    int FLAG_VIDEO = 21;
    ImageView backIV;
    RelativeLayout header;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_videos);

        setTV();

        header = (RelativeLayout) findViewById(R.id.header);
        videoLoader();

        backIV = (ImageView) findViewById(R.id.back);
        backIV.setOnClickListener(v -> {
            KSUtil.Bounce(this, backIV);
            onBackPressed();
        });

    }

    public void setTV(){
        LinearLayout adContainer = findViewById(R.id.banner_container);
        if (!AdManager.isloadFbMAXAd) {
            //admob
            AdManager.initAd(MyAlbumActivity.this);
            AdManager.loadBannerAd(MyAlbumActivity.this, adContainer);
            AdManager.loadInterAd(MyAlbumActivity.this);
        } else {
//            MAX + Fb banner Ads
            AdManager.initMAX(MyAlbumActivity.this);
            AdManager.maxBanner(MyAlbumActivity.this, adContainer);
            AdManager.maxInterstital(MyAlbumActivity.this);
        }
    }

    @Override
    public void onBackPressed() {
        AdManager.adCounter++;
        if (AdManager.adCounter == AdManager.adDisplayCounter) {
            if (!AdManager.isloadFbMAXAd) {
                AdManager.showInterAd(MyAlbumActivity.this, null, 0);
            } else {
                AdManager.showMaxInterstitial(MyAlbumActivity.this, null, 0);
            }
        } else {
            Intent returnIntent = new Intent();
            setResult(RESULT_CANCELED, returnIntent);
            super.onBackPressed();
        }
    }

    void startActivityes(Intent intent, int reqCode) {
        if (!AdManager.isloadFbMAXAd) {
            AdManager.adCounter++;
            AdManager.showInterAd(MyAlbumActivity.this, intent,reqCode);
        } else {
            AdManager.adCounter++;
            AdManager.showMaxInterstitial(MyAlbumActivity.this, intent,reqCode);
        }
    }


    public void videoLoader() {
        getFromStorage();
        videoListView = (RecyclerView) findViewById(R.id.recyclerView);
        videoAdapter = new MyVideoAdapter(true, videoPath, MyAlbumActivity.this, (v, position) -> {

            Intent intent = new Intent(MyAlbumActivity.this, VideoPlayerActivity.class);
            intent.putExtra("video_path", videoPath.get(position));
            startActivityes(intent, FLAG_VIDEO);
        });

        videoListView.setLayoutManager(new GridLayoutManager(this, 2));
        videoListView.setItemAnimator(new DefaultItemAnimator());
        videoListView.setAdapter(videoAdapter);

    }

    public void getFromStorage() {
        String folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                + "/" + getResources().getString(R.string.app_name);
        File file = new File(folder);
        videoPath = new ArrayList<String>();
        if (file.isDirectory()) {
            File[] listFile = file.listFiles();
            Arrays.sort(listFile, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
            for (int i = 0; i < listFile.length; i++) {

                if (listFile[i].getAbsolutePath().contains(".mp4")) {
                    videoPath.add(listFile[i].getAbsolutePath());
                }

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FLAG_VIDEO) {
            videoAdapter.notifyDataSetChanged();
            videoLoader();
        }
    }


}
