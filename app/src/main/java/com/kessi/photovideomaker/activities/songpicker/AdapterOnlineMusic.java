package com.kessi.photovideomaker.activities.songpicker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.kessi.photovideomaker.R;
import com.kessi.photovideomaker.activities.songpicker.roomdb.OnlineSongItem;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class AdapterOnlineMusic extends RecyclerView.Adapter<AdapterOnlineMusic.MyViewHolder> {

    ArrayList<OnlineSongItem> arrayList;
    OnDownloadSongListener listener;
    Context mContext;

    public AdapterOnlineMusic(ArrayList<OnlineSongItem> arrayList, OnDownloadSongListener listener) {
        this.arrayList = arrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new AdapterOnlineMusic.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_online_music, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        OnlineSongItem item = arrayList.get(position);
        MediaPlayer mp = new MediaPlayer();
        Boolean isStopped = false;

        holder.btn_pause.setVisibility(View.GONE);
        holder.btn_play.setVisibility(View.VISIBLE);
        holder.progressBar_play.setVisibility(View.GONE);

        if (item.isDownload) {
            holder.btn_redownload.setVisibility(View.VISIBLE);
            holder.btn_download.setVisibility(View.GONE);
        } else {
            holder.btn_redownload.setVisibility(View.GONE);
            holder.btn_download.setVisibility(View.VISIBLE);
        }
        holder.tv_artist.setText(item.getArtist());
        holder.tv_name.setText(item.getTitle());
        holder.btn_download.setOnClickListener(v1 -> {
            listener.onDownload(item, holder.progressBar, holder.btn_download, holder.btn_redownload);
        });

        holder.btn_redownload.setOnClickListener(v1 -> {
            listener.onDownload(item, holder.progressBar, holder.btn_download, holder.btn_redownload);
        });

        CountDownTimer countdown = new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                if(mp.isPlaying()){
                    holder.btn_pause.setVisibility(View.GONE);
                    holder.btn_play.setVisibility(View.VISIBLE);
                    mp.stop();
                }
            }
        };

        try {
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mp.setDataSource(mContext, Uri.parse(item.getUrl()));
            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
                @Override
                public void onPrepared(MediaPlayer mp) {
                    if(!mp.isPlaying()){
                        holder.progressBar_play.setVisibility(View.GONE);
                        holder.btn_pause.setVisibility(View.VISIBLE);
                        holder.btn_play.setVisibility(View.GONE);
                        mp.start();
                        countdown.start();
//                        listener.onPlaySong(holder.getAbsoluteAdapterPosition());
                    }
                }
            });
        } catch (Exception e) {
            holder.btn_pause.setVisibility(View.GONE);
            holder.btn_play.setVisibility(View.VISIBLE);
            holder.progressBar_play.setVisibility(View.GONE);
            Toast.makeText(mContext, "Something went wrong! Please try again!", Toast.LENGTH_SHORT).show();
            Log.e("Err", e.getMessage());
        }

        holder.btn_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mp.isPlaying()){
                    mp.stop();
                    countdown.cancel();
                    holder.btn_pause.setVisibility(View.GONE);
                    holder.btn_play.setVisibility(View.VISIBLE);
                }
            }
        });

        holder.btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.progressBar_play.setVisibility(View.VISIBLE);
                holder.btn_pause.setVisibility(View.VISIBLE);
                holder.btn_play.setVisibility(View.GONE);
                mp.prepareAsync();
            }
        });

//        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
//        mediaMetadataRetriever.setDataSource(item.getUrl(), new HashMap<String, String>());
//        try {
//            final byte[] coverImage = mediaMetadataRetriever.getEmbeddedPicture();
//            Bitmap bitmap= BitmapFactory.decodeByteArray(coverImage, 0, coverImage.length);
//
//            if(bitmap != null){
//                holder.iv.setImageBitmap(bitmap);
//            }else{
//                Picasso.get()
//                        .load(R.drawable.ic_download)
//                        .into(holder.iv);
//            }
//
//        } catch (Exception e) {
//            Picasso.get()
//                    .load(R.drawable.ic_download)
//                    .into(holder.iv);
//            e.printStackTrace();
//        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_name, tv_artist;
        ProgressBar progressBar, progressBar_play;
        ImageView btn_download, btn_redownload, btn_play, btn_pause;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_artist = itemView.findViewById(R.id.tv_artist);
            tv_name = itemView.findViewById(R.id.tv_name);
            progressBar = itemView.findViewById(R.id.progressBar);
            progressBar_play = itemView.findViewById(R.id.progressBar_play);
            btn_download = itemView.findViewById(R.id.btn_download);
            btn_redownload = itemView.findViewById(R.id.btn_redownload);
            btn_play = itemView.findViewById(R.id.btn_play);
            btn_pause = itemView.findViewById(R.id.btn_pause);
        }
    }

}
