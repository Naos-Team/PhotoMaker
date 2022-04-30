package vcarry.data;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.naosteam.slideshowmaker.R;

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
                new Frame_in_Background(0.5305f, 0.196f, 0.155f, "w,7.1:4.7")
        );
        list_frame1.add(
                new Frame_in_Background(0.816f, 0.184f, 0.170f, "w,5:7")
        );
        list_frame1.add(
                new Frame_in_Background(0.462f, 0.468f, 0.165f, "w,5:7")
        );
        list_frame1.add(
                new Frame_in_Background(0.680f, 0.47f, 0.164f, "w,5.3:7")
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
                new Image_in_Background(600, 393, 164, 51, 70)
        );
        background_templates.add(new Background_Template("5 frs",
                BitmapFactory.decodeResource(activity.getResources(), R.drawable.back),
                list_frame1, list_image1));


        ArrayList<Frame_in_Background> list_frame2 = new ArrayList<>();
        ArrayList<Image_in_Background> list_image2 = new ArrayList<>();
        list_frame2 = new ArrayList<>();
        list_frame2.add(
                new Frame_in_Background(0.045f, 0.293f, 0.358f, "w,15.2:35")
        );
        list_frame2.add(
                new Frame_in_Background(0.267f, 0.266f, 0.43f, "w,16.21:44")
        );
        list_frame2.add(
                new Frame_in_Background(0.497f, 0.225f, 0.51f, "w,17.51:54")
        );
        list_frame2.add(
                new Frame_in_Background(0.73f, 0.26f, 0.44f, "w,19.31:52")
        );
        list_frame2.add(
                new Frame_in_Background(0.955f, 0.293f, 0.357f, "w,10:23")
        );

        list_image2 = new ArrayList<>();
        list_image2.add(
                new Image_in_Background(38, 188, 359, 152, 350)
        );
        list_image2.add(
                new Image_in_Background(224, 151, 431, 164, 440)
        );
        list_image2.add(
                new Image_in_Background(416, 111, 509, 176, 540)
        );
        list_image2.add(
                new Image_in_Background(612, 146, 440, 194, 520)
        );
        list_image2.add(
                new Image_in_Background(807, 187, 360, 100, 230)
        );
        background_templates.add(new Background_Template("5 frs",
                BitmapFactory.decodeResource(activity.getResources(), R.drawable.back2),
                list_frame2, list_image2));

        ArrayList<Frame_in_Background> list_frame3 = new ArrayList<>();

        ArrayList<Image_in_Background> list_image3 = new ArrayList<>();
        list_frame3 = new ArrayList<>();
        list_frame3.add(
                new Frame_in_Background(0.1394f, 0.129f, 0.259f, "w,5.21:7")
        );
        list_frame3.add(
                new Frame_in_Background(0.140f, 0.450f, 0.161f, "w,25:21")
        );
        list_frame3.add(
                new Frame_in_Background(0.4705f, 0.17f, 0.444f, "w,8.85:13")
        );
        list_frame3.add(
                new Frame_in_Background(0.810f, 0.114f, 0.163f, "w,25:21")
        );
        list_frame3.add(
                new Frame_in_Background(0.810f, 0.372f, 0.261f, "w,20.1:27")
        );

        list_image3 = new ArrayList<>();
        list_image3.add(
                new Image_in_Background(112 , 96, 259, 522, 700)
        );
        list_image3.add(
                new Image_in_Background(112, 378, 162, 251, 210)
        );
        list_image3.add(
                new Image_in_Background(328, 95, 444, 89, 130)
        );
        list_image3.add(
                new Image_in_Background(652, 96, 163, 253, 210)
        );
        list_image3.add(
                new Image_in_Background(652, 274, 262, 202, 270)
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
                new Frame_in_Background(0.259f, 0.461f, 0.165f, "w,24.9:33")
        );
        list_frame4.add(
                new Frame_in_Background(0.471f, 0.195f, 0.199f, "w,25.5:25")
        );
        list_frame4.add(
                new Frame_in_Background(0.4710f, 0.537f, 0.282f, "w,29:41")
        );
        list_frame4.add(
                new Frame_in_Background(0.692f, 0.132f, 0.165f, "w,25.1:33")
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
                new Image_in_Background(379, 388, 279, 291, 410)
        );
        list_image4.add(
                new Image_in_Background(606, 112, 163, 251, 330)
        );
        list_image4.add(
                new Image_in_Background(605, 298, 202, 298, 210)
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
                new Image_in_Background(470, 210, 66, 182, 130)
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
                new Frame_in_Background(0.501f, 0.71f, 0.17f, "w,4.2:7")
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
        );//1
        list_image7.add(
                new Image_in_Background(220, 184, 100, 27, 38)
        );//2
        list_image7.add(
                new Image_in_Background(352, 222, 75, 37, 53)
        );//3
        list_image7.add(
                new Image_in_Background(464, 257, 50, 27, 19)
        );//4
        list_image7.add(
                new Image_in_Background(464, 373, 108, 255, 130)
        );//5
        list_image7.add(
                new Image_in_Background(596, 225, 70, 385, 530)
        );//6
        list_image7.add(
                new Image_in_Background(709, 184, 100, 14, 19)
        );//7
        list_image7.add(
                new Image_in_Background(838, 219, 70, 387, 530)
        );//8
        list_image7.add(
                new Image_in_Background(46, 332, 100, 56, 77)
        );//9
        list_image7.add(
                new Image_in_Background(302, 344, 98, 192, 110)
        );//10
        list_image7.add(
                new Image_in_Background(529, 343,99, 19, 11)
        );//11
        list_image7.add(
                new Image_in_Background(756, 330, 100, 37, 53)
        );//12
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
                new Image_in_Background(452, 590, 170, 415, 700)
        );
        list_image7.add(
                new Image_in_Background(595, 595, 70, 130, 185)
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

        background_templates.add(new Background_Template("6 frs",
                BitmapFactory.decodeResource(activity.getResources(), R.drawable.back11),
                list_frame11, list_image11));

        ArrayList<Frame_in_Background> list_frame12 = new ArrayList<>();
        ArrayList<Image_in_Background> list_image12 = new ArrayList<>();
        list_frame12 = new ArrayList<>();
        list_frame12.add(
                new Frame_in_Background(0.08f, 0.26f, 0.4f, "w,230:333")
        );
        list_frame12.add(
                new Frame_in_Background(0.48f, 0.21f, 0.25f, "w,37.2:52")
        );
        list_frame12.add(
                new Frame_in_Background(0.771f, 0.21f, 0.25f, "w,37.1:52")
        );
        list_frame12.add(
                new Frame_in_Background(0.48f, 0.62f, 0.25f, "w,37.2:52")
        );
        list_frame12.add(
                new Frame_in_Background(0.73f, 0.56f, 0.17f, "w,27:35")
        );
        list_frame12.add(
                new Frame_in_Background(0.947f, 0.56f, 0.17f, "w,27:35")
        );

        list_image12 = new ArrayList<>();
        list_image12.add(
                new Image_in_Background(58, 155, 400, 230, 333)
        );
        list_image12.add(
                new Image_in_Background(397, 158, 250, 37, 52)
        );
        list_image12.add(
                new Image_in_Background(637, 157, 250, 37, 52)
        );
        list_image12.add(
                new Image_in_Background(397, 463, 250, 37, 52)
        );
        list_image12.add(
                new Image_in_Background(635, 465, 171, 27, 35)
        );
        list_image12.add(
                new Image_in_Background(822, 465, 171, 27, 35)
        );

        background_templates.add(new Background_Template("6 frs",
                BitmapFactory.decodeResource(activity.getResources(), R.drawable.back12),
                list_frame12, list_image12));

        ArrayList<Frame_in_Background> list_frame13 = new ArrayList<>();
        ArrayList<Image_in_Background> list_image13 = new ArrayList<>();
        list_frame13 = new ArrayList<>();
        list_frame13.add(
                new Frame_in_Background(0.11f, 0.261f, 0.152f, "w,28:45")
        );
        list_frame13.add(
                new Frame_in_Background(0.273f, 0.261f, 0.152f, "w,28:45")
        );
        list_frame13.add(
                new Frame_in_Background(0.054f, 0.505f, 0.152f, "w,28:45")
        );
        list_frame13.add(
                new Frame_in_Background(0.783f, 0.506f, 0.152f, "w,28:45")
        );
        list_frame13.add(
                new Frame_in_Background(0.945f, 0.506f, 0.152f, "w, 28:45")
        );
        list_frame13.add(
                new Frame_in_Background(0.22f, 0.54f, 0.21f, "w,136:179")
        );
        list_frame13.add(
                new Frame_in_Background(0.535f, 0.35f, 0.37f, "w,229:329")
        );
        list_frame13.add(
                new Frame_in_Background(0.9f, 0.26f, 0.16f, "w,181:137")
        );

        list_image13 = new ArrayList<>();
        list_image13.add(
                new Image_in_Background(100, 220, 152, 28, 45)
        );
        list_image13.add(
                new Image_in_Background(247, 222, 152, 28, 45)
        );
        list_image13.add(
                new Image_in_Background(49, 428, 152, 28, 45)
        );
        list_image13.add(
                new Image_in_Background(709, 430, 152, 28, 45)
        );
        list_image13.add(
                new Image_in_Background(855, 430, 153, 28, 45)
        );
        list_image13.add(
                new Image_in_Background(190, 431, 200, 136, 179)
        );
        list_image13.add(
                new Image_in_Background(400, 220, 370, 229, 329)
        );
        list_image13.add(
                new Image_in_Background(707, 220, 155, 183, 137)
        );

        background_templates.add(new Background_Template("8 frs",
                BitmapFactory.decodeResource(activity.getResources(), R.drawable.back13),
                list_frame13, list_image13));

        ArrayList<Frame_in_Background> list_frame14 = new ArrayList<>();
        ArrayList<Image_in_Background> list_image14 = new ArrayList<>();
        list_frame14 = new ArrayList<>();
        list_frame14.add(
                new Frame_in_Background(0.253f, 0.075f, 0.22f, "w,47:64")
        );
        list_frame14.add(
                new Frame_in_Background(0.482f, 0.075f, 0.2f, "w,48:64")
        );
        list_frame14.add(
                new Frame_in_Background(0.707f, 0.078f, 0.214f, "w,47:64")
        );
        list_frame14.add(
                new Frame_in_Background(0.482f, 0.383f, 0.216f, "w,48:64")
        );
        list_frame14.add(
                new Frame_in_Background(0.254f, 0.386f, 0.216f, "w,47:64")
        );
        list_frame14.add(
                new Frame_in_Background(0.711f, 0.385f, 0.214f, "w,47:64")
        );

        list_image14 = new ArrayList<>();
        list_image14.add(
                new Image_in_Background(215, 60, 216, 474, 640)
        );
        list_image14.add(
                new Image_in_Background(407, 58, 213, 474, 640)
        );
        list_image14.add(
                new Image_in_Background(596, 60, 215, 474, 640)
        );
        list_image14.add(
                new Image_in_Background(214, 301, 218, 470, 640)
        );
        list_image14.add(
                new Image_in_Background(407, 300, 216, 471, 640)
        );
        list_image14.add(
                new Image_in_Background(599, 300, 216, 471, 640)
        );

        background_templates.add(new Background_Template("6 frs",
                BitmapFactory.decodeResource(activity.getResources(), R.drawable.back14),
                list_frame14, list_image14));

        ArrayList<Frame_in_Background> list_frame15 = new ArrayList<>();
        ArrayList<Image_in_Background> list_image15 = new ArrayList<>();
        list_frame15 = new ArrayList<>();
        list_frame15.add(
                new Frame_in_Background(0.26f, 0.115f, 0.208f, "w,97:107")
        );
        list_frame15.add(
                new Frame_in_Background(0.536f, 0.264f, 0.207f, "w,103:107")
        );
        list_frame15.add(
                new Frame_in_Background(0.7972f, 0.3985f, 0.208f, "w,93:107")
        );


        list_image15 = new ArrayList<>();
        list_image15.add(

                new Image_in_Background(212, 91, 210, 97, 107)
        );
        list_image15.add(
                new Image_in_Background(428, 210, 207, 105, 107)
        );
        list_image15.add(
                new Image_in_Background(653, 317, 209, 93, 107)
        );


        background_templates.add(new Background_Template("3 frs",
                BitmapFactory.decodeResource(activity.getResources(), R.drawable.back15),
                list_frame15, list_image15));


        ArrayList<Frame_in_Background> list_frame16 = new ArrayList<>();
        ArrayList<Image_in_Background> list_image16 = new ArrayList<>();
        list_frame16 = new ArrayList<>();
        list_frame16.add(
                new Frame_in_Background(0.057f, 0.244f, 0.08f, "w,110:74")
        );
        list_frame16.add(
                new Frame_in_Background(0.2585f, 0.195f, 0.135f, "w,19:15")
        );
        list_frame16.add(
                new Frame_in_Background(0.495f, 0.051f, 0.12f, "w,73:107")
        );
        list_frame16.add(
                new Frame_in_Background(0.655f, 0.179f, 0.12f, "w,73:107")
        );

        list_frame16.add(
                new Frame_in_Background(0.82f, 0.213f, 0.082f, "w,109:74")
        );

        list_frame16.add(
                new Frame_in_Background(0.93f, 0.346f, 0.082f, "w,109:74")
        );
        list_frame16.add(
                new Frame_in_Background(0.728f, 0.37f, 0.135f, "w,19:15")
        );
        list_frame16.add(
                new Frame_in_Background(0.495f, 0.47f, 0.082f, "w,109:74")
        );
        list_frame16.add(
                new Frame_in_Background(0.333f, 0.395f, 0.12f, "w,73:107")
        );
        list_frame16.add(
                new Frame_in_Background(0.1635f, 0.379f, 0.082f, "w,109:74")
        );
        list_frame16.add(
                new Frame_in_Background(0.495f, 0.257f, 0.175f, "w,24:31")
        );



        list_image16 = new ArrayList<>();
        list_image16.add(
                new Image_in_Background(51, 224, 82, 111, 74)
        );
        list_image16.add(
                new Image_in_Background(214, 170, 135, 191, 150)
        );
        list_image16.add(
                new Image_in_Background(455, 46, 120, 73, 107)
        );
        list_image16.add(
                new Image_in_Background(601, 157, 120, 73, 107)
        );
        list_image16.add(
                new Image_in_Background(720, 197, 82, 111, 74)
        );
        list_image16.add(
                new Image_in_Background(818, 319, 82, 111, 74)
        );
        list_image16.add(
                new Image_in_Background(603, 320, 135, 191, 150)
        );
        list_image16.add(
                new Image_in_Background(435, 430, 82, 111, 74)
        );
        list_image16.add(
                new Image_in_Background(305, 348, 120, 73, 107)
        );
        list_image16.add(
                new Image_in_Background(141, 348, 82, 111, 74)
        );
        list_image16.add(
                new Image_in_Background(427, 212, 175, 24, 31)
        );


        background_templates.add(new Background_Template("11f rs",
                BitmapFactory.decodeResource(activity.getResources(), R.drawable.back16),
                list_frame16, list_image16));

        ArrayList<Frame_in_Background> list_frame17 = new ArrayList<>();
        ArrayList<Image_in_Background> list_image17 = new ArrayList<>();
        list_frame17 = new ArrayList<>();
        list_frame17.add(
                new Frame_in_Background(0.17f, 0.201f, 0.14f, "w,79:110")
        );
        list_frame17.add(
                new Frame_in_Background(0.335f, 0.13f, 0.12f, "w,67:95")
        );
        list_frame17.add(
                new Frame_in_Background(0.5f, 0.1965f, 0.2f, "w,21:31")
        );
        list_frame17.add(
                new Frame_in_Background(0.679f, 0.315f, 0.14f, "w,39:55")
        );

        list_frame17.add(
                new Frame_in_Background(0.6678f, 0.13f, 0.12f, "w,67:95")
        );

        list_frame17.add(
                new Frame_in_Background(0.325f, 0.315f, 0.14f, "w,39:55")
        );
        list_frame17.add(
                new Frame_in_Background(0.835f, 0.215f, 0.14f, "w,79:110")
        );



        list_image17 = new ArrayList<>();
        list_image17.add(
                new Image_in_Background(153, 175, 140, 79, 110)
        );
        list_image17.add(
                new Image_in_Background(307, 114, 121, 67, 95)
        );
        list_image17.add(
                new Image_in_Background(293, 270, 140, 39, 55)
        );
        list_image17.add(
                new Image_in_Background(433, 155, 200, 21, 31)
        );
        list_image17.add(
                new Image_in_Background(611, 114, 121, 672, 950)
        );
        list_image17.add(
                new Image_in_Background(610, 270, 140, 39, 55)
        );
        list_image17.add(
                new Image_in_Background(750, 184, 140, 79, 110)
        );


        background_templates.add(new Background_Template("7f rs",
                BitmapFactory.decodeResource(activity.getResources(), R.drawable.back17),
                list_frame17, list_image17));


        ArrayList<Frame_in_Background> list_frame18 = new ArrayList<>();
        ArrayList<Image_in_Background> list_image18 = new ArrayList<>();
        list_frame18 = new ArrayList<>();
        list_frame18.add(
                new Frame_in_Background(0.12f, 0.42f, 0.085f, "w,99:65")
        );
        list_frame18.add(
                new Frame_in_Background(0.193f, 0.605f, 0.15f, "w,61:40")
        );
        list_frame18.add(
                new Frame_in_Background(0.307f, 0.377f, 0.15f, "w,21:30")
        );
        list_frame18.add(
                new Frame_in_Background(0.5f, 0.41f, 0.225f, "w,2:3")
        );

        list_frame18.add(
                new Frame_in_Background(0.8f, 0.412f, 0.15f, "w,61:40")
        );

        list_frame18.add(
                new Frame_in_Background(0.5f, 0.65f, 0.1f, "w,99:65")
        );
        list_frame18.add(
                new Frame_in_Background(0.727f, 0.6f, 0.1f, "w,99:65")
        );
        list_frame18.add(
                new Frame_in_Background(0.885f, 0.62f, 0.125f, "w,2:3")
        );



        list_image18 = new ArrayList<>();
        list_image18.add(
                new Image_in_Background(102, 387, 85, 99, 65)
        );
        list_image18.add(
                new Image_in_Background(148, 509, 155, 40, 60)
        );
        list_image18.add(
                new Image_in_Background(277, 321, 150, 2, 3)
        );
        list_image18.add(
                new Image_in_Background(425, 317, 225, 3, 2)
        );
        list_image18.add(
                new Image_in_Background(621, 351, 150, 40, 60)
        );
        list_image18.add(
                new Image_in_Background(620, 542, 98, 99, 65)
        );
        list_image18.add(
                new Image_in_Background(425, 587, 99, 99, 65)
        );
        list_image18.add(
                new Image_in_Background(812, 545, 122, 2, 3)
        );


        background_templates.add(new Background_Template("8f rs",
                BitmapFactory.decodeResource(activity.getResources(), R.drawable.back18),
                list_frame18, list_image18));

        ArrayList<Frame_in_Background> list_frame19 = new ArrayList<>();
        ArrayList<Image_in_Background> list_image19 = new ArrayList<>();
        list_frame19 = new ArrayList<>();
        list_frame19.add(
                new Frame_in_Background(0.043f, 0.219f, 0.148f, "w,168:132")
        );
        list_frame19.add(
                new Frame_in_Background(0.959f, 0.218f, 0.15f, "w,168:132")
        );
        list_frame19.add(
                new Frame_in_Background(0.338f, 0.074f, 0.28f, "w,186:251")
        );
        list_frame19.add(
                new Frame_in_Background(0.657f, 0.075f, 0.28f, "w,186:251")
        );
        list_frame19.add(
                new Frame_in_Background(0.251f, 0.453f, 0.15f, "w,172:135")
        );
        list_frame19.add(
                new Frame_in_Background(0.786f, 0.453f, 0.15f, "w,172:135")
        );
        list_frame19.add(
                new Frame_in_Background(0.516f, 0.475f, 0.188f, "w,133:169")
        );

        list_image19 = new ArrayList<>();
        list_image19.add(
                new Image_in_Background(203, 385, 152, 172,135)
        );
        list_image19.add(
                new Image_in_Background(635, 385, 152, 172, 135)
        );
        list_image19.add(
                new Image_in_Background(35, 187, 148, 168, 132)
        );
        list_image19.add(
                new Image_in_Background(775, 185, 152, 168, 132)
        );
        list_image19.add(
                new Image_in_Background(522, 53, 280, 186, 251)
        );
        list_image19.add(
                new Image_in_Background(268, 53, 280, 186, 251)
        );
        list_image19.add(
                new Image_in_Background(439, 385, 192, 133, 169)
        );

       background_templates.add(new Background_Template("3frs",
                BitmapFactory.decodeResource(activity.getResources(), R.drawable.back19),
                list_frame19, list_image19));

        ArrayList<Frame_in_Background> list_frame20 = new ArrayList<>();
        ArrayList<Image_in_Background> list_image20 = new ArrayList<>();
        list_frame20 = new ArrayList<>();
        list_frame20.add(
                new Frame_in_Background(0.08f, 0.25f, 0.345f, "w,218:305")
        );
        list_frame20.add(
                new Frame_in_Background(0.496f, 0.1285f, 0.345f, "w,220:310")
        );
        list_frame20.add(
                new Frame_in_Background(0.916f, 0.0295f, 0.343f, "w,216:305")
        );

        list_image20 = new ArrayList<>();
        list_image20.add(
                new Image_in_Background(60, 164, 342, 220, 305)
        );
        list_image20.add(
                new Image_in_Background(374, 85, 344, 220, 305)
        );
        list_image20.add(
                new Image_in_Background(693, 20, 344, 220, 305)
        );


        background_templates.add(new Background_Template("3frs",
                BitmapFactory.decodeResource(activity.getResources(), R.drawable.back20),
                list_frame20, list_image20));

        return background_templates;
    }
}
