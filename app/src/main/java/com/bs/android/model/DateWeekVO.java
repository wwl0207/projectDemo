package com.bs.android.model;

import java.util.List;

/**
 * created by WWL on 2019/4/29 0029:14
 * 选择日期
 */
public class DateWeekVO extends BaseVO{
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean{
        private String date;
        private String id;
        private String datewee;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDatewee() {
            return datewee;
        }

        public void setDatewee(String datewee) {
            this.datewee = datewee;
        }
    }
}
