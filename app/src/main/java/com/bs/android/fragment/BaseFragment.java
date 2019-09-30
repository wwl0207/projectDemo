package com.bs.android.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.bs.android.R;
import com.bs.android.activity.BaseActivity;
import com.bs.android.activity.WebviewActivity;
import com.bs.android.customview.titlebar.MyTitleBarView;
import com.bs.android.utils.CommentUtil;
import com.bs.android.customview.titlebar.MyTitleBarView;
import com.bs.android.utils.CommentUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by admin on 2017/12/21.
 */

public abstract class BaseFragment extends Fragment {

    @BindView(R.id.my_titlebar)
    MyTitleBarView my_titlebar;

    private LinearLayout ll_base_fragment_layout;

    private Context context;

    protected BaseActivity mActivity;

    private Unbinder unbinder;



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
        mActivity= (BaseActivity) context;
    }


    /**
     * 获取fragment的context
     * @return
     */
    protected Context getFragmentContext(){
        return context;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_base,container,false);
        ll_base_fragment_layout = (LinearLayout) view.findViewById(R.id.ll_base_fragment_layout);
        View layoutView = inflater.inflate(getContentLayoutRes(), container, false);
        ll_base_fragment_layout.addView(layoutView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        initView(view);
        initHttpData();
    }

    public static void setWindowStatusBarColor(Activity activity, int colorResId) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(activity.getResources().getColor(colorResId));

                //底部导航栏
                window.setNavigationBarColor(activity.getResources().getColor(colorResId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取fragment布局
     * @return
     */
    protected abstract int getContentLayoutRes();


    /**
     * 初始化控件
     */
    protected abstract void initView(View view);


    /**
     * 联网请求
     */
    protected abstract void initHttpData();



    /**
     * 隐藏titleBar
     */
    protected void hideTitleBar(){

        if (ll_base_fragment_layout!=null) {
            my_titlebar.setVisibility(View.GONE);
        }
    }

    /**
     * 设置标题
     * @param title
     */
    protected void setMyTitle(String title){

        if (ll_base_fragment_layout!=null){
            my_titlebar.setTittle(title);
        }

    }

    /**
     * 广告跳转到浏览器
     * @param url
     */
    public void startAdvWevView(String url){
        Intent intent = new Intent();
        intent.setData(Uri.parse(url));//Url 就是你要打开的网址
        intent.setAction(Intent.ACTION_VIEW);
        this.startActivity(intent); //启动浏览器
    }
    /**
     * 开启一个网页
     * @param url
     */
    public void startWebView(String url){
        Intent intent=new Intent(mActivity,WebviewActivity.class);
        intent.putExtra("url",url);
        startActivity(intent);
    }

    /**
     * 是否显示返回键
     * @param isVisiable
     */
    protected void setIsVisiableBack(boolean isVisiable){
        my_titlebar.isVisiableBack(isVisiable);
    }


    /**
     * 显示吐司
     * @param text
     */
    protected void showToast(String text){
        CommentUtil.showSingleToast(text);
    }



    /**
     * 对TitleBar进行复杂的操作可以拿到对象进行操作
     * 获取MyTititleBarView
     * @return
     */
    protected MyTitleBarView getMyTitleBarView(){
        return my_titlebar;
    }


    /**
     * 移除当前fragment(必须加入当前回退栈才能移除)
     */
    protected void removeFragment(){
        AppCompatActivity activity= (AppCompatActivity) context;
        if (activity.getSupportFragmentManager().getBackStackEntryCount()!=0){
            activity.getSupportFragmentManager().popBackStack();
        }
    }



    /**
     * @param cls 要启动的activity
     */
    protected void startActivity(Class<?> cls){
        CommentUtil.startActivity(context,cls);
    }

    @Override
    public void onDestroy() {
        if (unbinder!=null){
            unbinder.unbind();
        }
        super.onDestroy();
    }
}