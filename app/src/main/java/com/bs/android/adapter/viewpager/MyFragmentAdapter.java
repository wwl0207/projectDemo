package com.bs.android.adapter.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.flyco.tablayout.listener.CustomTabEntity;

import java.util.ArrayList;

/**
 * Created by admin on 2017/12/21.
 */

public class MyFragmentAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private final ArrayList<CustomTabEntity> mTitles;

    public MyFragmentAdapter(FragmentManager fm, ArrayList<Fragment> mFragments, ArrayList<CustomTabEntity> mTitles) {
        super(fm);
        this.mFragments = mFragments;
        this.mTitles = mTitles;
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position).getTabTitle();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }
}
