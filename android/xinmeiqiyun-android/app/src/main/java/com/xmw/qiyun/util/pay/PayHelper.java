package com.xmw.qiyun.util.pay;

import android.app.Activity;
import android.os.AsyncTask;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.xmw.qiyun.App;
import com.xmw.qiyun.data.model.net.store.WxResult;
import com.xmw.qiyun.util.manage.ConstantsUtil;
import com.xmw.qiyun.util.manage.NoteUtil;
import com.xmw.qiyun.util.share.WxShareUtil;

import java.util.Map;

/**
 * Created by dell on 2018/1/26.
 */

enum PayHelper {

    INSTANCE;

    private static final String WX_PAY_ERROR_1 = "您没有安装微信";
    private static final String WX_PAY_ERROR_2 = "当前版本不支持支付功能";
    private static final String WX_PAY_ERROR_3 = "微信支付失败";

    //支付宝支付
    public void aliPay(Activity activity, String payInfo) {

        new AliPayTask(activity, payInfo).execute();
    }

    //微信支付
    public void wxPay(Activity activity, WxResult wxResult) {

        new WxTask(activity, wxResult).execute();
    }

    //支付宝支付线程
    private class AliPayTask extends AsyncTask<String, Integer, Map<String, String>> {

        private Activity mContext;
        private String mPayInfo;

        AliPayTask(Activity context, String payInfo) {

            super();
            mContext = context;
            mPayInfo = payInfo;
        }

        @Override
        protected Map<String, String> doInBackground(String... params) {

            return new PayTask(mContext).payV2(mPayInfo, true);
        }

        @Override
        protected void onPostExecute(Map<String, String> payResult) {

            new PayResult().payResult(payResult.get("resultStatus"), ConstantsUtil.PAY_ALI);
        }
    }

    //微信支付线程
    private class WxTask extends AsyncTask<String, Integer, Boolean> {

        private Activity mContext;
        private WxResult mWxResult;
        private boolean canPay;

        WxTask(Activity context, WxResult wxResult) {

            mContext = context;
            mWxResult = wxResult;
        }

        @Override
        protected void onPreExecute() {

            canPay = true;

            if (!App.getApi().isWXAppInstalled()) {

                NoteUtil.showToast(mContext, WX_PAY_ERROR_1);
                canPay = false;

            }
        }

        @Override
        protected Boolean doInBackground(String... params) {

            boolean sendReq = false;

            if (canPay) {

                PayReq request = new PayReq();
                request.appId = WxShareUtil.APP_ID;
                request.partnerId = mWxResult.getPartnerId();
                request.prepayId = mWxResult.getPrepayId();
                request.packageValue = mWxResult.getPackageValue();
                request.nonceStr = mWxResult.getNonceStr();
                request.timeStamp = mWxResult.getTimeStamp();
                request.sign = mWxResult.getSign();
                sendReq = App.getApi().sendReq(request);
            }

            return sendReq;
        }

        @Override
        protected void onPostExecute(Boolean result) {

            if (!result) {
                NoteUtil.showToast(mContext, WX_PAY_ERROR_3);
            }
        }
    }
}
