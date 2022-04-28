package com.naosteam.slideshowmaker.activities;

import static android.provider.Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.naosteam.slideshowmaker.R;
import com.naosteam.slideshowmaker.activities.intro.IntroActivity;
import com.naosteam.slideshowmaker.util.AppOpenAdsManager;

import org.apache.commons.io.comparator.LastModifiedFileComparator;

import java.io.File;
import java.util.Arrays;

public class SplashActivity extends AppCompatActivity {

    ImageView icon, bgIV;
    String[] permissionsList = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    AppOpenAdsManager appOpenAdsManager;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        appOpenAdsManager = new AppOpenAdsManager(this, new AppOpenAdsManager.OpenAdsListener() {
            @Override
            public void onClick() {
                checkAccessPermission();
            }
        });

        new Handler().postDelayed(() -> {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
                appOpenAdsManager.showAdIfAvailable();
            }else{
                checkAccessPermission();
            }
        }, 2000);



    }

    public void openMainActivity() {
        String folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                + "/" + getResources().getString(R.string.app_name);
        File file = new File(folder);

        int count = 0;

        if (file.isDirectory()) {
            File[] listFile = file.listFiles();

            Arrays.sort(listFile, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
            for (int i = 0; i < listFile.length; i++) {

                if (listFile[i].getAbsolutePath().contains(".mp4")) {
                    count++;
                }

            }

            if (count > 0) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            } else {
                startActivity(new Intent(SplashActivity.this, IntroActivity.class));
            }


        } else {
            startActivity(new Intent(SplashActivity.this, IntroActivity.class));
        }

        finish();

//        startActivity(new Intent(SplashActivity.this, BgTemplateDetailsActivity.class));
    }

    private void openAccessFileDialog() {

        final Dialog dialog = new Dialog(SplashActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);

        dialog.setContentView(R.layout.dialog_alert);

        TextView maintext = dialog.findViewById(R.id.maintext);
        maintext.setText("Please give permission to this app to access all file!");

        RelativeLayout img_btn_yes = dialog.findViewById(R.id.yes);
        RelativeLayout img_btn_no = dialog.findViewById(R.id.no);

        img_btn_no.setOnClickListener(v ->{
            dialog.dismiss();
            quitSplash();
        });

        img_btn_yes.setOnClickListener(v->{
            Intent i = new Intent();
            i.setAction(ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
            startActivityForResult(i, 11);
            dialog.dismiss();
        });

        dialog.show();
    }


    private void checkAccessPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !Environment.isExternalStorageManager()) {
            openAccessFileDialog();
        } else {
            if (!checkPermissions(SplashActivity.this, permissionsList)) {
                ActivityCompat.requestPermissions(SplashActivity.this, permissionsList, 21);
            } else {
                openMainActivity();
            }
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
                openMainActivity();
            }else{

                quitSplash();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 11 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && Environment.isExternalStorageManager()){
           openMainActivity();
        }else{
            quitSplash();
        }
    }

    private void quitSplash(){
        Toast.makeText(this, "Please allow the permissions to continue", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 1500);
    }
}
