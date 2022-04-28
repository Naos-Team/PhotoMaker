package com.naosteam.slideshowmaker.activities.songpicker.roomdb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface SongDao {
    @Query("SELECT * FROM song")
    List<OnlineSongItem> getAll();

    @Insert
    void insertAll(ArrayList<OnlineSongItem> songs);

    @Insert
    void insertSong(OnlineSongItem song);

    @Query("SELECT EXISTS(SELECT * FROM song WHERE id = :id)")
    boolean isSongExist(int id);

    @Update
    void updateSong(OnlineSongItem songItem);
}
