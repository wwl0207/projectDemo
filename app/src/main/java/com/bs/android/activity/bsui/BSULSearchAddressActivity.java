package com.bs.android.activity.bsui;

import android.Manifest;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.Projection;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.bs.android.R;
import com.bs.android.activity.BaseActivity;
import com.bs.android.activity.MarketLocationActivity;
import com.bs.android.model.address.AddressVO;
import com.bs.android.utils.CommentUtil;
import com.bs.android.utils.DataSafeUtils;
import com.bs.android.utils.LocUtils;
import com.bs.android.utils.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mylocation.android.model.EventBusModel;
import com.mylocation.android.model.MeiTuanBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

/**
 * created by WWL on 2019/9/10 0010:09
 */
@RuntimePermissions
public class BSULSearchAddressActivity extends BaseActivity implements AMapLocationListener, PoiSearch.OnPoiSearchListener, LocationSource {
    @BindView(R.id.address_edit)
    EditText addressEdit;
    @BindView(R.id.address_recyclerview)
    RecyclerView addressRecyclerview;


    @BindView(R.id.city_choise)
    TextView cityChoise;
    @BindView(R.id.search_recyclerview)
    RecyclerView searchRecyclerview;
    @BindView(R.id.rl_delete_keywords)
    RelativeLayout rl_delete_keywords;
    @BindView(R.id.back_laytout)
    LinearLayout backLaytout;
    @BindView(R.id.map)
    MapView mMapView;
    private List<AddressVO.DataBean.AddressListBean> mList = new ArrayList<>();
    private List<AddressVO.DataBean.AddressListBean> mSearchList = new ArrayList<>();
    BaseQuickAdapter<AddressVO.DataBean.AddressListBean, BaseViewHolder> mAdapter;
    BaseQuickAdapter<AddressVO.DataBean.AddressListBean, BaseViewHolder> mSearchAdapter;
    private double latitude = 0;
    private double longitude = 0;
    private double mLatitude1 = 0;//传进来的经纬度
    private double mLongitude1 = 0;
    public String mCityName = "";
        private String mCityCode = "";
    AMapLocationClient mlocationClient;
    AMap aMap;
    Marker marker;
    MarkerOptions markerOption;
    LocationSource.OnLocationChangedListener mListener;
    AMapLocationClientOption mLocationOption;
    private boolean isSearch = false;
    private int zoomIndex = 16;
    AddressVO.DataBean.AddressListBean AddressData = new AddressVO.DataBean.AddressListBean();

    @Override
    protected int getContentLayoutRes() {
        return R.layout.bsui_choiseaddress_layout;
    }

    @Override
    protected void initView() {
//        setMyTitle("根据定位选择地址信息");
        hideTitleBar();
        EventBus.getDefault().register(this);

        AddressVO.DataBean.AddressListBean dataBean = (AddressVO.DataBean.AddressListBean) this.getIntent().getSerializableExtra("info");
        if (!DataSafeUtils.isEmpty(dataBean)) {
            this.AddressData = dataBean;
            if (!DataSafeUtils.isEmpty(dataBean.getLat())) {
                mLatitude1 = Double.parseDouble(dataBean.getLat());
            }
            if (!DataSafeUtils.isEmpty(dataBean.getLng())) {
                mLongitude1 = Double.parseDouble(dataBean.getLng());
            }
            mCityName = dataBean.getCity();
            cityChoise.setText(mCityName);
        }


        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mMapView.getLayoutParams();
        params.width = CommentUtil.getDisplayWidth(this);
        params.height = params.width - 100;
        mMapView.setLayoutParams(params);

        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);//普通模式


        getAdapter(mList);
        getSearchAdapter(mSearchList);


