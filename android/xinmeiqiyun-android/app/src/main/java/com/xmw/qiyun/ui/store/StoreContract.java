package com.xmw.qiyun.ui.store;

import com.xmw.qiyun.base.BasePresenter;
import com.xmw.qiyun.base.BaseView;

/**
 * Created by dell on 2018/1/10.
 */

interface StoreContract {

    interface View extends BaseView<Presenter> {

        void init();
    }

    interface Presenter extends BasePresenter<View> {

    }
}
