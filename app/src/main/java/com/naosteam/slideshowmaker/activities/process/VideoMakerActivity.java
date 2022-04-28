package com.naosteam.slideshowmaker.activities.process;

import android.content.Intent;
import android.media.MediaScannerConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.arthenica.ffmpegkit.FFmpegKit;
import com.arthenica.ffmpegkit.FFmpegKitConfig;
import com.arthenica.ffmpegkit.FFmpegSession;
import com.arthenica.ffmpegkit.ReturnCode;
import com.naosteam.slideshowmaker.KessiApplication;
import com.naosteam.slideshowmaker.R;
import com.naosteam.slideshowmaker.activities.VideoPlayerActivity;
import com.naosteam.slideshowmaker.activities.videoeditor.VideoThemeActivity;
import com.naosteam.slideshowmaker.util.AdManager;
import com.naosteam.slideshowmaker.util.KSUtil;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import vcarry.util.FileUtils;
import vcarry.util.Utils;

public class VideoMakerActivity extends AppCompatActivity {
    TextView perTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_maker);
        setTV();

        perTV = findViewById(R.id.perTV);
        new ProcessVideo().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void setTV(){
        FrameLayout nativeContainer = findViewById(R.id.nativeContainer);
        FrameLayout nativeContainerMAX = findViewById(R.id.nativeContainerMAX);

        if (!AdManager.isloadFbMAXAd) {
            //admob
            AdManager.initAd(VideoMakerActivity.this);
            AdManager.loadInterAd(VideoMakerActivity.this);
            AdManager.loadNativeAd(VideoMakerActivity.this, nativeContainer);
        } else {
            //MAX + Fb banner Ads
            AdManager.initMAX(VideoMakerActivity.this);
            AdManager.maxInterstital(VideoMakerActivity.this);
            AdManager.loadNativeMAX(VideoMakerActivity.this, nativeContainerMAX);
        }
    }

    public class ProcessVideo extends AsyncTask<Integer, Integer, List<String>> {
        File imgDir;
        String cmd;

        @Override
        protected void onPreExecute() {

        }


        protected List<String> doInBackground(Integer... params) {

            new File(FileUtils.TEMP_DIRECTORY, "video.txt").delete();
            if (!VideoThemeActivity.logFile.exists()) {
                try {
                    VideoThemeActivity.logFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            imgDir = FileUtils.getImageDirectory(VideoThemeActivity.application.selectedTheme
                    .toString());
            Calendar c = Calendar.getInstance();

            SimpleDateFormat df = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
            String formattedDate = df.format(c.getTime());

            VideoThemeActivity.outputPath = VideoThemeActivity.folderPath;
            File file = new File(VideoThemeActivity.outputPath);
            if (!file.exists())
                file.mkdirs();
            VideoThemeActivity.outputPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    + "/" + getResources().getString(R.string.app_name)
                    + "/" + "video_" + formattedDate + ".mp4";
            int finalwidth = KessiApplication.VIDEO_WIDTH;

            VideoThemeActivity.duration1 = Float.parseFloat(""
                    + String.valueOf(VideoThemeActivity.total)
                    .replace(" Seconds", "")) * 1000;

            if (VideoThemeActivity.mDuration < VideoThemeActivity.duration1) {

                if (Utils.framePostion > -1) {
                    Log.v("withframe", "withframe");

                    cmd = "-y&-r&22.0/" + VideoThemeActivity.application.getSecond() + "&-i&" + imgDir.getAbsolutePath() + "/img%5d.jpg" + "&-i&"
                            + FileUtils.frameFile.getAbsolutePath() + "&-ss&" + 0
                            + "&-i&" + VideoThemeActivity.application.getMusicData().track_data + "&-filter_complex&[1]scale="
                            + finalwidth +
                            ":-1[b];[0:v][b]overlay&-vcodec&libx264&-acodec&aac&-r&30&-t&" +
                            VideoThemeActivity.total + "&-strict&experimental&-preset&ultrafast&" +
                            VideoThemeActivity.outputPath + "";

                } else {
                    //without frame
                    Log.v("withoutframe", "withoutframe");
                    cmd = "-y&-r&" + 22.0 / VideoThemeActivity.application.getSecond() + "&-i&" + imgDir.getAbsolutePath()
                            + "/img%5d.jpg&-ss&" +
                            0 + "&-i&" + VideoThemeActivity.application.getMusicData().track_data +
                            "&-map&0:0&-map&1:0&-vcodec&libx264&-acodec&aac&-r&30&-t&" + VideoThemeActivity.total +
                            "&-strict&experimental&-preset&ultrafast&" + VideoThemeActivity.outputPath + "";
                }
                String[] command = cmd.split("&");

                if (command.length != 0) {
//                    execFFmpegBinary(command);
                    execFFmpegBinary(cmd.replace("&", " "));
                } else {
                    Toast.makeText(getApplicationContext(), "Command Empty", Toast.LENGTH_LONG).show();
                }
            } else {

                if (Utils.framePostion > -1) {
                    Log.v("withframe", "withframe");

                    cmd = "-y&-r&22.0/" + VideoThemeActivity.application.getSecond() + "&-i&" + imgDir.getAbsolutePath() +
                            "/img%5d.jpg" + "&-i&"
                            + FileUtils.frameFile.getAbsolutePath() + "&-ss&" + 0
                            + "&-i&" + VideoThemeActivity.application.getMusicData().track_data + "&-filter_complex&[1]scale=" +
                            finalwidth +
                            ":-1[b];[0:v][b]overlay&-vcodec&libx264&-acodec&aac&-r&30&-t&" +
                            VideoThemeActivity.total + "&-strict&experimental&-preset&ultrafast&" + VideoThemeActivity.outputPath + "";

                } else {
                    //without frame
                    Log.v("withoutframe", "withoutframe");
                    cmd = "-y&-r&" + 22.0 / VideoThemeActivity.application.getSecond() + "&-i&" + imgDir.getAbsolutePath() +
                            "/img%5d.jpg&-ss&" +
                            0 + "&-i&" + VideoThemeActivity.application.getMusicData().track_data +
                            "&-map&0:0&-map&1:0&-vcodec&libx264&-acodec&aac&-r&30&-t&" + VideoThemeActivity.total +
                            "&-strict&experimental&-preset&ultrafast&" + VideoThemeActivity.outputPath + "";
                }
                String[] command = cmd.split("&");

                if (command.length != 0) {
//                    execFFmpegBinary(command);
                    execFFmpegBinary(cmd.replace("&", " "));
                } else {
                    Toast.makeText(getApplicationContext(), "Command Empty", Toast.LENGTH_LONG).show();
                }

            }
            return null;

        }


        @Override
        protected void onPostExecute(List<String> result) {


        }

        @Override
        protected void onProgressUpdate(final Integer... values) {

        }
    }


    void execFFmpegBinary(final String command) {

        FFmpegKitConfig.enableStatisticsCallback(statistics -> {
            float progress = Float.parseFloat(String.valueOf(statistics.getTime())) * 100 / (int) (((float) (KSUtil.videoPathList.size() - 1)) * VideoThemeActivity.seconds);
            runOnUiThread(() -> {
                perTV.setText("" + (int) progress / 1000 + " %");
            });
        });

        FFmpegSession session = FFmpegKit.execute(command);

        if (ReturnCode.isSuccess(session.getReturnCode())) {
            // SUCCESS
            removeFrameImage(VideoThemeActivity.folderPath);
            removeFrameImage(VideoThemeActivity.folderPath + "/temp");
            removeFrameImage(VideoThemeActivity.folderPath + "/edittmpzoom");
            FileUtils.deleteFile(VideoThemeActivity.tempFile);
            removemusic(VideoThemeActivity.folderPath + "/music/");
            File f = new File(VideoThemeActivity.outputPath);
            MediaScannerConnection.scanFile(getApplicationContext(),
                    new String[]{f.getAbsolutePath()},
                    new String[]{"mp4"}, null);

            VideoThemeActivity.handler.postDelayed(runnable, 1000);
        }
    }


    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            VideoThemeActivity.handler.removeCallbacks(runnable);

            Intent in = new Intent(VideoMakerActivity.this,
                    VideoPlayerActivity.class);
            in.putExtra("video_path", VideoThemeActivity.outputPath);
            in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(in);
            AdManager.adCounter = AdManager.adDisplayCounter;
            if (!AdManager.isloadFbMAXAd) {
                AdManager.showInterAd(VideoMakerActivity.this,in, 0);
            } else {
                AdManager.showMaxInterstitial(VideoMakerActivity.this,in, 0);
            }
        }
    };


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

    boolean back = false;
    @Override
    public void onBackPressed() {
        if (back) {
            super.onBackPressed();
        }
    }


}
