package com.xmw.qiyun.ui.cargo.cargoDetail;

import com.xmw.qiyun.base.BasePresenter;
import com.xmw.qiyun.base.BaseView;
import com.xmw.qiyun.data.model.net.cargo.CargoDetail;

/**
 * Created by hongyuan on 2017/8/11.
 */

interface CargoDetailContract {

    interface View extends BaseView<Presenter> {

        void init();

        void refreshData(CargoDetail cargoDetail);
    }

    interface Presenter extends BasePresenter<View> {

        void getData(String id);

        void doGoClickCargoMaster(String id);

        void doCallItem(String mobile);
    }
}
