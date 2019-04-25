package com.xmw.qiyun.ui.splash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.xmw.qiyun.data.manager.UserManager;
import com.xmw.qiyun.data.model.net.standard.ProvinceBean;
import com.xmw.qiyun.data.model.net.user.LoginAndRegisterBean;
import com.xmw.qiyun.net.api.API;
import com.xmw.qiyun.net.api.MySubscriber;
import com.xmw.qiyun.service.PositionService;
import com.xmw.qiyun.service.StandardService;
import com.xmw.qiyun.ui.information.InformationActivity;
import com.xmw.qiyun.util.manage.CommonUtil;
import com.xmw.qiyun.util.manage.RouterUtil;

/**
 * Created by mac on 2017/7/26.
 */

class SplashPresentImpl implements SplashContract.Presenter {

    private Context mContext;

    SplashPresentImpl(Context context){
        mContext = context;
    }

    @Override
    public void bindView(SplashContract.View view) {

    }

    @Override
    public void doStandardService() {

        mContext.startService(new Intent(mContext, StandardService.class));
    }

    @Override
    public void doPositionService() {

        mContext.startService(new Intent(mContext, PositionService.class));
    }

    @Override
    public void goWelcome() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                RouterUtil.go(RouterUtil.ROUTER_WELCOME, mContext);

                ((Activity)mContext).finish();

            }
        }, 500);
    }

    @Override
    public void checkHasLogin() {

        if (CommonUtil.isNullOrEmpty(UserManager.getToken())) {

            UserManager.logout();
            goLogin();

        } else {

            API.getService().reLogin(UserManager.getPhone()).subscribe(new MySubscriber<LoginAndRegisterBean>() {
                @Override
                public void onNext(LoginAndRegisterBean loginAndRegisterBean) {

                    if(loginAndRegisterBean.getStatusCode() == 1){

                        UserManager.login(loginAndRegisterBean.getData());

                        if(loginAndRegisterBean.getData().isFull()){

                            goHome();

                        }else{

                            goInfo();
                        }

                    }else{

                        UserManager.logout();
                        goLogin();
                    }
                }
            });
        }
    }

    @Override
    public void goLogin() {

        RouterUtil.go(RouterUtil.ROUTER_LOGIN, mContext);
        ((Activity)mContext).finish();
    }

    @Override
    public void goInfo() {

        Bundle bundle = new Bundle();
        bundle.putInt(InformationActivity.EXTRA_FROM_PAGE, 0);

        RouterUtil.go(RouterUtil.ROUTER_INFORMATION, mContext, bundle);
        ((Activity)mContext).finish();
    }

    @Override
    public void goHome() {

        RouterUtil.go(RouterUtil.ROUTER_HOME, mContext);
        ((Activity)mContext).finish();
    }
}