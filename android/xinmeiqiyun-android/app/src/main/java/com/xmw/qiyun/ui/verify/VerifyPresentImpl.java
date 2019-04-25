package com.xmw.qiyun.ui.verify;

import android.content.Context;
import android.os.Bundle;

import com.xmw.qiyun.R;
import com.xmw.qiyun.data.model.net.CommonResponse;
import com.xmw.qiyun.data.model.net.user.VerifyPersonalAndCompany;
import com.xmw.qiyun.data.model.net.user.VerifyPersonalAndCompanyBean;
import com.xmw.qiyun.net.api.API;
import com.xmw.qiyun.net.api.MySubscriber;
import com.xmw.qiyun.net.json.GsonImpl;
import com.xmw.qiyun.ui.verify.verifyCompany.VerifyCompanyActivity;
import com.xmw.qiyun.util.manage.NoteUtil;
import com.xmw.qiyun.util.manage.RouterUtil;

/**
 * Created by hongyuan on 2017/8/29.
 */

class VerifyPresentImpl implements VerifyContract.Presenter {

    private Context mContext;
    private VerifyContract.View mView;

    VerifyPresentImpl(Context context, VerifyContract.View view) {

        mContext = context;
        mView = view;
    }

    @Override
    public void bindView(VerifyContract.View view) {

    }

    @Override
    public void getVerifyInfo() {

        NoteUtil.showLoading(mContext);

        API.getService().getPersonalAndCompanyInfo().subscribe(new MySubscriber<VerifyPersonalAndCompanyBean>() {
            @Override
            public void onNext(VerifyPersonalAndCompanyBean verifyPersonalAndCompanyBean) {

                NoteUtil.hideLoading();
                NoteUtil.showToast(mContext, verifyPersonalAndCompanyBean.getMessage());

                mView.getData(verifyPersonalAndCompanyBean.getData());
            }
        });
    }

    @Override
    public void goVerifyPersonal(VerifyPersonalAndCompany verifyPersonalAndCompany, boolean hasApplied) {

        if (verifyPersonalAndCompany == null) {

            NoteUtil.showToast(mContext, mContext.getString(R.string.toast_verify_fail));

            return;
        }

        Bundle bundle = new Bundle();
        bundle.putString(VerifyActivity.EXTRA_VERIFY_VALUE, GsonImpl.init().toJson(verifyPersonalAndCompany));
        bundle.putBoolean(VerifyActivity.EXTRA_VERIFY_HAS_APPLIED, hasApplied);

        RouterUtil.go(RouterUtil.ROUTER_VERIFY_PERSONAL, mContext, bundle);
    }

    @Override
    public void goVerifyCompany(VerifyPersonalAndCompany verifyPersonalAndCompany, boolean hasApplied) {

        Bundle bundle = new Bundle();
        bundle.putString(VerifyActivity.EXTRA_VERIFY_VALUE, GsonImpl.init().toJson(verifyPersonalAndCompany));
        bundle.putBoolean(VerifyActivity.EXTRA_VERIFY_HAS_APPLIED, hasApplied);
        bundle.putInt(VerifyCompanyActivity.EXTRA_VERIFY_FROM, VerifyCompanyActivity.EXTRA_VERIFY_FROM_TOTAL);

        RouterUtil.go(RouterUtil.ROUTER_VERIFY_COMPANY, mContext, bundle);
    }

    @Override
    public void doApply(VerifyPersonalAndCompany verifyPersonalAndCompany) {

        NoteUtil.showLoading(mContext);

        API.getService().appPersonalAndCompanyInfo().subscribe(new MySubscriber<CommonResponse>() {
            @Override
            public void onNext(CommonResponse commonResponse) {

                NoteUtil.hideLoading();
                NoteUtil.showToast(mContext, commonResponse.getMessage());

                mView.init();
            }
        });
    }
}
