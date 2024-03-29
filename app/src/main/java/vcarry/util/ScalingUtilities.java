package vcarry.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.BlurMaskFilter;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

import com.naosteam.slideshowmaker.KessiApplication;

import java.io.IOException;

public class ScalingUtilities {

    public enum ScalingLogic {
        CROP,
        FIT
    }

    public static Bitmap decodeResource(Resources res, int resId, int dstWidth, int dstHeight, ScalingLogic scalingLogic) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        options.inJustDecodeBounds = false;
        options.inSampleSize = calculateSampleSize(options.outWidth, options.outHeight, dstWidth, dstHeight, scalingLogic);
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static Bitmap createScaledBitmap(Bitmap unscaledBitmap, int dstWidth, int dstHeight, ScalingLogic scalingLogic) {
        Rect srcRect = calculateSrcRect(unscaledBitmap.getWidth(), unscaledBitmap.getHeight(), dstWidth, dstHeight, scalingLogic);
        Rect dstRect = calculateDstRect(unscaledBitmap.getWidth(), unscaledBitmap.getHeight(), dstWidth, dstHeight, scalingLogic);
        Bitmap scaledBitmap = Bitmap.createBitmap(dstRect.width(), dstRect.height(), Config.ARGB_8888);
        new Canvas(scaledBitmap).drawBitmap(unscaledBitmap, srcRect, dstRect, new Paint(2));
        return scaledBitmap;
    }

    public static int calculateSampleSize(int srcWidth, int srcHeight, int dstWidth, int dstHeight, ScalingLogic scalingLogic) {
        if (scalingLogic == ScalingLogic.FIT) {
            if (((float) srcWidth) / ((float) srcHeight) > ((float) dstWidth) / ((float) dstHeight)) {
                return srcWidth / dstWidth;
            }
            return srcHeight / dstHeight;
        } else if (((float) srcWidth) / ((float) srcHeight) > ((float) dstWidth) / ((float) dstHeight)) {
            return srcHeight / dstHeight;
        } else {
            return srcWidth / dstWidth;
        }
    }

    public static Rect calculateSrcRect(int srcWidth, int srcHeight, int dstWidth, int dstHeight, ScalingLogic scalingLogic) {
        if (scalingLogic != ScalingLogic.CROP) {
            return new Rect(0, 0, srcWidth, srcHeight);
        }
        float dstAspect = ((float) dstWidth) / ((float) dstHeight);
        if (((float) srcWidth) / ((float) srcHeight) > dstAspect) {
            int srcRectWidth = (int) (((float) srcHeight) * dstAspect);
            int srcRectLeft = (srcWidth - srcRectWidth) / 2;
            return new Rect(srcRectLeft, 0, srcRectLeft + srcRectWidth, srcHeight);
        }
        int srcRectHeight = (int) (((float) srcWidth) / dstAspect);
        int scrRectTop = (srcHeight - srcRectHeight) / 2;
        return new Rect(0, scrRectTop, srcWidth, scrRectTop + srcRectHeight);
    }

    public static Rect calculateDstRect(int srcWidth, int srcHeight, int dstWidth, int dstHeight, ScalingLogic scalingLogic) {
        if (scalingLogic != ScalingLogic.FIT) {
            return new Rect(0, 0, dstWidth, dstHeight);
        }
        float srcAspect = ((float) srcWidth) / ((float) srcHeight);
        if (srcAspect > ((float) dstWidth) / ((float) dstHeight)) {
            return new Rect(0, 0, dstWidth, (int) (((float) dstWidth) / srcAspect));
        }
        return new Rect(0, 0, (int) (((float) dstHeight) * srcAspect), dstHeight);
    }

    public static Bitmap ConvetrSameSize(Bitmap originalImage, int mDisplayWidth, int mDisplayHeight) {
        Bitmap bitmap = originalImage;
        Bitmap cs = Bitmap.createBitmap(mDisplayWidth, mDisplayHeight, Config.ARGB_8888);
        Canvas comboImage = new Canvas(cs);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, mDisplayWidth, mDisplayHeight);
        paint.setAntiAlias(true);
        comboImage.drawARGB(0, 0, 0, 0);
        paint.setColor(-12434878);
        comboImage.drawRect(rect, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        comboImage.drawBitmap(newscaleBitmap(bitmap, mDisplayWidth, mDisplayHeight), 0.0f, 0.0f, paint);
        return cs;
    }

