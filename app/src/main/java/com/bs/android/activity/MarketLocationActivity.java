package com.bs.android.activity;


import android.Manifest;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.bs.android.R;
import com.bs.android.constant.SPConsts;
import com.bs.android.utils.CommentUtil;
import com.bs.android.utils.LocationUtils;
import com.bs.android.utils.SPUtil;
import com.mcxtzhang.indexlib.IndexBar.bean.BaseIndexPinyinBean;
import com.mcxtzhang.indexlib.suspension.SuspensionDecoration;
import com.mylocation.android.adapter.CommonAdapter;
import com.mylocation.android.adapter.HeaderRecyclerAndFooterWrapperAdapter;
import com.mylocation.android.adapter.MeituanAdapter;
import com.mylocation.android.adapter.OnItemClickListener;
import com.mylocation.android.adapter.ViewHolder;
import com.mylocation.android.model.CityVO;
import com.mylocation.android.model.EventBusModel;
import com.mylocation.android.model.LocationModel1;
import com.mylocation.android.model.MeiTuanBean;
import com.mylocation.android.model.MeituanHeaderBean;
import com.mylocation.android.model.MeituanTopHeaderBean;
import com.mylocation.android.view.IndexBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * 商城定位
 */
@RuntimePermissions
public class MarketLocationActivity extends BaseActivity {

    @BindView(R.id.recyclerview_city)
    RecyclerView recyclerview_city;

    @BindView(R.id.tvSideBarHint)
    TextView mTvSideBarHint;

    /**
     * 右侧边栏导航区域
     */
    @BindView(R.id.indexBar)
    IndexBar mIndexBar;

    private LinearLayoutManager mManager;

    //设置给InexBar、ItemDecoration的完整数据集
    private List<BaseIndexPinyinBean> mSourceDatas;

    //头部数据源
    private List<MeituanHeaderBean> mHeaderDatas;

    //主体部分数据源（城市数据）
    private List<MeiTuanBean> mBodyDatas;

    private SuspensionDecoration mDecoration;

    private MeituanAdapter mAdapter;
    private HeaderRecyclerAndFooterWrapperAdapter mHeaderAdapter;
//    private List<MarketCityModel.DataBean.HotBean> hotModelDataList;
    private List<CityVO.DataBean.ListBean> cityModelDataList;


    @Override
    protected int getContentLayoutRes() {
        return R.layout.activity_market_location;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        setMyTitle("选择城市");
        initCity();
        initCityData();
        MarketLocationActivityPermissionsDispatcher.askLocationWithPermissionCheck(this);

    }


    private void initCityData(){


        try {
            String str = CommentUtil.getAssetJson("city", MarketLocationActivity.this);
            //将读出的字符串转换成JSONobject
            new org.json.JSONObject(str);
            CityVO object = com.alibaba.fastjson.JSONObject.parseObject(str, CityVO.class);
            cityModelDataList = object.getData().getList();

            initDatas(cityModelDataList);
            //获取JSONObject中的数组数据
        } catch (JSONException e) {
        }

    }

