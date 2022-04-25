package com.kessi.photovideomaker.activities.songpicker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kessi.photovideomaker.R;
import com.kessi.photovideomaker.activities.songpicker.roomdb.OnlineSongItem;

import java.util.ArrayList;

public class AdapterOnlineMusic extends RecyclerView.Adapter<AdapterOnlineMusic.MyViewHolder> {

    ArrayList<OnlineSongItem> arrayList;
    OnDownloadSongListener listener;

    public AdapterOnlineMusic(ArrayList<OnlineSongItem> arrayList, OnDownloadSongListener listener) {
        this.arrayList = arrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterOnlineMusic.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_online_music, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        OnlineSongItem item = arrayList.get(position);

        if(item.isDownload){
            holder.btn_redownload.setVisibility(View.VISIBLE);
            holder.btn_download.setVisibility(View.GONE);
        }else{
            holder.btn_redownload.setVisibility(View.GONE);
            holder.btn_download.setVisibility(View.VISIBLE);
        }

        holder.tv_name.setText(item.getTitle());
        holder.btn_download.setOnClickListener(v1 ->{
            listener.onDownload(item, holder.progressBar, holder.btn_download, holder.btn_redownload);
        });

        holder.btn_redownload.setOnClickListener(v1 -> {
            listener.onDownload(item, holder.progressBar, holder.btn_download, holder.btn_redownload);
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_name;
        ProgressBar progressBar;
        ImageView btn_download, btn_redownload;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.tv_name);
            progressBar = itemView.findViewById(R.id.progressBar);
            btn_download = itemView.findViewById(R.id.btn_download);
            btn_redownload = itemView.findViewById(R.id.btn_redownload);
        }
    }
}
