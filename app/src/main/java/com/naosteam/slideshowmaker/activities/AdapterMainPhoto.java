package com.naosteam.slideshowmaker.activities;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.naosteam.slideshowmaker.R;
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
            openConfirmDialog(position);
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

    private void openConfirmDialog(int position){
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);

        dialog.setContentView(R.layout.dialog_alert);

        TextView maintext = dialog.findViewById(R.id.maintext);
        maintext.setText("Do you want to delete this photo?");
        RelativeLayout img_btn_yes = dialog.findViewById(R.id.yes);
        RelativeLayout img_btn_no = dialog.findViewById(R.id.no);

        img_btn_no.setOnClickListener(v ->{
            dialog.dismiss();
        });

        img_btn_yes.setOnClickListener(v->{
            listener.onDelete(position);
            dialog.dismiss();
        });

        dialog.show();
    }
}
