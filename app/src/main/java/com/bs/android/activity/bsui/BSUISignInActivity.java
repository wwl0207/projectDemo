package com.bs.android.activity.bsui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bs.android.R;
import com.bs.android.activity.BaseActivity;
import com.bs.android.customview.sign.SignCalendar;
import com.bs.android.http.Callback;
import com.bs.android.http.ConstantUrl;
import com.bs.android.http.HttpUtils;
import com.bs.android.model.signVO;
import com.bs.android.utils.CommentUtil;
import com.bs.android.utils.DataSafeUtils;
import com.bs.android.utils.SPUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * created by WWL on 2019/4/23 0023:18
 * 签到
 */
public class BSUISignInActivity extends BaseActivity {
    @BindView(R.id.backimg)
    ImageView backimg;
    @BindView(R.id.myintegral_tv)
    TextView myintegral_tv;//我的积分
    @BindView(R.id.sign_days)
    TextView sign_days;//连续签到几次
    @BindView(R.id.tv_sign_year_month)
    TextView tvSignYearMonth;
    @BindView(R.id.rl_current_date)
    RelativeLayout rlCurrentDate;
    @BindView(R.id.sc_main)
    SignCalendar calendar;
    @BindView(R.id.sign_rule_tv)
    TextView sign_rule_tv;
    @BindView(R.id.sign_submit)
    Button signSubmit;
    List<String> list = new ArrayList<String>();
    @BindView(R.id.lastMonth_img)
    ImageView lastMonthImg;
    @BindView(R.id.nextMonth_img)
    ImageView nextMonthImg;
    @BindView(R.id.right_tv)
    TextView right_tv;
    private int month;
    private int year;
    private String date;
    private String mUid = "";
    private String mToken = "";
    private signVO.DataBean mData = new signVO.DataBean();

    @SuppressLint("NewApi")
    @Override
    protected int getContentLayoutRes() {
        return R.layout.bsui_signin_activity;
    }


    @Override
    protected void initView() {
        hideTitleBar();

        if (SPUtil.getUserDataModel() != null) {
            mUid = SPUtil.getUserDataModel().getData().getUid();
            mToken = SPUtil.getUserDataModel().getData().getToken();
        }

        //接收传递过来的初始化数据

        month = Calendar.getInstance().get(Calendar.MONTH);
        year = Calendar.getInstance().get(Calendar.YEAR);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        date = formatter.format(curDate);

        tvSignYearMonth.setText(year + "年" + (month + 1) + "月");//设置日期
    }

    @Override
    protected void initHttpData() {
        showDialog();
        HashMap<String, String> map = new HashMap<>();
        map.put("uid", mUid);
        map.put("token", mToken);
        HttpUtils.POST(ConstantUrl.MYSIGNLIST, map, signVO.class, new Callback<signVO>() {
            @Override
            public void onStarted() {
            }

            @Override
            public void onSucceed(String json, String code, final signVO model) {
                if (!DataSafeUtils.isEmpty(model.getData().getRule_info() )) {
                    sign_rule_tv.setText(model.getData().getRule_info() + "");
                }
                if (!DataSafeUtils.isEmpty(model.getData().getIntegral())) {
                    myintegral_tv.setText(model.getData().getIntegral());
                }
                sign_days.setText("");//连续签到多少次
                if (!DataSafeUtils.isEmpty(model.getData().getDay())) {
                    String signDay = model.getData().getDay();
                    String[] splitDay = signDay.split(",");
                    for (int i = 0; i < splitDay.length; i++) {
                        list.add(splitDay[i]);
                    }
                }
                calendar.setCalendarDaysBgColor(list, R.drawable.sign_nowday_bg);

                if (model.getData().getSign().equals("1")) {//1是已签到，0是未签到
                    signSubmit.setBackground(CommentUtil.setBackgroundShap(BSUISignInActivity.this, 30, R.color.C5, R.color.C5));
                    signSubmit.setText("已签到");
                    signSubmit.setClickable(false);
                } else {
                    signSubmit.setBackground(CommentUtil.setBackgroundShap(BSUISignInActivity.this, 30, R.color.actionsheet_red, R.color.actionsheet_red));
                    signSubmit.setText("签到");
                }

            }

            @Override
            public void onOtherStatus(String json, String code) {

            }

            @Override
            public void onFailed(Throwable e) {
                dismissDialog();
            }

            @Override
            public void onFinish() {
                dismissDialog();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.backimg, R.id.sign_submit, R.id.lastMonth_img, R.id.nextMonth_img,R.id.right_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backimg:
                this.finish();
                break;
            case R.id.sign_submit:
                initSignCalendarData();//判断是否签到成功接口
//                showRegistDialog();
                break;
            case R.id.lastMonth_img:
                calendar.lastMonth(tvSignYearMonth);
                break;
            case R.id.nextMonth_img:
                calendar.nextMonth(tvSignYearMonth);
                break;
            case R.id.right_tv:
//                openActivity(MyIntegralActivity.class);
                break;

        }
    }


    /**
     * 弹框获取优惠券
     */
//    private void showRegistDialog(String money) {
//        final LayoutInflater inflater = getLayoutInflater();
//        View view = inflater.inflate(R.layout.signin_dialog_layout, null);
//
//        TextView diaolog_conent = view.findViewById(R.id.diaolog_conent);
//        TextView diaolog_money = view.findViewById(R.id.diaolog_money);
//        diaolog_conent.setText(money + "元优惠券");
//        diaolog_money.setText("" + money);
//
//        final Dialog dialog = new Dialog(this, R.style.MyRedPackageDialog3);
//        dialog.setContentView(view);
//        dialog.setCanceledOnTouchOutside(false);
//        Window dialogWindow = dialog.getWindow();
//        WindowManager m = this.getWindowManager();
//        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高度
//        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
//        p.height = (int) (d.getHeight() * 0.68); // 高度设置为屏幕的0.6
//        p.width = (int) (d.getWidth() * 0.78); // 宽度设置为屏幕的0.65
//        dialogWindow.setAttributes(p);
//
//        dialog.show();
//        ImageView iv_cancel = view.findViewById(R.id.iv_cancel);
//        TextView bonus_btn = view.findViewById(R.id.bonus_btn);
//        bonus_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent intent = new Intent(BSUISignInActivity.this, MyBalanceActivity.class);
////                startActivity(intent);
//                removeActivity(BSUISignInActivity.this);
//            }
//        });
//        iv_cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (dialog != null)
//                    dialog.dismiss();
//            }
//        });
//
//    }


    //点击签到按钮网络请求数据
    private void initSignCalendarData() {
        showDialog();
        HashMap<String, String> map = new HashMap<>();
        map.put("uid", mUid);
        map.put("token", mToken);
        HttpUtils.POST(ConstantUrl.MYSIGN, map, signVO.class, new Callback<signVO>() {
            @Override
            public void onStarted() {
            }

            @Override
            public void onSucceed(String json, String code, final signVO model) {
                calendar.setCalendarDayBgColor(date, R.drawable.sign_nowday_bg);
                signSubmit.setBackground(CommentUtil.setBackgroundShap(BSUISignInActivity.this, 30, R.color.C5, R.color.C5));
                signSubmit.setText("已签到");
                signSubmit.setClickable(false);
                initHttpData();
            }

            @Override
            public void onOtherStatus(String json, String code) {

            }

            @Override
            public void onFailed(Throwable e) {
                dismissDialog();
            }

            @Override
            public void onFinish() {
                dismissDialog();
            }
        });


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
