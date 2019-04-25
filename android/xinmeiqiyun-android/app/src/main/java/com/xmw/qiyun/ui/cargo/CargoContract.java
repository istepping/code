package com.xmw.qiyun.ui.cargo;

import com.xmw.qiyun.base.BasePresenter;
import com.xmw.qiyun.base.BaseView;

/**
 * Created by dell on 2017/11/1.
 */

interface CargoContract {

    interface View extends BaseView<Presenter> {

        void init();

        void initTitleBar(boolean isCargoList);
    }

    interface Presenter extends BasePresenter<View> {

        void goMap();
    }
}
