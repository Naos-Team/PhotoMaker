package vcarry.data;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Background_Template {
    private Bitmap bitmap_background;
    private ArrayList<Frame_in_Background> list_frame;
    private ArrayList<Image_in_Background> list_image;

    public Background_Template(Bitmap bitmap_background, ArrayList<Frame_in_Background> list_frame, ArrayList<Image_in_Background> list_image) {
        this.bitmap_background = bitmap_background;
        this.list_frame = list_frame;
        this.list_image = list_image;
    }

    public Bitmap getBitmap_background() {
        return bitmap_background;
    }

    public void setBitmap_background(Bitmap bitmap_background) {
        this.bitmap_background = bitmap_background;
    }

    public ArrayList<Frame_in_Background> getList_frame() {
        return list_frame;
    }

    public void setList_frame(ArrayList<Frame_in_Background> list_frame) {
        this.list_frame = list_frame;
    }

    public ArrayList<Image_in_Background> getList_image() {
        return list_image;
    }

    public void setList_image(ArrayList<Image_in_Background> list_image) {
        this.list_image = list_image;
    }
}
