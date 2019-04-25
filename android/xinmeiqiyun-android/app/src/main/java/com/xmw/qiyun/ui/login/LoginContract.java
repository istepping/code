package com.xmw.qiyun.ui.login;

import com.xmw.qiyun.base.BasePresenter;
import com.xmw.qiyun.base.BaseView;
import com.xmw.qiyun.data.model.net.user.CodeBody;

/**
 * Created by mac on 2017/7/27.
 */

interface LoginContract {

    interface View extends BaseView<Presenter> {

        void countTime();
        void loadImageCode(byte[] bytes);
    }

    interface Presenter extends BasePresenter<View> {

        void getImage(String mobileCode);
        void getCode(CodeBody codeBody);
        void doLogin(String phone, String code);

        void goRegister();
        void goInformation();
        void goHome();
    }
}