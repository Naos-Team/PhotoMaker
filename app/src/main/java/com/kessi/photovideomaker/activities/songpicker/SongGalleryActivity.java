package com.kessi.photovideomaker.activities.songpicker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Media;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.URLUtil;
import android.widget.AbsoluteLayout.LayoutParams;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.downloader.Error;
import com.downloader.OnDownloadListener;
import com.downloader.PRDownloader;
import com.downloader.PRDownloaderConfig;
import com.kessi.photovideomaker.AppDatabase;
import com.kessi.photovideomaker.KessiApplication;
import com.kessi.photovideomaker.R;
import com.kessi.photovideomaker.activities.songpicker.roomdb.OnlineSongItem;
import com.kessi.photovideomaker.activities.songpicker.roomdb.SongDao;
import com.kessi.photovideomaker.activities.videoeditor.VideoThemeActivity;
import com.kessi.photovideomaker.util.AdManager;
import com.kessi.photovideomaker.util.KSUtil;
import com.xinlan.imageeditlibrary.editimage.utils.FileUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import vcarry.data.MusicData;
import vcarry.util.FileUtils;
import vcarry.util.SongMetadataReader;
import vcarry.view.MarkerView;
import vcarry.view.WaveformView;


public class SongGalleryActivity extends AppCompatActivity implements MarkerView.MarkerListener, WaveformView.WaveformListener {
    boolean isFromItemClick = false;
    boolean isPlaying = false;
    MusicAdapter mAdapter;
    AdapterOnlineMusic adapterOnlineMusic;
    String mArtist;
    boolean mCanSeekAccurately;
    float mDensity;
    MarkerView mEndMarker;
    int mEndPos;
    TextView mEndText;
    boolean mEndVisible;
    String mExtension;
    ImageView mFfwdButton;
    File mFile;
    String mFilename = "record";
    int mFlingVelocity;
    Handler mHandler;
    boolean mIsPlaying;
    boolean mKeyDown;
    int mLastDisplayedEndPos;
    int mLastDisplayedStartPos;
    boolean mLoadingKeepGoing;
    long mLoadingLastUpdateTime;
    int mMarkerBottomOffset;
    int mMarkerLeftInset;
    int mMarkerRightInset;
    int mMarkerTopOffset;
    int mMaxPos;
    ArrayList<MusicData> mPVMWSMusicData;
    RecyclerView mMusicList, rv_online_music;
    int mOffset;
    int mOffsetGoal;
    ImageView mPlayButton;
    int mPlayEndMsec;
    int mPlayStartMsec;
    int mPlayStartOffset;
    MediaPlayer mPlayer;
    LinearLayout loaderLay;
    String mRecordingFilename;
    Uri mRecordingUri;
    ImageView mRewindButton;
    SoundFile mSoundFile;
    MarkerView mStartMarker;
    int mStartPos;
    TextView mStartText;
    boolean mStartVisible;
    Runnable mTimerRunnable = new run1();
    String mTitle;
    boolean mTouchDragging;
    int mTouchInitialEndPos;
    int mTouchInitialOffset;
    int mTouchInitialStartPos;
    float mTouchStart;
    long mWaveformTouchStartMsec;
    WaveformView mPVMWSWaveformView;
    int mWidth;
    ImageView backimg_music, done_music;
    ImageButton mZoomInButton;
    MusicData selectedPVMWSMusicData;
    AppDatabase appDatabase;
    SongDao songDao;
    RelativeLayout btn_your_music, btn_online_music;
    private ArrayList<OnlineSongItem> arraylist_online = new ArrayList<>();

    ImageButton mZoomOutButton;
    private static final String PREFS_NAME = "preferenceName";
    public static boolean flagsong = false;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        appDatabase = AppDatabase.getInstance(this);
        songDao = appDatabase.songDao();

        PRDownloader.initialize(getApplicationContext());

        mRecordingFilename = null;
        mRecordingUri = null;
        mPlayer = null;
        mIsPlaying = false;
        mSoundFile = null;
        mKeyDown = false;
        mHandler = new Handler();
        setContentView(R.layout.activity_songgallery);

        setTV();

        loaderLay = findViewById(R.id.loaderLay);
        loaderLay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        bindView();
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mDensity = metrics.density;
        loadGui();
        init();
        new LoadOnlineSongAsync().execute();

