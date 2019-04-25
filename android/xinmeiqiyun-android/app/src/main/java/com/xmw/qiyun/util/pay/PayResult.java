package com.xmw.qiyun.util.pay;

import android.text.TextUtils;

import com.xmw.qiyun.data.manager.StoreManager;
import com.xmw.qiyun.data.manager.UserManager;
import com.xmw.qiyun.data.model.event.ResultEvent;
import com.xmw.qiyun.data.model.net.CommonResponse;
import com.xmw.qiyun.data.model.net.store.PayResultBody;
import com.xmw.qiyun.net.api.API;
import com.xmw.qiyun.net.api.MySubscriber;
import com.xmw.qiyun.util.manage.ConstantsUtil;
import com.xmw.qiyun.util.share.WxShareUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by dell on 2018/1/26.
 */

public class PayResult {

    public void payResult(String result, int payType) {
        switch (payType) {
            case ConstantsUtil.PAY_ALI:
                aliPayResult(result, payType);
                break;
            case ConstantsUtil.PAY_WX:
                wxPayResult(result, payType);
                break;
        }
    }

    //支付宝支付结果
    private void aliPayResult(String result, int payType) {

        if (TextUtils.equals(result, "9000")) {
            onPaySuccess(payType);
        } else {
            onPayFail();
        }
    }

    //微信支付结果
    private void wxPayResult(String result, int payType) {

        if (TextUtils.equals(result, "0")) {
            onPaySuccess(payType);
        } else {
            onPayFail();
        }
    }

    //支付成功
    private void onPaySuccess(int payType) {

        switch (payType) {
            
            case ConstantsUtil.PAY_ALI: {

                PayResultBody payResultBody = new PayResultBody();
                payResultBody.setId(UserManager.getId());
                payResultBody.setAppId(ConstantsUtil.ALI_APP_ID);
                payResultBody.setPaymentChannel(2);
                payResultBody.setTradeNumber(StoreManager.getOrderId());

                API.getService().getWxResult(payResultBody).subscribe(new MySubscriber<CommonResponse>() {
                    @Override
                    public void onNext(CommonResponse commonResponse) {

                        EventBus.getDefault().post(new ResultEvent(TextUtils.equals("TRADE_SUCCESS", commonResponse.getMessage())));
                    }
                });
            }
            break;
            
            case ConstantsUtil.PAY_WX: {

                PayResultBody payResultBody = new PayResultBody();
                payResultBody.setId(UserManager.getId());
                payResultBody.setAppId(WxShareUtil.APP_ID);
                payResultBody.setPaymentChannel(1);
                payResultBody.setTradeNumber(StoreManager.getOrderId());
                payResultBody.setTransactionId(StoreManager.getWxId());

                API.getService().getWxResult(payResultBody).subscribe(new MySubscriber<CommonResponse>() {
                    @Override
                    public void onNext(CommonResponse commonResponse) {

                        EventBus.getDefault().post(new ResultEvent(TextUtils.equals("SUCCESS", commonResponse.getMessage())));
                    }
                });
            }
            break;
        }
    }

    //支付失败
    private void onPayFail() {

        EventBus.getDefault().post(new ResultEvent(false));
    }
}
