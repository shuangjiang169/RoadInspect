package com.example.boyceng.roadinspect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by fengan on 2017/10/25/025.
 */

public class AudioColumnView extends View {
    private int columnNum = 10;
    private int random;
    private boolean isStart = true;
    private Random mRandom;
    private int mRect_t1;
    private int mRect_t2;
    private int mRect_t3;
    private int mRect_t4;
    private int mRect_t5;
    private int mRect_t6;
    private int mRect_t7;
    private int mRect_t8;
    private int mRect_t9;
    private int mRect_t10;



    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x1234) {
                invalidate();
            }
        }
    };
    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private double mRect_w;
    private RectF r1;
    private RectF r2;
    private RectF r3;
    private RectF r4;
    private RectF r5;
    private RectF r6;
    private RectF r7;
    private RectF r8;
    private RectF r9;
    private RectF r10
            ;
    public AudioColumnView(Context context) {
        this(context, null);
    }

    public AudioColumnView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public AudioColumnView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mRandom = new Random();
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#ffffff"));
        //绘制阴影，param1：模糊半径；param2：x轴大小：param3：y轴大小；param4：阴影颜色
        mPaint.setShadowLayer(3F, 2F, 2F, Color.parseColor("#abd3f9"));
      // mPaint.setAlpha(int 222);
        mPaint.setStyle(Paint.Style.FILL);
        r1 = new RectF();
        r2 = new RectF();
        r3 = new RectF();
        r4 = new RectF();
        r5 = new RectF();
        r6 = new RectF();
        r7 = new RectF();
        r8 = new RectF();
        r9 = new RectF();
        r10 = new RectF();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        mRect_w = mWidth/(2*columnNum+1);
        random =mHeight / 10;
    }

    public void start(){
        isStart = true;
        invalidate();
    }

    public void stop(){
        isStart = false;
        invalidate();
    }

    public boolean isStart(){
        return isStart;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isStart) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    mRect_t1 = mRandom.nextInt(random);
                    mRect_t2 = mRandom.nextInt(random);
                    mRect_t3 = mRandom.nextInt(random);
                    mRect_t4 = mRandom.nextInt(random);
                    mRect_t5 = mRandom.nextInt(random);
                    mRect_t6 = mRandom.nextInt(random);
                    mRect_t7 = mRandom.nextInt(random);
                    mRect_t8 = mRandom.nextInt(random);
                    mRect_t9 = mRandom.nextInt(random);
                    mRect_t10 = mRandom.nextInt(random);

                    mHandler.sendEmptyMessage(0x1234);
                }
            },300);
        }
        //画柱状；动态图，就要改变柱状的top值        改变两个mRect_w的值可以改变柱状宽度，必须按等差数列来 ， mRect_t1*10 几根条就乘以几
        r1.set((float) (mRect_w),mRect_t1*columnNum,(float)( mRect_w*2),(float) (mHeight));
        r2.set((float) (mRect_w*3),mRect_t2*columnNum,(float)(mRect_w*4),(float) (mHeight));
        r3.set((float) (mRect_w*5),mRect_t3*columnNum,(float)(mRect_w*6),(float) (mHeight));
        r4.set((float) (mRect_w*7),mRect_t4*columnNum,(float)( mRect_w*8),(float) (mHeight));
        r5.set((float) (mRect_w*9),mRect_t5*columnNum,(float)(mRect_w*10),(float) (mHeight));
        r6.set((float) (mRect_w*11),mRect_t6*columnNum,(float)( mRect_w*12),(float) (mHeight));
        r7.set((float) (mRect_w*13),mRect_t7*columnNum,(float)(mRect_w*14),(float) (mHeight));
        r8.set((float) (mRect_w*15),mRect_t8*columnNum,(float)(mRect_w*16),(float) (mHeight));
        r9.set((float) (mRect_w*17),mRect_t9*columnNum,(float)( mRect_w*18),(float) (mHeight));
        r10.set((float) (mRect_w*19),mRect_t10*columnNum,(float)(mRect_w*20),(float) (mHeight));
        canvas.drawRoundRect(r1, 20, 20, mPaint);
        canvas.drawRoundRect(r2, 20, 20, mPaint);
        canvas.drawRoundRect(r3, 40, 40, mPaint);
        canvas.drawRoundRect(r4, 40, 40, mPaint);
        canvas.drawRoundRect(r5, 40, 40, mPaint);
        canvas.drawRoundRect(r6, 40, 40, mPaint);
        canvas.drawRoundRect(r7, 40, 40, mPaint);
        canvas.drawRoundRect(r8, 40, 40, mPaint);
        canvas.drawRoundRect(r9, 40, 40, mPaint);
        canvas.drawRoundRect(r10, 40, 40, mPaint);
    }
}
