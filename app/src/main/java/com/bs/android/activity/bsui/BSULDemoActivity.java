package com.bs.android.activity.bsui;

import android.os.Bundle;
import android.view.View;

import com.bs.android.R;
import com.bs.android.activity.BaseActivity;
import com.bs.android.adapter.BSUIExpandableAdapter;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * created by WWL on 2019/9/10 0010:09
 */
public class BSULDemoActivity extends BaseActivity {
    @Override
    protected int getContentLayoutRes() {
        return R.layout.bsul_main_activity;
    }

    @Override
    protected void initView() {
        setMyTitle("最常用的UI控件");

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

    @OnClick({R.id.tv01, R.id.tv02, R.id.tv03, R.id.tv04, R.id.tv05, R.id.tv06, R.id.tv07, R.id.tv08, R.id.tv09, R.id.tv10, R.id.tv11, R.id.tv12, R.id.tv13, R.id.tv14, R.id.tv15, R.id.tv16, R.id.tv17})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv01:
                startActivity(BSULBannerActivity.class);
                break;
            case R.id.tv02:
                startActivity(BSULTabLayoutActivity.class);
                break;
            case R.id.tv03:
                startActivity(BSULScrollTopActivity.class);
                break;
            case R.id.tv04:
                startActivity(BSULSmartRefreshActivity.class);
                break;
            case R.id.tv05:
                startActivity(BSULSearchAddressActivity.class);
                break;
            case R.id.tv06:
                startActivity(BSULPlaceholderActivity.class);
                break;
            case R.id.tv07:
                startActivity(BSULStaticLayoutActivity.class);
                break;
            case R.id.tv08:
                startActivity(BSULPopupWindowActivity.class);
                break;
            case R.id.tv09:
                startActivity(BSULEditTextInputActivity.class);
                break;
            case R.id.tv10:
                startActivity(BSUISignInActivity.class);
                break;
            case R.id.tv11:
                startActivity(BSULDialogActivity.class);
                break;
            case R.id.tv12:
                startActivity(BSULCountdownActivity.class);
                break;
            case R.id.tv13:
                startActivity(BSULPicturePreviewActivity.class);
                break;
            case R.id.tv14:
                startActivity(BSULPayActivity.class);
                break;
            case R.id.tv15:
                startActivity(BSULCustomButtonActivity.class);
                break;
            case R.id.tv16:
                startActivity(BSULAutoLineActivity.class);
                break;
            case R.id.tv17:
                startActivity(BSULExpandListActivity.class);
                break;
        }
    }
}
