package com.bs.android.activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;

import com.bs.android.R;
import com.bs.android.constant.FragmentConsts;
import com.bs.android.customview.radiobutton.MyRadioButton;
import com.bs.android.fragment.TabFourFragment;
import com.bs.android.fragment.TabOneFragment;
import com.bs.android.fragment.TabThreeFragment;
import com.bs.android.fragment.TabTwoFragment;
import com.bs.android.model.EventBusModel;
import com.jude.swipbackhelper.SwipeBackHelper;
import com.stephentuso.welcome.WelcomeHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.net.URL;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.rg_main)
    RadioGroup rg_main;

    @BindView(R.id.rb_index01)
    MyRadioButton rbIndex01;

    @BindView(R.id.rb_index02)
    MyRadioButton rbIndex02;

    @BindView(R.id.rb_index03)
    MyRadioButton rbIndex03;

    @BindView(R.id.rb_index04)
    MyRadioButton rbIndex04;

    public Fragment currentFragment = new Fragment();
    private TabOneFragment tabOneFragment;
    private TabTwoFragment tabTwoFragment;
    private TabThreeFragment tabThreeFragment;
    private TabFourFragment tabFourFragment;

    private int currentPageIndex = 0;
    public boolean myFragmentSelected = false;
    private static final int COMPLETED = 0;
    private WelcomeHelper sampleWelcomeScreen;
    @Override
    protected int getContentLayoutRes() {
        return R.layout.activity_main;

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeBackHelper.getCurrentPage(this)//获取当前页面
                .setSwipeBackEnable(false)//设置是否可滑动
                .setSwipeEdge(200)//可滑动的范围。px。200表示为左边200px的屏幕
                .setSwipeEdgePercent(0.2f)//可滑动的范围。百分比。0.2表示为左边20%的屏幕
                .setSwipeSensitivity(0.5f)//对横向滑动手势的敏感程度。0为迟钝 1为敏感
                .setScrimColor(Color.TRANSPARENT)//底层阴影颜色
                .setClosePercent(0.8f)//触发关闭Activity百分比
                .setSwipeRelateEnable(true)//是否与下一级activity联动。默认是
                .setSwipeRelateOffset(500)//activity联动时的偏移量。默认500px。
                .addListener(null);

//        sampleWelcomeScreen = new WelcomeHelper(this, WelcomeGuideActivity.class);
//        sampleWelcomeScreen.show(savedInstanceState);
        if (savedInstanceState != null) {

            /**
             * 恢复fragment状态
             */
            FragmentManager supportFragmentManager = getSupportFragmentManager();
            TabOneFragment tabOneFragment = (TabOneFragment) supportFragmentManager.getFragment(savedInstanceState, FragmentConsts.FRAGMENT01);
            if (tabOneFragment != null) {
                this.tabOneFragment = tabOneFragment;
            } else {
                this.tabOneFragment = new TabOneFragment();
            }

            TabTwoFragment tabTwoFragment = (TabTwoFragment) getSupportFragmentManager().getFragment(savedInstanceState, FragmentConsts.FRAGMENT02);
            if (tabTwoFragment != null) {
                this.tabTwoFragment = tabTwoFragment;
            } else {
                this.tabTwoFragment = new TabTwoFragment();
            }

            TabThreeFragment tabThreeFragment = (TabThreeFragment) getSupportFragmentManager().getFragment(savedInstanceState, FragmentConsts.FRAGMENT03);
            if (tabThreeFragment != null) {
                this.tabThreeFragment = tabThreeFragment;
            } else {
                this.tabThreeFragment = new TabThreeFragment();
            }

            TabFourFragment tabFourFragment = (TabFourFragment) getSupportFragmentManager().getFragment(savedInstanceState, FragmentConsts.FRAGMENT04);
            if (tabFourFragment != null) {
                this.tabFourFragment = tabFourFragment;
            } else {
                this.tabFourFragment = new TabFourFragment();
            }

            currentPageIndex = savedInstanceState.getInt(FragmentConsts.FRAGMENT_PAGE_INDEX, 0);

        } else {

            /**
             * 初始化fragment
             */

            tabOneFragment = new TabOneFragment();
            tabTwoFragment = new TabTwoFragment();
            tabThreeFragment = new TabThreeFragment();
            tabFourFragment = new TabFourFragment();

        }

        hideTitleBar();
        mainradioCheck();
        switchRadioButton(currentPageIndex);

//        /**
//         * 获取底部导航栏图标
//         */
//        GlobalModel globalModel = SPUtil.getGlobalModel();
//        GlobalModel.DataBean globalModelData = globalModel.getData();
//
//        List<MainIconVO.DataBean> dataData = globalModelData.getBottom_icon();
//        if (dataData != null) {
//            if (dataData.size() > 0) {
//                textTv.clear();
//                textTv.addAll(dataData);
//                getMainUI(dataData);
//            }
//        }

        /**
         * 版本更新
         */

//        if (globalModel != null) {
//            GlobalModel.DataBean modelData = globalModel.getData();
//            GlobalModel.DataBean.VersionBean.AndroidVersionBean android_version = modelData.getVersion().getAndroid_version();
//            String build = android_version.getBuild();
//            if (CommentUtil.getVersionCode(this) < Integer.parseInt(build)) {
//
//                boolean isForce = false;
//                if ("1".equals(android_version.getIsforce())) {
//                    isForce = true;
//                } else {
//                    isForce = false;
//                }
//                CommentUtil.updateVersion(this, getString(R.string.app_name), android_version.getContent(), android_version.getDownload(), isForce);
//
//            } else {
//                /**
//                 * 广告
//                 */
//                if (App.isShowAd) {
//                    if (globalModel != null) {
//                        GlobalModel.DataBean globalData = globalModel.getData();
//                        String notice = globalData.getNotice();
//                        if (!TextUtils.isEmpty(notice)) {
//                            CommentUtil.showMainAdDialog(this, notice, new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    App.isShowAd = false;
//                                }
//                            });
//
//                        }
//
//                    }
//                }
//            }
//        }

    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        setMainActivity(true);

    }


    /**
     * 切换底部菜单
     *
     * @param index
     */
    private void switchRadioButton(int index) {
        myFragmentSelected = false;
        switch (index) {

            case 0:
                currentPageIndex = 0;
                switchFragment(tabOneFragment);
                setRadioButtonType(0);
                break;

            case 1:
                currentPageIndex = 1;
                switchFragment(tabTwoFragment);
                setRadioButtonType(1);
                break;

            case 2:

                currentPageIndex = 2;
                switchFragment(tabThreeFragment);
                setRadioButtonType(2);
                break;

            case 3:

                currentPageIndex = 3;
                switchFragment(tabFourFragment);
                setRadioButtonType(3);
                break;

        }


    }


    /**
     * 切换fragment
     *
     * @param baseFragment
     */
    private void switchFragment(Fragment baseFragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (currentFragment != baseFragment) {

            fragmentTransaction.hide(currentFragment);
            currentFragment = baseFragment;
            if (!baseFragment.isAdded()) {
                fragmentTransaction.add(R.id.framelayout_main, baseFragment)
                        .hide(baseFragment)
                        .show(baseFragment)
                        .commitAllowingStateLoss();
            } else {
                fragmentTransaction.show(baseFragment).commitAllowingStateLoss();
            }

        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
//        sampleWelcomeScreen.onSaveInstanceState(outState);
        if (tabOneFragment != null && tabOneFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, FragmentConsts.FRAGMENT01, tabOneFragment);
        }


        if (tabTwoFragment != null && tabTwoFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, FragmentConsts.FRAGMENT02, tabTwoFragment);
        }
        if (tabThreeFragment != null && tabThreeFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, FragmentConsts.FRAGMENT03, tabThreeFragment);
        }
        if (tabFourFragment != null && tabFourFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, FragmentConsts.FRAGMENT04, tabFourFragment);
        }

        outState.putInt(FragmentConsts.FRAGMENT_PAGE_INDEX, currentPageIndex);
        super.onSaveInstanceState(outState);
    }

    public void setRadioButtonType(int type) {
//        rg_main.setBackgroundColor(getResources().getColor(R.color.backgroud_color01));
//        if (textTv.size()>4) {
//            rb_main.setText(textTv.get(0).getTitle());
//            rb_video.setText(textTv.get(1).getTitle());
//            rb_playlist.setText(textTv.get(2).getTitle());
//            rb_find.setText(textTv.get(3).getTitle());
//            rb_my.setText(textTv.get(4).getTitle());
//        }
//        if (SelectImg.size() > 4 && unSelectImg.size() > 4) {
//            if (type == 0) {
//                rb_main.setRbDrawableTop(SelectImg.get(0));
//                rb_video.setRbDrawableTop(unSelectImg.get(1));
//                rb_playlist.setRbDrawableTop(unSelectImg.get(2));
//                rb_find.setRbDrawableTop(unSelectImg.get(3));
//                rb_my.setRbDrawableTop(unSelectImg.get(4));
//                rb_main.setTextColor(Color.parseColor(textTv.get(0).getColor_select()));
//                rb_video.setTextColor(Color.parseColor(textTv.get(1).getColor()));
//                rb_playlist.setTextColor(Color.parseColor(textTv.get(2).getColor()));
//                rb_find.setTextColor(Color.parseColor(textTv.get(3).getColor()));
//                rb_my.setTextColor(Color.parseColor(textTv.get(4).getColor()));
//            } else if (type == 1) {
//                rb_main.setRbDrawableTop(unSelectImg.get(0));
//                rb_video.setRbDrawableTop(SelectImg.get(1));
//                rb_playlist.setRbDrawableTop(unSelectImg.get(2));
//                rb_find.setRbDrawableTop(unSelectImg.get(3));
//                rb_my.setRbDrawableTop(unSelectImg.get(4));
//                rb_main.setTextColor(Color.parseColor(textTv.get(0).getColor()));
//                rb_video.setTextColor(Color.parseColor(textTv.get(1).getColor_select()));
//                rb_playlist.setTextColor(Color.parseColor(textTv.get(2).getColor()));
//                rb_find.setTextColor(Color.parseColor(textTv.get(3).getColor()));
//                rb_my.setTextColor(Color.parseColor(textTv.get(4).getColor()));
//            }
//        }


    }

    @Override
    protected void initHttpData() {

    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == COMPLETED) {
//                setRadioButtonType(0);
//                switchRadioButton(currentPageIndex);
//                mainradioCheck();
            }

        }
    };

    private void mainradioCheck() {
        rg_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                View checked = group.findViewById(checkedId);
                int index = group.indexOfChild(checked);
                switchRadioButton(index);
            }
        });
    }

