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


        ArrayList<Frame_in_Background> list_frame1 = new ArrayList<>();

        ArrayList<Image_in_Background> list_image1 = new ArrayList<>();
        list_frame1 = new ArrayList<>();
        list_frame1.add(
                new Frame_in_Background(0.205f, 0.215f, 0.24f, "w,21:32")
        );
        list_frame1.add(
                new Frame_in_Background(0.5305f, 0.196f, 0.155f, "w,7:4.7")
        );
        list_frame1.add(
                new Frame_in_Background(0.816f, 0.184f, 0.170f, "w,5:7")
        );
        list_frame1.add(
                new Frame_in_Background(0.462f, 0.468f, 0.165f, "w,5:7")
        );
        list_frame1.add(
                new Frame_in_Background(0.680f, 0.47f, 0.164f, "w,5:7")
        );

        list_image1 = new ArrayList<>();
        list_image1.add(
                new Image_in_Background(173, 161, 240, 21, 32)
        );
        list_image1.add(
                new Image_in_Background(407, 164, 157, 70, 47)
        );
        list_image1.add(
                new Image_in_Background(715, 153, 170, 50, 70)
        );
        list_image1.add(
                new Image_in_Background(408, 393, 164, 50, 70)
        );
        list_image1.add(
                new Image_in_Background(600, 393, 164, 50, 70)
        );
        background_templates.add(new Background_Template("5 frs",
                BitmapFactory.decodeResource(activity.getResources(), R.drawable.back),
                list_frame1, list_image1));


        ArrayList<Frame_in_Background> list_frame2 = new ArrayList<>();
        ArrayList<Image_in_Background> list_image2 = new ArrayList<>();
        list_frame2 = new ArrayList<>();
        list_frame2.add(
                new Frame_in_Background(0.0456f, 0.2935f, 0.3584f, "w,15.1:35")
        );
        list_frame2.add(
                new Frame_in_Background(0.267f, 0.2665f, 0.43f, "w,16.23:44")
        );
        list_frame2.add(
                new Frame_in_Background(0.498f, 0.227f, 0.51f, "w,17.5:54")
        );
        list_frame2.add(
                new Frame_in_Background(0.731f, 0.260f, 0.44f, "w,19.2:52")
        );
        list_frame2.add(
                new Frame_in_Background(0.9548f, 0.293f, 0.36f, "w,9.82:23")
        );

        list_image2 = new ArrayList<>();
        list_image2.add(
                new Image_in_Background(38, 188, 359, 152, 350)
        );
        list_image2.add(
                new Image_in_Background(225, 151, 431, 162, 440)
        );
        list_image2.add(
                new Image_in_Background(416, 111, 509, 175, 540)
        );
        list_image2.add(
                new Image_in_Background(612, 146, 440, 193, 520)
        );
        list_image2.add(
                new Image_in_Background(807, 187, 359, 100, 230)
        );
        background_templates.add(new Background_Template("5 frs",
                BitmapFactory.decodeResource(activity.getResources(), R.drawable.back2),
                list_frame2, list_image2));

        ArrayList<Frame_in_Background> list_frame3 = new ArrayList<>();

        ArrayList<Image_in_Background> list_image3 = new ArrayList<>();
        list_frame3 = new ArrayList<>();
        list_frame3.add(
                new Frame_in_Background(0.1394f, 0.13f, 0.258f, "w,5.22:7")
        );
        list_frame3.add(
                new Frame_in_Background(0.1393f, 0.450f, 0.16f, "w,25.22:21")
        );
        list_frame3.add(
                new Frame_in_Background(0.4705f, 0.17f, 0.444f, "w,8.85:13")
        );
        list_frame3.add(
                new Frame_in_Background(0.811f, 0.115f, 0.162f, "w,25.25:21")
        );
        list_frame3.add(
                new Frame_in_Background(0.810f, 0.372f, 0.2628f, "w,20:27")
        );

        list_image3 = new ArrayList<>();
        list_image3.add(
                new Image_in_Background(113 , 96, 258, 524, 700)
        );
        list_image3.add(
                new Image_in_Background(112, 378, 160, 254, 210)
        );
        list_image3.add(
                new Image_in_Background(328, 95, 443, 89, 130)
        );
        list_image3.add(
                new Image_in_Background(653, 96, 164, 250, 210)
        );
        list_image3.add(
                new Image_in_Background(653, 274, 263, 201, 270)
        );
        background_templates.add(new Background_Template("5 frs",
                BitmapFactory.decodeResource(activity.getResources(), R.drawable.back3),
                list_frame3, list_image3));

        ArrayList<Frame_in_Background> list_frame4 = new ArrayList<>();
        ArrayList<Image_in_Background> list_image4 = new ArrayList<>();
        list_frame4 = new ArrayList<>();
        list_frame4.add(
                new Frame_in_Background(0.228f, 0.2675f, 0.12f, "w,6.9:5")
        );
        list_frame4.add(
                new Frame_in_Background(0.259f, 0.461f, 0.165f, "w,25:33")
        );
        list_frame4.add(
                new Frame_in_Background(0.471f, 0.195f, 0.199f, "w,25.5:25")
        );
        list_frame4.add(
                new Frame_in_Background(0.4710f, 0.537f, 0.282f, "w,29:41")
        );
        list_frame4.add(
                new Frame_in_Background(0.692f, 0.132f, 0.165f, "w,25.2:33")
        );
        list_frame4.add(
                new Frame_in_Background(0.847f, 0.372f, 0.2015f, "w,30:21")
        );

        list_image4 = new ArrayList<>();
        list_image4.add(
                new Image_in_Background(192, 236, 120, 691, 500)
        );
        list_image4.add(
                new Image_in_Background(227, 386, 163, 25, 33)
        );
        list_image4.add(
                new Image_in_Background(377, 159, 197, 258, 250)
        );
        list_image4.add(
                new Image_in_Background(379, 388, 279, 292, 410)
        );
        list_image4.add(
                new Image_in_Background(606, 112, 163, 25, 33)
        );
        list_image4.add(
                new Image_in_Background(605, 298, 202, 297, 210)
        );
        background_templates.add(new Background_Template("6 frs",
                BitmapFactory.decodeResource(activity.getResources(), R.drawable.back4),
                list_frame4, list_image4));

        ArrayList<Frame_in_Background> list_frame5 = new ArrayList<>();
        ArrayList<Image_in_Background> list_image5 = new ArrayList<>();
        list_frame5 = new ArrayList<>();
        list_frame5.add(
                new Frame_in_Background(0.194f, 0.336f, 0.098f, "w,19.5:28")
        );
        list_frame5.add(
                new Frame_in_Background(0.3813f, 0.195f, 0.054f, "w,10.9:6")
        );
        list_frame5.add(
                new Frame_in_Background(0.329f, 0.325f, 0.122f, "w,0.97:1")
        );
        list_frame5.add(
                new Frame_in_Background(0.5161f, 0.224f, 0.068f, "w,18:13")
        );
        list_frame5.add(
                new Frame_in_Background(0.587f, 0.4185f, 0.108f, "w,25.6:13")
        );
        list_frame5.add(
                new Frame_in_Background(0.672f, 0.16f, 0.1375f, "w,3.1:4")
        );
        list_frame5.add(
                new Frame_in_Background(0.8025f, 0.3425f, 0.0693f, "w,7.1:5")
        );

        list_image5 = new ArrayList<>();
        list_image5.add(
                new Image_in_Background(182, 305, 96, 195, 280)
        );
        list_image5.add(
                new Image_in_Background(345, 186, 52, 110, 60)
        );
        list_image5.add(
                new Image_in_Background(290, 286, 122, 97, 100)
        );
        list_image5.add(
                new Image_in_Background(470, 210, 66, 18, 13)
        );
        list_image5.add(
                new Image_in_Background(464, 373, 108, 255, 130)
        );
        list_image5.add(
                new Image_in_Background(599, 140, 135, 32, 40)
        );
        list_image5.add(
                new Image_in_Background(725, 321, 67, 71, 50)
        );
        background_templates.add(new Background_Template("7 frs",
                BitmapFactory.decodeResource(activity.getResources(), R.drawable.back5),
                list_frame5, list_image5));

        ArrayList<Frame_in_Background> list_frame6 = new ArrayList<>();
        ArrayList<Image_in_Background> list_image6 = new ArrayList<>();
        list_frame6 = new ArrayList<>();
        list_frame6.add(
                new Frame_in_Background(0.528f, 0.0735f, 0.37f, "w,142:93")
        );

        list_image6 = new ArrayList<>();
        list_image6.add(
                new Image_in_Background(230, 48, 369, 143, 93)
        );

        background_templates.add(new Background_Template("1 fr",
                BitmapFactory.decodeResource(activity.getResources(), R.drawable.back6),
                list_frame6, list_image6));

        ArrayList<Frame_in_Background> list_frame7 = new ArrayList<>();
        ArrayList<Image_in_Background> list_image7 = new ArrayList<>();
        list_frame7 = new ArrayList<>();
        list_frame7.add(
                new Frame_in_Background(0.1184f, 0.2363f, 0.071f, "w,37:53")
        );//1
        list_frame7.add(
                new Frame_in_Background(0.236f, 0.205f, 0.1f, "w,27:38")
        );//2
        list_frame7.add(
                new Frame_in_Background(0.3725f, 0.24f, 0.075f, "w,37:53")
        );//3
        list_frame7.add(
                new Frame_in_Background(0.4995f, 0.271f, 0.05f, "w,27:19")
        );//4
        list_frame7.add(
                new Frame_in_Background(0.628f, 0.242f, 0.07f, "w,38:53")
        );//5
        list_frame7.add(
                new Frame_in_Background(0.765f, 0.204f, 0.1f, "w,14:19")
        );//6
        list_frame7.add(
                new Frame_in_Background(0.882f, 0.2348f, 0.07f, "w,38:53")
        );//7
        list_frame7.add(
                new Frame_in_Background(0.0492f, 0.3685f, 0.1f, "w,56:77")
        );//8
        list_frame7.add(
                new Frame_in_Background(0.1875f, 0.3676f, 0.102f, "w,56:77")
        );//9
        list_frame7.add(
                new Frame_in_Background(0.364f, 0.382f, 0.098f, "w,19:11")
        );//10
        list_frame7.add(
                new Frame_in_Background(0.636f, 0.3805f, 0.1f, "w,19:11")
        );//11
        list_frame7.add(
                new Frame_in_Background(0.8125f, 0.368f, 0.1f, "w,37:53")
        );//12
        list_frame7.add(
                new Frame_in_Background(0.946f, 0.368f, 0.1f, "w,37:53")
        );//13
        list_frame7.add(
                new Frame_in_Background(0.108f, 0.538f, 0.1f, "w,37:53")
        );//14
        list_frame7.add(
                new Frame_in_Background(0.2385f, 0.522f, 0.07f, "w,78:55")
        );//15
        list_frame7.add(
                new Frame_in_Background(0.7665f, 0.522f, 0.07f, "w,78:55")
        );//16
        list_frame7.add(
                new Frame_in_Background(0.896f, 0.537f, 0.098f, "w,56:77")
        );//17
        list_frame7.add(
                new Frame_in_Background(0.2428f, 0.6401f, 0.07f, "w,78:55")
        );//18
        list_frame7.add(
                new Frame_in_Background(0.377f, 0.64f, 0.07f, "w,13:18")
        );//19
        list_frame7.add(
                new Frame_in_Background(0.501f, 0.71f, 0.17f, "w,4:7")
        );//20
        list_frame7.add(
                new Frame_in_Background(0.625f, 0.64f, 0.07f, "w,13:18")
        );//21
        list_frame7.add(
                new Frame_in_Background(0.766f, 0.641f, 0.07f, "w,78:55")
        );//22
        list_frame7.add(
                new Frame_in_Background(0.364f, 0.741f, 0.05f, "w,26:19")
        );//23
        list_frame7.add(
                new Frame_in_Background(0.638f, 0.742f, 0.05f, "w,26:19")
        );//24


        list_image7 = new ArrayList<>();
        list_image7.add(
                new Image_in_Background(113, 220, 70, 387, 530)
        );
        list_image7.add(
                new Image_in_Background(220, 184, 100, 27, 38)
        );
        list_image7.add(
                new Image_in_Background(352, 222, 75, 37, 53)
        );
        list_image7.add(
                new Image_in_Background(464, 257, 50, 27, 19)
        );
        list_image7.add(
                new Image_in_Background(464, 373, 108, 255, 130)
        );
        list_image7.add(
                new Image_in_Background(596, 225, 70, 385, 530)
        );
        list_image7.add(
                new Image_in_Background(709, 184, 100, 14, 19)
        );
        list_image7.add(
                new Image_in_Background(838, 219, 70, 387, 530)
        );
        list_image7.add(
                new Image_in_Background(46, 332, 100, 56, 77)
        );
        list_image7.add(
                new Image_in_Background(175, 332, 100, 56, 77)
        );
        list_image7.add(
                new Image_in_Background(302, 344, 98, 192, 110)
        );
        list_image7.add(
                new Image_in_Background(529, 343,99, 19, 11)
        );
        list_image7.add(
                new Image_in_Background(880, 331, 100, 37, 53)
        );
        list_image7.add(
                new Image_in_Background(101, 485, 100, 37, 53)
        );
        list_image7.add(
                new Image_in_Background(215, 485, 71, 78, 55)
        );
        list_image7.add(
                new Image_in_Background(690, 485, 70, 78, 55)
        );
        list_image7.add(
                new Image_in_Background(832, 486, 99, 56, 77)
        );
        list_image7.add(
                new Image_in_Background(220, 597, 70, 78, 55)
        );
        list_image7.add(
                new Image_in_Background(359, 596, 70, 13, 18)
        );
        list_image7.add(
                new Image_in_Background(452, 590, 170, 41, 70)
        );
        list_image7.add(
                new Image_in_Background(596, 595, 70, 130, 185)
        );
        list_image7.add(
                new Image_in_Background(692, 597, 70, 78, 55)
        );
        list_image7.add(
                new Image_in_Background(339, 705, 50, 27, 19)
        );
        list_image7.add(
                new Image_in_Background(595,705, 50, 27, 19)
        );

        background_templates.add(new Background_Template("24 frs",
                BitmapFactory.decodeResource(activity.getResources(), R.drawable.back7),
                list_frame7, list_image7));

        ArrayList<Frame_in_Background> list_frame8 = new ArrayList<>();
        ArrayList<Image_in_Background> list_image8 = new ArrayList<>();
        list_frame8 = new ArrayList<>();
        list_frame8.add(
                new Frame_in_Background(0.0535f, 0.664f, 0.25f, "w,64:79")
        );
        list_frame8.add(
                new Frame_in_Background(0.328f, 0.6f, 0.18f, "w,50:57")
        );
        list_frame8.add(
                new Frame_in_Background(0.311f, 0.3615f, 0.145f, "w,34.2:47")
        );
        list_frame8.add(
                new Frame_in_Background(0.505f, 0.362f, 0.15f, "w,60.6:49")
        );
        list_frame8.add(
                new Frame_in_Background(0.7021f, 0.36f, 0.15f, "w,34.2:47")
        );
        list_frame8.add(
                new Frame_in_Background(0.687f, 0.114f, 0.178f, "w,50:57")
        );
        list_frame8.add(
                new Frame_in_Background(0.949f, 0.033f, 0.248f, "w,64:79")
        );

        list_image8 = new ArrayList<>();
        list_image8.add(
                new Image_in_Background(42, 498, 250, 65, 79)
        );
        list_image8.add(
                new Image_in_Background(276, 492, 180, 50, 57)
        );
        list_image8.add(
                new Image_in_Background(278, 309, 146, 345, 470)
        );
        list_image8.add(
                new Image_in_Background(411, 308, 151, 608, 490)
        );
        list_image8.add(
                new Image_in_Background(625, 306, 149, 350, 470)
        );
        list_image8.add(
                new Image_in_Background(580, 94, 179, 50, 57)
        );
        list_image8.add(
                new Image_in_Background(760, 25, 248, 64, 79)
        );
        background_templates.add(new Background_Template("7 frs",
                BitmapFactory.decodeResource(activity.getResources(), R.drawable.back8),
                list_frame8, list_image8));

        ArrayList<Frame_in_Background> list_frame9 = new ArrayList<>();
        ArrayList<Image_in_Background> list_image9 = new ArrayList<>();
        list_frame9 = new ArrayList<>();
        list_frame9.add(
                new Frame_in_Background(0.035f, 0.3375f, 0.337f, "w,28:43")
        );
        list_frame9.add(
                new Frame_in_Background(0.496f, 0.338f, 0.340f, "w,29.7:23")
        );
        list_frame9.add(
                new Frame_in_Background(0.96f, 0.339f, 0.338f, "w,27.7:43")
        );

        list_image9 = new ArrayList<>();
        list_image9.add(
                new Image_in_Background(34, 152, 429, 278, 430)
        );
        list_image9.add(
                new Image_in_Background(352, 152, 430, 298, 230)
        );
        list_image9.add(
                new Image_in_Background(950, 150, 430, 277, 430)
        );

        background_templates.add(new Background_Template("3 frs",
                BitmapFactory.decodeResource(activity.getResources(), R.drawable.back9),
                list_frame9, list_image9));

        ArrayList<Frame_in_Background> list_frame10 = new ArrayList<>();
        ArrayList<Image_in_Background> list_image10 = new ArrayList<>();
        list_frame10 = new ArrayList<>();
        list_frame10.add(
                new Frame_in_Background(0.09f, 0.392f, 0.185f, "w,31:41")
        );
        list_frame10.add(
                new Frame_in_Background(0.393f, 0.083f, 0.184f, "w,31:41")
        );
        list_frame10.add(
                new Frame_in_Background(0.366f, 0.465f, 0.315f, "w,30.7:47")
        );
        list_frame10.add(
                new Frame_in_Background(0.644f, 0.605f, 0.184f, "w,31:41")
        );
        list_frame10.add(
                new Frame_in_Background(0.689f, 0.18f, 0.315f, "w,30.8:47")
        );
        list_frame10.add(
                new Frame_in_Background(0.942f, 0.3f, 0.188f, "w,31.1:41")
        );

        list_image10 = new ArrayList<>();
        list_image10.add(
                new Image_in_Background(75, 317, 189, 31, 41)
        );
        list_image10.add(
                new Image_in_Background(338, 70, 184, 31, 41)
        );
        list_image10.add(
                new Image_in_Background(290, 320, 315, 310, 470)
        );
        list_image10.add(
                new Image_in_Background(547, 123, 316, 309, 470)
        );
        list_image10.add(
                new Image_in_Background(554, 494, 185, 31, 41)
        );
        list_image10.add(
                new Image_in_Background(808, 244, 188, 312, 410)
        );

        background_templates.add(new Background_Template("6 frs",
                BitmapFactory.decodeResource(activity.getResources(), R.drawable.back10),
                list_frame10, list_image10));

        ArrayList<Frame_in_Background> list_frame11 = new ArrayList<>();
        ArrayList<Image_in_Background> list_image11 = new ArrayList<>();
        list_frame11 = new ArrayList<>();
        list_frame11.add(
                new Frame_in_Background(0.166f, 0.517f, 0.19f, "w,8:13")
        );
        list_frame11.add(
                new Frame_in_Background(0.359f, 0.291f, 0.11f, "w,13:8")
        );
        list_frame11.add(
                new Frame_in_Background(0.3788f, 0.564f, 0.23f, "w,5:8")
        );
        list_frame11.add(
                new Frame_in_Background(0.603f, 0.412f, 0.19f, "w,8:13")
        );
        list_frame11.add(
                new Frame_in_Background(0.654f, 0.652f, 0.11f, "w,13:8")
        );
        list_frame11.add(
                new Frame_in_Background(0.856f, 0.461f, 0.11f, "w,13:8")
        );

        list_image11 = new ArrayList<>();
        list_image11.add(
                new Image_in_Background(149, 421, 185, 81, 130)
        );
        list_image11.add(
                new Image_in_Background(296, 258, 111, 13, 8)
        );
        list_image11.add(
                new Image_in_Background(321 ,428, 245, 5, 8)
        );
        list_image11.add(
                new Image_in_Background(536, 335, 187, 79, 130)
        );
        list_image11.add(
                new Image_in_Background(538, 580, 110, 13, 8)
        );
        list_image11.add(
                new Image_in_Background(704, 410, 110, 13, 8)
        );

        background_templates.add(new Background_Template("6 frames",
                BitmapFactory.decodeResource(activity.getResources(), R.drawable.back11),
                list_frame11, list_image11));

        return background_templates;
    }
}
