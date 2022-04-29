package com.naosteam.slideshowmaker.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;


import com.naosteam.slideshowmaker.R;
import com.naosteam.slideshowmaker.util.AdManager;
import com.naosteam.slideshowmaker.util.KSUtil;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class VideoPlayerActivity extends AppCompatActivity implements
        SeekBar.OnSeekBarChangeListener {

    ImageView btnback;
    VideoView videoView;
    String path;
    MediaController mediaController;
    int vw, vh;
    FrameLayout frame;
    SeekBar seekVideo;
    TextView tvStartVideo, tvEndVideo;
    int duration = 0;
    Handler handler = new Handler();
    ImageView btnPlayVideo;
    boolean isPlay = false;

    LinearLayout btnShare, btnRate;
    ImageView btnDelete;
    RelativeLayout main, top;

    LinearLayout ll_adView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_video);

        //setTV();

        LoadAds();

        main = findViewById(R.id.main);
        top = findViewById(R.id.header);
        btnback = findViewById(R.id.btn_back1);
        videoView = findViewById(R.id.video111);
        frame = findViewById(R.id.frame1);
        seekVideo = findViewById(R.id.videoSeek);
        seekVideo.setOnSeekBarChangeListener(this);
        seekVideo.setEnabled(false);
        Drawable dr = getResources().getDrawable(R.drawable.shipbar_round);
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap,
                getResources().getDisplayMetrics().widthPixels * 18 / 1080,
                getResources().getDisplayMetrics().widthPixels * 18 / 1080, true));
        seekVideo.setThumb(d);

        tvStartVideo = findViewById(R.id.tvStartVideo);
        tvEndVideo = findViewById(R.id.tvEndVideo);
        btnPlayVideo = findViewById(R.id.btnPlayVideo);

        btnShare = findViewById(R.id.btnShare);
        btnShare.setOnClickListener(v -> {
            KSUtil.Bounce(this, btnShare);
            videoView.pause();
            share();
        });

        btnRate = findViewById(R.id.btnRate);
        btnRate.setOnClickListener(v -> {
            KSUtil.Bounce(this, btnRate);
            videoView.pause();
            rateUs();
        });

        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(v -> {
            KSUtil.Bounce(this, btnDelete);
            videoView.pause();
            delete();
        });


        btnback.setOnClickListener(arg0 -> {
            // TODO Auto-generated method stub
            KSUtil.Bounce(this, btnback);
            onBackPressed();
        });


        mediaController = new MediaController(VideoPlayerActivity.this);

        path = getIntent().getExtras().getString("video_path");
        videoView.setVideoPath(path);
        videoView.seekTo(100);
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(this.path);

        vw = Integer
                .valueOf(mediaMetadataRetriever
                        .extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));
        vh = Integer
                .valueOf(mediaMetadataRetriever
                        .extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));

        mediaMetadataRetriever.release();

        frame.setOnClickListener(view -> {

            if (!isPlay) {

                videoView.seekTo(seekVideo.getProgress());
                videoView.start();
                handler.postDelayed(seekrunnable, 200);
                videoView.setVisibility(View.VISIBLE);
                btnPlayVideo.setVisibility(View.VISIBLE);
                btnPlayVideo.setImageResource(0);
                btnPlayVideo.setImageResource(R.drawable.pause2);
            } else {

                videoView.pause();
                handler.removeCallbacks(seekrunnable);
                btnPlayVideo.setVisibility(View.VISIBLE);
                btnPlayVideo.setImageResource(0);
                btnPlayVideo.setImageResource(R.drawable.play);

            }
            isPlay = !isPlay;

        });
        videoView.setOnPreparedListener(mp -> {

            duration = videoView.getDuration();
            seekVideo.setMax(duration);

            tvStartVideo.setText("00:00");
            try {
                tvEndVideo.setText("" + formatTimeUnit(duration));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        videoView.setOnCompletionListener(mp -> {
            btnPlayVideo.setImageResource(0);
            btnPlayVideo.setImageResource(R.drawable.play);
            btnPlayVideo.setVisibility(View.VISIBLE);
            videoView.seekTo(100);
            seekVideo.setProgress(0);
            tvStartVideo.setText("00:00");
            handler.removeCallbacks(seekrunnable);
            isPlay = !isPlay;

        });
        btnPlayVideo.setOnClickListener(onclickplayvideo);
    }

    private void LoadAds(){
        AdManager.initAd(VideoPlayerActivity.this);
        ll_adView = findViewById(R.id.ll_adView16);
        AdManager.loadAdmobBanner(this, ll_adView);
    }


    public void setTV() {
        //FrameLayout nativeContainer = findViewById(R.id.nativeContainer);
        //FrameLayout nativeContainerMAX = findViewById(R.id.nativeContainerMAX);

        if (!AdManager.isloadFbMAXAd) {
            //admob
            AdManager.initAd(VideoPlayerActivity.this);
           // AdManager.loadNativeAd(VideoPlayerActivity.this, nativeContainer);
            AdManager.loadInterAd(VideoPlayerActivity.this);
        } else {
            //MAX + Fb banner Ads
            AdManager.initMAX(VideoPlayerActivity.this);
            //AdManager.loadNativeMAX(VideoPlayerActivity.this, nativeContainerMAX);
            AdManager.maxInterstital(VideoPlayerActivity.this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoView.seekTo(100);
    }

    @Override
    public void onProgressChanged(SeekBar seekbar, int progress,
                                  boolean fromTouch) {
        if (fromTouch) {
            videoView.seekTo(progress);
            try {
                tvStartVideo.setText("" + formatTimeUnit(progress));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStopTrackingTouch(SeekBar arg0) {
        // TODO Auto-generated method stub

    }

    public static String formatTimeUnit(long millis) throws ParseException {
        String formatted = String.format(
                "%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis)
                        - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
                        .toMinutes(millis)));
        return formatted;
    }

    View.OnClickListener onclickplayvideo = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            Log.e("", "play status " + isPlay);

            if (!isPlay) {
                videoView.seekTo(seekVideo.getProgress());
                videoView.start();
                handler.postDelayed(seekrunnable, 200);
                videoView.setVisibility(View.VISIBLE);
                btnPlayVideo.setVisibility(View.VISIBLE);
                btnPlayVideo.setImageResource(0);
                btnPlayVideo.setImageResource(R.drawable.pause2);

            } else {
                videoView.pause();
                handler.removeCallbacks(seekrunnable);
                btnPlayVideo.setVisibility(View.VISIBLE);
                btnPlayVideo.setImageResource(0);
                btnPlayVideo.setImageResource(R.drawable.play);
            }
            isPlay = !isPlay;

        }
    };

    Runnable seekrunnable = new Runnable() {
        public void run() {
            if (videoView.isPlaying()) {
                int curPos = videoView.getCurrentPosition();
                seekVideo.setProgress(curPos);
                try {
                    tvStartVideo.setText("" + formatTimeUnit(curPos));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (curPos == duration) {
                    seekVideo.setProgress(0);
                    tvStartVideo.setText("00:00");
                    handler.removeCallbacks(seekrunnable);
                } else
                    handler.postDelayed(seekrunnable, 200);

            } else {
                seekVideo.setProgress(duration);
                try {
                    tvStartVideo.setText("" + formatTimeUnit(duration));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                handler.removeCallbacks(seekrunnable);
            }
        }
    };


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
            Toast.makeText(VideoPlayerActivity.this, "Deleted Successfully!!!",
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
        AdManager.showAdmobInterAd(VideoPlayerActivity.this, new AdManager.InterAdsListener() {
            @Override
            public void onClick() {
                VideoPlayerActivity.super.onBackPressed();
                if (KSUtil.fromAlbum) {
                    Intent intent = new Intent();
                    setResult(FLAG_VIDEO, intent);
                } else {
                    gotoMain();
                }
            }
        });

    }


    public void gotoMain() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


}
