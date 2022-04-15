package com.kessi.photovideomaker.util;

import android.app.Activity;
import android.content.Context;

import com.kessi.photovideomaker.R;


public class Animatee {
    public static void animateSlideUp(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.animate_slide_up_enter, R.anim.animate_slide_up_exit);

    }

    public static void animateSplit(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.split_enter, R.anim.split_exit);

    }
}
