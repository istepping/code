package com.xmw.qiyun.ui.truck.truckDetail;

import com.xmw.qiyun.base.BasePresenter;
import com.xmw.qiyun.base.BaseView;
import com.xmw.qiyun.data.model.net.truck.Truck;
import com.xmw.qiyun.data.model.net.truck.TruckDetail;

/**
 * Created by hongyuan on 2017/8/11.
 */

interface TruckDetailContract {

    interface View extends BaseView<Presenter> {

        void init();
        void refreshData(TruckDetail truckDetail);
    }

    interface Presenter extends BasePresenter<View> {

        void getData(String id);
    }
}
