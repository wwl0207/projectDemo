package com.bs.android.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.bs.android.App;
import com.bs.android.activity.WelcomeActivity;
import com.bs.android.constant.SPConsts;
import com.bs.android.model.mg.GlobalModel;
import com.bs.android.model.mg.UserDataModel;

/**
 * Created by admin on 2017/12/19.
 */

public class SPUtil {
    public static SharedPreferences getSharedPreference(){
        SharedPreferences sp = App.instance.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sp;
    }

    public static String getString(String key){
        SharedPreferences sp = getSharedPreference();
        return sp.getString(key, "");
    }

    public static boolean getBoolean(String key){
        SharedPreferences sp = getSharedPreference();
        return sp.getBoolean(key, false);
    }

    public static int getInt(String key){
        SharedPreferences sp = getSharedPreference();
        return sp.getInt(key, 0);
    }
    public static int getInt(String key, int defValue){
        SharedPreferences sp = getSharedPreference();
        return sp.getInt(key, defValue);
    }


    public static void put(String key , Object value){
        SharedPreferences sp = getSharedPreference();
        SharedPreferences.Editor edit = sp.edit();
        if(value instanceof String){
            edit.putString(key, (String) value);
        } else if( value instanceof Boolean){
            edit.putBoolean(key, (Boolean) value);
        } else if( value instanceof Integer){
            edit.putInt(key, (Integer) value);
        }
        edit.commit();
    }


    /**
     * 获取userdata
     * @return
     */
    public static UserDataModel getUserDataModel() {

        UserDataModel userDataModel = null;

        String user_json = SPUtil.getString(SPConsts.USER_DATA);
        if (!TextUtils.isEmpty(user_json)) {
            Gson gson = new Gson();
            userDataModel = gson.fromJson(user_json, UserDataModel.class);
        }
        return userDataModel;
    }


    public static GlobalModel getGlobalModel(){

        GlobalModel globalModel=null;
        String global_json = SPUtil.getString(SPConsts.GLOBAL_PARA);
        if (!TextUtils.isEmpty(global_json)){
            Gson gson=new Gson();
            globalModel= gson.fromJson(global_json, GlobalModel.class);
        }
        return globalModel;
    }

}
