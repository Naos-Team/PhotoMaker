package com.xinlan.imageeditlibrary.editimage.view;


import java.util.LinkedHashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.RequiresApi;

import com.xinlan.imageeditlibrary.editimage.fragment.TextImageFragment;
import com.xinlan.imageeditlibrary.editimage.utils.RectUtil;


public class StickerView extends View {
    private static int STATUS_IDLE = 0;
    private static int STATUS_MOVE = 1;//
    private static int STATUS_DELETE = 2;//
    private static int STATUS_ROTATE = 3;//

    private TextImageFragment textImageFragment;
    private int imageCount;//
    private Context mContext;
    private int currentStatus;//
    private StickerItem currentItem;//
    private float oldx, oldy;
    private int ID_Selected = -1;
    private Paint rectPaint = new Paint();
    private Paint boxPaint = new Paint();

    private LinkedHashMap<Integer, StickerItem> bank = new LinkedHashMap<Integer, StickerItem>();//

    private Point mPoint = new Point(0 , 0);

    public TextImageFragment getTextImageFragment() {
        return textImageFragment;
    }

    public void setTextImageFragment(TextImageFragment textImageFragment) {
        this.textImageFragment = textImageFragment;
    }

    public StickerView(Context context) {
        super(context);
        init(context);
    }

    public StickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public StickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        currentStatus = STATUS_IDLE;

        rectPaint.setColor(Color.RED);
        rectPaint.setAlpha(100);

    }

    public void addBitImage(final Bitmap addBit) {
        StickerItem item = new StickerItem(this.getContext());
        item.init(addBit, this);
        if (currentItem != null) {
            currentItem.isDrawHelpTool = false;
        }
        bank.put(++imageCount, item);
        this.invalidate();//
    }

    public void addBitImageWithAccuracy(final Bitmap addBit, int accuracy, Bitmap bitmap_root) {
        StickerItem item = new StickerItem(this.getContext());
        item.init(addBit, this);
        item.setImg_Opacity_root(addBit);
        item.setAccuracy(accuracy);
        item.setImg_Root(bitmap_root);
        if (currentItem != null) {
            currentItem.isDrawHelpTool = false;
        }
        bank.put(++imageCount, item);
        this.invalidate();//
    }

    public void updateItem(final Bitmap editBit, int accuracy){
        if (ID_Selected != -1) {
            StickerItem item = bank.get(ID_Selected);
            item.setBitmap(editBit);
            item.setImg_Opacity_root(editBit);
            item.setAccuracy(accuracy);
            this.invalidate();
        }
    }

    public void updateOpacityItem(final Bitmap editBit, int opacity){
        if (ID_Selected != -1) {
            StickerItem item = bank.get(ID_Selected);
            item.setBitmap(editBit);
            item.setOpacity(opacity);
            this.invalidate();
        }
    }

    public StickerItem getCurrentItem(){
        if (ID_Selected != -1) {
            return bank.get(ID_Selected);
        } else
            return null;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // System.out.println("on draw!!~");
        for (Integer id : bank.keySet()) {
            StickerItem item = bank.get(id);
            item.draw(canvas);
        }// end for each
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // System.out.println(w + "   " + h + "    " + oldw + "   " + oldh);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean ret = super.onTouchEvent(event);//

        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:

                int deleteId = -1;
                for (Integer id : bank.keySet()) {
                    StickerItem item = bank.get(id);
                    if (item.detectDeleteRect.contains(x, y)) {//
                        // ret = true;
                        deleteId = id;
                        currentStatus = STATUS_DELETE;
                    } else if (item.detectRotateRect.contains(x, y)) {//
                        ret = true;
                        if (currentItem != null) {
                            currentItem.isDrawHelpTool = false;
                        }
                        currentItem = item;
                        currentItem.isDrawHelpTool = true;
                        currentStatus = STATUS_ROTATE;
                        oldx = x;
                        oldy = y;
                    } else if (detectInItemContent(item , x , y)) {//
                        // 被选中一张贴图
                        ret = true;
                        ID_Selected = id;
                        if(textImageFragment != null){
                            textImageFragment.openOpacity();
                        }
                        if (currentItem != null) {
                            currentItem.isDrawHelpTool = false;
                        }
                        currentItem = item;
                        currentItem.isDrawHelpTool = true;
                        currentStatus = STATUS_MOVE;
                        oldx = x;
                        oldy = y;
                    }// end if
                }// end for each

                if (!ret && currentItem != null && currentStatus == STATUS_IDLE) {//
                    currentItem.isDrawHelpTool = false;
                    currentItem = null;
                    ID_Selected = -1;
                    textImageFragment.closeOpacity();
                    invalidate();
                }

                if (deleteId > 0 && currentStatus == STATUS_DELETE) {//
                    bank.remove(deleteId);
                    currentStatus = STATUS_IDLE;//
                    invalidate();
                }// end if

                break;
            case MotionEvent.ACTION_MOVE:
                ret = true;
                if (currentStatus == STATUS_MOVE) {//
                    float dx = x - oldx;
                    float dy = y - oldy;
                    if (currentItem != null) {
                        currentItem.updatePos(dx, dy);
                        invalidate();
                    }// end if
                    oldx = x;
                    oldy = y;
                } else if (currentStatus == STATUS_ROTATE) {//

                    float dx = x - oldx;
                    float dy = y - oldy;
                    if (currentItem != null) {
                        currentItem.updateRotateAndScale(oldx, oldy, dx, dy);//
                        invalidate();
                    }// end if
                    oldx = x;
                    oldy = y;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                ret = false;
                currentStatus = STATUS_IDLE;
                break;
        }// end switch
        return ret;
    }


    private boolean detectInItemContent(StickerItem item , float x , float y){
        //reset
        mPoint.set((int)x , (int)y);

        RectUtil.rotatePoint(mPoint , item.helpBox.centerX() , item.helpBox.centerY() , -item.roatetAngle);
        return item.helpBox.contains(mPoint.x, mPoint.y);
    }

    public LinkedHashMap<Integer, StickerItem> getBank() {
        return bank;
    }

    public void clear() {
        bank.clear();
        this.invalidate();
    }
}// end class
