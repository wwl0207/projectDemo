package com.bs.android.model.mg;

import com.bs.android.model.BaseVO;

import java.io.Serializable;
import java.util.List;

public class GlobalModel extends BaseVO {


    /**
     * code : 200
     * desc : 请求成功
     * time : 1553513273
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

    public static class DataBean implements Serializable{
        /**
         * message : http://film.test.hbbeisheng.com/api/publics/articlelist.html
         * level_desc : http://film.test.hbbeisheng.com/api/publics/about.html
         * default_word : 小姐姐
         * hot_search : ["小姐姐","元旦"]
         * notice :
         * version : {"ios_version":{"id":"1","platform":"1","build":"4","version":"1.01","content":"测试更新内容 1.完善XXXXX 2.修改XXXXX","isforce":"1","istest":"0","download":"https://www.pgyer.com/Q7Ks","createtime":"1552392706"},"android_version":{"id":"2","platform":"2","build":"1","version":"1.0.1","content":"测试更新内容 1.完善XXXXX 2.修改XXXXX","isforce":"1","istest":"0","download":"http://movie.test.hbbeisheng.com/111.apk","createtime":"1550221242"}}
         */

        private ShareBean share;
        private String club;
        private String message;
        private String level_desc;
        private String default_word;
        private String notice;
        private VersionBean version;
        private List<String> hot_search;
        private String shortvideo;//发现界面的类别
        private String web_url;//这个是用aes加密过的域名地址，把这个域名对比系统存储的git获取的bio域名，如果发现不一样，就主动去请求一次github接口重新获取bio域名

        public String getWeb_url() {
            return web_url;
        }

        public void setWeb_url(String web_url) {
            this.web_url = web_url;
        }

        public String getShortvideo() {
            return shortvideo;
        }

        public void setShortvideo(String shortvideo) {
            this.shortvideo = shortvideo;
        }


        public String getClub() {
            return club;
        }

        public void setClub(String club) {
            this.club = club;
        }

        public ShareBean getShare() {
            return share;
        }

        public void setShare(ShareBean share) {
            this.share = share;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getLevel_desc() {
            return level_desc;
        }

        public void setLevel_desc(String level_desc) {
            this.level_desc = level_desc;
        }

        public String getDefault_word() {
            return default_word;
        }

        public void setDefault_word(String default_word) {
            this.default_word = default_word;
        }

        public String getNotice() {
            return notice;
        }

        public void setNotice(String notice) {
            this.notice = notice;
        }

        public VersionBean getVersion() {
            return version;
        }

        public void setVersion(VersionBean version) {
            this.version = version;
        }

        public List<String> getHot_search() {
            return hot_search;
        }

        public void setHot_search(List<String> hot_search) {
            this.hot_search = hot_search;
        }

        public static class ShareBean {
            /**
             * title : 这个非常好
             * desc : 这个特别好，我很喜欢看呀
             */

            private String title;
            private String desc;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }

        public static class VersionBean {
            /**
             * ios_version : {"id":"1","platform":"1","build":"4","version":"1.01","content":"测试更新内容 1.完善XXXXX 2.修改XXXXX","isforce":"1","istest":"0","download":"https://www.pgyer.com/Q7Ks","createtime":"1552392706"}
             * android_version : {"id":"2","platform":"2","build":"1","version":"1.0.1","content":"测试更新内容 1.完善XXXXX 2.修改XXXXX","isforce":"1","istest":"0","download":"http://movie.test.hbbeisheng.com/111.apk","createtime":"1550221242"}
             */

            private IosVersionBean ios_version;
            private AndroidVersionBean android_version;

            public IosVersionBean getIos_version() {
                return ios_version;
            }

            public void setIos_version(IosVersionBean ios_version) {
                this.ios_version = ios_version;
            }

            public AndroidVersionBean getAndroid_version() {
                return android_version;
            }

            public void setAndroid_version(AndroidVersionBean android_version) {
                this.android_version = android_version;
            }

            public static class IosVersionBean {
                /**
                 * id : 1
                 * platform : 1
                 * build : 4
                 * version : 1.01
                 * content : 测试更新内容 1.完善XXXXX 2.修改XXXXX
                 * isforce : 1
                 * istest : 0
                 * download : https://www.pgyer.com/Q7Ks
                 * createtime : 1552392706
                 */

                private String id;
                private String platform;
                private String build;
                private String version;
                private String content;
                private String isforce;
                private String istest;
                private String download;
                private String createtime;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getPlatform() {
                    return platform;
                }

                public void setPlatform(String platform) {
                    this.platform = platform;
                }

                public String getBuild() {
                    return build;
                }

                public void setBuild(String build) {
                    this.build = build;
                }

                public String getVersion() {
                    return version;
                }

                public void setVersion(String version) {
                    this.version = version;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public String getIsforce() {
                    return isforce;
                }

                public void setIsforce(String isforce) {
                    this.isforce = isforce;
                }

                public String getIstest() {
                    return istest;
                }

                public void setIstest(String istest) {
                    this.istest = istest;
                }

                public String getDownload() {
                    return download;
                }

                public void setDownload(String download) {
                    this.download = download;
                }

                public String getCreatetime() {
                    return createtime;
                }

                public void setCreatetime(String createtime) {
                    this.createtime = createtime;
                }
            }

            public static class AndroidVersionBean {
                /**
                 * id : 2
                 * platform : 2
                 * build : 1
                 * version : 1.0.1
                 * content : 测试更新内容 1.完善XXXXX 2.修改XXXXX
                 * isforce : 1
                 * istest : 0
                 * download : http://movie.test.hbbeisheng.com/111.apk
                 * createtime : 1550221242
                 */

                private String id;
                private String platform;
                private String build;
                private String version;
                private String content;
                private String isforce;
                private String istest;
                private String download;
                private String createtime;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getPlatform() {
                    return platform;
                }

                public void setPlatform(String platform) {
                    this.platform = platform;
                }

                public String getBuild() {
                    return build;
                }

                public void setBuild(String build) {
                    this.build = build;
                }

                public String getVersion() {
                    return version;
                }

                public void setVersion(String version) {
                    this.version = version;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public String getIsforce() {
                    return isforce;
                }

                public void setIsforce(String isforce) {
                    this.isforce = isforce;
                }

                public String getIstest() {
                    return istest;
                }

                public void setIstest(String istest) {
                    this.istest = istest;
                }

                public String getDownload() {
                    return download;
                }

                public void setDownload(String download) {
                    this.download = download;
                }

                public String getCreatetime() {
                    return createtime;
                }

                public void setCreatetime(String createtime) {
                    this.createtime = createtime;
                }
            }
        }
    }
}
