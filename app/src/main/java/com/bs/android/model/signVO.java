package com.bs.android.model;

/**
 * created by WWL on 2019/5/16 0016:15
 * 签到
 */
public class signVO extends BaseVO {


    /**
     * data : {"adv":{"name":"签到","url_type":"1","url":"#","image":"http://cailanzi.test.hbbeisheng.com/upload/image_collection/1557993804.png","width":"500","height":"250"},"rule_info":"1.当月连续签到1天，获得￥5 配送费 2.当月连续签到5天，获得￥15 配送费 3.当月连续签到10天，获得￥30 配送费 ","day":"2019-05-14,2019-05-15","sign":"0"}
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
         * adv : {"name":"签到","url_type":"1","url":"#","image":"http://cailanzi.test.hbbeisheng.com/upload/image_collection/1557993804.png","width":"500","height":"250"}
         * rule_info : 1.当月连续签到1天，获得￥5 配送费 2.当月连续签到5天，获得￥15 配送费 3.当月连续签到10天，获得￥30 配送费
         * day : 2019-05-14,2019-05-15
         * sign : 0
         */

        private String rule_info;
        private String day;
        private String sign;
        private String integral;

//        private String money;//": "0",//这个是点击签到的时候才有的

        public String getIntegral() {
            return integral;
        }

        public void setIntegral(String integral) {
            this.integral = integral;
        }

//        public String getMoney() {
//            return money;
//        }
//
//        public void setMoney(String money) {
//            this.money = money;
//        }


        public String getRule_info() {
            return rule_info;
        }

        public void setRule_info(String rule_info) {
            this.rule_info = rule_info;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public static class AdvBean {
            /**
             * name : 签到
             * url_type : 1
             * url : #
             * image : http://cailanzi.test.hbbeisheng.com/upload/image_collection/1557993804.png
             * width : 500
             * height : 250
             */

            private String name;
            private String url_type;
            private String url;
            private String image;
            private String width;
            private String height;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getUrl_type() {
                return url_type;
            }

            public void setUrl_type(String url_type) {
                this.url_type = url_type;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getWidth() {
                return width;
            }

            public void setWidth(String width) {
                this.width = width;
            }

            public String getHeight() {
                return height;
            }

            public void setHeight(String height) {
                this.height = height;
            }
        }
    }
}
