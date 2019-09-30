package com.bs.android.activity;


import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bs.android.App;
import com.bs.android.R;
import com.bs.android.customview.dialog.ActionSheetDialog;
import com.bs.android.http.Callback;
import com.bs.android.http.ConstantUrl;
import com.bs.android.http.HttpUtils;
import com.bs.android.http.JsonUtils;
import com.bs.android.utils.CommentUtil;
import com.bs.android.utils.DataSafeUtils;
import com.bs.android.utils.LoginUtil;
import com.bs.android.utils.SPUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 修改密码
 */
public class ChangePwdActivity extends BaseActivity {
    @BindView(R.id.et_find_phone)
    EditText et_find_phone;

    @BindView(R.id.et_user_pwd)
    EditText et_user_pwd;

    @BindView(R.id.et_user_pwd_again)
    EditText et_user_pwd_again;

    @BindView(R.id.et_sms_code)
    EditText et_sms_code;

    @BindView(R.id.tv_get_sms_code)
    TextView tv_get_sms_code;

    @BindView(R.id.tv_phone_head)
    TextView tv_phone_head;

    private String mUid="";
    private String mToken="";
    private String mUrl="";
//    private LoginUtil loginUtil;

    @Override
    protected int getContentLayoutRes() {
        return R.layout.activity_change_pwd;
    }

    @Override
    protected void initView() {
        loginUtil = new LoginUtil();
        setMyTitle("修改登录密码");

        if (SPUtil.getUserDataModel() != null) {
            mUid = SPUtil.getUserDataModel().getData().getUid();
            mToken = SPUtil.getUserDataModel().getData().getToken();
            if (!DataSafeUtils.isEmpty(SPUtil.getUserDataModel().getData().getMobile()))
                et_find_phone.setText(SPUtil.getUserDataModel().getData().getMobile());
        }
        String url =this.getIntent().getStringExtra("url");
        if (!DataSafeUtils.isEmpty(url)){
            this.mUrl = url;
        }
    }

    @Override
    protected void initHttpData() {
        if (loginUtil != null) {
            loginUtil.getIntoTime(tv_get_sms_code);
        }
    }

    @OnClick({R.id.ll_option_head, R.id.tv_get_sms_code, R.id.bt_user_forget_pwd})
    public void onViewClick(View view) {

        switch (view.getId()) {

            case R.id.ll_option_head:
                /**
                 * 选择国际区号
                 */
                showOptionHead();
                break;

            case R.id.tv_get_sms_code:
                /**
                 * 获取验证码
                 */
                sendSmsCode("3");
                break;

            case R.id.bt_user_forget_pwd:
                /**
                 * 修改密码提交
                 */
                submitForgetPwd(mUrl);
                break;

        }

    }


    /**
     * 修改密码提交
     */
    private void submitForgetPwd(String url) {

//        String prefix = getPhoneHead(tv_phone_head.getText().toString());
        String phone = et_find_phone.getText().toString().trim();
        String password = et_user_pwd.getText().toString().trim();
        String passwordAgian = et_user_pwd_again.getText().toString().trim();
        String smsCode = et_sms_code.getText().toString().trim();

//        if (!LoginUtil.checkPhone(phone)) {
//            return;
//        }

        if (!LoginUtil.checkPassword(password)) {
            return;
        }

//        if (!LoginUtil.checkPassword(passwordAgian)){
//            return;
//        }

        if (!LoginUtil.checkSmsCode(smsCode)) {
            return;
        }

        Map map = new HashMap();
//        map.put("prefix", prefix);
//        map.put("mobile", phone);
        map.put("uid", mUid);
        map.put("token", mToken);
        map.put("code", smsCode);
        map.put("password", password);
//        map.put("again",passwordAgian);
        HttpUtils.POST(url, map, new Callback() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onSucceed(String json, String code, Object o) {

                String desc = JsonUtils.getSinglePara(json, "desc");
                CommentUtil.showSingleToast(desc);
                App.removeActivity(ChangePwdActivity.this);

            }

            @Override
            public void onOtherStatus(String json, String code) {

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
     * 发送验证码
     * 1.注册时发短信，2，忘记登录密码时发短信，3修改密码时发短信，4修改支付密码时发短信 5.绑定手机号
     */
    private void sendSmsCode(String type) {
        String prefix = getPhoneHead(tv_phone_head.getText().toString());
        String phone = et_find_phone.getText().toString().trim();
        /**
         * 检查手机号
         */
        if (!LoginUtil.checkPhone(phone)) {
            return;
        }

        Map map = new HashMap();
        map.put("mobile", phone);
//        map.put("prefix", prefix);
        map.put("type", type);
        HttpUtils.POST(ConstantUrl.SEND_SMS_CODE, map, new Callback() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onSucceed(String json, String code, Object o) {

                CommentUtil.showSingleToast("获取验证码成功");
                loginUtil.startDaoJiShi(tv_get_sms_code);
            }

            @Override
            public void onOtherStatus(String json, String code) {

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
     * 选择手机号
     */
    private void showOptionHead() {
        CommentUtil.hideSoftInput(this);

        new ActionSheetDialog(this)
                .builder()
                .setTitle("选择国际区号")
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .addSheetItem("+63", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                tv_phone_head.setText("+63");
                            }
                        })
                .addSheetItem("+86", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                tv_phone_head.setText("+86");
                            }
                        })
                .addSheetItem("+66", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                tv_phone_head.setText("+66");
                            }
                        })
                .addSheetItem("+84", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                tv_phone_head.setText("+84");
                            }
                        })
                .show();


    }


    private String getPhoneHead(String head) {
        if (!TextUtils.isEmpty(head)) {
            return head.substring(1, head.length());
        }
        return "";
    }

    @Override
    protected void onDestroy() {
        if (loginUtil != null) {
            loginUtil.getOutTime();
            loginUtil.cancelDaojishi();
        }
        super.onDestroy();
    }


}
