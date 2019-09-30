package com.bs.android.activity.bsui;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

import com.bs.android.R;
import com.bs.android.activity.BaseActivity;
import com.bs.android.customview.SpikeDownTimerView;
import com.bs.android.utils.DateUtils;

import java.text.ParseException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * created by WWL on 2019/9/10 0010:09
 * 几种倒计时控件
 */
public class BSULCountdownActivity extends BaseActivity {
    @BindView(R.id.timerview)
    SpikeDownTimerView timerview;
    CountDownTimer timer;
    @BindView(R.id.timer_tv)
    TextView timerTv;
    @BindView(R.id.timer_tv2)
    TextView timerTv2;

    @Override
    protected int getContentLayoutRes() {
        return R.layout.bsui_countdown_layout;
    }

    @Override
    protected void initView() {
        setMyTitle("倒计时控件");

    }

    @Override
    protected void initHttpData() {
        // 第一种方式
        try {
            geAdvanceSaleTime(1568599500);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //第二种方式
        RefultTimeStart(7201);
    }


    private void geAdvanceSaleTime(long end_time) throws ParseException {
        int sum = (int) (end_time - DateUtils.dateToStamp());
//        int sum = (int) (Long.parseLong(end_time) - DateUtils.dateToStamp());
        Log.v("logger", sum + "-------");
        if (sum > 0) {
            // 把秒数传到倒计时方法中。。
            timerview.addTime(sum);
            // 开始倒计时
            timerview.start();
        } else {
            timerview.addTime(0);
        }
    }


    /**
     * 倒数计时器__________我把timer01.start()注释了，没让它启动，如果以后需要，再启动便可
     */
    private void RefultTimeStart(final int mTimes) {
        /****
         * 倒计时方法如下---------------------------------------------
         */
        timer = new CountDownTimer(mTimes * 1000, 1000) {

            /**
             * 固定间隔被调用,就是每隔countDownInterval会回调一次方法onTick
             * @param millisUntilFinished
             */
            @Override
            public void onTick(long millisUntilFinished) {
                timerTv.setText(secToTime((int) (millisUntilFinished / 1000)));//毫秒/1000=秒
                timerTv2.setText(formatTime(millisUntilFinished ));//毫秒
            }

            /**
             * 倒计时完成时被调用
             */
            @Override
            public void onFinish() {
                Log.i("tags", "倒计时--完毕了");
            }
        };
        timer.start();
    }

    /**
     * 将毫秒转化为 分钟：秒 的格式
     *
     * @param millisecond 毫秒
     * @return
     */
    public String formatTime(long millisecond) {
        int minute;//分钟
        int second;//秒数
        minute = (int) ((millisecond / 1000) / 60);
        second = (int) ((millisecond / 1000) % 60);
        if (minute < 10) {
            if (second < 10) {
                return "0" + minute + ":" + "0" + second;
            } else {
                return "0" + minute + ":" + second;
            }
        } else {
            if (second < 10) {
                return minute + ":" + "0" + second;
            } else {
                return minute + ":" + second;
            }
        }
    }

    /**
     *
     * @param seconds 秒
     * @return
     */
    public static String secToTime(int seconds) {
        int hour = seconds / 3600;
        int minute = (seconds - hour * 3600) / 60;
        int second = (seconds - hour * 3600 - minute * 60);

        StringBuffer sb = new StringBuffer();
        if (hour >= 0) {
            if (hour >= 10) {
                sb.append(hour + ":");
            } else {
                sb.append("0" + hour + ":");
            }
        }
        if (minute >= 0) {
            if (minute >= 10) {
                sb.append(minute + ":");
            } else {
                sb.append("0" + minute + ":");
            }
        }
        if (second >= 0) {
            if (second >= 10) {
                sb.append(second + "");
            } else {
                sb.append("0" + second + "");
            }

        }

        return sb.toString();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (timerview != null) {
                timerview.stop();
                timerview = null;
            }
            if (timer != null) {
                timer.cancel();
                timer.onFinish();
                timer = null;
            }
        } catch (Exception e) {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
