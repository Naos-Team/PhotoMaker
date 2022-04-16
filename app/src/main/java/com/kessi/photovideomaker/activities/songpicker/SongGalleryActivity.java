package com.kessi.photovideomaker.activities.songpicker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.AbsoluteLayout.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.kessi.photovideomaker.KessiApplication;
import com.kessi.photovideomaker.R;
import com.kessi.photovideomaker.util.AdManager;
import com.kessi.photovideomaker.util.KSUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import vcarry.data.MusicData;
import vcarry.util.FileUtils;
import vcarry.util.SongMetadataReader;
import vcarry.view.MarkerView;
import vcarry.view.WaveformView;


public class SongGalleryActivity extends AppCompatActivity implements MarkerView.MarkerListener, WaveformView.WaveformListener {
    boolean isFromItemClick = false;
    boolean isPlaying = false;
    MusicAdapter mAdapter;
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
    RecyclerView mMusicList;
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

    ImageButton mZoomOutButton;
    private static final String PREFS_NAME = "preferenceName";
    public static boolean flagsong = false;



    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);


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

        mHandler.postDelayed(mTimerRunnable, 100);

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

    ArrayList<MusicData> getMusicFiles() {
        ArrayList<MusicData> mPVMWSMusicData = new ArrayList();
        Cursor mCursor = getContentResolver().query(Media.EXTERNAL_CONTENT_URI, new String[]{"_id", "title", "_data", "_display_name", "duration"}, "is_music != 0", null, "title ASC");
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
        setResult(-1, new Intent().setData(getContentResolver().insert(Media.getContentUriForPath(outPath), values)));
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
            public TextView musicName;
            public ImageView iv;
            public Holder(View v) {
                super(v);
                musicName = (TextView) v.findViewById(R.id.musicName);
                iv = v.findViewById(R.id.iv);
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
                holder.iv.setImageResource(R.drawable.music_press);
                holder.musicName.setTextColor(getColor(R.color.music_selected));
            }else {
                holder.iv.setImageResource(R.drawable.smusic_ic);
                holder.musicName.setTextColor(getColor(R.color.text_music));
            }

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
