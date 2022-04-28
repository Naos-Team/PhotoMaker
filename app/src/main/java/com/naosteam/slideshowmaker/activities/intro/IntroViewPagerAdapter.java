package com.naosteam.slideshowmaker.activities.intro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.naosteam.slideshowmaker.R;

import java.util.ArrayList;

public class IntroViewPagerAdapter extends PagerAdapter {

    Context mContext;
    ArrayList<IntroItem> mList;
    StartButtonListener listener;

    public IntroViewPagerAdapter(Context mContext, ArrayList<IntroItem> mList, StartButtonListener listener) {
        this.mContext = mContext;
        this.mList = mList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutScreen = layoutInflater.inflate(R.layout.layout_intro_screen, null);

        ImageView iv_intro = layoutScreen.findViewById(R.id.iv_intro);
        TextView tv_intro = layoutScreen.findViewById(R.id.tv_intro);
        LinearLayout btn_start = layoutScreen.findViewById(R.id.btn_start);

        tv_intro.setText(mList.get(position).getTitle());
        iv_intro.setImageResource(mList.get(position).getImage());

        if(position == 2){
            btn_start.setVisibility(View.VISIBLE);
            btn_start.setOnClickListener((v) -> {
                listener.onClick();
            });
        }

        container.addView(layoutScreen);

        return layoutScreen;

    }



    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
