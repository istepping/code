package com.xmw.qiyun.ui.price.priceListC;

import com.xmw.qiyun.base.BasePresenter;
import com.xmw.qiyun.base.BaseView;
import com.xmw.qiyun.data.model.net.price.PriceList;

/**
 * Created by hongyuan on 2017/9/18.
 */

interface PriceListCContract {

    interface View extends BaseView<Presenter> {

        void init();
        void showList(PriceList priceList);

        void doRefresh();
        void doLoadMore();

        void stopLoading();
    }

    interface Presenter extends BasePresenter<View> {

        void getData(int page);
    }
}
