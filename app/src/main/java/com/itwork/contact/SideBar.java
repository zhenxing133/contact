package com.itwork.contact;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by yuan.zhen.xing on 2018-07-16.
 */

public class SideBar extends View {
    //字母列表
    private String[] letters = {"#", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "S", "Y", "Z"};
    private Paint mPaint;
    //字母宽高
    private int letterHeight;
    private int letterWidth;
    //view宽高
    private int width;
    private int height;
    //
    private int cellHeight;
    //选中的字母
    private int choose = -1;
    //定义回调接口，用于显示选中字母
    private OnItemSelectListener onItemSelectListener;

    public void setOnItemSelectListener(OnItemSelectListener onItemSelectListener) {

        this.onItemSelectListener = onItemSelectListener;
    }

    public interface OnItemSelectListener {

        void setText(String content);

        void setVisibility();
    }

    //构造方法
    public SideBar(Context context) {
        this(context, null);
    }

    public SideBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#FF0000"));
        mPaint.setTextSize(dip2px(context,12));
        //抗锯齿
        mPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        cellHeight = height / letters.length;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < letters.length; i++) {
//            Rect rect = new Rect();
//            mPaint.getTextBounds(letters[i], 0, 1, rect);
//            letterWidth = width / 2 - rect.width() / 2;
//            Log.e("yzx", "letterWidth=" + letterWidth);
//            letterHeight = (int) (cellHeight / 2 + rect.height() / 2 + i * cellHeight);
            float x = width / 2 ;
            float y = cellHeight + cellHeight * i ;
            if (choose == i) {
                mPaint.setColor(Color.RED);
            } else {
                mPaint.setColor(Color.BLUE);
            }
            canvas.drawText(letters[i],x,y, mPaint);
        }
    }

    //处理触摸事件了

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int downY;
        int moveY;
        int current;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = (int) event.getY();
                current = (int) (downY / cellHeight);
                if (current > -1 && current < letters.length) {
                    choose = current;
                    if (onItemSelectListener != null) {
                        onItemSelectListener.setText(letters[choose]);
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                moveY = (int) event.getY();
                current = (int) (moveY / cellHeight);
                if (current > -1 && current < letters.length) {
                    choose = current;
                    if (onItemSelectListener != null) {
                        onItemSelectListener.setText(letters[choose]);
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
                if (onItemSelectListener != null) {
                    onItemSelectListener.setVisibility();
                }
                choose = -1 ;
                break;
        }
        invalidate();
        return true;
    }

    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
