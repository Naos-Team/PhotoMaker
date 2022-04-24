package com.xinlan.imageeditlibrary.editimage.adapter;

import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xinlan.imageeditlibrary.R;
import com.xinlan.imageeditlibrary.editimage.model.FontItem;
import com.xinlan.imageeditlibrary.editimage.model.RatioItem;
import com.xinlan.imageeditlibrary.editimage.ui.FontPicker;
import com.xinlan.imageeditlibrary.editimage.ui.pickTypeListener;

import java.util.ArrayList;
import java.util.List;

public class FontAdapter extends RecyclerView.Adapter<FontAdapter.FontHolder>{
    private pickTypeListener listener;

    private ArrayList<FontItem> arrayList = new ArrayList<FontItem>();//

    public FontAdapter(ArrayList array, pickTypeListener listener) {
        this.arrayList = array;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FontAdapter.FontHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.view_font_item, viewGroup, false);

        return new FontAdapter.FontHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FontAdapter.FontHolder holder, int position) {
        holder.tv_name.setText(arrayList.get(position).getName());
        holder.tv_name.setTypeface(arrayList.get(position).getTypeface());
        holder.linear_font.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FontItem tf = (FontItem) arrayList.get(position);
                listener.onChoice(tf.getTypeface());
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class FontHolder extends RecyclerView.ViewHolder {

        private TextView tv_name;
        private LinearLayout linear_font;

        public FontHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            linear_font = itemView.findViewById(R.id.linear_font);

        }
    }
}
