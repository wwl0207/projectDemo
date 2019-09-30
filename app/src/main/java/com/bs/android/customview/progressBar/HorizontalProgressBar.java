package com.bs.android.customview.progressBar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.bs.android.utils.CommentUtil;
import com.bs.android.utils.CommentUtil;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * @author txs
 * @date 2018/01/29
 */


public class HorizontalProgressBar extends View {


    private float maxProgress=100.0f;

    /**
     * 当前进度，100进制的
     */
    private float currentProgress=0.0f;

    private float PbWidth= CommentUtil.dpToPx(8.0f);
    private int measuredHeight;
    private int measuredWidth;


    /**
     * 每次叠加的进度
     */
    private float spaceProgress;

    /**
     * 外面传过来的进度
     */
    private float currentNum=0;





    private Handler handler=new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){

                case 0:

                    if (currentProgress<=currentNum-spaceProgress) {

                        currentProgress = currentProgress + spaceProgress;

                        postInvalidate();
                    }else {
                        executorService.shutdown();
                        currentProgress=currentNum;

                        postInvalidate();

                    }

                    break;

            }



        }
    };
    private ScheduledExecutorService executorService;
    private final Paint progressPaint;


    public HorizontalProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        progressPaint = new Paint();
        progressPaint.setAntiAlias(true);
        progressPaint.setStyle(Paint.Style.STROKE);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        measuredHeight = getMeasuredHeight();
        measuredWidth = getMeasuredWidth();

    }


    @Override
    protected void onDraw(Canvas canvas) {

        float progress=measuredWidth*currentProgress/maxProgress;

        progressPaint.setStrokeWidth(PbWidth);
        progressPaint.setColor(Color.parseColor("#F8CD14"));

        canvas.drawLine(0,measuredHeight/2,progress,measuredHeight/2,progressPaint);

    }


    /**
     * 设置最大进度
     * @param maxProgress
     */
    public void setMaxProgress(float maxProgress){
        this.maxProgress=maxProgress;
    }


    /**
     * 设置当前进度
     * @param currentNum
     */
    public void setCurrentProgress(float currentNum){
        this.currentNum=currentNum;
    }


    /**
     * 开始进度动画
     */
    public void startProgress(){

        spaceProgress=maxProgress/1000;

        executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(
                new Runnable() {
                    @Override
                    public void run() {

                        Message message = handler.obtainMessage();
                        message.what=0;
                        handler.handleMessage(message);

                    }
                },
                0,
                4,
                TimeUnit.MILLISECONDS);



    }


}
