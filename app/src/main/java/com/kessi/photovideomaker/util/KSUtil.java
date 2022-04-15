package com.kessi.photovideomaker.util;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.kessi.photovideomaker.R;

import java.util.ArrayList;

public class KSUtil {

    //selected images path list
    public static ArrayList<String> videoPathList = new ArrayList<>();
    public static ArrayList<Bitmap> savebmp=new ArrayList<Bitmap>();

    //image editor path and position
    public static String imgEditorPath;
    public static int imbEditorPos;


    public static boolean fromAlbum = false;

    public static AnimatorSet Bubble(View view){
        AnimatorSet animatorSet = new AnimatorSet();


        animatorSet.playTogether(
                ObjectAnimator.ofFloat(view, "alpha", 0, 1, 1, 1),
                ObjectAnimator.ofFloat(view, "scaleX", 0.9f, 1.15f, 0.9f, 1),
                ObjectAnimator.ofFloat(view, "scaleY", 0.9f, 1.15f, 0.9f, 1)
        );

        return animatorSet;
    }



    public static void Bounce(Context context, View view) {
        Animation myAnim = AnimationUtils.loadAnimation(context, R.anim.bounce_anim);
        myAnim.setInterpolator(new BounceyInterpolator(0.2d, 20.0d));
        view.startAnimation(myAnim);
    }
}
