package com.bs.android.utils;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.List;

/**
 * created by WWL on 2019/1/22 0022:16
 * 轮播图
 */
public class BannerUtils {
/**示例方法**/
//    public void setbannerData(List< FoodMainVo.DataBean.BannerBean> list){
//        foodBanner.setImageLoader(new ImageLoader() {
//            @Override
//            public void displayImage(Context context, Object o, ImageView imageView) {
//                FoodMainVo.DataBean.BannerBean advBean = (FoodMainVo.DataBean.BannerBean) o;
//                Glide.with(mActivity).load(advBean.getAdv_image() + "").into(imageView);
//            }
//        });
//        foodBanner.setOnBannerListener(new OnBannerListener() {
//            @Override
//            public void OnBannerClick(int i) {
//                stitle = single_adv.get(i).getAdv_title();
//                simg = single_adv.get(i).getAdv_image();
//                showActivity(single_adv.get(i).getAdv_class(),single_adv.get(i).getAdv_id(), single_adv.get(i).getAdv_url());
//            }
//        });
//    }
    public static void getBanner(Context context, Banner mBanner, List<?> mImg, int index1, int index2, int office, int time, int bannerStyle, int IndicatorGravity) {
        ViewGroup.LayoutParams params = mBanner.getLayoutParams();
        float scale = Float.parseFloat(index1 + "") / Float.parseFloat(index2 + "");
        params.width = CommentUtil.getDisplayWidth(context)-office;
        params.height = (int) (params.width * scale);
        mBanner.setLayoutParams(params);
        //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
        mBanner.setBannerAnimation(Transformer.Default);
        //设置轮播间隔时间
        mBanner.setDelayTime(time);
        mBanner.setImages(mImg);
        mBanner.setBannerStyle(bannerStyle);//设置内置样式，共有六种可以点入方法内逐一体验使用。
//        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);//设置内置样式，共有六种可以点入方法内逐一体验使用。
        mBanner.setIndicatorGravity(IndicatorGravity);
        mBanner.start();
    }
}
