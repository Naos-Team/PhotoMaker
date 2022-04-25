package vcarry.data;

import android.widget.ImageView;

public class Image_Button {
    private ImageView img_Main;
    private ImageView img_Edit;
    private ImageView img_ReChoice;

    public Image_Button(ImageView img_Main, ImageView img_Edit, ImageView img_ReChoice) {
        this.img_Main = img_Main;
        this.img_Edit = img_Edit;
        this.img_ReChoice = img_ReChoice;
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
}
