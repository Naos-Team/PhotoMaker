package vcarry.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.kessi.photovideomaker.KessiApplication;
import com.kessi.photovideomaker.R;
import com.kessi.photovideomaker.activities.videoeditor.VideoThemeActivity;
import com.kessi.photovideomaker.util.AdManager;

import java.util.ArrayList;
import java.util.Arrays;

import vcarry.mask.KessiTheme;

import vcarry.util.FileUtils;


public class ThemeAdapter extends RecyclerView.Adapter<ThemeAdapter.Holder> {
    private KessiApplication application = KessiApplication.getInstance();
    private LayoutInflater inflater;
    private ArrayList<KessiTheme> list;
    private VideoThemeActivity activity;

    int position = 0;

    public class Holder extends RecyclerView.ViewHolder {
        ImageView cbSelect;
        private View clickableView;
        private ImageView ivThumb;
        private View mainView;
        private TextView tvThemeNew;

        public Holder(View v) {
            super(v);
            this.cbSelect = (ImageView) v.findViewById(R.id.cbSelect);
            this.ivThumb = (ImageView) v.findViewById(R.id.ivThumb);
            this.tvThemeNew = (TextView) v.findViewById(R.id.tvThemeNew);
            this.clickableView = v.findViewById(R.id.clickableView);
            this.mainView = v;
        }
    }

    public ThemeAdapter(VideoThemeActivity PVMWSPreviewActivity) {
        this.activity = PVMWSPreviewActivity;
        this.list = new ArrayList(Arrays.asList(KessiTheme.values()));
        this.inflater = LayoutInflater.from(PVMWSPreviewActivity);
    }

    public Holder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt) {
        View view =this.inflater.inflate(R.layout.theme_items, paramViewGroup, false);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((activity.getResources()
                .getDisplayMetrics().widthPixels* 190 / 1080 ),
                (activity.getResources()
                        .getDisplayMetrics().widthPixels * 190 / 1080));
        params.setMargins(10,10,10,10);

        view.setLayoutParams(params);
        return new Holder(view);
    }

    public void onBindViewHolder(Holder holder, @SuppressLint("RecyclerView") final int pos) {
        KessiTheme PVMWSThemes = (KessiTheme) this.list.get(pos);
        Glide.with(this.application).load(Integer.valueOf(PVMWSThemes.getThemeDrawable())).into(holder.ivThumb);

        if (pos<7){
            holder.tvThemeNew.setVisibility(View.VISIBLE);
        }else {
            holder.tvThemeNew.setVisibility(View.GONE);
        }

        holder.cbSelect.setVisibility(View.GONE);
        if (position == pos){
            holder.cbSelect.setVisibility(View.VISIBLE);
        }

        holder.clickableView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (list.get(pos) != application.selectedTheme) {
                    if (!AdManager.isloadFbMAXAd) {
                        AdManager.adCounter++;
                        AdManager.showInterAd(activity, null,0);
                    } else {
                        AdManager.adCounter++;
                        AdManager.showMaxInterstitial(activity, null,0);
                    }

                    position = pos;
//                    deleteThemeDir(application.selectedTheme.toString());
                    application.videoImages.clear();
                    application.selectedTheme = (KessiTheme) list.get(pos);
                    application.setCurrentTheme(application.selectedTheme.toString());
                    activity.reset();
//                    Intent intent = new Intent(application, ServiceAnim.class);
//                    intent.putExtra(ServiceAnim.EXTRA_SELECTED_THEME, application.getCurrentTheme());
//                    application.startService(intent);
                    notifyDataSetChanged();

                }
            }
        });
    }

    private void deleteThemeDir(final String dir) {
        new Thread() {
            public void run() {
                FileUtils.deleteThemeDir(dir);
            }
        }.start();
    }

    public ArrayList<KessiTheme> getList() {
        return this.list;
    }

    public int getItemCount() {
        return this.list.size();
    }
}
