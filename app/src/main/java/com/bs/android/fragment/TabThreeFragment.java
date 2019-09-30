package com.bs.android.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bs.android.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 */
public class TabThreeFragment extends BaseFragment {

    Unbinder unbinder;
    List<String> mList = new ArrayList<>();
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.no_content)
    RelativeLayout noContent;
    @BindView(R.id.footer)
    ClassicsFooter footer;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    int page = 1;

    @Override
    protected int getContentLayoutRes() {
        return R.layout.tab_three_fragment;
    }

    @Override
    protected void initView(View view) {
        hideTitleBar();

        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                initHttpData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                initHttpData();
            }
        });
    }

    @Override
    protected void initHttpData() {
        mList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            mList.add("第" + i + "行--------");
        }
//        TypedValue mTypedValue = new TypedValue();
//        mActivity.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
//        int mBackground = mTypedValue.resourceId;
        BaseQuickAdapter<String, BaseViewHolder> mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.text_tv, mList) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {

                helper.setText(R.id.tv, item);
//                helper.getView(R.id.tv).setBackgroundResource(mBackground);
            }
        };

        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));

        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
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

    @OnClick(R.id.no_content)
    public void onViewClicked() {
        page = 1;
        initHttpData();
    }
}
