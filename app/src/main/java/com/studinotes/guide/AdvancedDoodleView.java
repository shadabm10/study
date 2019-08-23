package com.studinotes.guide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.forward.androids.ScaleGestureDetectorApi27;
import cn.forward.androids.TouchGestureDetector;

/**
 *
 * Created on 24/06/2018.
 */
public class AdvancedDoodleView extends View {

    private final static String TAG = "AdvancedDoodleView";

    private Paint mPaint = new Paint();
    private List<PathItem> mPathList = new ArrayList<>(); //
    private TouchGestureDetector mTouchGestureDetector; //
    private float mLastX, mLastY;
    private PathItem mCurrentPathItem; //
    private PathItem mSelectedPathItem; //

    private Bitmap mBitmap;
    private float mBitmapTransX, mBitmapTransY, mBitmapScale = 1;

    public AdvancedDoodleView(Context context, Bitmap bitmap) {
        super(context);
        mBitmap = bitmap;

        //
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(20);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        //
        mTouchGestureDetector = new TouchGestureDetector(getContext(), new TouchGestureDetector.OnTouchGestureListener() {

            RectF mRectF = new RectF();

            //
            Float mLastFocusX;
            Float mLastFocusY;
            float mTouchCentreX, mTouchCentreY;

            @Override
            public boolean onScaleBegin(ScaleGestureDetectorApi27 detector) {
                Log.d(TAG, "onScaleBegin: ");
                mLastFocusX = null;
                mLastFocusY = null;
                return true;
            }

            @Override
            public void onScaleEnd(ScaleGestureDetectorApi27 detector) {
                Log.d(TAG, "onScaleEnd: ");
            }

            @Override
            public boolean onScale(ScaleGestureDetectorApi27 detector) { //
                Log.d(TAG, "onScale: ");
                // 屏幕上的焦点
                mTouchCentreX = detector.getFocusX();
                mTouchCentreY = detector.getFocusY();

                if (mLastFocusX != null && mLastFocusY != null) { //
                    float dx = mTouchCentreX - mLastFocusX;
                    float dy = mTouchCentreY - mLastFocusY;
                    // 移动图片
                    mBitmapTransX = mBitmapTransX + dx;
                    mBitmapTransY = mBitmapTransY + dy;
                }

                // 缩放图片
                mBitmapScale = mBitmapScale * detector.getScaleFactor();
                if (mBitmapScale < 0.1f) {
                    mBitmapScale = 0.1f;
                }
                invalidate();

                mLastFocusX = mTouchCentreX;
                mLastFocusY = mTouchCentreY;

                return true;
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) { //
                float x = toX(e.getX()), y = toY(e.getY());
                boolean found = false;
                for (PathItem path : mPathList) { //
                    path.mPath.computeBounds(mRectF, true); //
                    mRectF.offset(path.mX, path.mY); //
                    if (mRectF.contains(x, y)) { //
                        found = true;
                        mSelectedPathItem = path;
                        break;
                    }
                }
                if (!found) { //
                    mSelectedPathItem = null;
                }
                invalidate();
                return true;
            }

            @Override
            public void onScrollBegin(MotionEvent e) { //
                Log.d(TAG, "onScrollBegin: ");
                float x = toX(e.getX()), y = toY(e.getY());
                if (mSelectedPathItem == null) {
                    mCurrentPathItem = new PathItem(); //
                    mPathList.add(mCurrentPathItem); //
                    mCurrentPathItem.mPath.moveTo(x, y);
                }
                mLastX = x;
                mLastY = y;
                invalidate(); //
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) { // 滑动中
                Log.d(TAG, "onScroll: " + e2.getX() + " " + e2.getY());
                float x = toX(e2.getX()), y = toY(e2.getY());
                if (mSelectedPathItem == null) { //
                    mCurrentPathItem.mPath.quadTo(
                            mLastX,
                            mLastY,
                            (x + mLastX) / 2,
                            (y + mLastY) / 2); //
                } else { //
                    mSelectedPathItem.mX = mSelectedPathItem.mX + x - mLastX;
                    mSelectedPathItem.mY = mSelectedPathItem.mY + y - mLastY;
                }
                mLastX = x;
                mLastY = y;
                invalidate(); //
                return true;
            }

            @Override
            public void onScrollEnd(MotionEvent e) { //
                Log.d(TAG, "onScrollEnd: ");
                float x = toX(e.getX()), y = toY(e.getY());
                if (mSelectedPathItem == null) {
                    mCurrentPathItem.mPath.quadTo(
                            mLastX,
                            mLastY,
                            (x + mLastX) / 2,
                            (y + mLastY) / 2); //
                    mCurrentPathItem = null; //
                }
                invalidate(); //
            }

        });

        //
        //
        mTouchGestureDetector.setScaleSpanSlop(1); //
        mTouchGestureDetector.setScaleMinSpan(1); //
        mTouchGestureDetector.setIsLongpressEnabled(false);
        mTouchGestureDetector.setIsScrollAfterScaled(false);
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldw, int oldh) { //view
        super.onSizeChanged(width, height, oldw, oldh);
        int w = mBitmap.getWidth();
        int h = mBitmap.getHeight();
        float nw = w * 1f / getWidth();
        float nh = h * 1f / getHeight();
        float centerWidth, centerHeight;
        //
        if (nw > nh) {
            mBitmapScale = 1 / nw;
            centerWidth = getWidth();
            centerHeight = (int) (h * mBitmapScale);
        } else {
            mBitmapScale = 1 / nh;
            centerWidth = (int) (w * mBitmapScale);
            centerHeight = getHeight();
        }
        //
        mBitmapTransX = (getWidth() - centerWidth) / 2f;
        mBitmapTransY = (getHeight() - centerHeight) / 2f;
        invalidate();
    }

    /**
     *
     */
    public final float toX(float touchX) {
        return (touchX - mBitmapTransX) / mBitmapScale;
    }

    /**
     *
     */
    public final float toY(float touchY) {
        return (touchY - mBitmapTransY) / mBitmapScale;
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
        canvas.translate(mBitmapTransX, mBitmapTransY);
        canvas.scale(mBitmapScale, mBitmapScale);

        //
        canvas.drawBitmap(mBitmap, 0, 0, null);

        for (PathItem path : mPathList) { //
            canvas.save();
            canvas.translate(path.mX, path.mY);
            if (mSelectedPathItem == path) {
                mPaint.setColor(Color.YELLOW); //
            } else {
                mPaint.setColor(Color.RED); //
            }
            canvas.drawPath(path.mPath, mPaint);
            canvas.restore();
        }
    }

    /**
     *
     */
    private static class PathItem {
        Path mPath = new Path(); //
        float mX, mY; //
    }
}
