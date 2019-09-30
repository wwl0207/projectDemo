package com.bs.android.activity.bsui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bs.android.R;
import com.bs.android.activity.BaseActivity;
import com.bs.android.customview.dialog.ActionSheetDialog;
import com.bs.android.customview.dialog.AlertDialog;
import com.bs.android.utils.ToastUtils;
import com.rey.material.app.BottomSheetDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * created by WWL on 2019/9/10 0010:09
 * 几种弹框功能
 */
public class BSULDialogActivity extends BaseActivity {
    @BindView(R.id.tv01)
    TextView tv01;
    @BindView(R.id.tv02)
    TextView tv02;
    AlertDialog dialog;
    OptionsPickerView pickerView01, pickerView02;
    private TimePickerView pvTime;
    private ArrayList<String> options1Items01 = new ArrayList<>();
    private ArrayList<ArrayList<String>> options1Items2 = new ArrayList<>();

    @Override
    protected int getContentLayoutRes() {
        return R.layout.bsui_dialog_layout;
    }

    @Override
    protected void initView() {
        setMyTitle("一般会用到的两种弹框样式");

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

    @OnClick({R.id.tv01, R.id.tv02, R.id.tv03, R.id.tv04, R.id.tv05, R.id.tv06})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv01:
                showCenterDialog();
                break;
            case R.id.tv02:
                showBottomDialog01();
                break;
            case R.id.tv03:
                showBottomDialog02();
                break;
            case R.id.tv04:
                showBottomDialog03();
                break;
            case R.id.tv05:
                showBottomDialog04();
                break;
            case R.id.tv06:
                showBottomPicker();
                break;
        }
    }


    /**
     * setNegativeButton/setPositiveButton两个按钮可以随机选择，一个或者两个，默认是一个
     * setPosBtnColor/setNegBtnColor可以更改两个按钮的字体颜色
     * setCancelable可设置是否点击空白区域消失与否，默认触摸消失
     */
    private void showCenterDialog() {
        dialog = new AlertDialog(BSULDialogActivity.this);
        dialog.builder();
        dialog.setTitle("居中弹框");
        dialog.setMsg("这个是内容提示");
        dialog.setCancelable(false);
        dialog.setNegativeButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO：这里是需要的功能
            }
        });
//        dialog.setPositiveButton("取消", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        dialog.setPosBtnColor(getResources().getColor(R.color.actionsheet_red));
        dialog.show();
    }

    private void showBottomDialog01() {
        new ActionSheetDialog(this)
                .builder()
                .setTitle("标题提示")
                .addSheetItem("第一条", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {

                    }
                })
                .addSheetItem("第二条", ActionSheetDialog.SheetItemColor.Red, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {

                    }
                })
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .show();
    }


    private void showBottomDialog02() {
//        View view = LayoutInflater.from(BSULDialogActivity.this).inflate(R.layout.bsui_bottomsheet_dialog, null);
        View dialogView = View.inflate(this, R.layout.bsui_bottomsheet_dialog, null);
        BottomSheetDialog sheetDialog = new BottomSheetDialog(this);
        sheetDialog.setContentView(dialogView);
        sheetDialog.show();
    }

    private void showBottomDialog03() {
        options1Items01.add("广东");
        options1Items01.add("湖南");
        options1Items01.add("广西");

        pickerView01 = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                ToastUtils.showLongToast(options1Items01.get(options1));
            }
        })
                .setTitleText("单选")
                .setDividerColor(getResources().getColor(R.color.gray))
                .setTextColorCenter(getResources().getColor(R.color.gray)) //设置选中项文字颜色
                .setContentTextSize(18)
                .setSubmitColor(getResources().getColor(R.color.colorPrimary))//确定按钮文字颜色
                .setCancelColor(getResources().getColor(R.color.gray))//取消按钮文字颜色
                .build();
        pickerView01.setPicker(options1Items01);
        pickerView01.show();
    }

    private void showBottomDialog04() {
        ArrayList<String> options2Items_01 = new ArrayList<>();
        options2Items_01.add("广州");
        options2Items_01.add("佛山");
        options2Items_01.add("东莞");
        options2Items_01.add("珠海");
        ArrayList<String> options2Items_02 = new ArrayList<>();
        options2Items_02.add("长沙");
        options2Items_02.add("岳阳");
        options2Items_02.add("株洲");
        options2Items_02.add("衡阳");
        ArrayList<String> options2Items_03 = new ArrayList<>();
        options2Items_03.add("桂林");
        options2Items_03.add("玉林");
        options1Items2.add(options2Items_01);
        options1Items2.add(options2Items_02);
        options1Items2.add(options2Items_03);

        pickerView02 = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                ToastUtils.showLongToast(options1Items01.get(options1)
                        + options1Items2.get(options1).get(options2));
            }
        })
                .setTitleText("多选")
                .setDividerColor(getResources().getColor(R.color.gray))
                .setTextColorCenter(getResources().getColor(R.color.gray)) //设置选中项文字颜色
                .setContentTextSize(18).isRestoreItem(true)
                .setLabels("省", "市", null)//设置选择的三级单位
                .setSubmitColor(getResources().getColor(R.color.colorPrimary))//确定按钮文字颜色
                .setCancelColor(getResources().getColor(R.color.gray))//取消按钮文字颜色
                .build();
        pickerView02.setPicker(options1Items01, options1Items2);
        pickerView02.show();
    }

    private void showBottomPicker(){
            Calendar selectedDate = Calendar.getInstance();
            //时间选择器
            pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    ToastUtils.showShortToast(getTime(date));
                }
            }).setTitleText("时间选择")
                    .setDate(selectedDate)
                    .setType(new boolean[]{true, true, true, true, false, false})
                    .setDividerColor(getResources().getColor(R.color.gray))
                    .setTextColorCenter(getResources().getColor(R.color.gray)) //设置选中项文字颜色
                    .setContentTextSize(18).isCenterLabel(true) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                    .setSubmitColor(getResources().getColor(R.color.colorPrimary))//确定按钮文字颜色
                    .setCancelColor(getResources().getColor(R.color.gray))//取消按钮文字颜色
                    .build();
        pvTime.show();
    }

    public static String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd ");
        return format.format(date);
    }
}