        if(LocUtils.isLocationEnabled(this)){//位置服务已打开
            BSULSearchAddressActivityPermissionsDispatcher.getLocationDataWithPermissionCheck(this);
        }else{//位置服务未打开，跳转打开界面
            ToastUtils.showLongToast("请先打开本机位置服务");
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent,887);
        }
    }

    @Override
    protected void initHttpData() {

        addressEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("")) {
                    if (s.toString().trim().length() > 0) {
                        isSearch = true;
                        doSearchQuery(addressEdit.getText().toString().trim());
                        rl_delete_keywords.setVisibility(View.VISIBLE);
                    }
                } else {
                    isSearch = false;
                    doSearchQuery(mCityName);
                    rl_delete_keywords.setVisibility(View.GONE);
                    mSearchList.clear();
                    mSearchAdapter.notifyDataSetChanged();
                }
            }
        });
    }


    private void getAdapter(List<AddressVO.DataBean.AddressListBean> mList) {
        mAdapter = new BaseQuickAdapter<AddressVO.DataBean.AddressListBean, BaseViewHolder>(R.layout.bsui_address_search_item, mList) {
            @Override
            protected void convert(BaseViewHolder helper, AddressVO.DataBean.AddressListBean item) {

                if (item.getAddress() != null && item.getAddress().contains(addressEdit.getText().toString().trim())) {
                    int index = item.getAddress().indexOf(addressEdit.getText().toString().trim());
                    int len = addressEdit.getText().toString().trim().length();
                    Spanned temp = Html.fromHtml(item.getAddress().substring(0, index)
                            + "<font color=#27C79b>" + item.getAddress().substring(index, index + len) + "</font>"
                            + item.getAddress().substring(index + len, item.getAddress().length()));

                    helper.setText(R.id.title_tv, temp);
                } else {
                    helper.setText(R.id.title_tv, item.getAddress());
                }

                helper.setText(R.id.name_tv, item.getDoor());
            }
        };
        addressRecyclerview.setAdapter(mAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        addressRecyclerview.setLayoutManager(manager);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                AddressVO.DataBean.AddressListBean info = (AddressVO.DataBean.AddressListBean) adapter.getData().get(position);
                EventBus.getDefault().post(new EventBusModel("searchaddress", info));
                removeActivity(BSULSearchAddressActivity.this);
            }
        });
    }


    @Override
    public void onPoiSearched(PoiResult result, int i) {
        Log.v("logger", i + "-----------------------------------d--------------------iii");
        PoiSearch.Query query = result.getQuery();
        ArrayList<PoiItem> pois = result.getPois();
        if (pois.size() > 0) {
            mList.clear();
            for (PoiItem poi : pois) {
                if (poi.getSnippet().length() > 4) {
                    String snippet = poi.getSnippet();
                    AddressVO.DataBean.AddressListBean info = new AddressVO.DataBean.AddressListBean();
                    info.setDoor(snippet);
                    LatLonPoint point = poi.getLatLonPoint();
                    info.setLat(point.getLatitude() + "");
                    info.setLng(point.getLongitude() + "");
                    info.setCityCode(poi.getCityCode());
                    info.setCity(poi.getCityName());
                    Log.v("logger", poi.getTitle() + "-----------------------------------d--------");
                    info.setProvince(poi.getProvinceName());
                    info.setDistrict(poi.getAdName());
                    info.setDistrictCode(poi.getAdCode());
                    info.setAddress(poi.getTitle());
                    if (!isSearch)
                        mList.add(info);
                    else
                        mSearchList.add(info);

                }
            }
            if (!isSearch) {
                mAdapter.notifyDataSetChanged();
            } else {
                mSearchAdapter.notifyDataSetChanged();
            }
        } else {
            Toast.makeText(this, "未检索到相关信息", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mMapView.onCreate(savedInstanceState);
    }


    /**
     * 开始进行poi搜索
     */
    protected void doSearchQuery(String keyword) {
        Log.v("logger", mCityName + "------");
        PoiSearch.Query query = new PoiSearch.Query(keyword, "", mCityName);
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(1);// 设置查第一页
        PoiSearch search = new PoiSearch(this, query);
        search.setOnPoiSearchListener(this);
        search.searchPOIAsyn();
//        }
    }


    private void getSearchAdapter(List<AddressVO.DataBean.AddressListBean> mSearchList) {
        mSearchAdapter = new BaseQuickAdapter<AddressVO.DataBean.AddressListBean, BaseViewHolder>(R.layout.bsui_address_search_item, mSearchList) {
            @Override
            protected void convert(BaseViewHolder helper, AddressVO.DataBean.AddressListBean item) {
                if (item.getAddress() != null && item.getAddress().contains(addressEdit.getText().toString().trim())) {
                    int index = item.getAddress().indexOf(addressEdit.getText().toString().trim());
                    int len = addressEdit.getText().toString().trim().length();
                    Spanned temp = Html.fromHtml(item.getAddress().substring(0, index)
                            + "<font color=#27C79b>" + item.getAddress().substring(index, index + len) + "</font>"
                            + item.getAddress().substring(index + len, item.getAddress().length()));

                    helper.setText(R.id.title_tv, temp);
                } else {
                    helper.setText(R.id.title_tv, item.getAddress());
                }

                helper.setText(R.id.name_tv, item.getDoor());
            }
        };
        searchRecyclerview.setAdapter(mSearchAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        searchRecyclerview.setLayoutManager(manager);
        mSearchAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                AddressVO.DataBean.AddressListBean info = (AddressVO.DataBean.AddressListBean) adapter.getData().get(position);
                EventBus.getDefault().post(new EventBusModel("searchaddress", info));
                removeActivity(BSULSearchAddressActivity.this);
            }
        });
    }


    @OnClick({R.id.back_laytout, R.id.rl_delete_keywords})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_laytout:
                this.finish();
                break;
            case R.id.rl_delete_keywords:
                addressEdit.setText("");
                rl_delete_keywords.setVisibility(View.GONE);
