package com.naosteam.slideshowmaker.activities.videoeditor;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.signature.MediaStoreSignature;
import com.naosteam.slideshowmaker.KessiApplication;
import com.naosteam.slideshowmaker.R;
import com.naosteam.slideshowmaker.activities.MainActivity;
import com.naosteam.slideshowmaker.activities.process.VideoMakerActivity;
import com.naosteam.slideshowmaker.activities.songpicker.SongGalleryActivity;
import com.naosteam.slideshowmaker.util.AdManager;
import com.naosteam.slideshowmaker.util.KSUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import vcarry.adapters.FrameAdapter;
import vcarry.adapters.ThemeAdapter;
import vcarry.data.ImageData;
import vcarry.data.MusicData;
import vcarry.mask.KessiTheme;
import vcarry.service.ServiceAnim;
import vcarry.util.FileUtils;
import vcarry.util.Utils;


public class VideoThemeActivity extends AppCompatActivity implements OnClickListener, OnSeekBarChangeListener {
    public static KessiApplication application;
    ArrayList<ImageData> arrayList;
    private static final String PREFS_NAME = "preferenceName";
    LinearLayout cvframeview;
    LinearLayout cvthemview;
    Float[] duration = new Float[]{1.0f, 1.5f,
            2.0f, 2.5f, 3.0f, 3.5f,
            4.0f, 4.5f, 5.0f};
    Boolean[] check = new Boolean[]{true, true, true ,true ,true, true, true, true};
    int old_duration_position = -1;
    public static View flLoader;

    int frame;
    FrameAdapter frameAdapter;
    RequestManager glide;
    public static Handler handler = new Handler();
    int seekProgress = 0;
    private TextView txt_Seekbar_picktime;
    private SeekBar seekBar_picktime;

    ImageView idanimation, ibAddMusic, ibAddDuration, idviewFrame;


    LayoutInflater inflater;
    boolean isFromTouch = false;
    ImageView ivFrame;
    View ivPlayPause;
    ImageView ivPlayPause1;
    ImageView ivPreview, backimg_preview, done_preview;
    ArrayList<ImageData> lastData = new ArrayList();
    ConstraintLayout llEdit;
    LockRunnable lockRunnable = new LockRunnable();
    MediaPlayer mPlayer;

    RecyclerView rvThemes, rvFrame;
    public static float seconds = 3.0f;
    SeekBar seekBar;
    ThemeAdapter themeAdapter;
    LinearLayout toolbar;
    TextView tvEndTime;
    TextView tvTime;

    public static String outputPath = "";
    public static String folderPath = null;
    public static int mDuration;
    public static File tempFile;
    public static Float duration1;
    public static int total;
    FrameLayout scaleCard;
    public static File logFile = new File(FileUtils.TEMP_DIRECTORY, "video.txt");

    Button img_btn_yes, img_btn_no;
    LinearLayout maindailog;


    LinearLayout  laySeconds;

