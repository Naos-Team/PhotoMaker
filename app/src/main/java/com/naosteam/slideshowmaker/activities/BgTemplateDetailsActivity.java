package com.naosteam.slideshowmaker.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.naosteam.slideshowmaker.KessiApplication;
import com.naosteam.slideshowmaker.R;
import com.naosteam.slideshowmaker.activities.songpicker.SongGalleryActivity;
import com.naosteam.slideshowmaker.activities.swap.EditImageUtil;
import com.naosteam.slideshowmaker.activities.videoeditor.VideoThemeActivity;
import com.naosteam.slideshowmaker.util.AdManager;
import com.naosteam.slideshowmaker.util.KSUtil;
import com.xinlan.imageeditlibrary.editimage.utils.BitmapUtils;
import com.xinlan.imageeditlibrary.editimage.EditImageActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import vcarry.data.Background_Template;
import vcarry.data.Frame_in_Background;
import vcarry.data.ImageData;
import vcarry.data.Image_Button;
import vcarry.service.ServiceAnim;
import vcarry.util.ScalingUtilities;

public class BgTemplateDetailsActivity extends AppCompatActivity {
    private ImageView backimg_select_img_bg, img_bg_template_selected;
    private Button btn_photo_template, btn_video_template;
    private ConstraintLayout layout_bg_template_main;
    private static Background_Template background_template;
    private ArrayList<Image_Button> list_image_button;
    private int REQUEST_CODE_FOLDER = 456, id_now = -1;
    private static final int ACTION_REQUEST_EDITIMAGE = 9;
    private KessiApplication application;
    private LinearLayout ll_adView;

    public static Background_Template getBackground_template() {
        return background_template;
    }

    public static void setBackground_template(Background_Template background_template) {
        BgTemplateDetailsActivity.background_template = background_template;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bg_template_details);
        application = KessiApplication.getInstance();
        list_image_button = new ArrayList<>();

