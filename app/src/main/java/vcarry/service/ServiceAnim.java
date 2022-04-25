package vcarry.service;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.DisplayMetrics;


import com.kessi.photovideomaker.KessiApplication;
import com.kessi.photovideomaker.R;
import com.kessi.photovideomaker.util.KSUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import vcarry.data.Background_Template;
import vcarry.data.Image_in_Background;
import vcarry.mask.KessiMaskBitmap3D;
import vcarry.util.FileUtils;
import vcarry.util.ScalingUtilities;
import vcarry.util.Utils;


public class ServiceAnim extends IntentService {
    public static final String ACTION_CREATE_NEW_THEME_IMAGES = "ACTION_CREATE_NEW_THEME_IMAGES";
    public static final String ACTION_UPDATE_THEME_IMAGES = "ACTION_UPDATE_THEME_IMAGES";
    public static final String EXTRA_SELECTED_THEME = "selected_theme";
    public static ArrayList<String> arrayList;
    public static boolean isImageComplate = false;
    public static final Object mLock = new Object();
    public static String selectedTheme;
    KessiApplication application;
    boolean check;
    private Builder mBuilder;
    private NotificationManager mNotifyManager;
    int totalImages;
    DisplayMetrics displayMetrics;
    private static Background_Template background_template;
    Bitmap bg, bg_cushion, bg_cushion_black;

    public static Background_Template getBackground_template() {
        return background_template;
    }

    public static void setBackground_template(Background_Template background_template) {
        ServiceAnim.background_template = background_template;
    }

    public ServiceAnim() {
        this(ServiceAnim.class.getName());
    }

    public ServiceAnim(String name) {
        super(name);
        this.check = false;
    }

