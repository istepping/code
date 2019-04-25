package com.xmw.qiyun.ui.setting.aboutAndUpdate;

import android.content.Context;
import android.os.Bundle;

import com.xmw.qiyun.R;
import com.xmw.qiyun.data.model.net.other.VersionBean;
import com.xmw.qiyun.net.api.API;
import com.xmw.qiyun.net.api.MySubscriber;
import com.xmw.qiyun.ui.showText.ShowTextActivity;
import com.xmw.qiyun.util.dialog.DialogUtil;
import com.xmw.qiyun.util.manage.NoteUtil;
import com.xmw.qiyun.util.manage.RouterUtil;

/**
 * Created by hongyuan on 2017/8/29.
 */

class AboutAndUpdatePresentImpl implements AboutAndUpdateContract.Presenter {

    private Context mContext;
    private AboutAndUpdateContract.View mView;

    AboutAndUpdatePresentImpl(Context context, AboutAndUpdateContract.View view){

        mContext = context;
        mView = view;
    }

    @Override
    public void bindView(AboutAndUpdateContract.View view) {

    }

    @Override
    public void doCall() {

        DialogUtil.INSTANCE.initPhoneDialog(mContext, "4001650001");
    }

    @Override
    public void goShowText() {

        Bundle bundle = new Bundle();

        bundle.putString(ShowTextActivity.EXTRA_TITLE, mContext.getString(R.string.agreement_service));
        bundle.putString(ShowTextActivity.EXTRA_TXT, "file:///android_asset/service_agreement.html");

        RouterUtil.go(RouterUtil.ROUTER_SHOW_TEXT, mContext, bundle);
    }

    @Override
    public void doUpdateCheck() {

        NoteUtil.showLoading(mContext);

        API.getService().checkVersion().subscribe(new MySubscriber<VersionBean>() {
            @Override
            public void onNext(VersionBean versionBean) {

                NoteUtil.hideLoading();

                mView.translateVersion(versionBean);
            }
        });
    }
}
