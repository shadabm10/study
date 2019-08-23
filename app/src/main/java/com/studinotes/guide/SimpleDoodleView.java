package com.studinotes.guide;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.forward.androids.TouchGestureDetector;

/**

 * Created on 24/06/2018.
 */
public class SimpleDoodleView extends View {

    private final static String TAG = "SimpleDoodleView";

    private Paint mPaint = new Paint();
    private List<Path> mPathList = new ArrayList<>(); //
    private TouchGestureDetector mTouchGestureDetector; //
    private float mLastX, mLastY;
    private Path mCurrentPath; //

    public SimpleDoodleView(Context context) {
        super(context);
        // 设置画笔
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(20);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        // 由手势识别器处理手势
        mTouchGestureDetector = new TouchGestureDetector(getContext(), new TouchGestureDetector.OnTouchGestureListener() {

            @Override
            public void onScrollBegin(MotionEvent e) { //
                Log.d(TAG, "onScrollBegin: ");
                mCurrentPath = new Path(); //
                mPathList.add(mCurrentPath); //
                mCurrentPath.moveTo(e.getX(), e.getY());
                mLastX = e.getX();
                mLastY = e.getY();
                invalidate(); //
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) { // 滑动中
                Log.d(TAG, "onScroll: " + e2.getX() + " " + e2.getY());
                mCurrentPath.quadTo(
                        mLastX,
                        mLastY,
                        (e2.getX() + mLastX) / 2,
                        (e2.getY() + mLastY) / 2); //
                mLastX = e2.getX();
                mLastY = e2.getY();
                invalidate(); //
                return true;
            }

            @Override
            public void onScrollEnd(MotionEvent e) { //
                Log.d(TAG, "onScrollEnd: ");
                mCurrentPath.quadTo(
                        mLastX,
                        mLastY,
                        (e.getX() + mLastX) / 2,
                        (e.getY() + mLastY) / 2); //
                mCurrentPath = null; //
                invalidate(); //
            }

        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        boolean consumed = mTouchGestureDetector.onTouchEvent(event); //
        if (!consumed) {
            return super.dispatchTouchEvent(event);
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (Path path : mPathList) { //
            canvas.drawPath(path, mPaint);
        }
    }
}
