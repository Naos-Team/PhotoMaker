package com.xinlan.imageeditlibrary.editimage.fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xinlan.imageeditlibrary.R;
import com.xinlan.imageeditlibrary.editimage.EditImageActivity;
import com.xinlan.imageeditlibrary.editimage.ModuleConfig;
import com.xinlan.imageeditlibrary.editimage.adapter.ImageTextAdapter;
import com.xinlan.imageeditlibrary.editimage.adapter.StickerAdapter;
import com.xinlan.imageeditlibrary.editimage.model.StickerBean;
import com.xinlan.imageeditlibrary.editimage.task.StickerTask;
import com.xinlan.imageeditlibrary.editimage.view.StickerItem;
import com.xinlan.imageeditlibrary.editimage.view.StickerView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class TextImageFragment extends BaseEditFragment {
    public static final int INDEX = ModuleConfig.INDEX_IMAGETEXT;

    public static final String TAG = StickerFragment.class.getName();
    public static final String STICKER_FOLDER = "stickers";
    private int REQUEST_CODE_FOLDER = 456;
    private static final int ACTION_REQUEST_EDITIMAGE = 9;

    private View mainView;
    private ViewFlipper flipper;
    private View backToMenu;//
    private RecyclerView typeList;//
    private StickerView mStickerView;//
    private ImageTextAdapter mTextImageAdapter;//
    private ArrayList<Bitmap> list_Bitmap = new ArrayList<>();
    private ImageView btn_choice;

    private List<StickerBean> stickerBeanList = new ArrayList<StickerBean>();

    private SaveStickersTask mSaveTask;

    public static TextImageFragment newInstance() {
        return new TextImageFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        mainView = inflater.inflate(R.layout.fragment_edit_image_text,
                null);
        //loadStickersData();

        return mainView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.mStickerView = activity.mStickerView;
        flipper = (ViewFlipper) mainView.findViewById(R.id.flipper);
        flipper.setInAnimation(activity, R.anim.in_bottom_to_top);
        flipper.setOutAnimation(activity, R.anim.out_bottom_to_top);
        btn_choice = (ImageView) mainView.findViewById(R.id.btn_choice);

        btn_choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                getActivity().startActivityFromFragment(TextImageFragment.this, Intent.createChooser(intent,
                        "Choose picture"), REQUEST_CODE_FOLDER);
            }
        });
        //
        backToMenu = mainView.findViewById(R.id.back_to_main_1);
        typeList = (RecyclerView) mainView
                .findViewById(R.id.imgtext_list);
        typeList.setHasFixedSize(true);
        list_Bitmap.clear();
        list_Bitmap.add(BitmapFactory.decodeResource(getResources(),  R.drawable.text_image));
        list_Bitmap.add(BitmapFactory.decodeResource(getResources(),  R.drawable.text_image2));
        list_Bitmap.add(BitmapFactory.decodeResource(getResources(),  R.drawable.text_image3));
        list_Bitmap.add(BitmapFactory.decodeResource(getResources(),  R.drawable.text_image4));
        list_Bitmap.add(BitmapFactory.decodeResource(getResources(),  R.drawable.text_image_5));
        list_Bitmap.add(BitmapFactory.decodeResource(getResources(),  R.drawable.text_image_6));
        list_Bitmap.add(BitmapFactory.decodeResource(getResources(),  R.drawable.text_image_7));
        list_Bitmap.add(BitmapFactory.decodeResource(getResources(),  R.drawable.text_image_8));
        list_Bitmap.add(BitmapFactory.decodeResource(getResources(),  R.drawable.text_image_9));
        list_Bitmap.add(BitmapFactory.decodeResource(getResources(),  R.drawable.text_image_10));
        list_Bitmap.add(BitmapFactory.decodeResource(getResources(),  R.drawable.text_image_11));
        list_Bitmap.add(BitmapFactory.decodeResource(getResources(),  R.drawable.text_image_12));
        list_Bitmap.add(BitmapFactory.decodeResource(getResources(),  R.drawable.text_image_13));
        list_Bitmap.add(BitmapFactory.decodeResource(getResources(),  R.drawable.text_image_14));
        list_Bitmap.add(BitmapFactory.decodeResource(getResources(),  R.drawable.text_image_15));
        list_Bitmap.add(BitmapFactory.decodeResource(getResources(),  R.drawable.text_image_16));
        list_Bitmap.add(BitmapFactory.decodeResource(getResources(),  R.drawable.text_image_17));
        list_Bitmap.add(BitmapFactory.decodeResource(getResources(),  R.drawable.text_image_18));
        list_Bitmap.add(BitmapFactory.decodeResource(getResources(),  R.drawable.text_image_19));
        list_Bitmap.add(BitmapFactory.decodeResource(getResources(),  R.drawable.text_image_20));
        list_Bitmap.add(BitmapFactory.decodeResource(getResources(),  R.drawable.text_image_21));
        list_Bitmap.add(BitmapFactory.decodeResource(getResources(),  R.drawable.text_image_22));
        list_Bitmap.add(BitmapFactory.decodeResource(getResources(),  R.drawable.text_image_23));
        list_Bitmap.add(BitmapFactory.decodeResource(getResources(),  R.drawable.text_image_24));
        list_Bitmap.add(BitmapFactory.decodeResource(getResources(),  R.drawable.text_image_25));
        list_Bitmap.add(BitmapFactory.decodeResource(getResources(),  R.drawable.text_image_26));
        list_Bitmap.add(BitmapFactory.decodeResource(getResources(),  R.drawable.text_image_27));
        list_Bitmap.add(BitmapFactory.decodeResource(getResources(),  R.drawable.text_image_28));
        list_Bitmap.add(BitmapFactory.decodeResource(getResources(),  R.drawable.text_image_29));
        list_Bitmap.add(BitmapFactory.decodeResource(getResources(),  R.drawable.text_image_30));
        list_Bitmap.add(BitmapFactory.decodeResource(getResources(),  R.drawable.text_image_31));
        list_Bitmap.add(BitmapFactory.decodeResource(getResources(),  R.drawable.text_image_32));
        list_Bitmap.add(BitmapFactory.decodeResource(getResources(),  R.drawable.text_image_33));
        list_Bitmap.add(BitmapFactory.decodeResource(getResources(),  R.drawable.text_image_34));
        list_Bitmap.add(BitmapFactory.decodeResource(getResources(),  R.drawable.text_image_35));
        list_Bitmap.add(BitmapFactory.decodeResource(getResources(),  R.drawable.text_image_36));
        list_Bitmap.add(BitmapFactory.decodeResource(getResources(),  R.drawable.text_image_37));
        list_Bitmap.add(BitmapFactory.decodeResource(getResources(),  R.drawable.text_image_38));
        list_Bitmap.add(BitmapFactory.decodeResource(getResources(),  R.drawable.text_image_39));
        list_Bitmap.add(BitmapFactory.decodeResource(getResources(),  R.drawable.text_image_40));





        LinearLayoutManager mLayoutManager = new LinearLayoutManager(activity);
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        typeList.setLayoutManager(mLayoutManager);
        mTextImageAdapter = new ImageTextAdapter(this, list_Bitmap);
        typeList.setAdapter(mTextImageAdapter);


        LinearLayout.LayoutParams paramsBtn = new LinearLayout.LayoutParams(
                getActivity().getResources().getDisplayMetrics().widthPixels * 120 / 1080,
                getActivity().getResources().getDisplayMetrics().heightPixels * 176 / 1920);
        paramsBtn.gravity = Gravity.BOTTOM;
        backToMenu.setLayoutParams(paramsBtn);

        backToMenu.setOnClickListener(new BackToMenuClick());//
    }

    @Override
    public void onShow() {
        activity.mode = EditImageActivity.MODE_IMATEXT;
        activity.mTextImageFragment.getmStickerView().setVisibility(
                View.VISIBLE);
        activity.bannerFlipper.showNext();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private Bitmap getImageFromAssetsFile(String fileName) {
        Bitmap image = null;
        AssetManager am = getResources().getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }


    public void selectedStickerItem(Bitmap bitmap, int opacity) {
        mStickerView.addBitImageWithOpacity(bitmap, opacity);
    }

    public StickerView getmStickerView() {
        return mStickerView;
    }

    public void setmStickerView(StickerView mStickerView) {
        this.mStickerView = mStickerView;
    }


    private final class BackToMenuClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            backToMain();
        }
    }// end inner class

    @Override
    public void backToMain() {
        activity.mode = EditImageActivity.MODE_NONE;
        activity.bottomGallery.setCurrentItem(0);
        mStickerView.setVisibility(View.GONE);
        activity.bannerFlipper.showPrevious();
    }


    private final class SaveStickersTask extends StickerTask {
        public SaveStickersTask(EditImageActivity activity) {
            super(activity);
        }

        @Override
        public void handleImage(Canvas canvas, Matrix m) {
            LinkedHashMap<Integer, StickerItem> addItems = mStickerView.getBank();
            for (Integer id : addItems.keySet()) {
                StickerItem item = addItems.get(id);
                item.matrix.postConcat(m);//
                canvas.drawBitmap(item.bitmap, item.matrix, null);
            }// end for
        }

        @Override
        public void onPostResult(Bitmap result) {
            mStickerView.clear();
            activity.changeMainBitmap(result,true);
            backToMain();
        }
    }// end inner class


    public void applyStickers() {

        if (mSaveTask != null) {
            mSaveTask.cancel(true);
        }
        mSaveTask = new SaveStickersTask((EditImageActivity) getActivity());
        mSaveTask.execute(activity.getMainBit());
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //chọn hình trong file;
        if(requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                mTextImageAdapter.addBitmap(bitmap);

            } catch (FileNotFoundException e) {
                Toast.makeText(getActivity(), "Something wrong! You can not use this photo!", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }else if (requestCode == ACTION_REQUEST_EDITIMAGE && resultCode == RESULT_OK){
        }
    }
}// end class

