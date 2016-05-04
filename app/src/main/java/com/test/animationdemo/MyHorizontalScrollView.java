package com.test.animationdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * author：supershook on 2016/4/20 15:38
 */
public class MyHorizontalScrollView extends ViewGroup {
    private static  final  String TAG = "MyHorizontalScrollView";
    /**
     * 滑动工具
     */
    private Scroller scroller;
    /**
     * 子布局个数
     */
    private int mChildrenSize;
    /**
     * 子布局宽度
     */
    private int mChildrenWidth;
    /**
     * 子布局下标
     */
    private int mChildrenIndex;

    /**
     * 上次滑动X坐标
     */
    private int mLastX =0;
    /**
     * 记录上次滑动Y坐标
     */
    private int mLastY = 0;

    /**
     * onIntercept中X坐标
     */
    private int mLastInterceptX =0 ;
    /**
     * onIntercept中Y坐标
     */
    private int mLastInterceptY =0 ;
    /**
     * 运动速率追踪器
     */
    private VelocityTracker velocityTracker;

    public MyHorizontalScrollView(Context context) {
        super(context);
        init();

    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        if (scroller== null){
            scroller = new Scroller(getContext());
            velocityTracker = VelocityTracker.obtain();
        }
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

        int childLeft = 0;
        final int childCount = getChildCount();
        mChildrenSize = childCount;
        for (int i=0;i<childCount;i++){
            final View child = getChildAt(i);
            if (child.getVisibility() != View.GONE){
                final int childWidth = child.getMeasuredWidth();
                mChildrenWidth = childWidth;
                child.layout(childLeft,0,childLeft+childWidth,child.getMeasuredHeight());
                childLeft+=childWidth;
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth =0;
        int measureHeigth = 0;
        final  int childCount = getChildCount();
        measureChildren(widthMeasureSpec,heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        if (childCount ==0){
            setMeasuredDimension(0,0);//这里没考虑margin和padding
        }else {
            if (widthSpecMode==MeasureSpec.AT_MOST && heightSpecMode ==MeasureSpec.AT_MOST){
                final View childView = getChildAt(0);
                measureWidth = childView.getMeasuredWidth()*childCount;
                measureHeigth = childView.getMeasuredHeight();
                setMeasuredDimension(measureWidth,measureHeigth);

            }else  if (heightSpecMode==MeasureSpec.AT_MOST){
                final View childView = getChildAt(0);
                measureHeigth = childView.getMeasuredHeight();
                setMeasuredDimension(widthSpecSize,measureHeigth);
            }else if (widthSpecMode ==MeasureSpec.AT_MOST){
                final View childView = getChildAt(0);
                measureWidth = childView.getMeasuredWidth();
                setMeasuredDimension(measureWidth,widthSpecSize);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        int x  = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()){
            case  MotionEvent.ACTION_DOWN:
                Log.e(TAG,"onInterceptTouchEvent_ACTION_DOWN");
                intercept =false;
                if (!scroller.isFinished()){
                    //如果动画没结束，则继续拦截当前操作，并终止动画执行。
                    scroller.abortAnimation();
                    intercept =true;
                }
                break;

            case MotionEvent.ACTION_MOVE:
                Log.e(TAG,"onInterceptTouchEvent_ACTION_MOVE");
                int deltaX = (int) (ev.getX()- mLastInterceptX);
                int deltaY = (int) (ev.getY()-mLastInterceptY);
                 if (Math.abs(deltaX)>Math.abs(deltaY)){
                     intercept =true;
                 }else {
                     intercept = false;
                 }
                break;

            case MotionEvent.ACTION_UP:
                Log.e(TAG,"onInterceptTouchEvent_ACTION_UP");
                intercept = false;

                break;
        }
        mLastInterceptX = x;
        mLastInterceptY = y;
        mLastX = x;
        mLastY = y;
        Log.e(TAG,"intercept:"+intercept);
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        velocityTracker.addMovement(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (!scroller.isFinished()){
                    scroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = (int) (event.getX()-mLastX);
                int deltaY = (int) (event.getY()-mLastY);
                scrollBy(-deltaX,-deltaY);
                break;

            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                velocityTracker.computeCurrentVelocity(1000);
                float xVelocity = velocityTracker.getXVelocity();
                Log.e(TAG,"速率："+xVelocity);
                if (Math.abs(xVelocity)>=50){
                    mChildrenIndex = xVelocity>0?mChildrenIndex-1:mChildrenIndex+1;//速率有可能是正的，也有可能负的。
                }else {
                    mChildrenIndex = (scrollX+mChildrenWidth/2)/mChildrenWidth;
                }

                mChildrenIndex = Math.max(0,Math.min(mChildrenIndex,mChildrenSize-1));

                int dx = mChildrenIndex*mChildrenWidth-scrollX;
                smoothScrollBy(dx,0);
                velocityTracker.clear();
                break;
        }

        mLastX = x;
        mLastY = y;
        return  true;
    }

    private void smoothScrollBy(int dx, int i) {
        scroller.startScroll(getScrollX(),0,dx,0,500);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX(),scroller.getCurrY());
            postInvalidate();
        }
    }

}
