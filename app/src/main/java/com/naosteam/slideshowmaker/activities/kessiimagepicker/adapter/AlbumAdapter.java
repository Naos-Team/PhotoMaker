package com.naosteam.slideshowmaker.activities.kessiimagepicker.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.naosteam.slideshowmaker.R;
import com.naosteam.slideshowmaker.activities.kessiimagepicker.model.ImageModel;

import java.io.File;
import java.util.ArrayList;


public class AlbumAdapter extends ArrayAdapter<ImageModel> {
    Context context;
    ArrayList<ImageModel> data = new ArrayList();
    int layoutResourceId;
    com.naosteam.slideshowmaker.activities.kessiimagepicker.myinterface.OnAlbum onItem;
    int pWidthItem = 0, pHeightItem = 0;
//    int pWHIconNext = 0;

    static class RecordHolder {
        //        ImageView iconNext;
        ImageView imageItem;
        LinearLayout layoutRoot;
        TextView txtPath;
        TextView txtTitle;

        RecordHolder() {
        }
    }

    public AlbumAdapter(Context context, int layoutResourceId, ArrayList<ImageModel> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        this.pWidthItem = getDisplayInfo((Activity) context).widthPixels * 200 / 1080;
        this.pHeightItem = getDisplayInfo((Activity) context).heightPixels * 200 / 1920;
//        this.pWHIconNext = this.pWidthItem / 4;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        RecordHolder holder;
        View row = convertView;
        if (row == null) {
            row = ((Activity) this.context).getLayoutInflater().inflate(this.layoutResourceId, parent, false);
            holder = new RecordHolder();
            holder.txtTitle = (TextView) row.findViewById(R.id.name_album);
            holder.txtPath = (TextView) row.findViewById(R.id.count);
            holder.imageItem = (ImageView) row.findViewById(R.id.icon_album);
//            holder.iconNext = (ImageView) row.findViewById(R.id.iconNext);
//            holder.layoutRoot = (LinearLayout) row.findViewById(R.id.layoutRoot);
//            holder.layoutRoot.getLayoutParams().height = this.pWidthItem+15;
//            holder.imageItem.getLayoutParams().width = this.pWidthItem;
//            holder.imageItem.getLayoutParams().height = this.pHeightItem;
//            holder.iconNext.getLayoutParams().width = this.pWHIconNext;
//            holder.iconNext.getLayoutParams().height = this.pWHIconNext;
            row.setTag(holder);
        } else {
            holder = (RecordHolder) row.getTag();
        }
        ImageModel item = (ImageModel) this.data.get(position);
        holder.txtTitle.setText(item.getName());
        holder.txtPath.setText(String.valueOf(item.getCount()));
//        holder.imageItem.setBackground();
        Glide.with(this.context).load(new File(item.getPathFile())).asBitmap().placeholder(R.drawable.piclist_icon_default).into(holder.imageItem);

        row.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (AlbumAdapter.this.onItem != null) {
                    AlbumAdapter.this.onItem.OnItemAlbumClick(position);
                }
            }
        });
        return row;
    }

    public com.naosteam.slideshowmaker.activities.kessiimagepicker.myinterface.OnAlbum getOnItem() {
        return this.onItem;
    }

    public void setOnItem(com.naosteam.slideshowmaker.activities.kessiimagepicker.myinterface.OnAlbum onItem) {
        this.onItem = onItem;
    }

    public static DisplayMetrics getDisplayInfo(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm;
    }
}