        mHandler.postDelayed(mTimerRunnable, 100);

    }

    private class LoadOnlineSongAsync extends AsyncTask<Void, String, Boolean>{

        @Override
        protected Boolean doInBackground(Void... voids) {
            try{
                ArrayList<OnlineSongItem> song_list = new ArrayList<>();
                song_list.add(new OnlineSongItem(1, "Nothing-Like-You---Luke-Combs", "https://bestradiofree.com/country_music_radio/uploads/13516_Nothing-Like-You---Luke-Combs.mp3"));
                song_list.add(new OnlineSongItem(2, "Blessings - Florida Georgia Line", "https://bestradiofree.com/country_music_radio/uploads/Blessings - Florida Georgia Line.mp3"));
                song_list.add(new OnlineSongItem(3, "Finish Your Sentences - Carly Pearce. Michael Ray", "https://bestradiofree.com/country_music_radio/uploads/Finish Your Sentences - Carly Pearce. Michael Ray.mp3"));
                song_list.add(new OnlineSongItem(4, "Anything She Says (feat_ Seaforth) - Mitchell Tenpenny. Seaforth", "https://bestradiofree.com/country_music_radio/uploads/Anything She Says (feat_ Seaforth) - Mitchell Tenpenny. Seaforth.mp3"));

                song_list.forEach(item -> {
                    if(!songDao.isSongExist(item.getId())){
                        songDao.insertSong(item);
                    }
                });

                arraylist_online = new ArrayList<>(songDao.getAll());

                return true;

            }catch (Exception e){
                Log.e("SD", e.getMessage());
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            adapterOnlineMusic = new AdapterOnlineMusic(arraylist_online, new OnDownloadSongListener() {
                @Override
                public void onDownload(OnlineSongItem item, ProgressBar progressBar, ImageView btn_download, ImageView btn_downloaded) {

                    final Dialog dialog = new Dialog(SongGalleryActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);

                    dialog.setContentView(R.layout.dialog_alert);

                    TextView maintext = dialog.findViewById(R.id.maintext);
                    if(item.isDownload){
                        maintext.setText("You already have this song. Do you want to download again?");
                    }else{
                        maintext.setText("The song will download to your phone. Are your sure?");
                    }
                    Button img_btn_yes = dialog.findViewById(R.id.yes);
                    Button img_btn_no = dialog.findViewById(R.id.no);

                    img_btn_no.setOnClickListener(v ->{
                        dialog.dismiss();
                    });

                    img_btn_yes.setOnClickListener(v->{
                        dialog.dismiss();
                        startDownload(item, progressBar, btn_download, btn_downloaded);
                    });

                    dialog.show();
                }


            });

            rv_online_music.setLayoutManager(new LinearLayoutManager(SongGalleryActivity.this));
            rv_online_music.setAdapter(adapterOnlineMusic);
            adapterOnlineMusic.notifyDataSetChanged();

        }
    }

    private void startDownload(OnlineSongItem item, ProgressBar progressBar, ImageView btn_download, ImageView btn_downloaded){
        progressBar.setVisibility(View.VISIBLE);
        btn_download.setVisibility(View.GONE);
        btn_downloaded.setVisibility(View.GONE);
        String url = item.getUrl();
        String dirPath = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();

        String dirPath1 =  Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                + "/" + getResources().getString(R.string.app_name)
                + "/song" ;
//        String dirPath = FileUtils.APP_DIRECTORY.getPath() + "/song";
        int downloadId = PRDownloader.download(url, dirPath1, fileName(url))
                .build()
                .start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {

                        progressBar.setVisibility(View.GONE);
                        btn_download.setVisibility(View.GONE);
                        btn_downloaded.setVisibility(View.VISIBLE);

                        if(!item.isDownload){
                            item.setDownload(true);
                            int index = 0;
                            while (index < arraylist_online.size()){
                                if(arraylist_online.get(index).getId() == item.getId()){
                                    arraylist_online.get(index).isDownload = true;
                                    break;
                                }else{
                                    index++;
                                }
                            }
                            adapterOnlineMusic.notifyDataSetChanged();
                            AsyncTask.execute(new Runnable() {
                                @Override
                                public void run() {
                                    songDao.updateSong(item);
                                }
                            });
                        }

                        new LoadMusics().execute();

                        Toast.makeText(SongGalleryActivity.this, "Download success!", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(Error error) {
                        progressBar.setVisibility(View.GONE);
                        if(item.isDownload){
                            btn_download.setVisibility(View.GONE);
                            btn_downloaded.setVisibility(View.VISIBLE);
                        }else{
                            btn_download.setVisibility(View.VISIBLE);
                            btn_downloaded.setVisibility(View.GONE);
                        }
                        Toast.makeText(SongGalleryActivity.this, "Download failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private String fileName(String url){
        return URLUtil.guessFileName(url, url, getContentResolver().getType(Uri.parse(url)));
    }

    public void setTV(){
        LinearLayout adContainer = findViewById(R.id.banner_container);
        if (!AdManager.isloadFbMAXAd) {
            //admob
            AdManager.initAd(SongGalleryActivity.this);
            AdManager.loadBannerAd(SongGalleryActivity.this, adContainer);
            AdManager.loadInterAd(SongGalleryActivity.this);
        } else {
            //MAX + Fb banner Ads
            AdManager.initMAX(SongGalleryActivity.this);
            AdManager.maxBannerBg(SongGalleryActivity.this, adContainer, getColor(R.color.bottom_bg_color));
            AdManager.maxInterstital(SongGalleryActivity.this);
        }
    }

    @Override
    public void onBackPressed() {
        AdManager.adCounter++;
        if (AdManager.adCounter == AdManager.adDisplayCounter) {
            if (!AdManager.isloadFbMAXAd) {
                AdManager.showInterAd(SongGalleryActivity.this, null, 0);
            } else {
                AdManager.showMaxInterstitial(SongGalleryActivity.this, null, 0);
            }
        } else {

            if (flagsong) {
                setResult(0);

                if (isPlaying) {
                    mPlayer.release();
                }
                flagsong = false;
            }

            super.onBackPressed();
        }
    }

    void bindView() {
        mMusicList = (RecyclerView) findViewById(R.id.rvMusicList);
        rv_online_music = findViewById(R.id.rv_online_music);
        btn_online_music = findViewById(R.id.btn_online_music);
        btn_your_music = findViewById(R.id.btn_your_music);
        btn_your_music.setOnClickListener((v)->{
            KSUtil.Bounce(this, btn_your_music);
            btn_your_music.setBackgroundResource(R.drawable.shape_selected_button);
            btn_online_music.setBackgroundResource(R.drawable.shape_open_gallery);
            rv_online_music.setVisibility(View.GONE);
            mMusicList.setVisibility(View.VISIBLE);
        });
        btn_online_music.setOnClickListener((v -> {
            KSUtil.Bounce(this, btn_online_music);
            btn_online_music.setBackgroundResource(R.drawable.shape_selected_button);
            btn_your_music.setBackgroundResource(R.drawable.shape_open_gallery);
            rv_online_music.setVisibility(View.VISIBLE);
            mMusicList.setVisibility(View.GONE);

        }));

    }

    void init() {
        new LoadMusics().execute(new Void[0]);
    }

    void setUpRecyclerView() {
        mAdapter = new MusicAdapter(mPVMWSMusicData);
        mMusicList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        mMusicList.setItemAnimator(new DefaultItemAnimator());
        mMusicList.setAdapter(mAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    ArrayList<MusicData> getMusicFiles() {
        //TODO: fix dont read mp3 file
//        String dirPath0 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        Uri audioCollection;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            audioCollection = MediaStore.Audio.Media
                    .getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
        } else {
            audioCollection = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        }

        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0 ";

        ArrayList<MusicData> mPVMWSMusicData = new ArrayList();
        Cursor mCursor = getContentResolver().query(audioCollection, new String[]{"_id", "title", "_data", "_display_name", "duration"}, selection, null, "title ASC");
        int trackId = mCursor.getColumnIndex("_id");
        int trackTitle = mCursor.getColumnIndex("title");
        int trackDisplayName = mCursor.getColumnIndex("_display_name");
        int trackData = mCursor.getColumnIndex("_data");
        int trackDuration = mCursor.getColumnIndex("duration");
        if (mCursor != null && mCursor.moveToFirst()) {
            do {
                String path = mCursor.getString(trackData);
                if (isAudioFile(path)) {
                    MusicData musicData = new MusicData();
                    musicData.track_Id = mCursor.getLong(trackId);
                    musicData.track_Title = mCursor.getString(trackTitle);
                    musicData.track_data = path;
                    musicData.track_duration = mCursor.getLong(trackDuration);
                    musicData.track_displayName = mCursor.getString(trackDisplayName);
                    mPVMWSMusicData.add(musicData);
                }
            } while (mCursor.moveToNext());
        }

        return mPVMWSMusicData;
    }

    boolean isAudioFile(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        return path.endsWith(".mp3");
    }


    protected void onDestroy() {
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.stop();
        }
        mPlayer = null;
        if (mRecordingFilename != null) {
            try {
                if (!new File(mRecordingFilename).delete()) {
                    showFinalAlert(new Exception(), (int) R.string.delete_tmp_error);
                }
                getContentResolver().delete(mRecordingUri, null, null);
            } catch (Exception e) {
                showFinalAlert(e, (int) R.string.delete_tmp_error);
            }
        }
        super.onDestroy();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 62) {
            return super.onKeyDown(keyCode, event);
        }
        onPlay(mStartPos);
        return true;
    }

    public void waveformDraw() {
        mWidth = mPVMWSWaveformView.getMeasuredWidth();
        if (mOffsetGoal != mOffset && !mKeyDown) {
            updateDisplay();
        } else if (mIsPlaying) {
            updateDisplay();
        } else if (mFlingVelocity != 0) {
            updateDisplay();
        }
    }

    public void waveformTouchStart(float x) {
        mTouchDragging = true;
        mTouchStart = x;
        mTouchInitialOffset = mOffset;
        mFlingVelocity = 0;
        mWaveformTouchStartMsec = System.currentTimeMillis();
    }

    public void waveformTouchMove(float x) {
        mOffset = trap((int) (((float) mTouchInitialOffset) + (mTouchStart - x)));
        updateDisplay();
    }

    public void waveformTouchEnd() {
        mTouchDragging = false;
        mOffsetGoal = mOffset;
        if (System.currentTimeMillis() - mWaveformTouchStartMsec < 300) {
            if (mIsPlaying) {
                int seekMsec = mPVMWSWaveformView.pixelsToMillisecs((int) (mTouchStart + ((float) mOffset)));
                if (seekMsec < mPlayStartMsec || seekMsec >= mPlayEndMsec) {
                    handlePause();
                    return;
                } else {
                    mPlayer.seekTo(seekMsec - mPlayStartOffset);
                    return;
                }
            }
            onPlay((int) (mTouchStart + ((float) mOffset)));
        }
    }

    public void waveformFling(float vx) {
        mTouchDragging = false;
        mOffsetGoal = mOffset;
        mFlingVelocity = (int) (-vx);
        updateDisplay();
    }

    public void markerDraw() {
    }

    public void markerTouchStart(MarkerView marker, float x) {
        mTouchDragging = true;
        mTouchStart = x;
        mTouchInitialStartPos = mStartPos;
        mTouchInitialEndPos = mEndPos;
    }

    public void markerTouchMove(MarkerView marker, float x) {
        float delta = x - mTouchStart;
        if (marker == mStartMarker) {
            mStartPos = trap((int) (((float) mTouchInitialStartPos) + delta));
            mEndPos = trap((int) (((float) mTouchInitialEndPos) + delta));
        } else {
            mEndPos = trap((int) (((float) mTouchInitialEndPos) + delta));
            if (mEndPos < mStartPos) {
                mEndPos = mStartPos;
            }
        }
        updateDisplay();
    }

    public void markerTouchEnd(MarkerView marker) {
        mTouchDragging = false;
        if (marker == mStartMarker) {
            setOffsetGoalStart();
        } else {
            setOffsetGoalEnd();
        }
    }

    public void markerLeft(MarkerView marker, int velocity) {
        mKeyDown = true;
        if (marker == mStartMarker) {
            int saveStart = mStartPos;
            mStartPos = trap(mStartPos - velocity);
            mEndPos = trap(mEndPos - (saveStart - mStartPos));
            setOffsetGoalStart();
        }
        if (marker == mEndMarker) {
            if (mEndPos == mStartPos) {
                mStartPos = trap(mStartPos - velocity);
                mEndPos = mStartPos;
            } else {
                mEndPos = trap(mEndPos - velocity);
            }
            setOffsetGoalEnd();
        }
        updateDisplay();
    }

    public void markerRight(MarkerView marker, int velocity) {
        mKeyDown = true;
        if (marker == mStartMarker) {
            int saveStart = mStartPos;
            mStartPos += velocity;
            if (mStartPos > mMaxPos) {
                mStartPos = mMaxPos;
            }
            mEndPos += mStartPos - saveStart;
            if (mEndPos > mMaxPos) {
                mEndPos = mMaxPos;
            }
            setOffsetGoalStart();
        }
        if (marker == mEndMarker) {
            mEndPos += velocity;
            if (mEndPos > mMaxPos) {
                mEndPos = mMaxPos;
            }
            setOffsetGoalEnd();
        }
        updateDisplay();
    }

    public void markerEnter(MarkerView marker) {
    }

    public void markerKeyUp() {
        mKeyDown = false;
        updateDisplay();
    }

    public void markerFocus(MarkerView marker) {
        mKeyDown = false;
        if (marker == mStartMarker) {
            setOffsetGoalStartNoUpdate();
        } else {
            setOffsetGoalEndNoUpdate();
        }
        mHandler.postDelayed(new Runnable() {
            public void run() {
                updateDisplay();
            }
        }, 100);
    }

    public static void onAbout(Activity activity) {
        new Builder(activity).setTitle(R.string.about_title).setMessage(R.string.about_text)
                .setPositiveButton(R.string.alert_ok_button, null).setCancelable(false).show();
    }

    void loadGui() {


        mMarkerLeftInset = (int) (46.0f * mDensity);
        mMarkerRightInset = (int) (48.0f * mDensity);
        mMarkerTopOffset = (int) (mDensity * 10.0f);
        mMarkerBottomOffset = (int) (mDensity * 10.0f);
        mStartText = (TextView) findViewById(R.id.starttext);
        mEndText = (TextView) findViewById(R.id.endtext);
        mPlayButton = (ImageView) findViewById(R.id.play);
        mRewindButton = (ImageView) findViewById(R.id.rew);
        mFfwdButton = (ImageView) findViewById(R.id.ffwd);
        mPVMWSWaveformView = (WaveformView) findViewById(R.id.waveform);
        mStartMarker = (MarkerView) findViewById(R.id.startmarker);
        mEndMarker = (MarkerView) findViewById(R.id.endmarker);
        backimg_music = (ImageView) findViewById(R.id.backimg_music);
        done_music = (ImageView) findViewById(R.id.done_music);

        RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(
                getResources().getDisplayMetrics().widthPixels * 210 / 1080,
                getResources().getDisplayMetrics().heightPixels * 100 / 1920);
        params3.addRule(RelativeLayout.CENTER_IN_PARENT);
        mStartText.setLayoutParams(params3);
        mEndText.setLayoutParams(params3);

        RelativeLayout.LayoutParams params31 = new RelativeLayout.LayoutParams(
                getResources().getDisplayMetrics().widthPixels * 60 / 1080,
                getResources().getDisplayMetrics().heightPixels * 60 / 1920);
        params31.addRule(RelativeLayout.CENTER_IN_PARENT);
        mRewindButton.setLayoutParams(params31);
        mFfwdButton.setLayoutParams(params31);

        RelativeLayout.LayoutParams params40 = new RelativeLayout.LayoutParams(
                getResources().getDisplayMetrics().widthPixels * 94 / 1080,
                getResources().getDisplayMetrics().heightPixels * 94 / 1920);
        params40.addRule(RelativeLayout.CENTER_IN_PARENT);
        mPlayButton.setLayoutParams(params40);


        mStartText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mStartText.hasFocus()) {
                    try {
                        mStartPos = mPVMWSWaveformView.secondsToPixels(Double.parseDouble(mStartText.getText()
                                .toString()));
                        updateDisplay();
                    } catch (NumberFormatException e) {
                    }
                }
                if (mEndText.hasFocus()) {
                    try {
                        mEndPos = mPVMWSWaveformView.secondsToPixels(Double.parseDouble(mEndText.getText()
                                .toString()));
                        updateDisplay();
                    } catch (NumberFormatException e2) {
                    }
                }
            }
        });


        mEndText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mStartText.hasFocus()) {
                    try {
                        mStartPos = mPVMWSWaveformView.secondsToPixels(Double.parseDouble(mStartText.getText()
                                .toString()));
                        updateDisplay();
                    } catch (NumberFormatException e) {
                    }
                }
                if (mEndText.hasFocus()) {
                    try {
                        mEndPos = mPVMWSWaveformView.secondsToPixels(Double.parseDouble(mEndText.getText()
                                .toString()));
                        updateDisplay();
                    } catch (NumberFormatException e2) {
                    }
                }

            }
        });

        mPlayButton.setOnClickListener(v -> {
            KSUtil.Bounce(this, mPlayButton);
            onPlay(mStartPos);
        });

        mRewindButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsPlaying) {
                    int newPos = mPlayer.getCurrentPosition() + -5000;
                    if (newPos < mPlayStartMsec) {
                        newPos = mPlayStartMsec;
                    }
                    mPlayer.seekTo(newPos);
                    return;
                }
                mStartMarker.requestFocus();
                markerFocus(mStartMarker);
            }
        });

        mFfwdButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mIsPlaying) {
                    int newPos = mPlayer.getCurrentPosition() + 5000;
                    if (newPos > mPlayEndMsec) {
                        newPos = mPlayEndMsec;
                    }
                    mPlayer.seekTo(newPos);
                    return;
                }
                mEndMarker.requestFocus();
                markerFocus(mEndMarker);
            }
        });
        enableDisableButtons();

        mPVMWSWaveformView.setListener(this);
        mMaxPos = 0;
        mLastDisplayedStartPos = -1;
        mLastDisplayedEndPos = -1;
        if (mSoundFile != null) {
            mPVMWSWaveformView.setSoundFile(mSoundFile);
            mPVMWSWaveformView.recomputeHeights(mDensity);
            mMaxPos = mPVMWSWaveformView.maxPos();
        }

        mStartMarker.setListener(this);
        mStartMarker.setAlpha(255);
        mStartMarker.setFocusable(true);
        mStartMarker.setFocusableInTouchMode(true);
        mStartVisible = true;

        mEndMarker.setListener(this);
        mEndMarker.setAlpha(255);
        mEndMarker.setFocusable(true);
        mEndMarker.setFocusableInTouchMode(true);
        mEndVisible = true;
        updateDisplay();

        backimg_music.setOnClickListener(v -> {
            KSUtil.Bounce(SongGalleryActivity.this, backimg_music);
            onBackPressed();
        });

        done_music.setOnClickListener(v -> {
            KSUtil.Bounce(SongGalleryActivity.this, done_music);
            if (flagsong) {
                onSave();
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("musiccustom", selectedPVMWSMusicData.track_data);
                editor.commit();
                KessiApplication.getInstance().setMusicData(selectedPVMWSMusicData);
            }
            else
            {

            }
        });

    }

    void loadFromFile() {

        mFile = new File(mFilename);
        mExtension = getExtensionFromFilename(mFilename);
        SongMetadataReader metadataReader = new SongMetadataReader(this, mFilename);
        mTitle = metadataReader.mTitle;
        mArtist = metadataReader.mArtist;
        String titleLabel = mTitle;
        if (mArtist != null && mArtist.length() > 0) {
            titleLabel = new StringBuilder(String.valueOf(titleLabel)).append(" - ").append(mArtist).toString();
        }
        setTitle(titleLabel);
        mLoadingLastUpdateTime = System.currentTimeMillis();
        mLoadingKeepGoing = true;
        loaderLay.setVisibility(View.VISIBLE);
        final SoundFile.ProgressListener listener = new SoundFile.ProgressListener() {
            public boolean reportProgress(double fractionComplete) {
                long now = System.currentTimeMillis();
                if (now - mLoadingLastUpdateTime > 100) {
                    mLoadingLastUpdateTime = now;
                }
                return mLoadingKeepGoing;
            }
        };
        mCanSeekAccurately = false;
        new Thread() {
            public void run() {
                mCanSeekAccurately = SeekTest.CanSeekAccurately(getPreferences(0));
                System.out.println("Seek test done, creating media player.");
                try {
                    MediaPlayer player = new MediaPlayer();
                    player.setDataSource(mFile.getAbsolutePath());
                    player.setAudioStreamType(3);
                    player.prepare();
                    mPlayer = player;
                } catch (final IOException e) {
                    mHandler.post(new Runnable() {
                        public void run() {
                            handleFatalError("ReadError", getResources().getText(R.string.read_error), e);
                        }
                    });
                }
            }
        }.start();
        new Thread() {

            class runn implements Runnable {
                runn() {
                }

                public void run() {
                    finishOpeningSoundFile();
                }
            }

            public void run() {
                try {
                    mSoundFile = SoundFile.create(mFile.getAbsolutePath(), listener);
                    if (mSoundFile == null) {
                        String err;
//                        mProgressDialog.dismiss();
                        loaderLay.setVisibility(View.GONE);
                        flagsong = true;
                        String[] components = mFile.getName().toLowerCase().split("\\.");
                        if (components.length < 2) {
                            err = getResources().getString(R.string.no_extension_error);
                        } else {
                            err = new StringBuilder(String.valueOf(getResources()
                                    .getString(R.string.bad_extension_error)))
                                    .append(" ").append(components[components.length - 1]).toString();
                        }
                        final String finalErr = err;
                        mHandler.post(new Runnable() {
                            public void run() {
                                handleFatalError("UnsupportedExtension", finalErr, new Exception());
                            }
                        });
                        return;
                    }
//                    mProgressDialog.dismiss();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loaderLay.setVisibility(View.GONE);
                        }
                    });

                    flagsong = true;
                    if (mLoadingKeepGoing) {
                        mHandler.post(new runn());
                    } else {
                        finish();
                    }
                } catch (final Exception e) {
//                    mProgressDialog.dismiss();
                    loaderLay.setVisibility(View.GONE);
                    flagsong = true;
                    e.printStackTrace();
                    mHandler.post(new Runnable() {
                        public void run() {
                            handleFatalError("ReadError", getResources().getText(R.string.read_error), e);
                        }
                    });
                }
            }
        }.start();
    }

    void finishOpeningSoundFile() {
        mPVMWSWaveformView.setSoundFile(mSoundFile);
        mPVMWSWaveformView.recomputeHeights(mDensity);
        mMaxPos = mPVMWSWaveformView.maxPos();
        mLastDisplayedStartPos = -1;
        mLastDisplayedEndPos = -1;
        mTouchDragging = false;
        mOffset = 0;
        mOffsetGoal = 0;
        mFlingVelocity = 0;
        resetPositions();
        if (mEndPos > mMaxPos) {
            mEndPos = mMaxPos;
        }
        updateDisplay();
        if (isFromItemClick) {
            onPlay(mStartPos);
        }
    }

    synchronized void updateDisplay() {
        if (mIsPlaying) {
            int now = mPlayer.getCurrentPosition() + mPlayStartOffset;
            int frames = mPVMWSWaveformView.millisecsToPixels(now);
            mPVMWSWaveformView.setPlayback(frames);
            setOffsetGoalNoUpdate(frames - (mWidth / 2));
            if (now >= mPlayEndMsec) {
                handlePause();
            }
        }
        if (!mTouchDragging) {
            int offsetDelta;
            if (mFlingVelocity != 0) {
                offsetDelta = mFlingVelocity / 30;
                if (mFlingVelocity > 80) {
                    mFlingVelocity -= 80;
                } else if (mFlingVelocity < -80) {
                    mFlingVelocity += 80;
                } else {
                    mFlingVelocity = 0;
                }
                mOffset += offsetDelta;
                if (mOffset + (mWidth / 2) > mMaxPos) {
                    mOffset = mMaxPos - (mWidth / 2);
                    mFlingVelocity = 0;
                }
                if (mOffset < 0) {
                    mOffset = 0;
                    mFlingVelocity = 0;
                }
                mOffsetGoal = mOffset;
            } else {
                offsetDelta = mOffsetGoal - mOffset;
                if (offsetDelta > 10) {
                    offsetDelta /= 10;
                } else if (offsetDelta > 0) {
                    offsetDelta = 1;
                } else if (offsetDelta < -10) {
                    offsetDelta /= 10;
                } else if (offsetDelta < 0) {
                    offsetDelta = -1;
                } else {
                    offsetDelta = 0;
                }
                mOffset += offsetDelta;
            }
        }
        mPVMWSWaveformView.setParameters(mStartPos, mEndPos, mOffset);
        mPVMWSWaveformView.invalidate();
        mStartMarker.setContentDescription("Start Marker" + " " + formatTime(mStartPos));
        mEndMarker.setContentDescription("End Marker" + " " + formatTime(mEndPos));
        int startX = (mStartPos - mOffset) - mMarkerLeftInset;
        if (mStartMarker.getWidth() + startX < 0) {
            if (mStartVisible) {
                mStartMarker.setAlpha(0);
                mStartVisible = false;
            }
            startX = 0;
        } else if (!mStartVisible) {
            mHandler.postDelayed(new Runnable() {
                public void run() {
                    mStartVisible = true;
                    mStartMarker.setAlpha(255);
                }
            }, 0);
        }
        int endX = ((mEndPos - mOffset) - mEndMarker.getWidth()) + mMarkerRightInset;
        if (mEndMarker.getWidth() + endX < 0) {
            if (mEndVisible) {
                mEndMarker.setAlpha(0);
                mEndVisible = false;
            }
            endX = 0;
        } else if (!mEndVisible) {
            mHandler.postDelayed(new Runnable() {
                public void run() {
                    mEndVisible = true;
                    mEndMarker.setAlpha(255);
                }
            }, 0);
        }
        mStartMarker.setLayoutParams(new LayoutParams(-2, -2, startX, mMarkerTopOffset));
        mEndMarker.setLayoutParams(new LayoutParams(-2, -2, endX, (mPVMWSWaveformView.getMeasuredHeight() - mEndMarker.getHeight()) - mMarkerBottomOffset));
    }

    void enableDisableButtons() {
        if (mIsPlaying) {
            mPlayButton.setImageResource(R.drawable.pause2);
            mPlayButton.setContentDescription("Stop");
            return;
        }
        mPlayButton.setImageResource(R.drawable.play);
        mPlayButton.setContentDescription("Play");
    }

    void resetPositions() {
        mStartPos = mPVMWSWaveformView.secondsToPixels(3.0d);
        mEndPos = mPVMWSWaveformView.secondsToPixels((double) mMaxPos);
    }

    int trap(int pos) {
        if (pos < 0) {
            return 0;
        }
        if (pos > mMaxPos) {
            return mMaxPos;
        }
        return pos;
    }

    void setOffsetGoalStart() {
        setOffsetGoal(mStartPos - (mWidth / 2));
    }

    void setOffsetGoalStartNoUpdate() {
        setOffsetGoalNoUpdate(mStartPos - (mWidth / 2));
    }

    void setOffsetGoalEnd() {
        setOffsetGoal(mEndPos - (mWidth / 2));
    }

    void setOffsetGoalEndNoUpdate() {
        setOffsetGoalNoUpdate(mEndPos - (mWidth / 2));
    }

    void setOffsetGoal(int offset) {
        setOffsetGoalNoUpdate(offset);
        updateDisplay();
    }

    void setOffsetGoalNoUpdate(int offset) {
        if (!mTouchDragging) {
            mOffsetGoal = offset;
            if (mOffsetGoal + (mWidth / 2) > mMaxPos) {
                mOffsetGoal = mMaxPos - (mWidth / 2);
            }
            if (mOffsetGoal < 0) {
                mOffsetGoal = 0;
            }
        }
    }

    String formatTime(int pixels) {
        if (mPVMWSWaveformView == null || !mPVMWSWaveformView.isInitialized()) {
            return "";
        }
        return formatDecimal(mPVMWSWaveformView.pixelsToSeconds(pixels));
    }

    String formatDecimal(double x) {
        int xWhole = (int) x;
        int xFrac = (int) ((100.0d * (x - ((double) xWhole))) + 0.5d);
        if (xFrac >= 100) {
            xWhole++;
            xFrac -= 100;
            if (xFrac < 10) {
                xFrac *= 10;
            }
        }
        if (xFrac < 10) {
            return new StringBuilder(String.valueOf(xWhole)).append(".0").append(xFrac).toString();
        }
        return new StringBuilder(String.valueOf(xWhole)).append(".").append(xFrac).toString();
    }

    synchronized void handlePause() {
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.pause();
        }
        mPVMWSWaveformView.setPlayback(-1);
        mIsPlaying = false;
        enableDisableButtons();
    }

    synchronized void onPlay(int startPosition) {
        if (mIsPlaying) {
            handlePause();
        } else if (!(mPlayer == null || startPosition == -1)) {
            try {
                mPlayStartMsec = mPVMWSWaveformView.pixelsToMillisecs(startPosition);
                if (startPosition < mStartPos) {
                    mPlayEndMsec = mPVMWSWaveformView.pixelsToMillisecs(mStartPos);
                } else if (startPosition > mEndPos) {
                    mPlayEndMsec = mPVMWSWaveformView.pixelsToMillisecs(mMaxPos);
                } else {
                    mPlayEndMsec = mPVMWSWaveformView.pixelsToMillisecs(mEndPos);
                }
                mPlayStartOffset = 0;
                int startFrame = mPVMWSWaveformView.secondsToFrames(((double) mPlayStartMsec) * 0.001d);
                int endFrame = mPVMWSWaveformView.secondsToFrames(((double) mPlayEndMsec) * 0.001d);
                int startByte = mSoundFile.getSeekableFrameOffset(startFrame);
                int endByte = mSoundFile.getSeekableFrameOffset(endFrame);
                if (mCanSeekAccurately && startByte >= 0 && endByte >= 0) {
                    try {
                        mPlayer.reset();
                        mPlayer.setAudioStreamType(3);
                        mPlayer.setDataSource(new FileInputStream(mFile.getAbsolutePath()).getFD(), (long) startByte, (long) (endByte - startByte));
                        mPlayer.prepare();
                        mPlayStartOffset = mPlayStartMsec;
                    } catch (Exception e) {
                        System.out.println("Exception trying to play file subset");
                        mPlayer.reset();
                        mPlayer.setAudioStreamType(3);
                        mPlayer.setDataSource(mFile.getAbsolutePath());
                        mPlayer.prepare();
                        mPlayStartOffset = 0;
                    }
                }
                mPlayer.setOnCompletionListener(new OnCompletionListener() {
                    public synchronized void onCompletion(MediaPlayer arg0) {
                        handlePause();
                    }
                });
                mIsPlaying = true;
                if (mPlayStartOffset == 0) {
                    mPlayer.seekTo(mPlayStartMsec);
                }
                mPlayer.start();
                updateDisplay();
                enableDisableButtons();
            } catch (Exception e2) {
                showFinalAlert(e2, (int) R.string.play_error);
            }
        }
    }

    void showFinalAlert(Exception e, CharSequence message) {
        CharSequence title;
        if (e != null) {
            Log.e("", "Error: " + message);
            Log.e("", getStackTrace(e));
            title = getResources().getText(R.string.alert_title_failure);
            setResult(0, new Intent());
        } else {
            Log.i("Ringdroid", "Success: " + message);
            title = "Success";
        }
        new Builder(this, 0)
                .setTitle(title).setMessage(message).setPositiveButton(R.string.alert_ok_button, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                finish();
            }
        }).setCancelable(false).show();
    }

    void showFinalAlert(Exception e, int messageResourceId) {
        showFinalAlert(e, getResources().getText(messageResourceId));
    }

    String makeRingtoneFilename(CharSequence title, String extension) {
        FileUtils.TEMP_DIRECTORY_AUDIO.mkdirs();
        File tempFile = new File(FileUtils.TEMP_DIRECTORY_AUDIO, title + extension);
        if (tempFile.exists()) {
            FileUtils.deleteFile(tempFile);
        }
        return tempFile.getAbsolutePath();
    }

    void saveRingtone(CharSequence title) {
        final String outPath = makeRingtoneFilename(title, mExtension);
        if (outPath == null) {
            showFinalAlert(new Exception(), (int) R.string.no_unique_filename);
            return;
        }
        double startTime = mPVMWSWaveformView.pixelsToSeconds(mStartPos);
        double endTime = mPVMWSWaveformView.pixelsToSeconds(mEndPos);
        final int startFrame = mPVMWSWaveformView.secondsToFrames(startTime);
        final int endFrame = mPVMWSWaveformView.secondsToFrames(endTime);
        final int duration = (int) ((endTime - startTime) + 0.5d);
        loaderLay.setVisibility(View.VISIBLE);
        final CharSequence charSequence = title;
        new Thread() {

            class progressListener implements SoundFile.ProgressListener {
                progressListener() {
                }

                public boolean reportProgress(double frac) {
                    return true;
                }
            }

            public void run() {
                final File outFile = new File(outPath);
                try {
                    mSoundFile.WriteFile(outFile, startFrame, endFrame - startFrame);
                    SoundFile.create(outPath, new progressListener());
                    final String str = outPath;
                    final int i = duration;
                    mHandler.post(new Runnable() {
                        public void run() {
                            afterSavingRingtone(charSequence, str, outFile, i);
                        }
                    });
                } catch (Exception e) {
                    CharSequence errorMessage;
                    Exception e2 = e;
                    loaderLay.setVisibility(View.GONE);
                    if (e2.getMessage().equals("No space left on device")) {
                        errorMessage = getResources().getText(R.string.no_space_error);
                        e2 = null;
                    } else {
                        errorMessage = getResources().getText(R.string.write_error);
                        Log.e("Err", e2.getMessage());
                    }
                    final CharSequence finalErrorMessage = errorMessage;
                    final Exception finalException = e2;
                    mHandler.post(new Runnable() {
                        public void run() {
                            handleFatalError("WriteError", finalErrorMessage, finalException);
                        }
                    });
                }
            }
        }.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    void afterSavingRingtone(CharSequence title, String outPath, File outFile, int duration) {
        if (outFile.length() <= 512) {
            outFile.delete();
            new Builder(this).setTitle(R.string.alert_title_failure)
                    .setMessage(R.string.too_small_error).setPositiveButton(R.string.alert_ok_button, null).setCancelable(false).show();
            return;
        }
        long fileSize = outFile.length();
        String artist = (String) getResources().getText(R.string.artist_name);
        ContentValues values = new ContentValues();
        values.put("_data", outPath);
        values.put("title", title.toString());
        values.put("_size", Long.valueOf(fileSize));
        values.put("mime_type", "audio/mpeg");
        values.put("artist", artist);
        values.put("duration", Integer.valueOf(duration));
        values.put("is_music", Boolean.valueOf(true));
        Log.e("audio", "duaration is " + duration);

        try{
            Uri uri = Media.getContentUriForPath(outPath);
            ContentResolver cs = getContentResolver();

            Cursor c = getContentResolver().query(uri, null, null, null);
            if (c.getCount() >= 1) {
                // already inserted, update this row or do sth else
                setResult(-1, new Intent().setData(uri));
            } else {
                // row does not exist, you can insert or do sth else
                Uri uriData = cs.insert(uri, values);
                setResult(-1, new Intent().setData(uriData));
            }



        }catch (Exception e){
            Log.e("Err", e.getMessage());
        }

        selectedPVMWSMusicData.track_data = outPath;

        selectedPVMWSMusicData.track_duration = (long) (duration * 1000);
        KessiApplication.getInstance().setMusicData(selectedPVMWSMusicData);
        loaderLay.setVisibility(View.GONE);

        finish();


    }

    void handleFatalError(CharSequence errorInternalName, CharSequence errorString, Exception exception) {
        Log.i("Ringdroid", "handleFatalError");
    }

    void onSave() {
        if (mIsPlaying) {
            handlePause();
        }
        saveRingtone("temp");
    }

    void enableZoomButtons() {
        mZoomInButton.setEnabled(mPVMWSWaveformView.canZoomIn());
        mZoomOutButton.setEnabled(mPVMWSWaveformView.canZoomOut());
    }

    String getStackTrace(Exception e) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        e.printStackTrace(new PrintWriter(stream, true));
        return stream.toString();
    }

    String getExtensionFromFilename(String filename) {
        return filename.substring(filename.lastIndexOf(46), filename.length());
    }



    class run1 implements Runnable {
        run1() {
        }

        public void run() {
            if (!(mStartPos == mLastDisplayedStartPos || mStartText.hasFocus())) {
                mStartText.setText(formatTime(mStartPos));
                mLastDisplayedStartPos = mStartPos;
            }
            if (!(mEndPos == mLastDisplayedEndPos || mEndText.hasFocus())) {
                mEndText.setText(formatTime(mEndPos));
                mLastDisplayedEndPos = mEndPos;
            }
            mHandler.postDelayed(mTimerRunnable, 100);
        }
    }



    public class LoadMusics extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            super.onPreExecute();
            loaderLay.setVisibility(View.VISIBLE);
        }

        protected Void doInBackground(Void... paramVarArgs) {
            mPVMWSMusicData = getMusicFiles();
            if (mPVMWSMusicData.size() > 0) {
                selectedPVMWSMusicData = (MusicData) mPVMWSMusicData.get(0);
                mFilename = selectedPVMWSMusicData.getTrack_data();
            } else {
                mFilename = "record";
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
//            pDialog.dismiss();
            loaderLay.setVisibility(View.GONE);
            if (!mFilename.equals("record")) {
                setUpRecyclerView();
                loadFromFile();
                // supportInvalidateOptionsMenu();
            } else if (mPVMWSMusicData.size() > 0) {
                Toast.makeText(getApplicationContext(),
                        "No Music found in device\nPlease add music in sdCard", Toast.LENGTH_LONG).show();
            }
        }
    }

    public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.Holder> {
        SparseBooleanArray booleanArray = new SparseBooleanArray();
        RadioButton mButton;
        int mSelectedChoice = 0;
        ArrayList<MusicData> PVMWSMusicData;

        public class Holder extends RecyclerView.ViewHolder {
            public TextView musicName, tv_time;
            ImageView iv;
            public Holder(View v) {
                super(v);
                iv = v.findViewById(R.id.iv);
                tv_time = v.findViewById(R.id.tv_time);
                musicName = (TextView) v.findViewById(R.id.musicName);
            }
        }

        public MusicAdapter(ArrayList<MusicData> mPVMWSMusicData) {
            PVMWSMusicData = mPVMWSMusicData;
            booleanArray.put(0, true);
        }

        public Holder onCreateViewHolder(ViewGroup parent, int paramInt) {
            return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.pvmws_music_list_items, parent, false));
        }

        public void onBindViewHolder(Holder holder, @SuppressLint("RecyclerView") final int pos) {

            holder.musicName.setText(((MusicData) PVMWSMusicData.get(pos)).track_displayName);

            if (mSelectedChoice == pos){
                holder.musicName.setTextColor(getColor(R.color.music_selected));
                holder.musicName.setTypeface(null, Typeface.BOLD);
                holder.iv.setVisibility(View.VISIBLE);
            }else {
                holder.musicName.setTypeface(null, Typeface.NORMAL);
                holder.musicName.setTextColor(getColor(R.color.text_music));
                holder.iv.setVisibility(View.GONE);
            }

            double sec = (int) (PVMWSMusicData.get(pos).track_duration / 1000);

            int minutes = (int)((sec % 3600) / 60);
            int seconds = (int) (sec % 60);

            String timeString = String.format("%02d:%02d", minutes, seconds);

            holder.tv_time.setText(timeString);

            holder.musicName.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    booleanArray.clear();
                    booleanArray.put(pos, true);
                    onPlay(-1);
                    playMusic(pos);
                    isFromItemClick = true;
                    notifyDataSetChanged();
                }
            });
        }

        public int getItemCount() {
            return PVMWSMusicData.size();
        }

        public void playMusic(int pos) {
            if (mSelectedChoice != pos) {
                selectedPVMWSMusicData = (MusicData) mPVMWSMusicData.get(pos);
                mFilename = selectedPVMWSMusicData.getTrack_data();
                loadFromFile();
            }
            mSelectedChoice = pos;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
