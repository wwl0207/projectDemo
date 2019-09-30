package com.bs.android.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.bs.android.R;
import com.bs.android.customview.dialog.ActionSheetDialog;
import com.bs.android.model.EventBusModel;
import com.bs.android.utils.CommentUtil;
import com.bs.android.utils.LoginUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登录界面
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_account_phone)
    EditText et_account_phone;

    @BindView(R.id.et_account_pwd)
    EditText et_account_pwd;

    @BindView(R.id.tv_phone_head)
    TextView tv_phone_head;

    private String isfrom;


    public static Intent createIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }
    @Override
    protected int getContentLayoutRes() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        hideTitleBar();

        isfrom = getIntent().getStringExtra("isfrom");



    }

    @Override
    protected void initHttpData() {

    }


    @OnClick({R.id.rl_login_back,R.id.ll_option_head,R.id.tv_forget_pwd,R.id.tv_register,R.id.bt_account_login})
    public void onViewClick(View view){

        switch (view.getId()){

            case R.id.rl_login_back:
                /**
                 * 登录返回
                 */
                if (TextUtils.isEmpty(isfrom)) {
                    startActivity(MainActivity.class);
                }
                removeActivity(this);
                break;

            case R.id.ll_option_head:
                /**
                 * 选择国际区号
                 */
                showOptionHead();
                break;

            case R.id.tv_forget_pwd:
                /**
                 * 忘记密码
                 */
                startActivity(ForgetPwdActivity.class);
                break;

            case R.id.tv_register:
                /**
                 * 注册
                 */
                startActivity(RegisterActivity.class);
                break;

            case R.id.bt_account_login:
                /**
                 * 登录
                 */

                login();

                break;

        }

    }





    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN&&TextUtils.isEmpty(isfrom)) {
            startActivity(MainActivity.class);
            removeActivity(this);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * 账号登陆
     */
    private void login(){

        String prefix=getPhoneHead(tv_phone_head.getText().toString());
        String phone=et_account_phone.getText().toString().trim();
        String password=et_account_pwd.getText().toString().trim();

        /**
         * 检查手机号是否合法
         */
        if (!LoginUtil.checkPhone(phone)){
            return;
        }

        /**
         * 检查密码是否合法
         */
        if (!LoginUtil.checkPassword(password)){
            return;
        }
        LoginUtil.accountLogin(this,prefix,phone,password,isfrom);

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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleEventBus(EventBusModel eventBusModel){
        String code = eventBusModel.getCode();
        switch (code){
            case "login_success":
                removeActivity(this);
                break;


        }

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
