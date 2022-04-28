package com.naosteam.slideshowmaker.activities.photoframe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.naosteam.slideshowmaker.R;

import java.util.ArrayList;

import vcarry.data.Background_Template;

public class AdapterBackgroundFrame extends RecyclerView.Adapter<AdapterBackgroundFrame.MyViewHolder>{

    Context mContext;
    ArrayList<Background_Template> arrayList;
    BackgroundFrameClickListener listener;

    public AdapterBackgroundFrame(ArrayList<Background_Template> arrayList, BackgroundFrameClickListener listener) {
        this.arrayList = arrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_background_template, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Background_Template bg =  arrayList.get(position);

        holder.iv.setImageBitmap(bg.getBitmap_background());
        holder.tv_name.setText(arrayList.get(position).getName());
        int index = position;
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(index);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_name;
        CardView cv;
        ImageView iv;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.tv_name);
            cv = itemView.findViewById(R.id.cv);
            iv = itemView.findViewById(R.id.iv);
        }
    }
}
