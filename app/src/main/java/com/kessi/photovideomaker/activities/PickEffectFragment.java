package com.kessi.photovideomaker.activities;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kessi.photovideomaker.R;
import com.kessi.photovideomaker.activities.videoeditor.VideoThemeActivity;
import com.kessi.photovideomaker.util.KSUtil;

import java.util.ArrayList;
import java.util.Arrays;

import vcarry.adapters.EffectAdapter;
import vcarry.adapters.OnBackFragment;
import vcarry.adapters.OnEffectItemClick;
import vcarry.adapters.ResultEffectAdapter;
import vcarry.mask.KessiMaskBitmap3D;

public class PickEffectFragment extends Fragment {
    private ImageView backimg_preview_pick_Effect, done_preview_pick_Effect;
    private VideoView video_Preview_Pick_Effect;
    private RecyclerView rcl_List_Effect, rcl_Effect_picked;

    private OnBackFragment onBackFragment;
    private ArrayList<KessiMaskBitmap3D.EFFECT> list_result, list_all;
    private EffectAdapter adapter_effect_list;
    private ResultEffectAdapter adapter_result_list;

    public PickEffectFragment(OnBackFragment onBackFragment) {
        this.onBackFragment = onBackFragment;
        this.list_result = new ArrayList<>();
        this.list_all = new ArrayList(Arrays.asList(KessiMaskBitmap3D.EFFECT.values()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.pick_effect_fragment, container, false);
        SetUp(view);

        return view;
    }

    private void SetUp(View view){
        backimg_preview_pick_Effect = (ImageView) view.findViewById(R.id.backimg_preview_pick_Effect);
        done_preview_pick_Effect = (ImageView) view.findViewById(R.id.done_preview_pick_Effect);
        video_Preview_Pick_Effect = (VideoView) view.findViewById(R.id.video_Preview_Pick_Effect);
        rcl_Effect_picked = (RecyclerView) view.findViewById(R.id.rcl_Effect_picked);
        rcl_List_Effect = (RecyclerView) view.findViewById(R.id.rcl_List_Effect);
        //buttonOK = (Button) view.findViewById(R.id.buttonOK);

        backimg_preview_pick_Effect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackFragment.onBackFragment(list_result);
            }
        });

        done_preview_pick_Effect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list_result.size() > 0){
                    KessiMaskBitmap3D.setCustom_effect(list_result);
                    onBackFragment.onEndChoose();
                } else {
                    Toast.makeText(getActivity(), "You don't choose anything!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        adapter_effect_list = new EffectAdapter((VideoThemeActivity) getActivity(), list_all, new OnEffectItemClick() {
            @Override
            public void onItemClick(int position) {
                String path = "android.resource://" + getActivity().getPackageName() + "/" + list_all.get(position).getVideoResoucre();
                video_Preview_Pick_Effect.setVideoURI(Uri.parse(path));
                video_Preview_Pick_Effect.start();
            }

            @Override
            public void onAddClick(int position) {
                if(list_result.size() >= KSUtil.videoPathList.size() - 1){
                    Toast.makeText(getActivity(), "You have chosen enough effects!", Toast.LENGTH_SHORT).show();
                } else {
                    list_result.add(list_all.get(position));
                    adapter_result_list.notifyDataSetChanged();
                }
            }
        });

        adapter_result_list = new ResultEffectAdapter( list_result, new OnEffectItemClick() {
            @Override
            public void onItemClick(int position) {
                list_result.remove(position);
                adapter_result_list.notifyDataSetChanged();
            }

            @Override
            public void onAddClick(int position) {

            }
        });

        rcl_Effect_picked.setItemAnimator(new DefaultItemAnimator());

        rcl_Effect_picked.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        rcl_Effect_picked.setAdapter(adapter_result_list);

        rcl_List_Effect.setAdapter(adapter_effect_list);
        rcl_List_Effect.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}
