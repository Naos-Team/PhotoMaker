package com.kessi.photovideomaker.activities;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kessi.photovideomaker.R;
import com.kessi.photovideomaker.activities.songpicker.AdapterOnlineMusic;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterMainPhoto extends RecyclerView.Adapter<AdapterMainPhoto.MyViewHolder> {

    MainActivity.onClickPhotoListener listener;
    Context mContext;
    ArrayList<String> arrayList_path;
    boolean isGrid = false;

    public AdapterMainPhoto(boolean isGrid, ArrayList<String> arrayList_path, MainActivity.onClickPhotoListener listener) {
        this.listener = listener;
        this.arrayList_path = arrayList_path;
        this.isGrid = isGrid;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(isGrid?R.layout.item_photo_frame_grid:R.layout.item_photo_frame, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String path = arrayList_path.get(position);

        Picasso.get()
                .load(Uri.parse("file://" + path))
                .into(holder.iv);

        holder.delete.setOnClickListener(v->{
            listener.onDelete(position);
        });

        holder.iv.setOnClickListener(v->{
            listener.onClick(position);
        });

    }

    @Override
    public int getItemCount() {
        return arrayList_path.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView iv, edit, delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            delete = itemView.findViewById(R.id.delete);
            iv = itemView.findViewById(R.id.item_img);
        }
    }
}
