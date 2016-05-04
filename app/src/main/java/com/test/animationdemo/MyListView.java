package com.test.animationdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * TODO: document your custom view class.
 */
public class MyListView extends ListView {
    private static final String TAG = "MyView";

    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    int lastX;
    int lastY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int rawX = (int) event.getRawX();
        int rawY = (int) event.getRawY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "MotionEvent.ACTION_DOWN");

                break;
            case MotionEvent.ACTION_MOVE:

                Log.e(TAG, "MotionEvent.ACTION_DOWN");
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "MotionEvent.ACTION_DOWN");
                break;

            default:
                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
    }

}
