package com.naosteam.slideshowmaker.activities.kessiimagepicker.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore.Images.Media;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.naosteam.slideshowmaker.R;
import com.naosteam.slideshowmaker.activities.kessiimagepicker.Constants;
import com.naosteam.slideshowmaker.activities.kessiimagepicker.adapter.AlbumAdapter;
import com.naosteam.slideshowmaker.activities.kessiimagepicker.adapter.ListAlbumAdapter;
import com.naosteam.slideshowmaker.activities.kessiimagepicker.model.ImageModel;
import com.naosteam.slideshowmaker.activities.songpicker.SongGalleryActivity;
import com.naosteam.slideshowmaker.activities.swap.SwapperActivity;
import com.naosteam.slideshowmaker.util.AdManager;
import com.naosteam.slideshowmaker.util.KSUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class ImagePickerActivity extends AppCompatActivity implements OnClickListener, com.naosteam.slideshowmaker.activities.kessiimagepicker.myinterface.OnAlbum, com.naosteam.slideshowmaker.activities.kessiimagepicker.myinterface.OnListAlbum {
    public static final String KEY_DATA_RESULT = "KEY_DATA_RESULT";
    public static final String KEY_LIMIT_MAX_IMAGE = "KEY_LIMIT_MAX_IMAGE";
    public static final String KEY_LIMIT_MIN_IMAGE = "KEY_LIMIT_MIN_IMAGE";
    public static final int PICKER_REQUEST_CODE = 1001;
    AlbumAdapter albumAdapter;
    ArrayList<ImageModel> dataAlbum = new ArrayList();
    ArrayList<ImageModel> dataListPhoto = new ArrayList();
    GridView gridViewAlbum;
    GridView gridViewListAlbum;
    HorizontalScrollView horizontalScrollView;
    LinearLayout layoutListItemSelect;
    public static int limitImageMax = 30;
    int limitImageMin = 2;
    ListAlbumAdapter listAlbumAdapter;
    public static ArrayList<ImageModel> listItemSelect;
    int pWHBtnDelete;
    int pWHItemSelected;
    ArrayList<String> pathList;
    AlertDialog sortDialog;
    TextView txtTotalImage;
    private Handler mHandler;
    private ProgressDialog pd;
    private int position = 0;
    private static final int READ_STORAGE_CODE = 1001;
    private static final int WRITE_STORAGE_CODE = 1002;
    LinearLayout ll_adView;


    public static ArrayList<String> saveFiles;

    LottieAnimationView progress;

    public void OnItemListAlbumClick(ImageModel item) {
        if (this.listItemSelect.size() < this.limitImageMax) {
            addItemSelect(item);
        } else {
            Toast.makeText(this, "Limit " + this.limitImageMax + " images", Toast.LENGTH_SHORT).show();
        }
    }

    private class GetItemAlbum extends AsyncTask<Void, Void, String> {
        private GetItemAlbum() {
        }

        protected String doInBackground(Void... params) {
            Cursor cursor = ImagePickerActivity.this.getContentResolver().query(Media.EXTERNAL_CONTENT_URI, new String[]{"_data", "bucket_display_name"}, null, null, null);
            if (cursor != null) {
                int column_index_data = cursor.getColumnIndexOrThrow("_data");
                while (cursor.moveToNext()) {
                    String pathFile = cursor.getString(column_index_data);
                    File file = new File(pathFile);
                    if (file.exists()) {
                        boolean check = ImagePickerActivity.this.checkFile(file);
                        if (!ImagePickerActivity.this.Check(file.getParent(), ImagePickerActivity.this.pathList) && check) {
                            ImagePickerActivity.this.pathList.add(file.getParent());
                            ImagePickerActivity.this.dataAlbum.add(new ImageModel(file.getParentFile().getName(), pathFile, file.getParent(), Arrays.stream(file.getParentFile().listFiles()).count()));
                        }
                    }
                }
                cursor.close();
            }
            return "";
        }

        protected void onPostExecute(String result) {
            ImagePickerActivity.this.gridViewAlbum.setAdapter(ImagePickerActivity.this.albumAdapter);
            progress.setVisibility(View.GONE);
        }

        protected void onPreExecute() {
        }

        protected void onProgressUpdate(Void... values) {
        }
    }

    private class GetItemListAlbum extends AsyncTask<Void, Void, String> {
        String pathAlbum;

        GetItemListAlbum(String pathAlbum) {
            this.pathAlbum = pathAlbum;
        }

        protected String doInBackground(Void... params) {
            File file = new File(this.pathAlbum);
            if (file.isDirectory()) {
                for (File fileTmp : file.listFiles()) {
                    if (fileTmp.exists()) {
                        boolean check = ImagePickerActivity.this.checkFile(fileTmp);
                        if (!fileTmp.isDirectory() && check) {
                            ImagePickerActivity.this.dataListPhoto.add(new ImageModel(fileTmp.getName(), fileTmp.getAbsolutePath(), fileTmp.getAbsolutePath(), 0));
                            publishProgress(new Void[0]);
                        }
                    }
                }
            }
            return "";
        }

        protected void onPostExecute(String result) {
            progress.setVisibility(View.GONE);
            try {
                Collections.sort(ImagePickerActivity.this.dataListPhoto, new Comparator<ImageModel>() {
                    @Override
                    public int compare(ImageModel item, ImageModel t1) {
                        File fileI = new File(item.getPathFolder());
                        File fileJ = new File(t1.getPathFolder());
                        if (fileI.lastModified() > fileJ.lastModified()) {
                            return -1;
                        }
                        if (fileI.lastModified() < fileJ.lastModified()) {
                            return 1;
                        }
                        return 0;
                    }
                });
            } catch (Exception e) {
            }
            ImagePickerActivity.this.listAlbumAdapter.notifyDataSetChanged();
        }

        protected void onPreExecute() {
            progress.setVisibility(View.VISIBLE);
        }

        protected void onProgressUpdate(Void... values) {
        }
    }

    ImageView btnBack, btnDone;
    RelativeLayout header;
    LinearLayout footer;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_picker);

        progress = findViewById(R.id.loader);
        LoadAds();
        saveFiles = new ArrayList<String>();

        listItemSelect = new ArrayList<>();
        pathList = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            this.limitImageMax = bundle.getInt(KEY_LIMIT_MAX_IMAGE, 30);
            this.limitImageMin = bundle.getInt(KEY_LIMIT_MIN_IMAGE, 2);
            if (this.limitImageMin > this.limitImageMax) {
                finish();
            }
            if (this.limitImageMin < 1) {
                finish();
            }
