package com.xinlan.imageeditlibrary.editimage.adapter;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xinlan.imageeditlibrary.R;
import com.xinlan.imageeditlibrary.editimage.fragment.TextImageFragment;
import com.xinlan.imageeditlibrary.editimage.view.StickerItem;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageTextAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public DisplayImageOptions imageOption = new DisplayImageOptions.Builder()
            .cacheInMemory(true).showImageOnLoading(R.drawable.yd_image_tx)
            .build();//

    private TextImageFragment mTextImageFragment;
    private ArrayList<Bitmap> listBitmap = new ArrayList<>();
    private List<String> pathList = new ArrayList<String>();//



    public ImageTextAdapter(TextImageFragment fragment, ArrayList<Bitmap> listBitmap) {
        super();
        this.listBitmap = listBitmap;
        this.mTextImageFragment = fragment;
    }

    public void addBitmap(Bitmap bitmap){
        AutomaticPixelClearingTask automaticPixelClearingTask = new AutomaticPixelClearingTask(bitmap);
        automaticPixelClearingTask.execute(50);
    }



    public static class TextImageHolder extends RecyclerView.ViewHolder {
        public ImageView image;

        public TextImageHolder(View itemView) {
            super(itemView);
            this.image = (ImageView) itemView.findViewById(R.id.img);
        }
    }// end inner class

    public void update(int opacity, StickerItem item){

        UpdateAutomaticPixelClearingTask task = new UpdateAutomaticPixelClearingTask(item.getImg_Root());
        task.execute(opacity);
    }

    @Override
    public int getItemCount() {
        return listBitmap.size();
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        TextImageHolder imageHoler = (TextImageHolder) holder;
//        String path = pathList.get(position);
//        ImageLoader.getInstance().displayImage("assets://" + path,
//                imageHoler.image, imageOption);
//        imageHoler.image.setTag(path);
        imageHoler.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AutomaticPixelClearingTask automaticPixelClearingTask = new AutomaticPixelClearingTask(listBitmap.get(position));
                automaticPixelClearingTask.execute(50);
            }
        });
        imageHoler.image.setScaleType(ImageView.ScaleType.FIT_XY);
        imageHoler.image.setImageBitmap(listBitmap.get(position));
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

    class AutomaticPixelClearingTask extends AsyncTask<Integer, Void, Bitmap> {

        private Bitmap bitmap;
        private int opacity = 0;

        public AutomaticPixelClearingTask(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(Integer... ints) {
            Bitmap oldBitmap = bitmap;

            int colorToReplace = oldBitmap.getPixel(0, 0);
            opacity = ints[0];
            int width = oldBitmap.getWidth();
            int height = oldBitmap.getHeight();
            int[] pixels = new int[width * height];
            oldBitmap.getPixels(pixels, 0, width, 0, 0, width, height);

            int rA = Color.alpha(colorToReplace);
            int rR = Color.red(colorToReplace);
            int rG = Color.green(colorToReplace);
            int rB = Color.blue(colorToReplace);

            int pixel, a = ints[0];

            int firstX=999999, firstY=999999, lastX=0, lastY=0;
            boolean check = false;
            for(int y = 0; y < height; ++y){
                for (int x = 0; x < width; ++x) {
                    int index = y * width + x;
                    pixel = pixels[index];
                    int rrA = Color.alpha(pixel);
                    int rrR = Color.red(pixel);
                    int rrG = Color.green(pixel);
                    int rrB = Color.blue(pixel);
                    if ( (rrR >= rR + a || rrR <= rR - a)
                            ||( rrG >= rG + a || rrG <= rG - a)
                            || (rrB >= rB + a || rrB <= rB - a)){
                        firstX= (x < firstX) ? x : firstX;
                        firstY= (y < firstY) ? y : firstY;
                        lastX= (x > lastX) ? x : lastX;
                        lastY= (y > lastY) ? y : lastY;
                    }
                }
            }

            // iteration through pixels
            for (int y = 0; y < height; ++y) {
                for (int x = 0; x < width; ++x) {
                    // get current index in 2D-matrix
                    int index = y * width + x;
                    pixel = pixels[index];
                    int rrA = Color.alpha(pixel);
                    int rrR = Color.red(pixel);
                    int rrG = Color.green(pixel);
                    int rrB = Color.blue(pixel);

                    if (  rrA <= rA + a && rrA >= rA - a
                            && rrR <= rR + a && rrR >= rR - a
                            && rrG <= rG + a && rrG >= rG - a
                            && rrB <= rB + a && rrB >= rB - a ) {
                        pixels[index] = Color.TRANSPARENT;
                    }
                }
            }

            Bitmap newBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            newBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            Bitmap resizedBmp = Bitmap.createBitmap(newBitmap, firstX, firstY, lastX-firstX, lastY-firstY);
            return resizedBmp;
        }

        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            mTextImageFragment.selectedStickerItem(result, opacity, bitmap);
        }
    }

    class UpdateAutomaticPixelClearingTask extends AsyncTask<Integer, Void, Bitmap> {

        private Bitmap bitmap;
        private int opacity = 0;

        public UpdateAutomaticPixelClearingTask(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
//            //Toast.makeText(mTextImageFragment.getContext(), "Please wait a few seconds", Toast.LENGTH_LONG);
//            mTextImageFragment.updateStickerItem(bitmap, 100);
        }

        @Override
        protected Bitmap doInBackground(Integer... ints) {
            Bitmap oldBitmap = bitmap;

            int colorToReplace = oldBitmap.getPixel(0, 0);
            opacity = ints[0];
            int width = oldBitmap.getWidth();
            int height = oldBitmap.getHeight();
            int[] pixels = new int[width * height];
            oldBitmap.getPixels(pixels, 0, width, 0, 0, width, height);

            int rA = Color.alpha(colorToReplace);
            int rR = Color.red(colorToReplace);
            int rG = Color.green(colorToReplace);
            int rB = Color.blue(colorToReplace);

            int pixel, a = ints[0];

            int firstX = 999999, firstY = 999999, lastX = 0, lastY = 0;
            boolean check = false;
            for (int y = 0; y < height; ++y) {
                for (int x = 0; x < width; ++x) {
                    int index = y * width + x;
                    pixel = pixels[index];
                    int rrA = Color.alpha(pixel);
                    int rrR = Color.red(pixel);
                    int rrG = Color.green(pixel);
                    int rrB = Color.blue(pixel);
                    if ((rrR >= rR + a || rrR <= rR - a)
                            || (rrG >= rG + a || rrG <= rG - a)
                            || (rrB >= rB + a || rrB <= rB - a)) {
                        firstX = (x < firstX) ? x : firstX;
                        firstY = (y < firstY) ? y : firstY;
                        lastX = (x > lastX) ? x : lastX;
                        lastY = (y > lastY) ? y : lastY;
                    }
                }
            }

            // iteration through pixels
            for (int y = 0; y < height; ++y) {
                for (int x = 0; x < width; ++x) {
                    // get current index in 2D-matrix
                    int index = y * width + x;
                    pixel = pixels[index];
                    int rrA = Color.alpha(pixel);
                    int rrR = Color.red(pixel);
                    int rrG = Color.green(pixel);
                    int rrB = Color.blue(pixel);

                    if (rrA <= rA + a && rrA >= rA - a
                            && rrR <= rR + a && rrR >= rR - a
                            && rrG <= rG + a && rrG >= rG - a
                            && rrB <= rB + a && rrB >= rB - a) {
                        pixels[index] = Color.TRANSPARENT;
                    }
                }
            }

            Bitmap newBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            newBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            Bitmap resizedBmp = Bitmap.createBitmap(newBitmap, firstX, firstY, lastX - firstX, lastY - firstY);
            return resizedBmp;
        }

        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            mTextImageFragment.updateStickerItem(result, opacity);
        }
    }

}// end class