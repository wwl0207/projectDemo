package com.bs.android.utils.webviewutil;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.bs.android.App;
import com.bs.android.activity.BaseActivity;
import com.bs.android.customview.webview.MyWebChromeClient;
import com.bs.android.utils.CommentUtil;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.io.File;

/**
 * @author WWL
 */

public class WebViewUtils {


    /**
     * 获取网页数据
     */
    public static void getWebViewPage(final BaseActivity baseActivity, final WebView webview, final String url, final MyWebChromeClient webChromeClient){

        webview.setWebChromeClient(new WebChromeClient());
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDatabaseEnabled(false);
        webSettings.setSupportMultipleWindows(true);

        //        打开页面时， 自适应屏幕：
        webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放 将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true);// 缩放至屏幕的大小
        webSettings.setLoadsImagesAutomatically(true);  //支持自动加载图片
        webSettings.setDomStorageEnabled(true); //不设置此 无法加载h5
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);  //支持js调用window.open方法


        webview.setWebChromeClient(webChromeClient);
        JsCallbackObject jsCallbackObject = new JsCallbackObject(baseActivity);
        webview.addJavascriptInterface(jsCallbackObject,"jsCallbackObject");
        webview.setWebViewClient(new WebViewClient(){

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (url.contains("mqqwpa:")) {
                    if (checkApkExist(baseActivity, "com.tencent.mobileqq")) {
                        baseActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    } else {
                        CommentUtil.showSingleToast("本机未安装QQ应用");
                    }

                }else {
                    view.loadUrl(url);
                    //如果不需要其他对点击链接事件的处理返回true，否则返回false
                }
                return true;
            }

            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
                String title = webview.getTitle();
                if (!TextUtils.isEmpty(title)) {
                    baseActivity.setMyTitle(title);
                }
            }
        });

        webview.loadUrl(url);



    }

        private static boolean checkApkExist(Context context, String packageName) {
            if (packageName == null || "".equals(packageName))
                return false;
            try {
                ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName,
                        PackageManager.GET_UNINSTALLED_PACKAGES);
                return true;
            } catch (PackageManager.NameNotFoundException e) {
                return false;
            }
        }



    public static void clearWebviewCache(){

        File file=new File(App.instance.getFilesDir().getParentFile(),"app_webview");
        CommentUtil.deleteFile(file);
        Log.i("logger","清理完成");

    }

    public interface WebviewLoadCallback{
        void onPageLoadFinish();
    }

}


