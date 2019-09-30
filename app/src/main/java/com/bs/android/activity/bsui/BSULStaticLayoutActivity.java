package com.bs.android.activity.bsui;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.bs.android.R;
import com.bs.android.activity.BaseActivity;
import com.tv.lib.MenuItemLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * created by WWL on 2019/9/10 0010:09
 * 静态布局
 */
public class BSULStaticLayoutActivity extends BaseActivity {
    @BindView(R.id.item_layout1)
    MenuItemLayout itemLayout1;
    @BindView(R.id.item_layout2)
    MenuItemLayout itemLayout2;
    @BindView(R.id.item_layout3)
    MenuItemLayout itemLayout3;
    @BindView(R.id.item_layout4)
    MenuItemLayout itemLayout4;
    @BindView(R.id.item_layout5)
    MenuItemLayout itemLayout5;
    @BindView(R.id.item_layout6)
    MenuItemLayout itemLayout6;
    @BindView(R.id.item_layout7)
    MenuItemLayout itemLayout7;
    @BindView(R.id.item_layout8)
    MenuItemLayout itemLayout8;
    @BindView(R.id.item_layout9)
    MenuItemLayout itemLayout9;

    @Override
    protected int getContentLayoutRes() {
        return R.layout.bsui_staticitem_layout;
    }

    @Override
    protected void initView() {
        setMyTitle("静态列表布局自定义");

        itemLayout1.setTitleImgId(R.drawable.umeng_socialize_copy);
        itemLayout1.setTitleColor(R.color.red_background);
        itemLayout1.setTitleSize(15);
        itemLayout1.setContentText("第一条数据展示");
        itemLayout1.setContentSize(15);
        itemLayout1.setContentColor(R.color.black);
        itemLayout1.setShowContentImg(false);
        itemLayout1.setShowRightImg(false);
        itemLayout1.setDivideLine(2);

        itemLayout2.setShowTitleImg(false);//
        itemLayout2.setTitleColor(R.color.blue_background);
        itemLayout2.setTitleSize(17);
        itemLayout2.setContentText("第二条数据展示");
        itemLayout2.setContentSize(13);
        itemLayout2.setContextGray(Gravity.CENTER);
        itemLayout2.setContentColor(R.color.red_background);
        itemLayout2.setShowContentImg(false);
        itemLayout2.setShowRightImg(false);
        itemLayout2.setDivideLine(1);

        itemLayout3.setTitleImgId(R.drawable.umeng_socialize_delete);
        itemLayout3.setTitleColor(R.color.black);
        itemLayout3.setTitleSize(15);
        itemLayout3.setContentText("第三条数据展示");
        itemLayout3.setContentSize(15);
        itemLayout3.setContextGray(Gravity.RIGHT);
        itemLayout3.setContentColor(R.color.gray);
        itemLayout3.setShowContentImg(true);
        itemLayout3.setContentImgId(R.drawable.main_ad_alarm_icon);
        itemLayout3.setShowRightImg(false);
        itemLayout3.setDivideLine(1);

        itemLayout4.setTitleImgId(R.drawable.umeng_socialize_menu_default);
        itemLayout4.setTitleColor(R.color.black);
        itemLayout4.setTitleSize(15);
        itemLayout4.setContentText("第四条数据展示");
        itemLayout4.setContentSize(15);
        itemLayout4.setContextGray(Gravity.RIGHT);
        itemLayout4.setContentColor(R.color.actionsheet_red);
        itemLayout4.setShowContentImg(true);
        itemLayout4.setContentImgId(R.drawable.share_select1);
        itemLayout4.setShowRightImg(true);
        itemLayout4.setDivideLine(2);

        itemLayout5.setTitleImgId(R.drawable.umeng_socialize_qzone);
        itemLayout5.setTitleColor(R.color.black);
        itemLayout5.setTitleSize(15);
        itemLayout5.setContentText("第五条数据展示");
        itemLayout5.setContentSize(15);
        itemLayout5.setContextGray(Gravity.RIGHT);
        itemLayout5.setContentColor(R.color.black);
        itemLayout5.setShowContentImg(false);
        itemLayout5.setShowRightImg(true);
        itemLayout5.setDivideLine(1);

        itemLayout6.setTitleImgId(R.drawable.umeng_socialize_more);
        itemLayout6.setTitleColor(R.color.black);
        itemLayout6.setTitleSize(15);
        itemLayout6.setShowContentTv(false);
        itemLayout6.setShowContentImg(false);
        itemLayout6.setShowRightImg(true);
        itemLayout6.setTitleWeight(true);
        itemLayout6.setDivideLine(1);

        itemLayout7.setTitleImgId(R.drawable.umeng_socialize_fav);
        itemLayout7.setTitleColor(R.color.black);
        itemLayout7.setTitleSize(15);
        itemLayout7.setShowContentTv(false);
        itemLayout7.setShowContentImg(true);
        itemLayout7.setShowRightImg(false);
        itemLayout7.setTitleWeight(true);
        itemLayout7.setDivideLine(2);
        itemLayout7.setTitleMargPaddingLeft(10);
        itemLayout7.setContentImgId(R.drawable.umeng_socialize_qq);

        itemLayout8.setTitleImgId(R.drawable.umeng_socialize_copy);
        itemLayout8.setTitleColor(R.color.red_background);
        itemLayout8.setTitleSize(15);
        itemLayout8.setContentText("第八条数据展示");
        itemLayout8.setContentSize(15);
        itemLayout8.setContentColor(R.color.black);
        itemLayout8.setShowContentImg(false);
        itemLayout8.setShowRightImg(false);
        itemLayout8.setDivideLine(1);
        itemLayout8.setContentEable(true);
        itemLayout8.setContentLines(2);
//      itemLayout8.setContentKeyboardType(4);

//        itemLayout9.setTitleImgId(R.drawable.umeng_socialize_copy);
////        itemLayout9.setTitleColor(R.color.red_background);
////        itemLayout9.setTitleSize(25);
//        itemLayout9.setContentText("第9条数据展示");
////        itemLayout9.setContentSize(15);
////        itemLayout9.setContentColor(R.color.black);
//        itemLayout9.setShowContentImg(false);
//        itemLayout9.setShowRightImg(false);
//        itemLayout9.setContextGray(Gravity.LEFT);
//        itemLayout9.setDivideLine(1);
//        itemLayout9.setContentEable(true);
//        itemLayout9.setContentKeyboardType(5);

    }

    @Override
    protected void initHttpData() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.item_layout1, R.id.item_layout2, R.id.item_layout3, R.id.item_layout4, R.id.item_layout7})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.item_layout1:
                Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_layout2:
                Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_layout3:
                Toast.makeText(this, "3", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_layout4:
                Toast.makeText(this, "4", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_layout7:
                Toast.makeText(this, "拍照", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