    TextView txtsec1, txtsec15, txtsec2, txtsec25, txtsec3, txtsec35, txtsec4, txtsec45, txtsec5;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_creater);
        application = KessiApplication.getInstance();
        application.selectedTheme = KessiTheme.None;
        Utils.framePostion = -1;
        application.videoImages.clear();
        bindView();
        folderPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/"
                + getResources().getString(R.string.app_name);

        Intent intent = new Intent(getApplicationContext(), ServiceAnim.class);
        intent.putExtra(ServiceAnim.EXTRA_SELECTED_THEME, application.getCurrentTheme());
        startService(intent);

        Drawable dr = getResources().getDrawable(R.drawable.shipbar_round);
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap,
                getResources().getDisplayMetrics().widthPixels * 18 / 1080,
                getResources().getDisplayMetrics().widthPixels * 18 / 1080, true));
        seekBar_picktime.setThumb(d);

        init();
        addListner();
        KessiApplication.isBreak = false;

        RelativeLayout.LayoutParams paramsbtn = new RelativeLayout.LayoutParams(
                getResources().getDisplayMetrics().widthPixels * 120 / 1080,
                getResources().getDisplayMetrics().heightPixels * 120 / 1920);
        idanimation.setLayoutParams(paramsbtn);
        idviewFrame.setLayoutParams(paramsbtn);
        ibAddMusic.setLayoutParams(paramsbtn);
        ibAddDuration.setLayoutParams(paramsbtn);


        if (!AdManager.isloadFbMAXAd) {
            //admob
            AdManager.initAd(VideoThemeActivity.this);
            AdManager.loadInterAd(VideoThemeActivity.this);
        } else {
            //MAX + Fb banner Ads
            AdManager.initMAX(VideoThemeActivity.this);
            AdManager.maxInterstital(VideoThemeActivity.this);
        }
    }


    void startActivityes(Intent intent, int reqCode) {
        if (!AdManager.isloadFbMAXAd) {
            AdManager.adCounter++;
            AdManager.showInterAd(VideoThemeActivity.this, intent,reqCode);
        } else {
            AdManager.adCounter++;
            AdManager.showMaxInterstitial(VideoThemeActivity.this, intent,reqCode);
        }
    }

    void bindView() {
        flLoader = findViewById(R.id.flLoader);

        txt_Seekbar_picktime = findViewById(R.id.txt_Seekbar_picktime);
        seekBar_picktime = findViewById(R.id.seekBar_picktime);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            seekBar_picktime.setMin(10);
        }
        seekBar_picktime.setMax(100);
        seekBar_picktime.setProgress(10);
        seekBar_picktime.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txt_Seekbar_picktime.setText((float) progress/10 + "s");
                seconds = (float) progress/10;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                change_Choice(-1);
                setDuration();
            }
        });


        flLoader.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ivPreview = findViewById(R.id.previewImageView1);
        ivFrame = findViewById(R.id.ivFrame);
        seekBar = findViewById(R.id.sbPlayTime);
        seekBar.setEnabled(false);
        Drawable dr = getResources().getDrawable(R.drawable.shipbar_round);
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap,
                getResources().getDisplayMetrics().widthPixels * 18 / 1080,
                getResources().getDisplayMetrics().widthPixels * 18 / 1080, true));
        seekBar.setThumb(d);


        tvEndTime = findViewById(R.id.tvEndTime);
        tvTime = findViewById(R.id.tvTime);
        llEdit = findViewById(R.id.llEdit);
        scaleCard = findViewById(R.id.scaleCard);
        ivPlayPause = findViewById(R.id.ivPlayPause);

        LinearLayout.LayoutParams paramsd = new LinearLayout.LayoutParams(
                getResources().getDisplayMetrics().widthPixels * 130 / 1080,
                getResources().getDisplayMetrics().heightPixels * 130 / 1920);
        ivPlayPause.setLayoutParams(paramsd);


        //ivPlayPause1 = findViewById(R.id.ivPlayPause1);
        LinearLayout.LayoutParams paramsp = new LinearLayout.LayoutParams(
                getResources().getDisplayMetrics().widthPixels * 60 / 1080,
                getResources().getDisplayMetrics().heightPixels * 60 / 1920);
