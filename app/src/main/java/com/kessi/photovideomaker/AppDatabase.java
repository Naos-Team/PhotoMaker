package com.kessi.photovideomaker;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.kessi.photovideomaker.activities.songpicker.roomdb.OnlineSongItem;
import com.kessi.photovideomaker.activities.songpicker.roomdb.SongDao;

import java.util.ArrayList;
import java.util.List;

@Database(entities = {OnlineSongItem.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SongDao songDao();

    private static AppDatabase instance;
    public static AppDatabase getInstance(Context context){
        if (instance==null){
            instance = Room.databaseBuilder(context, AppDatabase.class, "db").build();
        }
        return instance;
    }

}
