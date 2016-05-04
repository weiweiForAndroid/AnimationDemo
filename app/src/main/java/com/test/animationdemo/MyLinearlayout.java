package com.test.animationdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * author：supershook on 2016/4/20 21:03
 */
public class MyLinearlayout extends LinearLayout {
    public MyLinearlayout(Context context) {
        super(context);
    }

    public MyLinearlayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearlayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
