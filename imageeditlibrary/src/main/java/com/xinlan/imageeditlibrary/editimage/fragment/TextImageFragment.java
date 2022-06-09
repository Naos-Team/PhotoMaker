package com.xinlan.imageeditlibrary.editimage.fragment;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class TextImageFragment extends BaseEditFragment {
    public static final int INDEX = ModuleConfig.INDEX_IMAGETEXT;

    public static final String TAG = StickerFragment.class.getName();
    public static final String STICKER_FOLDER = "stickers";

    private View mainView;
    private ViewFlipper flipper;
    private View backToMenu;//
    private RecyclerView typeList;//
    private StickerView mStickerView;//
    private ImageTextAdapter mTextImageAdapter;//

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

        //
        backToMenu = mainView.findViewById(R.id.back_to_main_1);
        typeList = (RecyclerView) mainView
                .findViewById(R.id.imgtext_list);
        typeList.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(activity);
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        typeList.setLayoutManager(mLayoutManager);
        typeList.setAdapter(new ImageTextAdapter(this));


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


    public void swipToStickerDetails(String path) {
        mTextImageAdapter.addStickerImages(path);
        flipper.showNext();
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


    public void selectedStickerItem(String path) {
        mStickerView.addBitImage(getImageFromAssetsFile(path));
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
}// end class

