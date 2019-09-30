package com.bs.android.activity.bsui;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.bs.android.R;
import com.bs.android.activity.BaseActivity;
import com.bs.android.adapter.BSUIExpandableAdapter;
import com.bs.android.model.ExpandListVO;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * created by WWL on 2019/9/10 0010:09
 * 列表伸缩
 */
public class BSULExpandListActivity extends BaseActivity {

    @BindView(R.id.expandlist)
    ExpandableListView expandableListView;

    private List<ExpandListVO.DataBean> mParentList = new ArrayList<>();
    BSUIExpandableAdapter mAdapter;
    @Override
    protected int getContentLayoutRes() {
        return R.layout.bsui_expandlist_layout;
    }

    @Override
    protected void initView() {
        setMyTitle("列表伸缩");

        mParentList = getDataList();


    }


    @Override
    protected void initHttpData() {
        mAdapter = new BSUIExpandableAdapter(this, mParentList);
        expandableListView.setAdapter(mAdapter);
        //设置分组的监听
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Toast.makeText(getApplicationContext(), mParentList.get(groupPosition).getParentName(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        //设置子项布局监听
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(getApplicationContext(), mParentList.get(groupPosition).getList().get(childPosition).getChildName(), Toast.LENGTH_SHORT).show();
                return true;

            }
        });

        //控制他只能打开一个组
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                int count = mAdapter.getGroupCount();
                for(int i = 0;i < count;i++){
                    if (i!=groupPosition){
                        expandableListView.collapseGroup(i);
                    }
                }
            }
        });
    }


    private List<ExpandListVO.DataBean> getDataList() {
        List<ExpandListVO.DataBean> mParentList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ExpandListVO.DataBean dataBean = new ExpandListVO.DataBean();
            dataBean.setParentId(i + "");
            dataBean.setParentName("父类名称" + i);
            List<ExpandListVO.DataBean.ListBean> mChildList = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                ExpandListVO.DataBean.ListBean listBean = new ExpandListVO.DataBean.ListBean();
                listBean.setChildId(j + "");
                listBean.setChildName(i + "子类名称" + j);
                mChildList.add(listBean);
            }
            dataBean.setList(mChildList);
            mParentList.add(dataBean);
        }
        return mParentList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
