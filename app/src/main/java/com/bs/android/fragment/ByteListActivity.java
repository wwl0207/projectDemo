package com.bs.android.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bs.android.R;
import com.bs.android.activity.BaseActivity;
import com.bs.android.activity.MainActivity;
import com.bs.android.http.Callback;
import com.bs.android.http.ConstantUrl;
import com.bs.android.http.HttpUtils;
import com.bs.android.http.JsonUtils;
import com.bs.android.model.ShopListVO;
import com.bs.android.utils.CommentUtil;
import com.bs.android.utils.DataSafeUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.samlss.broccoli.Broccoli;
import me.samlss.broccoli.BroccoliGradientDrawable;
import me.samlss.broccoli.PlaceholderParameter;

/**
 * created by WWL on 2019/8/28 0028:09
 */
public class ByteListActivity extends BaseActivity {
    @BindView(R.id.back_layout)
    RelativeLayout backLayout;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.no_content)
    RelativeLayout noContent;
    @BindView(R.id.footer)
    ClassicsFooter footer;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    List<ShopListVO.DataBean.ListBean> mList = new ArrayList<>();
    BaseQuickAdapter<ShopListVO.DataBean.ListBean, BaseViewHolder> mShopAdapter;
    private Map<View, Broccoli> mViewPlaceholderManager = new HashMap<>();
    private Map<View, Runnable> mTaskManager = new HashMap<>();
    int page = 1;

    @Override
    protected int getContentLayoutRes() {
        return R.layout.bytelist_layout;
    }

    @Override
    protected void initView() {
        hideTitleBar();
        tvTitle.setText("占位符测试");

        initAdapter(mList);
        showDialog();
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

    private void initAdapter(List<ShopListVO.DataBean.ListBean> mList) {
        mShopAdapter = new BaseQuickAdapter<ShopListVO.DataBean.ListBean, BaseViewHolder>(R.layout.shoplist_item_view, mList) {
            @Override
            protected void convert(BaseViewHolder hepler, ShopListVO.DataBean.ListBean item) {
                Broccoli broccoli = mViewPlaceholderManager.get(hepler.itemView);
                if (broccoli == null) {
                    broccoli = new Broccoli();
                    mViewPlaceholderManager.put(hepler.itemView, broccoli);
                }

                broccoli.removeAllPlaceholders();
                broccoli.addPlaceholder(new PlaceholderParameter.Builder()
                        .setView(hepler.getView(R.id.shop_name))
                        .setDrawable(new BroccoliGradientDrawable(Color.parseColor("#DDDDDD"),
                                Color.parseColor("#CCCCCC"), 0, 1000, new LinearInterpolator()))
                        .build());
                broccoli.addPlaceholder(new PlaceholderParameter.Builder()
                        .setView(hepler.getView(R.id.shop_address))
                        .setDrawable(new BroccoliGradientDrawable(Color.parseColor("#eeeeee"),
                                Color.parseColor("#eeeeee"), 0, 1000, new LinearInterpolator()))
                        .build());
                broccoli.addPlaceholder(new PlaceholderParameter.Builder()
                        .setView(hepler.getView(R.id.shop_distance))
                        .setDrawable(new BroccoliGradientDrawable(Color.parseColor("#eeeeee"),
                                Color.parseColor("#eeeeee"), 0, 1000, new LinearInterpolator()))
                        .build());
                broccoli.addPlaceholder(new PlaceholderParameter.Builder()
                        .setView(hepler.getView(R.id.shop_sales))
                        .setDrawable(new BroccoliGradientDrawable(Color.parseColor("#eeeeee"),
                                Color.parseColor("#eeeeee"), 0, 1000, new LinearInterpolator()))
                        .build());
                broccoli.addPlaceholder(new PlaceholderParameter.Builder()
                        .setView(hepler.getView(R.id.shop_img))
                        .setDrawable(new BroccoliGradientDrawable(Color.parseColor("#eeeeee"),
                                Color.parseColor("#eeeeee"), 0, 1000, new LinearInterpolator()))
                        .build());
                broccoli.show();


                //delay to show the data
                Runnable task = mTaskManager.get(hepler.itemView);
                if (task == null) {
                    final Broccoli finalBroccoli = broccoli;
                    task = new Runnable() {
                        @Override
                        public void run() {
                            finalBroccoli.removeAllPlaceholders();
//                            if (mList.isEmpty()){
//                                return;
//                            }
                            if (!DataSafeUtils.isEmpty(item.getTitle())) {
                                hepler.setText(R.id.shop_name, item.getTitle());
                            } else {
                                hepler.setText(R.id.shop_name, "无店名");
                            }
                            if (!DataSafeUtils.isEmpty(item.getAddress())) {
                                hepler.setGone(R.id.addr_layout, true);
                                hepler.setText(R.id.shop_address, item.getAddress());
                            } else {
                                hepler.setGone(R.id.addr_layout, false);
                            }
                            if (!DataSafeUtils.isEmpty(item.getDistance())) {
                                hepler.setText(R.id.shop_distance, "距" + item.getDistance());
                            }
                            if (!DataSafeUtils.isEmpty(item.getCount())) {
                                hepler.setText(R.id.shop_sales, Html.fromHtml("共<font color=#ff0000>" + item.getCount() + "</font>件商品     销量" + item.getSales() + ""));
                            }
                            Glide.with(mContext).load("http://pic1.win4000.com/wallpaper/c/53cdd1f7c1f21.jpg").into((ImageView) hepler.getView(R.id.shop_img));
                        }
                    };
                    mTaskManager.put(hepler.itemView, task);
                } else {
                    hepler.itemView.removeCallbacks(task);
                }
                hepler.itemView.postDelayed(task, 500);

            }
        };
        recyclerview.setAdapter(mShopAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerview.setLayoutManager(manager);
    }

    @Override
    protected void initHttpData() {
        Map<String, String> map = new HashMap<>();
        map.put("cId", "2");
        map.put("keyword", "");
        map.put("latitude", "32.073661");
        map.put("longitude", "112.107987");
        map.put("page", "1");
        HttpUtils.POST(ConstantUrl.SHOPLIST, map, false, ShopListVO.class, new Callback<ShopListVO>() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onSucceed(String json, String code, ShopListVO model) {
                if (recyclerview != null) {
                    recyclerview.setVisibility(View.VISIBLE);
                }
                if (noContent != null) {
                    noContent.setVisibility(View.GONE);
                }
                mList = model.getData().getList();
                if (page == 1) {
                    mShopAdapter.setNewData(model.getData().getList());
                } else
                    mShopAdapter.addData(model.getData().getList());
            }


            @Override
            public void onOtherStatus(String json, String code) {
            }

            @Override
            public void onFailed(Throwable e) {
            }

            @Override
            public void onFinish() {
                dismissDialog();
                if (refreshLayout != null) {
                    refreshLayout.finishRefresh();
                    refreshLayout.finishLoadMore();
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.back_layout)
    public void onViewClicked() {
        this.finish();
    }
}
