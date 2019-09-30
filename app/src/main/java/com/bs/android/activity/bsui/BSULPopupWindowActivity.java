package com.bs.android.activity.bsui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bs.android.R;
import com.bs.android.activity.BaseActivity;
import com.bspopupwindow.BSPopupWindowActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * created by WWL on 2019/9/10 0010:09
 * 赛选事件
 */
public class BSULPopupWindowActivity extends BaseActivity {

    @BindView(R.id.tv01)
    TextView tv01;
    @BindView(R.id.tv02)
    TextView tv02;

    @Override
    protected int getContentLayoutRes() {
        return R.layout.bsui_popup_layout;
    }

    @Override
    protected void initView() {
        setMyTitle("弹框列表筛选");

    }

    @Override
    protected void initHttpData() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv01, R.id.tv02})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv01:
                startActivity(BSPopupWindowActivity.class);
                break;
            case R.id.tv02:
                startActivity(BSPopupWindow2Activity.class);
                break;
        }
    }
}
