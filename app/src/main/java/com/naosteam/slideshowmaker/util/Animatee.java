package com.naosteam.slideshowmaker.util;

import android.app.Activity;
import android.content.Context;

import com.naosteam.slideshowmaker.R;


public class Animatee {
    public static void animateSlideUp(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.animate_slide_up_enter, R.anim.animate_slide_up_exit);

    }

    public static void animateSplit(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.split_enter, R.anim.split_exit);

    }
}