//                doSearchQuery("");
                isSearch = false;
                mSearchList.clear();
                mSearchAdapter.notifyDataSetChanged();
                break;
        }
    }


    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void getLocationData() {
        //对高德地图进行初始化，设置精度，时间间隔等
//        if (latitude == 0 && longitude == 0) {
        initLocate();
//        } else {
//            setLocationData();
//        }
    }

    /**
     * 没有经纬度 去定位获取经纬度 并搜索附近
     */
    private void initLocate() {
        aMap.setMyLocationEnabled(true);
        getCameraChangeLocation();//启动系统蓝点坐标 并通过经纬度定位中间
        //======================================================================================================================
        startLocationClient();//启动系统定位

    }


    /**
     * 已有经纬度，直接定位
     */
    private void setLocationData() {
        PoiSearch.Query query = new PoiSearch.Query("", "", mCityName);
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(1);// 设置查第一页
        PoiSearch search = new PoiSearch(this, query);
        search.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(final PoiResult poiResult, int i) {
//                mLatitude1 = latLonPoint.getLatitude();
//                mLongitude1 = latLonPoint.getLongitude();
                final LatLng latLng = new LatLng(mLatitude1, mLongitude1);
                aMap.animateCamera(CameraUpdateFactory.changeLatLng(latLng), 1500, null);
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomIndex));
                aMap.setMyLocationEnabled(false);
                getCameraChangeLocation();
                aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {

            }
        });
        search.searchPOIAsyn();

