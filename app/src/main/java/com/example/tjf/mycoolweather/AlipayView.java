package com.example.tjf.mycoolweather;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ImageFormat;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2018-06-05 0005.
 */

public class AlipayView extends View {
    private Paint mPaint;
    private RectF mRectF;
    private int mCenterX, mCenterY;
    private State state;


    enum State {
        IDLE,
        PROGERSS,
        FINISH
    }

    private int firstProgress;
    private int secondProgress;
    private Path mPath;
    private boolean isEndpay;
    private Line firstLine;
    private Line secondLine;
    private float lineProgress;
    private int sweepLength;
    private float xDis3to1, xDis2to1;
    private float x1, y1, x2, y2, x3, y3;
    private int radius = 150;
    private float inceeaseDis = 10f;
    private float circleIncRate = 10f;
    private int sweepMaxAngle = 200;


    class Line {
        PointF startPoint;
        PointF endPoint;
        float k;
        float b;

        public Line(PointF startPoint, PointF endPoint) {
            this.startPoint = startPoint;
            this.endPoint = endPoint;
            k = (endPoint.y - startPoint.y) / (endPoint.x - startPoint.x);
            b = startPoint.y - k * startPoint.x;
        }

        float getY(float x) {
            return (k * x + b);
        }
    }

    public AlipayView(Context context) {
        super(context, null);
    }

    public AlipayView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public AlipayView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(10f);
        mPaint.setColor(getResources().getColor(R.color.colorPrimary));
        mRectF = new RectF();
        mPath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        mCenterX = w / 2;
        mCenterY = h / 2;

        x1 = mCenterX - radius / 3 * 2;
        y1 = mCenterY + radius / 8;
        x2 = mCenterX - radius / 5;
        y2 = mCenterY + radius / 3 * 2;
        x3 = mCenterX + radius / 4 * 3;
        y3 = mCenterY - radius / 4;
        firstLine = new Line(new PointF(x1, y1), new PointF(x2, y2));
        secondLine = new Line(new PointF(x2, y2), new PointF(x3, y3));
        xDis3to1 = x3 - x1;
        xDis2to1 = x2 - x1;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mRectF.set(mCenterX - radius, mCenterY - radius, mCenterX + radius, mCenterY + radius);
        switch (state) {
            case IDLE:
                canvas.drawArc(mRectF, -90f, 360, false, mPaint);
                mPath.moveTo(firstLine.startPoint.x, firstLine.startPoint.y);
                mPath.lineTo(firstLine.endPoint.x, firstLine.endPoint.y);
                mPath.lineTo(secondLine.endPoint.x, secondLine.endPoint.y);
                canvas.drawPath(mPath, mPaint);
                break;

            case PROGERSS:
                canvas.drawArc(mRectF, -90f + firstProgress, sweepLength, false, mPaint);
                secondProgress += circleIncRate;
                if (secondProgress < sweepMaxAngle) {
                    firstProgress = 0;
                    sweepLength += circleIncRate;
                } else if (secondProgress >= sweepMaxAngle && secondProgress <= 360) {
                    firstProgress = secondProgress - sweepMaxAngle;
                } else {
                    if (sweepLength > 0) {
                        firstProgress += circleIncRate;
                        sweepLength -= circleIncRate;
                    } else {
                        reset();
                        if (isEndpay) {
                            state = State.FINISH;

                        }
                        postInvalidateDelayed(200);
                        break;
                    }
                }
                break;

            case FINISH:
                mPath.reset();
                if (secondProgress < 360) {
                    float sweepAngle = secondProgress;
                    canvas.drawArc(mRectF, -90f, sweepAngle, false, mPaint);
                    secondProgress += circleIncRate;
                    invalidate();
                } else {
                    canvas.drawArc(mRectF, -90f, 360f, false, mPaint);
                    mPath.moveTo(firstLine.startPoint.x, firstLine.startPoint.y);
                    float lineX = x1 + lineProgress;
                    if (lineProgress < xDis2to1) {
                        mPath.lineTo(lineX, firstLine.getY(lineX));
                        invalidate();
                    } else if (lineProgress >= xDis2to1 && lineProgress < xDis3to1) {
                        mPath.lineTo(firstLine.endPoint.x, firstLine.endPoint.y);
                        mPath.lineTo(lineX, secondLine.getY(lineX));
                        invalidate();
                    } else {
                        mPath.lineTo(firstLine.endPoint.x, firstLine.endPoint.y);
                        mPath.lineTo(secondLine.endPoint.x, secondLine.endPoint.y);
                    }
                    lineProgress += inceeaseDis;
                    canvas.drawPath(mPath, mPaint);
                }
                break;
        }


    }

    private void reset() {
        firstProgress = 0;
        secondProgress = 0;
        sweepLength = 0;
        lineProgress = 0;
    }

}
