package com.xmw.qiyun.ui.setting.aboutAndUpdate;

import com.xmw.qiyun.base.BasePresenter;
import com.xmw.qiyun.base.BaseView;
import com.xmw.qiyun.data.model.net.other.VersionBean;

/**
 * Created by hongyuan on 2017/8/29.
 */

interface AboutAndUpdateContract {

    interface View extends BaseView<Presenter> {

        void init();
        void translateVersion(VersionBean versionBean);
    }

    interface Presenter extends BasePresenter<View> {

        void doCall();
        void goShowText();
        void doUpdateCheck();
    }
}
