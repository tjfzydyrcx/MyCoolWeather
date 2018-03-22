package com.example.mygobang;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018-01-29 0029.
 */
public class GobangView extends View {

    private int mPanelWidth;
    private float mLineHeight;
    private int MAX_LINE = 10;


    private Paint mPaint = new Paint();
    private Bitmap mWhitePiece;
    private Bitmap mBlackPiece;

    private float ratioPieceOfLineHeight = 3 * 1.0f / 4;
    private ArrayList<Point> mWhiteArrray = new ArrayList<>();
    private ArrayList<Point> mBlackArrray = new ArrayList<>();
    private boolean mIsWhite = true;
    private boolean mIsGameOver;

    private boolean mIsWhiteWinner = true;

    public GobangView(Context context) {
        super(context);
    }

    public GobangView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackgroundColor(0x44ff0000);

        init();
    }

    private void init() {
        mPaint.setColor(0x88000000);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mBlackPiece = BitmapFactory.decodeResource(getResources(), R.drawable.stone_b1);
        mWhitePiece = BitmapFactory.decodeResource(getResources(), R.drawable.stone_w2);

    }

    public GobangView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = Math.min(widthSize, heightSize);

        if (widthMode == MeasureSpec.UNSPECIFIED) {
            width = heightSize;
        } else if (heightMode == MeasureSpec.UNSPECIFIED) {
            width = widthSize;
        }
        setMeasuredDimension(width, width);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mPanelWidth = w;
        mLineHeight = mPanelWidth * 1.0f / MAX_LINE;
        int pieceWidth = (int) (mLineHeight * ratioPieceOfLineHeight);
        mWhitePiece = Bitmap.createScaledBitmap(mWhitePiece, pieceWidth, pieceWidth, false);
        mBlackPiece = Bitmap.createScaledBitmap(mBlackPiece, pieceWidth, pieceWidth, false);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        onDrawBoard(canvas);

        onDrawPiece(canvas);
        ChackGameOver();
    }

    private void ChackGameOver() {

        boolean whiteWin = chackFiveLine(mWhiteArrray);
        boolean blackWin = chackFiveLine(mBlackArrray);
        if (whiteWin || blackWin) {
            mIsGameOver = true;
            mIsWhiteWinner = whiteWin;
            String text = mIsWhiteWinner ? "白棋胜利" : "黑棋胜利";
            Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
            ;
        }

    }

    private boolean chackFiveLine(List<Point> mPoint) {
        for (Point p : mPoint) {
            int x = p.x;
            int y = p.y;
            boolean win = checkHorizontal(x, y, mPoint);
            if (win) return true;
            win = checkVertical(x, y, mPoint);
            if (win) return true;
            win = checkLeftDiagonal(x, y, mPoint);
            if (win) return true;
            win = checkRightDiagonal(x, y, mPoint);
            if (win) return true;
        }
        return false;
    }

    private int MAX_COUNT_IN_lINE = 5;

    private boolean checkHorizontal(int x, int y, List<Point> mPoint) {

        int count = 1;
        //左边
        for (int i = 1; i < MAX_COUNT_IN_lINE; i++) {
            if (mPoint.contains(new Point(x - i, y))) {
                count++;
            } else {
                break;
            }
        }
        //右边
        for (int i = 1; i < MAX_COUNT_IN_lINE; i++) {
            if (mPoint.contains(new Point(x + i, y))) {
                count++;
            } else {
                break;
            }
        }

        if (count == MAX_COUNT_IN_lINE) return true;
        return false;
    }

    private boolean checkVertical(int x, int y, List<Point> mPoint) {

        int count = 1;
        //左边
        for (int i = 1; i < MAX_COUNT_IN_lINE; i++) {
            if (mPoint.contains(new Point(x, y - i))) {
                count++;
            } else {
                break;
            }
        }
        //右边
        for (int i = 1; i < MAX_COUNT_IN_lINE; i++) {
            if (mPoint.contains(new Point(x, y + i))) {
                count++;
            } else {
                break;
            }
        }
        if (count == MAX_COUNT_IN_lINE) return true;
        return false;
    }

    private boolean checkLeftDiagonal(int x, int y, List<Point> mPoint) {

        int count = 1;
        //左边
        for (int i = 1; i < MAX_COUNT_IN_lINE; i++) {
            if (mPoint.contains(new Point(x - i, y + i))) {
                count++;
            } else {
                break;
            }
        }
        //右边
        for (int i = 1; i < MAX_COUNT_IN_lINE; i++) {
            if (mPoint.contains(new Point(x + i, y - i))) {
                count++;
            } else {
                break;
            }
        }

        if (count == MAX_COUNT_IN_lINE) return true;
        return false;
    }

    private boolean checkRightDiagonal(int x, int y, List<Point> mPoint) {

        int count = 1;
        //左边
        for (int i = 1; i < MAX_COUNT_IN_lINE; i++) {
            if (mPoint.contains(new Point(x - i, y - i))) {
                count++;
            } else {
                break;
            }
        }
        //右边
        for (int i = 1; i < MAX_COUNT_IN_lINE; i++) {
            if (mPoint.contains(new Point(x + i, y + i))) {
                count++;
            } else {
                break;
            }
        }
        if (count == MAX_COUNT_IN_lINE) return true;
        return false;
    }

    private void onDrawPiece(Canvas canvas) {

        for (int i = 0, n = mWhiteArrray.size(); i < n; i++) {
            Point whitePoint = mWhiteArrray.get(i);
            canvas.drawBitmap(mWhitePiece, (whitePoint.x + (1 - ratioPieceOfLineHeight) / 2) * mLineHeight,
                    (whitePoint.y + (1 - ratioPieceOfLineHeight) / 2) * mLineHeight, null);
        }
        for (int i = 0, n = mBlackArrray.size(); i < n; i++) {
            Point blackPoint = mBlackArrray.get(i);
            canvas.drawBitmap(mBlackPiece, (blackPoint.x + (1 - ratioPieceOfLineHeight) / 2) * mLineHeight,
                    (blackPoint.y + (1 - ratioPieceOfLineHeight) / 2) * mLineHeight, null);
        }
    }

    private void onDrawBoard(Canvas canvas) {
        int w = mPanelWidth;
        float lineHeight = mLineHeight;
        Log.e("tis=", lineHeight + "");
        for (int i = 0; i < MAX_LINE; i++) {
            int startX = (int) (lineHeight / 2);
            int endX = (int) (w - lineHeight / 2);
            int y = (int) ((0.5 + i) * lineHeight);
            canvas.drawLine(startX, y, endX, y, mPaint);
            canvas.drawLine(y, startX, y, endX, mPaint);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//
        if (mIsGameOver) {
            return false;
        }
        int action = event.getAction();
        if (action == MotionEvent.ACTION_UP) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            Point p = getValidPoint(x, y);
            if (mWhiteArrray.contains(p) || mBlackArrray.contains(p)) {
                return false;
            }
            if (mIsWhite) {
                mWhiteArrray.add(p);
            } else {
                mBlackArrray.add(p);
            }
            invalidate();
            mIsWhite = !mIsWhite;
        }
        return true;
    }


    private Point getValidPoint(int x, int y) {

        return new Point((int) (x / mLineHeight), (int) (y / mLineHeight));
    }

    public  void  Start(){
        mWhiteArrray.clear();;
        mBlackArrray.clear();
        mIsGameOver=false;
        mIsWhiteWinner=false;
        invalidate();
    }

    public static final String INSTANCE = "instane";
    public static final String INSTANCE_GAME_OVER = "game_over";
    public static final String INSTANCE_WHITE_ARRAY = "white_array";
    public static final String INSTANCE_BLACK_ARRAY = "black_array";


    @Override
    protected Parcelable onSaveInstanceState() {

        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE, super.onSaveInstanceState());
        bundle.putBoolean(INSTANCE_GAME_OVER, mIsGameOver);
        bundle.putParcelableArrayList(INSTANCE_WHITE_ARRAY, mWhiteArrray);
        bundle.putParcelableArrayList(INSTANCE_BLACK_ARRAY, mBlackArrray);
        return bundle;

    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle){
            Bundle bundle= (Bundle) state;
            mIsGameOver=bundle.getBoolean(INSTANCE_GAME_OVER);
            mWhiteArrray=bundle.getParcelableArrayList(INSTANCE_WHITE_ARRAY);
            mBlackArrray=bundle.getParcelableArrayList(INSTANCE_BLACK_ARRAY);
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE));
            return;
        }
        super.onRestoreInstanceState(state);
    }
}
