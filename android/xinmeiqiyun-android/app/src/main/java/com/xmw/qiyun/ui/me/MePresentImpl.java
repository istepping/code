package com.xmw.qiyun.ui.me;

import android.content.Context;
import android.os.Bundle;

import com.xmw.qiyun.data.manager.UserManager;
import com.xmw.qiyun.data.model.net.user.UserInfoBean;
import com.xmw.qiyun.net.api.API;
import com.xmw.qiyun.net.api.MySubscriber;
import com.xmw.qiyun.ui.information.InformationActivity;
import com.xmw.qiyun.util.manage.NoteUtil;
import com.xmw.qiyun.util.manage.RouterUtil;

/**
 * Created by mac on 2017/8/2.
 */

class MePresentImpl implements MeContract.Presenter {

    private Context mContext;

    private MeContract.View mView;

    MePresentImpl(Context context, MeContract.View view) {

        mContext = context;
        mView = view;
    }

    @Override
    public void bindView(MeContract.View view) {

    }

    @Override
    public void getUserInfo() {

        NoteUtil.showLoading(mContext);

        API.getService().getUserInfo(UserManager.getId()).subscribe(new MySubscriber<UserInfoBean>() {
            @Override
            public void onNext(UserInfoBean userInfoBean) {

                NoteUtil.hideLoading();

                mView.getData(userInfoBean);
            }
        });
    }

    @Override
    public void goVerify() {

        RouterUtil.go(RouterUtil.ROUTER_VERIFY, mContext);
    }

    @Override
    public void goInformation() {

        Bundle bundle = new Bundle();
        bundle.putInt(InformationActivity.EXTRA_FROM_PAGE, 1);

        RouterUtil.go(RouterUtil.ROUTER_INFORMATION, mContext, bundle);
    }

    @Override
    public void goStore() {

        RouterUtil.go(RouterUtil.ROUTER_STORE, mContext);
    }

    @Override
    public void goSetting() {

        RouterUtil.go(RouterUtil.ROUTER_SETTING, mContext);
    }
}
