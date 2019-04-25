package com.xmw.qiyun.ui.me;

import com.xmw.qiyun.base.BasePresenter;
import com.xmw.qiyun.base.BaseView;
import com.xmw.qiyun.data.model.net.user.UserInfoBean;

/**
 * Created by mac on 2017/8/2.
 */

interface MeContract {

    interface View extends BaseView<Presenter> {

        void init();
        void getData(UserInfoBean userInfoBean);
    }

    interface Presenter extends BasePresenter<View> {

        void getUserInfo();
        void goVerify();
        void goInformation();
        void goStore();
        void goSetting();
    }
}
