package com.bs.android.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bs.android.utils.DataSafeUtils;
import com.bs.android.utils.SPUtil;
import com.jaeger.library.StatusBarUtil;
import com.jude.swipbackhelper.SwipeBackHelper;
import com.jude.swipbackhelper.SwipeListener;
import com.bs.android.App;
import com.bs.android.R;
import com.bs.android.customview.titlebar.MyTitleBarView;
import com.bs.android.utils.CommentUtil;
import com.bs.android.customview.titlebar.MyTitleBarView;
import com.bs.android.utils.CommentUtil;
import com.bs.android.utils.DialogUtil;
import com.bs.android.utils.LoginUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by admin on 2017/12/20.
 */

public abstract class BaseActivity extends FragmentActivity {
    private Unbinder unbinder;

    @BindView(R.id.my_titlebar)
     protected MyTitleBarView my_titlebar;

    private LinearLayout ll_base_layout;

    private boolean isMainActivity = false;//是否是主界面

    private long exitTime = 0;//退出时间

    private boolean isStopCrossScreen = true;
    LoginUtil loginUtil;
    public String mUid="";
    public String mToken="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);
        setContentView(getContentLayoutRes());

        SwipeBackHelper.getCurrentPage(this)//获取当前页面
                .setSwipeBackEnable(true)//设置是否可滑动
                .setSwipeEdge(200)//可滑动的范围。px。200表示为左边200px的屏幕
                .setSwipeEdgePercent(0.2f)//可滑动的范围。百分比。0.2表示为左边20%的屏幕
                .setSwipeSensitivity(0.5f)//对横向滑动手势的敏感程度。0为迟钝 1为敏感
                .setScrimColor(Color.TRANSPARENT)//底层阴影颜色
                .setClosePercent(0.8f)//触发关闭Activity百分比
                .setSwipeRelateEnable(false)//是否与下一级activity联动。默认是
                .setSwipeRelateOffset(500)//activity联动时的偏移量。默认500px。
                .addListener(new SwipeListener() {//滑动监听
                    @Override
                    public void onScroll(float percent, int px) {//滑动的百分比与距离
                    }
                    @Override
                    public void onEdgeTouch() {//当开始滑动
                    }
                    @Override
                    public void onScrollToClose() {//当滑动关闭
                    }
                });

        if (isStopCrossScreen) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        StatusBarUtil.setTransparentForImageView(this, null);
        App.addActivity(this);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        setContentView(View.inflate(this, layoutResID, null));
    }

    @Override
    public void setContentView(View view) {
        SwipeBackHelper.onCreate(this);
        ll_base_layout = (LinearLayout) findViewById(R.id.ll_base_layout);
        if (ll_base_layout != null) {
            ll_base_layout.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            unbinder = ButterKnife.bind(this);
            my_titlebar.setStatueBarColor(getResources().getColor(App.mainColor));
            initView();
            initHttpData();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        SwipeBackHelper.onPostCreate(this);
    }

    /**
     * 如果用户已登录，获取用户id
     */
    public boolean getUserLogin() {
        if (!DataSafeUtils.isEmpty(SPUtil.getUserDataModel())) {
            mUid = SPUtil.getUserDataModel().getData().getUid();
            mToken = SPUtil.getUserDataModel().getData().getToken();
            return true;
        }else{
            return false;
        }
    }

    public boolean isLogin() {
        if (!DataSafeUtils.isEmpty(SPUtil.getUserDataModel())) {
            mUid = SPUtil.getUserDataModel().getData().getUid();
            mToken = SPUtil.getUserDataModel().getData().getToken();
            return true;
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("isfrom", "share");
            startActivity(intent);
            return false;
        }
    }



    private Dialog mDialog;

    //     显示dialog
    public void showDialog() {
        try {
            if (mDialog == null) {
                mDialog = DialogUtil.createLoadingDialog(this);
            }
            mDialog.show();
        } catch (Exception e) {
        }
    }

    // 隐藏dialog
    public void dismissDialog() {
        try {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
        } catch (Exception e) {
        }
    }

    /**
     * 隐藏titleBar
     */
    protected void hideTitleBar() {

        if (ll_base_layout != null) {
            my_titlebar.setVisibility(View.GONE);
        }
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setMyTitle(String title) {

        if (ll_base_layout != null) {
            if (my_titlebar != null) {
                my_titlebar.setTittle(title);
            }
        }

    }

    /**
     * 设置通知栏颜色
     *
     * @param color
     */
    protected void setStatueBarColor(String color) {
        if (ll_base_layout != null) {
            if (my_titlebar != null)
                my_titlebar.setStatueBarColor(Color.parseColor(color));
        }

    }


    /**
     * 对TitleBar进行复杂的操作可以拿到对象进行操作
     * 获取MyTititleBarView
     *
     * @return
     */
    protected MyTitleBarView getMyTitleBarView() {
        return my_titlebar;
    }

    /**
     * 是否显示返回键
     *
     * @param isVisiable
     */
    protected void setIsVisiableBack(boolean isVisiable) {
        my_titlebar.isVisiableBack(isVisiable);
    }


    /**
     * 移除activity
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        App.removeActivity(activity);
    }


    /**
     * 获取activity布局
     *
     * @return
     */
    protected abstract int getContentLayoutRes();

    /**
     * 初始化控件
     */
    protected abstract void initView();


    /**
     * 联网请求
     */
    protected abstract void initHttpData();


    /**
     * 显示吐司
     *
     * @param text
     */
    public void showToast(String text) {
        CommentUtil.showSingleToast(text);
    }


    /**
     * @param cls 要启动的activity
     */
    public void startActivity(Class<?> cls) {
        CommentUtil.startActivity(this, cls);
    }


    /**
     * 是否禁止横屏
     *
     * @param isStopCrossScreen
     */
    protected void setStopCrossScreen(boolean isStopCrossScreen) {
        this.isStopCrossScreen = isStopCrossScreen;
    }

    /**
     * 设置是否是MainActivity
     *
     * @param isMainActivity
     */
    protected void setMainActivity(boolean isMainActivity) {
        this.isMainActivity = isMainActivity;
    }

    /**
     * 广告跳转到浏览器
     *
     * @param url
     */
    public void startAdvWevView(String url) {
        Intent intent = new Intent();
        intent.setData(Uri.parse(url));//Url 就是你要打开的网址
        intent.setAction(Intent.ACTION_VIEW);
        this.startActivity(intent); //启动浏览器
    }

    /**
     * 开启一个网页
     *
     * @param url
     */
    public void startWebView(String url) {
        Intent intent = new Intent(this, WebviewActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }

    public void showActivity(String type, String id, String url) {
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        if ("1".equals(type)) {//
            startWebView(url);
        } else if ("2".equals(type)) {//
            startAdvWevView(url);
        } else if ("3".equals(type)) {//
            startActivity(RegisterActivity.class);
        } else if ("4".equals(type)) {//
            startActivity(LoginActivity.class);
        } else if ("5".equals(type)) {
            startActivity(ChangePwdActivity.class);
        } else if ("6".equals(type)) {//
            startActivity(ForgetPwdActivity.class);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (isMainActivity) {
                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    showToast("再按一次退出程序");
                    exitTime = System.currentTimeMillis();
                } else {
                    if (loginUtil != null) {
                        loginUtil.cancelDaojishi();
                    }
                    App.removeAllActivity();
                    removeActivity(this);//将该Activity从集合中移除
                    System.exit(0);//彻底关闭应用
                }
                return true;
            } else {
                removeActivity(this);
            }
        }
        return super.onKeyDown(keyCode, event);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        SwipeBackHelper.onDestroy(this);
    }

}