    private void initCity() {
        recyclerview_city.setLayoutManager(mManager = new LinearLayoutManager(this));

        mSourceDatas = new ArrayList<>();
        mHeaderDatas = new ArrayList<>();
        List<MeiTuanBean> locationCity = new ArrayList<>();
        locationCity.add(new MeiTuanBean().setCity("定位中"));
        mHeaderDatas.add(new MeituanHeaderBean(locationCity, "自动定位", ""));
        List<MeiTuanBean> recentCitys = new ArrayList<>();
        mHeaderDatas.add(new MeituanHeaderBean(recentCitys, "最近访问城市", ""));
//        List<MeiTuanBean> hotCitys = new ArrayList<>();
//        mHeaderDatas.add(new MeituanHeaderBean(hotCitys, "热门城市", ""));
        mSourceDatas.addAll(mHeaderDatas);

        mAdapter = new MeituanAdapter(this, R.layout.meituan_item_select_city, mBodyDatas);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                // TODO: 2018/8/14 选择了城市
                MeiTuanBean meiTuanBean= (MeiTuanBean) o;
                selectedCity(meiTuanBean);
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                return false;
            }
        });
        mHeaderAdapter = new HeaderRecyclerAndFooterWrapperAdapter(mAdapter) {
            @Override
            protected void onBindHeaderHolder(ViewHolder holder, int headerPos, int layoutId, Object o) {
                switch (layoutId) {
                    case R.layout.meituan_item_header:
                        final MeituanHeaderBean meituanHeaderBean = (MeituanHeaderBean) o;
                        //网格
                        RecyclerView recyclerView = holder.getView(R.id.rvCity);
                        recyclerView.setAdapter(
                                new CommonAdapter<MeiTuanBean>(MarketLocationActivity.this, R.layout.meituan_item_header_item, meituanHeaderBean.getCityList()) {
                                    @Override
                                    public void convert(final ViewHolder holder, final MeiTuanBean meiTuanBean) {
                                        holder.setText(R.id.tvName, meiTuanBean.getCity());
                                        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                String citycode = meiTuanBean.getCitycode();
                                                Log.v("tags","--"+citycode);
                                                if (!TextUtils.isEmpty(citycode)) {
                                                    selectedCity(meiTuanBean);
                                                }
                                            }
                                        });
                                    }
                                });
                        recyclerView.setLayoutManager(new GridLayoutManager(MarketLocationActivity.this, 3));
                        break;
                    case R.layout.meituan_item_header_top:
                        MeituanTopHeaderBean meituanTopHeaderBean = (MeituanTopHeaderBean) o;
                        holder.setText(R.id.tvCurrent, meituanTopHeaderBean.getTxt());
                        break;
                    default:
                        break;
                }
            }
        };
        mHeaderAdapter.setHeaderView(1, R.layout.meituan_item_header, mHeaderDatas.get(0));
        mHeaderAdapter.setHeaderView(2, R.layout.meituan_item_header, mHeaderDatas.get(1));
