package com.xmw.qiyun.ui.cargo.cargoOwner;

import com.xmw.qiyun.base.BasePresenter;
import com.xmw.qiyun.base.BaseView;
import com.xmw.qiyun.data.model.net.cargo.CargoOwner;
import com.xmw.qiyun.data.model.net.cargo.CargoOwnerBean;

/**
 * Created by hongyuan on 2017/8/29.
 */

interface CargoOwnerContract {

    interface View extends BaseView<Presenter> {

        void init();

        void refreshData(CargoOwnerBean cargoOwnerBean);
    }

    interface Presenter extends BasePresenter<View> {

        void getData(String id);

        void goMap(CargoOwner cargoOwner);
    }
}
