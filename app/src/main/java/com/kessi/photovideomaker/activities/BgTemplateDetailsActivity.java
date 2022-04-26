package com.kessi.photovideomaker.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.kessi.photovideomaker.KessiApplication;
import com.kessi.photovideomaker.R;
import com.kessi.photovideomaker.activities.videoeditor.VideoThemeActivity;
import com.kessi.photovideomaker.util.KSUtil;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

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
    private KessiApplication application;

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

    }

    void bindView() {
        backimg_select_img_bg = (ImageView) findViewById(R.id.backimg_select_img_bg);
        img_bg_template_selected = (ImageView) findViewById(R.id.img_bg_template_selected);
        btn_photo_template = (Button) findViewById(R.id.btn_photo_template);
        btn_video_template = (Button) findViewById(R.id.btn_video_template);
        layout_bg_template_main = (ConstraintLayout) findViewById(R.id.layout_bg_template_main);

        img_bg_template_selected.setImageBitmap(background_template.getBitmap_background());

        backimg_select_img_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        for (Frame_in_Background k : background_template.getList_frame()){
            list_image_button.add(Create_Image_View(k.getHor_bias(), k.getVerti_bias(), k.getHeight_per(), k.getRatio()));
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
                    "Please choice enough image to edit", Toast.LENGTH_SHORT).show();
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
                break;
            case R.id.btn_video_template:
                new Done_Template().execute();
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

        img_Edit.setImageResource(R.drawable.reedit);
        img_Edit.setId(View.generateViewId());
        img_Edit.setScaleType(ImageView.ScaleType.CENTER_CROP);
        img_Edit.setVisibility(View.GONE);
        img_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        img_ReChoice.setImageResource(R.drawable.sticker_rotate);
        img_ReChoice.setId(View.generateViewId());
        img_ReChoice.setScaleType(ImageView.ScaleType.CENTER_CROP);
        img_ReChoice.setVisibility(View.GONE);
        img_ReChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id_now = img_Temp.getId();
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityIfNeeded(intent, REQUEST_CODE_FOLDER);
            }
        });

        layout_bg_template_main.addView(img_Edit);
        layout_bg_template_main.addView(img_ReChoice);

        ConstraintSet cs = new ConstraintSet();
        cs.clone(layout_bg_template_main);

        cs.constrainWidth(img_Edit.getId(), ConstraintSet.MATCH_CONSTRAINT);
        cs.constrainHeight(img_Edit.getId(), ConstraintSet.MATCH_CONSTRAINT);

        cs.connect(img_Edit.getId(), ConstraintSet.BOTTOM, img_Temp.getId(), ConstraintSet.TOP, 12);
        cs.connect(img_Edit.getId(), ConstraintSet.END, img_Temp.getId(), ConstraintSet.END);
        cs.connect(img_Edit.getId(), ConstraintSet.START, img_Temp.getId(), ConstraintSet.START);

        cs.setDimensionRatio(img_Edit.getId(), "1:1");
        cs.constrainPercentHeight(img_Edit.getId(), 0.1f);

        cs.constrainWidth(img_ReChoice.getId(), ConstraintSet.MATCH_CONSTRAINT);
        cs.constrainHeight(img_ReChoice.getId(), ConstraintSet.MATCH_CONSTRAINT);

        cs.connect(img_ReChoice.getId(), ConstraintSet.TOP, img_Temp.getId(), ConstraintSet.BOTTOM, 12);
        cs.connect(img_ReChoice.getId(), ConstraintSet.END, img_Temp.getId(), ConstraintSet.END);
        cs.connect(img_ReChoice.getId(), ConstraintSet.START, img_Temp.getId(), ConstraintSet.START);

        cs.setDimensionRatio(img_ReChoice.getId(), "1:1");
        cs.constrainPercentHeight(img_ReChoice.getId(), 0.1f);
        cs.applyTo(layout_bg_template_main);
    }

    private Image_Button Create_Image_View(float hor_bias, float verti_bias, float height_per, String ratio){

        ImageView img_Temp = new ImageView(this);

        Image_Button image_button = new Image_Button(img_Temp, null, null);

        img_Temp.setImageResource(R.drawable.diamond);

        img_Temp.setId(View.generateViewId());
        img_Temp.setBackgroundColor(getColor(R.color.black));
        img_Temp.setScaleType(ImageView.ScaleType.CENTER_CROP);

        img_Temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(id_now != img_Temp.getId()){
                    unClick();
                    id_now = img_Temp.getId();
                }
                if(image_button.isFirst_time()){
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityIfNeeded(intent, REQUEST_CODE_FOLDER);
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
                        list_image_button.get(i).getImg_Main().setImageBitmap(bitmap);
                        list_image_button.get(i).setFirst_time(false);
                        list_image_button.get(i).setDir(getPathFromURI(uri));
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    class Done_Template extends AsyncTask<Void, Void, Void> {

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
            startActivity(new Intent(BgTemplateDetailsActivity.this, VideoThemeActivity.class));

        }
    }
}