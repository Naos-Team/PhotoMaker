package com.kessi.photovideomaker.util;

import android.view.animation.Interpolator;

public class BounceyInterpolator implements Interpolator {
    private double mAmplitude = 1.0d;
    private double mFrequency = 10.0d;

    public BounceyInterpolator(double amplitude, double frequency) {
        this.mAmplitude = amplitude;
        this.mFrequency = frequency;
    }

    public float getInterpolation(float time) {
        return (float) ((Math.pow(2.718281828459045d, ((double) (-time)) / this.mAmplitude) * -1.0d * Math.cos(this.mFrequency * ((double) time))) + 1.0d);
    }
}
