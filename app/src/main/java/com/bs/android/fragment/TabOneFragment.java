package com.bs.android.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bs.android.R;
import com.bs.android.adapter.viewpager.MyFragmentAdapter;
import com.bs.android.model.TabEntity;
import com.bs.android.utils.CommentUtil;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.picture.android.PictureActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cece.com.bannerlib.callback.OnItemViewClickListener;
import cece.com.bannerlib.model.PicBean;

import static cece.com.bannerlib.BannerGetData.getBannerData;

/**
 * 主页mainfragment
 */
public class TabOneFragment extends BaseFragment implements OnItemViewClickListener {

    Unbinder unbinder;
    @BindView(R.id.tablayout1)
    CommonTabLayout mTablayout;
    @BindView(R.id.tab_viewpager)
    ViewPager mViewPager;
    @BindView(R.id.rl_banner)
    RelativeLayout rl_banner;
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    private String[] mTitles = {"首页", "消息", "联系人", "更多"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    @Override
    protected int getContentLayoutRes() {
        return R.layout.tab_one_fragment;
    }

    @Override
    protected void initView(View view) {
        hideTitleBar();

        for (String title : mTitles) {
            mFragments.add(SimpleCardFragment.getInstance("Switch ViewPager " + title));
        }
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i]));
        }

        mViewPager.setAdapter(new MyFragmentAdapter(getActivity().getSupportFragmentManager(), mFragments, mTabEntities));
        TabSelect();
    }


    private void TabSelect() {
        mTablayout.setTabData(mTabEntities);
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
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setCurrentItem(0);
    }

    private List<PicBean> getList() {
        List<PicBean> mList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            PicBean bean = new PicBean();
            bean.setId(i + "");
            if (i == 0)
                bean.setPic("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=180868167,273146879&fm=26&gp=0.jpg");
            else if (i == 1)
                bean.setPic("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1638695478,3359394321&fm=26&gp=0.jpg");
            else if (i == 2)
                bean.setPic("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3197648140,1059193869&fm=26&gp=0.jpg");
            else if (i == 3)
                bean.setPic("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=55649405,1452350630&fm=26&gp=0.jpg");
            else if (i == 4)
                bean.setPic("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3365093992,2526641564&fm=26&gp=0.jpg");
            else
                bean.setPic("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1486513396,554854054&fm=26&gp=0.jpg");
            bean.setTitle("");
            bean.setUrl("");
            mList.add(bean);
        }
        return mList;
    }

    /**
     * BannerLayout实现轮播图效果
     */
    private void setAdvAdapterData() {
        ViewGroup.LayoutParams params = rl_banner.getLayoutParams();
        params.width = CommentUtil.getDisplayWidth(mActivity);
        params.height = (int) ((int) (params.width * 0.3));
        rl_banner.setLayoutParams(params);
        List<PicBean> list = new ArrayList<>();
        list = getList();
        //两种效果 带滑动缩放效果以及圆角以及普通轮播图
        getBannerData(mActivity, this, rl_banner, list, true, Color.RED, Color.GRAY, 5, 5, 1);

    }

    @Override
    public void onItemClick(View view, PicBean bean) {
        ArrayList<String> mpiclist = new ArrayList<>();
        for (int i = 0; i < getList().size(); i++) {
            mpiclist.add(getList().get(i).getPic());
        }
        Toast.makeText(mActivity, "222", Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("list", mpiclist);
        bundle.putString("id", bean.getId());
        Intent intent = new Intent(mActivity, PictureActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        /**
         * 其他H5链接
         */
//        String h5url = bean.getUrl();
//        mActivity.startAdvWevView(h5url);
//        startActivity(BSPopupWindowActivity.class);
    }


    @Override
    protected void initHttpData() {
        setAdvAdapterData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
