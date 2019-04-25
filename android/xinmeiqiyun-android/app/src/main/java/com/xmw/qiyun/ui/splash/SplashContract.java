package com.xmw.qiyun.ui.splash;

import com.xmw.qiyun.base.BasePresenter;
import com.xmw.qiyun.base.BaseView;

/**
 * Created by mac on 2017/7/26.
 */

interface SplashContract {
    interface View extends BaseView<Presenter> {
        void init();
    }
    interface Presenter extends BasePresenter<View> {
        void doStandardService();
        void doPositionService();
        void goWelcome();
        void checkHasLogin();
        void goLogin();
        void goInfo();
        void goHome();
    }
}
