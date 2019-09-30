package com.bs.android.activity.bsui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.bs.android.R;
import com.bs.android.activity.BaseActivity;
import com.picture.android.PictureActivity;

import java.util.ArrayList;
import java.util.List;

import cece.com.bannerlib.model.PicBean;

/**
 * created by WWL on 2019/9/10 0010:09
 * 图片预览
 */
public class BSULPicturePreviewActivity extends BaseActivity {
    @Override
    protected int getContentLayoutRes() {
        return R.layout.picture_view_layout;
    }

    @Override
    protected void initView() {
        setMyTitle("自定义图片预览");

    }

    @Override
    protected void initHttpData() {
        ArrayList<String> mpiclist = getList();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("list", mpiclist);
        bundle.putString("id","1");
        Intent intent = new Intent(this, PictureActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);

        this.finish();
    }

    private ArrayList<String> getList() {
        ArrayList<String> mList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            mList.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=180868167,273146879&fm=26&gp=0.jpg");
            mList.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1638695478,3359394321&fm=26&gp=0.jpg");
            mList.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3197648140,1059193869&fm=26&gp=0.jpg");
            mList.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=55649405,1452350630&fm=26&gp=0.jpg");
            mList.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3365093992,2526641564&fm=26&gp=0.jpg");
            mList.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1486513396,554854054&fm=26&gp=0.jpg");
        }
        return mList;
    }
}
