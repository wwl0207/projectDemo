package com.bs.android.activity.bsui;

import com.bs.android.R;
import com.bs.android.activity.BaseActivity;

/**
 * created by WWL on 2019/9/10 0010:09
 * 滑动置顶效果
 */
public class BSULScrollTopActivity extends BaseActivity {
    @Override
    protected int getContentLayoutRes() {
        return R.layout.bsul_scrolltop_layout;
    }

    @Override
    protected void initView() {
        setMyTitle("滑动置顶效果");

    }

    @Override
    protected void initHttpData() {

    }
}
