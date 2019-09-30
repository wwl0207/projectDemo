package com.bs.android.activity.bsui;

import android.app.Dialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bs.android.R;
import com.bs.android.activity.BaseActivity;
import com.bs.android.customview.dialog.AlertDialog;
import com.bs.android.customview.dialog.EditDialog;
import com.bs.android.http.Callback;
import com.bs.android.http.ConstantUrl;
import com.bs.android.http.HttpUtils;
import com.bs.android.http.PayCallback;
import com.bs.android.model.EventBusModel;
import com.bs.android.model.pay.OrderCrateVO;
import com.bs.android.model.pay.OrderLineVO;
import com.bs.android.model.pay.PayResult;
import com.bs.android.model.pay.PayUtis;
import com.bs.android.utils.ButtonUtils;
import com.bs.android.utils.CommentUtil;
import com.bs.android.utils.DataSafeUtils;
import com.bs.android.utils.SPUtil;
import com.bs.android.utils.ToastUtils;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;

/**
 * created by WWL on 2019/9/10 0010:09
 * 支付界面
 */
public class BSULPayActivity extends BaseActivity {

    @BindView(R.id.pay_over_time)
    TextView payOverTime;//支付超时倒计时
    @BindView(R.id.pay_money)
    TextView payMoney;//多少人民币
    @BindView(R.id.pay_cb_1)
    ImageView mPayCb1;
    @BindView(R.id.pay_layout_01)
    LinearLayout pay_layout_01;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.pay_cb_2)
    ImageView mPayCb2;
    @BindView(R.id.pay_layout_02)
    LinearLayout pay_layout_02;
    @BindView(R.id.pay_cb_3)
    ImageView mPayCb3;
    @BindView(R.id.pay_layout_03)
    LinearLayout pay_layout_03;
    @BindView(R.id.pay_cb_0)
    ImageView mPayCb0;
    @BindView(R.id.pay_layout_0)
    LinearLayout pay_layout_0;
    @BindView(R.id.ok_tv)
    TextView okTv;
    @BindView(R.id.tv_wallet_num)
    TextView tvWalletNum;
    //============================支付方式=========================================
    private String mPayType = "1";     //"2" 微信支付，"1" 支付宝支付
    private String mOrderId = "";
    private String mOrderNo = "";

    private EditDialog passwordsDialog;
    private OrderCrateVO mDataBean;
    private OrderLineVO.DataBean mData;
    private Double mPayMoney=0.00;//支付金额
    private Double mBalance=0.00;//余额
    private String mType = "";// 类型(1:其他 2:充值)
    private String mOrderType = "";//如果1表示付款   2表示充值
    private String mPinTuan = "";//1.拼团订单   2其他订单
    //倒计时
    CountDownTimer timer;

    @Override
    protected int getContentLayoutRes() {
        return R.layout.bsui_pay_layout;
    }

    @Override
    protected void initView() {
        boolean registered = EventBus.getDefault().isRegistered(this);
        if (!registered) {
            EventBus.getDefault().register(this);
        }

        setMyTitle("在线支付");
        if (SPUtil.getUserDataModel() != null) {
            mUid = SPUtil.getUserDataModel().getData().getUid();
            mToken = SPUtil.getUserDataModel().getData().getToken();
        }

        Bundle mBundle = this.getIntent().getExtras();
        if (mBundle != null) {
            mDataBean = (OrderCrateVO) mBundle.getSerializable("order");
        }

        if (mDataBean != null) {
            if (mDataBean.getData().getOrder_id() != null && !mDataBean.getData().getOrder_id().equals("")) {
                mOrderId = mDataBean.getData().getOrder_id();
                mOrderNo = mDataBean.getData().getOut_trade_no();
                mType = mDataBean.getData().getmType() + "";

            }
        }

        my_titlebar.setBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message();

            }
        });

    }

    @Override
    protected void initHttpData() {
        showDialog();
        HashMap<String, String> map = new HashMap<>();
        map.put("uid", mUid + "");
        map.put("token", mToken + "");
        map.put("out_trade_no", mOrderNo + "");
        HttpUtils.POST(ConstantUrl.ORDERLINE, map, OrderLineVO.class, new Callback<OrderLineVO>() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onSucceed(String json, String code, final OrderLineVO createVO) {
                mData = createVO.getData();
                if (!DataSafeUtils.isEmpty(createVO.getData().getPay_money()))
                    mPayMoney = Double.parseDouble(createVO.getData().getPay_money());
                if (!DataSafeUtils.isEmpty(createVO.getData().getMoney()))
                    mBalance = Double.parseDouble(createVO.getData().getMoney());
                if (!DataSafeUtils.isEmpty(createVO.getData().getType())) {
                    mOrderType = createVO.getData().getType();
                    if (createVO.getData().getType().equals("2")) {
                        pay_layout_0.setVisibility(View.GONE);
                    }
                }
                if (!DataSafeUtils.isEmpty(createVO.getData().getFlag())) {
                    mPinTuan = createVO.getData().getFlag();
                }
//                if (!DataSafeUtils.isEmpty(createVO.getData().getTime())) {
//                    TimeStart(Integer.parseInt(createVO.getData().getTime()));
//                }

                if (!DataSafeUtils.isEmpty(createVO.getData().getPay_money())) {
                    payMoney.setText("￥" + createVO.getData().getPay_money());
                }


                if (!DataSafeUtils.isEmpty(createVO.getData().getMoney())) {
                    tvWalletNum.setText("￥" + createVO.getData().getMoney());
                }
                if (Double.parseDouble(createVO.getData().getPay_money()) > Double.parseDouble(createVO.getData().getMoney())) {
                    selectPayment(true, false, false, false);
                } else {
                    if (mOrderType.equals("2")) {
                        selectPayment(true, false, false, false);
                    } else {
                        selectPayment(false, false, true, false);
                    }
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



    @OnClick({R.id.ok_tv, R.id.pay_layout_0, R.id.pay_layout_01, R.id.pay_layout_02, R.id.pay_layout_03})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ok_tv:
                if (!ButtonUtils.isFastDoubleClick(R.id.ok_tv)) {
                    commit();
                }

                break;

            case R.id.pay_layout_0:
                /**
                 * 余额
                 */
                if (mPayMoney > mBalance) {
                    Toast.makeText(this, "余额不足", Toast.LENGTH_SHORT).show();
                } else {
                    selectPayment(false, false, true, false);
                }
                break;
            case R.id.pay_layout_01:
                /**
                 * 支付宝
                 */
                selectPayment(true, false, false, false);

                break;


            case R.id.pay_layout_02:
                /**
                 * 微信
                 */
                selectPayment(false, true, false, false);
                break;

            case R.id.pay_layout_03:
                /**
                 * 银联
                 */
                selectPayment(false, false, false, true);

                break;
        }
    }


    /**
     * 选择支付方式
     *
     * @param op1
     * @param op2
     */
    private void selectPayment(boolean op1, boolean op2, boolean op3, boolean op4) {


        if (op1) {//支付宝
            mPayType = "1";
            mPayCb1.setImageResource(R.drawable.check_select);
            mPayCb0.setImageResource(R.drawable.check_normal);
            mPayCb2.setImageResource(R.drawable.check_normal);
            mPayCb3.setImageResource(R.drawable.check_normal);
        }


        if (op2) {//微信
            mPayType = "2";
            mPayCb2.setImageResource(R.drawable.check_select);
            mPayCb0.setImageResource(R.drawable.check_normal);
            mPayCb1.setImageResource(R.drawable.check_normal);
            mPayCb3.setImageResource(R.drawable.check_normal);
        }

        if (op3) {//余额
            mPayType = "3";
            mPayCb0.setImageResource(R.drawable.check_select);
            mPayCb1.setImageResource(R.drawable.check_normal);
            mPayCb2.setImageResource(R.drawable.check_normal);
            mPayCb3.setImageResource(R.drawable.check_normal);
        }

        if (op4) {//银联
            mPayType = "4";
            mPayCb3.setImageResource(R.drawable.check_select);
            mPayCb0.setImageResource(R.drawable.check_normal);
            mPayCb1.setImageResource(R.drawable.check_normal);
            mPayCb2.setImageResource(R.drawable.check_normal);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBusModel event) {
        Bundle bundle = new Bundle();
        bundle.putString("order_id", mOrderId);
        String code = event.getCode();
        switch (code) {
            case "weixin_pay":
                //支付成功
                payFinish(1);
                break;
            case "weixin_pay_false":
                //失败
                payFinish(2);
                break;
            case "password_confirm_dialog":
                /**
                 * 点击了密码支付的确认按钮
                 */
                String payPassword = passwordsDialog.getEditText();
                if (TextUtils.isEmpty(payPassword)) {
                    Toast.makeText(this, "支付密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                checkPaymentPasswords(payPassword);

                break;
        }

    }

    public void commit() {

        if ("1".equals(mPayType)) {
            /**
             * 支付宝支付
             */
            pay(mOrderNo);

        } else if ("2".equals(mPayType)) {
            /**
             * 微信支付
             */
            if (CommentUtil.isWeixinAvilible(this)) {
                pay(mOrderNo);
            } else {
                Toast.makeText(this, "请安装微信客户端", Toast.LENGTH_SHORT).show();
                return;
            }

        } else if ("3".equals(mPayType)) {
            /**
             * 余额支付
             */
            passwordsDialog = CommentUtil.showInputPasswords(BSULPayActivity.this, 0);
//            pay(mOrderNo);

        } else if ("4".equals(mPayType)) {
            /**
             * 银联支付
             */
            pay(mOrderNo);

        }

    }


    public void pay(String mOrderNo) {
        showDialog();
        if (SPUtil.getUserDataModel() != null) {
            mUid = SPUtil.getUserDataModel().getData().getUid();
            mToken = SPUtil.getUserDataModel().getData().getToken();
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("uid", mUid);
        map.put("token", mToken);
        map.put("out_trade_no", mOrderNo);
        map.put("pay_type", mPayType);
        map.put("type", mOrderType);
        HttpUtils.POST(ConstantUrl.MYORDERPAY, map, new Callback() {
            @Override
            public void onStarted() {
            }

            @Override
            public void onSucceed(String json, String code, Object o) {
                dismissDialog();
                Log.v("logger", json);
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    String code1 = (String) jsonObject.get("code");
                    if (code1.equals("200")) {

                        if ("2".equals(mPayType)) {
                            Gson gson = new Gson();
                            final PayResult vo = gson.fromJson(json, PayResult.class);
                            Log.v("logger", vo.getData().getOrder().getAppid() + "--");
                            PayUtis.weiXinPay(BSULPayActivity.this, vo.getData().getOrder());
                        } else if (mPayType.equals("1")) {
                            JSONObject data = (JSONObject) jsonObject.get("data");
                            String payInfo = (String) data.get("payOrder");
                            PayUtis.zhiFuBaoPay(BSULPayActivity.this, payInfo, new PayCallback() {
                                @Override
                                public void payResult(int type) {
//                                    if (type == 1) {
//                                        EventBus.getDefault().post(new EventBusModel("over_update"));
//                                    }
                                    payFinish(type);
                                }
                            });
                        } else if (mPayType.equals("3")) {
                            payFinish(1);
                        } else if (mPayType.equals("4")) {
                            //TODO:银联支付
                        }

                    } else if (code.equals("400")) {
                        String msg = (String) jsonObject.get("desc");
                        ToastUtils.showShortToast(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onOtherStatus(String json, String code) {
                dismissDialog();
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


    public void payFinish(int type) {
        if (type == 1) {
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.pay_result_dialog_layout, null);

            final Dialog dialog = new Dialog(BSULPayActivity.this, R.style.MyRedPackageDialog3);
            dialog.setContentView(view);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            Window dialogWindow = dialog.getWindow();
            WindowManager m = this.getWindowManager();
            Display d = m.getDefaultDisplay(); // 获取屏幕宽、高度
            WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
//        p.width = WindowManager.LayoutParams.MATCH_PARENT;
//        p.height = WindowManager.LayoutParams.MATCH_PARENT;
            p.height = (int) (d.getHeight() * 0.48); // 高度设置为屏幕的0.6
            p.width = (int) (d.getWidth() * 0.68); // 宽度设置为屏幕的0.65
            dialogWindow.setAttributes(p);

            dialog.show();
            CountdownView cuntdownView = view.findViewById(R.id.cuntdown_view);
            TextView payresulttv = view.findViewById(R.id.pay_result_tv);
            if (mOrderType.equals("1")) {
                payresulttv.setText("购买成功");
            } else {
                payresulttv.setText("充值成功");
            }

            cuntdownView.start(2000);
            cuntdownView.setOnCountdownIntervalListener(10, new CountdownView.OnCountdownIntervalListener() {
                @Override
                public void onInterval(CountdownView cv, long remainTime) {
                    int time = (int) (remainTime / 1000) + 1;
                }
            });
            cuntdownView.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
                @Override
                public void onEnd(CountdownView cv) {
                    inntentActivity("1");
                    dialog.dismiss();
                    dialog.cancel();
                    BSULPayActivity.this.finish();
                }
            });
        } else {
            inntentActivity("0");
        }
    }

    /**
     * 是否支付成功
     *
     * @param paystatus 是否支付成功
     */
    private void inntentActivity(String paystatus) {
        Bundle bundle = new Bundle();
        bundle.putString("order_id", mOrderNo);
        if (mType.equals("0")) {
            bundle.putString("type", "1");
//            open(MyWalletBalanceActivity.class, bundle, 0);
            BSULPayActivity.this.finish();
        } else if (mType.equals("1")) {
            if (mPinTuan.equals("1") && paystatus.equals("0")) {
                BSULPayActivity.this.finish();
            } else {
                if (mPinTuan.equals("1") && paystatus.equals("1")){
                    //拼团成功或者购买成功
                    EventBus.getDefault().post(new EventBusModel("group_refresh"));
                }
//                open(MyOrderDetailActivity.class, bundle, 0);
                BSULPayActivity.this.finish();
            }
        }
        BSULPayActivity.this.finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Message();
            return true;
        }
        return true;
    }


    /**
     * 离开弹框
     */
    private void Message() {
        AlertDialog  dialog = new AlertDialog(BSULPayActivity.this);
        dialog.builder();
        dialog.setTitle("确认要放弃付款吗？");
        dialog.setMsg("未支付订单将会自动关闭，请尽快支付~");
        dialog.setCancelable(false);

        dialog.setNegativeButton("暂时放弃", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO：这里是需要的功能
                BSULPayActivity.this.finish();
            }
        });
        dialog.setPositiveButton("继续支付", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        dialog.setPosBtnColor(getResources().getColor(R.color.actionsheet_red));
        dialog.show();

    }


    /**
     * 验证余额支付的密码
     *
     * @param payPassword
     */
    private void checkPaymentPasswords(String payPassword) {
        if (!isLogin()) {
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("uid", mUid);
        map.put("token", mToken);
        map.put("pay_password", payPassword);
        HttpUtils.POST(ConstantUrl.CHECKPAYPASSWORD, map, new Callback() {

            @Override
            public void onStarted() {

            }

            @Override
            public void onSucceed(String json, String code, Object o) {
//                if ("1".equals(verifyPayPassword)) {
                /**
                 * 密码正确
                 */
                if (passwordsDialog != null) {
                    passwordsDialog.dialogDismiss();
                }
                pay(mOrderNo);

//                } else if ("0".equals(verifyPayPassword)) {
//                    /**
//                     * 密码错误
//                     */
//                    if (passwordsDialog != null) {
//                        passwordsDialog.showErrorMsg();
//                    }
//
//
//                }
            }

            @Override
            public void onOtherStatus(String json, String code) {
                /**
                 * 密码错误
                 */
                if (passwordsDialog != null) {
                    passwordsDialog.showErrorMsg();
                }
            }

            @Override
            public void onFailed(Throwable e) {

            }

            @Override
            public void onFinish() {

            }
        });


    }


    /**
     * 倒数计时器
     */
    private void TimeStart(final int mTimes) {
        /****
         * 倒计时方法如下---------------------------------------------
         */
        timer = new CountDownTimer(mTimes * 1000, 1000) {

            /**
             * 固定间隔被调用,就是每隔countDownInterval会回调一次方法onTick
             * @param millisUntilFinished
             */
            @Override
            public void onTick(long millisUntilFinished) {
                String mTime = formatTime(millisUntilFinished);
                payOverTime.setText(mTime);
            }

            /**
             * 倒计时完成时被调用
             */
            @Override
            public void onFinish() {
                Log.v("logger", "倒计时完毕了");
                payOverTime.setText("00:00");

                final Bundle bundle = new Bundle();
                bundle.putString("order_id", mOrderId);
//                if (mType.equals("0")) {
//                    open(MyBalanceActivity.class, bundle, 0);
//                } else if (mType.equals("1")) {
//                open(MyOrderDetailActivity.class, bundle, 0);
//                }
                BSULPayActivity.this.finish();

            }
        };
        timer.start();
    }

    /**
     * 将毫秒转化为 分钟：秒 的格式
     *
     * @param millisecond 毫秒
     * @return
     */
    public String formatTime(long millisecond) {
        int minute;//分钟
        int second;//秒数
        minute = (int) ((millisecond / 1000) / 60);
        second = (int) ((millisecond / 1000) % 60);
        if (minute < 10) {
            if (second < 10) {
                return "0" + minute + ":" + "0" + second;
            } else {
                return "0" + minute + ":" + second;
            }
        } else {
            if (second < 10) {
                return minute + ":" + "0" + second;
            } else {
                return minute + ":" + second;
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        boolean registered = EventBus.getDefault().isRegistered(this);
        if (registered) {
            EventBus.getDefault().unregister(this);
        }
        try {
            if (timer != null) {
                timer.cancel();
                timer.onFinish();
            }
        } catch (Exception e) {
            Log.i("logger", e.getMessage());
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