//        mHeaderAdapter.setHeaderView(3, R.layout.meituan_item_header, mHeaderDatas.get(2));


        recyclerview_city.setAdapter(mHeaderAdapter);
        recyclerview_city.addItemDecoration(mDecoration=new SuspensionDecoration(this, mSourceDatas)
                .setmTitleHeight((int) CommentUtil.dpToPx(35))
                .setColorTitleBg(Color.parseColor("#d6d3d3"))
                .setTitleFontSize((int) CommentUtil.dpToPx(15))
                .setColorTitleFont(Color.parseColor("#444444"))
                .setHeaderViewCount(mHeaderAdapter.getHeaderViewCount() - mHeaderDatas.size()));
        recyclerview_city.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));


        mIndexBar.setmPressedShowTextView(mTvSideBarHint)//设置HintTextView
                .setNeedRealIndex(true)//设置需要真实的索引
                .setmLayoutManager(mManager)//设置RecyclerView的LayoutManager
                .setHeaderViewCount(mHeaderAdapter.getHeaderViewCount() - mHeaderDatas.size());


    }

    /**
     * 组织数据源
     *
     * @return
     */
    private void initDatas(List<CityVO.DataBean.ListBean> cityModelDataList) {

                mBodyDatas = new ArrayList<>();
                for (int i = 0; i < cityModelDataList.size(); i++) {
                    MeiTuanBean cityBean = new MeiTuanBean();
                    cityBean.setCity(cityModelDataList.get(i).getCityname());//设置城市名称
                    cityBean.setCitycode(cityModelDataList.get(i).getCitycode());
                    mBodyDatas.add(cityBean);
                }
                //先排序
                    if(mIndexBar.getDataHelper()!=null){
                        mIndexBar.getDataHelper().sortSourceDatas(mBodyDatas);

                        mAdapter.setDatas(mBodyDatas);
                        mHeaderAdapter.notifyDataSetChanged();
                        mSourceDatas.addAll(mBodyDatas);

                        mIndexBar.setmSourceDatas(mSourceDatas)//设置数据
                                .invalidate();
                        mDecoration.setmDatas(mSourceDatas);




                        //延迟两秒加载头部
                        MeituanHeaderBean header1 = mHeaderDatas.get(0);
                        header1.getCityList().clear();
                        header1.getCityList().add(new MeiTuanBean().setCity("定位中 . . ."));



                        /**
                         * 热门城市
                         */
                        String selected_city_history = SPUtil.getString("selected_city_history");

                        if (!TextUtils.isEmpty(selected_city_history)){
                            List<MeiTuanBean> recentCitys = JSONArray.parseArray(selected_city_history, MeiTuanBean.class);
                            MeituanHeaderBean header2 = mHeaderDatas.get(1);
                            header2.setCityList(recentCitys);
                        }


                        mHeaderAdapter.notifyItemRangeChanged(1, 3);
                    }




    }


    /**
     * 选中城市后的处理
     */
    private void selectedCity(MeiTuanBean meiTuanBean){

        Log.i("logger",meiTuanBean.getCity()+","+meiTuanBean.getCitycode());

        SPUtil.put(SPConsts.SELECTED_CITY, JSON.toJSONString(meiTuanBean));
        String selected_city_history = SPUtil.getString(SPConsts.SELECTED_CITY_HISTORY);
        if (TextUtils.isEmpty(selected_city_history)) {
            List<MeiTuanBean> meiTuanBeans = new ArrayList<>();
            meiTuanBeans.add(meiTuanBean);
            SPUtil.put(SPConsts.SELECTED_CITY_HISTORY, JSON.toJSONString(meiTuanBeans));
        }else {

            List<MeiTuanBean> meiTuanBeans = JSONArray.parseArray(selected_city_history, MeiTuanBean.class);

            List<MeiTuanBean> meiTuanBeanHistory = getMeiTuanBeanHistory(meiTuanBeans, meiTuanBean);

            SPUtil.put(SPConsts.SELECTED_CITY_HISTORY, JSON.toJSONString(meiTuanBeanHistory));

        }
        EventBus.getDefault().post(new EventBusModel("selected_city",meiTuanBean));

        removeActivity(this);

    }




    private List<MeiTuanBean> getMeiTuanBeanHistory(List<MeiTuanBean> meiTuanBeans,MeiTuanBean meiTuanBean){

        for (int i=0;i<meiTuanBeans.size();i++){

            MeiTuanBean tuanBean = meiTuanBeans.get(i);
            String city = tuanBean.getCity();
            if (city.equals(meiTuanBean.getCity())){
                return meiTuanBeans;
            }
        }

        meiTuanBeans.add(meiTuanBean);

        return meiTuanBeans;
    }

    @Override
    protected void initHttpData() {

    }


    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void askLocation() {
        LocationUtils.getLocation(this);
    }

    @SuppressLint("NeedOnRequestPermissionsResult")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MarketLocationActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void showRationale(final PermissionRequest request) {
        request.proceed();
    }

    @OnPermissionDenied({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void onPermissionDenied() {
    }

    @OnNeverAskAgain({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void onNerverAskAgain() {
    }



    @Subscribe
    public void handleEventBus(EventBusModel eventBusModel){

        String code = eventBusModel.getCode();
        switch (code){

            case "get_location":
                LocationModel1.RegeocodeBean locationModel= (LocationModel1.RegeocodeBean) eventBusModel.getObject();
                MeituanHeaderBean header1 = mHeaderDatas.get(0);
                header1.getCityList().clear();
                MeiTuanBean meiTuanBean = new MeiTuanBean();
                String city = locationModel.getAddressComponent().getCity();
                meiTuanBean.setCity(city);
                meiTuanBean.setCitycode(locationModel.getAddressComponent().getCitycode());
//                meiTuanBean.setId(getTeiTuanBeanId(city));
                header1.getCityList().add(meiTuanBean);
                mHeaderAdapter.notifyItemChanged(0);
                mHeaderAdapter.notifyItemChanged(1);
                break;


        }
    }


//    private String getTeiTuanBeanId(String name){
//
//        if (cityModelDataList!=null){
//
//            for (int i=0;i<cityModelDataList.size();i++){
//
//                CityVO.DataBean.ListBean listBean = cityModelDataList.get(i);
//                String cityName = listBean.getCityname();
//                if (cityName.contains(name)){
//
//                    return listBean.getCitycode();
//                }
//
//
//            }
//        }
//        return "";
//
//    }


    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
