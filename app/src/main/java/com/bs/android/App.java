package com.bs.android;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.bs.android.http.BaseHttpConsts;
import com.bs.android.utils.ToastUtils;
import com.tencent.smtt.sdk.QbSdk;

import java.util.ArrayList;
import java.util.List;

public class App extends Application {
    public static int mainColor = com.bs.android.R.color.white;

    public static List<Activity> activityList = new ArrayList<Activity>();//所有activity的集合

    private boolean isDebug = true;

    public static App instance;//App对象的单例

    public static String HOST= BaseHttpConsts.HOST;

    @Override
    public void onCreate() {
        super.onCreate();

        //处理多dex
        MultiDex.install(this);

        if (instance == null) {
            instance = this;
        }

        //初始化日志
        initLogger();


       new Thread(new Runnable() {
           @Override
           public void run() {
               initRefresh();

               ToastUtils.init(true);

               //初始化腾讯x5
               initTencentX5();
           }
       }).start();


    }


    static {
        ClassicsFooter.REFRESH_FOOTER_NOTHING="数据已全部加载完";
    }


    /**
     * 初始化下拉刷新
     */
    private void initRefresh(){

        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                MaterialHeader materialHeader=new MaterialHeader(context);
                materialHeader.setColorSchemeResources(R.color.share_btn_save_color);
                layout.setEnableScrollContentWhenRefreshed(false);
                return materialHeader;//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                layout.setEnableScrollContentWhenLoaded(false);
                return new ClassicsFooter(context);
            }
        });

    }


    /**
     * 初始化logger
     */
    private void initLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(3)         // (Optional) How many method line to show. Default 2
                .methodOffset(0)        // (Optional) Hides internal method calls up to offset. Default 5
                .tag("logger")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {

                return isDebug;
            }
        });
    }

    /**
     * 初始化X5浏览器
     */
    private void initTencentX5() {
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Logger.i("X5内核加载成功");
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }

    /**
     * 添加activity集合
     *
     * @param activity
     */
    public static void addActivity(Activity activity) {
        if (!activityList.contains(activity)) {
            activityList.add(activity);
        }
    }


    /**
     * 移除activity
     *
     * @param activity
     */
    public static void removeActivity(Activity activity) {
        if (activityList.contains(activity)) {
            activityList.remove(activity);
            activity.finish();
        }
    }



    /**
     * 移除所有的activity
     */
    public static void removeAllActivity() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        activityList.clear();

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base);
    }

}
