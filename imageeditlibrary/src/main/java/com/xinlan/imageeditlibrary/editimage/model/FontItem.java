package com.xinlan.imageeditlibrary.editimage.model;

import android.graphics.Typeface;

public class FontItem {

    private String name;
    private Typeface typeface;

    public FontItem(String name, Typeface typeface) {
        this.name = name;
        this.typeface = typeface;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Typeface getTypeface() {
        return typeface;
    }

    public void setTypeface(Typeface typeface) {
        this.typeface = typeface;
    }


}
