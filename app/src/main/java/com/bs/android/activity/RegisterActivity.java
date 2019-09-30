package com.bs.android.activity;

import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bs.android.R;
import com.bs.android.constant.SPConsts;
import com.bs.android.http.ConstantUrl;
import com.bs.android.customview.dialog.ActionSheetDialog;
import com.bs.android.model.EventBusModel;
import com.bs.android.model.mg.UserDataModel;
import com.bs.android.utils.CommentUtil;
import com.bs.android.utils.LoginUtil;
import com.bs.android.utils.SPUtil;
import com.bs.android.http.Callback;
import com.bs.android.http.HttpUtils;
import com.bs.android.http.JsonUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 注册界面
 */
public class RegisterActivity extends BaseActivity {

    @BindView(R.id.et_register_phone)
    EditText et_register_phone;

    @BindView(R.id.et_user_pwd)
    EditText et_user_pwd;

    @BindView(R.id.et_user_pwd_again)
    EditText et_user_pwd_again;

    @BindView(R.id.et_sms_code)
    EditText et_sms_code;

    @BindView(R.id.et_invitation_code)
    EditText et_invitation_code;

    @BindView(R.id.tv_get_sms_code)
    TextView tv_get_sms_code;

    @BindView(R.id.tv_phone_head)
    TextView tv_phone_head;
    @BindView(R.id.rl_login_back)
    RelativeLayout rl_login_back;

    private LoginUtil loginUtil;

    @Override
    protected int getContentLayoutRes() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
      hideTitleBar();
        loginUtil = new LoginUtil();
    }

    @Override
    protected void initHttpData() {
        if (loginUtil!=null){
            loginUtil.getIntoTime(tv_get_sms_code);
        }
    }

    @OnClick({R.id.ll_option_head,R.id.tv_get_sms_code,R.id.bt_user_register,R.id.ll_user_agreement,R.id.rl_login_back})
    public void onViewClick(View view){

        switch (view.getId()){

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
                sendSmsCode("1");
                break;

            case R.id.bt_user_register:
                /**
                 * 点击注册
                  */
                register();
                break;

            case R.id.ll_user_agreement:
                /**
                 * 用户协议
                 */
//                startActivity(UserAgreeActivity.class);

                break;
            case R.id.rl_login_back:
                this.finish();
                break;

        }

    }


    /**
     * 注册
     */
    private void register(){

        String prefix=getPhoneHead(tv_phone_head.getText().toString());
        String phone = et_register_phone.getText().toString().trim();
        String password = et_user_pwd.getText().toString().trim();
        String passwordAgian=et_user_pwd_again.getText().toString().trim();
        String smsCode=et_sms_code.getText().toString().trim();
        String invitationCode=et_invitation_code.getText().toString().trim();


        if (!LoginUtil.checkPhone(phone)){
            return;
        }

        if (!LoginUtil.checkSmsCode(smsCode)){
            return;
        }

        if (!LoginUtil.checkPassword(password)){
            return;
        }

//        if (!LoginUtil.checkPassword(passwordAgian)){
//            return;
//        }

        Map map=new HashMap();
        map.put("prefix",prefix);
        map.put("mobile",phone);
        map.put("code",smsCode);
        map.put("password",password);
//        map.put("again",passwordAgian);
        map.put("inviter",invitationCode);
        HttpUtils.POST(ConstantUrl.USER_REGISTER, map, UserDataModel.class, new Callback<UserDataModel>(){
            @Override
            public void onStarted() {

            }

            @Override
            public void onSucceed(String json, String code, UserDataModel userDataModel) {
                SPUtil.put(SPConsts.USER_DATA, JSONObject.toJSONString(userDataModel));
                String desc = JsonUtils.getSinglePara(json, "desc");
                CommentUtil.showSingleToast(desc);
                EventBus.getDefault().post(new EventBusModel("close_login"));
                EventBus.getDefault().post(new EventBusModel("login_success"));
                removeActivity(RegisterActivity.this);
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

        String prefix=getPhoneHead(tv_phone_head.getText().toString());
        String phone = et_register_phone.getText().toString().trim();
        /**
         * 检查手机号
         */
        if (!LoginUtil.checkPhone(phone)) {
            return;
        }

        Map map=new HashMap();
        map.put("mobile",phone);
        map.put("type",type);
        map.put("prefix",prefix);
        HttpUtils.POST(ConstantUrl.SEND_SMS_CODE, map, new Callback() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onSucceed(String json, String code, Object o) {

                CommentUtil.showSingleToast("短信发送成功");
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
    private void showOptionHead(){
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


    private String getPhoneHead(String head){
        if (!TextUtils.isEmpty(head)){
            return head.substring(1, head.length());
        }
        return "";
    }

    @Override
    protected void onDestroy() {
        if (loginUtil!=null){
                loginUtil. getOutTime();
            loginUtil.cancelDaojishi();
        }

        super.onDestroy();
    }
}
