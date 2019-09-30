package com.bs.android.model;

import java.util.List;

/**
 * created by WWL on 2019/9/16 0016:10
 */
public class ExpandListVO extends BaseVO {
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean{
        private String parentId;
        private String parentName;

        private List<ListBean> list;

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getParentName() {
            return parentName;
        }

        public void setParentName(String parentName) {
            this.parentName = parentName;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean{
            private String childId;
            private String childName;

            public String getChildId() {
                return childId;
            }

            public void setChildId(String childId) {
                this.childId = childId;
            }

            public String getChildName() {
                return childName;
            }

            public void setChildName(String childName) {
                this.childName = childName;
            }
        }
    }
}
