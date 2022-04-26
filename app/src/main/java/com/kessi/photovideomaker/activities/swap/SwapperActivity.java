package com.kessi.photovideomaker.activities.swap;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kessi.photovideomaker.KessiApplication;
import com.kessi.photovideomaker.R;
import com.kessi.photovideomaker.activities.MainActivity;
import com.kessi.photovideomaker.activities.swap.gridsupport.AbsDynamicGridView;
import com.kessi.photovideomaker.activities.videoeditor.VideoThemeActivity;
import com.kessi.photovideomaker.util.AdManager;
import com.kessi.photovideomaker.util.KSUtil;
import com.xinlan.imageeditlibrary.editimage.EditImageActivity;

import java.io.File;
import java.util.ArrayList;

import vcarry.data.ImageData;
import vcarry.service.ServiceAnim;
import vcarry.util.FileUtils;

public class SwapperActivity extends AppCompatActivity implements View.OnClickListener {
    AbsDynamicGridView absDynamicGridView;
    SwapperAdapter zapperAdapter;
    ImageView back, done;
    ArrayList<String> local, local2;

    File myDir;
    KessiApplication application;
    RelativeLayout header;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zapper);

        setTV();

        application = KessiApplication.getInstance();
        application.isEditEnable = true;

        String root = FileUtils.APP_DIRECTORY + "/";

        myDir = new File(root + "imagesfolder");

        if (myDir.exists()) {

        } else {
            myDir.mkdirs();
        }

        init();
        initGrid();
    }


    public void setTV(){
        LinearLayout adContainer = findViewById(R.id.banner_container);
        if (!AdManager.isloadFbMAXAd) {
            //admob
            AdManager.initAd(SwapperActivity.this);
            AdManager.loadBannerAd(SwapperActivity.this, adContainer);
            AdManager.loadInterAd(SwapperActivity.this);
        } else {
            //MAX + Fb banner Ads
            AdManager.initMAX(SwapperActivity.this);
            AdManager.maxBanner(SwapperActivity.this, adContainer);
            AdManager.maxInterstital(SwapperActivity.this);
        }
    }

    @Override
    public void onBackPressed() {
        AdManager.adCounter++;
        if (AdManager.adCounter == AdManager.adDisplayCounter) {
            if (!AdManager.isloadFbMAXAd) {
                AdManager.showInterAd(SwapperActivity.this, null, 0);
            } else {
                AdManager.showMaxInterstitial(SwapperActivity.this, null, 0);
            }
        } else {
            if (absDynamicGridView.isEditMode()) {
                absDynamicGridView.stopEditMode();
            } else {
                File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/MS_SlideShow"+"/imagesfolder");
                if (dir.isDirectory())
                {
                    String[] children = dir.list();
                    for (int i = 0; i < children.length; i++)
                    {
                        new File(dir, children[i]).delete();
                    }
                }
                super.onBackPressed();

            }
        }
    }

    void init() {
        header = (RelativeLayout) findViewById(R.id.header);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);

        done = (ImageView) findViewById(R.id.done);
        done.setOnClickListener(this);

    }

    void initGrid() {
        absDynamicGridView = (AbsDynamicGridView) findViewById(R.id.dynamicGrid);

        zapperAdapter = new SwapperAdapter(SwapperActivity.this, KSUtil.videoPathList, 2);
        absDynamicGridView.setAdapter(zapperAdapter);

        absDynamicGridView.setOnDragListener(new AbsDynamicGridView.OnDragListener() {
            @Override
            public void onDragStarted(int position) {

            }

            @Override
            public void onDragPositionsChanged(int oldPosition, int newPosition) {
                if (oldPosition == newPosition)
                    return;
                if (oldPosition > newPosition) {
                    local = new ArrayList<String>();
                    local.clear();
                    local.add(KSUtil.videoPathList.get(oldPosition));

                    for (int i = oldPosition; i > newPosition; i--) {
                        KSUtil.videoPathList.set(i, KSUtil.videoPathList.get(i - 1));
                    }

                    KSUtil.videoPathList.set(newPosition, local.get(0));

                } else {

                    local2 = new ArrayList<String>();
                    local2.clear();
                    local2.add(KSUtil.videoPathList.get(oldPosition));

                    for (int i = oldPosition; i < newPosition; i++) {
                        KSUtil.videoPathList.set(i, KSUtil.videoPathList.get(i + 1));
                    }

                    KSUtil.videoPathList.set(newPosition, local2.get(0));

                }
                zapperAdapter.notifyDataSetChanged();


                absDynamicGridView.stopEditMode();


            }
        });
        absDynamicGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {

                absDynamicGridView.startEditMode(position);

                return true;
            }
        });
        absDynamicGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

            }
        });


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.back:
                KSUtil.Bounce(this, back);
                onBackPressed();
                break;

            case R.id.done:
                KSUtil.Bounce(this, done);
                if (KSUtil.videoPathList.size() >= 4){
                    new Done().execute();
                }else {
                    Toast.makeText(SwapperActivity.this, "Please Select at list 4 Images...",Toast.LENGTH_SHORT).show();
                    gotoMain();
                }

                break;
        }
    }

    public void gotoMain() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    class Done extends AsyncTask<Void, Void, Void> {

        ProgressDialog pd;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(SwapperActivity.this);
            pd.setMessage("Loading....");
            pd.setCancelable(false);
            pd.show();
            ServiceAnim.setTemp_back(false);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            application.isEditEnable = false;

            application.selectedImages.clear();
            KSUtil.videoPathList.add(0, KSUtil.videoPathList.get(0));
            for (int i = 0; i < KSUtil.videoPathList.size(); i++) {
                ImageData idata = new ImageData();
                idata.setImagePath(KSUtil.videoPathList.get(i));
                application.selectedImages.add(i, idata);
            }



            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pd.dismiss();
            application.isEditEnable = false;

            startActivity(new Intent(SwapperActivity.this, VideoThemeActivity.class));

        }
    }

    void startActivityes(Intent intent, int reqCode) {
        if (!AdManager.isloadFbMAXAd) {
            AdManager.adCounter++;
            AdManager.showInterAd(SwapperActivity.this, intent, reqCode);
        } else {
            AdManager.adCounter++;
            AdManager.showMaxInterstitial(SwapperActivity.this, intent, reqCode);
        }
    }

            public static final int ACTION_REQUEST_EDITIMAGE = 9;
    public void editorIntent(int position, String path) {
        Log.e("calll","calll");
        KSUtil.imbEditorPos = position;
        KSUtil.imgEditorPath = path;

        File outputFile = EditImageUtil.genEditFile();
        EditImageActivity.start(this,KSUtil.imgEditorPath,outputFile.getAbsolutePath(),ACTION_REQUEST_EDITIMAGE);

        Uri uri = getImageContentUri(SwapperActivity.this,new File(KSUtil.imgEditorPath));
        Log.e("uri", uri.toString());

    }

    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Images.Media._ID },
                MediaStore.Images.Media.DATA + "=? ",
                new String[] { filePath }, null);
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            cursor.close();
            return Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ACTION_REQUEST_EDITIMAGE && resultCode == RESULT_OK){
            handleEditorImage(data);
        }
        AdManager.adCounter = AdManager.adDisplayCounter;
        if (!AdManager.isloadFbMAXAd) {
            AdManager.showInterAd(SwapperActivity.this, null, 0);
        } else {
            AdManager.showMaxInterstitial(SwapperActivity.this, null, 0);
        }
    }

    private void handleEditorImage(Intent data) {
        String newFilePath = data.getStringExtra(EditImageActivity.EXTRA_OUTPUT);
        boolean isImageEdit = data.getBooleanExtra(EditImageActivity.IMAGE_IS_EDIT, false);

        if (isImageEdit) {
//            Toast.makeText(this, getString(R.string.save_path, newFilePath), Toast.LENGTH_LONG).show();
        } else {//Not edited or used the original image
            newFilePath = data.getStringExtra(EditImageActivity.FILE_PATH);
        }

        KSUtil.videoPathList.set(KSUtil.imbEditorPos, newFilePath);
        initGrid();
        zapperAdapter.refreshlist();
    }



}
