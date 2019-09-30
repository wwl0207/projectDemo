package com.bs.android.model.pay;

import com.bs.android.model.BaseVO;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * created by WWL on 2019/5/16 0016:11
 */
public class OrderLineVO extends BaseVO implements Serializable{

    /**
     * data : {"money":"25","time":"34494"}
     */
    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * money : 25
         * time : 34494
         */

        private String pay_money;
        private String money;
        private String type;//(1:其他 2:充值)
        private String flag;//标志(1:团购订单  2:其他)
        @SerializedName("time")
        private String time;

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getPay_money() {
            return pay_money;
        }

        public void setPay_money(String pay_money) {
            this.pay_money = pay_money;
        }
    }
}
