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

    public AdapterMainPhoto(ArrayList<String> arrayList_path, MainActivity.onClickPhotoListener listener) {
        this.listener = listener;
        this.arrayList_path = arrayList_path;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo_frame, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String path = arrayList_path.get(position);

        Picasso.get()
                .load(path)
                .into(holder.iv);

        holder.delete.setOnClickListener(v->{
            listener.onDelete(position);
        });
        holder.edit.setOnClickListener(v -> {
            listener.onEdit(position);
        });

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView iv, edit, delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
            iv = itemView.findViewById(R.id.item_img);
        }
    }
}
