package com.bs.android.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.bs.android.http.Callback;
import com.bs.android.http.HttpUtils;
import com.mylocation.android.model.EventBusModel;
import com.mylocation.android.model.LocationModel1;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

public class LocationUtils {

    public static String TAG = "test";
    public static double mLat = 0;
    public static double mLng = 0;

    public static void getLocation(final Context mActivity) {


        LocationManager locationManager = (LocationManager) mActivity.getSystemService(mActivity.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);//低精度，如果设置为高精度，依然获取不了location。
        criteria.setAltitudeRequired(false);//不要求海拔
        criteria.setBearingRequired(false);//不要求方位
        criteria.setCostAllowed(true);//允许有花费
        criteria.setPowerRequirement(Criteria.POWER_LOW);//低功耗

        //从可用的位置提供器中，匹配以上标准的最佳提供器
        String locationProvider = locationManager.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "onCreate: 没有权限 ");
            return;
        }
        final Location location = locationManager.getLastKnownLocation(locationProvider);
        Log.d(TAG, "onCreate: " + (location == null) + "..");
        if (location != null) {
            Log.d(TAG, "onCreate: location");
            //不为空,显示地理位置经纬度
            Log.d("logger", "定位成功1------->" + "location------>经度为：" + location.getLatitude() + "\n纬度为" + location.getLongitude());
            mLat = location.getLatitude();
            mLng = location.getLongitude();
//            MainActivity.mLat =mLat+"";
//            MainActivity.mLng = mLng+"";

            EventBus.getDefault().post(new EventBusModel("get_latlng", location));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    GetLocationMsg(location.getLatitude(), location.getLongitude());
                }
            }, 1000);
        }
        //监视地理位置变化
        locationManager.requestLocationUpdates(locationProvider, 0, 0, locationListener);
        locationManager.removeUpdates(locationListener);
    }

    /**
     * LocationListern监听器
     * 参数：地理位置提供器、监听位置变化的时间间隔、位置变化的距离间隔、LocationListener监听器
     */

    public static LocationListener locationListener = new LocationListener() {

        @Override
        public void onStatusChanged(String provider, int status, Bundle arg2) {

        }

        @Override
        public void onProviderEnabled(String provider) {
//            Log.d(TAG, "onProviderEnabled: " + provider + ".." + Thread.currentThread().getName());
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.d(TAG, "onProviderDisabled: " + provider + ".." + Thread.currentThread().getName());
        }

        @Override
        public void onLocationChanged(Location location) {
            Log.d(TAG, "onLocationChanged: " + ".." + Thread.currentThread().getName());
            //如果位置发生变化,重新显示
            Log.d(TAG, "定位成功------->" + "location------>经度为：" + location.getLatitude() + "\n纬度为" + location.getLongitude());
//            GetLocationMsg(mActivity,location.getLatitude(),location.getLongitude());

        }
    };


    public static void GetLocationMsg(final double latitude, final double longitude) {
        /**
         * 经纬度转换地理位置信息
         */
//        String url ="http://maps.google.cn/maps/api/geocode/json?latlng="+latitude+","+longitude+"&language=CN";
        String url = "https://restapi.amap.com/v3/geocode/regeo";

        Map map = new HashMap();
        map.put("output", "json");
        map.put("location", longitude + "," + latitude);
        map.put("key","237cd12e36639977c9bd14e97a6a55af");

        HttpUtils.GET(url, map, LocationModel1.class, new Callback<LocationModel1>() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onSucceed(String json, String code, LocationModel1 locationModel1) {
                Log.v("tags", "json = " + json);

                LocationModel1.RegeocodeBean results = locationModel1.getRegeocode();
                if (!DataSafeUtils.isEmpty(results)) {
//                LocationInfoModel locationInfo = new LocationInfoModel();
//                locationInfo.setCity(results.getAddressComponent().getCity());
//                locationInfo.setProvince(results.getAddressComponent().getProvince());
//                locationInfo.setCountry(results.getAddressComponent().getCountry());
//                    AddressSearchListActivity.mCityName = results.getAddressComponent().getCity();
//                    AddressSearchListActivity.mCityCode = results.getAddressComponent().getCitycode();
                        EventBus.getDefault().post(new EventBusModel("get_location", results));
                }
            }


            @Override
            public void onOtherStatus(String json, String code) {

            }

            @Override
            public void onFailed(Throwable e) {

            }

            @Override
            public void onFinish() {

            }
        });


    }

}
