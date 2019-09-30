package com.bs.android.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bs.android.R;
import com.bs.android.utils.BToast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

@SuppressLint("ValidFragment")
public class SimpleCardFragment extends BaseFragment {
    @BindView(R.id.card_title_tv)
    TextView cardTitleTv;
    Unbinder unbinder;
    private String mTitle;
    private TimePickerView pvTime;

    private OptionsPickerView pvOptions;
    private ArrayList<String> options1Items01 = new ArrayList<>();
    public static SimpleCardFragment getInstance(String title) {
        SimpleCardFragment sf = new SimpleCardFragment();
        sf.mTitle = title;
        return sf;
    }


    @Override
    protected int getContentLayoutRes() {
        return R.layout.fr_simple_card;
    }

    @Override
    protected void initView(View view) {
        hideTitleBar();
        cardTitleTv.setText(mTitle);

        options1Items01.add("广东");
        options1Items01.add("广西");
        options1Items01.add("湖南");
        options1Items01.add("湖北");
        options1Items01.add("西藏");
        showPickerView();

        initTimePicker();

        cardTitleTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                pvOptions.show();
//                pvTime.show();
            }
        });
    }

    @Override
    protected void initHttpData() {

    }

    private void showPickerView() {// 弹出选择器
        pvOptions = new OptionsPickerBuilder(mActivity, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                BToast.showText(mActivity,options1Items01.get(options1));
                cardTitleTv.setText(options1Items01.get(options1));
            }
        })
                .setTitleText("单选")
                .setDividerColor(getResources().getColor(R.color.C2_1))
                .setTextColorCenter(getResources().getColor(R.color.black)) //设置选中项文字颜色
                .setContentTextSize(18)
                .setSubmitColor(getResources().getColor(R.color.colorPrimary))//确定按钮文字颜色
                .setCancelColor(getResources().getColor(R.color.gray))//取消按钮文字颜色
                .build();
        pvOptions.setPicker(options1Items01);
    }

    /**
     * 时间选择
     */
    private void initTimePicker() {
        Calendar selectedDate = Calendar.getInstance();
        //时间选择器
        pvTime = new TimePickerBuilder(mActivity, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                BToast.showText(mActivity,getTime(date));
            }
        }).setTitleText("时间选择")
                .setDate(selectedDate)
                .setType(new boolean[]{true, true, true, false, false, false})
                .setDividerColor(getResources().getColor(R.color.C2_1))
                .setTextColorCenter(getResources().getColor(R.color.black)) //设置选中项文字颜色
                .setContentTextSize(18).isCenterLabel(true) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setSubmitColor(getResources().getColor(R.color.colorPrimary))//确定按钮文字颜色
                .setCancelColor(getResources().getColor(R.color.gray))//取消按钮文字颜色
                .build();
    }

    public static String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd ");
        return format.format(date);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}