package com.kessi.photovideomaker.activities.songpicker.roomdb;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "song")
public class OnlineSongItem {

    @PrimaryKey
    public int id;
    @ColumnInfo
    public String title;
    @ColumnInfo
    public String artist;
    @ColumnInfo
    public String url;
    @ColumnInfo
    public boolean isDownload;

    public boolean isDownload() {
        return isDownload;
    }

    public void setDownload(boolean download) {
        isDownload = download;
    }

    public OnlineSongItem(int id, String title, String artist, String url) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.isDownload = false;
        this.artist = artist;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
