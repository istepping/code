package com.xmw.qiyun.ui.inputSingle;

import com.xmw.qiyun.base.BasePresenter;
import com.xmw.qiyun.base.BaseView;

/**
 * Created by hongyuan on 2017/8/28.
 */

interface InputSingleContract {

    interface View extends BaseView<Presenter> {

        void init();
    }

    interface Presenter extends BasePresenter<View> {

        void save(Object object);
    }
}
