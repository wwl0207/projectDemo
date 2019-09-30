package com.bs.android.model.mg;

import com.bs.android.model.BaseVO;

public class UserDataModel extends BaseVO {


    /**
     * code : 200
     * desc : 登陆成功
     * time : 1553513209
     * data : {"uid":"33","token":"MczxMpO0O0Oh","username":"","nickname":"","mobile":"","realname":"","headpic":"http://film.test.hbbeisheng.com/public/default/avatar.png","login_type":"1"}
     */

    private String code;
    private String desc;
    private String time;
    private DataBean data;

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

    public static class DataBean {
        /**
         * uid : 33
         * token : MczxMpO0O0Oh
         * username :
         * nickname :
         * mobile :
         * realname :
         * headpic : http://film.test.hbbeisheng.com/public/default/avatar.png
         * login_type : 1
         */

        private String uid;
        private String token;
        private String username;
        private String nickname;
        private String mobile;
        private String realname;
        private String headpic;
        private String login_type;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getHeadpic() {
            return headpic;
        }

        public void setHeadpic(String headpic) {
            this.headpic = headpic;
        }

        public String getLogin_type() {
            return login_type;
        }

        public void setLogin_type(String login_type) {
            this.login_type = login_type;
        }
    }
}
