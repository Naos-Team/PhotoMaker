package com.xinlan.imageeditlibrary.editimage.adapter;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xinlan.imageeditlibrary.R;
import com.xinlan.imageeditlibrary.editimage.fragment.TextImageFragment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageTextAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public DisplayImageOptions imageOption = new DisplayImageOptions.Builder()
            .cacheInMemory(true).showImageOnLoading(R.drawable.yd_image_tx)
            .build();//

    private TextImageFragment mTextImageFragment;
    private ImageClick mImageClick = new ImageClick();
    private List<String> pathList = new ArrayList<String>();//

    public ImageTextAdapter(TextImageFragment fragment) {
        super();
        this.mTextImageFragment = fragment;
    }

    public class TextImageHolder extends RecyclerView.ViewHolder {
        public ImageView image;

        public TextImageHolder(View itemView) {
            super(itemView);
            this.image = (ImageView) itemView.findViewById(R.id.img);
        }
    }// end inner class

    @Override
    public int getItemCount() {
        return pathList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewtype) {
        View v = null;
        v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.view_sticker_item, parent, false);
       TextImageHolder holer = new TextImageHolder(v);
        return holer;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        StickerAdapter.ImageHolder imageHoler = (StickerAdapter.ImageHolder) holder;
        String path = pathList.get(position);
        ImageLoader.getInstance().displayImage("assets://" + path,
                imageHoler.image, imageOption);
        imageHoler.image.setTag(path);
        imageHoler.image.setOnClickListener(mImageClick);

        LinearLayout.LayoutParams paramsBtn = new LinearLayout.LayoutParams(
                mTextImageFragment.getActivity().getResources().getDisplayMetrics().widthPixels * 164 / 1080,
                mTextImageFragment.getActivity().getResources().getDisplayMetrics().heightPixels * 164 / 1920);
        paramsBtn.gravity = Gravity.CENTER;
        paramsBtn.leftMargin = 15;
        imageHoler.image.setLayoutParams(paramsBtn);
    }

    public void addStickerImages(String folderPath) {
        pathList.clear();
        try {
            String[] files = mTextImageFragment.getActivity().getAssets()
                    .list(folderPath);
            for (String name : files) {
                pathList.add(folderPath + File.separator + name);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.notifyDataSetChanged();
    }

    private final class ImageClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String data = (String) v.getTag();
            //System.out.println("data---->" + data);
            mTextImageFragment.selectedStickerItem(data);
        }
    }// end inner class

}// end class