//        ivPlayPause1.setLayoutParams(paramsp);

        toolbar = findViewById(R.id.toolbar_preview);
        rvThemes = findViewById(R.id.rvThemes);
        laySeconds = findViewById(R.id.laySeconds);

        rvFrame = findViewById(R.id.rvFrame);

        ibAddDuration = findViewById(R.id.ibAddDuration);
        ibAddMusic = findViewById(R.id.ibAddMusic);

        backimg_preview = findViewById(R.id.backimg_preview);
        done_preview = findViewById(R.id.done_preview);

        LinearLayout.LayoutParams ln = new
                LinearLayout.LayoutParams(KessiApplication.VIDEO_WIDTH, KessiApplication.VIDEO_WIDTH);
        scaleCard.setLayoutParams(ln);

        FrameLayout.LayoutParams fr = new
                FrameLayout.LayoutParams(KessiApplication.VIDEO_WIDTH, KessiApplication.VIDEO_WIDTH);
        ivPreview.setLayoutParams(fr);
        ivFrame.setLayoutParams(fr);

        backimg_preview.setOnClickListener(v -> {
            KSUtil.Bounce(VideoThemeActivity.this, backimg_preview);
            onBackPressed();
        });
        done_preview.setOnClickListener(v -> {
            KSUtil.Bounce(VideoThemeActivity.this, done_preview);
            if (mPlayer != null) {
                mDuration = mPlayer.getDuration();

                if (mPlayer.isPlaying())
                    mPlayer.pause();

            }

            lockRunnable.stop();

            startActivityes(new Intent(VideoThemeActivity.this, VideoMakerActivity.class),0);

        });


        txtsec1 = findViewById(R.id.txtsec1);
        txtsec15 = findViewById(R.id.txtsec15);
        txtsec2 = findViewById(R.id.txtsec2);
        txtsec25 = findViewById(R.id.txtsec25);
        txtsec3 = findViewById(R.id.txtsec3);
        txtsec35 = findViewById(R.id.txtsec35);
        txtsec4 = findViewById(R.id.txtsec4);
        txtsec45 = findViewById(R.id.txtsec45);
        txtsec5 = findViewById(R.id.txtsec5);

        txtsec1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sec1();
            }
        });
        txtsec15.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sec15();
            }
        });
        txtsec2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sec2();
            }
        });
        txtsec25.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sec25();
            }
        });
        txtsec3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sec3();
            }
        });
        txtsec35.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sec35();
            }
        });
        txtsec4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sec4();
            }
        });
        txtsec45.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sec45();
            }
        });
        txtsec5.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sec5();
            }
        });


        LinearLayout.LayoutParams paramstxt = new LinearLayout.LayoutParams(
                getResources().getDisplayMetrics().widthPixels * 204 / 1080,
                getResources().getDisplayMetrics().widthPixels * 204 / 1080);
        paramstxt.setMargins(10,10,10,10);
        txtsec1.setLayoutParams(paramstxt);
        txtsec15.setLayoutParams(paramstxt);
        txtsec2.setLayoutParams(paramstxt);
        txtsec25.setLayoutParams(paramstxt);
        txtsec3.setLayoutParams(paramstxt);
        txtsec35.setLayoutParams(paramstxt);
        txtsec4.setLayoutParams(paramstxt);
        txtsec45.setLayoutParams(paramstxt);
        txtsec5.setLayoutParams(paramstxt);
    }

    void init() {
        seconds = application.getSecond();
        inflater = LayoutInflater.from(this);
        glide = Glide.with(this);
        application = KessiApplication.getInstance();
        arrayList = application.getSelectedImages();

        total = (int) (((float) (arrayList.size() - 1)) * seconds);
        seekBar.setMax((arrayList.size() - 1) * 30);



        tvEndTime.setText(String.format("%02d:%02d", new Object[]{Integer.valueOf(total / 60),
                Integer.valueOf(total % 60)}));
        setUpThemeAdapter();
        glide.load(((ImageData) application.getSelectedImages().get(0)).imagePath).into(ivPreview);


        setTheme();


    }


    void addListner() {
        findViewById(R.id.video_clicker).setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(this);
        findViewById(R.id.ibAddMusic).setOnClickListener(this);
        findViewById(R.id.ibAddDuration).setOnClickListener(this);
    }


    void reinitMusic() {
        Exception e;
        try {
            MusicData PVMWSMusicData = application.getMusicData();

            if (PVMWSMusicData != null) {
                Uri photoURI = FileProvider.getUriForFile(
                        getApplicationContext(),
                        getApplicationContext()
                                .getPackageName() + ".provider", new File(PVMWSMusicData.track_data));
                mPlayer = MediaPlayer.create(this, photoURI);
//                mPlayer = MediaPlayer.create(this, Uri.parse(PVMWSMusicData.track_data));
                Log.e("musicpath", PVMWSMusicData.track_data);
                mPlayer.setLooping(true);
                try {
                    mPlayer.prepare();
                    return;
                } catch (Exception e2) {
                    e = e2;
                }
            } else {
                return;
            }
            e.printStackTrace();
        } catch (Exception e1) {

        }

    }

    void playMusic() {
        if (flLoader.getVisibility() != View.VISIBLE && mPlayer != null && !mPlayer.isPlaying()) {
            mPlayer.start();
        }
    }

    void pauseMusic() {
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.pause();
        }
    }




    public static void appendVideoLog(String text) {
        if (!FileUtils.TEMP_DIRECTORY.exists()) {
            FileUtils.TEMP_DIRECTORY.mkdirs();
        }
        try {
            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
            buf.append(text);
            buf.newLine();
            buf.close();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    void removeFrameImage(String path) {
        File appimages = new File(path);
        if (appimages.exists()) {
            File[] files = appimages.listFiles();
            if (files != null) {
                for (File f : files) {
                    if ((f.getName().endsWith(".jpg") || f.getName().endsWith(
                            ".png"))) {
                        f.delete();
                    }
                }
            }
        }
    }

    void removemusic(String path) {
        File appimages = new File(path);
        if (appimages.exists()) {
            File[] files = appimages.listFiles();
            if (files != null) {
                for (File f : files) {
                    if ((f.getName().endsWith(".mp3"))) {
                        f.delete();
                    }
                }
            }
        }
    }

    void setUpThemeAdapter() {
        themeAdapter = new ThemeAdapter(VideoThemeActivity.this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, RecyclerView.HORIZONTAL, false);
        GridLayoutManager gridLayoutManagerFrame = new GridLayoutManager(this, 1, RecyclerView.HORIZONTAL, false);
        rvThemes.setLayoutManager(gridLayoutManager);
        rvThemes.setItemAnimator(new DefaultItemAnimator());
        rvThemes.setAdapter(themeAdapter);
        frameAdapter = new FrameAdapter(VideoThemeActivity.this);
        rvFrame.setLayoutManager(gridLayoutManagerFrame);
        rvFrame.setItemAnimator(new DefaultItemAnimator());
        rvFrame.setAdapter(frameAdapter);

        cvthemview = findViewById(R.id.cvthemview);
        cvframeview = findViewById(R.id.cvframeview);
        idanimation = findViewById(R.id.idanimation);
        idviewFrame = findViewById(R.id.idviewFrame);


        idanimation.setOnClickListener(v -> {
            KSUtil.Bounce(VideoThemeActivity.this, idanimation);
            unpress();
            idanimation.setImageResource(R.drawable.theme_unpresed);
            cvthemview.setVisibility(View.VISIBLE);
            cvframeview.setVisibility(View.GONE);
            laySeconds.setVisibility(View.GONE);

            startActivityes(null,0);
        });

        idviewFrame.setOnClickListener(v -> {
            KSUtil.Bounce(VideoThemeActivity.this, idviewFrame);
            unpress();
            idviewFrame.setImageResource(R.drawable.frame_unpresed);
            cvframeview.setVisibility(View.VISIBLE);
            cvthemview.setVisibility(View.GONE);
            laySeconds.setVisibility(View.GONE);

            startActivityes(null,0);
        });
    }


    synchronized void displayImage() {
        try {
            if (seekProgress >= seekBar.getMax()) {
                seekProgress = 0;
                lockRunnable.stop();
            } else {
                if (seekProgress > 0 && flLoader.getVisibility() == View.VISIBLE) {
                    flLoader.setVisibility(View.GONE);
                    if (!(mPlayer == null || mPlayer.isPlaying())) {
                        mPlayer.start();
                    }
                }


                seekBar.setSecondaryProgress(application.videoImages.size());


                if (seekBar.getProgress() < seekBar.getSecondaryProgress()) {


                    seekProgress %= application.videoImages.size();


                    glide.load((String) application.videoImages.get(seekProgress))
                            .asBitmap()
                            .signature(new MediaStoreSignature("image/*", System.currentTimeMillis(), 0))
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            ivPreview.setImageBitmap(resource);
                        }
                    });
                    seekProgress++;
                    if (!isFromTouch) {
                        seekBar.setProgress(seekProgress);
                    }
                    int j = (int) ((((float) seekProgress) / 30.0) * seconds);
                    int mm = j / 60;
                    int ss = j % 60;
                    tvTime.setText(String.format("%02d:%02d", new Object[]{Integer.valueOf(mm), Integer.valueOf(ss)}));


                    total = (int) (((float) (arrayList.size() - 1)) * seconds);


                    tvEndTime.setText(String.format("%02d:%02d", new Object[]
                            {Integer.valueOf(total / 60), Integer.valueOf(total % 60)}));
                }
            }
        } catch (Exception e) {
            glide = Glide.with(this);
        }
    }

    @SuppressLint({"WrongConstant"})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibAddDuration:
                KSUtil.Bounce(this, ibAddDuration);
                unpress();
                ibAddDuration.setImageResource(R.drawable.timer_unpresed);
                cvframeview.setVisibility(View.GONE);
                cvthemview.setVisibility(View.GONE);
