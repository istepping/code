package com.xmw.qiyun.ui.price;

import com.xmw.qiyun.base.BasePresenter;
import com.xmw.qiyun.base.BaseView;

/**
 * Created by hongyuan on 2017/9/18.
 */

interface PriceContract {

    interface View extends BaseView<Presenter> {

        void init();
    }

    interface Presenter extends BasePresenter<View> {

    }
}
