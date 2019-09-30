package com.bs.android.activity;


import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bs.android.R;
import com.bs.android.customview.dialog.AlertDialog;
import com.bs.android.greendao.SearchHistoryModelDao;
import com.bs.android.model.SearchHistoryModel;
import com.bs.android.utils.CommentUtil;
import com.bs.android.utils.db.GreenDaoDBUtil;
import com.flyco.tablayout.SystemBarHelper;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 搜索界面
 */
public class SearchActivity extends BaseActivity {

    @BindView(R.id.ll_history_search)
    LinearLayout ll_history_search;

    @BindView(R.id.rl_more_history)
    RelativeLayout rl_more_history;

    @BindView(R.id.et_search_content)
    EditText et_search_content;

    @BindView(R.id.tag_flow_layout_history)
    TagFlowLayout tag_flow_layout_history;

//    @BindView(R.id.tag_flow_layout_hot)
//    TagFlowLayout tag_flow_layout_hot;

    private SearchHistoryModelDao searchHistoryModelDao;

    List<SearchHistoryModel> allHistoryModels = new ArrayList<>();
    private List<SearchHistoryModel> historyDatas = new ArrayList<>();

    @Override
    protected int getContentLayoutRes() {
        return R.layout.activity_search;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initView() {
        this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        SystemBarHelper.immersiveStatusBar(getWindow(), 0);
        hideTitleBar();

//        if (!DataSafeUtils.isEmpty(GreenDaoDBUtil.getDaoSession())) {
//            if (!DataSafeUtils.isEmpty(GreenDaoDBUtil.getDaoSession().getSearchHistoryModelDao())) {
                searchHistoryModelDao = GreenDaoDBUtil.getDaoSession().getSearchHistoryModelDao();
//            }
//        }

        initHistoryFlowLayout();
        searchHistoryDB();

    }

    /**
     * 初始化历史流式布局
     */
    private void initHistoryFlowLayout() {

        tag_flow_layout_history.setAdapter(historyAdapter);

    }

    private TagAdapter<SearchHistoryModel> historyAdapter = new TagAdapter<SearchHistoryModel>(historyDatas) {
        @Override
        public View getView(FlowLayout parent, final int position, final SearchHistoryModel searchHistoryModel) {
            final LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(SearchActivity.this).inflate(R.layout.item_flow_layou_history,
                    tag_flow_layout_history, false);

            TextView tv_name = linearLayout.findViewById(R.id.tv_name);
            tv_name.setText(searchHistoryModel.getSearchName());

            tv_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startSearchResult(searchHistoryModel.getSearchName());
                }
            });

            final ImageView iv_delete = linearLayout.findViewById(R.id.iv_delete);
            iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (searchHistoryModelDao != null) {
                        searchHistoryModelDao.delete(searchHistoryModel);
                        historyDatas.remove(position);
                        notifyDataChanged();
                        if (historyDatas.size() == 0) {
                            ll_history_search.setVisibility(View.GONE);
                        }
                    }
                }
            });


            tv_name.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    iv_delete.setVisibility(View.VISIBLE);
                    return true;
                }
            });

            return linearLayout;
        }
    };


    /**
     * 初始化热搜流式布局
     */
//    private void initHotFlowLayout() {
//
//        GlobalModel globalModel = SPUtil.getGlobalModel();
//        if (globalModel!=null) {
//
//            List<String> hot_search = globalModel.getData().getHot_search();
//
//            tag_flow_layout_hot.setAdapter(new TagAdapter<String>(hot_search) {
//                @Override
//                public View getView(FlowLayout parent, final int position, final String keyword) {
//                    LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(SearchActivity.this).inflate(R.layout.item_flow_layou_history,
//                            tag_flow_layout_hot, false);
//
//                    TextView tv_name = linearLayout.findViewById(R.id.tv_name);
//                    tv_name.setText(keyword);
//                    tv_name.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            startSearchResult(keyword);
//
//                        }
//                    });
//
//                    return linearLayout;
//                }
//            });
//
//
//        }
//
//    }
    @Override
    protected void initHttpData() {

    }

    @OnClick({R.id.rl_search_back, R.id.rl_delete_keywords, R.id.rl_search, R.id.rl_delete_history, R.id.rl_more_history})
    public void onViewClick(View view) {

        switch (view.getId()) {
            case R.id.rl_search_back:
                removeActivity(this);
                break;

            case R.id.rl_delete_keywords:
                /**
                 * 删除搜索关键词
                 */
                et_search_content.setText("");
                break;

            case R.id.rl_search:
                /**
                 * 搜索
                 */
                String keyword = et_search_content.getText().toString().trim();
                startSearchResult(keyword);
                break;

            case R.id.rl_delete_history:

                /**
                 * 清空搜索历史
                 */
                new AlertDialog(this).builder()
                        .setMsg("确认要清空搜索历史?")
                        .setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                            }
                        })
                        .setPositiveButton("删除", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (searchHistoryModelDao != null) {
                                    searchHistoryModelDao.deleteAll();
                                    ll_history_search.setVisibility(View.GONE);
                                }
                            }
                        })
                        .show();


                break;

            case R.id.rl_more_history:
                /**
                 * 更多历史
                 */
                rl_more_history.setVisibility(View.GONE);
                historyDatas.clear();
                historyDatas.addAll(allHistoryModels);
                historyAdapter.notifyDataChanged();

                break;
        }

    }


    /**
     * 根据关键字跳转到搜索结果
     *
     * @param keyword
     */
    private void startSearchResult(String keyword) {

        if (!TextUtils.isEmpty(keyword)) {
            /**
             * 1.记录搜索历史
             */
            if (searchHistoryModelDao != null) {

                List<SearchHistoryModel> historyModels = searchHistoryModelDao
                        .queryBuilder()
                        .where(SearchHistoryModelDao.Properties.SearchName.eq(keyword)).build().list();

                if (historyModels.size() == 0) {
                    SearchHistoryModel menuSearchHistoryModel = new SearchHistoryModel();
                    menuSearchHistoryModel.setSearchName(keyword);
                    menuSearchHistoryModel.setDateTime(CommentUtil.getCurrentTime());
                    searchHistoryModelDao.insert(menuSearchHistoryModel);
                } else {
                    SearchHistoryModel searchHistoryModel = historyModels.get(0);
                    searchHistoryModel.setDateTime(CommentUtil.getCurrentTime());
                    searchHistoryModelDao.update(searchHistoryModel);
                }

            }

            /**
             * 2.跳转界面
             */
//            Intent searchIntent = new Intent(this, GoodsListActivity.class);
//            searchIntent.putExtra("keyword", keyword);
//            startActivity(searchIntent);
//            removeActivity(this);
        }

    }

    @Override
    protected void onResume() {
        searchHistoryDB();
        super.onResume();
    }

    private void searchHistoryDB() {

        if (searchHistoryModelDao != null) {
            allHistoryModels = searchHistoryModelDao.queryBuilder().
                    orderDesc(SearchHistoryModelDao.Properties.DateTime).list();

            if (allHistoryModels.size() != 0) {
                historyDatas.clear();

                ll_history_search.setVisibility(View.VISIBLE);
                if (allHistoryModels.size() > 10) {
                    rl_more_history.setVisibility(View.VISIBLE);
                    historyDatas.addAll(allHistoryModels.subList(0, 10));
                } else {
                    historyDatas.addAll(allHistoryModels);
                    rl_more_history.setVisibility(View.GONE);
                }
                historyAdapter.notifyDataChanged();
            } else {
                ll_history_search.setVisibility(View.GONE);
            }
        }
    }


}
