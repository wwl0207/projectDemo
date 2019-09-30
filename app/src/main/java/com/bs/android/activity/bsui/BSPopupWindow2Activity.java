package com.bs.android.activity.bsui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.bs.android.R;
import com.bs.android.activity.BaseActivity;
import com.bs.android.model.DataBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import fj.dropdownmenu.lib.concat.DropdownI;
import fj.dropdownmenu.lib.ion.ViewInject;
import fj.dropdownmenu.lib.ion.ViewUtils;
import fj.dropdownmenu.lib.pojo.DropdownItemObject;
import fj.dropdownmenu.lib.utils.DropdownUtils;
import fj.dropdownmenu.lib.view.DropdownButton;
import fj.dropdownmenu.lib.view.DropdownColumnView;

/**
 * created by WWL on 2019/9/12 0012:09
 */
public class BSPopupWindow2Activity extends BaseActivity implements DropdownI.SingleRow, DropdownI.DoubleRow,
        DropdownI.ThreeRow {

    @ViewInject(R.id.btnType)
    @BindView(R.id.btnType)
    DropdownButton btnType;
    @ViewInject(R.id.btnAnimal)
    @BindView(R.id.btnAnimal)
    DropdownButton btnAnimal;
    @ViewInject(R.id.btnRegion)
    @BindView(R.id.btnRegion)
    DropdownButton btnRegion;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mask)
    View mask;
    @ViewInject(R.id.lvType)
    @BindView(R.id.lvType)
    DropdownColumnView lvType;
    @ViewInject(R.id.lvAnimal)
    @BindView(R.id.lvAnimal)
    DropdownColumnView lvAnimal;
    @ViewInject(R.id.lvRegion)
    @BindView(R.id.lvRegion)
    DropdownColumnView lvRegion;


    @Override
    protected int getContentLayoutRes() {
        return R.layout.bsui_popupwindow_layout;

    }


    @Override
    protected void initView() {
        DropdownUtils.init(this, mask);
        ViewUtils.injectViews(this, mask);

        setRecyclerView();

        //分类
        lvType.setSingleRow(this)
                .setSingleRowList(DataBean.getType(), -1)  //单列数据
                .setButton(btnType) //按钮
                .show();
        //动物
        lvAnimal.setDoubleRow(this)
                .setSingleRowList(DataBean.getAnimalSingle(), -1)//单列数据
                .setDoubleRowList(DataBean.getAnimalDouble(), -1)//双列数据
                .setButton(btnAnimal)          //按钮
                .show();
        //地区
        lvRegion.setThreeRow(this)
                .setSingleRowList(DataBean.getRegionProvince(), -1)  //单列数据
                .setDoubleRowList(DataBean.getRegionCity(), -1)//双列数据
                .setThreeRowList(DataBean.getRegionArea(), -1)//三列数据
                .setButton(btnRegion) //按钮
                .show();
    }

    @Override
    protected void initHttpData() {

    }


    public void setRecyclerView() {
        List<String> mData = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            mData.add("title" + i);
        }

        com.uitest.android.adapter.MyAdapter mAdapter = new com.uitest.android.adapter.MyAdapter(mData);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        // 设置adapter
        mRecyclerView.setAdapter(mAdapter);
    }


    /**
     * 单列表返回
     */
    @Override
    public void onSingleChanged(DropdownItemObject singleRowObject) {
        Log.d("类型",singleRowObject.getValue());
    }

    /**
     * 双列表返回
     */
    @Override
    public void onDoubleSingleChanged(DropdownItemObject singleRowObject) {
        Log.d("动物",singleRowObject.getValue());
    }

    @Override
    public void onDoubleChanged(DropdownItemObject doubleRowObject) {
        Log.d("动物子类",doubleRowObject.getValue());
    }

    /**
     * 三列表返回
     */
    @Override
    public void onThreeSingleChanged(DropdownItemObject singleRowObject) {
        Log.d("省",singleRowObject.getValue());
    }

    @Override
    public void onThreeDoubleChanged(DropdownItemObject doubleRowObject) {
        Log.d("市",doubleRowObject.getValue());
    }

    @Override
    public void onThreeChanged(DropdownItemObject threeRowObject) {
        Log.d("区",threeRowObject.getValue());
    }


}
