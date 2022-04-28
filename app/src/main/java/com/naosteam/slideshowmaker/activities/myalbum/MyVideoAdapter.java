package com.naosteam.slideshowmaker.activities.myalbum;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.naosteam.slideshowmaker.R;

import java.io.File;
import java.util.ArrayList;

public class MyVideoAdapter extends RecyclerView.Adapter<MyVideoAdapter.FileHolder>{

    ArrayList<String> videoFiles;
    Context context;
    CustomItemClickListener listener;
    Boolean isGrid = false;

    public MyVideoAdapter(Boolean isGrid, ArrayList<String> fileList, Context context , CustomItemClickListener listener) {
        this.videoFiles = fileList;
        this.context = context;
        this.listener = listener;
        this.isGrid = isGrid;
    }

    public class FileHolder extends RecyclerView.ViewHolder{

        ImageView videoThumb, share, delete;
        ImageView play;
        RelativeLayout videoLay;
        RelativeLayout lay;

        public FileHolder(View itemView) {
            super(itemView);
            videoThumb = (ImageView) itemView.findViewById(R.id.videoThumbIV);
            share = (ImageView) itemView.findViewById(R.id.share);
            delete = (ImageView) itemView.findViewById(R.id.delete);
            videoLay = (RelativeLayout) itemView.findViewById(R.id.videoLay);
            play = (ImageView) itemView.findViewById(R.id.play);
            lay = (RelativeLayout) itemView.findViewById(R.id.lay);

        }
    }

    @NonNull
    @Override
    public FileHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(isGrid?R.layout.myvideolay:R.layout.myvideolay_h, parent, false);

        return new FileHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FileHolder holder, @SuppressLint("RecyclerView") final int position) {
        Glide.with(context).load(videoFiles.get(position)).override(500,500)
                .into(holder.videoThumb);

        holder.itemView.setOnClickListener(v -> listener.onItemClick(v, position));


        holder.share.setOnClickListener(v -> share(videoFiles.get(position)));

        holder.delete.setOnClickListener(v -> {
            openConfirmDialog(position);
        });

    }

    @Override
    public int getItemCount() {
        return videoFiles.size();
    }

    public void delete(int position) {
        videoFiles.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, videoFiles.size());
    }

    void share(String path) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        share.setType("video/*");
        Uri photoURI = FileProvider.getUriForFile(
                context.getApplicationContext(),
                context.getApplicationContext()
                        .getPackageName() + ".provider", new File(path));
        share.putExtra(Intent.EXTRA_STREAM,
                photoURI);
        context.startActivity(Intent.createChooser(share, "Share via"));
    }

    private void openConfirmDialog(int position){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);

        dialog.setContentView(R.layout.dialog_alert);

        TextView maintext = dialog.findViewById(R.id.maintext);
        maintext.setText("Do you want to delete this video?");
        RelativeLayout img_btn_yes = dialog.findViewById(R.id.yes);
        RelativeLayout img_btn_no = dialog.findViewById(R.id.no);

        img_btn_no.setOnClickListener(v ->{
            dialog.dismiss();
        });

        img_btn_yes.setOnClickListener(v->{
            new File(videoFiles.get(position)).delete();
            delete(position);
            Toast.makeText(context, "Delete Successfully!!!", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        dialog.show();
    }
}