//            Log.e("ImagePickerActivity", "limitImageMin = " + this.limitImageMin);
//            Log.e("ImagePickerActivity", "limitImageMax = " + this.limitImageMax);
        }
        this.pWHItemSelected = (((int) ((((float) getDisplayInfo(this).heightPixels) / 100.0f) * 25.0f)) / 100) * 80;
        this.pWHBtnDelete = (this.pWHItemSelected / 100) * 25;

        btnBack = (ImageView) findViewById(R.id.back);
        btnBack.setOnClickListener(v -> {
            KSUtil.Bounce(this, btnBack);
            onBackPressed();
        });


        header = (RelativeLayout) findViewById(R.id.header);

        footer = (LinearLayout) findViewById(R.id.footer);


        this.gridViewListAlbum = (GridView) findViewById(R.id.gridViewListAlbum);
        this.txtTotalImage = (TextView) findViewById(R.id.txtTotalImage);

        btnDone = findViewById(R.id.btnDone);
        btnDone.setOnClickListener(this);

        this.layoutListItemSelect = (LinearLayout) findViewById(R.id.layoutListItemSelect);
        this.horizontalScrollView = (HorizontalScrollView) findViewById(R.id.horizontalScrollView);
        this.horizontalScrollView.getLayoutParams().height = this.pWHItemSelected;
        this.gridViewAlbum = (GridView) findViewById(R.id.gridViewAlbum);


        pd = new ProgressDialog(ImagePickerActivity.this);
        pd.setIndeterminate(true);
        pd.setMessage("Loading...");

        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
            }
        };

        try {
            Collections.sort(this.dataAlbum, new Comparator<ImageModel>() {
                @Override
                public int compare(ImageModel lhs, ImageModel rhs) {
                    return lhs.getName().compareToIgnoreCase(rhs.getName());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.albumAdapter = new AlbumAdapter(this, R.layout.piclist_row_album, this.dataAlbum);
        this.albumAdapter.setOnItem(this);

        if (isPermissionGranted(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            new GetItemAlbum().execute(new Void[0]);
        } else {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE, READ_STORAGE_CODE);
        }

        new GetItemAlbum().execute(new Void[0]);

        updateTxtTotalImage();

    }

    private void LoadAds(){
        AdManager.initAd(ImagePickerActivity.this);
        ll_adView = findViewById(R.id.ll_adView10);
        AdManager.loadAdmobBanner(this, ll_adView);
    }

    @Override
    public void onBackPressed() {
        AdManager.showAdmobInterAd(ImagePickerActivity.this, new AdManager.InterAdsListener() {
            @Override
            public void onClick() {
                if (gridViewListAlbum.getVisibility() == View.VISIBLE) {
                    dataListPhoto.clear();
                    listAlbumAdapter.notifyDataSetChanged();
                    gridViewListAlbum.setVisibility(View.GONE);
                    return;
                }
                ImagePickerActivity.super.onBackPressed();
            }
        });
//        if (AdManager.adCounter == AdManager.adDisplayCounter) {
//            if (!AdManager.isloadFbMAXAd) {
//                AdManager.showInterAd(ImagePickerActivity.this, null, 0);
//            } else {
//                AdManager.showMaxInterstitial(ImagePickerActivity.this, null, 0);
//            }
//        } else {
//            if (this.gridViewListAlbum.getVisibility() == View.VISIBLE) {
//                this.dataListPhoto.clear();
//                this.listAlbumAdapter.notifyDataSetChanged();
//                this.gridViewListAlbum.setVisibility(View.GONE);
//                return;
//            }
//            super.onBackPressed();
//        }
    }


    private boolean isPermissionGranted(String permission) {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(this, permission);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted returning false
        return false;
    }


    //Requesting permission
    private void requestPermission(String permission, int code) {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{permission}, code);
    }


    private boolean Check(String a, ArrayList<String> list) {
        if (!list.isEmpty() && list.contains(a)) {
            return true;
        }
        return false;
    }

    public void showDialogSortAlbum() {
        CharSequence[] items = getResources().getStringArray(R.array.array_sort_value);
        final Builder builder = new Builder(this);
        builder.setTitle(getResources().getString(R.string.text_title_dialog_sort_by_album));
        Log.e("TAG", "showDialogSortAlbum");
        builder.setSingleChoiceItems(items, position, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, final int i) {
                switch (i) {
                    case 0:
                        position = i;
                        Collections.sort(ImagePickerActivity.this.dataAlbum, new Comparator<ImageModel>() {
                            @Override
                            public int compare(ImageModel lhs, ImageModel rhs) {
                                return lhs.getName().compareToIgnoreCase(rhs.getName());
                            }
                        });
                        ImagePickerActivity.this.refreshGridViewAlbum();
                        Log.e("TAG", "showDialogSortAlbum by NAME");
                        break;
                    case 1:
                        position = i;
                        doinBackground();
                        Log.e("TAG", "showDialogSortAlbum by Size");
                        break;
                    case 2:
                        position = i;
                        Collections.sort(ImagePickerActivity.this.dataAlbum, new Comparator<ImageModel>() {
                            @Override
                            public int compare(ImageModel lhs, ImageModel rhs) {
                                File fileI = new File(lhs.getPathFolder());
                                File fileJ = new File(rhs.getPathFolder());
                                long totalSizeFileI = ImagePickerActivity.getFolderSize(fileI);
                                long totalSizeFileJ = ImagePickerActivity.getFolderSize(fileJ);
                                if (totalSizeFileI > totalSizeFileJ) {
                                    return -1;
                                }
                                if (totalSizeFileI < totalSizeFileJ) {
                                    return 1;
                                }
                                return 0;
                            }
                        });
                        ImagePickerActivity.this.refreshGridViewAlbum();
                        Log.e("TAG", "showDialogSortAlbum by Date");
                        break;
                }
                ImagePickerActivity.this.sortDialog.dismiss();
            }
        });
        this.sortDialog = builder.create();
        this.sortDialog.show();
    }

    public void refreshGridViewAlbum() {
        this.albumAdapter = new AlbumAdapter(this, R.layout.piclist_row_album, this.dataAlbum);
        this.albumAdapter.setOnItem(this);
        this.gridViewAlbum.setAdapter(this.albumAdapter);
        this.gridViewAlbum.setVisibility(View.GONE);
        this.gridViewAlbum.setVisibility(View.VISIBLE);
    }

    public void showDialogSortListAlbum() {
        CharSequence[] items = getResources().getStringArray(R.array.array_sort_value);
        Builder builder = new Builder(this);
        builder.setTitle(getResources().getString(R.string.text_title_dialog_sort_by_photo));
        builder.setSingleChoiceItems(items, position, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        position = i;
                        doinBackgroundPhoto(i);
                    case 1:
                        position = i;
                        doinBackgroundPhoto(i);
                    case 2:
                        position = i;
                        doinBackgroundPhoto(i);
                }
                ImagePickerActivity.this.sortDialog.dismiss();
            }
        });
        this.sortDialog = builder.create();
        this.sortDialog.show();
    }

    public void refreshGridViewListAlbum() {
        this.listAlbumAdapter = new ListAlbumAdapter(this, R.layout.piclist_row_list_album, this.dataListPhoto);
        this.listAlbumAdapter.setOnListAlbum(this);
        this.gridViewListAlbum.setAdapter(this.listAlbumAdapter);
        this.gridViewListAlbum.setVisibility(View.GONE);
        this.gridViewListAlbum.setVisibility(View.VISIBLE);
    }

    public static long getFolderSize(File directory) {
        long length = 0;
        if (directory == null) {
            return 0;
        }
        if (!directory.exists()) {
            return 0;
        }
        File[] files = directory.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.isFile()) {
                    boolean isCheck = false;
                    for (int k = 0; k < Constants.FORMAT_IMAGE.size(); k++) {
                        if (file.getName().endsWith((String) Constants.FORMAT_IMAGE.get(k))) {
                            isCheck = true;
                            break;
                        }
                    }
                    if (isCheck) {
                        length++;
                    }
                }
            }
        }
        return length;
    }


    void addItemSelect(final ImageModel item) {
        item.setId(this.listItemSelect.size());
        this.listItemSelect.add(item);
        updateTxtTotalImage();
        final View viewItemSelected = View.inflate(this, R.layout.piclist_item_selected, null);
        FrameLayout layoutRoot = (FrameLayout) viewItemSelected.findViewById(R.id.layoutRoot);
        ImageView imageItem = (ImageView) viewItemSelected.findViewById(R.id.imageItem);
        ImageView btnDelete = (ImageView) viewItemSelected.findViewById(R.id.btnDelete);

        imageItem.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with((Activity) this).load(item.getPathFile()).asBitmap().placeholder(R.drawable.piclist_icon_default).into(imageItem);


        btnDelete.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ImagePickerActivity.this.layoutListItemSelect.removeView(viewItemSelected);
                ImagePickerActivity.this.listItemSelect.remove(item);
                ImagePickerActivity.this.updateTxtTotalImage();
                listAlbumAdapter.notifyDataSetChanged();

            }
        });

        ImagePickerActivity.this.layoutListItemSelect.addView(viewItemSelected);
        viewItemSelected.startAnimation(AnimationUtils.loadAnimation(ImagePickerActivity.this, R.anim.abc_fade_in));
        ImagePickerActivity.this.sendScroll();

    }


    void updateTxtTotalImage() {
        this.txtTotalImage.setText(String.format(getResources().getString(R.string.text_images), new Object[]{Integer.valueOf(this.listItemSelect.size())}));
    }

    private void sendScroll() {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ImagePickerActivity.this.horizontalScrollView.fullScroll(66);
                    }
                });
            }
        }).start();
    }

    void showListAlbum(String pathAlbum) {
//        getSupportActionBar().setTitle(new File(pathAlbum).getName());
        this.listAlbumAdapter = new ListAlbumAdapter(this, R.layout.piclist_row_list_album, this.dataListPhoto);
        this.listAlbumAdapter.setOnListAlbum(this);
        this.gridViewListAlbum.setAdapter(this.listAlbumAdapter);
        this.gridViewListAlbum.setVisibility(View.VISIBLE);
        new GetItemListAlbum(pathAlbum).execute(new Void[0]);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.btnDone) {
            KSUtil.Bounce(this, btnDone);
            ArrayList<String> listString = getListString(this.listItemSelect);
            if (listString.size() >= this.limitImageMin) {
                done(listString);
            } else {
                Toast.makeText(this, "Please select at lease " + this.limitImageMin + " images", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void done(ArrayList<String> listString) {
        KSUtil.videoPathList.clear();
        KSUtil.videoPathList = listString;
        if (KSUtil.videoPathList != null && !KSUtil.videoPathList.isEmpty()) {
            StringBuilder sb = new StringBuilder("");
            for (int i = 0; i < KSUtil.videoPathList.size(); i++) {
                sb.append("Image Path" + (i + 1) + ":" + KSUtil.videoPathList.get(i));
                sb.append("\n");

            }
            Log.e("Image", sb.toString());

            AdManager.adCounter = AdManager.adDisplayCounter;
            if (!AdManager.isloadFbMAXAd) {
                AdManager.showInterAd(ImagePickerActivity.this, new Intent(ImagePickerActivity.this, SwapperActivity.class), 0);
            } else {
                AdManager.showMaxInterstitial(ImagePickerActivity.this, new Intent(ImagePickerActivity.this, SwapperActivity.class), 0);
            }
        }
    }

    ArrayList<String> getListString(ArrayList<ImageModel> listItemSelect) {
        ArrayList<String> listString = new ArrayList();
        for (int i = 0; i < listItemSelect.size(); i++) {
            listString.add(((ImageModel) listItemSelect.get(i)).getPathFile());
        }
        return listString;
    }

    private boolean checkFile(File file) {
        if (file == null) {
            return false;
        }
        if (!file.isFile()) {
            return true;
        }
        String name = file.getName();
        if (name.startsWith(".") || file.length() == 0) {
            return false;
        }
        boolean isCheck = false;
        for (int k = 0; k < Constants.FORMAT_IMAGE.size(); k++) {
            if (name.endsWith((String) Constants.FORMAT_IMAGE.get(k))) {
                isCheck = true;
                break;
            }
        }
        return isCheck;
    }

    public static DisplayMetrics getDisplayInfo(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm;
    }


    public void handlerDoWork(com.naosteam.slideshowmaker.activities.kessiimagepicker.myinterface.IHandler mIHandler) {
        mHandler.sendMessage(this.mHandler.obtainMessage(0, mIHandler));
    }

    private void doinBackgroundPhoto(final int position) {

        new AsyncTask<String, String, Void>() {
            @Override
            protected void onPreExecute() {
                pd.show();
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(String... strings) {

                Log.d("tag", "Postion" + position);

                if (position == 0) {
                    try {
                        Collections.sort(ImagePickerActivity.this.dataListPhoto, new Comparator<ImageModel>() {
                            @Override
                            public int compare(ImageModel lhs, ImageModel rhs) {
                                return lhs.getName().compareToIgnoreCase(rhs.getName());
                            }
                        });

                    } catch (Exception e) {

                    }
                } else if (position == 1) {
                    Collections.sort(ImagePickerActivity.this.dataListPhoto, new Comparator<ImageModel>() {
                        @Override
                        public int compare(ImageModel lhs, ImageModel rhs) {
                            File fileI = new File(lhs.getPathFolder());
                            File fileJ = new File(rhs.getPathFolder());
                            long totalSizeFileI = ImagePickerActivity.getFolderSize(fileI);
                            long totalSizeFileJ = ImagePickerActivity.getFolderSize(fileJ);
                            if (totalSizeFileI > totalSizeFileJ) {
                                return -1;
                            }
                            if (totalSizeFileI < totalSizeFileJ) {
                                return 1;
                            }
                            return 0;
                        }
                    });
                } else if (position == 2) {
                    try {
                        Collections.sort(ImagePickerActivity.this.dataListPhoto, new Comparator<ImageModel>() {
                            @Override
                            public int compare(ImageModel lhs, ImageModel rhs) {
                                File fileI = new File(lhs.getPathFolder());
                                File fileJ = new File(rhs.getPathFolder());
                                if (fileI.lastModified() > fileJ.lastModified()) {
                                    return -1;
                                }
                                if (fileI.lastModified() < fileJ.lastModified()) {
                                    return 1;
                                }
                                return 0;
                            }
                        });
                    } catch (Exception e3) {
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                ImagePickerActivity.this.refreshGridViewListAlbum();
                pd.dismiss();
            }
        }.execute();
    }

    private void doinBackground() {

        new AsyncTask<String, String, Void>() {
            @Override
            protected void onPreExecute() {
                pd.show();
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(String... strings) {
                Collections.sort(ImagePickerActivity.this.dataAlbum, new Comparator<ImageModel>() {
                    @Override
                    public int compare(ImageModel item, ImageModel t1) {
                        File fileI = new File(item.getPathFolder());
                        File fileJ = new File(t1.getPathFolder());
                        if (fileI.lastModified() > fileJ.lastModified()) {
                            return -1;
                        }
                        if (fileI.lastModified() < fileJ.lastModified()) {
                            return 1;
                        }
                        return 0;
                    }
                });
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                ImagePickerActivity.this.refreshGridViewAlbum();
                pd.dismiss();
            }
        }.execute();
    }

    public void OnItemAlbumClick(int position) {
        dataListPhoto.clear();
        showListAlbum(((ImageModel) this.dataAlbum.get(position)).getPathFolder());
    }



}
