package com.kessi.photovideomaker.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.kessi.photovideomaker.R;
import com.kessi.photovideomaker.util.KSUtil;
import com.squareup.picasso.Picasso;

import java.io.File;

public class ImageSaverActivity extends AppCompatActivity {

    ImageView btnBack, imv_saved;
    LinearLayout btnEmail;
    FrameLayout frame;
    LinearLayout btnShare, btnRate;
    ImageView btnDelete;
    RelativeLayout main, top;
    static String path;

    public static String getPath() {
        return path;
    }

    public static void setPath(String path) {
        ImageSaverActivity.path = path;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_saver);
        BindView();
    }

    void BindView(){

        main = findViewById(R.id.main1);
        top = findViewById(R.id.header1);
        btnBack = findViewById(R.id.btn_back11);
        frame = findViewById(R.id.frame11);
        imv_saved = findViewById(R.id.imv_saved);
        btnEmail = findViewById(R.id.btnEmail);



        if(!path.isEmpty()){
            Picasso.get()
                    .load(Uri.parse("file://"+ path))
                    .into(imv_saved);
        }

//        Drawable dr = getResources().getDrawable(R.drawable.shipbar_round);
//        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
//        Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap,
//                getResources().getDisplayMetrics().widthPixels * 18 / 1080,
//                getResources().getDisplayMetrics().widthPixels * 18 / 1080, true));
//        imv_saved.setThumb(d);

        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ImageSaverActivity.this, SendEmailActivity.class);
                startActivity(intent);
            }
        });

        btnShare = findViewById(R.id.btnShare1);
        btnShare.setOnClickListener(v -> {
            KSUtil.Bounce(this, btnShare);
            share();
            //startActivityes(null, 0);
        });

        btnRate = findViewById(R.id.btnRate1);
        btnRate.setOnClickListener(v -> {
            KSUtil.Bounce(this, btnRate);
            rateUs();
            //startActivityes(null, 0);
        });

        btnDelete = findViewById(R.id.btnDelete1);
        btnDelete.setOnClickListener(v -> {
            KSUtil.Bounce(this, btnDelete);
            delete();
            //startActivityes(null, 0);
        });

//        path = getIntent().getExtras().getString("image_path");
//        imv_saved.setImageBitmap(path);


        btnBack.setOnClickListener(arg0 -> {
            // TODO Auto-generated method stub
            KSUtil.Bounce(this, btnBack);
            onBackPressed();
        });

    }

    void share() {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        share.setType("video/*");
        Uri photoURI = FileProvider.getUriForFile(
                getApplicationContext(),
                getApplicationContext()
                        .getPackageName() + ".provider", new File(path));
        share.putExtra(Intent.EXTRA_STREAM,
                photoURI);
        startActivity(Intent.createChooser(share, "Share via"));
    }

    void rateUs() {
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

    PopupWindow popupWindow;
    LinearLayout alertLay;
    Button yes, no;

    public void delete() {

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.delete_alert, null);

        alertLay = alertLayout.findViewById(R.id.alertLay);
        yes = alertLayout.findViewById(R.id.yes);
        no = alertLayout.findViewById(R.id.no);

        yes.setOnClickListener(v -> {
            popupWindow.dismiss();
            new File(path).delete();
            Toast.makeText(ImageSaverActivity.this, "Deleted Successfully!!!",
                    Toast.LENGTH_SHORT).show();
            onBackPressed();
        });

        no.setOnClickListener(v -> popupWindow.dismiss());

        popupWindow = new PopupWindow(alertLayout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        //display the popup window
        popupWindow.showAtLocation(main, Gravity.CENTER, 0, 0);
        dialogParam();
    }

    void dialogParam() {
        LinearLayout.LayoutParams paramsDialog = new LinearLayout.LayoutParams(
                getResources().getDisplayMetrics().widthPixels * 840 / 1080,
                getResources().getDisplayMetrics().heightPixels * 415 / 1920);
        alertLay.setLayoutParams(paramsDialog);

        LinearLayout.LayoutParams paramsCamera = new LinearLayout.LayoutParams(
                getResources().getDisplayMetrics().widthPixels * 300 / 1080,
                getResources().getDisplayMetrics().heightPixels * 130 / 1920);
        yes.setLayoutParams(paramsCamera);
        no.setLayoutParams(paramsCamera);
    }


    int FLAG_VIDEO = 21;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (KSUtil.fromAlbum) {
            Intent intent = new Intent();
            setResult(FLAG_VIDEO, intent);
        } else {
            gotoMain();
        }
    }


    public void gotoMain() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}