    public void onCreate() {
        super.onCreate();
        this.application = KessiApplication.getInstance();
        displayMetrics = getResources().getDisplayMetrics();
        bg = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.back2),
                KessiApplication.VIDEO_WIDTH, KessiApplication.VIDEO_HEIGHT, false);
        bg_cushion = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bg_cushion),
                KessiApplication.VIDEO_WIDTH, KessiApplication.VIDEO_HEIGHT, false);
        bg_cushion_black = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bg_cushion_black),
                KessiApplication.VIDEO_WIDTH, KessiApplication.VIDEO_HEIGHT, false);
    }

    @Deprecated
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("WrongThread")
    protected void onHandleIntent(Intent intent) {
        this.mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        this.mBuilder = new Builder(this);
        this.mBuilder.setContentTitle("Preparing Video").setContentText("Making in progress").setSmallIcon(R.mipmap.ic_launcher);
        selectedTheme = intent.getStringExtra(EXTRA_SELECTED_THEME);
        arrayList = KSUtil.videoPathList;
        this.application.initArray();
        isImageComplate = false;
        new ProcessImage1().execute();
    }

    class ProcessImage1 extends AsyncTask<Void, Void, Boolean> {
        protected void onPreExecute() {
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            createImages_With_Background();
            //createImages();
            return null;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            isImageComplate = true;
            stopSelf();
            isSameTheme();
        }
    }

    private void createImages_With_Background() {
        long statTime = System.currentTimeMillis();
        Bitmap newSecondBmp2 = null;
        this.totalImages = arrayList.size();
        int i = 0;
        int img_size = 0;

        ArrayList<Image_in_Background> list_imageinBackground = new ArrayList<>();
        //list_imageinBackground = background_template.getList_image();
        list_imageinBackground.add(
                new Image_in_Background(419, 114, 504, 17, 54)
        );
        list_imageinBackground.add(
                new Image_in_Background(226, 153, 427, 16, 44)
        );
        list_imageinBackground.add(
                new Image_in_Background(614, 147, 436, 19, 52)
        );
        list_imageinBackground.add(
                new Image_in_Background(39, 189, 356, 151, 350)
        );
        list_imageinBackground.add(
                new Image_in_Background(809, 190, 354, 99, 230)
        );

        while (i < arrayList.size() - 1 && isSameTheme() && !KessiApplication.isBreak) {
            Bitmap newFirstBmp;
            File imgDir = FileUtils.getImageDirectory(this.application.selectedTheme.toString());
            Bitmap firstBitmap = bg;
            Bitmap temp;
            if (i == 0) {
//                Log.e("pathsssas",arrayList.get(i));
                firstBitmap = ScalingUtilities.checkBitmap(arrayList.get(i), application);
                newFirstBmp = ScalingUtilities.Paint_Image(bg_cushion, bg, list_imageinBackground.get(i).getLeft_per(), list_imageinBackground.get(i).getTop_per(),
                        list_imageinBackground.get(i).getHeight_per(), list_imageinBackground.get(i).getWidth_ratio(),
                        list_imageinBackground.get(i).getHeight_ratio());
                bg = newFirstBmp;
             //   temp.recycle();
                firstBitmap.recycle();
                System.gc();
            } else {
                if (newSecondBmp2 == null || newSecondBmp2.isRecycled()) {
                    firstBitmap = ScalingUtilities.checkBitmap(arrayList.get(i), application);
                    temp = ScalingUtilities.scaleCenterCrop(firstBitmap, KessiApplication.VIDEO_WIDTH, KessiApplication.VIDEO_HEIGHT);
                    newSecondBmp2 = ScalingUtilities.Paint_Image(firstBitmap, bg, list_imageinBackground.get(i).getLeft_per(), list_imageinBackground.get(i).getTop_per(),
                            list_imageinBackground.get(i).getHeight_per(), list_imageinBackground.get(i).getWidth_ratio(),
                            list_imageinBackground.get(i).getHeight_ratio());
                    temp.recycle();
                    firstBitmap.recycle();
                    System.gc();
                }
                newFirstBmp = newSecondBmp2;
            }
            Bitmap secondBitmap = ScalingUtilities.checkBitmap(arrayList.get(i+1), application);
            newSecondBmp2 = ScalingUtilities.Paint_Image(secondBitmap, bg, list_imageinBackground.get(i).getLeft_per(), list_imageinBackground.get(i).getTop_per(),
                    list_imageinBackground.get(i).getHeight_per(), list_imageinBackground.get(i).getWidth_ratio(),
                    list_imageinBackground.get(i).getHeight_ratio());
            bg = newSecondBmp2;
//
//            temp2.recycle();
            secondBitmap.recycle();
            System.gc();
            KessiMaskBitmap3D.reintRect();

            KessiMaskBitmap3D.EFFECT effect = (KessiMaskBitmap3D.EFFECT) this.application.selectedTheme.getTheme().get(i % this.application.selectedTheme.getTheme().size());

            Bitmap bitmap3 = null;
            for (int j = 0; ((float) j) < KessiMaskBitmap3D.ANIMATED_FRAME && isSameTheme() && !KessiApplication.isBreak; j++) {


                Bitmap bmp = Bitmap.createBitmap(KessiApplication.VIDEO_WIDTH, KessiApplication.VIDEO_HEIGHT, Bitmap.Config.ARGB_8888);
                Paint paint = new Paint(1);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
                Canvas canvas = new Canvas(bmp);
                canvas.drawBitmap(newFirstBmp, 0.0f, 0.0f, null);
                if (effect.getMask(KessiApplication.VIDEO_WIDTH, KessiApplication.VIDEO_HEIGHT, j) != null) {

                    canvas.drawBitmap(effect.getMask(KessiApplication.VIDEO_WIDTH, KessiApplication.VIDEO_HEIGHT, j), 0.0f, 0.0f, paint);
                    bitmap3 = Bitmap.createBitmap(KessiApplication.VIDEO_WIDTH, KessiApplication.VIDEO_HEIGHT, Bitmap.Config.ARGB_8888);
                    Canvas canvas2 = new Canvas(bitmap3);
                    canvas2.drawBitmap(newSecondBmp2, 0.0f, 0.0f, null);
                    canvas2.drawBitmap(bmp, 0.0f, 0.0f, new Paint());

                }


//                bitmap3 = BitmapCompression.resizeImageToNewSize(bitmap3, displayMetrics.widthPixels, displayMetrics.heightPixels - ((int) (getResources().getDisplayMetrics().density * 50.0f)));
                if (isSameTheme()) {
                    File file = imgDir;
                    File file2 = new File(file, String.format("img%05d.jpg", new Object[]{Integer.valueOf(img_size)}));
                    img_size++;
                    try {
                        if (file2.exists()) {
                            file2.delete();
                        }
                        OutputStream fileOutputStream = new FileOutputStream(file2);
                        bitmap3.compress(CompressFormat.JPEG, 70, fileOutputStream);
                        fileOutputStream.close();
                        System.gc();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                    boolean isBreak = false;
                    while (this.application.isEditEnable) {
                        if (this.application.min_pos != Integer.MAX_VALUE) {
                            i = this.application.min_pos;
                            isBreak = true;
                        }
//                        if (KessiApplication.isBreak) {
//                            isImageComplate = true;
//                            stopSelf();
//                            return;
//                        }
                    }
                    if (isBreak) {
                        ArrayList<String> str = new ArrayList();
                        str.addAll(this.application.videoImages);
                        this.application.videoImages.clear();
                        int size = Math.min(str.size(), Math.max(0, i - i) * 30);
                        for (int p = 0; p < size; p++) {
                            this.application.videoImages.add((String) str.get(p));
                        }
                        this.application.min_pos = Integer.MAX_VALUE;
                    }
                    if (!isSameTheme() || KessiApplication.isBreak) {
                        break;
                    }
                    this.application.videoImages.add(file2.getAbsolutePath());
                    if (((float) j) == KessiMaskBitmap3D.ANIMATED_FRAME - Utils.DEFAULT_FONT_SCALE) {
                        for (int m = 0; m < 8 && isSameTheme() && !KessiApplication.isBreak; m++) {
                            this.application.videoImages.add(file2.getAbsolutePath());
                        }
                    }
//                    calculateProgress(i, j);
                    if (((float) img_size) == KessiMaskBitmap3D.ANIMATED_FRAME) {
                        break;
                    }
                }
            }
            i++;
//            if (arrayList.size() <= 4) {
//                if (i == 2) {
//                    sendBroadcast(new Intent("com.servicecomplete"));
//                }
//            } else {
//                if (i == 4) {
//                    sendBroadcast(new Intent("com.servicecomplete"));
//                }
//            }
            this.check = true;
        }
        isImageComplate = true;
        stopSelf();
        isSameTheme();
    }

    private void createImages() {
        long statTime = System.currentTimeMillis();
        Bitmap newSecondBmp2 = null;
        this.totalImages = arrayList.size();
        int i = 0;
        int img_size = 0;

        //Bitmap bg = Bitmap.createBitmap(KessiApplication.VIDEO_WIDTH, KessiApplication.VIDEO_HEIGHT, Bitmap.Config.ARGB_8888);
        //Bitmap bg = Bitmap.createBitmap(new int[]{0,0,0}, KessiApplication.VIDEO_WIDTH, KessiApplication.VIDEO_HEIGHT, Bitmap.Config.ARGB_8888);

        while (i < arrayList.size() - 1 && isSameTheme() && !KessiApplication.isBreak) {
            Bitmap newFirstBmp;
            File imgDir = FileUtils.getImageDirectory(this.application.selectedTheme.toString());
            Bitmap firstBitmap = null;
            Bitmap temp;
            if (i == 0) {
//                Log.e("pathsssas",arrayList.get(i));
                firstBitmap = ScalingUtilities.checkBitmap(arrayList.get(i), application);
                temp = ScalingUtilities.scaleCenterCrop(firstBitmap, KessiApplication.VIDEO_WIDTH, KessiApplication.VIDEO_HEIGHT);
                newFirstBmp = ScalingUtilities.ConvetrSameSize(bg_cushion_black, temp, KessiApplication.VIDEO_WIDTH, KessiApplication.VIDEO_HEIGHT, Utils.DEFAULT_FONT_SCALE, 0.0f);
                //   temp.recycle();
                firstBitmap.recycle();
                System.gc();
            } else {
                if (newSecondBmp2 == null || newSecondBmp2.isRecycled()) {
                    firstBitmap = ScalingUtilities.checkBitmap(arrayList.get(i), application);
                    temp = ScalingUtilities.scaleCenterCrop(firstBitmap, KessiApplication.VIDEO_WIDTH, KessiApplication.VIDEO_HEIGHT);
                    newSecondBmp2 = ScalingUtilities.ConvetrSameSize(firstBitmap, temp, KessiApplication.VIDEO_WIDTH, KessiApplication.VIDEO_HEIGHT, Utils.DEFAULT_FONT_SCALE, 0.0f);
                    temp.recycle();
                    firstBitmap.recycle();
                    System.gc();
                }
                newFirstBmp = newSecondBmp2;
            }
            Bitmap secondBitmap = ScalingUtilities.checkBitmap(arrayList.get(i + 1), application);
            Bitmap temp2 = ScalingUtilities.scaleCenterCrop(secondBitmap, KessiApplication.VIDEO_WIDTH, KessiApplication.VIDEO_HEIGHT);
            newSecondBmp2 = ScalingUtilities.ConvetrSameSize(secondBitmap, temp2, KessiApplication.VIDEO_WIDTH, KessiApplication.VIDEO_HEIGHT, Utils.DEFAULT_FONT_SCALE, 0.0f);
//
//            temp2.recycle();
            secondBitmap.recycle();
            System.gc();
            KessiMaskBitmap3D.reintRect();

            KessiMaskBitmap3D.EFFECT effect = (KessiMaskBitmap3D.EFFECT) this.application.selectedTheme.getTheme().get(i % this.application.selectedTheme.getTheme().size());

            Bitmap bitmap3 = null;
            for (int j = 0; ((float) j) < KessiMaskBitmap3D.ANIMATED_FRAME && isSameTheme() && !KessiApplication.isBreak; j++) {


                Bitmap bmp = Bitmap.createBitmap(KessiApplication.VIDEO_WIDTH, KessiApplication.VIDEO_HEIGHT, Bitmap.Config.ARGB_8888);
                Paint paint = new Paint(1);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
                Canvas canvas = new Canvas(bmp);
                canvas.drawBitmap(newFirstBmp, 0.0f, 0.0f, null);
                if (effect.getMask(KessiApplication.VIDEO_WIDTH, KessiApplication.VIDEO_HEIGHT, j) != null) {

                    canvas.drawBitmap(effect.getMask(KessiApplication.VIDEO_WIDTH, KessiApplication.VIDEO_HEIGHT, j), 0.0f, 0.0f, paint);
                    bitmap3 = Bitmap.createBitmap(KessiApplication.VIDEO_WIDTH, KessiApplication.VIDEO_HEIGHT, Bitmap.Config.ARGB_8888);
                    Canvas canvas2 = new Canvas(bitmap3);
                    canvas2.drawBitmap(newSecondBmp2, 0.0f, 0.0f, null);
                    canvas2.drawBitmap(bmp, 0.0f, 0.0f, new Paint());

                }


//                bitmap3 = BitmapCompression.resizeImageToNewSize(bitmap3, displayMetrics.widthPixels, displayMetrics.heightPixels - ((int) (getResources().getDisplayMetrics().density * 50.0f)));
                if (isSameTheme()) {
                    File file = imgDir;
                    File file2 = new File(file, String.format("img%05d.jpg", new Object[]{Integer.valueOf(img_size)}));
                    img_size++;
                    try {
                        if (file2.exists()) {
                            file2.delete();
                        }
                        OutputStream fileOutputStream = new FileOutputStream(file2);
                        bitmap3.compress(CompressFormat.JPEG, 70, fileOutputStream);
                        fileOutputStream.close();
                        System.gc();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                    boolean isBreak = false;
                    while (this.application.isEditEnable) {
                        if (this.application.min_pos != Integer.MAX_VALUE) {
                            i = this.application.min_pos;
                            isBreak = true;
                        }
//                        if (KessiApplication.isBreak) {
//                            isImageComplate = true;
//                            stopSelf();
//                            return;
//                        }
                    }
                    if (isBreak) {
                        ArrayList<String> str = new ArrayList();
                        str.addAll(this.application.videoImages);
                        this.application.videoImages.clear();
                        int size = Math.min(str.size(), Math.max(0, i - i) * 30);
                        for (int p = 0; p < size; p++) {
                            this.application.videoImages.add((String) str.get(p));
                        }
                        this.application.min_pos = Integer.MAX_VALUE;
                    }
                    if (!isSameTheme() || KessiApplication.isBreak) {
                        break;
                    }
                    this.application.videoImages.add(file2.getAbsolutePath());
                    if (((float) j) == KessiMaskBitmap3D.ANIMATED_FRAME - Utils.DEFAULT_FONT_SCALE) {
                        for (int m = 0; m < 8 && isSameTheme() && !KessiApplication.isBreak; m++) {
                            this.application.videoImages.add(file2.getAbsolutePath());
                        }
                    }
//                    calculateProgress(i, j);
                    if (((float) img_size) == KessiMaskBitmap3D.ANIMATED_FRAME) {
                        break;
                    }
                }
            }
            i++;
//            if (arrayList.size() <= 4) {
//                if (i == 2) {
//                    sendBroadcast(new Intent("com.servicecomplete"));
//                }
//            } else {
//                if (i == 4) {
//                    sendBroadcast(new Intent("com.servicecomplete"));
//                }
//            }
            this.check = true;
        }
        isImageComplate = true;
        stopSelf();
        isSameTheme();
    }


    private boolean isSameTheme() {
        return selectedTheme.equals(this.application.getCurrentTheme());
    }

//    private void calculateProgress(int i, int j) {
//        final int progress = (int) ((100.0f * ((float) this.application.videoImages.size())) / ((float) ((this.totalImages - 1) * 30)));
//        updateNotification(progress);
//        new Handler(Looper.getMainLooper()).post(new Runnable() {
//            public void run() {
//                OnProgressReceiver receiver = ServiceAnim.this.application.getOnProgressReceiver();
//                if (receiver != null) {
//                    receiver.onImageProgressUpdate(progress);
//                }
//            }
//        });
//    }
//    private void updateNotification(int progress) {
//        this.mBuilder.setProgress(100, (int) ((25.0f * ((float) progress)) / 100.0f), false);
//        this.mNotifyManager.notify(1001, this.mBuilder.build());
//    }

//    public String getRealPathFromURI(Context context, Uri contentUri) {
//        Cursor cursor = null;
//        try {
//            cursor = context.getContentResolver().query(contentUri, new String[]{"_data"}, null, null, null);
//            int column_index = cursor.getColumnIndexOrThrow("_data");
//            cursor.moveToFirst();
//            String string = cursor.getString(column_index);
//            return string;
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//        }
//    }
}
