package com.xmw.qiyun.ui.welcome;

import android.app.Activity;
import android.content.Context;

import com.xmw.qiyun.data.manager.UserManager;
import com.xmw.qiyun.util.manage.CommonUtil;
import com.xmw.qiyun.util.manage.RouterUtil;

/**
 * Created by hongyuan on 2017/9/19.
 */

class WelcomePresentImpl implements WelcomeContract.Presenter {

    private Context mContext;

    WelcomePresentImpl(Context context){
        mContext = context;
    }

    @Override
    public void bindView(WelcomeContract.View view) {

    }

    @Override
    public void checkHasLogin() {

        UserManager.saveIsFirst(false);

        if(!CommonUtil.isNullOrEmpty(UserManager.getToken()) & UserManager.getIsFull()){

            goHome();

        }else{

            UserManager.logout();
            goLogin();
        }
    }

    @Override
    public void goLogin() {

        RouterUtil.go(RouterUtil.ROUTER_LOGIN, mContext);
        ((Activity)mContext).finish();
    }

    @Override
    public void goHome() {

        RouterUtil.go(RouterUtil.ROUTER_HOME, mContext);
        ((Activity)mContext).finish();
    }
}
