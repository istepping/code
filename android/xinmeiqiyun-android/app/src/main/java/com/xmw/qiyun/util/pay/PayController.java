package com.xmw.qiyun.util.pay;

import android.app.Activity;

import com.xmw.qiyun.data.model.net.store.WxResult;

/**
 * Created by dell on 2018/1/26.
 */

public class PayController {

    //支付宝支付
    public static void aliPay(Activity context, String payInfo) {

        PayHelper.INSTANCE.aliPay(context, payInfo);
    }

    //微信支付
    public static void wxPay(Activity context, WxResult wxResult) {

        PayHelper.INSTANCE.wxPay(context, wxResult);
    }
}
