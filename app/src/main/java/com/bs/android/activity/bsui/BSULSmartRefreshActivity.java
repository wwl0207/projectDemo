package com.bs.android.activity.bsui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bs.android.R;
import com.bs.android.activity.BaseActivity;
import com.bs.android.http.Callback;
import com.bs.android.http.HttpUtils;
import com.bs.android.http.JsonUtils;
import com.bs.android.model.ShopListVO;
import com.bs.android.utils.CommentUtil;
import com.bs.android.utils.DataSafeUtils;
import com.bs.android.utils.NetworkUtil;
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
 * created by WWL on 2019/9/10 0010:09
 * 刷新控件以及网络加载几种情况
 */
public class BSULSmartRefreshActivity extends BaseActivity implements Callback {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.empty_img)
    ImageView emptyImg;
    @BindView(R.id.empty_textview)
    TextView emptyTextview;
    @BindView(R.id.no_content)
    RelativeLayout noContent;
    @BindView(R.id.footer)
    ClassicsFooter footer;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    int page = 1;
    ShopAdapter mShopAdapter;
    List<ShopListVO.DataBean.ListBean> mList = new ArrayList<>();
    ShopListVO dataBean = new ShopListVO();

    private Map<View, Broccoli> mViewPlaceholderManager = new HashMap<>();
    private Map<View, Runnable> mTaskManager = new HashMap<>();
    private String mUrl = "http://www.11111.com";

    @Override
    protected int getContentLayoutRes() {
        return R.layout.bsui_smartrefresh_layout;
    }

    @Override
    protected void initView() {
        setMyTitle("SmarRefresh刷新控件与数据加载几种状态");

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
        showDialog();
        initAdapter(mList);
    }


    @Override
    protected void initHttpData() {
        dataBean = getShopListData();
        HttpUtils.POST(mUrl, this);
    }

    private ShopListVO getShopListData() {
        ShopListVO vo = new ShopListVO();
        vo.setCode("200");
        vo.setDesc("成功");
        vo.setMsg("success");
        ShopListVO.DataBean dataBean = new ShopListVO.DataBean();
        dataBean.setCount("3");
        dataBean.setPage("1");
        List<ShopListVO.DataBean.ListBean> listBeans = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ShopListVO.DataBean.ListBean bean = new ShopListVO.DataBean.ListBean();
            bean.setAddress("武汉市洪山村水利路珞珈学院");
            bean.setCount("4" + i);
            bean.setDistance("200" + i + "km");
            bean.setImage("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=15576430,1042561804&fm=26&gp=0.jpg");
            bean.setLatitude("112.30393984");
            bean.setLongitude("100.20310321");
            bean.setSales("40" + i);
            bean.setShop_id("4");
            bean.setTitle("数据加载" + (i + 5));
            listBeans.add(bean);
        }
        dataBean.setList(listBeans);
        vo.setData(dataBean);
        return vo;
    }


    private void initAdapter(List<ShopListVO.DataBean.ListBean> mList) {
        mShopAdapter = new ShopAdapter(R.layout.shoplist_item_view, mList);
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.no_content)
    public void onViewClicked() {
    }

    @Override
    public void onStarted() {

    }

    @Override
    public void onSucceed(String json, String code, Object data) {
        if (!DataSafeUtils.isEmpty(data)) {
//            ShopListVO model = (ShopListVO) data;
            if (null != recyclerview) recyclerview.setVisibility(View.VISIBLE);
            if (null != noContent) noContent.setVisibility(View.GONE);
            mList = dataBean.getData().getList();
            if (page == 1) {
                mShopAdapter.setNewData(dataBean.getData().getList());
            } else {
                if (null != mShopAdapter) {
                    mShopAdapter.addData(dataBean.getData().getList());
                }
            }

            if (dataBean.getData().getPage().equals(dataBean.getData().getCount())) {
                if (refreshLayout != null) {
                    refreshLayout.finishLoadMoreWithNoMoreData();
                    footer.onFinish(refreshLayout, true);
                    refreshLayout.setNoMoreData(true);
                }
            } else {
                if (refreshLayout != null) {
                    refreshLayout.setEnableLoadMore(true);
                    refreshLayout.setNoMoreData(false);
                    footer.onFinish(refreshLayout, false);
                }
            }
        }
    }

    @Override
    public void onOtherStatus(String json, String code) {
        if (code.equals("300")) {
            if (page == 1) {
                if (null != recyclerview) recyclerview.setVisibility(View.GONE);
                if (null != noContent) noContent.setVisibility(View.VISIBLE);
            }
            if (refreshLayout != null) {
                refreshLayout.finishLoadMoreWithNoMoreData();
                footer.onFinish(refreshLayout, true);
                refreshLayout.setNoMoreData(true);

            }
        } else {
            String desc = JsonUtils.getSinglePara(json, "desc");
            CommentUtil.showSingleToast(desc);
        }
    }

    @Override
    public void onFailed(Throwable e) {
        dismissDialog();
        if (refreshLayout != null) {
            refreshLayout.finishLoadMore();
            refreshLayout.finishRefresh();
        }
        if (!NetworkUtil.isNetworkConnected(this)) {
            if (recyclerview != null)
                recyclerview.setVisibility(View.GONE);
            if (noContent != null) {
                noContent.setVisibility(View.VISIBLE);
                emptyTextview.setText("网络连接异常");
                emptyImg.setImageResource(R.drawable.no_network_img);
                noContent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog();
                        mUrl = "http://jinlemall.net/routine/publics/index";
                        page = 1;
                        initHttpData();
                    }
                });
            }
        } else {
            if (recyclerview != null)
                recyclerview.setVisibility(View.GONE);
            if (noContent != null) {
                noContent.setVisibility(View.VISIBLE);
                emptyTextview.setText("数据异常,点击重试");
                emptyImg.setImageResource(R.drawable.error_content);
                noContent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog();
                        mUrl = "http://jinlemall.net/routine/publics/index";
                        page = 1;
                        initHttpData();
                    }
                });
            }
        }
    }

    @Override
    public void onFinish() {
        if (refreshLayout != null) {
            refreshLayout.finishLoadMore();
            refreshLayout.finishRefresh();
        }
        dismissDialog();

    }


    public class ShopAdapter extends BaseQuickAdapter<ShopListVO.DataBean.ListBean, BaseViewHolder> {
        public ShopAdapter(int layoutResId, @Nullable List<ShopListVO.DataBean.ListBean> data) {
            super(layoutResId, data);
        }

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
                    .setDrawable(new BroccoliGradientDrawable(Color.parseColor("#DDDDDD"),
                            Color.parseColor("#CCCCCC"), 0, 1000, new LinearInterpolator()))
                    .build());
            broccoli.addPlaceholder(new PlaceholderParameter.Builder()
                    .setView(hepler.getView(R.id.shop_distance))
                    .setDrawable(new BroccoliGradientDrawable(Color.parseColor("#DDDDDD"),
                            Color.parseColor("#CCCCCC"), 0, 1000, new LinearInterpolator()))
                    .build());
            broccoli.addPlaceholder(new PlaceholderParameter.Builder()
                    .setView(hepler.getView(R.id.shop_sales))
                    .setDrawable(new BroccoliGradientDrawable(Color.parseColor("#DDDDDD"),
                            Color.parseColor("#CCCCCC"), 0, 1000, new LinearInterpolator()))
                    .build());
            broccoli.addPlaceholder(new PlaceholderParameter.Builder()
                    .setView(hepler.getView(R.id.shop_img))
                    .setDrawable(new BroccoliGradientDrawable(Color.parseColor("#DDDDDD"),
                            Color.parseColor("#CCCCCC"), 0, 1000, new LinearInterpolator()))
                    .build());
            broccoli.show();


            //delay to show the data
            Runnable task = mTaskManager.get(hepler.itemView);
            if (task == null) {
                final Broccoli finalBroccoli = broccoli;
                task = new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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
                            hepler.setText(R.id.shop_sales, Html.fromHtml("共<font color=#ff0000>3</font>件商品     销量" + item.getSales() + ""));
                        }


                        ImageView imgs= (ImageView) hepler.getView(R.id.shop_img);
                        ImageAnimator(imgs);//图片加载动画
                        Glide.with(mContext).load(item.getImage()).into(imgs);
                    }
                };
                mTaskManager.put(hepler.itemView, task);
            } else {
                hepler.itemView.removeCallbacks(task);
            }
            hepler.itemView.postDelayed(task, 2000);

        }
    }

    /**
     * 图片加载动画
     * @param imgs
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void ImageAnimator(ImageView imgs) {
        final int width = imgs.getMeasuredWidth()/2;
        final int height = imgs.getMeasuredHeight()/2;
        final float radius = (float) Math.sqrt(width * width + height * height);
        //主要代码
        Animator animator = ViewAnimationUtils.createCircularReveal(imgs, width , height , 0, radius);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //mImageView.setVisibility(View.GONE);
            }
        });
        animator.setDuration(2000);
        animator.start();
    }
}
