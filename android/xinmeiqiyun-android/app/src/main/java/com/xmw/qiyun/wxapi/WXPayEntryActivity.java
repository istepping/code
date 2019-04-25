package com.xmw.qiyun.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.xmw.qiyun.App;
import com.xmw.qiyun.data.manager.StoreManager;
import com.xmw.qiyun.util.manage.ConstantsUtil;
import com.xmw.qiyun.util.pay.PayResult;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        App.getApi().handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {

        super.onNewIntent(intent);

        setIntent(intent);

        App.getApi().handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {

            new PayResult().payResult(String.valueOf(resp.errCode), ConstantsUtil.PAY_WX);

            if (resp.errCode == 0) StoreManager.setWxId(resp.transaction);

            finish();
        }
    }
}