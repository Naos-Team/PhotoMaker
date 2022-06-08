package com.naosteam.slideshowmaker.activities.songpicker;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.naosteam.slideshowmaker.activities.songpicker.roomdb.OnlineSongItem;

public interface OnDownloadSongListener {
    void onDownload(OnlineSongItem item, ProgressBar progressBar, ImageView btn_download, ImageView btn_downloaded);
    void onPlaySong(int position, MediaPlayer mp, ImageView btn_play, ImageView btn_pause, CountDownTimer countdown);
}
