package com.naosteam.slideshowmaker.activities.myalbum;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.naosteam.slideshowmaker.R;
import com.naosteam.slideshowmaker.activities.AdapterMainPhoto;
import com.naosteam.slideshowmaker.activities.ImageSaverActivity;
import com.naosteam.slideshowmaker.activities.MainActivity;
import com.naosteam.slideshowmaker.activities.kessiimagepicker.activity.ImagePickerActivity;
import com.naosteam.slideshowmaker.util.AdManager;
import com.naosteam.slideshowmaker.util.KSUtil;

import org.apache.commons.io.comparator.LastModifiedFileComparator;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class PhotoAlbumActivity extends AppCompatActivity {

    RecyclerView rv;
    ImageView back;
    AdapterMainPhoto photoAdapter;
    ArrayList<String> photoPath;
    LinearLayout ll_adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_album);
        LoadAds();

        rv = findViewById(R.id.rv);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdManager.showAdmobInterAd(PhotoAlbumActivity.this, new AdManager.InterAdsListener() {
                    @Override
                    public void onClick() {
                        onBackPressed();
                    }
                });
            }
        });

        videoLoader();
    }

    private void LoadAds(){
        AdManager.initAd(PhotoAlbumActivity.this);
        ll_adView = findViewById(R.id.ll_adView1);
        AdManager.loadAdmobBanner(this, ll_adView);
        AdManager.loadInterAd(this);
    }



    public void videoLoader() {
        getFromStorage();

        photoAdapter = new AdapterMainPhoto(true, photoPath, new MainActivity.onClickPhotoListener() {
            @Override
            public void onDelete(int position) {
                String path = photoPath.get(position);

                File file1 = new File(path);
                if(file1.exists()){
                    file1.delete();
                    try{
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                            getContentResolver().delete(Uri.parse(path), null);
                        }
                    }catch (Exception e){
                        Log.e("Err", e.getMessage());
                    }
                    photoPath.remove(position);
                    photoAdapter.notifyItemRemoved(position);
                    photoAdapter.notifyItemRangeChanged(position, photoPath.size());
                    Toast.makeText(PhotoAlbumActivity.this, "Delete successfully!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onClick(int position) {
                String path = photoPath.get(position);
                ImageSaverActivity.setPath(path);
                startActivity(new Intent(PhotoAlbumActivity.this, ImageSaverActivity.class));
            }
        });

        rv.setLayoutManager(new GridLayoutManager(this, 2));
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(photoAdapter);

    }

    public void getFromStorage() {
        String folder_photo = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                + "/" + getResources().getString(R.string.app_name)
                + "/Image";

        File photo_file =  new File(folder_photo);

        if(!photo_file.exists()){
            photo_file.mkdir();
        }

        File file_photo = new File(folder_photo);

        photoPath = new ArrayList<String>();

        if (file_photo.isDirectory()) {
            if (file_photo.listFiles()!=null){
                File[] listFile = file_photo.listFiles();
                Arrays.sort(listFile, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
                for (int i = 0; i < listFile.length; i++) {

                    if (listFile[i].getAbsolutePath().contains(".png")) {
                        photoPath.add(listFile[i].getAbsolutePath());
                    }

                }

            }
        }
    }

    @Override
    public void onBackPressed() {
//        AdManager.adCounter++;
//        if (AdManager.adCounter == AdManager.adDisplayCounter) {
//            if (!AdManager.isloadFbMAXAd) {
//                AdManager.showInterAd(MyAlbumActivity.this, null, 0);
//            } else {
//                AdManager.showMaxInterstitial(MyAlbumActivity.this, null, 0);
//            }
//        } else {
//            Intent returnIntent = new Intent();
//            setResult(RESULT_CANCELED, returnIntent);
//            super.onBackPressed();
//        }
        AdManager.showAdmobInterAd(this, new AdManager.InterAdsListener() {
            @Override
            public void onClick() {
                Intent returnIntent = new Intent();
                setResult(RESULT_CANCELED, returnIntent);
                PhotoAlbumActivity.super.onBackPressed();
            }
        });
    }

}