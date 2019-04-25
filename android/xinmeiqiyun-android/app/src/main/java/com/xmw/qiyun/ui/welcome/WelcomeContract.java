package com.xmw.qiyun.ui.welcome;

import com.xmw.qiyun.base.BasePresenter;
import com.xmw.qiyun.base.BaseView;

/**
 * Created by hongyuan on 2017/9/19.
 */

public interface WelcomeContract {

    interface View extends BaseView<Presenter> {

        void init();
    }

    interface Presenter extends BasePresenter<View> {

        void checkHasLogin();
        void goLogin();
        void goHome();
    }
}
