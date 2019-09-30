package com.bs.android.model;

import com.alibaba.fastjson.JSONObject;

/**
 * created by WWL on 2019/6/9 0009:09
 */
public class BaseVO {
    private String code;
    private String desc;
    private String msg;
    private String time;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
