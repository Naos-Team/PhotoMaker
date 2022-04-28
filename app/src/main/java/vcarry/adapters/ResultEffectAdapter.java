package vcarry.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.naosteam.slideshowmaker.R;

import java.util.ArrayList;

import vcarry.mask.KessiMaskBitmap3D;

public class ResultEffectAdapter extends RecyclerView.Adapter<ResultEffectAdapter.ResultEffectHolder> {

    private ArrayList<KessiMaskBitmap3D.EFFECT> list_result;
    private OnEffectItemClick onEffectItemClick;

    public ResultEffectAdapter(ArrayList<KessiMaskBitmap3D.EFFECT> list_result, OnEffectItemClick onEffectItemClick) {
        this.list_result = list_result;
        this.onEffectItemClick = onEffectItemClick;
    }

    @NonNull
    @Override
    public ResultEffectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_result_effect, parent, false);
//        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams((activity.getResources()
//                .getDisplayMetrics().widthPixels* 225 / 1080 ),
//                //ViewGroup.LayoutParams.MATCH_PARENT);//,
//                (activity.getResources()
//                        .getDisplayMetrics().widthPixels * 225/ 1080));
//        view.setLayoutParams(params);
        return new ResultEffectHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultEffectHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return list_result.size();
    }

    class ResultEffectHolder extends RecyclerView.ViewHolder{
        ImageView img_effect_result, img_del_effect_result;

        public ResultEffectHolder(@NonNull View itemView) {
            super(itemView);
            img_effect_result = (ImageView) itemView.findViewById(R.id.img_effect_result);
            img_del_effect_result = (ImageView) itemView.findViewById(R.id.img_del_effect_result);
        }

        public void bindView(int position){
            img_effect_result.setImageResource(list_result.get(position).getImageResource());
            img_del_effect_result.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onEffectItemClick.onItemClick(position);
                }
            });
        }
    }
}
