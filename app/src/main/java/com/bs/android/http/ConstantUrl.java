package com.bs.android.http;

/**
 * 接口
 */
public interface ConstantUrl {
    /**
     * 店铺列表
     */
    String SHOPLIST ="/routine/publics/get_gys_list";
    /**
     * 全局配置接口
     */
    String GLOBAL_PARA="/api/publics/globalParam.html";

    /**
     * 发送短信接口
     */
    String SEND_SMS_CODE="/api/publics/sendMsg.html";

    /**
     * 用户注册接口
     */
    String USER_REGISTER="/api/publics/register.html";

    /**
     * 用户登录接口
     */
    String USER_LOGIN="/api/publics/login.html";
    /**
     * 找回密码
     */
    String FORGET_PWD="/api/publics/changePwd.html";
    /**
     * 我的签到接口
     */
    String MYSIGNLIST = "/routine/shop_auth_api/integral_list";
    /**
     * 签到接口
     */
    String MYSIGN = "/routine/shop_auth_api/user_sign";
    /**
     * 订单在线支付页面接口
     */
    String ORDERLINE = "/routine/shop_auth_api/order_online";
    /**
     * 我的支付接口
     */
    String MYORDERPAY = "/routine/shop_auth_api/pay_order";
    /**
     * 验证支付密码
     */
    String CHECKPAYPASSWORD = "/routine/shop_auth_api/check_pay_password";


}
