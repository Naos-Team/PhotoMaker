package com.kessi.photovideomaker.activities;

import static android.provider.Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.kessi.photovideomaker.R;
import com.kessi.photovideomaker.activities.intro.IntroActivity;
import com.kessi.photovideomaker.activities.kessiimagepicker.activity.ImagePickerActivity;
import com.kessi.photovideomaker.util.Animatee;
import com.kessi.photovideomaker.util.KSUtil;
import com.kessi.photovideomaker.util.Render;

import org.apache.commons.io.comparator.LastModifiedFileComparator;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class SplashActivity extends AppCompatActivity {

    ImageView icon, bgIV;
    String[] permissionsList = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {


            checkAccessPermission();
//            Animatee.animateSplit(SplashActivity.this);
        }, 1500);
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
        //startActivity(new Intent(SplashActivity.this, MainActivity.class));
    }

    private void openAccessFileDialog() {
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
                quitSplash();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
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