//                if (laySeconds.getVisibility() == View.GONE) {
//                    laySeconds.setVisibility(View.VISIBLE);
//
//                } else {
//                    laySeconds.setVisibility(View.GONE);
//                }
                laySeconds.setVisibility(View.VISIBLE);

                startActivityes(null,0);
                return;

            case R.id.ibAddMusic:
                KSUtil.Bounce(this, ibAddMusic);
                flLoader.setVisibility(8);
                loadSongSelection();
                return;

            case R.id.video_clicker:
                if (lockRunnable.isPause()) {
                    lockRunnable.play();
                    return;
                } else {
                    lockRunnable.pause();
                    return;
                }
            default:
                return;
        }
    }

    void unpress(){
        ibAddDuration.setImageResource(R.drawable.timer_presed);
        idviewFrame.setImageResource(R.drawable.frame_presed);
        idanimation.setImageResource(R.drawable.theme_presed);
    }

    protected void onPause() {
        super.onPause();
        lockRunnable.pause();
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        application.isEditEnable = false;
        if (resultCode == -1) {
            Intent intent;
            switch (requestCode) {
                case 101:
                    application.isFromSdCardAudio = true;
                    seekProgress = 0;
                    reinitMusic();
                    return;

                default:
                    return;
            }
        }
    }

    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        seekProgress = progress;

        if (isFromTouch) {
            seekBar.setProgress(Math.min(progress, seekBar.getSecondaryProgress()));
            displayImage();
            seekMediaPlayer();
        }
    }

    void seekMediaPlayer() {
        if (mPlayer != null) {
            try {
                mPlayer.seekTo(((int) (((((float) seekProgress) / 30.0) * seconds) * 1000.0f)) % mPlayer.getDuration());
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
        isFromTouch = true;
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
        isFromTouch = false;
    }

    public void reset() {


        KessiApplication.isBreak = false;
        application.videoImages.clear();
        handler.removeCallbacks(lockRunnable);
        lockRunnable.stop();
        Glide.get(this).clearMemory();
//        new threadclass().start();

        new deleteThemeDirAsync().execute();

//        FileUtils.deleteTempDir();
//        glide = Glide.with(this);
//        setTheme();

    }

    class deleteThemeDirAsync extends AsyncTask<Void, Void, Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            flLoader.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            new threadclass().start();
            deleteThemeDir(application.selectedTheme.toString());
            FileUtils.deleteTempDir();
            setTheme();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
//            KessiApplication.isBreak = false;
//            application.videoImages.clear();
//            handler.removeCallbacks(lockRunnable);
//            lockRunnable.stop();
//            Glide.get(VideoThemeActivity.this).clearMemory();
//            new threadclass().start();
            glide = Glide.with(VideoThemeActivity.this);
//            setTheme();

            Intent intent = new Intent(application, ServiceAnim.class);
            intent.putExtra(ServiceAnim.EXTRA_SELECTED_THEME, application.getCurrentTheme());
            application.startService(intent);
        }
    }

    private void deleteThemeDir(final String dir) {
        new Thread() {
            public void run() {
                FileUtils.deleteThemeDir(dir);
            }
        }.start();
    }

    public void onBackPressed() {
        AdManager.adCounter++;
        if (AdManager.adCounter == AdManager.adDisplayCounter) {
            if (!AdManager.isloadFbMAXAd) {
                AdManager.showInterAd(VideoThemeActivity.this, null, 0);
            } else {
                AdManager.showMaxInterstitial(VideoThemeActivity.this, null, 0);
            }
        } else {
            if (llEdit.getVisibility() != View.VISIBLE) {
                llEdit.setVisibility(View.VISIBLE);
                application.isEditEnable = false;
            } else {

                open();
            }
        }
    }

    public void open() {

        final Dialog d_delete = new Dialog(VideoThemeActivity.this);
        d_delete.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d_delete.getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);

        d_delete.setContentView(R.layout.dialog_delete);

        TextView maintext = d_delete.findViewById(R.id.maintext);
        img_btn_yes = d_delete.findViewById(R.id.img_btn_yes);
        img_btn_no = d_delete.findViewById(R.id.img_btn_no);
        maindailog = d_delete.findViewById(R.id.maindailog);


        popuplayout_dialog();

        maintext.setText(getResources().getString(R.string.back_m));
        img_btn_yes.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {

                application.videoImages.clear();
                application.getSelectedImages().clear();
                removeFrameImage(folderPath);
                removeFrameImage(folderPath + "/temp");
                removeFrameImage(folderPath + "/edittmpzoom");
                FileUtils.deleteFile(tempFile);
                removemusic(folderPath + "/music/");


                File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/MS_SlideShow" + "/imagesfolder");
                if (dir.isDirectory()) {
                    String[] children = dir.list();
                    for (int i = 0; i < children.length; i++) {
                        new File(dir, children[i]).delete();
                    }
                }


                d_delete.dismiss();
                Intent i_back = new Intent(VideoThemeActivity.this, MainActivity.class);
                i_back.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i_back);

            }
        });
        img_btn_no.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {

                d_delete.dismiss();
            }
        });
        d_delete.show();

    }


    public void setTheme() {
        if (application.isFromSdCardAudio) {
            new threadc1().start();
        } else {
            new threadc().start();
        }
    }



    public void setFrame(int data) {
        frame = data;
        if (data == -1) {
            ivFrame.setImageDrawable(null);
        } else {
            ivFrame.setImageResource(data);
        }
        application.setFrame(data);
    }

    public int getFrame() {
        return application.getFrame();
    }

    void loadSongSelection() {

        startActivityes(new Intent(this, SongGalleryActivity.class), 101);
    }


    class threadclass extends Thread {
        threadclass() {
        }

        public void run() {
            Glide.get(VideoThemeActivity.this).clearDiskCache();
        }
    }


    class threadc extends Thread {

        threadc() {
        }

        public void run() {
            KessiTheme PVMWSThemes = application.selectedTheme;
            try {
                FileUtils.TEMP_DIRECTORY_AUDIO.mkdirs();
                tempFile = new File(FileUtils.TEMP_DIRECTORY_AUDIO, "temp.mp3");
                if (tempFile.exists()) {
                    FileUtils.deleteFile(tempFile);
                }
                InputStream in = getResources().openRawResource(PVMWSThemes.getThemeMusic());
                FileOutputStream out = new FileOutputStream(tempFile);
                byte[] buff = new byte[1024];
                while (true) {
                    int read = in.read(buff);
                    if (read <= 0) {
                        break;
                    }
                    out.write(buff, 0, read);
                }
                MediaPlayer player = new MediaPlayer();
                player.setDataSource(tempFile.getAbsolutePath());
                player.setAudioStreamType(3);
                player.prepare();
                final MusicData PVMWSMusicData = new MusicData();
                PVMWSMusicData.track_data = tempFile.getAbsolutePath();
                player.setOnPreparedListener(new OnPreparedListener() {
                    public void onPrepared(MediaPlayer mp) {
                        PVMWSMusicData.track_duration = (long) mp.getDuration();
                        mp.stop();
                    }
                });
                PVMWSMusicData.track_Title = "temp";
                application.setMusicData(PVMWSMusicData);
            } catch (Exception e) {
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    reinitMusic();
                    lockRunnable.play();

                    lockRunnable.pause();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            lockRunnable.play();

                        }
                    }, 4800);
                }
            });
        }
    }

    class threadc1 extends Thread {

        threadc1() {
        }

        public void run() {
            KessiTheme PVMWSThemes = application.selectedTheme;
            try {
                FileUtils.TEMP_DIRECTORY_AUDIO.mkdirs();
                tempFile = new File(FileUtils.TEMP_DIRECTORY_AUDIO, "temp.mp3");
                if (tempFile.exists()) {
                    FileUtils.deleteFile(tempFile);
                }
                InputStream in = getResources().openRawResource(PVMWSThemes.getThemeMusic());
                FileOutputStream out = new FileOutputStream(tempFile);
                byte[] buff = new byte[1024];
                while (true) {
                    int read = in.read(buff);
                    if (read <= 0) {
                        break;
                    }
                    out.write(buff, 0, read);
                }
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                String custom = settings.getString("musiccustom", "abc");
                MediaPlayer player = new MediaPlayer();
                player.setDataSource(tempFile.getAbsolutePath());
                player.setAudioStreamType(3);
                player.prepare();

                final MusicData PVMWSMusicData = new MusicData();
                PVMWSMusicData.track_data = custom;
                player.setOnPreparedListener(new OnPreparedListener() {
                    public void onPrepared(MediaPlayer mp) {
                        PVMWSMusicData.track_duration = (long) mp.getDuration();
                        mp.stop();
                    }
                });
                // PVMWSMusicData.track_Title = "temp";
                application.setMusicData(PVMWSMusicData);
            } catch (Exception e) {
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    reinitMusic();
                    lockRunnable.play();

                    lockRunnable.pause();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            lockRunnable.play();

                        }
                    }, 4800);
                }
            });
        }
    }


    protected void onResume() {
        super.onResume();
    }

    public void change_Choice(int position){
        TextView tx;
        if(position != -1 ){
            seekBar_picktime.setProgress(Math.round(duration[position]*10));
            txt_Seekbar_picktime.setText(duration[position] + "s");
        }
        switch (old_duration_position){
            case -1:
                old_duration_position = position;
                return;
            case 0:
                tx = findViewById(R.id.txtsec1);
                break;
            case 1:
                tx = findViewById(R.id.txtsec15);
                break;
            case 2:
                tx = findViewById(R.id.txtsec2);
                break;
            case 3:
                tx = findViewById(R.id.txtsec25);
                break;
            case 4:
                tx = findViewById(R.id.txtsec3);
                break;
            case 5:
                tx = findViewById(R.id.txtsec35);
                break;
            case 6:
                tx = findViewById(R.id.txtsec4);
                break;
            case 7:
                tx = findViewById(R.id.txtsec45);
                break;
            default:
                tx = findViewById(R.id.txtsec5);
                break;
        }
        tx.setBackgroundResource(R.drawable.bg_duration_item);
        old_duration_position = position;
    }

    public void sec1() {

        change_Choice(0);
        seconds = duration[0].floatValue();
        txtsec1.setBackgroundResource(R.drawable.bg_duration_item_selected);
        setDuration();
    }

    public void sec15() {
        change_Choice(1);
        seconds = duration[1].floatValue();
        txtsec15.setBackgroundResource(R.drawable.bg_duration_item_selected);
        setDuration();
    }

    public void sec2() {
        change_Choice(2);
        seconds = duration[2].floatValue();
        txtsec2.setBackgroundResource(R.drawable.bg_duration_item_selected);
        setDuration();
    }

    public void sec25() {
        change_Choice(3);
        seconds = duration[3].floatValue();
        txtsec25.setBackgroundResource(R.drawable.bg_duration_item_selected);
        setDuration();
    }

    public void sec3() {
        change_Choice(4);
        seconds = duration[4].floatValue();
        txtsec3.setBackgroundResource(R.drawable.bg_duration_item_selected);
        setDuration();
    }

    public void sec35() {
        change_Choice(5);
        seconds = duration[5].floatValue();
        txtsec35.setBackgroundResource(R.drawable.bg_duration_item_selected);
        setDuration();
    }

    public void sec4() {
        change_Choice(6);
        seconds = duration[6].floatValue();
        txtsec4.setBackgroundResource(R.drawable.bg_duration_item_selected);
        setDuration();
    }

    public void sec45() {
        change_Choice(7);
        seconds = duration[7].floatValue();
        txtsec45.setBackgroundResource(R.drawable.bg_duration_item_selected);
        setDuration();
    }

    public void sec5() {
        change_Choice(8);
        seconds = duration[8].floatValue();
        txtsec5.setBackgroundResource(R.drawable.bg_duration_item_selected);
        setDuration();
    }

    void setDuration() {

        if (!AdManager.isloadFbMAXAd) {
            AdManager.adCounter++;
            AdManager.showInterAd(VideoThemeActivity.this, null,0);
        } else {
            AdManager.adCounter++;
            AdManager.showMaxInterstitial(VideoThemeActivity.this, null,0);
        }

        application.setSecond(seconds);

        total = (int) (((float) (arrayList.size() - 1)) * seconds);

        lockRunnable.stop();

    }


    class LockRunnable implements Runnable {
        ArrayList<ImageData> appList = new ArrayList();
        boolean isPause = false;

        class startplayclass implements AnimationListener {
            startplayclass() {
            }

            public void onAnimationStart(Animation animation) {
                ivPlayPause.setVisibility(View.VISIBLE);
 //               ivPlayPause1.setImageResource(R.drawable.pause2);

            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                ivPlayPause.setVisibility(View.GONE);
                //ivPlayPause1.setImageResource(R.drawable.pause2);
            }
        }

        class pauseclass implements AnimationListener {
            pauseclass() {
            }

            public void onAnimationStart(Animation animation) {
                ivPlayPause.setVisibility(View.VISIBLE);
 //               ivPlayPause1.setImageResource(R.drawable.small_play);
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
            }
        }

        LockRunnable() {
        }

        void setAppList(ArrayList<ImageData> appList) {
            appList.clear();
            appList.addAll(appList);
        }

        public void run() {
            displayImage();
            if (!isPause) {
                /*ms*/
                handler.postDelayed(lockRunnable, (long) Math.round(27.0f * seconds));
            }
        }

        public boolean isPause() {
            return isPause;
        }

        public void play() {
            isPause = false;
            playMusic();

            handler.postDelayed(lockRunnable, (long) Math.round(27.0f * seconds));
            Animation animation = new AlphaAnimation(1.0f, 0.0f);
            animation.setDuration(500);
            animation.setFillAfter(true);
            animation.setAnimationListener(new startplayclass());
            ivPlayPause.startAnimation(animation);
            if (llEdit.getVisibility() != View.VISIBLE) {
                llEdit.setVisibility(View.VISIBLE);
                application.isEditEnable = false;
                if (ServiceAnim.isImageComplate) {
                    Intent intent = new Intent(getApplicationContext(), ServiceAnim.class);
                    intent.putExtra(ServiceAnim.EXTRA_SELECTED_THEME, application.getCurrentTheme());
                    startService(intent);
                }
            }

        }

        public void pause() {
            isPause = true;
            pauseMusic();
            Animation animation = new AlphaAnimation(0.0f, 1.0f);
            animation.setDuration(500);
            animation.setFillAfter(true);
            ivPlayPause.startAnimation(animation);
            animation.setAnimationListener(new pauseclass());
        }

        public void stop() {
            pause();
            seekProgress = 0;
            try {


                if (mPlayer != null) {
                    mPlayer.stop();
                }
                reinitMusic();
            } catch (Exception e) {

            }
            seekBar.setProgress(seekProgress);

        }
    }

    void popuplayout_dialog() {

        RelativeLayout.LayoutParams params4 = new RelativeLayout.LayoutParams(
                getResources().getDisplayMetrics().widthPixels * 300 / 1080,
                getResources().getDisplayMetrics().heightPixels * 130 / 1920);
        params4.addRule(RelativeLayout.CENTER_IN_PARENT);
        img_btn_yes.setLayoutParams(params4);
        img_btn_no.setLayoutParams(params4);

        RelativeLayout.LayoutParams params5 = new RelativeLayout.LayoutParams(
                getResources().getDisplayMetrics().widthPixels * 840 / 1080,
                getResources().getDisplayMetrics().heightPixels * 500 / 1920);
        params5.addRule(RelativeLayout.CENTER_IN_PARENT);
        maindailog.setLayoutParams(params5);
    }
}
