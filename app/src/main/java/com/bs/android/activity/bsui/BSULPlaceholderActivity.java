package com.bs.android.activity.bsui;

import com.bs.android.R;
import com.bs.android.activity.BaseActivity;

/**
 * created by WWL on 2019/9/10 0010:09
 * 占位符方法
 */
public class BSULPlaceholderActivity extends BaseActivity {
    @Override
    protected int getContentLayoutRes() {
        return R.layout.bsui_placeholder_layout;
    }

    @Override
    protected void initView() {
        setMyTitle("占位符效果");
        startActivity(BSULSmartRefreshActivity.class);
    }

    @Override
    protected void initHttpData() {
        this.finish();
    }
}
