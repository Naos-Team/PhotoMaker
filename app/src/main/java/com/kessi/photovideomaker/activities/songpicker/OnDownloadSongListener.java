package com.kessi.photovideomaker.activities.songpicker;

import android.widget.ImageView;
import android.widget.ProgressBar;

import com.kessi.photovideomaker.activities.songpicker.roomdb.OnlineSongItem;

public interface OnDownloadSongListener {
    void onDownload(OnlineSongItem item, ProgressBar progressBar, ImageView btn_download, ImageView btn_downloaded);
}