    public static Bitmap Paint_Image(Bitmap bitmap, Bitmap bgBitmap, int left_per, int top_per, int height_per, int width_ratio, int height_ratio){// ratio width/height

        Bitmap cs = bgBitmap.copy(bgBitmap.getConfig(), true);
        int height = bgBitmap.getHeight()*height_per/1000;
        Canvas canvas = new Canvas(cs);
        //bgBitmap.getHeight()*left_per/1000
        float left = (float) Math.round(bgBitmap.getHeight()*left_per)/1000;
        //bgBitmap.getHeight()*top_per/1000
        float top = (float) Math.round(bgBitmap.getHeight()*top_per)/1000;

        bitmap = scaleCenterCrop(bitmap, height * width_ratio/height_ratio, height);
        canvas.drawBitmap(Bitmap.createScaledBitmap(bitmap, height * width_ratio/height_ratio, height, true),
                left , top, new Paint());

        return cs;
    }

    public static Bitmap ConvetrSameSizeTransBg(Bitmap originalImage, int mDisplayWidth, int mDisplayHeight) {
        Bitmap bitmap = originalImage;
        Bitmap cs = Bitmap.createBitmap(mDisplayWidth, mDisplayHeight, Config.ARGB_8888);
        Canvas comboImage = new Canvas(cs);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, mDisplayWidth, mDisplayHeight);
        comboImage.drawBitmap(newscaleBitmap(bitmap, mDisplayWidth, mDisplayHeight), 0.0f, 0.0f, paint);
        return cs;
    }

    public static Bitmap ConvetrSameSize(Bitmap originalImage, Bitmap bgBitmap, int mDisplayWidth, int mDisplayHeight) {
        Bitmap bitmap = originalImage;
        Bitmap cs = bgBitmap;
        Canvas comboImage = new Canvas(cs);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, mDisplayWidth, mDisplayHeight);
        comboImage.drawBitmap(newscaleBitmap(bitmap, mDisplayWidth, mDisplayHeight), 0.0f, 0.0f, paint);
        return cs;
    }

    public static Bitmap ConvetrSameSize(Bitmap originalImage, Bitmap bgBitmap, int mDisplayWidth, int mDisplayHeight, float x, float y) {
        Bitmap bitmap = originalImage;
        //Bitmap cs = bgBitmap.copy(bgBitmap.getConfig(), true);
        Bitmap cs = Bitmap.createBitmap(KessiApplication.VIDEO_WIDTH, KessiApplication.VIDEO_HEIGHT, Config.RGB_565);
//        new Canvas(FastBlur.doBlur(cs, 25, true)).drawBitmap(newscaleBitmap(bitmap, mDisplayWidth, mDisplayHeight, x, y), 0.0f, 0.0f, new Paint());
       // cs = FastBlur.doBlur(cs,  1, true);
        new Canvas(cs).drawBitmap(newscaleBitmap(bitmap, mDisplayWidth, mDisplayHeight, x, y), 0.0f, 0.0f, new Paint());
        return cs;
    }

    public static Bitmap ConvetrSameSizeNew(Bitmap originalImage, Bitmap bgBitmap, int mDisplayWidth, int mDisplayHeight) {
        Bitmap cs = FastBlur.doBlur(bgBitmap, 25, true);
        Canvas comboImage = new Canvas(cs);
        Paint paint = new Paint();
        float originalWidth = (float) originalImage.getWidth();
        float originalHeight = (float) originalImage.getHeight();
        float scale = ((float) mDisplayWidth) / originalWidth;
        float scaleY = ((float) mDisplayHeight) / originalHeight;
        float xTranslation = 0.0f;
        float yTranslation = (((float) mDisplayHeight) - (originalHeight * scale)) / 2.0f;
        if (yTranslation < 0.0f) {
            yTranslation = 0.0f;
            scale = ((float) mDisplayHeight) / originalHeight;
            xTranslation = (((float) mDisplayWidth) - (originalWidth * scaleY)) / 2.0f;
        }
        Matrix transformation = new Matrix();
        transformation.postTranslate(xTranslation, yTranslation);
        transformation.preScale(scale, scale);
        comboImage.drawBitmap(originalImage, transformation, paint);
        return cs;
    }

    public static Bitmap scaleCenterCrop(Bitmap source, int newWidth, int newHeight) {
        int sourceWidth = source.getWidth();
        int sourceHeight = source.getHeight();
        if (sourceWidth == newWidth && sourceHeight == newHeight) {
            return source;
        }
        float scale = Math.max(((float) newWidth) / ((float) sourceWidth), ((float) newHeight) / ((float) sourceHeight));
        float scaledWidth = scale * ((float) sourceWidth);
        float scaledHeight = scale * ((float) sourceHeight);
        float left = (((float) newWidth) - scaledWidth) / 2.0f;
        float top = (((float) newHeight) - scaledHeight) / 2.0f;
        RectF targetRect = new RectF(left, top, left + scaledWidth, top + scaledHeight);
        Bitmap dest = Bitmap.createBitmap(newWidth, newHeight, source.getConfig());
        new Canvas(dest).drawBitmap(source, null, targetRect, null);
        return dest;
    }

    private static Bitmap newscaleBitmap(Bitmap originalImage, int width, int height, float x, float y) {
        Bitmap background = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        float originalWidth = (float) originalImage.getWidth();
        float originalHeight = (float) originalImage.getHeight();
        Canvas canvas = new Canvas(background);
        float scale = ((float) width) / originalWidth;
        float scaleY = ((float) height) / originalHeight;
        float xTranslation = 0.0f;
        float yTranslation = (((float) height) - (originalHeight * scale)) / 2.0f;
        if (yTranslation < 0.0f) {
            yTranslation = 0.0f;
            scale = ((float) height) / originalHeight;
            xTranslation = (((float) width) - (originalWidth * scaleY)) / 2.0f;
        }
        Matrix transformation = new Matrix();
        transformation.postTranslate(xTranslation * x, yTranslation + y);
//        Log.d("translation", "xTranslation :" + xTranslation + " yTranslation :" + yTranslation);
        transformation.preScale(scale, scale);
        canvas.drawBitmap(originalImage, transformation, new Paint());
        return background;
    }

    public static Bitmap addShadow(Bitmap bm, int color, int size, float dx, float dy) {
        Bitmap mask = Bitmap.createBitmap(bm.getWidth() + (size * 2), bm.getHeight(), Config.ALPHA_8);
        Matrix scaleToFit = new Matrix();
        scaleToFit.setRectToRect(new RectF(0.0f, 0.0f, (float) bm.getWidth(), (float) bm.getHeight()), new RectF(0.0f, 0.0f, (((float) bm.getWidth()) - dx) - ((float) size), ((float) bm.getHeight()) - dy), ScaleToFit.CENTER);
        Matrix dropShadow = new Matrix(scaleToFit);
        dropShadow.postTranslate(dx, dy);
        Canvas maskCanvas = new Canvas(mask);
        Paint paint = new Paint(1);
        maskCanvas.drawBitmap(bm, scaleToFit, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_OUT));
        maskCanvas.drawBitmap(bm, dropShadow, paint);
        BlurMaskFilter filter = new BlurMaskFilter((float) size, Blur.NORMAL);
        paint.reset();
        paint.setAntiAlias(true);
        paint.setColor(color);
        paint.setMaskFilter(filter);
        paint.setFilterBitmap(true);
        Bitmap ret = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), Config.ARGB_8888);
        Canvas retCanvas = new Canvas(ret);
        retCanvas.drawBitmap(mask, 0.0f, 0.0f, paint);
        retCanvas.drawBitmap(bm, scaleToFit, null);
        mask.recycle();
        return ret;
    }

    @SuppressLint({"NewApi"})
    public static Bitmap blurBitmap(Bitmap bitmap, Context context) {
        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        RenderScript rs = RenderScript.create(context);
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
        Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);
        blurScript.setRadius(25.0f);
        blurScript.setInput(allIn);
        blurScript.forEach(allOut);
        allOut.copyTo(outBitmap);
        bitmap.recycle();
        rs.destroy();
        return outBitmap;
    }

    public static Bitmap overlay(Bitmap bitmap1, Bitmap bitmapOverlay, int opacity) {
        Bitmap resultBitmap = Bitmap.createBitmap(bitmap1.getWidth(), bitmap1.getHeight(), Config.ARGB_8888);
        Canvas c = new Canvas(resultBitmap);
        c.drawBitmap(bitmap1, 0.0f, 0.0f, null);
        Paint p = new Paint();
        p.setAlpha(opacity);
        c.drawBitmap(bitmapOverlay, 0.0f, 0.0f, p);
        return resultBitmap;
    }

    private static Bitmap newscaleBitmap(Bitmap originalImage, int width, int height) {
        Bitmap background = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        float originalWidth = (float) originalImage.getWidth();
        float originalHeight = (float) originalImage.getHeight();
        Canvas canvas = new Canvas(background);
        float scale = ((float) width) / originalWidth;
        float xTranslation = 0.0f;
        float yTranslation = (((float) height) - (originalHeight * scale)) / 2.0f;
        if (yTranslation < 0.0f) {
            yTranslation = 0.0f;
            scale = ((float) height) / originalHeight;
            xTranslation = (((float) width) - (originalWidth * scale)) / 2.0f;
        }
        Matrix transformation = new Matrix();
        transformation.postTranslate(xTranslation, yTranslation);
//        Log.d("translation", "xTranslation :" + xTranslation + " yTranslation :" + yTranslation);
        transformation.preScale(scale, scale);
        Paint paint = new Paint();
        paint.setFilterBitmap(true);
        canvas.drawBitmap(originalImage, transformation, paint);
        return background;
    }

