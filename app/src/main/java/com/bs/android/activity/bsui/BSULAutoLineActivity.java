package com.bs.android.activity.bsui;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bs.android.R;
import com.bs.android.activity.BaseActivity;
import com.bs.android.activity.SearchActivity;
import com.bs.android.customview.RadioGroupEx;
import com.bs.android.greendao.SearchHistoryModelDao;
import com.bs.android.model.SearchHistoryModel;
import com.bumptech.glide.load.resource.drawable.DrawableResource;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * created by WWL on 2019/9/15 0015:11
 * 自动换行控件
 */
public class BSULAutoLineActivity extends BaseActivity {
    @BindView(R.id.tag_flow_layout_history)
    TagFlowLayout tagFlowLayoutHistory;

    @BindView(R.id.food_tag_layout)
    RadioGroupEx addtv_layout;//
    private String mRiderTags = "";
    private List<SearchHistoryModel> historyDatas = new ArrayList<>();

    @Override
    protected int getContentLayoutRes() {
        return R.layout.bsui_autoline_view;
    }

    @Override
    protected void initView() {
        tagFlowLayoutHistory.setAdapter(historyAdapter);

        getSecondAutoLine();
    }

    @Override
    protected void initHttpData() {
        for (int i = 0; i < 20; i++) {
            SearchHistoryModel model = new SearchHistoryModel();
            model.setId(Long.parseLong(i + ""));
            if (i == 0)
                model.setSearchName("第一条");
            else if (i == 1)
                model.setSearchName("第二条数据");
            else if (i == 2)
                model.setSearchName("折东方嘉盛疯狂派送");
            else if (i == 3)
                model.setSearchName("手动阀品牌方配送抵扣佛山");
            else
                model.setSearchName("度搜if建瓯市");
            historyDatas.add(model);
        }
        historyAdapter.notifyDataChanged();
    }


    private TagAdapter<SearchHistoryModel> historyAdapter = new TagAdapter<SearchHistoryModel>(historyDatas) {
        @Override
        public View getView(FlowLayout parent, final int position, final SearchHistoryModel searchHistoryModel) {
            final LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(BSULAutoLineActivity.this).inflate(R.layout.item_flow_layou_history,
                    tagFlowLayoutHistory, false);
            ImageView iv_delete =linearLayout.findViewById(R.id.iv_delete);
            TextView tv_name = linearLayout.findViewById(R.id.tv_name);
            tv_name.setText(searchHistoryModel.getSearchName());

            tv_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    GradientDrawable drawable = new GradientDrawable();
//                    drawable.setCornerRadius(10);
//                    drawable.setColor(Color.parseColor("#ff9800"));
//                    tv_name.setBackground(drawable);

                    if (!mRiderTags.contains(tv_name.getText().toString().trim())) {
                    GradientDrawable drawable = new GradientDrawable();
                    drawable.setCornerRadius(10);
                    drawable.setColor(Color.parseColor("#ff9800"));
                    tv_name.setBackground(drawable);
                        mRiderTags = mRiderTags + "," + tv_name.getText().toString().trim();
                    } else if (mRiderTags.contains(tv_name.getText().toString().trim())) {
                        tv_name.setBackgroundResource(R.drawable.search_history_item_back);
                        String[] mRiders = mRiderTags.substring(1).split(",");
                        mRiderTags = "";
                        for (int i = 0; i < mRiders.length; i++) {
                            if (!mRiders[i].equals(tv_name.getText().toString().trim())) {
                                mRiderTags = mRiderTags + "," + mRiders[i];
                            }
                        }
                    }
                }
            });
            tv_name.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    iv_delete.setVisibility(View.VISIBLE);
                    return false;
                }
            });
            iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (historyDatas != null) {
                        historyDatas.remove(position);
                        historyAdapter.notifyDataChanged();
                    }
                }
            });
            return linearLayout;
        }
    };

    private void getSecondAutoLine() {
        ArrayList<String> mStringList = new ArrayList<>();
        mStringList.add("对方是否");
        mStringList.add("圣诞节哦是否");
        mStringList.add("时代峰峻哦三舅佛你倒是");
        mStringList.add("d水电费是否");
        mStringList.add("dfj开发票独守空房的");
        mStringList.add("京东方");
        mStringList.add("s都手动阀");
        mStringList.add("dfkos块豆腐饭");
        mStringList.add("d哦哦我");
        mStringList.add("wieo二");
            //遍历这个json格式的数组
            View mGridView;
            addtv_layout.removeAllViews();
            for (int i = 0; i < mStringList.size(); i++) {
                String string = mStringList.get(i);
                mGridView = getLayoutInflater().inflate(R.layout.bsui_tag_layout, null);
                final TextView tvs = (TextView) mGridView
                        .findViewById(R.id.write_detail_tag_tv);
                tvs.setText(string);
                addtv_layout.addView(mGridView, i);

                tvs.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (!mRiderTags.contains(tvs.getText().toString().trim())) {
                            GradientDrawable drawable = new GradientDrawable();
                            drawable.setCornerRadius(5);
                            drawable.setColor(Color.parseColor("#ff9800"));
                            drawable.setStroke(1,Color.parseColor("#ff9800"));
                            drawable.setBounds(15,8,15,8);
                            tvs.setBackground(drawable);
                            mRiderTags = mRiderTags + "," + tvs.getText().toString().trim();
                        } else if (mRiderTags.contains(tvs.getText().toString().trim())) {
                            tvs.setBackgroundResource(R.drawable.bsui_autoline_label_bg);
                            String[] mRiders = mRiderTags.substring(1).split(",");
                            mRiderTags = "";
                            for (int i = 0; i < mRiders.length; i++) {
                                if (!mRiders[i].equals(tvs.getText().toString().trim())) {
                                    mRiderTags = mRiderTags + "," + mRiders[i];
                                }
                            }
                        }
                    }
                });
            }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
