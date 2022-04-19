package vcarry.mask;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import androidx.core.internal.view.SupportMenu;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class KessiMaskBitmap3D {
    public static float ANIMATED_FRAME = 0.0f;
    public static int ANIMATED_FRAME_CAL = ((int) (ANIMATED_FRAME - 1.0f));
    static final int HORIZONTAL = 0;
    public static int ORIGANAL_FRAME = 0;
    public static int TOTAL_FRAME = 0;
    static final int VERTICALE = 1;
    private static int averageHeight;
    private static int averageWidth;
    private static float axisX;
    private static float axisY;
    private static Bitmap[][] bitmaps;
    private static Camera camera = new Camera();
    public static int direction = 0;
    private static float f18f;
    private static Matrix matrix = new Matrix();
    static final Paint paint = new Paint();
    private static int partNumber = 8;
    static int[][] randRect = ((int[][]) Array.newInstance(Integer.TYPE, new int[]{20, 20}));
    static Random random = new Random();
    public static EFFECT rollMode;
    private static float rotateDegree;

    public enum EFFECT {

        NONE("NONE") {
            public Bitmap getMask(int w, int h, int factor) {
                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                return mask;
            }
        },
        CIRCLE_LEFT_TOP("CIRCLE LEFT TOP") {
            public Bitmap getMask(int w, int h, int factor) {
                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                canvas.drawCircle(0.0f, 0.0f, (((float) Math.sqrt((double) ((w * w) + (h * h)))) / ((float) KessiMaskBitmap3D.ANIMATED_FRAME_CAL)) * ((float) factor), paint);
                drawText(canvas);
                return mask;
            }
        },
        CIRCLE_RIGHT_TOP("Circle right top") {
            public Bitmap getMask(int w, int h, int factor) {
                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                canvas.drawCircle((float) w, 0.0f, (((float) Math.sqrt((double) ((w * w) + (h * h)))) / ((float) KessiMaskBitmap3D.ANIMATED_FRAME_CAL)) * ((float) factor), paint);
                drawText(canvas);
                return mask;
            }
        },
        CIRCLE_LEFT_BOTTOM("Circle left bottom") {
            public Bitmap getMask(int w, int h, int factor) {
                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                canvas.drawCircle(0.0f, (float) h, (((float) Math.sqrt((double) ((w * w) + (h * h)))) / ((float) KessiMaskBitmap3D.ANIMATED_FRAME_CAL)) * ((float) factor), paint);
                drawText(canvas);
                return mask;
            }
        },
        CIRCLE_RIGHT_BOTTOM("Circle right bottom") {
            public Bitmap getMask(int w, int h, int factor) {
                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                canvas.drawCircle((float) w, (float) h, (((float) Math.sqrt((double) ((w * w) + (h * h)))) / ((float) KessiMaskBitmap3D.ANIMATED_FRAME_CAL)) * ((float) factor), paint);
                drawText(canvas);
                return mask;
            }
        },
        CIRCLE_IN("Circle in") {
            public Bitmap getMask(int w, int h, int factor) {
                Paint paint = new Paint(1);
                paint.setColor(-16777216);
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                float r = KessiMaskBitmap3D.getRad(w * 2, h * 2);
                float f = (r / ((float) KessiMaskBitmap3D.ANIMATED_FRAME_CAL)) * ((float) factor);
                paint.setColor(-16777216);
                canvas.drawColor(-16777216);
                paint.setColor(0);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
                canvas.drawCircle(((float) w) / 2.0f, ((float) h) / 2.0f, r - f, paint);
                drawText(canvas);
                return mask;
            }
        },
        CIRCLE_OUT("Circle out") {
            public Bitmap getMask(int w, int h, int factor) {
                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                canvas.drawCircle(((float) w) / 2.0f, ((float) h) / 2.0f, (KessiMaskBitmap3D.getRad(w * 2, h * 2) / ((float) KessiMaskBitmap3D.ANIMATED_FRAME_CAL)) * ((float) factor), paint);
                drawText(canvas);
                return mask;
            }
        },
        CROSS_IN("Cross in") {
            public Bitmap getMask(int w, int h, int factor) {
                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                float fx = (((float) w) / (((float) KessiMaskBitmap3D.ANIMATED_FRAME_CAL) * 2.0f)) * ((float) factor);
                float fy = (((float) h) / (((float) KessiMaskBitmap3D.ANIMATED_FRAME_CAL) * 2.0f)) * ((float) factor);
                Path path = new Path();
                path.moveTo(0.0f, 0.0f);
                path.lineTo(fx, 0.0f);
                path.lineTo(fx, fy);
                path.lineTo(0.0f, fy);
                path.lineTo(0.0f, 0.0f);
                path.close();
                path.moveTo((float) w, 0.0f);
                path.lineTo(((float) w) - fx, 0.0f);
                path.lineTo(((float) w) - fx, fy);
                path.lineTo((float) w, fy);
                path.lineTo((float) w, 0.0f);
                path.close();
                path.moveTo((float) w, (float) h);
                path.lineTo(((float) w) - fx, (float) h);
                path.lineTo(((float) w) - fx, ((float) h) - fy);
                path.lineTo((float) w, ((float) h) - fy);
                path.lineTo((float) w, (float) h);
                path.close();
                path.moveTo(0.0f, (float) h);
                path.lineTo(fx, (float) h);
                path.lineTo(fx, ((float) h) - fy);
                path.lineTo(0.0f, ((float) h) - fy);
                path.lineTo(0.0f, 0.0f);
                path.close();
                canvas.drawPath(path, paint);
                drawText(canvas);
                return mask;
            }
        },
        CROSS_OUT("Cross out") {
            public Bitmap getMask(int w, int h, int factor) {
                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                float fx = (((float) w) / (((float) KessiMaskBitmap3D.ANIMATED_FRAME_CAL) * 2.0f)) * ((float) factor);
                float fy = (((float) h) / (((float) KessiMaskBitmap3D.ANIMATED_FRAME_CAL) * 2.0f)) * ((float) factor);
                Path path = new Path();
                path.moveTo((((float) w) / 2.0f) + fx, 0.0f);
                path.lineTo((((float) w) / 2.0f) + fx, (((float) h) / 2.0f) - fy);
                path.lineTo((float) w, (((float) h) / 2.0f) - fy);
                path.lineTo((float) w, (((float) h) / 2.0f) + fy);
                path.lineTo((((float) w) / 2.0f) + fx, (((float) h) / 2.0f) + fy);
                path.lineTo((((float) w) / 2.0f) + fx, (float) h);
                path.lineTo((((float) w) / 2.0f) - fx, (float) h);
                path.lineTo((((float) w) / 2.0f) - fx, (((float) h) / 2.0f) - fy);
                path.lineTo(0.0f, (((float) h) / 2.0f) - fy);
                path.lineTo(0.0f, (((float) h) / 2.0f) + fy);
                path.lineTo((((float) w) / 2.0f) - fx, (((float) h) / 2.0f) + fy);
                path.lineTo((((float) w) / 2.0f) - fx, 0.0f);
                path.close();
                canvas.drawPath(path, paint);
                drawText(canvas);
                return mask;
            }
        },
        DIAMOND_IN("Diamond in") {
            public Bitmap getMask(int w, int h, int factor) {
                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                Path path = new Path();
                float fx = (((float) w) / ((float) KessiMaskBitmap3D.ANIMATED_FRAME_CAL)) * ((float) factor);
                float fy = (((float) h) / ((float) KessiMaskBitmap3D.ANIMATED_FRAME_CAL)) * ((float) factor);
                path.moveTo(((float) w) / 2.0f, (((float) h) / 2.0f) - fy);
                path.lineTo((((float) w) / 2.0f) + fx, ((float) h) / 2.0f);
                path.lineTo(((float) w) / 2.0f, (((float) h) / 2.0f) + fy);
                path.lineTo((((float) w) / 2.0f) - fx, ((float) h) / 2.0f);
                path.lineTo(((float) w) / 2.0f, (((float) h) / 2.0f) - fy);


                path.close();
                canvas.drawPath(path, paint);
                return mask;
            }
        },
        DIAMOND_OUT("Diamond out") {
            public Bitmap getMask(int w, int h, int factor) {
                Paint paint = new Paint(1);
                paint.setColor(-16777216);
                paint.setColor(0);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                canvas.drawColor(-16777216);
                Path path = new Path();
                float fx = ((float) w) - ((((float) w) / ((float) KessiMaskBitmap3D.ANIMATED_FRAME_CAL)) * ((float) factor));
                float fy = ((float) h) - ((((float) h) / ((float) KessiMaskBitmap3D.ANIMATED_FRAME_CAL)) * ((float) factor));
                path.moveTo(((float) w) / 2.0f, (((float) h) / 2.0f) - fy);
                path.lineTo((((float) w) / 2.0f) + fx, ((float) h) / 2.0f);
                path.lineTo(((float) w) / 2.0f, (((float) h) / 2.0f) + fy);
                path.lineTo((((float) w) / 2.0f) - fx, ((float) h) / 2.0f);
                path.lineTo(((float) w) / 2.0f, (((float) h) / 2.0f) - fy);
                path.close();
                paint.setColor(0);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
                canvas.drawPath(path, paint);
                drawText(canvas);
                return mask;
            }
        },
        ECLIPSE_IN("Eclipse in") {
            public Bitmap getMask(int w, int h, int factor) {
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                float hf = (((float) h) / (((float) KessiMaskBitmap3D.ANIMATED_FRAME_CAL) * 2.0f)) * ((float) factor);
                float wf = (((float) w) / (((float) KessiMaskBitmap3D.ANIMATED_FRAME_CAL) * 2.0f)) * ((float) factor);
                RectF rectFL = new RectF(-wf, 0.0f, wf, (float) h);
                RectF rectFT = new RectF(0.0f, -hf, (float) w, hf);
                RectF rectFR = new RectF(((float) w) - wf, 0.0f, ((float) w) + wf, (float) h);
                RectF rectFB = new RectF(0.0f, ((float) h) - hf, (float) w, ((float) h) + hf);
                canvas.drawOval(rectFL, KessiMaskBitmap3D.paint);
                canvas.drawOval(rectFT, KessiMaskBitmap3D.paint);
                canvas.drawOval(rectFR, KessiMaskBitmap3D.paint);
                canvas.drawOval(rectFB, KessiMaskBitmap3D.paint);
                drawText(canvas);
                return mask;
            }
        },

        /*ms*/
        CIRCLE_BOMB("CIRCLE_BOMB") {
            public Bitmap getMask(int w, int h, int factor) {
                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                canvas.drawCircle(0.0f, 0.0f, (((float) Math.sqrt((double) ((w * w) + (h * h)))) / ((float) KessiMaskBitmap3D.ANIMATED_FRAME_CAL)) * ((float) factor), paint);
                canvas.drawCircle((float) w, 0.0f, (((float) Math.sqrt((double) ((w * w) + (h * h)))) / ((float) KessiMaskBitmap3D.ANIMATED_FRAME_CAL)) * ((float) factor), paint);
                canvas.drawCircle(0.0f, (float) h, (((float) Math.sqrt((double) ((w * w) + (h * h)))) / ((float) KessiMaskBitmap3D.ANIMATED_FRAME_CAL)) * ((float) factor), paint);
                canvas.drawCircle((float) w, (float) h, (((float) Math.sqrt((double) ((w * w) + (h * h)))) / ((float) KessiMaskBitmap3D.ANIMATED_FRAME_CAL)) * ((float) factor), paint);
                canvas.drawCircle(((float) w) / 2.0f, ((float) h) / 2.0f, (KessiMaskBitmap3D.getRad(w * 2, h * 2) / ((float) KessiMaskBitmap3D.ANIMATED_FRAME_CAL)) * ((float) factor), paint);

                drawText(canvas);
                return mask;
            }
        },
        /*ms*/
        FOUR_TRIANGLE("Four triangle") {
            public Bitmap getMask(int w, int h, int factor) {
                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                float ratioX = (((float) w) / (((float) KessiMaskBitmap3D.ANIMATED_FRAME_CAL) * 2.0f)) * ((float) factor);
                float ratioY = (((float) h) / (((float) KessiMaskBitmap3D.ANIMATED_FRAME_CAL) * 2.0f)) * ((float) factor);

                Path path = new Path();
                path.moveTo(0.0f, ratioY);
                path.lineTo(0.0f, 0.0f);
                path.lineTo(ratioX, 0.0f);
                path.lineTo((float) w, ((float) h) - ratioY);
                path.lineTo((float) w, (float) h);
                path.lineTo(((float) w) - ratioX, (float) h);
                path.lineTo(0.0f, ratioY);
                path.close();

                path.moveTo(((float) w) - ratioX, 0.0f);
                path.lineTo((float) w, 0.0f);
                path.lineTo((float) w, ratioY);
                path.lineTo(ratioX, (float) h);
                path.lineTo(0.0f, (float) h);
                path.lineTo(0.0f, ((float) h) - ratioY);
                path.lineTo(((float) w) - ratioX, 0.0f);
                path.close();

                canvas.drawPath(path, paint);
                return mask;
            }
        },
        HORIZONTAL_RECT("Horizontal rect") {
            public Bitmap getMask(int w, int h, int factor) {
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                float wf = ((float) w) / 10.0f;
                float rectW = (wf / ((float) KessiMaskBitmap3D.ANIMATED_FRAME_CAL)) * ((float) factor);
                for (int i = 0; i < 10; i++) {
                    canvas.drawRect(new Rect((int) (((float) i) * wf), 0, (int) ((((float) i) * wf) + rectW), h), paint);
                }
                drawText(canvas);
                return mask;
            }
        },

        /*ms*/
        ZIP("ZIP") {
            public Bitmap getMask(int w, int h, int factor) {
                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                float factorX = ((float) KessiMaskBitmap3D.ANIMATED_FRAME_CAL) / 2.0f;
                canvas.drawRoundRect(new RectF(0.0f, ((float) h) / 2.0f, (((float) w) / (((float) KessiMaskBitmap3D.ANIMATED_FRAME_CAL) / 2.0f)) * ((float) factor), ((float) h) / 4.0f), 0.0f, 0.0f, paint);
                canvas.drawRoundRect(new RectF(0.0f, ((float) h) / 1.3f, (((float) w) / (((float) KessiMaskBitmap3D.ANIMATED_FRAME_CAL) / 2.0f)) * ((float) factor), ((float) h)), 0.0f, 0.0f, paint);
                if (((float) factor) >= 0.5f + factorX) {
                    canvas.drawRoundRect(new RectF(((float) w) - ((((float) w) / (((float) (KessiMaskBitmap3D.ANIMATED_FRAME_CAL - 1)) / 2.0f)) * ((float) ((int) (((float) factor) - factorX)))), 0.0f, (float) w, (float) h), 0.0f, 0.0f, paint);
                }

                Matrix matrix = new Matrix();
                matrix.postRotate(90); // anti-clockwise by 90 degrees

                Bitmap rotatedBitmap = Bitmap.createBitmap(mask, 0, 0, mask.getWidth(), mask.getHeight(), matrix, true);
                return rotatedBitmap;

            }
        },
        LEAF("LEAF") {
            public Bitmap getMask(int w, int h, int factor) {
                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setStrokeCap(Paint.Cap.BUTT);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                float fx = (float) ((w / KessiMaskBitmap3D.ANIMATED_FRAME_CAL) * factor);
                float fy = (float) ((h / KessiMaskBitmap3D.ANIMATED_FRAME_CAL) * factor);
                Canvas canvas = new Canvas(mask);
                Path path = new Path();
                path.moveTo(0.0f, (float) h);
                path.cubicTo(0.0f, (float) h, (((float) w) / 2.0f) - fx, (((float) h) / 2.0f) - fy, (float) w, 0.0f);
                path.cubicTo((float) w, 0.0f, (((float) w) / 2.0f) + fx, (((float) h) / 2.0f) + fy, 0.0f, (float) h);


                path.close();
                canvas.drawPath(path, paint);
                drawText(canvas);
                return mask;
            }
        },

        /*ms*/
        OPEN_DOOR("OPEN_DOOR") {
            public Bitmap getMask(int w, int h, int factor) {
                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setStrokeCap(Paint.Cap.BUTT);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                float fx = (float) ((w / KessiMaskBitmap3D.ANIMATED_FRAME_CAL) * factor);
                float fy = (float) ((h / KessiMaskBitmap3D.ANIMATED_FRAME_CAL) * factor);
                Canvas canvas = new Canvas(mask);
                Path path = new Path();


                path.moveTo((float) (w / 2), 0.0f);
                path.lineTo(((float) (w / 2)) - fx, 0.0f);
//                path.lineTo(((float) (w / 2)) - (fx / 2.0f), (float) (h / 6));
//                path.lineTo(((float) (w / 2)) - (fx / 2.0f), (float) (h - (h / 6)));
                path.lineTo(((float) (w / 2)) - fx, (float) h);
                path.lineTo(((float) (w / 2)) + fx, (float) h);
//                path.lineTo(((float) (w / 2)) + (fx / 2.0f), (float) (h - (h / 6)));
//                path.lineTo(((float) (w / 2)) + (fx / 2.0f), (float) (h / 6));
                path.lineTo(((float) (w / 2)) + fx, 0.0f);
                path.lineTo(((float) (w / 2)) - fx, 0.0f);
                path.close();
                canvas.drawPath(path, paint);
                drawText(canvas);
                return mask;
            }
        },

        /*ms*/
        RL_DOOR("RL_DOOR") {
            public Bitmap getMask(int w, int h, int factor) {
                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setStrokeCap(Paint.Cap.BUTT);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                float fx = (float) ((w / KessiMaskBitmap3D.ANIMATED_FRAME_CAL) * factor);
                float fy = (float) ((h / KessiMaskBitmap3D.ANIMATED_FRAME_CAL) * factor);
                Canvas canvas = new Canvas(mask);
                Path path = new Path();


                //RL
                path.lineTo(((float) (w)) + fx, 0.0f);
                path.lineTo(((float) (w)) + fx, (float) h);
                path.lineTo(((float) (w)) - fx, (float) h);
                path.lineTo(((float) (w)) - fx, 0.0f);


                path.close();
                canvas.drawPath(path, paint);
                drawText(canvas);
                return mask;
            }
        },


        /*ms*/
        LR_DOOR("LR_DOOR") {
            public Bitmap getMask(int w, int h, int factor) {
                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setStrokeCap(Paint.Cap.BUTT);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                float fx = (float) ((w / KessiMaskBitmap3D.ANIMATED_FRAME_CAL) * factor);
                float fy = (float) ((h / KessiMaskBitmap3D.ANIMATED_FRAME_CAL) * factor);
                Canvas canvas = new Canvas(mask);
                Path path = new Path();


                //RL
                path.lineTo(((float) (w)) + fx, 0.0f);
                path.lineTo(((float) (w)) + fx, (float) h);
                path.lineTo(((float) (w)) - fx, (float) h);
                path.lineTo(((float) (w)) - fx, 0.0f);


                path.close();
                canvas.drawPath(path, paint);
                drawText(canvas);

                //flip hori
                Bitmap bOutput;
                Matrix matrix = new Matrix();
                matrix.preScale(-1.0f, 1.0f);
                bOutput = Bitmap.createBitmap(mask, 0, 0, mask.getWidth(), mask.getHeight(), matrix, true);

                return bOutput;
            }
        },

        /*ms*/
        WIPER_LEFT_CORNER("WIPER_LEFT_CORNER") {
            public Bitmap getMask(int w, int h, int factor) {


                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setStrokeCap(Paint.Cap.BUTT);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                float fx = (float) ((w / KessiMaskBitmap3D.ANIMATED_FRAME_CAL) * factor);
                float fy = (float) ((h / KessiMaskBitmap3D.ANIMATED_FRAME_CAL) * factor);
                Canvas canvas = new Canvas(mask);
                Path path = new Path();


                //wiper
                path.lineTo(0.0f, 0.0f);
                path.lineTo(((float) (w)) + fx, ((float) (w)) - fx);
                path.lineTo(((float) (w)) - fx, ((float) (w)) + fx);


                path.close();
                canvas.drawPath(path, paint);
                drawText(canvas);

                return mask;
            }
        },

        WIPER_RIGHT_CORNER("WIPER_RIGHT_CORNER") {
            public Bitmap getMask(int w, int h, int factor) {


                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setStrokeCap(Paint.Cap.BUTT);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                float fx = (float) ((w / KessiMaskBitmap3D.ANIMATED_FRAME_CAL) * factor);
                float fy = (float) ((h / KessiMaskBitmap3D.ANIMATED_FRAME_CAL) * factor);
                Canvas canvas = new Canvas(mask);
                Path path = new Path();


                //wiper
                path.lineTo(0.0f, 0.0f);
                path.lineTo(((float) (w)) + fx, ((float) (w)) - fx);
                path.lineTo(((float) (w)) - fx, ((float) (w)) + fx);


                path.close();
                canvas.drawPath(path, paint);
                drawText(canvas);


                //flip hori
                Bitmap bOutput;
                Matrix matrix = new Matrix();
                matrix.preScale(-1.0f, 1.0f);
                bOutput = Bitmap.createBitmap(mask, 0, 0, mask.getWidth(), mask.getHeight(), matrix, true);

                return bOutput;
            }
        },

        /*ms*/
        BT_DOOR("BT_DOOR") {
            public Bitmap getMask(int w, int h, int factor) {
                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setStrokeCap(Paint.Cap.BUTT);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                float fx = (float) ((w / KessiMaskBitmap3D.ANIMATED_FRAME_CAL) * factor);
                float fy = (float) ((h / KessiMaskBitmap3D.ANIMATED_FRAME_CAL) * factor);
                Canvas canvas = new Canvas(mask);
                Path path = new Path();


                //btm to top
                path.lineTo(0.0f, ((float) (w)) + fx);
                path.lineTo((float) w, ((float) (w)) + fx);
                path.lineTo((float) w, ((float) (w)) - fx);
                path.lineTo(0.0f, ((float) (w)) - fx);


                path.close();
                canvas.drawPath(path, paint);
                drawText(canvas);
                return mask;
            }
        },

        /*ms*/
        TB_DOOR("TB_DOOR") {
            public Bitmap getMask(int w, int h, int factor) {
                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setStrokeCap(Paint.Cap.BUTT);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                float fx = (float) ((w / KessiMaskBitmap3D.ANIMATED_FRAME_CAL) * factor);
                float fy = (float) ((h / KessiMaskBitmap3D.ANIMATED_FRAME_CAL) * factor);
                Canvas canvas = new Canvas(mask);
                Path path = new Path();


                //btm to top
                path.lineTo(0.0f, ((float) (w)) + fx);
                path.lineTo((float) w, ((float) (w)) + fx);
                path.lineTo((float) w, ((float) (w)) - fx);
                path.lineTo(0.0f, ((float) (w)) - fx);


                path.close();
                canvas.drawPath(path, paint);
                drawText(canvas);

                //flip ver
                Bitmap bOutput;
                Matrix matrix = new Matrix();
                matrix.preScale(1.0f, -1.0f);
                bOutput = Bitmap.createBitmap(mask, 0, 0, mask.getWidth(), mask.getHeight(), matrix, true);


                return bOutput;
            }
        },

        /*ms*/
        LOVE_DOOR("LOVE_DOOR") {
            public Bitmap getMask(int w, int h, int factor) {
                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setStrokeCap(Paint.Cap.BUTT);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                float fx = (float) ((w / KessiMaskBitmap3D.ANIMATED_FRAME_CAL) * factor);
                float fy = (float) ((h / KessiMaskBitmap3D.ANIMATED_FRAME_CAL) * factor);
                Canvas canvas = new Canvas(mask);
                Path path = new Path();


                path.moveTo((float) (w / 2), 0.0f);
                path.lineTo(((float) (w / 2)) - fx, 0.0f);
//                path.lineTo(((float) (w / 2)) - (fx / 2.0f), (float) (h / 6));
//                path.lineTo(((float) (w / 2)) - (fx / 2.0f), (float) (h - (h / 6)));
                path.lineTo(((float) (w / 2)) - fx, (float) h);
                path.lineTo(((float) (w / 2)) + fx, (float) h);
//                path.lineTo(((float) (w / 2)) + (fx / 2.0f), (float) (h - (h / 6)));
//                path.lineTo(((float) (w / 2)) + (fx / 2.0f), (float) (h / 6));
                path.lineTo(((float) (w / 2)) + fx, 0.0f);
                path.lineTo(((float) (w / 2)) - fx, 0.0f);


                path.moveTo(((float) w) / 2, ((float) h) / 5);
                path.cubicTo(5 * ((float) w) / 14, 0,
                        0, (((float) h) / 15),
                        ((float) w) / 28, (2 * ((float) h) / 5));
                path.cubicTo((((float) w) / 14), 2 * ((float) h) / 3,
                        (3 * ((float) w) / 7), 5 * ((float) h) / 6,
                        (((float) w) / 2), ((float) h));
                path.cubicTo((4 * ((float) w) / 7), 5 * ((float) h) / 6,
                        (13 * ((float) w) / 14), 2 * ((float) h) / 3,
                        (27 * ((float) w) / 28), 2 * ((float) h) / 5);
                path.cubicTo(((float) w), ((((float) h) / 15)),
                        9 * ((float) w) / 14, 0,
                        ((float) w) / 2, ((((float) h) / 5)));


                path.close();
                canvas.drawPath(path, paint);
                drawText(canvas);
                return mask;
            }
        },

        PIN_WHEEL("PIN_WHEEL") {
            public Bitmap getMask(int w, int h, int factor) {
                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                float rationX = (((float) w) / ((float) KessiMaskBitmap3D.ANIMATED_FRAME_CAL)) * ((float) factor);
                float rationY = (((float) h) / ((float) KessiMaskBitmap3D.ANIMATED_FRAME_CAL)) * ((float) factor);
                Path path = new Path();
                path.moveTo(((float) w) / 2.0f, ((float) h) / 2.0f);
                path.lineTo(0.0f, (float) h);
                path.lineTo(rationX, (float) h);
                path.close();
                path.moveTo(((float) w) / 2.0f, ((float) h) / 2.0f);
                path.lineTo((float) w, (float) h);
                path.lineTo((float) w, ((float) h) - rationY);
                path.close();
                path.moveTo(((float) w) / 2.0f, ((float) h) / 2.0f);
                path.lineTo((float) w, 0.0f);
                path.lineTo(((float) w) - rationX, 0.0f);
                path.close();
                path.moveTo(((float) w) / 2.0f, ((float) h) / 2.0f);
                path.lineTo(0.0f, 0.0f);
                path.lineTo(0.0f, rationY);
                path.close();
                canvas.drawPath(path, paint);
                return mask;
            }
        },

        /*ms*/
        RECT_RANDOM("RECT_RANDOM") {
            public Bitmap getMask(int w, int h, int factor) {
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                canvas.drawCircle(0.0f, (float) h, (((float) Math.sqrt((double) ((w * w) + (h * h)))) / ((float) KessiMaskBitmap3D.ANIMATED_FRAME_CAL)) * ((float) factor), paint);
                float wf = (float) (w / KessiMaskBitmap3D.ANIMATED_FRAME_CAL);
                float hf = (float) (h / KessiMaskBitmap3D.ANIMATED_FRAME_CAL);
                for (int i = 0; i < KessiMaskBitmap3D.randRect.length; i++) {
                    int rand = KessiMaskBitmap3D.random.nextInt(KessiMaskBitmap3D.randRect[i].length);
                    if (KessiMaskBitmap3D.randRect[i][rand] == 1) {
                        rand = KessiMaskBitmap3D.random.nextInt(KessiMaskBitmap3D.randRect[i].length);
                    }
                    KessiMaskBitmap3D.randRect[i][rand] = 1;
                    for (int j = 0; j < KessiMaskBitmap3D.randRect[i].length; j++) {
                        if (KessiMaskBitmap3D.randRect[i][j] == 1) {
                            canvas.drawRoundRect(new RectF(((float) i) * wf, ((float) j) * hf, (((float) i) + 1.0f) * wf, (((float) j) + 1.0f) * hf), 0.0f, 0.0f, paint);
                        }
                    }
                }
                drawText(canvas);
                return mask;
            }
        },
        /*ms*/
        BUBBLE("BUBBLE") {
            public Bitmap getMask(int w, int h, int factor) {
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                canvas.drawCircle(((float) w) / 2.0f, ((float) h) / 2.0f, (KessiMaskBitmap3D.getRad(w * 2, h * 2) / ((float) KessiMaskBitmap3D.ANIMATED_FRAME_CAL)) * ((float) factor), paint);
                float wf = (float) (w / KessiMaskBitmap3D.ANIMATED_FRAME_CAL);
                float hf = (float) (h / KessiMaskBitmap3D.ANIMATED_FRAME_CAL);
                for (int i = 0; i < KessiMaskBitmap3D.randRect.length; i++) {
                    int rand = KessiMaskBitmap3D.random.nextInt(KessiMaskBitmap3D.randRect[i].length);
                    if (KessiMaskBitmap3D.randRect[i][rand] == 1) {
                        rand = KessiMaskBitmap3D.random.nextInt(KessiMaskBitmap3D.randRect[i].length);
                    }
                    KessiMaskBitmap3D.randRect[i][rand] = 1;
                    for (int j = 0; j < KessiMaskBitmap3D.randRect[i].length; j++) {
                        if (KessiMaskBitmap3D.randRect[i][j] == 1) {
                            canvas.drawCircle(i * wf, j * wf, 30, paint);
                        }
                    }
                }
                drawText(canvas);
                return mask;
            }
        },
        SKEW_LEFT_MEARGE("SKEW_LEFT_MEARGE") {
            public Bitmap getMask(int w, int h, int factor) {
                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                float ratioX = (((float) w) / ((float) KessiMaskBitmap3D.ANIMATED_FRAME_CAL)) * ((float) factor);
                float ratioY = (((float) h) / ((float) KessiMaskBitmap3D.ANIMATED_FRAME_CAL)) * ((float) factor);
                Path path = new Path();
                path.moveTo(0.0f, ratioY);
                path.lineTo(ratioX, 0.0f);
                path.lineTo(0.0f, 0.0f);
                path.close();
                path.moveTo(((float) w) - ratioX, (float) h);
                path.lineTo((float) w, ((float) h) - ratioY);
                path.lineTo((float) w, (float) h);
                path.close();
                canvas.drawPath(path, paint);
                return mask;
            }
        },
        SKEW_LEFT_SPLIT("SKEW_LEFT_SPLIT") {
            public Bitmap getMask(int w, int h, int factor) {
                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                float ratioX = (((float) w) / ((float) KessiMaskBitmap3D.ANIMATED_FRAME_CAL)) * ((float) factor);
                float ratioY = (((float) h) / ((float) KessiMaskBitmap3D.ANIMATED_FRAME_CAL)) * ((float) factor);
                Path path = new Path();
                path.moveTo(0.0f, ratioY);
                path.lineTo(0.0f, 0.0f);
                path.lineTo(ratioX, 0.0f);
                path.lineTo((float) w, ((float) h) - ratioY);
                path.lineTo((float) w, (float) h);
                path.lineTo(((float) w) - ratioX, (float) h);
                path.lineTo(0.0f, ratioY);
                path.close();
                canvas.drawPath(path, paint);
                return mask;
            }
        },
        SKEW_RIGHT_SPLIT("SKEW_RIGHT_SPLIT") {
            public Bitmap getMask(int w, int h, int factor) {
                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                float ratioX = (((float) w) / ((float) KessiMaskBitmap3D.ANIMATED_FRAME_CAL)) * ((float) factor);
                float ratioY = (((float) h) / ((float) KessiMaskBitmap3D.ANIMATED_FRAME_CAL)) * ((float) factor);
                Path path = new Path();
                path.moveTo(((float) w) - ratioX, 0.0f);
                path.lineTo((float) w, 0.0f);
                path.lineTo((float) w, ratioY);
                path.lineTo(ratioX, (float) h);
                path.lineTo(0.0f, (float) h);
                path.lineTo(0.0f, ((float) h) - ratioY);
                path.lineTo(((float) w) - ratioX, 0.0f);
                path.close();
                canvas.drawPath(path, paint);
                return mask;
            }
        },
        SKEW_RIGHT_MEARGE("SKEW_RIGHT_MEARGE") {
            public Bitmap getMask(int w, int h, int factor) {
                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                float ratioX = (((float) w) / ((float) KessiMaskBitmap3D.ANIMATED_FRAME_CAL)) * ((float) factor);
                float ratioY = (((float) h) / ((float) KessiMaskBitmap3D.ANIMATED_FRAME_CAL)) * ((float) factor);
                Path path = new Path();
                path.moveTo(0.0f, ((float) h) - ratioY);
                path.lineTo(ratioX, (float) h);
                path.lineTo(0.0f, (float) h);
                path.close();
                path.moveTo(((float) w) - ratioX, 0.0f);
                path.lineTo((float) w, ratioY);
                path.lineTo((float) w, 0.0f);
                path.close();
                canvas.drawPath(path, paint);
                return mask;
            }
        },
        SQUARE_IN("SQUARE_IN") {
            public Bitmap getMask(int w, int h, int factor) {
                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                float ratioX = (((float) w) / (((float) KessiMaskBitmap3D.ANIMATED_FRAME_CAL) * 2.0f)) * ((float) factor);
                float ratioY = (((float) h) / (((float) KessiMaskBitmap3D.ANIMATED_FRAME_CAL) * 2.0f)) * ((float) factor);
                Path path = new Path();
                path.moveTo(0.0f, 0.0f);
                path.lineTo(0.0f, (float) h);
                path.lineTo(ratioX, (float) h);
                path.lineTo(ratioX, 0.0f);
                path.moveTo((float) w, (float) h);
                path.lineTo((float) w, 0.0f);
                path.lineTo(((float) w) - ratioX, 0.0f);
                path.lineTo(((float) w) - ratioX, (float) h);
                path.moveTo(ratioX, ratioY);
                path.lineTo(ratioX, 0.0f);
                path.lineTo(((float) w) - ratioX, 0.0f);
                path.lineTo(((float) w) - ratioX, ratioY);
                path.moveTo(ratioX, ((float) h) - ratioY);
                path.lineTo(ratioX, (float) h);
                path.lineTo(((float) w) - ratioX, (float) h);
                path.lineTo(((float) w) - ratioX, ((float) h) - ratioY);
                canvas.drawPath(path, paint);
                return mask;
            }
        },
        SQUARE_OUT("SQUARE_OUT") {
            public Bitmap getMask(int w, int h, int factor) {
                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                float ratioX = (((float) w) / (((float) KessiMaskBitmap3D.ANIMATED_FRAME_CAL) * 2.0f)) * ((float) factor);
                float ratioY = (((float) h) / (((float) KessiMaskBitmap3D.ANIMATED_FRAME_CAL) * 2.0f)) * ((float) factor);
                new Canvas(mask).drawRect(new RectF(((float) (w / 2)) - ratioX, ((float) (h / 2)) - ratioY, ((float) (w / 2)) + ratioX, ((float) (h / 2)) + ratioY), paint);
                return mask;
            }
        },

        /*ms*/
        SQUARE_R_OUT("SQUARE_R_OUT") {
            public Bitmap getMask(int w, int h, int factor) {
                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                float ratioX = ((((float) KessiMaskBitmap3D.ANIMATED_FRAME_CAL) * 3.0f)) * ((float) factor);
                float ratioY = ((((float) KessiMaskBitmap3D.ANIMATED_FRAME_CAL) * 3.0f)) * ((float) factor);

                new Canvas(mask).drawRect(new RectF(((float) (w)) - ratioX, ((float) (h)) - ratioY, ((float) (w)) + ratioX, ((float) (h)) + ratioY), paint);

                return mask;
            }
        },

        /*ms*/
        BAND("BAND") {
            public Bitmap getMask(int w, int h, int factor) {
                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                float ratioX = ((((float) KessiMaskBitmap3D.ANIMATED_FRAME_CAL) * 3.0f)) * ((float) factor);
                float ratioY = ((((float) KessiMaskBitmap3D.ANIMATED_FRAME_CAL) * 3.0f)) * ((float) factor);

                new Canvas(mask).drawRect(new RectF(((float) (w)) - ratioX, ((float) (h)) - ratioY, ((float) (w)) + ratioX, ((float) (h)) + ratioY), paint);
                new Canvas(mask).drawRect(new RectF(((float) (w / 2)) - ratioX, ((float) (h / 2)) - ratioY, ((float) (w / 2)) + ratioX, ((float) (h / 2)) + ratioY), paint);
                new Canvas(mask).drawRoundRect(new RectF(0.0f, 0.0f, (((float) Math.sqrt((double) ((w * w) + (h * h)))) / ((float) KessiMaskBitmap3D.ANIMATED_FRAME_CAL)) * ((float) factor), (((float) Math.sqrt((double) ((w * w) + (h * h)))) / ((float) KessiMaskBitmap3D.ANIMATED_FRAME_CAL)) * ((float) factor)), 0.0f, 0.0f, paint);
                return mask;
            }
        },
        /*ms*/
        VERTICAL_RECT("VERTICAL_RECT") {
            public Bitmap getMask(int w, int h, int factor) {
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                float hf = ((float) h) / 4.0f;
                float rectH = (((float) factor) * hf) / ((float) KessiMaskBitmap3D.ANIMATED_FRAME_CAL);
                for (int i = 0; i < 4; i++) {

                    //TB
                    canvas.drawRect(new Rect(0, (int) (((float) i) * hf), w, (int) ((((float) i) * hf) + rectH)), paint);

                }
                drawText(canvas);
                return mask;
            }
        },
        /*ms*/
        HORI_RECT("HORI_RECT") {
            public Bitmap getMask(int w, int h, int factor) {
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                float hf = ((float) h) / 4.0f;
                float rectH = (((float) factor) * hf) / ((float) KessiMaskBitmap3D.ANIMATED_FRAME_CAL);
                for (int i = 0; i < 4; i++) {

                    //LR
                    canvas.drawRect(new Rect((int) (((float) i) * hf), 0, (int) ((((float) i) * hf) + rectH), w), paint);
//                    canvas.drawRect(new Rect((int) ((((float) i) * hf) - rectH),0 , (int) (((float) i) * hf),w ), paint);
                }
                drawText(canvas);
                return mask;
            }
        },
        /*ms*/
        TOURNADO("TOURNADO") {
            public Bitmap getMask(int w, int h, int factor) {
                float r = KessiMaskBitmap3D.getRad(w, h);
                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                RectF oval = new RectF();

                oval.set((((float) w) / 2.0f) - r, (((float) h) / 2.0f) - r, (((float) w) / 2.0f) + r, (((float) h) / 2.0f) + r);
                float angle = (((float) KessiMaskBitmap3D.ANIMATED_FRAME_CAL)) * ((float) factor);
                canvas.drawArc(oval, 360.0f, angle, true, paint);
                drawText(canvas);

                return mask;
            }
        },
        /*ms*/
        TOURNADO_REV("TOURNADO_REV") {
            public Bitmap getMask(int w, int h, int factor) {
                float r = KessiMaskBitmap3D.getRad(w, h);
                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                RectF oval = new RectF();

                oval.set((((float) w) / 2.0f) - r, (((float) h) / 2.0f) - r, (((float) w) / 2.0f) + r, (((float) h) / 2.0f) + r);
                float angle = (((float) KessiMaskBitmap3D.ANIMATED_FRAME_CAL)) * ((float) factor);
                canvas.drawArc(oval, 360.0f, angle, true, paint);
                drawText(canvas);

                //flip ver
                Bitmap bOutput;
                Matrix matrix = new Matrix();
                matrix.preScale(1.0f, -1.0f);
                bOutput = Bitmap.createBitmap(mask, 0, 0, mask.getWidth(), mask.getHeight(), matrix, true);

                return bOutput;
            }
        },
        /*ms*/
        RUB_RL("RUB_RL") {
            public Bitmap getMask(int w, int h, int factor) {
                float r = KessiMaskBitmap3D.getRad(w, h);
                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);

                RectF oval = new RectF();
                oval.set((((float) w) / 2.0f) - r, (((float) h) / 2.0f) - r, (((float) w) / 2.0f) + r, (((float) h) / 2.0f) + r);
                float angle = (((float) KessiMaskBitmap3D.ANIMATED_FRAME_CAL)) * ((float) factor);

                canvas.drawArc(oval, 270.0f, angle, true, paint);

                return mask;
            }
        },
        /*ms*/
        RUB_LR("RUB_LR") {
            public Bitmap getMask(int w, int h, int factor) {
                float r = KessiMaskBitmap3D.getRad(w, h);
                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);

                RectF oval = new RectF();
                oval.set((((float) w) / 2.0f) - r, (((float) h) / 2.0f) - r, (((float) w) / 2.0f) + r, (((float) h) / 2.0f) + r);
                float angle = (((float) KessiMaskBitmap3D.ANIMATED_FRAME_CAL)) * ((float) factor);

                canvas.drawArc(oval, 270.0f, angle, true, paint);

                //flip hori
                Bitmap bOutput;
                Matrix matrix = new Matrix();
                matrix.preScale(-1.0f, 1.0f);
                bOutput = Bitmap.createBitmap(mask, 0, 0, mask.getWidth(), mask.getHeight(), matrix, true);


                return bOutput;
            }
        },
        /*ms*/
        WIND_MILL("WIND_MILL") {
            public Bitmap getMask(int w, int h, int factor) {
                float r = KessiMaskBitmap3D.getRad(w, h);
                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                RectF oval = new RectF();
                oval.set((((float) w) / 2.0f) - r, (((float) h) / 2.0f) - r, (((float) w) / 2.0f) + r, (((float) h) / 2.0f) + r);


                float angle = (((float) KessiMaskBitmap3D.ANIMATED_FRAME_CAL)) * ((float) factor);
                canvas.drawArc(oval, 180.0f, angle, true, paint);
                canvas.drawArc(oval, 360.0f, angle, true, paint);


                return mask;
            }
        };;


        String name;

        public Bitmap getMask(int i, int i2, int i3) {
            return null;
        }

        public Bitmap getMask(Bitmap bitmap, Bitmap bitmap2, int i) {
            return null;
        }

        public void initBitmaps(Bitmap bitmap, Bitmap bitmap2) {

        }

        private EFFECT(String name) {
            this.name = "";
            this.name = name;
        }

        public void drawText(Canvas canvas) {
            Paint paint = new Paint();
            paint.setTextSize(50.0f);
            paint.setColor(SupportMenu.CATEGORY_MASK);
        }
    }

    static {
        ANIMATED_FRAME = 0.0f;
        ANIMATED_FRAME_CAL = 0;
        ORIGANAL_FRAME = 0;
        TOTAL_FRAME = 0;
        TOTAL_FRAME = 30;
        ORIGANAL_FRAME = 8;
        ANIMATED_FRAME = 22.0f;
        ANIMATED_FRAME_CAL = (int) (ANIMATED_FRAME - 1.0f);
        paint.setColor(-16777216);
        paint.setAntiAlias(true);
        paint.setStyle(Style.FILL_AND_STROKE);
    }

    public static void reintRect() {
        randRect = (int[][]) Array.newInstance(Integer.TYPE, new int[]{(int) ANIMATED_FRAME, (int) ANIMATED_FRAME});
        for (int i = 0; i < randRect.length; i++) {
            for (int j = 0; j < randRect[i].length; j++) {
                randRect[i][j] = 0;
            }
        }
    }

    static float getRad(int w, int h) {
        return (float) Math.sqrt((double) (((w * w) + (h * h)) / 4));
    }

    private static float scaleX = 1.0f;
    private static float scaleY = 1.0f;


    public static ArrayList<EFFECT> random(int position){
        ArrayList<EFFECT> list =  new ArrayList(Arrays.asList(EFFECT.values()));
        int start = 0, end = list.size();
        ArrayList<EFFECT> temp = new ArrayList<>();
        switch (position){
            case 1:
                end = ((int)list.size())/2;
//                for(int i = start; i < end; ++i){
//                    if(list.get(i).name.contains("DOOR"))
//                        temp.add(list.get(i));
//                }
                break;
            case 2:
                start = ((int)list.size())/2 + 1;
//                for(int i = start; i < end; ++i){
//                    if(list.get(i).name.contains("OUT"))
//                        temp.add(list.get(i));
//                }
                break;
            default:
//                temp.addAll(list);
                break;
        }
        for(int i = start; i < end; ++i){
            temp.add(list.get(i));
        }
        Collections.shuffle(temp);
        return temp;
    }

}
