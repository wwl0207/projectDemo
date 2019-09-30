package com.bs.android.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bs.android.activity.LoginActivity;
import com.bs.android.activity.MainActivity;
import com.bs.android.constant.SPConsts;
import com.bs.android.http.ConstantUrl;
import com.bs.android.model.EventBusModel;
import com.bs.android.model.mg.UserDataModel;
import com.bs.android.http.Callback;
import com.bs.android.http.HttpUtils;
import com.bs.android.http.JsonUtils;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;


/**
 * Created by admin on 2017/12/22.
 */

public class LoginUtil {


    private final int SMS_CODE_TIME=60;
    private  int second=SMS_CODE_TIME;
    private Timer timer;
    private static TextView textView;


    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    int time= (int) msg.obj;
                    textView.setText(time+"秒后重发");
                    if (time==0){
                        timer.cancel();
                        timer=null;
                        second=SMS_CODE_TIME;
                        textView.setEnabled(true);
                        textView.setAlpha(1.0f);
                        textView.setText("重新获取");
                        //textView.setTextColor(App.getInstance().getResources().getColor(R.color.mmCircle_pink));
                        //textView.setBackgroundColor(getResources().getColor(R.color.color_login_checkcode));
                    }
                    break;

                case 1:
                    //CommentUtil.showSingleToast(LoginActivity.this,"验证码错误，登录失败");
            }
        }
    };


    /**
     * 账号密码登录
     */
    public static void accountLogin(final Context context,String prefix, final String mobile, String password, final String isfrom){

        Map map=new HashMap();
        map.put("prefix",prefix);
        map.put("account",mobile);
        map.put("password",password);
        HttpUtils.POST(ConstantUrl.USER_LOGIN, map, UserDataModel.class,new Callback<UserDataModel>() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onSucceed(String json, String code, UserDataModel  userDataModel) {
                String desc = JsonUtils.getSinglePara(json, "desc");
                CommentUtil.showSingleToast(desc);
                loginSuccessed(context, userDataModel, isfrom);
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



    /**
     * 动态密码登录
     */
    public static void dynamicLogin(final Context context, final String mobile, String smsCode){

//        HttpUtils httpUtils=new HttpUtils(context);
//        Map map=new HashMap();
//        map.put("method", MethodContstant.DynamicLogin);
//        map.put("mobile",mobile);
//        map.put("smsCode",smsCode);
//        httpUtils.setFastParseJsonType(HttpUtils.OBJECT_JSON,UserDataModel.class);
//        httpUtils.setHandleError(false);
//        httpUtils.request(map, new RequestCallback<UserDataModel>() {
//
//            @Override
//            public void onStart(int what) {
//
//            }
//
//            @Override
//            public void onSucceed(String json, String code, UserDataModel userDataModel) {
//
//                if ("10000".equals(code)){
//
//                    loginSuccessed(context,userDataModel,mobile);
//
//                } else {
//
//                    String statusStr = JsonUtils.getSinglePara(json, "statusStr");
//                    CommentUtil.showSingleToast(statusStr);
//
//                }
//
//            }
//
//            @Override
//            public void onFailed(int what, Response<String> response) {
//
//            }
//
//            @Override
//            public void onFinish(int what) {
//
//            }
//        });

    }




    /**
     * 重新登录
     * @param context
     */
    public static  void reLogin(final Context context){

        Intent intent=new Intent(context, LoginActivity.class);
        context.startActivity(intent);
        AppCompatActivity appCompatActivity= (AppCompatActivity) context;
        appCompatActivity.finish();


    }


    /**
     * 登陆成功
     * @param context
     */
    public static void loginSuccessed(Context context, UserDataModel userDataModel, String isfrom){

        SPUtil.put(SPConsts.USER_DATA, JSONObject.toJSONString(userDataModel));
        AppCompatActivity activity = (AppCompatActivity) context;
        Logger.v(isfrom+"---------------");
        if (TextUtils.isEmpty(isfrom)) {
            CommentUtil.startActivity(context, MainActivity.class);
        }
        EventBus.getDefault().post(new EventBusModel("login_success"));
        activity.finish();
    }

    /**
     * 检查手机号
     * @param phone 手机号
     * @return  是否合格
     */
    public static boolean checkPhone(String phone){

        if (TextUtils.isEmpty(phone)){
            CommentUtil.showSingleToast("手机号不能为空");
            return false;
        }
//        else if (!isPhoneLegal(phone)){
//            CommentUtil.showSingleToast("您的手机号不合法");
//            return false;
//        }
        else {
            return true;
        }


    }


    /**
     * 检查密码
     * @param password 密码
     * @return  是否合格
     */
    public static boolean checkPassword(String password){

        if (TextUtils.isEmpty(password)){

            CommentUtil.showSingleToast("密码不能为空");
            return false;

        }else {
            return true;
        }
//        else if (password.length()>=6&&password.length()<=16){
//
//            return true;
//
//        }else {
//            CommentUtil.showSingleToast("您的密码长度不正确");
//            return false;
//        }


    }


    /**
     * 检查短信验证码
     * @param smsCode
     * @return
     */
    public static boolean checkSmsCode(String smsCode){

        if (TextUtils.isEmpty(smsCode)){

            CommentUtil.showSingleToast("短信验证码不能为空");
            return false;

        }else {
            return true;
        }
//        else if (smsCode.length()==6){
//
//            return true;
//
//        }else {
//            CommentUtil.showSingleToast("短信验证码长度不正确");
//            return false;
//        }


    }


    /**
     * 大陆号码或香港号码均可
     */
    public static boolean isPhoneLegal(String str)throws PatternSyntaxException {
//        return isChinaPhoneLegal(str) || isHKPhoneLegal(str);
        return true;
    }


    /**
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
     * 此方法中前三位格式有：
     * 13+任意数
     * 15+除4的任意数
     * 18+除1和4的任意数
     * 17+除9的任意数
     * 147
     */
    public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
        String regExp = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 香港手机号码8位数，5|6|8|9开头+7位任意数
     */
    public static boolean isHKPhoneLegal(String str)throws PatternSyntaxException {
        String regExp = "^(5|6|8|9)\\d{7}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }


    /**
     * 开始倒计时
     * @param textView
     */
    public void startDaoJiShi(TextView textView) {
        this.textView=textView;

        textView.setEnabled(false);
        textView.setAlpha(0.7f);
        textView.setText(second+"秒后重发");

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message message = Message.obtain();
                message.what = 0;
                message.obj = --second;
                handler.sendMessage(message);
            }
        }, 1000, 1000);


    }


    /**
     * 获取定时器对象
     * @return
     */
    public void  cancelDaojishi(){
        if (timer!=null) {
            timer.cancel();
            this.second=SMS_CODE_TIME;
        }
    }

    public void getOutTime(){
        if (timer!=null) {
            long data = System.currentTimeMillis();
            Log.v("logger", data + "=======data");
            SPUtil.put("time", data + (second * 1000) + "");
        }
    }
    public void getIntoTime(TextView textView){
        long data = System.currentTimeMillis();
       String data2 = SPUtil.getString("time");
       if (!DataSafeUtils.isEmpty(data2)) {
           long secendtime = Long.parseLong(data2);
           Log.v("logger", secendtime + "=======data=" + data);
           if (secendtime > data) {
               int time = (int) ((secendtime - data) / 1000);
               this.second = time;
               startDaoJiShi(textView);
           }
       }
    }

}
