package vcarry.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.kessi.photovideomaker.R;
import com.kessi.photovideomaker.activities.videoeditor.VideoThemeActivity;

import java.util.ArrayList;

import vcarry.mask.KessiMaskBitmap3D;

public class EffectAdapter extends RecyclerView.Adapter<EffectAdapter.EffcectHolder> {
    private VideoThemeActivity activity;
    private ArrayList<KessiMaskBitmap3D.EFFECT> arrayList;
    private OnEffectItemClick onItemClick;
    private int old_index = -1;

    public int getOld_index() {
        return old_index;
    }

    public void setOld_index(int old_index) {
        this.old_index = old_index;
    }

    public EffectAdapter(VideoThemeActivity activity, ArrayList<KessiMaskBitmap3D.EFFECT> arrayList, OnEffectItemClick onItemClick) {
        this.activity = activity;
        this.arrayList = arrayList;
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public EffcectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_effect_list, parent, false);
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((activity.getResources()
//                .getDisplayMetrics().widthPixels* 225 / 1080 ),
//                //ViewGroup.LayoutParams.MATCH_PARENT);//,
//                (activity.getResources()
//                        .getDisplayMetrics().widthPixels * 225/ 1080));
//        params.setMargins(20,54,20,70);
//        view.setLayoutParams(params);
        return new EffcectHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EffcectHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class EffcectHolder extends RecyclerView.ViewHolder{
        ImageView img_effect_list, img_add_effect_item;
        TextView txtname_Effect_list;
        ConstraintLayout layout_effect_item_list;

        public EffcectHolder(@NonNull View itemView) {
            super(itemView);
            img_effect_list = (ImageView) itemView.findViewById(R.id.img_effect_list);
            img_add_effect_item = (ImageView) itemView.findViewById(R.id.img_add_effect_item);
            txtname_Effect_list = (TextView) itemView.findViewById(R.id.txtname_Effect_list);
            layout_effect_item_list = (ConstraintLayout) itemView.findViewById(R.id.layout_effect_item_list);
        }

        public void bindView(int postion){
            if(postion == old_index && old_index >= 0){
                img_add_effect_item.setColorFilter(activity.getColor(R.color.white));
                layout_effect_item_list.setBackgroundResource(R.drawable.effect_list_bg_selected);
                txtname_Effect_list.setTextColor(activity.getColor(R.color.white));
            } else {
                img_add_effect_item.setColorFilter(activity.getColor(R.color.music_selected));
                layout_effect_item_list.setBackgroundResource(R.drawable.effect_list_bg);
                txtname_Effect_list.setTextColor(activity.getColor(R.color.music_selected));
            }
            img_effect_list.setImageResource(arrayList.get(postion).getImageResource());
            img_add_effect_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    layout_effect_item_list.setBackgroundColor(activity.getColor(R.color.music_selected));
//                    txtname_Effect_list.setTextColor(activity.getColor(R.color.white));
                    onItemClick.onAddClick(postion);
                }
            });
            txtname_Effect_list.setText(arrayList.get(postion).toString().replace('_', ' '));
            layout_effect_item_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.onItemClick(postion);
                    if(postion != old_index){
                        old_index = postion;
                        notifyDataSetChanged();
                    }
                }
            });
        }


    }
}