//    private void getMainUI(final List<MainIconVO.DataBean> modelData) {
//        unSelectImg = new ArrayList<>();
//        SelectImg = new ArrayList<>();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    for (int i = 0; i < modelData.size(); i++) {
//                        Drawable drawable = loadImageFromNetwork(modelData.get(i).getPic());
//                        Drawable drawableselect = loadImageFromNetwork(modelData.get(i).getPic_select());
//                        unSelectImg.add(drawable);
//                        SelectImg.add(drawableselect);
//                    }
//                } catch (Exception e) {
//
//                }
//
//                //处理完成后给handler发送消息 
//                Message msg = new Message();
//                msg.what = COMPLETED;
//                handler.sendMessage(msg);
//            }
//        }).start();
//
//    }

    private Drawable loadImageFromNetwork(String imageUrl) {
        Drawable drawable = null;

        // 可以在这里通过文件名来判断，是否本地有此图片
        try {
            drawable = Drawable.createFromStream(
                    new URL(imageUrl).openStream(), "src");
        } catch (Exception e) {
        }

        if (drawable == null) {
            Log.d("test", "null drawable");
        } else {
            Log.d("test", "not null drawable");
        }

        return drawable;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleEventBus(EventBusModel eventBusModel) {

        String code = eventBusModel.getCode();
        switch (code) {
            case "unLogin"://没登录
                Intent intent = new Intent(this, LoginActivity.class);
                intent.putExtra("isfrom", "share");
                startActivity(intent);
                break;
        }


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
