package com.kessi.photovideomaker;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.StrictMode;
import android.provider.MediaStore.Images.Media;
import android.util.Log;

import com.onesignal.OneSignal;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import vcarry.data.ImageData;
import vcarry.data.MusicData;
import vcarry.mask.KessiTheme;
import vcarry.util.EPreferences;
import vcarry.util.FileUtils;
import vcarry.util.PermissionModelUtil;

public class KessiApplication extends Application{
    public static int VIDEO_HEIGHT;
    public static int VIDEO_WIDTH;
    private static KessiApplication instance;
    public static boolean isBreak = false;
    public HashMap<String, ArrayList<ImageData>> allAlbum;
    private ArrayList<String> allFolder;
    int frame = 0;
    public boolean isEditEnable = false;
    public boolean isFromSdCardAudio = false;
    public int min_pos = Integer.MAX_VALUE;
    private MusicData PVMWSMusicData;
    private float second = 3.0f;
    private String selectedFolderId = "";
    public final ArrayList<ImageData> selectedImages = new ArrayList();
    public final ArrayList<ImageData> selectedImagesstart = new ArrayList();
    public KessiTheme selectedTheme = KessiTheme.None;
    public ArrayList<String> videoImages = new ArrayList();
    public static String[] startframelist, endframelist;

    public static KessiApplication getInstance() {
        return instance;
    }

    private void init() {
        if (!new PermissionModelUtil(this).needPermissionCheck()) {
            getFolderList();
            if (!FileUtils.APP_DIRECTORY.exists()) {
                FileUtils.APP_DIRECTORY.mkdirs();
            }

        }
        try {
            setVideoHeightWidth();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void setVideoHeightWidth() {
        String s = getResources().getStringArray(R.array.video_height_width)
                [EPreferences.getInstance(getApplicationContext()).getInt(EPreferences.PREF_KEY_VIDEO_QUALITY, 2)];
        StringBuilder sb = new StringBuilder();
        sb.append("KessiApplication VideoQuality value  is:- ");
        sb.append(s);
        Log.d("TAG", sb.toString());
    }


    public Bitmap loadBitmapFromAssets(Context context, String path) {
        InputStream stream = null;
        try {
            stream = context.getAssets().open(path);
            return BitmapFactory.decodeStream(stream);
        } catch (Exception ignored) {
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    public HashMap<String, ArrayList<ImageData>> getAllAlbum() {
        return this.allAlbum;
    }


    public String getCurrentTheme() {
        return getSharedPreferences("theme", 0).getString("current_theme", KessiTheme.None.toString());
    }

    @SuppressLint("Range")
    public void getFolderList() {
        this.allFolder = new ArrayList();
        this.allAlbum = new HashMap();
        Cursor query = getContentResolver().query(Media.EXTERNAL_CONTENT_URI, new String[]{"_data", "_id", "bucket_display_name", "bucket_id", "datetaken", "_data"}, (String) null, (String[]) null, "_data DESC");
        if (query.moveToFirst()) {
            int columnIndex = query.getColumnIndex("bucket_display_name");
            int columnIndex2 = query.getColumnIndex("bucket_id");
            setSelectedFolderId(query.getString(columnIndex2));
            do {
                ImageData PVMWSImageData = new ImageData();
                PVMWSImageData.imagePath = query.getString(query.getColumnIndex("_data"));
                PVMWSImageData.imageThumbnail = query.getString(query.getColumnIndex("_data"));
                if (!PVMWSImageData.imagePath.endsWith(".gif")) {
                    String string = query.getString(columnIndex);
                    String string2 = query.getString(columnIndex2);
                    if (!this.allFolder.contains(string2)) {
                        this.allFolder.add(string2);
                    }
                    ArrayList<ImageData> list = (ArrayList) this.allAlbum.get(string2);
                    if (list == null) {
                        list = new ArrayList();
                    }
                    PVMWSImageData.folderName = string;
                    list.add(PVMWSImageData);
                    this.allAlbum.put(string2, list);
                }
            } while (query.moveToNext());
        }
    }

    public int getFrame() {
        return this.frame;
    }

    public MusicData getMusicData() {
        return this.PVMWSMusicData;
    }


    public float getSecond() {
        return this.second;
    }



    public ArrayList<ImageData> getSelectedImages() {
        return this.selectedImages;
    }

    public ArrayList<ImageData> getSelectedImagesstart() {
        return this.selectedImagesstart;
    }


    public void initArray() {
        this.videoImages = new ArrayList();
    }


    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            builder.detectFileUriExposure();
            StrictMode.setVmPolicy(builder.build());
        }

        instance = this;

        try {
            startframelist = getAssets().list("startframe");

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            endframelist = getAssets().list("endframe");

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        init();


        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId("da8792f6-ceab-45b0-8099-07656eaa1108");

    }



    public void setCurrentTheme(String s) {
        Editor edit = getSharedPreferences("theme", 0).edit();
        edit.putString("current_theme", s);
        edit.commit();
    }

    public void setFrame(int frame) {
        this.frame = frame;
    }

    public void setMusicData(MusicData PVMWSMusicData) {
        this.PVMWSMusicData = PVMWSMusicData;
    }



    public void setSecond(float second) {
        this.second = second;
    }

    public void setSelectedFolderId(String selectedFolderId) {
        this.selectedFolderId = selectedFolderId;
    }


}
