package com.xmw.qiyun.ui.inputMulti;

import com.xmw.qiyun.base.BasePresenter;
import com.xmw.qiyun.base.BaseView;

/**
 * Created by hongyuan on 2017/8/28.
 */

interface InputMultiContract {

    interface View extends BaseView<Presenter> {

        void init();
    }

    interface Presenter extends BasePresenter<View> {

        void save(Object object);
    }
}
