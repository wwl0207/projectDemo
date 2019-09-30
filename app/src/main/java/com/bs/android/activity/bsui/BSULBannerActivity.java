package com.bs.android.activity.bsui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bs.android.R;
import com.bs.android.activity.BaseActivity;
import com.bs.android.utils.CommentUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cece.com.bannerlib.callback.OnItemViewClickListener;
import cece.com.bannerlib.model.PicBean;

import static cece.com.bannerlib.BannerGetData.getBannerData;

/**
 * created by WWL on 2019/9/10 0010:09
 * 轮播图
 */
public class BSULBannerActivity extends BaseActivity implements OnItemViewClickListener {
    @BindView(R.id.rl_banner)
    RelativeLayout rl_banner;
    @BindView(R.id.rl_banner1)
    RelativeLayout rlBanner1;
    List<PicBean> list = new ArrayList<>();

    @Override
    protected int getContentLayoutRes() {
        return R.layout.bsul_banner_layout;
    }

    @Override
    protected void initView() {
    setMyTitle("两种轮播图效果");

    }

    @Override
    protected void initHttpData() {
        setAdvAdapterData1();
        setAdvAdapterData2();
    }

    private List<PicBean> getList() {
        List<PicBean> mList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            PicBean bean = new PicBean();
            bean.setId(i + "");
            bean.setType(i + "");
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
            bean.setUrl("https://blog.csdn.net/m0_37135879/article/details/79159615");
            mList.add(bean);
        }
        return mList;
    }

    /**
     * BannerLayout实现轮播图效果1
     */
    private void setAdvAdapterData1() {
        ViewGroup.LayoutParams params = rl_banner.getLayoutParams();
        params.width = CommentUtil.getDisplayWidth(this);
        params.height = (int) ((int) (params.width * 0.5));
        rl_banner.setLayoutParams(params);

        list = getList();
        //两种效果 带滑动缩放效果以及圆角以及普通轮播图
        getBannerData(this, this, rl_banner, list, true, Color.RED, Color.GRAY, 5, 5, 1);

    }

    /**
     * BannerLayout实现轮播图效果2
     */
    private void setAdvAdapterData2() {
        ViewGroup.LayoutParams params = rlBanner1.getLayoutParams();
        params.width = CommentUtil.getDisplayWidth(this);
        params.height = (int) ((int) (params.width * 0.5));
        rlBanner1.setLayoutParams(params);
        list = getList();
        //两种效果 带滑动缩放效果以及圆角以及普通轮播图
        getBannerData(this, this, rlBanner1, list, false, Color.YELLOW, Color.GRAY, 8, 3, 0);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onItemClick(View view, PicBean bean) {
        String h5url = bean.getUrl();
        startAdvWevView(h5url);

//        this.showActivity(bean.getType(), bean.getId(), bean.getUrl());


//        ArrayList<String> mpiclist = new ArrayList<>();
//        for (int i = 0; i < getList().size(); i++) {
//            mpiclist.add(getList().get(i).getPic());
//        }
//        Toast.makeText(this, ""+bean.getId(), Toast.LENGTH_SHORT).show();
//        Bundle bundle = new Bundle();
//        bundle.putStringArrayList("list", mpiclist);
//        bundle.putString("id", bean.getId());
//        Intent intent = new Intent(this, PictureActivity.class);
//        intent.putExtras(bundle);
//        startActivity(intent);
    }

    @OnClick(R.id.back_layout)
    public void onViewClicked() {
        this.finish();
    }
}
