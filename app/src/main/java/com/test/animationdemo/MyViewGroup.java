package com.test.animationdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * author：supershook on 2016/4/18 18:45
 */
public class MyViewGroup extends ScrollView {
    private static final String TAG = "MyViewGroup";
    int lastX = 0;
    int lastY = 0;

    public MyViewGroup(Context context) {
        super(context);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * {@inheritDoc}
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {


    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG,"MotionEvent.ACTION_DOWN");
                intercept= false;
                break;

            case MotionEvent.ACTION_MOVE:
                Log.e(TAG,"MotionEvent.ACTION_MOVE");

                int deltaX = x - lastX;
                int deltaY = y - lastY;
                if (Math.abs(deltaX)>Math.abs(deltaY)){
                    intercept = true;

                }else {
                    intercept = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG,"MotionEvent.ACTION_UP");
                intercept= false;
                break;

        }
        lastX = x;
        lastY = y;
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG,"onTouchEvent_MotionEvent.ACTION_DOWN");
                break;

            case MotionEvent.ACTION_MOVE:
                Log.e(TAG,"onTouchEvent_MotionEvent.ACTION_MOVE");

                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG,"onTouchEvent_MotionEvent.ACTION_UP");
                break;
        }
        return true;
    }
}