        bindView();
        LoadAds();

    }

    private void LoadAds(){
        AdManager.initAd(BgTemplateDetailsActivity.this);
        ll_adView = findViewById(R.id.ll_adView9);
        AdManager.loadInterAd(this);
        AdManager.loadAdmobBanner(this, ll_adView);
    }

    @Override
    public void onBackPressed() {
        AdManager.showAdmobInterAd(BgTemplateDetailsActivity.this, new AdManager.InterAdsListener() {
            @Override
            public void onClick() {
                BgTemplateDetailsActivity.super.onBackPressed();
            }
        });
    }

    void bindView() {


        backimg_select_img_bg = (ImageView) findViewById(R.id.backimg_select_img_bg);
        img_bg_template_selected = (ImageView) findViewById(R.id.img_bg_template_selected);
        btn_photo_template = (Button) findViewById(R.id.btn_photo_template);
        btn_video_template = (Button) findViewById(R.id.btn_video_template);
        layout_bg_template_main = (ConstraintLayout) findViewById(R.id.layout_bg_template_main);

        if (background_template!=null){
            img_bg_template_selected.setImageBitmap(background_template.getBitmap_background());
        }


        backimg_select_img_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (background_template != null){
            for (Frame_in_Background k : background_template.getList_frame()){
                list_image_button.add(Create_Image_View(k.getHor_bias(), k.getVerti_bias(), k.getHeight_per(), k.getRatio(), k.getColor()));
            }
        }


        for(Image_Button i : list_image_button){
            Create_Edit_ReChoice_Button(i.getImg_Main(), i);
        }

        btn_photo_template.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResult(btn_photo_template.getId());
            }
        });

        btn_video_template.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResult(btn_video_template.getId());
            }
        });
    }

    private void onResult(int id){
        ArrayList<String> image_path = getImagePath();
        if(image_path.size() < background_template.getList_frame().size()){
            Toast.makeText(BgTemplateDetailsActivity.this,
                    "Please choose enough image to edit!", Toast.LENGTH_SHORT).show();
            return;
        }
        switch (id){
            case R.id.btn_photo_template:
                Bitmap result = Bitmap.createBitmap(background_template.getBitmap_background());
                for(int i = 0; i < background_template.getList_frame().size(); ++i){
                    Bitmap temp = ((BitmapDrawable) list_image_button.get(i).getImg_Main().getDrawable()).getBitmap();
                    result = ScalingUtilities.Paint_Image(temp, result,
                            background_template.getList_image().get(i).getLeft_per(),
                            background_template.getList_image().get(i).getTop_per(),
                            background_template.getList_image().get(i).getHeight_per(),
                            background_template.getList_image().get(i).getWidth_ratio(),
                            background_template.getList_image().get(i).getHeight_ratio());
                }
                new Done_Template_Image().execute(result);
                break;
            case R.id.btn_video_template:
                new Done_Template_Video().execute();
                break;
            default:
                break;
        }
    }

    private ArrayList<String> getImagePath(){
        ArrayList<String> image_path = new ArrayList<>();
        for(Image_Button image_button : list_image_button){
            if(!image_button.getDir().equals("")){
                image_path.add(image_button.getDir());
            }
        }
        return image_path;
    }

    private void Create_Edit_ReChoice_Button(ImageView img_Temp, Image_Button image_button){

        ImageView img_Edit = new ImageView(this);
        ImageView img_ReChoice = new ImageView(this);
        image_button.setImg_Edit(img_Edit);
        image_button.setImg_ReChoice(img_ReChoice);

        img_Edit.setImageResource(R.drawable.edit_chooser);
        img_Edit.setId(View.generateViewId());
        img_Edit.setScaleType(ImageView.ScaleType.CENTER_CROP);
        img_Edit.setVisibility(View.GONE);
        img_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id_now = img_Temp.getId();
                String path =  image_button.getDir();
                openImageEditor(path);
            }
        });

        img_ReChoice.setImageResource(R.drawable.swap_chooser);
        img_ReChoice.setId(View.generateViewId());
        img_ReChoice.setScaleType(ImageView.ScaleType.CENTER_CROP);
        img_ReChoice.setVisibility(View.GONE);
        img_ReChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id_now = img_Temp.getId();
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityIfNeeded(Intent.createChooser(intent,
                        "Choose picture"), REQUEST_CODE_FOLDER);
            }
        });

        layout_bg_template_main.addView(img_Edit);
        layout_bg_template_main.addView(img_ReChoice);

        ConstraintSet cs = new ConstraintSet();
        cs.clone(layout_bg_template_main);

        cs.constrainWidth(img_Edit.getId(), ConstraintSet.MATCH_CONSTRAINT);
        cs.constrainHeight(img_Edit.getId(), ConstraintSet.MATCH_CONSTRAINT);

        cs.connect(img_Edit.getId(), ConstraintSet.TOP, img_Temp.getId(), ConstraintSet.TOP);
        cs.connect(img_Edit.getId(), ConstraintSet.BOTTOM, img_Temp.getId(), ConstraintSet.TOP);
        cs.connect(img_Edit.getId(), ConstraintSet.END, img_Temp.getId(), ConstraintSet.END);
        cs.connect(img_Edit.getId(), ConstraintSet.START, img_Temp.getId(), ConstraintSet.START);

        cs.setDimensionRatio(img_Edit.getId(), "1:1");
        cs.constrainPercentHeight(img_Edit.getId(), 0.1f);

        cs.constrainWidth(img_ReChoice.getId(), ConstraintSet.MATCH_CONSTRAINT);
        cs.constrainHeight(img_ReChoice.getId(), ConstraintSet.MATCH_CONSTRAINT);

        cs.connect(img_ReChoice.getId(), ConstraintSet.BOTTOM, img_Temp.getId(), ConstraintSet.BOTTOM);
        cs.connect(img_ReChoice.getId(), ConstraintSet.TOP, img_Temp.getId(), ConstraintSet.BOTTOM);
        cs.connect(img_ReChoice.getId(), ConstraintSet.END, img_Temp.getId(), ConstraintSet.END);
        cs.connect(img_ReChoice.getId(), ConstraintSet.START, img_Temp.getId(), ConstraintSet.START);

        cs.setDimensionRatio(img_ReChoice.getId(), "1:1");
        cs.constrainPercentHeight(img_ReChoice.getId(), 0.1f);
        cs.applyTo(layout_bg_template_main);
    }

    private Image_Button Create_Image_View(float hor_bias, float verti_bias, float height_per, String ratio, int color_f){

        ImageView img_Temp = new ImageView(this);

        Image_Button image_button = new Image_Button(img_Temp, null, null, color_f);

        img_Temp.setImageResource(R.drawable.add_new);
        img_Temp.setColorFilter(ContextCompat.getColor(BgTemplateDetailsActivity.this, color_f),
                android.graphics.PorterDuff.Mode.MULTIPLY);

        if (img_Temp.getWidth()>10 && img_Temp.getHeight()>10){
            img_Temp.setPadding(30, 30, 30, 30);
        }

        img_Temp.setId(View.generateViewId());
        //img_Temp.setBackgroundColor(getColor(R.color.white));
        img_Temp.setScaleType(ImageView.ScaleType.FIT_CENTER);

        img_Temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(id_now != img_Temp.getId()){
                    unClick();
                    id_now = img_Temp.getId();
                }
                if(image_button.isFirst_time()){
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                    startActivityIfNeeded(Intent.createChooser(intent,
                            "Choose picture"), REQUEST_CODE_FOLDER);
                } else {
                    if(image_button.getImg_Edit().getVisibility() == View.VISIBLE && image_button.getImg_ReChoice().getVisibility() == View.VISIBLE){
                        image_button.getImg_Edit().setVisibility(View.GONE);
                        image_button.getImg_ReChoice().setVisibility(View.GONE);
                    } else {
                        image_button.getImg_Edit().setVisibility(View.VISIBLE);
                        image_button.getImg_ReChoice().setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        layout_bg_template_main.addView(img_Temp);

        ConstraintSet cs = new ConstraintSet();
        cs.clone(layout_bg_template_main);

        //set ImageView chinh
        cs.constrainWidth(img_Temp.getId(), ConstraintSet.MATCH_CONSTRAINT);
        cs.constrainHeight(img_Temp.getId(), ConstraintSet.MATCH_CONSTRAINT);

        cs.connect(img_Temp.getId(), ConstraintSet.BOTTOM, img_bg_template_selected.getId(), ConstraintSet.BOTTOM);
        cs.connect(img_Temp.getId(), ConstraintSet.END, img_bg_template_selected.getId(), ConstraintSet.END);
        cs.connect(img_Temp.getId(), ConstraintSet.START, img_bg_template_selected.getId(), ConstraintSet.START);
        cs.connect(img_Temp.getId(), ConstraintSet.TOP, img_bg_template_selected.getId(), ConstraintSet.TOP);
        cs.setHorizontalBias(img_Temp.getId(), hor_bias);
        cs.setVerticalBias(img_Temp.getId(), verti_bias);
        cs.constrainPercentHeight(img_Temp.getId(), height_per);
        cs.setDimensionRatio(img_Temp.getId(), ratio);

        cs.applyTo(layout_bg_template_main);
        return image_button;
    }

    private void openImageEditor(String path){
        KSUtil.imgEditorPath = path;
        File outputFile = EditImageUtil.genEditFile();
        EditImageActivity.start(this,KSUtil.imgEditorPath,outputFile.getAbsolutePath(),ACTION_REQUEST_EDITIMAGE);
    }

    private void unClick(){
        for(int i = 0; i < list_image_button.size(); ++i){
            if(list_image_button.get(i).getImg_Main().getId() == id_now){
                list_image_button.get(i).getImg_Edit().setVisibility(View.GONE);
                list_image_button.get(i).getImg_ReChoice().setVisibility(View.GONE);
                break;
            }
        }
    }

    public String getPathFromURI(Uri ContentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver()
                .query(ContentUri, proj, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            res = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
            cursor.close();
        }


        return res;
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
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //chọn hình trong file;
        if(requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                for(int i = 0; i < list_image_button.size(); ++i){
                    if(list_image_button.get(i).getImg_Main().getId() == id_now){
                        list_image_button.get(i).getImg_Main().setColorFilter(ContextCompat.getColor(BgTemplateDetailsActivity.this, R.color.white),
                                android.graphics.PorterDuff.Mode.MULTIPLY);
                        list_image_button.get(i).getImg_Main().setImageBitmap(bitmap);
                        list_image_button.get(i).getImg_Main().setScaleType(ImageView.ScaleType.CENTER_CROP);
                        list_image_button.get(i).setFirst_time(false);
                        list_image_button.get(i).setDir(getPathsFromUri(uri));
                    }
                }
            } catch (FileNotFoundException e) {
                Toast.makeText(application, "Something wrong! You can not use this photo!", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }else if (requestCode == ACTION_REQUEST_EDITIMAGE && resultCode == RESULT_OK){
            handleEditorImage(data);
        }
    }

    private void handleEditorImage(Intent data) {
        String newFilePath = data.getStringExtra("extra_output");
        boolean isImageEdit = data.getBooleanExtra("image_is_edit", false);

        if (isImageEdit) {
//            Toast.makeText(this, getString(R.string.save_path, newFilePath), Toast.LENGTH_LONG).show();
        } else {//Not edited or used the original image
            newFilePath = data.getStringExtra(EditImageActivity.FILE_PATH);
        }

        try {
            File image = new File(newFilePath);
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(),bmOptions);
            for(int i = 0; i < list_image_button.size(); ++i){
                if(list_image_button.get(i).getImg_Main().getId() == id_now){
                    list_image_button.get(i).getImg_Main().setImageBitmap(bitmap);
                    list_image_button.get(i).setFirst_time(false);
                    list_image_button.get(i).setDir(newFilePath);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    class Done_Template_Image extends AsyncTask<Bitmap, Void, Boolean>{
        ProgressDialog pd;
        Bitmap result;
        String path;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(BgTemplateDetailsActivity.this);
            pd.setMessage("Loading....");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected Boolean doInBackground(Bitmap... bitmaps) {

            application.isEditEnable = false;
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
            String formattedDate = df.format(c.getTime());
            path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    + "/" + getResources().getString(R.string.app_name)
                    + "/Image"
                    + "/" + "image_" + formattedDate + ".png";

            String path1 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    + "/" + getResources().getString(R.string.app_name)
                    + "/Image";

            File imageDirectory =  new File(path1);

            if(!imageDirectory.exists()){
                imageDirectory.mkdir();
            }

            result = bitmaps[0];
            return  BitmapUtils.saveBitmap(bitmaps[0], path);
        }

        @Override
        protected void onPostExecute(Boolean b) {
            super.onPostExecute(b);
            pd.dismiss();
            application.isEditEnable = false;
            if(b){
                ImageSaverActivity.setPath(path);
                AdManager.showAdmobInterAd(BgTemplateDetailsActivity.this, new AdManager.InterAdsListener() {
                    @Override
                    public void onClick() {
                        startActivity(new Intent(BgTemplateDetailsActivity.this, ImageSaverActivity.class));
                    }
                });

            } else {
                Toast.makeText(BgTemplateDetailsActivity.this, "Error",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    class Done_Template_Video extends AsyncTask<Void, Void, Void> {

        ProgressDialog pd;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(BgTemplateDetailsActivity.this);
            pd.setMessage("Loading....");
            pd.setCancelable(false);
            pd.show();
            done(getImagePath());
            ServiceAnim.setBackground_template(background_template);
            ServiceAnim.setTemp_back(true);
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

            AdManager.showAdmobInterAd(BgTemplateDetailsActivity.this, new AdManager.InterAdsListener() {
                @Override
                public void onClick() {
                    startActivity(new Intent(BgTemplateDetailsActivity.this, VideoThemeActivity.class));
                }
            });
        }
    }

    public String getPathsFromUri(final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(getApplicationContext(), uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(getApplicationContext(), contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(getApplicationContext(), contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(getApplicationContext(), uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
}