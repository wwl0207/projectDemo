package com.bs.android.activity.bsui;

import com.bs.android.R;
import com.bs.android.activity.BaseActivity;

/**
 * created by WWL on 2019/9/10 0010:09
 */
public class BSULEditTextInputActivity extends BaseActivity {
    @Override
    protected int getContentLayoutRes() {
        return R.layout.bsui_edit_layout;
    }

    @Override
    protected void initView() {
        setMyTitle("EditText输入提示功能");

    }

    @Override
    protected void initHttpData() {

    }
}
