package com.bs.android.model.address;

import com.bs.android.model.BaseVO;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wwl
 * 我的地址列表
 */

public class AddressVO extends BaseVO implements Serializable {

    private String code;
    private String msg;
    private String time;
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        private String page;
        private String count;
        private List<AddressListBean> list;

        public String getPage() {
            return page;
        }

        public void setPage(String page) {
            this.page = page;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public List<AddressListBean> getList() {
            return list;
        }

        public void setList(List<AddressListBean> list) {
            this.list = list;
        }

        public static class AddressListBean implements Serializable{
            private String id;
            private String receiver_name;
            private String receiver_mobile;
            private String is_default;
            private String receiver_address;//这个是展示用

            private String province;//: "",
            private String city;//: "",
            private String district;//: "",
            private String address;//: "编辑的时候带过去
            private String lng;//: "112.114315",
            private String lat;//: "32.071586",
            //着下面三个是我自己查询地址用
            private String door;//门牌号
//            private String titleName;
            private String cityCode;
            private String districtCode;

            public String getDoor() {
                return door;
            }

            public void setDoor(String door) {
                this.door = door;
            }

//            public String getTitleName() {
//                return titleName;
//            }
//
//            public void setTitleName(String titleName) {
//                this.titleName = titleName;
//            }

            public String getCityCode() {
                return cityCode;
            }

            public void setCityCode(String cityCode) {
                this.cityCode = cityCode;
            }

            public String getDistrictCode() {
                return districtCode;
            }

            public void setDistrictCode(String districtCode) {
                this.districtCode = districtCode;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getLng() {
                return lng;
            }

            public void setLng(String lng) {
                this.lng = lng;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getReceiver_name() {
                return receiver_name;
            }

            public void setReceiver_name(String receiver_name) {
                this.receiver_name = receiver_name;
            }

            public String getReceiver_mobile() {
                return receiver_mobile;
            }

            public void setReceiver_mobile(String receiver_mobile) {
                this.receiver_mobile = receiver_mobile;
            }

            public String getIs_default() {
                return is_default;
            }

            public void setIs_default(String is_default) {
                this.is_default = is_default;
            }

            public String getReceiver_address() {
                return receiver_address;
            }

            public void setReceiver_address(String receiver_address) {
                this.receiver_address = receiver_address;
            }
        }
    }
}
