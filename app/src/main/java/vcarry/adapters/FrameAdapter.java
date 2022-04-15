package vcarry.adapters;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.kessi.photovideomaker.KessiApplication;
import com.kessi.photovideomaker.R;
import com.kessi.photovideomaker.activities.videoeditor.VideoThemeActivity;
import com.kessi.photovideomaker.util.AdManager;

import java.io.FileOutputStream;

import vcarry.util.FileUtils;
import vcarry.util.ScalingUtilities;
import vcarry.util.Utils;



public class FrameAdapter extends RecyclerView.Adapter<FrameAdapter.Holder> {
    VideoThemeActivity activity;
    private KessiApplication application;
    private OnItemClickListner<Object> clickListner;
    private int[] drawable = new int[]{ -1,R.drawable.f_1,
            R.drawable.f_2, R.drawable.f_3, R.drawable.f_4, R.drawable.f_5,
            R.drawable.f_6, R.drawable.f_7, R.drawable.f_8, R.drawable.f_9,
            R.drawable.f_10};
    private int[] drawable_thumb = new int[]{ R.drawable.none,R.drawable.f_1,
            R.drawable.f_2, R.drawable.f_3, R.drawable.f_4, R.drawable.f_5,
            R.drawable.f_6, R.drawable.f_7, R.drawable.f_8, R.drawable.f_9,
            R.drawable.f_10};
    private RequestManager glide;
    private LayoutInflater inflater;
    int lastPos = 0;

    int position = 0;
    public class Holder extends RecyclerView.ViewHolder {
        ImageView cbSelect;
        private View clickableView;
        private ImageView ivThumb;

        public Holder(View v) {
            super(v);
            this.cbSelect = (ImageView) v.findViewById(R.id.cbSelect);
            this.ivThumb = (ImageView) v.findViewById(R.id.ivThumb);
            this.clickableView = v.findViewById(R.id.clickableView);
        }
    }

    public FrameAdapter(VideoThemeActivity activity) {
        this.activity = activity;
        this.application = KessiApplication.getInstance();
        this.inflater = LayoutInflater.from(activity);
        this.glide = Glide.with(activity);
    }

    public void setOnItemClickListner(OnItemClickListner<Object> clickListner) {
        this.clickListner = clickListner;
    }

    public int getItemCount() {
        return this.drawable.length;
    }

    public int getItem(int pos) {
        return this.drawable[pos];
    }

    public int getItem1(int pos) {
        return this.drawable_thumb[pos];
    }

    public void onBindViewHolder(final Holder holder, @SuppressLint("RecyclerView") final int pos) {
        final int themes = getItem(pos);
        final int themes1 = getItem1(pos);
        holder.ivThumb.setScaleType(ScaleType.FIT_XY);
        Glide.with(this.application).load(Integer.valueOf(themes1)).into(holder.ivThumb);


        holder.cbSelect.setVisibility(View.GONE);
        if (position == pos){
            holder.cbSelect.setVisibility(View.VISIBLE);
        }

        holder.clickableView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (themes != activity.getFrame()) {
                    if (!AdManager.isloadFbMAXAd) {
                        AdManager.adCounter++;
                        AdManager.showInterAd(activity, null,0);
                    } else {
                        AdManager.adCounter++;
                        AdManager.showMaxInterstitial(activity, null,0);
                    }


                    position = pos;
                    activity.setFrame(themes);
                    if(themes==-1)
                    {
                    }
                    if (themes != -1) {

                        notifyItemChanged(lastPos);
                        notifyItemChanged(pos);
                        lastPos = pos;
                        Utils.framePostion = pos;
                        FileUtils.deleteFile(FileUtils.frameFile);
                        try {
                            Bitmap bm = ScalingUtilities.scaleCenterCrop(BitmapFactory
                                            .decodeResource(activity.getResources(), themes),
                                    KessiApplication.VIDEO_WIDTH, KessiApplication.VIDEO_HEIGHT);
                            FileOutputStream outStream = new FileOutputStream(FileUtils.frameFile);
                            bm.compress(CompressFormat.PNG, 100, outStream);
                            outStream.flush();
                            outStream.close();
                            bm.recycle();
                            System.gc();
                        } catch (Exception e) {
                        }
                    }
                    else
                    {
                        notifyItemChanged(lastPos);
                        notifyItemChanged(pos);
                        lastPos = pos;
                        Utils.framePostion = pos;
//                        holder.cbSelect.setChecked(false);
                    }
                }
            }
        });
    }

    public Holder onCreateViewHolder(ViewGroup parent, int pos) {
        View item = this.inflater.inflate(R.layout.frame_items, parent, false);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((activity.getResources()
                .getDisplayMetrics().widthPixels* 200 / 1080 ),
                (activity.getResources()
                        .getDisplayMetrics().widthPixels * 200 / 1080));
        params.setMargins(10,10,10,10);

        item.setLayoutParams(params);
        return new Holder(item);
    }
}