//    public static Bitmap checkBitmap(String path,int sw,int sh) {
//        int orientation = 1;
////        Options bounds = new Options();
////        bounds.inJustDecodeBounds = true;
//
//        Options options = new Options();
//        Bitmap bm;
//        Bitmap b2;
//        int rotateImage=0;
//        try {
//
//            System.gc();
//            options.inSampleSize = 1;
//            rotateImage = getCameraPhotoOrientation(path);
//            // b = rotate(b, rotateImage);
//            //  PI_Cons.bitmapimg1.add(rotate(decodeSampledBitmapFromResource(videoPathList.get(i),ScreenWidth,ScreenHeight),rotateImage));
//
//            bm = rotate(decodeSampledBitmapFromResource(path,
//                    sw, sh),rotateImage);
//
//        } catch (Exception e) {
//            options.inSampleSize = 2;
//            bm = rotate(BitmapFactory.decodeFile(path, options),rotateImage);
//        }
//        try {
//            String orientString = new ExifInterface(path).getAttribute("Orientation");
//            if (orientString != null) {
//                orientation = Integer.parseInt(orientString);
//            }
//            int rotationAngle = 0;
//            if (orientation == 6) {
//                rotationAngle = 90;
//            }
//            if (orientation == 3) {
//                rotationAngle = 180;
//            }
//            if (orientation == 8) {
//                rotationAngle = 270;
//            }
//            Matrix matrix = new Matrix();
//            matrix.setRotate((float) rotationAngle, ((float) bm.getWidth()) / 2.0f, ((float) bm.getHeight()) / 2.0f);
//            b2=Bitmap.createBitmap(bm);
//            return b2;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public static Bitmap decodeSampledBitmapFromResource(String path,
//                                                         int reqWidth, int reqHeight) {
//
//        // First decode with inJustDecodeBounds=true to check dimensions
//        final BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeFile(path, options);
//
//        // Calculate inSampleSize
//        options.inSampleSize = calculateInSampleSize(options, reqWidth,
//                reqHeight);
//
//        // Decode bitmap with inSampleSize set
//        options.inJustDecodeBounds = false;
//        return BitmapFactory.decodeFile(path, options);
//    }
//
//    public static int calculateInSampleSize(BitmapFactory.Options options,
//                                            int reqWidth, int reqHeight) {
//// Raw height and width of image
//        final int height = options.outHeight;
//        final int width = options.outWidth;
//        int inSampleSize = 1;
//
//        if (height > reqHeight || width > reqWidth) {
//
//// Calculate ratios of height and width to requested height and
//// width
//            final int heightRatio = Math.round((float) height
//                    / (float) reqHeight);
//            final int widthRatio = Math.round((float) width / (float) reqWidth);
//
//// Choose the smallest ratio as inSampleSize value, this will
//// guarantee
//// a final image with both dimensions larger than or equal to the
//// requested height and width.
//            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
//        }
//
//        return inSampleSize;
//    }
//
//    public static Bitmap rotate(Bitmap bitmap, int degree) {
//        int w = bitmap.getWidth();
//        int h = bitmap.getHeight();
//
//        Matrix mtx = new Matrix();
//        //       mtx.postRotate(degree);
//        mtx.setRotate(degree);
//
//        return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
//    }
//
//    public static int getCameraPhotoOrientation(String imagePath) {
//        int rotate = 0;
//        try {
////            context.getContentResolver().notifyChange(imageUri, null);
//            File imageFile = new File(imagePath);
//
//            ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
//            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
//
//            switch (orientation) {
//                case ExifInterface.ORIENTATION_ROTATE_270:
//                    rotate = 270;
//                    break;
//                case ExifInterface.ORIENTATION_ROTATE_180:
//                    rotate = 180;
//                    break;
//                case ExifInterface.ORIENTATION_ROTATE_90:
//                    rotate = 90;
//                    break;
//            }
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return rotate;
//    }

    public static Bitmap checkBitmap(String path, Context context) {
        int orientation = 1;

        Bitmap bm;
        BitmapFactory.Options options;

        try {
            options = new BitmapFactory.Options();
            options.inSampleSize = 1;
            BitmapFactory.decodeFile(path, options);
            bm = BitmapFactory.decodeFile(path, options);
        }catch (Exception e){
            options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            BitmapFactory.decodeFile(path, options);
            bm = BitmapFactory.decodeFile(path, options);
        }


        try {
            String orientString = new ExifInterface(path).getAttribute("Orientation");
            if (orientString != null) {
                orientation = Integer.parseInt(orientString);
            }
            int rotationAngle = 0;
            if (orientation == 6) {
                rotationAngle = 90;
            }
            if (orientation == 3) {
                rotationAngle = 180;
            }
            if (orientation == 8) {
                rotationAngle = 270;
            }
            Matrix matrix = new Matrix();
            matrix.setRotate((float) rotationAngle, ((float) bm.getWidth()) / 2.0f, ((float) bm.getHeight()) / 2.0f);
            bm = resizeImageToNewSize(bm,context.getResources().getDisplayMetrics().widthPixels, context.getResources().getDisplayMetrics().heightPixels - ((int) (context.getResources().getDisplayMetrics().density * 50.0f)));
            return rotate(bm, rotationAngle);
            //return Bitmap.createBitmap(bm, 0, 0, options.outWidth, options.outHeight, matrix, true);
        } catch (IOException em) {
            em.printStackTrace();
            return null;
        }


    }

    public static Bitmap rotate(Bitmap bitmap, int degree) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        Matrix mtx = new Matrix();
        //       mtx.postRotate(degree);
        mtx.setRotate(degree);

        return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
    }

    public static Bitmap resizeImageToNewSize(Bitmap bitmap, int i, int i2) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float f = (float) i;
        float f2 = (float) i2;
        if (!(height == i2 && width == i)) {
            f2 = ((float) i) / ((float) width);
            f = ((float) i2) / ((float) height);
            if (f2 >= f) {
                f2 = f;
            }
            f = ((float) width) * f2;
            f2 *= (float) height;
        }
        return Bitmap.createScaledBitmap(bitmap, (int) f, (int) f2, true);
    }
}
