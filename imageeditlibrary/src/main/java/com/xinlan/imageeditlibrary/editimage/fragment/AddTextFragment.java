package com.xinlan.imageeditlibrary.editimage.fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.xinlan.imageeditlibrary.R;
import com.xinlan.imageeditlibrary.editimage.EditImageActivity;
import com.xinlan.imageeditlibrary.editimage.ModuleConfig;
import com.xinlan.imageeditlibrary.editimage.task.StickerTask;
import com.xinlan.imageeditlibrary.editimage.ui.ColorPicker;
import com.xinlan.imageeditlibrary.editimage.ui.FontPicker;
import com.xinlan.imageeditlibrary.editimage.ui.pickTypeListener;
import com.xinlan.imageeditlibrary.editimage.view.TextStickerView;



public class AddTextFragment extends BaseEditFragment implements TextWatcher {
    public static final int INDEX = ModuleConfig.INDEX_ADDTEXT;
    public static final String TAG = AddTextFragment.class.getName();
    private View mainView;
    private View backToMenu;//
    private Typeface font;

    private EditText mInputText;//
    private ImageView mTextColorSelector, imv_font;
    private TextStickerView mTextStickerView;//
    private CheckBox mAutoNewLineCheck;

    private ColorPicker mColorPicker;
    private FontPicker fontPicker;

    private int mTextColor = Color.WHITE;
    private InputMethodManager imm;

    private SaveTextStickerTask mSaveTask;

    public static AddTextFragment newInstance() {
        AddTextFragment fragment = new AddTextFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mainView = inflater.inflate(R.layout.fragment_edit_image_add_text, null);
        return mainView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //mTextStickerView = (TextStickerView) mainView.findViewById(R.id.text_sticker_panel);
        mInputText = (EditText) mainView.findViewById(R.id.text_input);
        mTextStickerView = (TextStickerView) getActivity().findViewById(R.id.text_sticker_panel);

        imv_font = (ImageView) mainView.findViewById(R.id.imv_font);

        backToMenu = mainView.findViewById(R.id.back_to_main);

        mTextColorSelector = (ImageView) mainView.findViewById(R.id.text_color);
        mAutoNewLineCheck = (CheckBox) mainView.findViewById(R.id.check_auto_newline);

        backToMenu.setOnClickListener(new BackToMenuClick());//
        LinearLayout.LayoutParams paramsBtn = new LinearLayout.LayoutParams(
                getActivity().getResources().getDisplayMetrics().widthPixels * 120 / 1080,
                getActivity().getResources().getDisplayMetrics().heightPixels * 176 / 1920);
        paramsBtn.gravity = Gravity.BOTTOM;
        backToMenu.setLayoutParams(paramsBtn);


        mColorPicker = new ColorPicker(getActivity(), 255, 0, 0);
        mTextColorSelector.setOnClickListener(new SelectColorBtnClick());
        mInputText.addTextChangedListener(this);
        if (mInputText!=null){
            mTextStickerView.setEditText(mInputText);
        }



        mTextColorSelector.setBackgroundColor(mColorPicker.getColor());
        mTextStickerView.setTextColor(mColorPicker.getColor());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                getActivity().getResources().getDisplayMetrics().widthPixels * 80 / 1080,
                getActivity().getResources().getDisplayMetrics().heightPixels * 80 / 1920);
        params.gravity = Gravity.CENTER_VERTICAL;
        params.rightMargin = 10;
        mTextColorSelector.setLayoutParams(params);

        imv_font.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View v) {
                FontPicker fontPicker = new FontPicker(getContext(),
                        new pickTypeListener() {
                            @Override
                            public void onChoice(Typeface t) {
                                font = t;
                                mInputText.setTypeface(font);
                                mTextStickerView.setTf(font);
                            }
                        });
                fontPicker.show();

//                String text = mInputText.getText().toString();
                //mTextStickerView.setText(text);
            }
        });
    }

    @Override
    public void afterTextChanged(Editable s) {
        //mTextStickerView change
        String text = s.toString().trim();
        mTextStickerView.setText(text);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    private final class SelectColorBtnClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            mColorPicker.show();
            Button okColor = (Button) mColorPicker.findViewById(R.id.okColorButton);
            okColor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeTextColor(mColorPicker.getColor());
                    mColorPicker.dismiss();

                }
            });
        }
    }//end inner class


    private void changeTextColor(int newColor) {
        this.mTextColor = newColor;
        mTextColorSelector.setBackgroundColor(mTextColor);
        mTextStickerView.setTextColor(mTextColor);
    }

    public void hideInput() {
        if (getActivity() != null && getActivity().getCurrentFocus() != null && isInputMethodShow()) {
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public boolean isInputMethodShow() {
        return imm.isActive();
    }



    private final class BackToMenuClick implements OnClickListener {
        @Override
        public void onClick(View v) {
            backToMain();
        }
    }// end class


    @Override
    public void backToMain() {
        hideInput();
        activity.mode = EditImageActivity.MODE_NONE;
        activity.bottomGallery.setCurrentItem(MainMenuFragment.INDEX);
        activity.mainImage.setVisibility(View.VISIBLE);
        activity.bannerFlipper.showPrevious();
        mTextStickerView.setVisibility(View.GONE);
    }

    @Override
    public void onShow() {
        activity.mode = EditImageActivity.MODE_TEXT;
        activity.mainImage.setImageBitmap(activity.getMainBit());
        activity.bannerFlipper.showNext();
        mTextStickerView.setVisibility(View.VISIBLE);
        mInputText.clearFocus();
    }

    public void applyTextImage() {
        if (mSaveTask != null) {
            mSaveTask.cancel(true);
        }


        mSaveTask = new SaveTextStickerTask(activity);
        mSaveTask.execute(activity.getMainBit());
    }


    private final class SaveTextStickerTask extends StickerTask {

        public SaveTextStickerTask(EditImageActivity activity) {
            super(activity);
        }

        @Override
        public void handleImage(Canvas canvas, Matrix m) {
            float[] f = new float[9];
            m.getValues(f);
            int dx = (int) f[Matrix.MTRANS_X];
            int dy = (int) f[Matrix.MTRANS_Y];
            float scale_x = f[Matrix.MSCALE_X];
            float scale_y = f[Matrix.MSCALE_Y];
            canvas.save();
            canvas.translate(dx, dy);
            canvas.scale(scale_x, scale_y);
            //System.out.println("scale = " + scale_x + "       " + scale_y + "     " + dx + "    " + dy);
            mTextStickerView.drawText(canvas, mTextStickerView.layout_x,
                    mTextStickerView.layout_y, mTextStickerView.mScale, mTextStickerView.mRotateAngle, font);
            canvas.restore();
        }

        @Override
        public void onPostResult(Bitmap result) {
            mTextStickerView.clearTextContent();
            mTextStickerView.resetView();

            activity.changeMainBitmap(result , true);
            backToMain();
        }
    }//end inner class

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mSaveTask != null && !mSaveTask.isCancelled()) {
            mSaveTask.cancel(true);
        }
    }


}// end class
