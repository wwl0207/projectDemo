package com.bs.android.customview.dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bs.android.R;
import com.bs.android.model.EventBusModel;
import com.bs.android.utils.ButtonUtils;
import com.bs.android.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

public class EditDialog extends DialogFragment {


    private EditText et_password;
    private TextView tv_error_msg;
    private RelativeLayout rl_dialog_back;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //添加这一行
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);


        Bundle bundle = getArguments();
        int type = bundle.getInt("type");

        View root = getActivity().getLayoutInflater().inflate(R.layout.view_input_passwords, null);


        rl_dialog_back = root.findViewById(R.id.rl_dialog_back);
        rl_dialog_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDismiss();
                EventBus.getDefault().post(new EventBusModel("back_finger_print"));
            }
        });

        if (type == 1) {
            rl_dialog_back.setVisibility(View.VISIBLE);
        } else {
            rl_dialog_back.setVisibility(View.GONE);
        }

        et_password = (EditText) root.findViewById(R.id.et_password);
        et_password.setTransformationMethod(new PasswordSpotEdit());

        /**
         * 打开输入法
         */
        et_password.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        tv_error_msg = root.findViewById(R.id.tv_error_msg);

        TextView tv_forget_password = root.findViewById(R.id.tv_forget_password);
        tv_forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShortToast("开始跳转吗");
//                Intent intent = new Intent(getContext(), ChanngePayPwdActivity.class);
//                startActivity(intent);
                dismiss();
            }
        });

        TextView tv_confirm_passwords = (TextView) root.findViewById(R.id.tv_confirm_passwords);

        tv_confirm_passwords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ButtonUtils.isFastDoubleClick(R.id.tv_confirm_passwords)) {
                    EventBus.getDefault().post(new EventBusModel("password_confirm_dialog"));
                }
            }
        });

        return root;
    }


    /**
     * 显示错误信息
     */
    public void showErrorMsg() {
        Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
        et_password.startAnimation(shake);
        tv_error_msg.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_error_msg.setVisibility(View.INVISIBLE);
            }
        }, 1000);
    }


    /**
     * 获取编辑框的密码
     *
     * @return
     */
    public String getEditText() {
        return et_password.getText().toString().trim();
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        final Activity activity = getActivity();
        if (activity instanceof DialogInterface.OnDismissListener) {
            ((DialogInterface.OnDismissListener) activity).onDismiss(dialog);
        }
    }


    /**
     * 关闭弹窗
     */
    public void dialogDismiss() {
        dismiss();
    }


}