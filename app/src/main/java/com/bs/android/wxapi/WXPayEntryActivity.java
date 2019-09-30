package com.bs.android.wxapi;//package com.video.longvideo.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.bs.android.model.EventBusModel;
import com.bs.android.utils.CommentUtil;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by WWL on 2016/10/25.
 */

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.pay_result);


        api = WXAPIFactory.createWXAPI(this, "");
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {

    }

    @Override
    public void onResp(BaseResp resp) {
        Log.d("logger", "onPayFinish, errCode = " + resp.errCode);

        switch (resp.errCode){

            case 0:

                Log.e("logger", "" + "支付成功");
                EventBus.getDefault().post(new EventBusModel("payment_success", "wechatPay"));
                finish();

                break;

            case -1:
                EventBus.getDefault().post(new EventBusModel("payment_fail", "wechatPay"));
                Log.e("logger", "" + "支付失败");
                //                Toast.makeText(this,"支付失败", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 1000);

                break;

            case -2:
                CommentUtil.showSingleToast("用户取消");
                finish();
                break;

        }
    }



}
