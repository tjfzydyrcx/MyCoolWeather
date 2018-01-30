package com.example.mygobang;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;

/**
 * Created by Administrator on 2018-01-30 0030.
 */
public class BouncingView extends View {
    private Path mPath = new Path();
    private Paint mPaint;
    private int mArcHeight;
    private int mMaxArcHeight;
    private Status mStatus = Status.NONE;
    private Animation.AnimationListener mListener;

    public enum Status {
        NONE,
        STRAT_UP,
        START_DOWN
    }

    public BouncingView(Context context) {
        super(context);
    }

    public BouncingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(getResources().getColor(android.R.color.white));

        mMaxArcHeight = DisplayUtil.dip2px(getContext(), 50);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制贝塞尔曲线
        //绘制封闭的一个曲线，并且随着动画的执行，让上边的曲线不断增长高度
        int currentPointY = 0;
        mPath.reset();
        switch (mStatus) {
            case NONE:
                currentPointY = 0;
                break;
            case STRAT_UP:
                currentPointY= (int) (getHeight()*(1-(float)mArcHeight/mMaxArcHeight)*mMaxArcHeight);
                break;
            case START_DOWN:
                currentPointY=mMaxArcHeight;

                break;
            default:
                break;
        }
        mPath.moveTo(0,currentPointY);
        mPath.quadTo(getWidth()/2,currentPointY-mArcHeight,getWidth(),currentPointY);
        mPath.lineTo(getWidth(),getHeight());
        mPath.lineTo(0,getHeight());
        mPath.close();

        canvas.drawPath(mPath, mPaint);

    }

    public void show() {
        //开启动画
        //计算曲线增长
        ValueAnimator valueAn = ValueAnimator.ofInt(0, mMaxArcHeight);
        valueAn.setDuration(800);
        valueAn.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mStatus=Status.STRAT_UP;
                mArcHeight = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAn.start();





    }
}
