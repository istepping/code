package com.xmw.qiyun.ui.setting;

import android.content.Context;
import android.os.Bundle;

import com.xmw.qiyun.ui.setting.aboutAndUpdate.AboutAndUpdateActivity;
import com.xmw.qiyun.util.manage.RouterUtil;

/**
 * Created by hongyuan on 2017/8/29.
 */

class SettingPresentImpl implements SettingContract.Presenter {

    private Context mContext;

    SettingPresentImpl(Context context){
        mContext = context;
    }

    @Override
    public void bindView(SettingContract.View view) {

    }

    @Override
    public void goAbout() {

        Bundle bundle = new Bundle();
        bundle.putInt(AboutAndUpdateActivity.EXTRA_ABOUT_UPDATE_TYPE, AboutAndUpdateActivity.EXTRA_ABOUT);

        RouterUtil.go(RouterUtil.ROUTER_ABOUT_AND_UPDATE, mContext, bundle);
    }

    @Override
    public void goUpdate() {

        Bundle bundle = new Bundle();
        bundle.putInt(AboutAndUpdateActivity.EXTRA_ABOUT_UPDATE_TYPE, AboutAndUpdateActivity.EXTRA_UPDATE);

        RouterUtil.go(RouterUtil.ROUTER_ABOUT_AND_UPDATE, mContext, bundle);
    }
}

