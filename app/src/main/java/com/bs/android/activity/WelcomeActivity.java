package com.bs.android.activity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bs.android.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.iwgang.countdownview.CountdownView;


/**
 * 欢迎页面
 */
public class WelcomeActivity extends BaseActivity {
    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.adv_start)
    ImageView adv_start;
    @BindView(R.id.tv_layout)
    LinearLayout tvLayout;
    @BindView(R.id.cuntdown_view)
    CountdownView cuntdownView;

    @Override
    protected int getContentLayoutRes() {

        return R.layout.activity_welcome;
    }

    @Override
    protected void initView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        hideTitleBar();

        tvLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MainActivity.class);
                removeActivity(WelcomeActivity.this);
            }
        });
    }

    @Override
    protected void initHttpData() {

        cuntdownView.start(5000);
        cuntdownView.setOnCountdownIntervalListener(10, new CountdownView.OnCountdownIntervalListener() {
            @Override
            public void onInterval(CountdownView cv, long remainTime) {
                int time = (int) (remainTime / 1000) + 1;
                tvNext.setText(time + "");
            }
        });
        cuntdownView.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
            @Override
            public void onEnd(CountdownView cv) {
                startActivity(MainActivity.class);
                removeActivity(WelcomeActivity.this);
            }
        });


//        HashMap<String, String> map = new HashMap<>();
//        HttpUtils.POST(ConstantUrl.STARTADV, map, false, AdvVO.class, new Callback<AdvVO>() {
//            @Override
//            public void onStart() {
//            }
//
//            @Override
//            public void onSucceed(String json, String code, final AdvVO model) {
//                if (!DataSafeUtils.isEmpty(model.getData().getImage())) {
//                    try {
//                        Glide.with(WelcomeActivity.this).load(model.getData().getImage()).into(adv_start);
//                    } catch (Exception e) {
//
//                    }
//
//                    if (!DataSafeUtils.isEmpty(adv_start))
//                        adv_start.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                if (cuntdownView != null) {
//                                    cuntdownView.stop();
//                                }
//                                startActivity(MainActivity.class);
//                                startIntent(model.getData().getUrl(), model.getData().getUrl_type());
//                                removeActivity(WelcomeActivity.this);
//                            }
//                        });
//                }
//            }
//
//            @Override
//            public void onOtherStatus(String json, String code) {
//                if (code.equals("300")) {
//                    Glide.with(WelcomeActivity.this).load(R.drawable.start_adv_img).into(adv_start);
//                }
//            }
//
//            @Override
//            public void onFailed(Throwable e) {
//                dismissDialog();
//            }
//
//            @Override
//            public void onFinish() {
//                dismissDialog();
//            }
//        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cuntdownView != null) {
            cuntdownView.stop();
        }
    }
}