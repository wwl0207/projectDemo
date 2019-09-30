package com.bspopupwindow.model;

import java.util.ArrayList;
import java.util.List;

/**
 * created by WWL on 2019/8/17 0017:11
 */
public class getDemoData {
    public static List<CareeVO.DataBean> getData(){
        List<CareeVO.DataBean> mList = new ArrayList<>();
        for (int i=0;i<=5;i++){
            CareeVO.DataBean dataBean = new CareeVO.DataBean();
            dataBean.setId(i+1+"");
            dataBean.setTitle("第"+(i+1)+"列");

            List<CareeVO.DataBean.ListBean> listBeans = new ArrayList<>();
            for (int k=0;k<15;k++){
                CareeVO.DataBean.ListBean listBean = new CareeVO.DataBean.ListBean();
                listBean.setId(k+"");
                listBean.setTitle("第"+(i+1)+"列第"+k+"行");
                listBeans.add(listBean);
            }
            dataBean.setList(listBeans);
            mList.add(dataBean);
        }
        return mList;
    }
}
