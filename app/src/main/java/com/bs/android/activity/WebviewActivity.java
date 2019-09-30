package com.bs.android.activity;

import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.bs.android.R;
import com.bs.android.customview.webview.MyWebChromeClient;
import com.bs.android.utils.webviewutil.WebViewUtils;
import com.tencent.smtt.sdk.WebView;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 打开普通网页的activity
 */
public class WebviewActivity extends BaseActivity {

    @BindView(R.id.webView)
    WebView webView;

    @Override
    protected int getContentLayoutRes() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void initView() {


        setMyTitle("");
        String url = getIntent().getStringExtra("url");
        if (!TextUtils.isEmpty(url)) {
            Log.i("logger",url);
            WebViewUtils.getWebViewPage(this, webView,url , new MyWebChromeClient());
        }

        getMyTitleBarView().setBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });

    }

    @Override
    protected void initHttpData() {
        getWindow().getDecorView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                ArrayList<View> outView = new ArrayList<View>();
                getWindow().getDecorView().findViewsWithText(outView,"QQ浏览器",View.FIND_VIEWS_WITH_TEXT);
                int size = outView.size();
                if(outView!=null&&outView.size()>0){
                    outView.get(0).setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            back();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }




    private void back(){

        if (webView!=null){

            if (webView.canGoBack()){
                webView.goBack();
            }else {
                removeActivity(this);
            }
        }else {
            removeActivity(WebviewActivity.this);
        }
    }
}
