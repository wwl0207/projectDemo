package com.bs.android.activity.bsui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.bs.android.R;
import com.bs.android.activity.BaseActivity;
import com.bs.android.adapter.viewpager.MyFragmentAdapter;
import com.bs.android.fragment.SimpleCardFragment;
import com.bs.android.model.TabEntity;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * created by WWL on 2019/9/10 0010:09
 * Tablayout几种常见方式
 */
public class BSULTabLayoutActivity extends BaseActivity {
    @BindView(R.id.tablayout1)
    CommonTabLayout mTablayout;
    @BindView(R.id.tablayout2)
    SegmentTabLayout mTablayout2;
    @BindView(R.id.tablayout3)
    SlidingTabLayout mTablayout3;
    @BindView(R.id.tablayout4)
    SlidingTabLayout mTablayout4;
    @BindView(R.id.tab_viewpager)
    ViewPager mViewPager;

    private ArrayList<Fragment> mFragments = new ArrayList<>();

    private String[] mTitles = {"首页", "消息", "联系人", "更多","首页"};
    private String[] mTitles1 = {"首页", "消息", "联系人", "更多","首页","首页", "消息", "联系人", "更多","首页"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    @Override
    protected int getContentLayoutRes() {
        return R.layout.bsul_tablayout_layout;
    }

    @Override
    protected void initView() {
        setMyTitle("TabLayout最常用的几种效果");
        for (String title : mTitles) {
            mFragments.add(SimpleCardFragment.getInstance("Switch ViewPager " + title));
        }
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i]));
        }

        mViewPager.setAdapter(new MyFragmentAdapter(this.getSupportFragmentManager(), mFragments, mTabEntities));
        TabSelect();
    }

    @Override
    protected void initHttpData() {

    }


    private void TabSelect() {
        //TODO：CommonTabLayout  优点：可以有回弹动画效果  缺点，分类多了，显示不了，不能水平滑动显示
        //TODO: SlidingTabLayout 优点：可以实现多个分类栏目，可以滑动显示多个，缺点：没有回弹效果，但是呢，有保持和字体宽度一样的样式效果
        mTablayout.setTabData(mTabEntities);
        mTablayout2.setTabData(mTitles);

        if (mTabEntities.size()<5){
            mTablayout3.setTabSpaceEqual(true);//是固定

        }else {
            mTablayout3.setTabSpaceEqual(false);//是固定
        }
        mTablayout3.setViewPager(mViewPager,mTitles);
        mTablayout4.setViewPager(mViewPager,mTitles);

        mTablayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {//显示未读消息
//                if (position == 0) {
////                    mTablayout.showMsg(0, mRandom.nextInt(100) + 1);
//                }
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTablayout.setCurrentTab(position);
                mTablayout2.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setCurrentItem(0);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
