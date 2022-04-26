package vcarry.data;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.kessi.photovideomaker.R;
import com.kessi.photovideomaker.activities.BgTemplateDetailsActivity;

import java.util.ArrayList;

public class Background_Template {
    private String name;
    private Bitmap bitmap_background;
    private ArrayList<Frame_in_Background> list_frame;
    private ArrayList<Image_in_Background> list_image;

    public Background_Template(String name, Bitmap bitmap_background, ArrayList<Frame_in_Background> list_frame, ArrayList<Image_in_Background> list_image) {
        this.name = name;
        this.bitmap_background = bitmap_background;
        this.list_frame = list_frame;
        this.list_image = list_image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public static ArrayList<Background_Template> getTemplate_data(Activity activity){
        ArrayList<Background_Template> background_templates = new ArrayList<>();


        ArrayList<Frame_in_Background> list_frame = new ArrayList<>();

        ArrayList<Image_in_Background> list_image = new ArrayList<>();
        list_image = new ArrayList<>();
        list_frame = new ArrayList<>();
        list_frame.add(
                new Frame_in_Background(0.205f, 0.215f, 0.24f, "w,21:32")
        );
        list_frame.add(
                new Frame_in_Background(0.5305f, 0.195f, 0.155f, "w,7:4.7")
        );
        list_frame.add(
                new Frame_in_Background(0.816f, 0.184f, 0.170f, "w,5:7")
        );
        list_frame.add(
                new Frame_in_Background(0.462f, 0.468f, 0.165f, "w,5:7")
        );
        list_frame.add(
                new Frame_in_Background(0.680f, 0.47f, 0.164f, "w,5:7")
        );

        list_image = new ArrayList<>();
        list_image.add(
                new Image_in_Background(173, 161, 240, 21, 32)
        );
        list_image.add(
                new Image_in_Background(407, 164, 157, 70, 47)
        );
        list_image.add(
                new Image_in_Background(715, 153, 170, 50, 70)
        );
        list_image.add(
                new Image_in_Background(408, 393, 164, 50, 70)
        );
        list_image.add(
                new Image_in_Background(600, 393, 164, 50, 70)
        );
        background_templates.add(new Background_Template("New",
                BitmapFactory.decodeResource(activity.getResources(), R.drawable.back),
                list_frame, list_image));
        return background_templates;
    }
}
