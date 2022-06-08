package vcarry.data;

import android.widget.ImageView;

public class Image_Button {
    private ImageView img_Main;
    private ImageView img_Edit;
    private ImageView img_ReChoice;
    private String dir;
    private int color;
    private boolean first_time;

    public Image_Button(ImageView img_Main, ImageView img_Edit, ImageView img_ReChoice, int color) {
        this.img_Main = img_Main;
        this.img_Edit = img_Edit;
        this.img_ReChoice = img_ReChoice;
        this.first_time = true;
        this.dir = "";
        this.color = color;
    }

    public ImageView getImg_Main() {
        return img_Main;
    }

    public void setImg_Main(ImageView img_Main) {
        this.img_Main = img_Main;
    }

    public ImageView getImg_Edit() {
        return img_Edit;
    }

    public void setImg_Edit(ImageView img_Edit) {
        this.img_Edit = img_Edit;
    }

    public ImageView getImg_ReChoice() {
        return img_ReChoice;
    }

    public void setImg_ReChoice(ImageView img_ReChoice) {
        this.img_ReChoice = img_ReChoice;
    }

    public boolean isFirst_time() {
        return first_time;
    }

    public void setFirst_time(boolean first_time) {
        this.first_time = first_time;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