//        LatLng latLng = new LatLng(mLatitude1, mLongitude1);
//
////        getCameraChangeLocation();
//        MyLocationStyle myLocationStyle;
//        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//只定一次
//        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
//        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色 。
//        myLocationStyle.interval(10000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
//        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
//
//        aMap.setMyLocationEnabled(false);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
//        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
//        // 长按时中心点切换
//        aMap.animateCamera(CameraUpdateFactory.changeLatLng(latLng), 1500, null);
//        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomIndex));
//        PoiSearch.Query query = new PoiSearch.Query("", "", mCityName);
//        Log.v("logger", mCityName + "------cname---------" + mLatitude1);
//        query.setPageSize(20);
//        PoiSearch search = new PoiSearch(this, query);
//        search.setBound(new PoiSearch.SearchBound(new LatLonPoint(mLatitude1, mLongitude1), 10000));
//        search.setOnPoiSearchListener(this);
//        search.searchPOIAsyn();
    }

    /**
     * 移动地图，中心点位置不变 获取数据
     */
    private void getCameraChangeLocation() {
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色 。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。

        // 设置定位监听
//        aMap.setLocationSource(this);
        aMap.setOnCameraChangeListener(mapChangedListener);
        aMap.moveCamera(CameraUpdateFactory.zoomTo(zoomIndex));
    }

    /**
     * 启动系统定位
     */
    private void startLocationClient() {
        if (mlocationClient == null) {
            //初始化定位
            mlocationClient = new AMapLocationClient(this);
            //初始化定位参数
            mLocationOption = new AMapLocationClientOption();
            //设置定位回调监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();//启动定位
        }
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表

                mlocationClient.stopLocation();
                if (mLatitude1 > 0 && mLongitude1 > 0) {
                    aMap.setMyLocationEnabled(false);
                    setLocationData();
                    aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
                } else {
                    latitude = amapLocation.getLatitude();//获取纬度
                    longitude = amapLocation.getLongitude();//获取经度
                    cityChoise.setText(amapLocation.getCity() + "");
                    mCityName = amapLocation.getCity();
                    PoiSearch.Query query = new PoiSearch.Query("", "", "");
                    query.setPageSize(20);
                    PoiSearch search = new PoiSearch(this, query);
                    search.setBound(new PoiSearch.SearchBound(new LatLonPoint(latitude, longitude), 10000));
                    search.setOnPoiSearchListener(this);
                    search.searchPOIAsyn();
                    mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                }
            } else {
                dismissDialog();
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("logger", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        } else {
            dismissDialog();
        }
        dismissDialog();

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        BSULSearchAddressActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnClick(R.id.city_choise)
    public void onViewClicked() {
        Intent intent = new Intent(BSULSearchAddressActivity.this, MarketLocationActivity.class);
        startActivity(intent);
        addressEdit.setText("");
        rl_delete_keywords.setVisibility(View.GONE);
        isSearch = false;
        mSearchList.clear();
        mSearchAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        mMapView.onDestroy();
        mList.clear();
        mAdapter.notifyDataSetChanged();
        super.onDestroy();
    }


    @Subscribe
    public void handleEventBus(EventBusModel eventBusModel) {
        String code = eventBusModel.getCode();
        switch (code) {
            case "selected_city":
                MeiTuanBean bean = (MeiTuanBean) eventBusModel.getObject();
                if (!DataSafeUtils.isEmpty(bean)) {
                    mCityName = bean.getCity();
                    cityChoise.setText(mCityName);
                    mCityName = mCityName;
                    mCityCode = bean.getCitycode();
                    CitySearchQuery("市政府");
                }
                break;
        }
    }

    /**
     * 切换城市 定位一个地方
     *
     * @param keyword
     */
    protected void CitySearchQuery(String keyword) {
        PoiSearch.Query query = new PoiSearch.Query(keyword, "", mCityName);
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(1);// 设置查第一页
        PoiSearch search = new PoiSearch(this, query);
        search.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(final PoiResult poiResult, int i) {
                final LatLonPoint latLonPoint = poiResult.getPois().get(0).getLatLonPoint();
                mLatitude1 = latLonPoint.getLatitude();
                mLongitude1 = latLonPoint.getLongitude();
                final LatLng latLng = new LatLng(mLatitude1, mLongitude1);
                aMap.animateCamera(CameraUpdateFactory.changeLatLng(latLng), 1500, null);
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomIndex));
                aMap.setMyLocationEnabled(false);
                getCameraChangeLocation();
                aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {

            }
        });
        search.searchPOIAsyn();
    }


    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
//        Toast.makeText(this, "listener", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;


    }


    private AMap.OnCameraChangeListener mapChangedListener = new AMap.OnCameraChangeListener() {
        @Override
        public void onCameraChange(CameraPosition cameraPosition) {
            // 添加当前坐标覆盖物
            if (markerOption == null) {
                markerOption = new MarkerOptions()
//                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                        .position(cameraPosition.target)
                        .draggable(true);
                markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(), R.drawable.local_map)));
                marker = aMap.addMarker(markerOption);
            } else {
                marker.setPosition(cameraPosition.target);
                markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(), R.drawable.local_map)));
            }

        }

        @Override
        public void onCameraChangeFinish(CameraPosition cameraPosition) {

            LatLng mapCenterPoint = getMapCenterPoint();
            latitude = mapCenterPoint.latitude;
            longitude = mapCenterPoint.longitude;

            LatLonPoint latLonPoint = new LatLonPoint(BSULSearchAddressActivity.this.latitude, longitude);
            getAddressByLatlng(latLonPoint);
        }

    };

    private void getAddressByLatlng(LatLonPoint latLonPoint) {
        PoiSearch.Query query = new PoiSearch.Query("", "", "");
        query.setPageSize(20);
        PoiSearch search = new PoiSearch(this, query);
        search.setBound(new PoiSearch.SearchBound(latLonPoint, 10000));
        search.setOnPoiSearchListener(this);
        search.searchPOIAsyn();

    }

    public LatLng getMapCenterPoint() {
        int left = mMapView.getLeft();
        int top = mMapView.getTop();
        int right = mMapView.getRight();
        int bottom = mMapView.getBottom();
// 获得屏幕点击的位置
        int x = (int) (mMapView.getX() + (right - left) / 2);
        int y = (int) (mMapView.getY() + (bottom - top) / 2);
        Projection projection = aMap.getProjection();
        LatLng pt = projection.fromScreenLocation(new Point(x, y));
        return pt;

    }

}
