package com.bs.android.http;

/**
 * Created by WWL on 2017/12/19.
 */


public interface Callback<T>{
    //开始
    void onStarted();
    //成功
    /**
     *
     * @param json 请求的返回的数据，成功的数据
     * @param code 网络请求状态
     * @param t  解析类型
     */
    void onSucceed(String json, String code, T t);

    void onOtherStatus(String json, String code);
    //失败
    void onFailed(Throwable e);
    //完成
    void onFinish();

}
