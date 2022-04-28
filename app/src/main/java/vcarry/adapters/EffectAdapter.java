package vcarry.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.naosteam.slideshowmaker.R;
import com.naosteam.slideshowmaker.activities.videoeditor.VideoThemeActivity;

import java.util.ArrayList;

import vcarry.mask.KessiMaskBitmap3D;

public class EffectAdapter extends RecyclerView.Adapter<EffectAdapter.EffcectHolder> {
    private VideoThemeActivity activity;
    private ArrayList<KessiMaskBitmap3D.EFFECT> arrayList;
    private OnEffectItemClick onItemClick;

    public EffectAdapter(ArrayList<KessiMaskBitmap3D.EFFECT> arrayList, OnEffectItemClick onItemClick) {
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
                }
            });
        }
    }
}
