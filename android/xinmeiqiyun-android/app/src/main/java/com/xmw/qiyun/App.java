package com.xmw.qiyun;

import android.app.Application;

import com.chenenyu.router.Router;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xmw.qiyun.util.share.WxShareUtil;

/**
 * Created by mac on 2017/7/26.
 */

public class App extends Application {

    private static App instance;

    private static IWXAPI api;

    public static App getInstance() {

        return instance;
    }

    public static IWXAPI getApi() {

        return api;
    }

    @Override
    public void onCreate() {

        super.onCreate();

        instance = this;

        Router.initialize(this);

        api = WXAPIFactory.createWXAPI(this, WxShareUtil.APP_ID, false);

        api.registerApp(WxShareUtil.APP_ID);
    }
}