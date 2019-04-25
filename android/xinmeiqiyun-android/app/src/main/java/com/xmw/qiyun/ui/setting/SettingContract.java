package com.xmw.qiyun.ui.setting;

import com.xmw.qiyun.base.BasePresenter;
import com.xmw.qiyun.base.BaseView;

/**
 * Created by hongyuan on 2017/8/29.
 */

interface SettingContract {

    interface View extends BaseView<Presenter> {

        void init();
    }

    interface Presenter extends BasePresenter<View> {

        void goAbout();
        void goUpdate();
    }
}
