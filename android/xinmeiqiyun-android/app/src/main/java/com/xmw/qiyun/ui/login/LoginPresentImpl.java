package com.xmw.qiyun.ui.login;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Base64;

import com.xmw.qiyun.data.manager.UserManager;
import com.xmw.qiyun.data.model.net.CommonResponse;
import com.xmw.qiyun.data.model.net.user.CodeBody;
import com.xmw.qiyun.data.model.net.user.ImageUploadBean;
import com.xmw.qiyun.data.model.net.user.LoginAndRegisterBean;
import com.xmw.qiyun.data.model.net.user.LoginAndRegisterBody;
import com.xmw.qiyun.net.api.API;
import com.xmw.qiyun.net.api.MySubscriber;
import com.xmw.qiyun.ui.information.InformationActivity;
import com.xmw.qiyun.util.manage.NoteUtil;
import com.xmw.qiyun.util.manage.RouterUtil;
import com.xmw.qiyun.util.manage.SystemUtil;

import okhttp3.MultipartBody;

/**
 * Created by mac on 2017/7/27.
 */

class LoginPresentImpl implements LoginContract.Presenter {

    private Context mContext;
    private LoginContract.View mView;

    LoginPresentImpl(Context context, LoginContract.View view){

        mContext = context;
        mView = view;
    }

    @Override
    public void bindView(LoginContract.View view) {

    }

    @Override
    public void getImage(String mobileCode) {

        NoteUtil.showLoading(mContext);

        API.getService().getImage(mobileCode, 1).subscribe(new MySubscriber<ImageUploadBean>() {
            @Override
            public void onNext(ImageUploadBean imageUploadBean) {

                NoteUtil.hideLoading();

                if(imageUploadBean.getStatusCode() == 1){

                    mView.loadImageCode(Base64.decode(imageUploadBean.getData(), Base64.DEFAULT));
                }
            }
        });
    }

    @Override
    public void getCode(CodeBody codeBody) {

        NoteUtil.showLoading(mContext);

        API.getService().getLoginCode(codeBody).subscribe(new MySubscriber<CommonResponse>() {
            @Override
            public void onNext(CommonResponse commonResponse) {

                NoteUtil.hideLoading();
                NoteUtil.showToast(mContext, commonResponse.getMessage());

                if(commonResponse.getStatusCode() == 1){

                    mView.countTime();

                }else{

                    getImage(SystemUtil.getDeviceId());
                }
            }
        });
    }

    @Override
    public void doLogin(final String phone, String code) {

        NoteUtil.showLoading(mContext);

        API.getService().login(new LoginAndRegisterBody(phone, code)).subscribe(new MySubscriber<LoginAndRegisterBean>() {
            @Override
            public void onNext(LoginAndRegisterBean loginAndRegisterBean) {

                NoteUtil.hideLoading();
                NoteUtil.showToast(mContext, loginAndRegisterBean.getMessage());

                if(loginAndRegisterBean.getStatusCode() == 1){

                    UserManager.login(loginAndRegisterBean.getData());
                    UserManager.savePhone(phone);

                    if(loginAndRegisterBean.getData().isFull()) {

                        goHome();

                    } else {

                        goInformation();
                    }

                }else{

                    getImage(SystemUtil.getDeviceId());
                }
            }
        });
    }

    @Override
    public void goRegister() {

        RouterUtil.go(RouterUtil.ROUTER_REGISTER, mContext);
    }

    @Override
    public void goInformation() {

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