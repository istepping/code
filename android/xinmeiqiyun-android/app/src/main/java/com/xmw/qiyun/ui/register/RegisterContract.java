package com.xmw.qiyun.ui.register;

import com.xmw.qiyun.base.BasePresenter;
import com.xmw.qiyun.base.BaseView;
import com.xmw.qiyun.data.model.net.user.CodeBody;

/**
 * Created by mac on 2017/7/27.
 */

interface RegisterContract {
    interface View extends BaseView<Presenter> {
        void countTime();
        void loadImageCode(byte[] bytes);
    }
    interface Presenter extends BasePresenter<View> {

        void getImage(String mobileCode);
        void getCode(CodeBody codeBody);
        void doRegister(String phone, String code);

        void goInformation();
    }
}
